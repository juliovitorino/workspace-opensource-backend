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

package br.com.jcv.reminder.corelayer.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.reminder.corelayer.builder.ReminderListDTOBuilder;
import br.com.jcv.reminder.corelayer.builder.ReminderListModelBuilder;
import br.com.jcv.reminder.corelayer.dto.ReminderListDTO;
import br.com.jcv.reminder.corelayer.exception.ReminderListNotFoundException;
import br.com.jcv.reminder.corelayer.model.ReminderList;
import br.com.jcv.reminder.corelayer.repository.ReminderListRepository;
import br.com.jcv.reminder.corelayer.service.ReminderListService;
import br.com.jcv.reminder.corelayer.constantes.ReminderListConstantes;
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
public class ReminderListServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String REMINDERLIST_NOTFOUND_WITH_ID = "ReminderList não encontrada com id = ";
    public static final String REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALAPP = "ReminderList não encontrada com uuidExternalApp = ";
    public static final String REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALUSER = "ReminderList não encontrada com uuidExternalUser = ";
    public static final String REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALLIST = "ReminderList não encontrada com uuidExternalList = ";
    public static final String REMINDERLIST_NOTFOUND_WITH_TITLE = "ReminderList não encontrada com title = ";
    public static final String REMINDERLIST_NOTFOUND_WITH_STATUS = "ReminderList não encontrada com status = ";
    public static final String REMINDERLIST_NOTFOUND_WITH_DATECREATED = "ReminderList não encontrada com dateCreated = ";
    public static final String REMINDERLIST_NOTFOUND_WITH_DATEUPDATED = "ReminderList não encontrada com dateUpdated = ";


    @Mock
    private ReminderListRepository reminderlistRepositoryMock;

    @InjectMocks
    private ReminderListService reminderlistService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        reminderlistService = new ReminderListServiceImpl();
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
    public void shouldReturnListOfReminderListWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 10814L;
        UUID uuidExternalApp = UUID.fromString("28d4d4f4-d9e3-4bd1-ae4b-e8cef9c9b037");
        UUID uuidExternalUser = UUID.fromString("dd1a9d0f-f2b7-4604-ba36-2c82e1d78649");
        UUID uuidExternalList = UUID.fromString("1930d2a4-470e-425b-9d8d-4f51f829a9b8");
        String title = "3mB7NyW2GsYKtyxVFREJmr4DVrhdFhencITISEShgikkvxOAYu";
        String status = "R7D4zhUooO2Q21bdYrvWATE8GCQrj9niNDOshD78pOjaU4FnwP";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("uuidExternalApp", uuidExternalApp);
        mapFieldsRequestMock.put("uuidExternalUser", uuidExternalUser);
        mapFieldsRequestMock.put("uuidExternalList", uuidExternalList);
        mapFieldsRequestMock.put("title", title);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<ReminderList> reminderlistsFromRepository = new ArrayList<>();
        reminderlistsFromRepository.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        reminderlistsFromRepository.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        reminderlistsFromRepository.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        reminderlistsFromRepository.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());

        Mockito.when(reminderlistRepositoryMock.findReminderListByFilter(
            id,
            uuidExternalApp,
            uuidExternalUser,
            uuidExternalList,
            title,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(reminderlistsFromRepository);

        // action
        List<ReminderListDTO> result = reminderlistService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithReminderListListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 14846L;
        UUID uuidExternalApp = UUID.fromString("44b031a7-a71a-48b9-ac08-b34bb11dd827");
        UUID uuidExternalUser = UUID.fromString("9b6aa883-2e9a-4d22-929d-2e0f087c4d8f");
        UUID uuidExternalList = UUID.fromString("08870ae3-7457-44c8-8334-89474b26ac0b");
        String title = "CMlMt6Bl2NBxbNB4qRWFUhXSWpCEQlL0VFhhDvDypnHUJknyG8";
        String status = "96ONlu0UT0aaTrPWQ89CYKnyqX7LldejSemt2A5bAr30eTfO7y";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("uuidExternalApp", uuidExternalApp);
        mapFieldsRequestMock.put("uuidExternalUser", uuidExternalUser);
        mapFieldsRequestMock.put("uuidExternalList", uuidExternalList);
        mapFieldsRequestMock.put("title", title);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<ReminderList> reminderlistsFromRepository = new ArrayList<>();
        reminderlistsFromRepository.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        reminderlistsFromRepository.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        reminderlistsFromRepository.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        reminderlistsFromRepository.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());

        List<ReminderListDTO> reminderlistsFiltered = reminderlistsFromRepository
                .stream()
                .map(m->reminderlistService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageReminderListItems", reminderlistsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<ReminderList> pagedResponse =
                new PageImpl<>(reminderlistsFromRepository,
                        pageableMock,
                        reminderlistsFromRepository.size());

        Mockito.when(reminderlistRepositoryMock.findReminderListByFilter(pageableMock,
            id,
            uuidExternalApp,
            uuidExternalUser,
            uuidExternalList,
            title,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = reminderlistService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<ReminderListDTO> reminderlistsResult = (List<ReminderListDTO>) result.get("pageReminderListItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, reminderlistsResult.size());
    }


    @Test
    public void showReturnListOfReminderListWhenAskedForFindAllByStatus() {
        // scenario
        List<ReminderList> listOfReminderListModelMock = new ArrayList<>();
        listOfReminderListModelMock.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        listOfReminderListModelMock.add(ReminderListModelBuilder.newReminderListModelTestBuilder().now());

        Mockito.when(reminderlistRepositoryMock.findAllByStatus("A")).thenReturn(listOfReminderListModelMock);

        // action
        List<ReminderListDTO> listOfReminderLists = reminderlistService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfReminderLists.isEmpty());
        Assertions.assertEquals(2, listOfReminderLists.size());
    }
    @Test
    public void shouldReturnReminderListNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 13067L;
        Optional<ReminderList> reminderlistNonExistentMock = Optional.empty();
        Mockito.when(reminderlistRepositoryMock.findById(idMock)).thenReturn(reminderlistNonExistentMock);

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowReminderListNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 60621L;
        Mockito.when(reminderlistRepositoryMock.findById(idMock))
                .thenThrow(new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                REMINDERLIST_NOTFOUND_WITH_ID ));

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnReminderListDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 68242L;
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(
                ReminderListModelBuilder.newReminderListModelTestBuilder()
                        .id(idMock)
                        .uuidExternalApp(UUID.fromString("ee6f8a4c-f568-49af-a858-58e2e3111530"))
                        .uuidExternalUser(UUID.fromString("1b1e8527-d824-4080-a834-bcf99da0f13a"))
                        .uuidExternalList(UUID.fromString("8709d76e-0ab6-4e37-900c-f1a23469e4ea"))
                        .title("xyb2a1N15prdD0lHm4NfCMJtyNz1I00S0SmR1jjh7MILVm1rOC")

                        .status("X")
                        .now()
        );
        ReminderList reminderlistToSaveMock = reminderlistModelMock.orElse(null);
        ReminderList reminderlistSavedMck = ReminderListModelBuilder.newReminderListModelTestBuilder()
                        .id(40021L)
                        .uuidExternalApp(UUID.fromString("37c5f479-5383-4ffe-93d3-0b492e88ce24"))
                        .uuidExternalUser(UUID.fromString("a9ca64e6-c1b8-46e8-91a5-7c958de624e2"))
                        .uuidExternalList(UUID.fromString("32b4f8eb-7411-467a-8486-2f758bc74379"))
                        .title("WgwKXFhtemki0Vu1XLMvRiXrOr54oSfDfqxuo0mJqd9R0KokFE")

                        .status("A")
                        .now();
        Mockito.when(reminderlistRepositoryMock.findById(idMock)).thenReturn(reminderlistModelMock);
        Mockito.when(reminderlistRepositoryMock.save(reminderlistToSaveMock)).thenReturn(reminderlistSavedMck);

        // action
        ReminderListDTO result = reminderlistService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchReminderListByAnyNonExistenceIdAndReturnReminderListNotFoundException() {
        // scenario
        Mockito.when(reminderlistRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()-> reminderlistService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchReminderListByIdAndReturnDTO() {
        // scenario
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                .id(11351L)
                .uuidExternalApp(UUID.fromString("21b397c7-e532-47f6-99c8-753b5a7f0c9a"))
                .uuidExternalUser(UUID.fromString("d6ba8cac-ac64-4300-8dec-0702a272c1e6"))
                .uuidExternalList(UUID.fromString("48eb7912-51ff-40f2-9762-b1ef616ce9ad"))
                .title("1wmQMjSJfDY0haIKeADc9dx3j00hLb2NJhXPSAxPcpCD2AywbN")

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(reminderlistRepositoryMock.findById(Mockito.anyLong())).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findById(1L);

        // validate
        Assertions.assertInstanceOf(ReminderListDTO.class,result);
    }
    @Test
    public void shouldDeleteReminderListByIdWithSucess() {
        // scenario
        Optional<ReminderList> reminderlist = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder().id(1L).now());
        Mockito.when(reminderlistRepositoryMock.findById(Mockito.anyLong())).thenReturn(reminderlist);

        // action
        reminderlistService.delete(1L);

        // validate
        Mockito.verify(reminderlistRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceReminderListShouldReturnReminderListNotFoundException() {
        // scenario
        Mockito.when(reminderlistRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(
                ReminderListNotFoundException.class, () -> reminderlistService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingReminderListWithSucess() {
        // scenario
        ReminderListDTO reminderlistDTOMock = ReminderListDTOBuilder.newReminderListDTOTestBuilder()
                .id(80724L)
                .uuidExternalApp(UUID.fromString("5ffc4c87-bebf-40b2-8664-74dfc12a7697"))
                .uuidExternalUser(UUID.fromString("abafa7db-7ca2-4fa5-99b0-21baf2088939"))
                .uuidExternalList(UUID.fromString("4f8848e3-c575-4bf6-9593-369d60d49c68"))
                .title("TCIgFAtACJa5GhTMa0nTLj9HFjXo9itHMccchWEJX1WBpeIkdy")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ReminderList reminderlistMock = ReminderListModelBuilder.newReminderListModelTestBuilder()
                .id(reminderlistDTOMock.getId())
                .uuidExternalApp(reminderlistDTOMock.getUuidExternalApp())
                .uuidExternalUser(reminderlistDTOMock.getUuidExternalUser())
                .uuidExternalList(reminderlistDTOMock.getUuidExternalList())
                .title(reminderlistDTOMock.getTitle())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ReminderList reminderlistSavedMock = ReminderListModelBuilder.newReminderListModelTestBuilder()
                .id(reminderlistDTOMock.getId())
                .uuidExternalApp(reminderlistDTOMock.getUuidExternalApp())
                .uuidExternalUser(reminderlistDTOMock.getUuidExternalUser())
                .uuidExternalList(reminderlistDTOMock.getUuidExternalList())
                .title(reminderlistDTOMock.getTitle())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(reminderlistRepositoryMock.save(reminderlistMock)).thenReturn(reminderlistSavedMock);

        // action
        ReminderListDTO reminderlistSaved = reminderlistService.salvar(reminderlistDTOMock);

        // validate
        Assertions.assertInstanceOf(ReminderListDTO.class, reminderlistSaved);
        Assertions.assertNotNull(reminderlistSaved.getId());
    }

    @Test
    public void ShouldSaveNewReminderListWithSucess() {
        // scenario
        ReminderListDTO reminderlistDTOMock = ReminderListDTOBuilder.newReminderListDTOTestBuilder()
                .id(null)
                .uuidExternalApp(UUID.fromString("fb9a0933-51fa-4272-9d67-27b3df264b51"))
                .uuidExternalUser(UUID.fromString("35b528a1-35a8-496c-87b6-40ad387eaf44"))
                .uuidExternalList(UUID.fromString("6a256c74-776f-4876-addd-a2138a224770"))
                .title("DCHIF9RFSrbEGrMReJ6sGk7HPI79WidpyqudSF63pMquXM0xxY")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ReminderList reminderlistModelMock = ReminderListModelBuilder.newReminderListModelTestBuilder()
                .id(null)
                .uuidExternalApp(reminderlistDTOMock.getUuidExternalApp())
                .uuidExternalUser(reminderlistDTOMock.getUuidExternalUser())
                .uuidExternalList(reminderlistDTOMock.getUuidExternalList())
                .title(reminderlistDTOMock.getTitle())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ReminderList reminderlistSavedMock = ReminderListModelBuilder.newReminderListModelTestBuilder()
                .id(501L)
                .uuidExternalApp(reminderlistDTOMock.getUuidExternalApp())
                .uuidExternalUser(reminderlistDTOMock.getUuidExternalUser())
                .uuidExternalList(reminderlistDTOMock.getUuidExternalList())
                .title(reminderlistDTOMock.getTitle())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(reminderlistRepositoryMock.save(reminderlistModelMock)).thenReturn(reminderlistSavedMock);

        // action
        ReminderListDTO reminderlistSaved = reminderlistService.salvar(reminderlistDTOMock);

        // validate
        Assertions.assertInstanceOf(ReminderListDTO.class, reminderlistSaved);
        Assertions.assertNotNull(reminderlistSaved.getId());
        Assertions.assertEquals("P",reminderlistSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapReminderListDTOMock = new HashMap<>();
        mapReminderListDTOMock.put(ReminderListConstantes.UUIDEXTERNALAPP,UUID.fromString("5d0bb5aa-19aa-452b-b3a9-3bf2f91a6b02"));
        mapReminderListDTOMock.put(ReminderListConstantes.UUIDEXTERNALUSER,UUID.fromString("1c1e6678-a420-4ce3-97f4-035e9306d26b"));
        mapReminderListDTOMock.put(ReminderListConstantes.UUIDEXTERNALLIST,UUID.fromString("60ec75b9-6f23-468d-87ba-8ad9bb505182"));
        mapReminderListDTOMock.put(ReminderListConstantes.TITLE,"f69FEo28h1NLTgrhxwoLnPspAvuguUEpLcEkSLsI0GvUHgAgzC");
        mapReminderListDTOMock.put(ReminderListConstantes.STATUS,"d7Ph4oiz1e8gnd5AgyrQr5qlzTYnqDKIvTP3vYQcDRDnAdNlri");


        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(
                ReminderListModelBuilder.newReminderListModelTestBuilder()
                        .id(43237L)
                        .uuidExternalApp(UUID.fromString("b974b71d-40c1-4414-a412-4372af2debe8"))
                        .uuidExternalUser(UUID.fromString("57fd9996-8a9a-496d-bfde-3d12c877a2d6"))
                        .uuidExternalList(UUID.fromString("05981874-8b45-4b3a-b43e-28db7ae5caed"))
                        .title("YmwzN6kQ9Jk2ohTeu54v4X0iCE35t3z6Ao4wuFQnkiuY9f3zPJ")
                        .status("HuVAsGXlunNBNd9dGDHbUAiEulcgcYrNuzvHpNo06UIvqBYfJL")

                        .now()
        );

        Mockito.when(reminderlistRepositoryMock.findById(1L)).thenReturn(reminderlistModelMock);

        // action
        boolean executed = reminderlistService.partialUpdate(1L, mapReminderListDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnReminderListNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapReminderListDTOMock = new HashMap<>();
        mapReminderListDTOMock.put(ReminderListConstantes.UUIDEXTERNALAPP,UUID.fromString("0cb48b26-aea7-4734-b662-fbf0b685eaa8"));
        mapReminderListDTOMock.put(ReminderListConstantes.UUIDEXTERNALUSER,UUID.fromString("11638c40-cd71-45cd-b614-a8a43b14dc6e"));
        mapReminderListDTOMock.put(ReminderListConstantes.UUIDEXTERNALLIST,UUID.fromString("692a6111-ad41-48fa-acec-8719ba086d0c"));
        mapReminderListDTOMock.put(ReminderListConstantes.TITLE,"J33B9RbRAaE4QgHjN1WMTMcqX485pnWwX6V2S5XVO2sxeB6IMS");
        mapReminderListDTOMock.put(ReminderListConstantes.STATUS,"9tFW0L0ay1T38RkvUe4BSTkRCj6EbLWosWgKXRHoX6e2KrfHRX");


        Mockito.when(reminderlistRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.partialUpdate(1L, mapReminderListDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("ReminderList não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnReminderListListWhenFindAllReminderListByIdAndStatus() {
        // scenario
        List<ReminderList> reminderlists = Arrays.asList(
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now()
        );

        Mockito.when(reminderlistRepositoryMock.findAllByIdAndStatus(44341L, "A")).thenReturn(reminderlists);

        // action
        List<ReminderListDTO> result = reminderlistService.findAllReminderListByIdAndStatus(44341L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListListWhenFindAllReminderListByUuidExternalAppAndStatus() {
        // scenario
        List<ReminderList> reminderlists = Arrays.asList(
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now()
        );

        Mockito.when(reminderlistRepositoryMock.findAllByUuidExternalAppAndStatus(UUID.fromString("0f2e7c50-e719-46ea-8d67-ac0b0a64c6b9"), "A")).thenReturn(reminderlists);

        // action
        List<ReminderListDTO> result = reminderlistService.findAllReminderListByUuidExternalAppAndStatus(UUID.fromString("0f2e7c50-e719-46ea-8d67-ac0b0a64c6b9"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListListWhenFindAllReminderListByUuidExternalUserAndStatus() {
        // scenario
        List<ReminderList> reminderlists = Arrays.asList(
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now()
        );

        Mockito.when(reminderlistRepositoryMock.findAllByUuidExternalUserAndStatus(UUID.fromString("02baacc1-c5ad-4b41-a3cf-b215f7f89364"), "A")).thenReturn(reminderlists);

        // action
        List<ReminderListDTO> result = reminderlistService.findAllReminderListByUuidExternalUserAndStatus(UUID.fromString("02baacc1-c5ad-4b41-a3cf-b215f7f89364"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListListWhenFindAllReminderListByUuidExternalListAndStatus() {
        // scenario
        List<ReminderList> reminderlists = Arrays.asList(
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now()
        );

        Mockito.when(reminderlistRepositoryMock.findAllByUuidExternalListAndStatus(UUID.fromString("3846eb57-be7b-4d67-8f0f-abf71d0817b8"), "A")).thenReturn(reminderlists);

        // action
        List<ReminderListDTO> result = reminderlistService.findAllReminderListByUuidExternalListAndStatus(UUID.fromString("3846eb57-be7b-4d67-8f0f-abf71d0817b8"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListListWhenFindAllReminderListByTitleAndStatus() {
        // scenario
        List<ReminderList> reminderlists = Arrays.asList(
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now()
        );

        Mockito.when(reminderlistRepositoryMock.findAllByTitleAndStatus("gVOsB6vJ0KG52yskoLEy5Ypy9O4qlnLm0TY1ilydYrf3A67art", "A")).thenReturn(reminderlists);

        // action
        List<ReminderListDTO> result = reminderlistService.findAllReminderListByTitleAndStatus("gVOsB6vJ0KG52yskoLEy5Ypy9O4qlnLm0TY1ilydYrf3A67art", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListListWhenFindAllReminderListByDateCreatedAndStatus() {
        // scenario
        List<ReminderList> reminderlists = Arrays.asList(
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now()
        );

        Mockito.when(reminderlistRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reminderlists);

        // action
        List<ReminderListDTO> result = reminderlistService.findAllReminderListByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListListWhenFindAllReminderListByDateUpdatedAndStatus() {
        // scenario
        List<ReminderList> reminderlists = Arrays.asList(
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now(),
            ReminderListModelBuilder.newReminderListModelTestBuilder().now()
        );

        Mockito.when(reminderlistRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reminderlists);

        // action
        List<ReminderListDTO> result = reminderlistService.findAllReminderListByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentReminderListDTOWhenFindReminderListByIdAndStatus() {
        // scenario
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByIdAndStatus(86307L, "A")).thenReturn(1L);
        Mockito.when(reminderlistRepositoryMock.findById(1L)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByIdAndStatus(86307L, "A");

        // validate
        Assertions.assertInstanceOf(ReminderListDTO.class,result);
    }
    @Test
    public void shouldReturnReminderListNotFoundExceptionWhenNonExistenceReminderListIdAndStatus() {
        // scenario
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByIdAndStatus(86307L, "A")).thenReturn(0L);
        Mockito.when(reminderlistRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByIdAndStatus(86307L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentReminderListDTOWhenFindReminderListByUuidExternalAppAndStatus() {
        // scenario
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("ac372287-7397-485f-9589-b7de7db4071d"), "A")).thenReturn(1L);
        Mockito.when(reminderlistRepositoryMock.findById(1L)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByUuidExternalAppAndStatus(UUID.fromString("ac372287-7397-485f-9589-b7de7db4071d"), "A");

        // validate
        Assertions.assertInstanceOf(ReminderListDTO.class,result);
    }
    @Test
    public void shouldReturnReminderListNotFoundExceptionWhenNonExistenceReminderListUuidExternalAppAndStatus() {
        // scenario
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("ac372287-7397-485f-9589-b7de7db4071d"), "A")).thenReturn(0L);
        Mockito.when(reminderlistRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByUuidExternalAppAndStatus(UUID.fromString("ac372287-7397-485f-9589-b7de7db4071d"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALAPP));
    }
    @Test
    public void shouldReturnExistentReminderListDTOWhenFindReminderListByUuidExternalUserAndStatus() {
        // scenario
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(UUID.fromString("c3698ac1-de9d-49a1-b688-678e4932c4f5"), "A")).thenReturn(1L);
        Mockito.when(reminderlistRepositoryMock.findById(1L)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByUuidExternalUserAndStatus(UUID.fromString("c3698ac1-de9d-49a1-b688-678e4932c4f5"), "A");

        // validate
        Assertions.assertInstanceOf(ReminderListDTO.class,result);
    }
    @Test
    public void shouldReturnReminderListNotFoundExceptionWhenNonExistenceReminderListUuidExternalUserAndStatus() {
        // scenario
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(UUID.fromString("c3698ac1-de9d-49a1-b688-678e4932c4f5"), "A")).thenReturn(0L);
        Mockito.when(reminderlistRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByUuidExternalUserAndStatus(UUID.fromString("c3698ac1-de9d-49a1-b688-678e4932c4f5"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALUSER));
    }
    @Test
    public void shouldReturnExistentReminderListDTOWhenFindReminderListByUuidExternalListAndStatus() {
        // scenario
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalListAndStatus(UUID.fromString("e463ec0a-0473-4326-945e-e887cfe6c48d"), "A")).thenReturn(1L);
        Mockito.when(reminderlistRepositoryMock.findById(1L)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByUuidExternalListAndStatus(UUID.fromString("e463ec0a-0473-4326-945e-e887cfe6c48d"), "A");

        // validate
        Assertions.assertInstanceOf(ReminderListDTO.class,result);
    }
    @Test
    public void shouldReturnReminderListNotFoundExceptionWhenNonExistenceReminderListUuidExternalListAndStatus() {
        // scenario
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalListAndStatus(UUID.fromString("e463ec0a-0473-4326-945e-e887cfe6c48d"), "A")).thenReturn(0L);
        Mockito.when(reminderlistRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByUuidExternalListAndStatus(UUID.fromString("e463ec0a-0473-4326-945e-e887cfe6c48d"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALLIST));
    }
    @Test
    public void shouldReturnExistentReminderListDTOWhenFindReminderListByTitleAndStatus() {
        // scenario
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder().now());
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByTitleAndStatus("Ln0qiC8u7xt0W9QV7aPSWbwG0loqCjTHu2K2hwpLvSaxxSF0Or", "A")).thenReturn(1L);
        Mockito.when(reminderlistRepositoryMock.findById(1L)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByTitleAndStatus("Ln0qiC8u7xt0W9QV7aPSWbwG0loqCjTHu2K2hwpLvSaxxSF0Or", "A");

        // validate
        Assertions.assertInstanceOf(ReminderListDTO.class,result);
    }
    @Test
    public void shouldReturnReminderListNotFoundExceptionWhenNonExistenceReminderListTitleAndStatus() {
        // scenario
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByTitleAndStatus("Ln0qiC8u7xt0W9QV7aPSWbwG0loqCjTHu2K2hwpLvSaxxSF0Or", "A")).thenReturn(0L);
        Mockito.when(reminderlistRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByTitleAndStatus("Ln0qiC8u7xt0W9QV7aPSWbwG0loqCjTHu2K2hwpLvSaxxSF0Or", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_TITLE));
    }

    @Test
    public void shouldReturnReminderListDTOWhenUpdateExistingUuidExternalAppById() {
        // scenario
        UUID uuidExternalAppUpdateMock = UUID.fromString("fee615a6-f137-4322-b099-e9b077bb8d3f");
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderlistRepositoryMock.findById(420L)).thenReturn(reminderlistModelMock);
        Mockito.doNothing().when(reminderlistRepositoryMock).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // action
        reminderlistService.updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // validate
        Mockito.verify(reminderlistRepositoryMock,Mockito.times(1)).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);
    }
    @Test
    public void shouldReturnReminderListDTOWhenUpdateExistingUuidExternalUserById() {
        // scenario
        UUID uuidExternalUserUpdateMock = UUID.fromString("e4a6e411-410c-48a5-af36-8da45b5f4574");
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderlistRepositoryMock.findById(420L)).thenReturn(reminderlistModelMock);
        Mockito.doNothing().when(reminderlistRepositoryMock).updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);

        // action
        reminderlistService.updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);

        // validate
        Mockito.verify(reminderlistRepositoryMock,Mockito.times(1)).updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);
    }
    @Test
    public void shouldReturnReminderListDTOWhenUpdateExistingUuidExternalListById() {
        // scenario
        UUID uuidExternalListUpdateMock = UUID.fromString("382d72d4-f31e-47e5-94d3-bb8e0c0ac1d8");
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderlistRepositoryMock.findById(420L)).thenReturn(reminderlistModelMock);
        Mockito.doNothing().when(reminderlistRepositoryMock).updateUuidExternalListById(420L, uuidExternalListUpdateMock);

        // action
        reminderlistService.updateUuidExternalListById(420L, uuidExternalListUpdateMock);

        // validate
        Mockito.verify(reminderlistRepositoryMock,Mockito.times(1)).updateUuidExternalListById(420L, uuidExternalListUpdateMock);
    }
    @Test
    public void shouldReturnReminderListDTOWhenUpdateExistingTitleById() {
        // scenario
        String titleUpdateMock = "hLREtzW0Lcl4P1dOM1pbCgNAg9BXJgiIW10vIecbXvH6exa6eL";
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderlistRepositoryMock.findById(420L)).thenReturn(reminderlistModelMock);
        Mockito.doNothing().when(reminderlistRepositoryMock).updateTitleById(420L, titleUpdateMock);

        // action
        reminderlistService.updateTitleById(420L, titleUpdateMock);

        // validate
        Mockito.verify(reminderlistRepositoryMock,Mockito.times(1)).updateTitleById(420L, titleUpdateMock);
    }



    @Test
    public void showReturnExistingReminderListDTOWhenFindReminderListByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 2104L;
        Long maxIdMock = 1972L;
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(maxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnReminderListNotFoundExceptionWhenNonExistenceFindReminderListByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 2104L;
        Long noMaxIdMock = 0L;
        Optional<ReminderList> reminderlistModelMock = Optional.empty();
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderListDTOWhenFindReminderListByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("9c7c600c-10c8-4140-bc5c-f37b853dbab6");
        Long maxIdMock = 1972L;
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                .uuidExternalApp(uuidExternalAppMock)
                .now()
        );
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(maxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByUuidExternalAppAndStatus(uuidExternalAppMock);

        // validate
        Assertions.assertEquals(uuidExternalAppMock, result.getUuidExternalApp());

    }
    @Test
    public void showReturnReminderListNotFoundExceptionWhenNonExistenceFindReminderListByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("9c7c600c-10c8-4140-bc5c-f37b853dbab6");
        Long noMaxIdMock = 0L;
        Optional<ReminderList> reminderlistModelMock = Optional.empty();
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByUuidExternalAppAndStatus(uuidExternalAppMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALAPP));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderListDTOWhenFindReminderListByUuidExternalUserAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalUserMock = UUID.fromString("8ee8606c-274f-4d20-a92f-d2e021c5ea96");
        Long maxIdMock = 1972L;
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                .uuidExternalUser(uuidExternalUserMock)
                .now()
        );
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUserMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(maxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByUuidExternalUserAndStatus(uuidExternalUserMock);

        // validate
        Assertions.assertEquals(uuidExternalUserMock, result.getUuidExternalUser());

    }
    @Test
    public void showReturnReminderListNotFoundExceptionWhenNonExistenceFindReminderListByUuidExternalUserAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalUserMock = UUID.fromString("8ee8606c-274f-4d20-a92f-d2e021c5ea96");
        Long noMaxIdMock = 0L;
        Optional<ReminderList> reminderlistModelMock = Optional.empty();
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUserMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByUuidExternalUserAndStatus(uuidExternalUserMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALUSER));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderListDTOWhenFindReminderListByUuidExternalListAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalListMock = UUID.fromString("ac9b4864-d601-4a72-a390-0eb0e6282194");
        Long maxIdMock = 1972L;
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                .uuidExternalList(uuidExternalListMock)
                .now()
        );
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalListAndStatus(uuidExternalListMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(maxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByUuidExternalListAndStatus(uuidExternalListMock);

        // validate
        Assertions.assertEquals(uuidExternalListMock, result.getUuidExternalList());

    }
    @Test
    public void showReturnReminderListNotFoundExceptionWhenNonExistenceFindReminderListByUuidExternalListAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalListMock = UUID.fromString("ac9b4864-d601-4a72-a390-0eb0e6282194");
        Long noMaxIdMock = 0L;
        Optional<ReminderList> reminderlistModelMock = Optional.empty();
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByUuidExternalListAndStatus(uuidExternalListMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByUuidExternalListAndStatus(uuidExternalListMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALLIST));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderListDTOWhenFindReminderListByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "1dFKuuBe9LnUg5BgkqJlJz1znRNJ8o9xU9DW3dAx00KV63U5JG";
        Long maxIdMock = 1972L;
        Optional<ReminderList> reminderlistModelMock = Optional.ofNullable(ReminderListModelBuilder.newReminderListModelTestBuilder()
                .title(titleMock)
                .now()
        );
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByTitleAndStatus(titleMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(maxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListDTO result = reminderlistService.findReminderListByTitleAndStatus(titleMock);

        // validate
        Assertions.assertEquals(titleMock, result.getTitle());

    }
    @Test
    public void showReturnReminderListNotFoundExceptionWhenNonExistenceFindReminderListByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "1dFKuuBe9LnUg5BgkqJlJz1znRNJ8o9xU9DW3dAx00KV63U5JG";
        Long noMaxIdMock = 0L;
        Optional<ReminderList> reminderlistModelMock = Optional.empty();
        Mockito.when(reminderlistRepositoryMock.loadMaxIdByTitleAndStatus(titleMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderlistRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderlistModelMock);

        // action
        ReminderListNotFoundException exception = Assertions.assertThrows(ReminderListNotFoundException.class,
                ()->reminderlistService.findReminderListByTitleAndStatus(titleMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDERLIST_NOTFOUND_WITH_TITLE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

