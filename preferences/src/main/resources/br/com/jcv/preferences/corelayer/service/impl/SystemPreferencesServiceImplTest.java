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
import br.com.jcv.preferences.corelayer.builder.SystemPreferencesDTOBuilder;
import br.com.jcv.preferences.corelayer.builder.SystemPreferencesModelBuilder;
import br.com.jcv.preferences.corelayer.dto.SystemPreferencesDTO;
import br.com.jcv.preferences.corelayer.exception.SystemPreferencesNotFoundException;
import br.com.jcv.preferences.corelayer.model.SystemPreferences;
import br.com.jcv.preferences.corelayer.repository.SystemPreferencesRepository;
import br.com.jcv.preferences.corelayer.service.SystemPreferencesService;
import br.com.jcv.preferences.corelayer.constantes.SystemPreferencesConstantes;
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
public class SystemPreferencesServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String SYSTEMPREFERENCES_NOTFOUND_WITH_ID = "SystemPreferences não encontrada com id = ";
    public static final String SYSTEMPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP = "SystemPreferences não encontrada com uuidExternalApp = ";
    public static final String SYSTEMPREFERENCES_NOTFOUND_WITH_KEY = "SystemPreferences não encontrada com key = ";
    public static final String SYSTEMPREFERENCES_NOTFOUND_WITH_PREFERENCE = "SystemPreferences não encontrada com preference = ";
    public static final String SYSTEMPREFERENCES_NOTFOUND_WITH_STATUS = "SystemPreferences não encontrada com status = ";
    public static final String SYSTEMPREFERENCES_NOTFOUND_WITH_DATECREATED = "SystemPreferences não encontrada com dateCreated = ";
    public static final String SYSTEMPREFERENCES_NOTFOUND_WITH_DATEUPDATED = "SystemPreferences não encontrada com dateUpdated = ";


    @Mock
    private SystemPreferencesRepository systempreferencesRepositoryMock;

    @InjectMocks
    private SystemPreferencesService systempreferencesService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        systempreferencesService = new SystemPreferencesServiceImpl();
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
    public void shouldReturnListOfSystemPreferencesWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 2017L;
        UUID uuidExternalApp = UUID.fromString("c7de4ba5-9e18-4564-a97f-e5f48739e9ab");
        String key = "3famw1UDFKLiSN7zHJ67CXYntE78NqHaUREgbhTSciN4xUeDLP";
        String preference = "BKj9UQwUlxGOECtzddveK6JK01u67Vme5Jre9Psfjma2zQehsf";
        String status = "JgicO1NKLBjVQUozQByT2VAN7yeO5w0mvSGgzPOcn60qGIozVz";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("uuidExternalApp", uuidExternalApp);
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

        List<SystemPreferences> systempreferencessFromRepository = new ArrayList<>();
        systempreferencessFromRepository.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        systempreferencessFromRepository.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        systempreferencessFromRepository.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        systempreferencessFromRepository.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());

        Mockito.when(systempreferencesRepositoryMock.findSystemPreferencesByFilter(
            id,
            uuidExternalApp,
            key,
            preference,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(systempreferencessFromRepository);

        // action
        List<SystemPreferencesDTO> result = systempreferencesService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithSystemPreferencesListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 88151L;
        UUID uuidExternalApp = UUID.fromString("e926bdb0-2105-4d9d-b441-05dfff213b99");
        String key = "S4AkkmV6rD3DDOtit9kK62Tnn00YCqiCAOPrxREbqLadhx9nIE";
        String preference = "19MUHajkaXDo1aKWwuxk3IobT53w3uVCxjfsYmFADaBk9lABoy";
        String status = "ufN8RJJ8j73aTFWkF97V0z2MFm1hxHEACP0n7AIGzlXpM1q6cs";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("uuidExternalApp", uuidExternalApp);
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

        List<SystemPreferences> systempreferencessFromRepository = new ArrayList<>();
        systempreferencessFromRepository.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        systempreferencessFromRepository.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        systempreferencessFromRepository.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        systempreferencessFromRepository.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());

        List<SystemPreferencesDTO> systempreferencessFiltered = systempreferencessFromRepository
                .stream()
                .map(m->systempreferencesService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageSystemPreferencesItems", systempreferencessFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<SystemPreferences> pagedResponse =
                new PageImpl<>(systempreferencessFromRepository,
                        pageableMock,
                        systempreferencessFromRepository.size());

        Mockito.when(systempreferencesRepositoryMock.findSystemPreferencesByFilter(pageableMock,
            id,
            uuidExternalApp,
            key,
            preference,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = systempreferencesService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<SystemPreferencesDTO> systempreferencessResult = (List<SystemPreferencesDTO>) result.get("pageSystemPreferencesItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, systempreferencessResult.size());
    }


    @Test
    public void showReturnListOfSystemPreferencesWhenAskedForFindAllByStatus() {
        // scenario
        List<SystemPreferences> listOfSystemPreferencesModelMock = new ArrayList<>();
        listOfSystemPreferencesModelMock.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        listOfSystemPreferencesModelMock.add(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());

        Mockito.when(systempreferencesRepositoryMock.findAllByStatus("A")).thenReturn(listOfSystemPreferencesModelMock);

        // action
        List<SystemPreferencesDTO> listOfSystemPreferencess = systempreferencesService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfSystemPreferencess.isEmpty());
        Assertions.assertEquals(2, listOfSystemPreferencess.size());
    }
    @Test
    public void shouldReturnSystemPreferencesNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 13883L;
        Optional<SystemPreferences> systempreferencesNonExistentMock = Optional.empty();
        Mockito.when(systempreferencesRepositoryMock.findById(idMock)).thenReturn(systempreferencesNonExistentMock);

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowSystemPreferencesNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 34455L;
        Mockito.when(systempreferencesRepositoryMock.findById(idMock))
                .thenThrow(new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                SYSTEMPREFERENCES_NOTFOUND_WITH_ID ));

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnSystemPreferencesDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 62200L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(
                SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                        .id(idMock)
                        .uuidExternalApp(UUID.fromString("a603eca9-a88c-4cf0-8d50-c12d7e6bb0af"))
                        .key("y5IbhBuhCtsF7s8fGCMkhCH0EPgW0SPH8dJNDglBJWLBkPmV0t")
                        .preference("8KUYbdW0Hr1W6px2n4plKXmr4zhuDlTjiSMPzvNspfJkEJsGtC")

                        .status("X")
                        .now()
        );
        SystemPreferences systempreferencesToSaveMock = systempreferencesModelMock.orElse(null);
        SystemPreferences systempreferencesSavedMck = SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                        .id(47343L)
                        .uuidExternalApp(UUID.fromString("556894f3-d0fa-400b-a66c-f2ef7a251e6d"))
                        .key("Yk8MR0HF9LwbkDB60ELqyhOvBQ4MgobOWnE1vgm73cEyIqWtrm")
                        .preference("D1pQV0AE0D35bijz30AgjIy3SpMVgpBWy3QPkXLX47b85VJihT")

                        .status("A")
                        .now();
        Mockito.when(systempreferencesRepositoryMock.findById(idMock)).thenReturn(systempreferencesModelMock);
        Mockito.when(systempreferencesRepositoryMock.save(systempreferencesToSaveMock)).thenReturn(systempreferencesSavedMck);

        // action
        SystemPreferencesDTO result = systempreferencesService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchSystemPreferencesByAnyNonExistenceIdAndReturnSystemPreferencesNotFoundException() {
        // scenario
        Mockito.when(systempreferencesRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()-> systempreferencesService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchSystemPreferencesByIdAndReturnDTO() {
        // scenario
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .id(20624L)
                .uuidExternalApp(UUID.fromString("f46b67e2-d322-4939-a02e-4ea4c1095510"))
                .key("3NE0D5y5WfPhuc1YdxGioclhPKVwN3fnDfq5nzwQpGbAJLCu3z")
                .preference("RIv5NyNKEFqhvL9KV5hlloRyJh8mdnzV84A9z3mSGxNjabp19h")

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(systempreferencesRepositoryMock.findById(Mockito.anyLong())).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findById(1L);

        // validate
        Assertions.assertInstanceOf(SystemPreferencesDTO.class,result);
    }
    @Test
    public void shouldDeleteSystemPreferencesByIdWithSucess() {
        // scenario
        Optional<SystemPreferences> systempreferences = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().id(1L).now());
        Mockito.when(systempreferencesRepositoryMock.findById(Mockito.anyLong())).thenReturn(systempreferences);

        // action
        systempreferencesService.delete(1L);

        // validate
        Mockito.verify(systempreferencesRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceSystemPreferencesShouldReturnSystemPreferencesNotFoundException() {
        // scenario
        Mockito.when(systempreferencesRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(
                SystemPreferencesNotFoundException.class, () -> systempreferencesService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingSystemPreferencesWithSucess() {
        // scenario
        SystemPreferencesDTO systempreferencesDTOMock = SystemPreferencesDTOBuilder.newSystemPreferencesDTOTestBuilder()
                .id(17537L)
                .uuidExternalApp(UUID.fromString("e72e0fd0-1a09-4570-9faa-946d196cf0d3"))
                .key("sbuxCPINxqacjtOG0tlaogQzUKpKexKQ75atjoUmQqGbsI8OAA")
                .preference("M9yycPxpub7tW1qcE0EO8gV7jTQY2XLCaR4tjNsReuQmpgSl1t")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SystemPreferences systempreferencesMock = SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .id(systempreferencesDTOMock.getId())
                .uuidExternalApp(systempreferencesDTOMock.getUuidExternalApp())
                .key(systempreferencesDTOMock.getKey())
                .preference(systempreferencesDTOMock.getPreference())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SystemPreferences systempreferencesSavedMock = SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .id(systempreferencesDTOMock.getId())
                .uuidExternalApp(systempreferencesDTOMock.getUuidExternalApp())
                .key(systempreferencesDTOMock.getKey())
                .preference(systempreferencesDTOMock.getPreference())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(systempreferencesRepositoryMock.save(systempreferencesMock)).thenReturn(systempreferencesSavedMock);

        // action
        SystemPreferencesDTO systempreferencesSaved = systempreferencesService.salvar(systempreferencesDTOMock);

        // validate
        Assertions.assertInstanceOf(SystemPreferencesDTO.class, systempreferencesSaved);
        Assertions.assertNotNull(systempreferencesSaved.getId());
    }

    @Test
    public void ShouldSaveNewSystemPreferencesWithSucess() {
        // scenario
        SystemPreferencesDTO systempreferencesDTOMock = SystemPreferencesDTOBuilder.newSystemPreferencesDTOTestBuilder()
                .id(null)
                .uuidExternalApp(UUID.fromString("635b8994-a146-4066-9877-581ce0be4bea"))
                .key("O1biHuOft3EsYg0PG960BdC1vaSCXfyGTyt27vQmMWL2Iagbh0")
                .preference("er8adWfYT8MVeVz0bHLQQXIP0gVNpOcY3aCsncquDQ0fephV2k")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SystemPreferences systempreferencesModelMock = SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .id(null)
                .uuidExternalApp(systempreferencesDTOMock.getUuidExternalApp())
                .key(systempreferencesDTOMock.getKey())
                .preference(systempreferencesDTOMock.getPreference())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        SystemPreferences systempreferencesSavedMock = SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .id(501L)
                .uuidExternalApp(systempreferencesDTOMock.getUuidExternalApp())
                .key(systempreferencesDTOMock.getKey())
                .preference(systempreferencesDTOMock.getPreference())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(systempreferencesRepositoryMock.save(systempreferencesModelMock)).thenReturn(systempreferencesSavedMock);

        // action
        SystemPreferencesDTO systempreferencesSaved = systempreferencesService.salvar(systempreferencesDTOMock);

        // validate
        Assertions.assertInstanceOf(SystemPreferencesDTO.class, systempreferencesSaved);
        Assertions.assertNotNull(systempreferencesSaved.getId());
        Assertions.assertEquals("P",systempreferencesSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapSystemPreferencesDTOMock = new HashMap<>();
        mapSystemPreferencesDTOMock.put(SystemPreferencesConstantes.UUIDEXTERNALAPP,UUID.fromString("95d6d41a-51dd-4f25-8d17-179733ab3f37"));
        mapSystemPreferencesDTOMock.put(SystemPreferencesConstantes.KEY,"mQbqOto4PQbW08yWOvkUTejIUk8uACizeOOsLszVXSsozPlsgO");
        mapSystemPreferencesDTOMock.put(SystemPreferencesConstantes.PREFERENCE,"IqJ17bOHgIgbl705MoqR54UwHiRB3VwHD12xpS8VMptLaMFIbI");
        mapSystemPreferencesDTOMock.put(SystemPreferencesConstantes.STATUS,"yXT3fUw5Y0KD10D38v0gM2cY40xRmKVxCJSiigMsbG8xx7wlA6");


        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(
                SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                        .id(65133L)
                        .uuidExternalApp(UUID.fromString("0413eb67-2bc1-4c80-9b26-e866920a81aa"))
                        .key("YpWDIfhTLLC7MKvx9MhxJ40HVXfw3x6UHPAvSLTVcvqrJ4cKpv")
                        .preference("eRsFPLKiLn92wJrUpFqKDh4DWiQT6JP5N6WBolHrv8nU3OyifO")
                        .status("TetkgCcNxNLpS7yWaeQR710fqmdOpaJSdzDVOw7Q77MphWuY5F")

                        .now()
        );

        Mockito.when(systempreferencesRepositoryMock.findById(1L)).thenReturn(systempreferencesModelMock);

        // action
        boolean executed = systempreferencesService.partialUpdate(1L, mapSystemPreferencesDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnSystemPreferencesNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapSystemPreferencesDTOMock = new HashMap<>();
        mapSystemPreferencesDTOMock.put(SystemPreferencesConstantes.UUIDEXTERNALAPP,UUID.fromString("e501498b-5fca-47fb-8db2-55412668aca7"));
        mapSystemPreferencesDTOMock.put(SystemPreferencesConstantes.KEY,"UVWmqPrfmyEUMYMOGSlh2HBlMw9jzBf2YiFmevSynootinQSEw");
        mapSystemPreferencesDTOMock.put(SystemPreferencesConstantes.PREFERENCE,"jW0ImJHNCkDxlF4HYsOSTfo9UkTOunvwu4MGKULWQEChaNhmcC");
        mapSystemPreferencesDTOMock.put(SystemPreferencesConstantes.STATUS,"aJ0PJArLOt30J7gS73GECoAEnyBfLWLcITe5YxeI33g4T1AFJt");


        Mockito.when(systempreferencesRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.partialUpdate(1L, mapSystemPreferencesDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("SystemPreferences não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnSystemPreferencesListWhenFindAllSystemPreferencesByIdAndStatus() {
        // scenario
        List<SystemPreferences> systempreferencess = Arrays.asList(
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now()
        );

        Mockito.when(systempreferencesRepositoryMock.findAllByIdAndStatus(61603L, "A")).thenReturn(systempreferencess);

        // action
        List<SystemPreferencesDTO> result = systempreferencesService.findAllSystemPreferencesByIdAndStatus(61603L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSystemPreferencesListWhenFindAllSystemPreferencesByUuidExternalAppAndStatus() {
        // scenario
        List<SystemPreferences> systempreferencess = Arrays.asList(
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now()
        );

        Mockito.when(systempreferencesRepositoryMock.findAllByUuidExternalAppAndStatus(UUID.fromString("28871d07-0259-4197-b625-f15fa08fdcf6"), "A")).thenReturn(systempreferencess);

        // action
        List<SystemPreferencesDTO> result = systempreferencesService.findAllSystemPreferencesByUuidExternalAppAndStatus(UUID.fromString("28871d07-0259-4197-b625-f15fa08fdcf6"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSystemPreferencesListWhenFindAllSystemPreferencesByKeyAndStatus() {
        // scenario
        List<SystemPreferences> systempreferencess = Arrays.asList(
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now()
        );

        Mockito.when(systempreferencesRepositoryMock.findAllByKeyAndStatus("FNoWA3yBtt7qJHMqbJixTm21QehLUlsbzjCflLu0prfpbb4ibi", "A")).thenReturn(systempreferencess);

        // action
        List<SystemPreferencesDTO> result = systempreferencesService.findAllSystemPreferencesByKeyAndStatus("FNoWA3yBtt7qJHMqbJixTm21QehLUlsbzjCflLu0prfpbb4ibi", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSystemPreferencesListWhenFindAllSystemPreferencesByPreferenceAndStatus() {
        // scenario
        List<SystemPreferences> systempreferencess = Arrays.asList(
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now()
        );

        Mockito.when(systempreferencesRepositoryMock.findAllByPreferenceAndStatus("snNCSF3QI3s9cvF0XvLULFgzKiHew0UOfkVs0OwLqj1hFAk0zA", "A")).thenReturn(systempreferencess);

        // action
        List<SystemPreferencesDTO> result = systempreferencesService.findAllSystemPreferencesByPreferenceAndStatus("snNCSF3QI3s9cvF0XvLULFgzKiHew0UOfkVs0OwLqj1hFAk0zA", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSystemPreferencesListWhenFindAllSystemPreferencesByDateCreatedAndStatus() {
        // scenario
        List<SystemPreferences> systempreferencess = Arrays.asList(
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now()
        );

        Mockito.when(systempreferencesRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(systempreferencess);

        // action
        List<SystemPreferencesDTO> result = systempreferencesService.findAllSystemPreferencesByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnSystemPreferencesListWhenFindAllSystemPreferencesByDateUpdatedAndStatus() {
        // scenario
        List<SystemPreferences> systempreferencess = Arrays.asList(
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now(),
            SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now()
        );

        Mockito.when(systempreferencesRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(systempreferencess);

        // action
        List<SystemPreferencesDTO> result = systempreferencesService.findAllSystemPreferencesByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentSystemPreferencesDTOWhenFindSystemPreferencesByIdAndStatus() {
        // scenario
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByIdAndStatus(33018L, "A")).thenReturn(1L);
        Mockito.when(systempreferencesRepositoryMock.findById(1L)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findSystemPreferencesByIdAndStatus(33018L, "A");

        // validate
        Assertions.assertInstanceOf(SystemPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnSystemPreferencesNotFoundExceptionWhenNonExistenceSystemPreferencesIdAndStatus() {
        // scenario
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByIdAndStatus(33018L, "A")).thenReturn(0L);
        Mockito.when(systempreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.findSystemPreferencesByIdAndStatus(33018L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentSystemPreferencesDTOWhenFindSystemPreferencesByUuidExternalAppAndStatus() {
        // scenario
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("96557d3c-cc1b-47ad-955a-b3e5492c4453"), "A")).thenReturn(1L);
        Mockito.when(systempreferencesRepositoryMock.findById(1L)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findSystemPreferencesByUuidExternalAppAndStatus(UUID.fromString("96557d3c-cc1b-47ad-955a-b3e5492c4453"), "A");

        // validate
        Assertions.assertInstanceOf(SystemPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnSystemPreferencesNotFoundExceptionWhenNonExistenceSystemPreferencesUuidExternalAppAndStatus() {
        // scenario
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("96557d3c-cc1b-47ad-955a-b3e5492c4453"), "A")).thenReturn(0L);
        Mockito.when(systempreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.findSystemPreferencesByUuidExternalAppAndStatus(UUID.fromString("96557d3c-cc1b-47ad-955a-b3e5492c4453"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP));
    }
    @Test
    public void shouldReturnExistentSystemPreferencesDTOWhenFindSystemPreferencesByKeyAndStatus() {
        // scenario
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByKeyAndStatus("dQipRABAYl1dvm3n0a06hEBsJtYJTaGAYpnEbWwQzrkHIONKws", "A")).thenReturn(1L);
        Mockito.when(systempreferencesRepositoryMock.findById(1L)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findSystemPreferencesByKeyAndStatus("dQipRABAYl1dvm3n0a06hEBsJtYJTaGAYpnEbWwQzrkHIONKws", "A");

        // validate
        Assertions.assertInstanceOf(SystemPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnSystemPreferencesNotFoundExceptionWhenNonExistenceSystemPreferencesKeyAndStatus() {
        // scenario
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByKeyAndStatus("dQipRABAYl1dvm3n0a06hEBsJtYJTaGAYpnEbWwQzrkHIONKws", "A")).thenReturn(0L);
        Mockito.when(systempreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.findSystemPreferencesByKeyAndStatus("dQipRABAYl1dvm3n0a06hEBsJtYJTaGAYpnEbWwQzrkHIONKws", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_KEY));
    }
    @Test
    public void shouldReturnExistentSystemPreferencesDTOWhenFindSystemPreferencesByPreferenceAndStatus() {
        // scenario
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder().now());
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByPreferenceAndStatus("Fvzq00S0MqE3t5q60156DH0VKP5tMC0ca4jfhRSUypMlE02VDc", "A")).thenReturn(1L);
        Mockito.when(systempreferencesRepositoryMock.findById(1L)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findSystemPreferencesByPreferenceAndStatus("Fvzq00S0MqE3t5q60156DH0VKP5tMC0ca4jfhRSUypMlE02VDc", "A");

        // validate
        Assertions.assertInstanceOf(SystemPreferencesDTO.class,result);
    }
    @Test
    public void shouldReturnSystemPreferencesNotFoundExceptionWhenNonExistenceSystemPreferencesPreferenceAndStatus() {
        // scenario
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByPreferenceAndStatus("Fvzq00S0MqE3t5q60156DH0VKP5tMC0ca4jfhRSUypMlE02VDc", "A")).thenReturn(0L);
        Mockito.when(systempreferencesRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.findSystemPreferencesByPreferenceAndStatus("Fvzq00S0MqE3t5q60156DH0VKP5tMC0ca4jfhRSUypMlE02VDc", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_PREFERENCE));
    }

    @Test
    public void shouldReturnSystemPreferencesDTOWhenUpdateExistingUuidExternalAppById() {
        // scenario
        UUID uuidExternalAppUpdateMock = UUID.fromString("77cc00c6-9377-4581-a8db-196f765b2fd4");
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(systempreferencesRepositoryMock.findById(420L)).thenReturn(systempreferencesModelMock);
        Mockito.doNothing().when(systempreferencesRepositoryMock).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // action
        systempreferencesService.updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // validate
        Mockito.verify(systempreferencesRepositoryMock,Mockito.times(1)).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);
    }
    @Test
    public void shouldReturnSystemPreferencesDTOWhenUpdateExistingKeyById() {
        // scenario
        String keyUpdateMock = "dxofGKejNEQwotJn0tFw7v05ClRxKuRwaQRtOdFPYk38MvMPxV";
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(systempreferencesRepositoryMock.findById(420L)).thenReturn(systempreferencesModelMock);
        Mockito.doNothing().when(systempreferencesRepositoryMock).updateKeyById(420L, keyUpdateMock);

        // action
        systempreferencesService.updateKeyById(420L, keyUpdateMock);

        // validate
        Mockito.verify(systempreferencesRepositoryMock,Mockito.times(1)).updateKeyById(420L, keyUpdateMock);
    }
    @Test
    public void shouldReturnSystemPreferencesDTOWhenUpdateExistingPreferenceById() {
        // scenario
        String preferenceUpdateMock = "Wzgmm006yCw5LmOEYY5x7EO4TxHs5bQe0nL8JWKGam0bsFP0lc";
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(systempreferencesRepositoryMock.findById(420L)).thenReturn(systempreferencesModelMock);
        Mockito.doNothing().when(systempreferencesRepositoryMock).updatePreferenceById(420L, preferenceUpdateMock);

        // action
        systempreferencesService.updatePreferenceById(420L, preferenceUpdateMock);

        // validate
        Mockito.verify(systempreferencesRepositoryMock,Mockito.times(1)).updatePreferenceById(420L, preferenceUpdateMock);
    }



    @Test
    public void showReturnExistingSystemPreferencesDTOWhenFindSystemPreferencesByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 24641L;
        Long maxIdMock = 1972L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(systempreferencesRepositoryMock.findById(maxIdMock)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findSystemPreferencesByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnSystemPreferencesNotFoundExceptionWhenNonExistenceFindSystemPreferencesByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 24641L;
        Long noMaxIdMock = 0L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.empty();
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(systempreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.findSystemPreferencesByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingSystemPreferencesDTOWhenFindSystemPreferencesByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("bf69b27d-1aa7-4a63-9bbd-dd2b6bb42b50");
        Long maxIdMock = 1972L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .uuidExternalApp(uuidExternalAppMock)
                .now()
        );
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(maxIdMock);
        Mockito.when(systempreferencesRepositoryMock.findById(maxIdMock)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findSystemPreferencesByUuidExternalAppAndStatus(uuidExternalAppMock);

        // validate
        Assertions.assertEquals(uuidExternalAppMock, result.getUuidExternalApp());

    }
    @Test
    public void showReturnSystemPreferencesNotFoundExceptionWhenNonExistenceFindSystemPreferencesByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("bf69b27d-1aa7-4a63-9bbd-dd2b6bb42b50");
        Long noMaxIdMock = 0L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.empty();
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(systempreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.findSystemPreferencesByUuidExternalAppAndStatus(uuidExternalAppMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingSystemPreferencesDTOWhenFindSystemPreferencesByKeyAndStatusActiveAnonimous() {
        // scenario
        String keyMock = "YywOd0xjz1tI178ePCMmI646200NbClJ4tSp13XtIPhcNOj0tK";
        Long maxIdMock = 1972L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .key(keyMock)
                .now()
        );
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByKeyAndStatus(keyMock, "A")).thenReturn(maxIdMock);
        Mockito.when(systempreferencesRepositoryMock.findById(maxIdMock)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findSystemPreferencesByKeyAndStatus(keyMock);

        // validate
        Assertions.assertEquals(keyMock, result.getKey());

    }
    @Test
    public void showReturnSystemPreferencesNotFoundExceptionWhenNonExistenceFindSystemPreferencesByKeyAndStatusActiveAnonimous() {
        // scenario
        String keyMock = "YywOd0xjz1tI178ePCMmI646200NbClJ4tSp13XtIPhcNOj0tK";
        Long noMaxIdMock = 0L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.empty();
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByKeyAndStatus(keyMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(systempreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.findSystemPreferencesByKeyAndStatus(keyMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_KEY));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingSystemPreferencesDTOWhenFindSystemPreferencesByPreferenceAndStatusActiveAnonimous() {
        // scenario
        String preferenceMock = "I8KVPtpm6MNgYxo5y6X3PdsHG91tPvIoelBMSgfpN7CXI8LHFI";
        Long maxIdMock = 1972L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.ofNullable(SystemPreferencesModelBuilder.newSystemPreferencesModelTestBuilder()
                .preference(preferenceMock)
                .now()
        );
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByPreferenceAndStatus(preferenceMock, "A")).thenReturn(maxIdMock);
        Mockito.when(systempreferencesRepositoryMock.findById(maxIdMock)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesDTO result = systempreferencesService.findSystemPreferencesByPreferenceAndStatus(preferenceMock);

        // validate
        Assertions.assertEquals(preferenceMock, result.getPreference());

    }
    @Test
    public void showReturnSystemPreferencesNotFoundExceptionWhenNonExistenceFindSystemPreferencesByPreferenceAndStatusActiveAnonimous() {
        // scenario
        String preferenceMock = "I8KVPtpm6MNgYxo5y6X3PdsHG91tPvIoelBMSgfpN7CXI8LHFI";
        Long noMaxIdMock = 0L;
        Optional<SystemPreferences> systempreferencesModelMock = Optional.empty();
        Mockito.when(systempreferencesRepositoryMock.loadMaxIdByPreferenceAndStatus(preferenceMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(systempreferencesRepositoryMock.findById(noMaxIdMock)).thenReturn(systempreferencesModelMock);

        // action
        SystemPreferencesNotFoundException exception = Assertions.assertThrows(SystemPreferencesNotFoundException.class,
                ()->systempreferencesService.findSystemPreferencesByPreferenceAndStatus(preferenceMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(SYSTEMPREFERENCES_NOTFOUND_WITH_PREFERENCE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

