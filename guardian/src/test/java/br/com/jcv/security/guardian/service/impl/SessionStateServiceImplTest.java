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
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
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

import com.google.gson.Gson;

@TestInstance(PER_CLASS)
public class SessionStateServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String SESSIONSTATE_NOTFOUND_WITH_ID = "SessionState não encontrada com id = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_IDTOKEN = "SessionState não encontrada com idToken = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_IDUSERUUID = "SessionState não encontrada com idUserUUID = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_STATUS = "SessionState não encontrada com status = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_DATECREATED = "SessionState não encontrada com dateCreated = ";
    public static final String SESSIONSTATE_NOTFOUND_WITH_DATEUPDATED = "SessionState não encontrada com dateUpdated = ";


    @Mock
    private SessionStateRepository sessionstateRepositoryMock;
    @Mock
    private CacheProvider redisProviderMock;
    @Mock
    private Gson gsonMock;
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
        Long id = 32L;
        UUID idToken = UUID.fromString("accf21e7-91c2-45a2-a718-2d5aa364eef7");
        UUID idUserUUID = UUID.fromString("8608c6ac-1c21-4af0-9e90-e499ba5655ac");
        String status = "5HqTjT8eavI0nlpk3byfWFmnHqeI64zlRamktJKWbtV0aD81vh";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idToken", idToken);
        mapFieldsRequestMock.put("idUserUUID", idUserUUID);
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
            idUserUUID,
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
        Long id = 400L;
        UUID idToken = UUID.fromString("09602b2e-11af-44e8-a82c-c68d9b4157dd");
        UUID idUserUUID = UUID.fromString("f0390f7b-6ea4-43cc-b92e-0d121afc4cb8");
        String status = "nGUKTRuIohlnsC1tuxeA4PcCghikyj1gusgpdne0ruvIbaIINq";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idToken", idToken);
        mapFieldsRequestMock.put("idUserUUID", idUserUUID);
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
            idUserUUID,
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
        Long idMock = 1560L;
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
        Long idMock = 11526L;
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
        Long idMock = 18605L;
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(
                SessionStateModelBuilder.newSessionStateModelTestBuilder()
                        .id(idMock)
                        .idToken(UUID.fromString("8829f146-0a8b-4067-b66b-eb8fd570e41f"))
                        .idUserUUID(UUID.fromString("53fecc73-9516-4303-9ff4-cd4eec04496f"))

                        .status("X")
                        .now()
        );
        SessionState sessionstateToSaveMock = sessionstateModelMock.orElse(null);
        SessionState sessionstateSavedMck = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                        .id(62284L)
                        .idToken(UUID.fromString("1c639e91-db67-475f-9b52-de0125a7637b"))
                        .idUserUUID(UUID.fromString("774ca4ba-c089-4c47-b12c-30070239e840"))

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
                .id(56454L)
                .idToken(UUID.fromString("364689aa-a00e-4dc8-b386-984f3ac10a3a"))
                .idUserUUID(UUID.fromString("f22f1794-6382-4b9c-ac9d-e94601a9d28b"))

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
                .id(2572L)
                .idToken(UUID.fromString("ed8526be-3916-4564-905f-23d0d36db6a7"))
                .idUserUUID(UUID.fromString("99f6ed7a-9ca7-453f-a463-4526cdb3ad1b"))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SessionState sessionstateMock = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(sessionstateDTOMock.getId())
                .idToken(sessionstateDTOMock.getIdToken())
                .idUserUUID(sessionstateDTOMock.getIdUserUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SessionState sessionstateSavedMock = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(sessionstateDTOMock.getId())
                .idToken(sessionstateDTOMock.getIdToken())
                .idUserUUID(sessionstateDTOMock.getIdUserUUID())

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
                .idToken(UUID.fromString("1e7e504b-df27-4776-8b79-59019cb48d1f"))
                .idUserUUID(UUID.fromString("b3025c66-0188-47b1-8b53-3e39ab87939c"))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SessionState sessionstateModelMock = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(null)
                .idToken(sessionstateDTOMock.getIdToken())
                .idUserUUID(sessionstateDTOMock.getIdUserUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SessionState sessionstateSavedMock = SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .id(501L)
                .idToken(sessionstateDTOMock.getIdToken())
                .idUserUUID(sessionstateDTOMock.getIdUserUUID())

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
        mapSessionStateDTOMock.put(SessionStateConstantes.IDTOKEN,UUID.fromString("465ff580-aa8a-4f22-ae47-e4b6c9d00b66"));
        mapSessionStateDTOMock.put(SessionStateConstantes.IDUSERUUID,UUID.fromString("5219dd11-1f78-4943-9948-03a635ad06d2"));
        mapSessionStateDTOMock.put(SessionStateConstantes.STATUS,"Bm72deMW10AN1edjS1Ob0d4PG2ADXPqkVWIRiRL1Qf055XRHzt");


        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(
                SessionStateModelBuilder.newSessionStateModelTestBuilder()
                        .id(6206L)
                        .idToken(UUID.fromString("89455342-216a-4de3-9ade-5044287575a0"))
                        .idUserUUID(UUID.fromString("3e9fd024-7a9b-4f11-bcac-f43cae046beb"))
                        .status("WWIy8gRJqCw3uWoYAYzGy6R5lC0sHce9LjWlElbb8xhtgbPLG8")

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
        mapSessionStateDTOMock.put(SessionStateConstantes.IDTOKEN,UUID.fromString("2dc384b9-23d2-43ec-b178-5fdc1e961b08"));
        mapSessionStateDTOMock.put(SessionStateConstantes.IDUSERUUID,UUID.fromString("720bf05b-3236-4f45-8e07-db5406c2a4d9"));
        mapSessionStateDTOMock.put(SessionStateConstantes.STATUS,"9b4sYfJI0nPerRUuKrvWzySc2w1l8NoixfCr0WHfDpM5BDO0Vz");


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

        Mockito.when(sessionstateRepositoryMock.findAllByIdAndStatus(8566L, "A")).thenReturn(sessionstates);

        // action
        List<SessionStateDTO> result = sessionstateService.findAllSessionStateByIdAndStatus(8566L, "A");

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

        Mockito.when(sessionstateRepositoryMock.findAllByIdTokenAndStatus(UUID.fromString("8cbd52e6-026f-4611-b430-b643926749f0"), "A")).thenReturn(sessionstates);

        // action
        List<SessionStateDTO> result = sessionstateService.findAllSessionStateByIdTokenAndStatus(UUID.fromString("8cbd52e6-026f-4611-b430-b643926749f0"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSessionStateListWhenFindAllSessionStateByIdUserUUIDAndStatus() {
        // scenario
        List<SessionState> sessionstates = Arrays.asList(
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now(),
            SessionStateModelBuilder.newSessionStateModelTestBuilder().now()
        );

        Mockito.when(sessionstateRepositoryMock.findAllByIdUserUUIDAndStatus(UUID.fromString("06508b44-504b-4618-8cf2-ff422e578ce5"), "A")).thenReturn(sessionstates);

        // action
        List<SessionStateDTO> result = sessionstateService.findAllSessionStateByIdUserUUIDAndStatus(UUID.fromString("06508b44-504b-4618-8cf2-ff422e578ce5"), "A");

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
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdAndStatus(40012L, "A")).thenReturn(1L);
        Mockito.when(sessionstateRepositoryMock.findById(1L)).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findSessionStateByIdAndStatus(40012L, "A");

        // validate
        Assertions.assertInstanceOf(SessionStateDTO.class,result);
    }
    @Test
    public void shouldReturnSessionStateNotFoundExceptionWhenNonExistenceSessionStateIdAndStatus() {
        // scenario
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdAndStatus(40012L, "A")).thenReturn(0L);
        Mockito.when(sessionstateRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.findSessionStateByIdAndStatus(40012L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentSessionStateDTOWhenFindSessionStateByIdTokenAndStatus() {
        // scenario
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdTokenAndStatus(UUID.fromString("47d53549-0da4-43e1-b517-66cb3b883a0c"), "A")).thenReturn(1L);
        Mockito.when(sessionstateRepositoryMock.findById(1L)).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findSessionStateByIdTokenAndStatus(UUID.fromString("47d53549-0da4-43e1-b517-66cb3b883a0c"), "A");

        // validate
        Assertions.assertInstanceOf(SessionStateDTO.class,result);
    }
    @Test
    public void shouldReturnSessionStateNotFoundExceptionWhenNonExistenceSessionStateIdTokenAndStatus() {
        // scenario
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdTokenAndStatus(UUID.fromString("47d53549-0da4-43e1-b517-66cb3b883a0c"), "A")).thenReturn(0L);
        Mockito.when(sessionstateRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.findSessionStateByIdTokenAndStatus(UUID.fromString("47d53549-0da4-43e1-b517-66cb3b883a0c"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_IDTOKEN));
    }
    @Test
    public void shouldReturnExistentSessionStateDTOWhenFindSessionStateByIdUserUUIDAndStatus() {
        // scenario
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder().now());
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(UUID.fromString("2936747a-4b27-49c3-99be-c8b1e6981fb3"), "A")).thenReturn(1L);
        Mockito.when(sessionstateRepositoryMock.findById(1L)).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findSessionStateByIdUserUUIDAndStatus(UUID.fromString("2936747a-4b27-49c3-99be-c8b1e6981fb3"), "A");

        // validate
        Assertions.assertInstanceOf(SessionStateDTO.class,result);
    }
    @Test
    public void shouldReturnSessionStateNotFoundExceptionWhenNonExistenceSessionStateIdUserUUIDAndStatus() {
        // scenario
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(UUID.fromString("2936747a-4b27-49c3-99be-c8b1e6981fb3"), "A")).thenReturn(0L);
        Mockito.when(sessionstateRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.findSessionStateByIdUserUUIDAndStatus(UUID.fromString("2936747a-4b27-49c3-99be-c8b1e6981fb3"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_IDUSERUUID));
    }

    @Test
    public void shouldReturnSessionStateDTOWhenUpdateExistingIdTokenById() {
        // scenario
        UUID idTokenUpdateMock = UUID.fromString("32354900-f071-45d9-ad0c-68b6f283d79a");
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
    public void shouldReturnSessionStateDTOWhenUpdateExistingIdUserUUIDById() {
        // scenario
        UUID idUserUUIDUpdateMock = UUID.fromString("0b56148d-a8eb-41f7-91a9-136f2479345d");
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(sessionstateRepositoryMock.findById(420L)).thenReturn(sessionstateModelMock);
        Mockito.doNothing().when(sessionstateRepositoryMock).updateIdUserUUIDById(420L, idUserUUIDUpdateMock);

        // action
        sessionstateService.updateIdUserUUIDById(420L, idUserUUIDUpdateMock);

        // validate
        Mockito.verify(sessionstateRepositoryMock,Mockito.times(1)).updateIdUserUUIDById(420L, idUserUUIDUpdateMock);
    }



    @Test
    public void showReturnExistingSessionStateDTOWhenFindSessionStateByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 40808L;
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
        Long idMock = 40808L;
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
        UUID idTokenMock = UUID.fromString("d3ac5609-5f47-43c4-8f51-37425af81d6b");
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
        UUID idTokenMock = UUID.fromString("d3ac5609-5f47-43c4-8f51-37425af81d6b");
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

    @Test
    public void showReturnExistingSessionStateDTOWhenFindSessionStateByIdUserUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID idUserUUIDMock = UUID.fromString("2d37b41c-5ad3-4674-aa90-29caf27e6d05");
        Long maxIdMock = 1972L;
        Optional<SessionState> sessionstateModelMock = Optional.ofNullable(SessionStateModelBuilder.newSessionStateModelTestBuilder()
                .idUserUUID(idUserUUIDMock)
                .now()
        );
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(idUserUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(sessionstateRepositoryMock.findById(maxIdMock)).thenReturn(sessionstateModelMock);

        // action
        SessionStateDTO result = sessionstateService.findSessionStateByIdUserUUIDAndStatus(idUserUUIDMock);

        // validate
        Assertions.assertEquals(idUserUUIDMock, result.getIdUserUUID());

    }
    @Test
    public void showReturnSessionStateNotFoundExceptionWhenNonExistenceFindSessionStateByIdUserUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID idUserUUIDMock = UUID.fromString("2d37b41c-5ad3-4674-aa90-29caf27e6d05");
        Long noMaxIdMock = 0L;
        Optional<SessionState> sessionstateModelMock = Optional.empty();
        Mockito.when(sessionstateRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(idUserUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(sessionstateRepositoryMock.findById(noMaxIdMock)).thenReturn(sessionstateModelMock);

        // action
        SessionStateNotFoundException exception = Assertions.assertThrows(SessionStateNotFoundException.class,
                ()->sessionstateService.findSessionStateByIdUserUUIDAndStatus(idUserUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SESSIONSTATE_NOTFOUND_WITH_IDUSERUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

