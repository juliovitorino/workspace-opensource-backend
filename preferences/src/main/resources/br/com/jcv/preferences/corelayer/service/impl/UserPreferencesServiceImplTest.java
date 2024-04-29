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

package br.com.jcv.preferences.corelayer.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.preferences.corelayer.builder.UserPreferencesDTOBuilder;
import br.com.jcv.preferences.corelayer.builder.UserPreferencesModelBuilder;
import br.com.jcv.preferences.corelayer.dto.UserPreferencesDTO;
import br.com.jcv.preferences.corelayer.exception.UserPreferencesNotFoundException;
import br.com.jcv.preferences.corelayer.model.UserPreferences;
import br.com.jcv.preferences.corelayer.repository.UserPreferencesRepository;
import br.com.jcv.preferences.corelayer.service.UserPreferencesService;
import br.com.jcv.preferences.corelayer.constantes.UserPreferencesConstantes;
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
public class UserPreferencesServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String USERPREFERENCES_NOTFOUND_WITH_ID = "UserPreferences não encontrada com id = ";
    public static final String USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP = "UserPreferences não encontrada com uuidExternalApp = ";
    public static final String USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALUSER = "UserPreferences não encontrada com uuidExternalUser = ";
    public static final String USERPREFERENCES_NOTFOUND_WITH_KEY = "UserPreferences não encontrada com key = ";
    public static final String USERPREFERENCES_NOTFOUND_WITH_PREFERENCE = "UserPreferences não encontrada com preference = ";
    public static final String USERPREFERENCES_NOTFOUND_WITH_STATUS = "UserPreferences não encontrada com status = ";
    public static final String USERPREFERENCES_NOTFOUND_WITH_DATECREATED = "UserPreferences não encontrada com dateCreated = ";
    public static final String USERPREFERENCES_NOTFOUND_WITH_DATEUPDATED = "UserPreferences não encontrada com dateUpdated = ";


    @Mock
    private UserPreferencesRepository userpreferencesRepositoryMock;

    @InjectMocks
    private UserPreferencesService userpreferencesService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        userpreferencesService = new UserPreferencesServiceImpl();
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
    public void shouldReturnListOfUserPreferencesWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 23428L;
        UUID uuidExternalApp = UUID.fromString("1834892f-1437-49fa-b5fd-7f5bc7dd5e14");
        UUID uuidExternalUser = UUID.fromString("58871e2a-8446-405c-8225-9124360b511e");
        String key = "xVy2uBGvi4CMr3cgHn62RUPIopwJFnaC0TPPA7WMJp8IerN0Mx";
        String preference = "0r8RiH973ssIoqGfuGW8XNxMuEpdR95n4zqqTU26lN4zvFob0I";
        String status = "uFkdnOaqqbN0XBEhic0wbj64S99HdQkGsGW2d8iypRIb4KVAs6";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("uuidExternalApp", uuidExternalApp);
        mapFieldsRequestMock.put("uuidExternalUser", uuidExternalUser);
        mapFieldsRequestMock.put("key", key);
        mapFieldsRequestMock.put("preference", preference);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<UserPreferences> userpreferencessFromRepository = new ArrayList<>();
        userpreferencessFromRepository.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        userpreferencessFromRepository.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        userpreferencessFromRepository.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        userpreferencessFromRepository.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());

        Mockito.when(userpreferencesRepositoryMock.findUserPreferencesByFilter(
            id,
            uuidExternalApp,
            uuidExternalUser,
            key,
            preference,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(userpreferencessFromRepository);

        // action
        List<UserPreferencesDTO> result = userpreferencesService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithUserPreferencesListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 20473L;
        UUID uuidExternalApp = UUID.fromString("c01e97fe-f1a2-4a28-ad5b-ec0a75600a36");
        UUID uuidExternalUser = UUID.fromString("bc73dfca-20a1-4259-9fe7-3ef10091dd6e");
        String key = "jX0fxz5WAc6mOEkihVSp2GkO7ch4SAOpizxxLOTBPbGiRiygFy";
        String preference = "ihc49zbVNb1ihREu2m4hSoCAhy7AWKssv2lanyROryojaHYLiN";
        String status = "Xlr970OP0fCMt8aSeQKf7uP08vxXxTi1ro2yRWPlPON6hCUMh4";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("uuidExternalApp", uuidExternalApp);
        mapFieldsRequestMock.put("uuidExternalUser", uuidExternalUser);
        mapFieldsRequestMock.put("key", key);
        mapFieldsRequestMock.put("preference", preference);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<UserPreferences> userpreferencessFromRepository = new ArrayList<>();
        userpreferencessFromRepository.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        userpreferencessFromRepository.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        userpreferencessFromRepository.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        userpreferencessFromRepository.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());

        List<UserPreferencesDTO> userpreferencessFiltered = userpreferencessFromRepository
                .stream()
                .map(m->userpreferencesService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageUserPreferencesItems", userpreferencessFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<UserPreferences> pagedResponse =
                new PageImpl<>(userpreferencessFromRepository,
                        pageableMock,
                        userpreferencessFromRepository.size());

        Mockito.when(userpreferencesRepositoryMock.findUserPreferencesByFilter(pageableMock,
            id,
            uuidExternalApp,
            uuidExternalUser,
            key,
            preference,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = userpreferencesService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<UserPreferencesDTO> userpreferencessResult = (List<UserPreferencesDTO>) result.get("pageUserPreferencesItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, userpreferencessResult.size());
    }


    @Test
    public void showReturnListOfUserPreferencesWhenAskedForFindAllByStatus() {
        // scenario
        List<UserPreferences> listOfUserPreferencesModelMock = new ArrayList<>();
        listOfUserPreferencesModelMock.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        listOfUserPreferencesModelMock.add(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());

        Mockito.when(userpreferencesRepositoryMock.findAllByStatus("A")).thenReturn(listOfUserPreferencesModelMock);

        // action
        List<UserPreferencesDTO> listOfUserPreferencess = userpreferencesService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfUserPreferencess.isEmpty());
        Assertions.assertEquals(2, listOfUserPreferencess.size());
    }
    @Test
    public void shouldReturnUserPreferencesNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 36230L;
        Optional<UserPreferences> userpreferencesNonExistentMock = Optional.empty();
        Mockito.when(userpreferencesRepositoryMock.findById(idMock)).thenReturn(userpreferencesNonExistentMock);

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowUserPreferencesNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 64735L;
        Mockito.when(userpreferencesRepositoryMock.findById(idMock))
                .thenThrow(new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                USERPREFERENCES_NOTFOUND_WITH_ID ));

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnUserPreferencesDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 45775L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(
                UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                        .id(idMock)
                        .uuidExternalApp(UUID.fromString("7455ac60-741a-4c3f-b801-552cff97b8dd"))
                        .uuidExternalUser(UUID.fromString("8b18d1b8-c312-4c1c-bb59-219c1b95f4c4"))
                        .key("NGkEescyzXKf6y9mc03aA2YAlooVUquo7e81hUmD84EgymvKnc")
                        .preference("iyUk0NFlt01vMcVImqFyqPT7aTUjf5Se5K285mAiPpoyAf2d17")

                        .status("X")
                        .now()
        );
        UserPreferences userpreferencesToSaveMock = userpreferencesModelMock.orElse(null);
        UserPreferences userpreferencesSavedMck = UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                        .id(38842L)
                        .uuidExternalApp(UUID.fromString("5d52ea41-ec7f-4e44-96c3-aed054fcb019"))
                        .uuidExternalUser(UUID.fromString("e1311b3b-3ac1-4f23-840b-75886106a712"))
                        .key("q5yA0obe6AQhGbKgUYEWF0d1nWWqpvlBAoCiUt8QoT8QnD9MFG")
                        .preference("0LBNsfzbArd2oYG2in4q5RORwqP8rgNxIjheApJYoWLwsmFp0l")

                        .status("A")
                        .now();
        Mockito.when(userpreferencesRepositoryMock.findById(idMock)).thenReturn(userpreferencesModelMock);
        Mockito.when(userpreferencesRepositoryMock.save(userpreferencesToSaveMock)).thenReturn(userpreferencesSavedMck);

        // action
        UserPreferencesDTO result = userpreferencesService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchUserPreferencesByAnyNonExistenceIdAndReturnUserPreferencesNotFoundException() {
        // scenario
        Mockito.when(userpreferencesRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()-> userpreferencesService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchUserPreferencesByIdAndReturnDTO() {
        // scenario
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .id(58617L)
                .uuidExternalApp(UUID.fromString("06a383c4-981a-44cc-8528-db33885a3ab0"))
                .uuidExternalUser(UUID.fromString("7bea1e73-f032-4e86-bcfb-68ffa693f550"))
                .key("RAYUbQLL1Id10YTC8Y1SjvUSHj0EPLgzN3hybFwfWXCJAUSAPm")
                .preference("vPWuni4cA2PXUCRNXHdLRuyKUlP0j1QHaoO6nVq78lRXTuVGWM")

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(userpreferencesRepositoryMock.findById(Mockito.anyLong())).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findById(1L);

        // validate
        Assertions.assertInstanceOf(UserPreferencesDTO.class,result);
    }
    @Test
    public void shouldDeleteUserPreferencesByIdWithSucess() {
        // scenario
        Optional<UserPreferences> userpreferences = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().id(1L).now());
        Mockito.when(userpreferencesRepositoryMock.findById(Mockito.anyLong())).thenReturn(userpreferences);

        // action
        userpreferencesService.delete(1L);

        // validate
        Mockito.verify(userpreferencesRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceUserPreferencesShouldReturnUserPreferencesNotFoundException() {
        // scenario
        Mockito.when(userpreferencesRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(
                UserPreferencesNotFoundException.class, () -> userpreferencesService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingUserPreferencesWithSucess() {
        // scenario
        UserPreferencesDTO userpreferencesDTOMock = UserPreferencesDTOBuilder.newUserPreferencesDTOTestBuilder()
                .id(81130L)
                .uuidExternalApp(UUID.fromString("5dd29c0b-b6d5-4384-9a15-c6e38f776f25"))
                .uuidExternalUser(UUID.fromString("72c3558a-f4fa-480a-9139-7eec20a1c2cd"))
                .key("sY4U409P8Y62nmNsEskba92IWOM9XDD2P6JNBL05rbSCRUyTjA")
                .preference("IFmuhcD7uuMsMSsinKicD3JtOgYTdmT5WH693nmrHU9a90NxqX")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        UserPreferences userpreferencesMock = UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .id(userpreferencesDTOMock.getId())
                .uuidExternalApp(userpreferencesDTOMock.getUuidExternalApp())
                .uuidExternalUser(userpreferencesDTOMock.getUuidExternalUser())
                .key(userpreferencesDTOMock.getKey())
                .preference(userpreferencesDTOMock.getPreference())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        UserPreferences userpreferencesSavedMock = UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .id(userpreferencesDTOMock.getId())
                .uuidExternalApp(userpreferencesDTOMock.getUuidExternalApp())
                .uuidExternalUser(userpreferencesDTOMock.getUuidExternalUser())
                .key(userpreferencesDTOMock.getKey())
                .preference(userpreferencesDTOMock.getPreference())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(userpreferencesRepositoryMock.save(userpreferencesMock)).thenReturn(userpreferencesSavedMock);

        // action
        UserPreferencesDTO userpreferencesSaved = userpreferencesService.salvar(userpreferencesDTOMock);

        // validate
        Assertions.assertInstanceOf(UserPreferencesDTO.class, userpreferencesSaved);
        Assertions.assertNotNull(userpreferencesSaved.getId());
    }

    @Test
    public void ShouldSaveNewUserPreferencesWithSucess() {
        // scenario
        UserPreferencesDTO userpreferencesDTOMock = UserPreferencesDTOBuilder.newUserPreferencesDTOTestBuilder()
                .id(null)
                .uuidExternalApp(UUID.fromString("a6b2a22d-4728-4f29-a289-949e9cbe6655"))
                .uuidExternalUser(UUID.fromString("08babfdc-1335-4da3-b0c8-6e7fd0a926e4"))
                .key("45lsQY5cwAEh6gCCco05SCctj2M4pe7GgcGh5X2V9aBvqz3qvp")
                .preference("0YFXsqf4sWtrKEJVMpEqRb0WDAiN2n0QFwzHEmE9xf2wGGvjYH")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        UserPreferences userpreferencesModelMock = UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .id(null)
                .uuidExternalApp(userpreferencesDTOMock.getUuidExternalApp())
                .uuidExternalUser(userpreferencesDTOMock.getUuidExternalUser())
                .key(userpreferencesDTOMock.getKey())
                .preference(userpreferencesDTOMock.getPreference())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        UserPreferences userpreferencesSavedMock = UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .id(501L)
                .uuidExternalApp(userpreferencesDTOMock.getUuidExternalApp())
                .uuidExternalUser(userpreferencesDTOMock.getUuidExternalUser())
                .key(userpreferencesDTOMock.getKey())
                .preference(userpreferencesDTOMock.getPreference())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(userpreferencesRepositoryMock.save(userpreferencesModelMock)).thenReturn(userpreferencesSavedMock);

        // action
        UserPreferencesDTO userpreferencesSaved = userpreferencesService.salvar(userpreferencesDTOMock);

        // validate
        Assertions.assertInstanceOf(UserPreferencesDTO.class, userpreferencesSaved);
        Assertions.assertNotNull(userpreferencesSaved.getId());
        Assertions.assertEquals("P",userpreferencesSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapUserPreferencesDTOMock = new HashMap<>();
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.UUIDEXTERNALAPP,UUID.fromString("22ff9f34-6d22-4467-9def-84e12ae8d2a7"));
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.UUIDEXTERNALUSER,UUID.fromString("56786c15-e2ee-495b-9246-96bdd29137cb"));
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.KEY,"h9xhGhpBdKeqkwxAqOyk8bxuofiVTvNyjVhMbx0jesMoUH46VV");
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.PREFERENCE,"JXR0xFYjXr1z6qBOaaRCgOtxIpi1KIxDunzAup2067Of35emqt");
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.STATUS,"UJxv200BDwp96C1jSrv687rr84Rw128hoNlRt9H2LmoD34dEld");


        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(
                UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                        .id(5445L)
                        .uuidExternalApp(UUID.fromString("bc97f144-a505-4d54-9f68-ac6b3faf8966"))
                        .uuidExternalUser(UUID.fromString("228035b0-ad46-47c0-a392-e19ce56f9050"))
                        .key("IKI00HXVR6w0VLIzELVn5oL6yoLaecx7YHHyq0aolHvtSlDWkn")
                        .preference("jpQbHd0GzCywtg0jcQJAjDuD3LGSLlvWAYvFkqjxEVUXx9GTlc")
                        .status("EJSvQtnjber4S1WH4c7vLU2quNVpUdxYPRMhScrFEv0NVgIhyu")

                        .now()
        );

        Mockito.when(userpreferencesRepositoryMock.findById(1L)).thenReturn(userpreferencesModelMock);

        // action
        boolean executed = userpreferencesService.partialUpdate(1L, mapUserPreferencesDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnUserPreferencesNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapUserPreferencesDTOMock = new HashMap<>();
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.UUIDEXTERNALAPP,UUID.fromString("f90618d2-4104-4b67-b7fd-deb26c5b89f9"));
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.UUIDEXTERNALUSER,UUID.fromString("6ee58ddb-c521-453f-beb4-244dd71ed0cf"));
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.KEY,"Jwlm3GhuASuLHftOs3JTChTBqS8YX7RFmp9TamYBSsRnWoU1br");
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.PREFERENCE,"Jq7rMihVlJ6pYk7wG7aEJNX9esjSXvuq44fbiwf0TX15BlPIGF");
        mapUserPreferencesDTOMock.put(UserPreferencesConstantes.STATUS,"l1WAg25Oi0YgDass9oR4F0tFdczUa19567fAVTAlrD04lGmR5U");


        Mockito.when(userpreferencesRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.partialUpdate(1L, mapUserPreferencesDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("UserPreferences não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnUserPreferencesListWhenFindAllUserPreferencesByIdAndStatus() {
        // scenario
        List<UserPreferences> userpreferencess = Arrays.asList(
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now()
        );

        Mockito.when(userpreferencesRepositoryMock.findAllByIdAndStatus(34451L, "A")).thenReturn(userpreferencess);

        // action
        List<UserPreferencesDTO> result = userpreferencesService.findAllUserPreferencesByIdAndStatus(34451L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserPreferencesListWhenFindAllUserPreferencesByUuidExternalAppAndStatus() {
        // scenario
        List<UserPreferences> userpreferencess = Arrays.asList(
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now()
        );

        Mockito.when(userpreferencesRepositoryMock.findAllByUuidExternalAppAndStatus(UUID.fromString("eadc863e-c3b2-4cfb-84f1-1446607dbb6a"), "A")).thenReturn(userpreferencess);

        // action
        List<UserPreferencesDTO> result = userpreferencesService.findAllUserPreferencesByUuidExternalAppAndStatus(UUID.fromString("eadc863e-c3b2-4cfb-84f1-1446607dbb6a"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserPreferencesListWhenFindAllUserPreferencesByUuidExternalUserAndStatus() {
        // scenario
        List<UserPreferences> userpreferencess = Arrays.asList(
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now()
        );

        Mockito.when(userpreferencesRepositoryMock.findAllByUuidExternalUserAndStatus(UUID.fromString("102bd089-7aca-40f0-9bae-ffa4ddc70936"), "A")).thenReturn(userpreferencess);

        // action
        List<UserPreferencesDTO> result = userpreferencesService.findAllUserPreferencesByUuidExternalUserAndStatus(UUID.fromString("102bd089-7aca-40f0-9bae-ffa4ddc70936"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserPreferencesListWhenFindAllUserPreferencesByKeyAndStatus() {
        // scenario
        List<UserPreferences> userpreferencess = Arrays.asList(
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now()
        );

        Mockito.when(userpreferencesRepositoryMock.findAllByKeyAndStatus("tJ0PzB6H2UIcO5ngrx6SSn9a6WjrfOboCuMcGJemidldtLOwPy", "A")).thenReturn(userpreferencess);

        // action
        List<UserPreferencesDTO> result = userpreferencesService.findAllUserPreferencesByKeyAndStatus("tJ0PzB6H2UIcO5ngrx6SSn9a6WjrfOboCuMcGJemidldtLOwPy", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserPreferencesListWhenFindAllUserPreferencesByPreferenceAndStatus() {
        // scenario
        List<UserPreferences> userpreferencess = Arrays.asList(
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now()
        );

        Mockito.when(userpreferencesRepositoryMock.findAllByPreferenceAndStatus("BaGsDpMCIeTCQYNEe73oF0xomwz1bhpnveC2s07aiU2rL5hj1P", "A")).thenReturn(userpreferencess);

        // action
        List<UserPreferencesDTO> result = userpreferencesService.findAllUserPreferencesByPreferenceAndStatus("BaGsDpMCIeTCQYNEe73oF0xomwz1bhpnveC2s07aiU2rL5hj1P", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserPreferencesListWhenFindAllUserPreferencesByDateCreatedAndStatus() {
        // scenario
        List<UserPreferences> userpreferencess = Arrays.asList(
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now()
        );

        Mockito.when(userpreferencesRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(userpreferencess);

        // action
        List<UserPreferencesDTO> result = userpreferencesService.findAllUserPreferencesByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserPreferencesListWhenFindAllUserPreferencesByDateUpdatedAndStatus() {
        // scenario
        List<UserPreferences> userpreferencess = Arrays.asList(
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now(),
            UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now()
        );

        Mockito.when(userpreferencesRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(userpreferencess);

        // action
        List<UserPreferencesDTO> result = userpreferencesService.findAllUserPreferencesByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentUserPreferencesDTOWhenFindUserPreferencesByIdAndStatus() {
        // scenario
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByIdAndStatus(13061L, "A")).thenReturn(1L);
        Mockito.when(userpreferencesRepositoryMock.findById(1L)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByIdAndStatus(13061L, "A");

        // validate
        Assertions.assertInstanceOf(UserPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnUserPreferencesNotFoundExceptionWhenNonExistenceUserPreferencesIdAndStatus() {
        // scenario
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByIdAndStatus(13061L, "A")).thenReturn(0L);
        Mockito.when(userpreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByIdAndStatus(13061L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentUserPreferencesDTOWhenFindUserPreferencesByUuidExternalAppAndStatus() {
        // scenario
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("e26d0850-03d5-456e-843e-26a390575d9f"), "A")).thenReturn(1L);
        Mockito.when(userpreferencesRepositoryMock.findById(1L)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByUuidExternalAppAndStatus(UUID.fromString("e26d0850-03d5-456e-843e-26a390575d9f"), "A");

        // validate
        Assertions.assertInstanceOf(UserPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnUserPreferencesNotFoundExceptionWhenNonExistenceUserPreferencesUuidExternalAppAndStatus() {
        // scenario
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("e26d0850-03d5-456e-843e-26a390575d9f"), "A")).thenReturn(0L);
        Mockito.when(userpreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByUuidExternalAppAndStatus(UUID.fromString("e26d0850-03d5-456e-843e-26a390575d9f"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP));
    }
    @Test
    public void shouldReturnExistentUserPreferencesDTOWhenFindUserPreferencesByUuidExternalUserAndStatus() {
        // scenario
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(UUID.fromString("c13e79fc-bbc9-43f0-9a45-154fa0b2bfd2"), "A")).thenReturn(1L);
        Mockito.when(userpreferencesRepositoryMock.findById(1L)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByUuidExternalUserAndStatus(UUID.fromString("c13e79fc-bbc9-43f0-9a45-154fa0b2bfd2"), "A");

        // validate
        Assertions.assertInstanceOf(UserPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnUserPreferencesNotFoundExceptionWhenNonExistenceUserPreferencesUuidExternalUserAndStatus() {
        // scenario
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(UUID.fromString("c13e79fc-bbc9-43f0-9a45-154fa0b2bfd2"), "A")).thenReturn(0L);
        Mockito.when(userpreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByUuidExternalUserAndStatus(UUID.fromString("c13e79fc-bbc9-43f0-9a45-154fa0b2bfd2"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALUSER));
    }
    @Test
    public void shouldReturnExistentUserPreferencesDTOWhenFindUserPreferencesByKeyAndStatus() {
        // scenario
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByKeyAndStatus("acHq1zyMY8ylBbxnFbEksll8bpYHMcBbrHittlYTWEUH8sWaQ7", "A")).thenReturn(1L);
        Mockito.when(userpreferencesRepositoryMock.findById(1L)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByKeyAndStatus("acHq1zyMY8ylBbxnFbEksll8bpYHMcBbrHittlYTWEUH8sWaQ7", "A");

        // validate
        Assertions.assertInstanceOf(UserPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnUserPreferencesNotFoundExceptionWhenNonExistenceUserPreferencesKeyAndStatus() {
        // scenario
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByKeyAndStatus("acHq1zyMY8ylBbxnFbEksll8bpYHMcBbrHittlYTWEUH8sWaQ7", "A")).thenReturn(0L);
        Mockito.when(userpreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByKeyAndStatus("acHq1zyMY8ylBbxnFbEksll8bpYHMcBbrHittlYTWEUH8sWaQ7", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_KEY));
    }
    @Test
    public void shouldReturnExistentUserPreferencesDTOWhenFindUserPreferencesByPreferenceAndStatus() {
        // scenario
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder().now());
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByPreferenceAndStatus("144Rord0umhYrJuBiiYQqpmwsopKYcCWBqzjImuwxSKpq7mrpd", "A")).thenReturn(1L);
        Mockito.when(userpreferencesRepositoryMock.findById(1L)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByPreferenceAndStatus("144Rord0umhYrJuBiiYQqpmwsopKYcCWBqzjImuwxSKpq7mrpd", "A");

        // validate
        Assertions.assertInstanceOf(UserPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnUserPreferencesNotFoundExceptionWhenNonExistenceUserPreferencesPreferenceAndStatus() {
        // scenario
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByPreferenceAndStatus("144Rord0umhYrJuBiiYQqpmwsopKYcCWBqzjImuwxSKpq7mrpd", "A")).thenReturn(0L);
        Mockito.when(userpreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByPreferenceAndStatus("144Rord0umhYrJuBiiYQqpmwsopKYcCWBqzjImuwxSKpq7mrpd", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_PREFERENCE));
    }

    @Test
    public void shouldReturnUserPreferencesDTOWhenUpdateExistingUuidExternalAppById() {
        // scenario
        UUID uuidExternalAppUpdateMock = UUID.fromString("376e7043-59d2-44aa-a90f-d7c8953acf72");
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(userpreferencesRepositoryMock.findById(420L)).thenReturn(userpreferencesModelMock);
        Mockito.doNothing().when(userpreferencesRepositoryMock).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // action
        userpreferencesService.updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // validate
        Mockito.verify(userpreferencesRepositoryMock,Mockito.times(1)).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);
    }
    @Test
    public void shouldReturnUserPreferencesDTOWhenUpdateExistingUuidExternalUserById() {
        // scenario
        UUID uuidExternalUserUpdateMock = UUID.fromString("6bb046d0-ea29-4c39-afac-444d0db36ed0");
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(userpreferencesRepositoryMock.findById(420L)).thenReturn(userpreferencesModelMock);
        Mockito.doNothing().when(userpreferencesRepositoryMock).updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);

        // action
        userpreferencesService.updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);

        // validate
        Mockito.verify(userpreferencesRepositoryMock,Mockito.times(1)).updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);
    }
    @Test
    public void shouldReturnUserPreferencesDTOWhenUpdateExistingKeyById() {
        // scenario
        String keyUpdateMock = "QCMwEd0MIXA3F2dwFbI9WCXLLh9ShmtKdgAJeQd1iP2W13nyIL";
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(userpreferencesRepositoryMock.findById(420L)).thenReturn(userpreferencesModelMock);
        Mockito.doNothing().when(userpreferencesRepositoryMock).updateKeyById(420L, keyUpdateMock);

        // action
        userpreferencesService.updateKeyById(420L, keyUpdateMock);

        // validate
        Mockito.verify(userpreferencesRepositoryMock,Mockito.times(1)).updateKeyById(420L, keyUpdateMock);
    }
    @Test
    public void shouldReturnUserPreferencesDTOWhenUpdateExistingPreferenceById() {
        // scenario
        String preferenceUpdateMock = "fEKM8pqux5RVLEC0DfR97zMJ4fOoW3Bvh3K7w7Bzyw8Tq1VGtM";
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(userpreferencesRepositoryMock.findById(420L)).thenReturn(userpreferencesModelMock);
        Mockito.doNothing().when(userpreferencesRepositoryMock).updatePreferenceById(420L, preferenceUpdateMock);

        // action
        userpreferencesService.updatePreferenceById(420L, preferenceUpdateMock);

        // validate
        Mockito.verify(userpreferencesRepositoryMock,Mockito.times(1)).updatePreferenceById(420L, preferenceUpdateMock);
    }



    @Test
    public void showReturnExistingUserPreferencesDTOWhenFindUserPreferencesByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 52810L;
        Long maxIdMock = 1972L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(maxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnUserPreferencesNotFoundExceptionWhenNonExistenceFindUserPreferencesByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 52810L;
        Long noMaxIdMock = 0L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.empty();
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUserPreferencesDTOWhenFindUserPreferencesByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("b2b82b2f-35fc-413d-8104-8142afa2a808");
        Long maxIdMock = 1972L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .uuidExternalApp(uuidExternalAppMock)
                .now()
        );
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(maxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(maxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByUuidExternalAppAndStatus(uuidExternalAppMock);

        // validate
        Assertions.assertEquals(uuidExternalAppMock, result.getUuidExternalApp());

    }
    @Test
    public void showReturnUserPreferencesNotFoundExceptionWhenNonExistenceFindUserPreferencesByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("b2b82b2f-35fc-413d-8104-8142afa2a808");
        Long noMaxIdMock = 0L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.empty();
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByUuidExternalAppAndStatus(uuidExternalAppMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUserPreferencesDTOWhenFindUserPreferencesByUuidExternalUserAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalUserMock = UUID.fromString("c1a2ca7d-f4fe-476f-b6c2-4e3ec8c37721");
        Long maxIdMock = 1972L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .uuidExternalUser(uuidExternalUserMock)
                .now()
        );
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUserMock, "A")).thenReturn(maxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(maxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByUuidExternalUserAndStatus(uuidExternalUserMock);

        // validate
        Assertions.assertEquals(uuidExternalUserMock, result.getUuidExternalUser());

    }
    @Test
    public void showReturnUserPreferencesNotFoundExceptionWhenNonExistenceFindUserPreferencesByUuidExternalUserAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalUserMock = UUID.fromString("c1a2ca7d-f4fe-476f-b6c2-4e3ec8c37721");
        Long noMaxIdMock = 0L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.empty();
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUserMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByUuidExternalUserAndStatus(uuidExternalUserMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALUSER));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUserPreferencesDTOWhenFindUserPreferencesByKeyAndStatusActiveAnonimous() {
        // scenario
        String keyMock = "Wkr0pSXV8SfT5UCHVJBMEGSQQHzHAGEfYyRQuBJDc9DWfFqWwO";
        Long maxIdMock = 1972L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .key(keyMock)
                .now()
        );
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByKeyAndStatus(keyMock, "A")).thenReturn(maxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(maxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByKeyAndStatus(keyMock);

        // validate
        Assertions.assertEquals(keyMock, result.getKey());

    }
    @Test
    public void showReturnUserPreferencesNotFoundExceptionWhenNonExistenceFindUserPreferencesByKeyAndStatusActiveAnonimous() {
        // scenario
        String keyMock = "Wkr0pSXV8SfT5UCHVJBMEGSQQHzHAGEfYyRQuBJDc9DWfFqWwO";
        Long noMaxIdMock = 0L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.empty();
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByKeyAndStatus(keyMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByKeyAndStatus(keyMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_KEY));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUserPreferencesDTOWhenFindUserPreferencesByPreferenceAndStatusActiveAnonimous() {
        // scenario
        String preferenceMock = "rQhwLTq3eaOUUQ3iD6fx27s7gbwP3RcF8xEsC8kpdPdwxh2B6a";
        Long maxIdMock = 1972L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.ofNullable(UserPreferencesModelBuilder.newUserPreferencesModelTestBuilder()
                .preference(preferenceMock)
                .now()
        );
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByPreferenceAndStatus(preferenceMock, "A")).thenReturn(maxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(maxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesDTO result = userpreferencesService.findUserPreferencesByPreferenceAndStatus(preferenceMock);

        // validate
        Assertions.assertEquals(preferenceMock, result.getPreference());

    }
    @Test
    public void showReturnUserPreferencesNotFoundExceptionWhenNonExistenceFindUserPreferencesByPreferenceAndStatusActiveAnonimous() {
        // scenario
        String preferenceMock = "rQhwLTq3eaOUUQ3iD6fx27s7gbwP3RcF8xEsC8kpdPdwxh2B6a";
        Long noMaxIdMock = 0L;
        Optional<UserPreferences> userpreferencesModelMock = Optional.empty();
        Mockito.when(userpreferencesRepositoryMock.loadMaxIdByPreferenceAndStatus(preferenceMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(userpreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(userpreferencesModelMock);

        // action
        UserPreferencesNotFoundException exception = Assertions.assertThrows(UserPreferencesNotFoundException.class,
                ()->userpreferencesService.findUserPreferencesByPreferenceAndStatus(preferenceMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERPREFERENCES_NOTFOUND_WITH_PREFERENCE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

