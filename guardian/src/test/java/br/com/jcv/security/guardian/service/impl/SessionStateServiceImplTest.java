/*
Copyright <YEAR> <COPYRIGHT HOLDER>

This software is Open Source and is under MIT license agreement

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the “Software”), to deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions
of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
OR OTHER DEALINGS IN THE SOFTWARE.
*/

package br.com.jcv.security.guardian.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.security.guardian.builder.SessionStateDTOBuilder;
import br.com.jcv.security.guardian.builder.SessionStateModelBuilder;
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.exception.SessionStateNotFoundException;
import br.com.jcv.security.guardian.model.SessionState;
import br.com.jcv.security.guardian.repository.SessionStateRepository;
import br.com.jcv.security.guardian.service.SessionStateService;
import br.com.jcv.security.guardian.constantes.SessionStateConstantes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class SessionStateServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String SESSIONSTATE_NOTFOUND_WITH_ID = "SessionState não encontrada com id = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_IDTOKEN = "SessionState não encontrada com idToken = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_STATUS = "SessionState não encontrada com status = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_DATECREATED = "SessionState não encontrada com dateCreated = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_DATEUPDATED = "SessionState não encontrada com dateUpdated = ";


    @Mock
    private SessionStateRepository sessionstateRepositoryMock;

    @InjectMocks
    private SessionStateService sessionstateService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        sessionstateService = new SessionStateServiceImpl();
        MockitoAnnotations.initMocks(this);

        uuidMockedStatic = Mockito.mockStatic(UUID.class, Mockito.RETURNS_DEEP_STUBS);
        dateUtilityMockedStatic = Mockito.mockStatic(DateUtility.class, Mockito.RETURNS_DEEP_STUBS);

        uuidMockedStatic.when(() -> UUID.fromString(Mockito.anyString())).thenReturn(uuidMock);
    }

    @AfterAll
    public void tearDown() {
        uuidMockedStatic.close();
        dateUtilityMockedStatic.close();
    }


    @Test
    public void shouldReturnListOfSessionStateWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 6858L;
        UUID idToken = UUID.fromString("782c325a-e036-4c0d-9d9e-81acb225e127");
        String status = "MaWgMbk1DFb0jRKQwOCxy8g4phvhJ3ICW604iWtOGWb0a9E6VJ";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idToken", idToken);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<SessionState> sessionstatesFromRepository = new ArrayList<>();
        sessionstatesFromRepository.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        sessionstatesFromRepository.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        sessionstatesFromRepository.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        sessionstatesFromRepository.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());

        Mockito.when(sessionstateRepositoryMock.findSessionStateByFilter(
            id,
            idToken,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(sessionstatesFromRepository);

        // action
        List<SessionStateDTO> result = sessionstateService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithSessionStateListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 5020L;
        UUID idToken = UUID.fromString("2f17b33a-1057-4f17-a1df-93173c6b9fd2");
        String status = "x00idp0MCd51n2XUzdufvkNp7a2bkQdIvXKezsXfMbB6k2p9xF";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idToken", idToken);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<SessionState> sessionstatesFromRepository = new ArrayList<>();
        sessionstatesFromRepository.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        sessionstatesFromRepository.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        sessionstatesFromRepository.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        sessionstatesFromRepository.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());

        List<SessionStateDTO> sessionstatesFiltered = sessionstatesFromRepository
                .stream()
                .map(m->sessionstateService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageSessionStateItems", sessionstatesFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<SessionState> pagedResponse =
                new PageImpl<>(sessionstatesFromRepository,
                        pageableMock,
                        sessionstatesFromRepository.size());

        Mockito.when(sessionstateRepositoryMock.findSessionStateByFilter(pageableMock,
            id,
            idToken,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = sessionstateService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<SessionStateDTO> sessionstatesResult = (List<SessionStateDTO>) result.get("pageSessionStateItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, sessionstatesResult.size());
    }


    @Test
    public void showReturnListOfSessionStateWhenAskedForFindAllByStatus() {
        // scenario
        List<SessionState> listOfSessionStateModelMock = new ArrayList<>();
        listOfSessionStateModelMock.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        listOfSessionStateModelMock.add(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());

        Mockito.when(sessionstateRepositoryMock.findAllByStatus("A")).thenReturn(listOfSessionStateModelMock);

        // action
        List<SessionStateDTO> listOfSessionStates = sessionstateService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfSessionStates.isEmpty());
        Assertions.assertEquals(2, listOfSessionStates.size());
    }
    @Test
    public void shouldReturnSessionStateNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 8063L;
        Optional<SessionState> sessionstateNonExistentMock = Optional.empty();
        Mockito.when(sessionstateRepositoryMock.findById(idMock)).thenReturn(sessionstateNonExistentMock);

        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowSessionStateNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 22203L;
        Mockito.when(sessionstateRepositoryMock.findById(idMock))
                .thenThrow(new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                SESSIONSTATE_NOTFOUND_WITH_ID ));

        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnSessionStateDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 26016L;
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(
                SessionStateModelBuilder.newSessionStateModelTestBuilder()
                        .id(idMock)
                        .idToken(UUID.fromString("c6531239-9123-433c-81ca-e1a23f943c9f"))

                        .status("X")
                        .now()
        );
        SessionState sessionstateToSaveMock = sessionstateModelMock.orElse(null);
        SessionState sessionstateSavedMck = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                        .id(80375L)
                        .idToken(UUID.fromString("49cb76f5-0044-4102-afc9-ecd97fc3381e"))

                        .status("A")
                        .now();
        Mockito.when(sessionstateRepositoryMock.findById(idMock)).thenReturn(sessionstateModelMock);
        Mockito.when(sessionstateRepositoryMock.save(sessionstateToSaveMock)).thenReturn(sessionstateSavedMck);

        // action
        SessionStateDTO result = sessionstateService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchSessionStateByAnyNonExistenceIdAndReturnSessionStateNotFoundException() {
        // scenario
        Mockito.when(sessionstateRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()-> sessionstateService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchSessionStateByIdAndReturnDTO() {
        // scenario
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(50041L)
                .idToken(UUID.fromString("29384c8f-f5ff-4c9a-9f9b-ccf28c9310e9"))

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(sessionstateRepositoryMock.findById(Mockito.anyLong())).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findById(1L);

        // validate
        Assertions.assertInstanceOf(SessionStateDTO.class,result);
    }
    @Test
    public void shouldDeleteSessionStateByIdWithSucess() {
        // scenario
        Optional<SessionState> sessionstate = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder().id(1L).now());
        Mockito.when(sessionstateRepositoryMock.findById(Mockito.anyLong())).thenReturn(sessionstate);

        // action
        sessionstateService.delete(1L);

        // validate
        Mockito.verify(sessionstateRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceSessionStateShouldReturnSessionStateNotFoundException() {
        // scenario
        Mockito.when(sessionstateRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(
                SessionStateNotFoundException.class, () -> sessionstateService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingSessionStateWithSucess() {
        // scenario
        SessionStateDTO sessionstateDTOMock = SessionStateDTOBuilder.newSessionStateDTOTestBuilder()
                .id(62672L)
                .idToken(UUID.fromString("c1020ce4-3a74-4bac-9dd8-2847d93e9632"))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SessionState sessionstateMock = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(sessionstateDTOMock.getId())
                .idToken(sessionstateDTOMock.getIdToken())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SessionState sessionstateSavedMock = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(sessionstateDTOMock.getId())
                .idToken(sessionstateDTOMock.getIdToken())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(sessionstateRepositoryMock.save(sessionstateMock)).thenReturn(sessionstateSavedMock);

        // action
        SessionStateDTO sessionstateSaved = sessionstateService.salvar(sessionstateDTOMock);

        // validate
        Assertions.assertInstanceOf(SessionStateDTO.class, sessionstateSaved);
        Assertions.assertNotNull(sessionstateSaved.getId());
    }

    @Test
    public void ShouldSaveNewSessionStateWithSucess() {
        // scenario
        SessionStateDTO sessionstateDTOMock = SessionStateDTOBuilder.newSessionStateDTOTestBuilder()
                .id(null)
                .idToken(UUID.fromString("82eef911-dbbb-4139-8c05-cb3a446f2480"))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SessionState sessionstateModelMock = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(null)
                .idToken(sessionstateDTOMock.getIdToken())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SessionState sessionstateSavedMock = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(501L)
                .idToken(sessionstateDTOMock.getIdToken())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(sessionstateRepositoryMock.save(sessionstateModelMock)).thenReturn(sessionstateSavedMock);

        // action
        SessionStateDTO sessionstateSaved = sessionstateService.salvar(sessionstateDTOMock);

        // validate
        Assertions.assertInstanceOf(SessionStateDTO.class, sessionstateSaved);
        Assertions.assertNotNull(sessionstateSaved.getId());
        Assertions.assertEquals("P",sessionstateSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapSessionStateDTOMock = new HashMap<>();
        mapSessionStateDTOMock.put(SessionStateConstantes.IDTOKEN,UUID.fromString("a9383fc4-79c3-4205-8bf2-93643dccdb87"));
        mapSessionStateDTOMock.put(SessionStateConstantes.STATUS,"YaVnX5d7RUPGKbeJ5fvOWc2QjIjyd05KcuFp8NH91nUYLzf103");


        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(
                SessionStateModelBuilder.newSessionStateModelTestBuilder()
                        .id(4700L)
                        .idToken(UUID.fromString("28e65489-1cc9-4817-9e3d-3b3e225a6ff9"))
                        .status("8Yc8V2DAkf7c0pdik2SEOmXYFwpnc0VSf6Ratbddx6Bxes0rJ9")

                        .now()
        );

        Mockito.when(sessionstateRepositoryMock.findById(1L)).thenReturn(sessionstateModelMock);

        // action
        boolean executed = sessionstateService.partialUpdate(1L, mapSessionStateDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnSessionStateNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapSessionStateDTOMock = new HashMap<>();
        mapSessionStateDTOMock.put(SessionStateConstantes.IDTOKEN,UUID.fromString("e57f0c96-f9ff-4aec-be26-daa4814eab24"));
        mapSessionStateDTOMock.put(SessionStateConstantes.STATUS,"GR9H0oWYjfhdTSXEmd3oFzi76XVYYuIf70b8E8c7K0TAAKFxu0");


        Mockito.when(sessionstateRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.partialUpdate(1L, mapSessionStateDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("SessionState não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnSessionStateListWhenFindAllSessionStateByIdAndStatus() {
        // scenario
        List<SessionState> sessionstates = Arrays.asList(
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now()
        );

        Mockito.when(sessionstateRepositoryMock.findAllByIdAndStatus(30466L, "A")).thenReturn(sessionstates);

        // action
        List<SessionStateDTO> result = sessionstateService.findAllSessionStateByIdAndStatus(30466L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSessionStateListWhenFindAllSessionStateByIdTokenAndStatus() {
        // scenario
        List<SessionState> sessionstates = Arrays.asList(
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now()
        );

        Mockito.when(sessionstateRepositoryMock.findAllByIdTokenAndStatus(UUID.fromString("cf5c06e0-7600-4ea4-a486-a1279dcdf073"), "A")).thenReturn(sessionstates);

        // action
        List<SessionStateDTO> result = sessionstateService.findAllSessionStateByIdTokenAndStatus(UUID.fromString("cf5c06e0-7600-4ea4-a486-a1279dcdf073"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSessionStateListWhenFindAllSessionStateByDateCreatedAndStatus() {
        // scenario
        List<SessionState> sessionstates = Arrays.asList(
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now()
        );

        Mockito.when(sessionstateRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(sessionstates);

        // action
        List<SessionStateDTO> result = sessionstateService.findAllSessionStateByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSessionStateListWhenFindAllSessionStateByDateUpdatedAndStatus() {
        // scenario
        List<SessionState> sessionstates = Arrays.asList(
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now()
        );

        Mockito.when(sessionstateRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(sessionstates);

        // action
        List<SessionStateDTO> result = sessionstateService.findAllSessionStateByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentSessionStateDTOWhenFindSessionStateByIdAndStatus() {
        // scenario
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdAndStatus(660L, "A")).thenReturn(1L);
        Mockito.when(sessionstateRepositoryMock.findById(1L)).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findSessionStateByIdAndStatus(660L, "A");

        // validate
        Assertions.assertInstanceOf(SessionStateDTO.class,result);
    }
    @Test
    public void shouldReturnSessionStateNotFoundExceptionWhenNonExistenceSessionStateIdAndStatus() {
        // scenario
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdAndStatus(660L, "A")).thenReturn(0L);
        Mockito.when(sessionstateRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.findSessionStateByIdAndStatus(660L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentSessionStateDTOWhenFindSessionStateByIdTokenAndStatus() {
        // scenario
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdTokenAndStatus(UUID.fromString("f8d786d5-72c3-445a-8ce4-0342b7b8a907"), "A")).thenReturn(1L);
        Mockito.when(sessionstateRepositoryMock.findById(1L)).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findSessionStateByIdTokenAndStatus(UUID.fromString("f8d786d5-72c3-445a-8ce4-0342b7b8a907"), "A");

        // validate
        Assertions.assertInstanceOf(SessionStateDTO.class,result);
    }
    @Test
    public void shouldReturnSessionStateNotFoundExceptionWhenNonExistenceSessionStateIdTokenAndStatus() {
        // scenario
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdTokenAndStatus(UUID.fromString("f8d786d5-72c3-445a-8ce4-0342b7b8a907"), "A")).thenReturn(0L);
        Mockito.when(sessionstateRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.findSessionStateByIdTokenAndStatus(UUID.fromString("f8d786d5-72c3-445a-8ce4-0342b7b8a907"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_IDTOKEN));
    }

    @Test
    public void shouldReturnSessionStateDTOWhenUpdateExistingIdTokenById() {
        // scenario
        UUID idTokenUpdateMock = UUID.fromString("31c76ee7-833b-4dde-9480-55cba2b943cd");
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(sessionstateRepositoryMock.findById(420L)).thenReturn(sessionstateModelMock);
        Mockito.doNothing().when(sessionstateRepositoryMock).updateIdTokenById(420L, idTokenUpdateMock);

        // action
        sessionstateService.updateIdTokenById(420L, idTokenUpdateMock);

        // validate
        Mockito.verify(sessionstateRepositoryMock,Mockito.times(1)).updateIdTokenById(420L, idTokenUpdateMock);
    }



    @Test
    public void showReturnExistingSessionStateDTOWhenFindSessionStateByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 3078L;
        Long maxIdMock = 1972L;
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(sessionstateRepositoryMock.findById(maxIdMock)).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findSessionStateByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnSessionStateNotFoundExceptionWhenNonExistenceFindSessionStateByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 3078L;
        Long noMaxIdMock = 0L;
        Optional<SessionState> sessionstateModelMock = Optional.empty();
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(sessionstateRepositoryMock.findById(noMaxIdMock)).thenReturn(sessionstateModelMock);

        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.findSessionStateByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingSessionStateDTOWhenFindSessionStateByIdTokenAndStatusActiveAnonimous() {
        // scenario
        UUID idTokenMock = UUID.fromString("c5381f13-dad3-4ab3-96e5-6dc8fdd0b9fe");
        Long maxIdMock = 1972L;
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .idToken(idTokenMock)
                .now()
        );
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdTokenAndStatus(idTokenMock, "A")).thenReturn(maxIdMock);
        Mockito.when(sessionstateRepositoryMock.findById(maxIdMock)).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findSessionStateByIdTokenAndStatus(idTokenMock);

        // validate
        Assertions.assertEquals(idTokenMock, result.getIdToken());

    }
    @Test
    public void showReturnSessionStateNotFoundExceptionWhenNonExistenceFindSessionStateByIdTokenAndStatusActiveAnonimous() {
        // scenario
        UUID idTokenMock = UUID.fromString("c5381f13-dad3-4ab3-96e5-6dc8fdd0b9fe");
        Long noMaxIdMock = 0L;
        Optional<SessionState> sessionstateModelMock = Optional.empty();
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdTokenAndStatus(idTokenMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(sessionstateRepositoryMock.findById(noMaxIdMock)).thenReturn(sessionstateModelMock);

        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.findSessionStateByIdTokenAndStatus(idTokenMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_IDTOKEN));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

