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
import br.com.jcv.reminder.corelayer.builder.ListReminderDTOBuilder;
import br.com.jcv.reminder.corelayer.builder.ListReminderModelBuilder;
import br.com.jcv.reminder.corelayer.dto.ListReminderDTO;
import br.com.jcv.reminder.corelayer.exception.ListReminderNotFoundException;
import br.com.jcv.reminder.corelayer.model.ListReminder;
import br.com.jcv.reminder.corelayer.repository.ListReminderRepository;
import br.com.jcv.reminder.corelayer.service.ListReminderService;
import br.com.jcv.reminder.corelayer.constantes.ListReminderConstantes;
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
public class ListReminderServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String LISTREMINDER_NOTFOUND_WITH_ID = "ListReminder não encontrada com id = ";
    public static final String LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALAPP = "ListReminder não encontrada com uuidExternalApp = ";
    public static final String LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALUSER = "ListReminder não encontrada com uuidExternalUser = ";
    public static final String LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALLIST = "ListReminder não encontrada com uuidExternalList = ";
    public static final String LISTREMINDER_NOTFOUND_WITH_TITLE = "ListReminder não encontrada com title = ";
    public static final String LISTREMINDER_NOTFOUND_WITH_STATUS = "ListReminder não encontrada com status = ";
    public static final String LISTREMINDER_NOTFOUND_WITH_DATECREATED = "ListReminder não encontrada com dateCreated = ";
    public static final String LISTREMINDER_NOTFOUND_WITH_DATEUPDATED = "ListReminder não encontrada com dateUpdated = ";


    @Mock
    private ListReminderRepository listreminderRepositoryMock;

    @InjectMocks
    private ListReminderService listreminderService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        listreminderService = new ListReminderServiceImpl();
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
    public void shouldReturnListOfListReminderWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 25067L;
        UUID uuidExternalApp = UUID.fromString("bd99ebeb-5683-416a-836c-dcbd1d2775a1");
        UUID uuidExternalUser = UUID.fromString("1eefb0e0-caba-4f88-8e69-458a29e60965");
        UUID uuidExternalList = UUID.fromString("80dae158-43f6-4e52-a36a-586fd02ef2fc");
        String title = "Jyn82je9tq5sgt5w00kUQOJnXCKO2LaayMq5lQEPlyhuRl4rBD";
        String status = "6cwBJweCsRj35l0qlzY3E1XmkYM8o0VQAzrASJPblPzx5Xqn78";
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

        List<ListReminder> listremindersFromRepository = new ArrayList<>();
        listremindersFromRepository.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        listremindersFromRepository.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        listremindersFromRepository.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        listremindersFromRepository.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());

        Mockito.when(listreminderRepositoryMock.findListReminderByFilter(
            id,
            uuidExternalApp,
            uuidExternalUser,
            uuidExternalList,
            title,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(listremindersFromRepository);

        // action
        List<ListReminderDTO> result = listreminderService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithListReminderListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 1636L;
        UUID uuidExternalApp = UUID.fromString("25579563-be92-4200-b021-680f10fd6942");
        UUID uuidExternalUser = UUID.fromString("729d0a9f-4e71-4d86-ab56-216b884b8f77");
        UUID uuidExternalList = UUID.fromString("9a5c0f6d-b40a-4337-969a-860a0c40c3a5");
        String title = "PrXzGgkXF2dfzNcJFd54P7NzjUz893uMt5rs1zf7wDL2DPr3mL";
        String status = "J3tUMaHeygfvkU7kSIccLj0XWlueiqEBeDig3ErXu7pJjbDdCs";
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

        List<ListReminder> listremindersFromRepository = new ArrayList<>();
        listremindersFromRepository.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        listremindersFromRepository.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        listremindersFromRepository.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        listremindersFromRepository.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());

        List<ListReminderDTO> listremindersFiltered = listremindersFromRepository
                .stream()
                .map(m->listreminderService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageListReminderItems", listremindersFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<ListReminder> pagedResponse =
                new PageImpl<>(listremindersFromRepository,
                        pageableMock,
                        listremindersFromRepository.size());

        Mockito.when(listreminderRepositoryMock.findListReminderByFilter(pageableMock,
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
        Map<String, Object> result = listreminderService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<ListReminderDTO> listremindersResult = (List<ListReminderDTO>) result.get("pageListReminderItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, listremindersResult.size());
    }


    @Test
    public void showReturnListOfListReminderWhenAskedForFindAllByStatus() {
        // scenario
        List<ListReminder> listOfListReminderModelMock = new ArrayList<>();
        listOfListReminderModelMock.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        listOfListReminderModelMock.add(ListReminderModelBuilder.newListReminderModelTestBuilder().now());

        Mockito.when(listreminderRepositoryMock.findAllByStatus("A")).thenReturn(listOfListReminderModelMock);

        // action
        List<ListReminderDTO> listOfListReminders = listreminderService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfListReminders.isEmpty());
        Assertions.assertEquals(2, listOfListReminders.size());
    }
    @Test
    public void shouldReturnListReminderNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 20428L;
        Optional<ListReminder> listreminderNonExistentMock = Optional.empty();
        Mockito.when(listreminderRepositoryMock.findById(idMock)).thenReturn(listreminderNonExistentMock);

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowListReminderNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 14718L;
        Mockito.when(listreminderRepositoryMock.findById(idMock))
                .thenThrow(new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                LISTREMINDER_NOTFOUND_WITH_ID ));

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnListReminderDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 78168L;
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(
                ListReminderModelBuilder.newListReminderModelTestBuilder()
                        .id(idMock)
                        .uuidExternalApp(UUID.fromString("a556bf9e-290f-4999-8520-0e6756161ac8"))
                        .uuidExternalUser(UUID.fromString("43b3587f-07d0-4bd3-9328-45ac03c7e33e"))
                        .uuidExternalList(UUID.fromString("fe9bd236-a6c4-43ad-9110-fe04ea1358d8"))
                        .title("WcpRzrPeH19u5NdjRHExmhWqeXGGN0DASzc1RSMdG5UYVuShab")

                        .status("X")
                        .now()
        );
        ListReminder listreminderToSaveMock = listreminderModelMock.orElse(null);
        ListReminder listreminderSavedMck = ListReminderModelBuilder.newListReminderModelTestBuilder()
                        .id(7220L)
                        .uuidExternalApp(UUID.fromString("3d9c4de3-a394-43ac-a50c-ef9265a967e7"))
                        .uuidExternalUser(UUID.fromString("d5f971ce-a87c-4a0a-b2ad-f2de35bb5de6"))
                        .uuidExternalList(UUID.fromString("110709ae-1923-4e2a-a3a6-85f0d0d25b64"))
                        .title("CPlkol1IKay9rpQYIamn1jq06GpWmxcAi4yvDtKzX6YSBXGydJ")

                        .status("A")
                        .now();
        Mockito.when(listreminderRepositoryMock.findById(idMock)).thenReturn(listreminderModelMock);
        Mockito.when(listreminderRepositoryMock.save(listreminderToSaveMock)).thenReturn(listreminderSavedMck);

        // action
        ListReminderDTO result = listreminderService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchListReminderByAnyNonExistenceIdAndReturnListReminderNotFoundException() {
        // scenario
        Mockito.when(listreminderRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()-> listreminderService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchListReminderByIdAndReturnDTO() {
        // scenario
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                .id(28836L)
                .uuidExternalApp(UUID.fromString("d7d001cc-8a07-4e99-925f-db890be4018b"))
                .uuidExternalUser(UUID.fromString("a8b433f8-4f41-4631-bcfc-1d422741315c"))
                .uuidExternalList(UUID.fromString("cdf056c3-a30d-4959-a56e-76e014aae9f7"))
                .title("ER0SimifUgCFxQl2DHfPiq6DgOh41HcrnTpt1W2YoKxJEUezNP")

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(listreminderRepositoryMock.findById(Mockito.anyLong())).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findById(1L);

        // validate
        Assertions.assertInstanceOf(ListReminderDTO.class,result);
    }
    @Test
    public void shouldDeleteListReminderByIdWithSucess() {
        // scenario
        Optional<ListReminder> listreminder = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder().id(1L).now());
        Mockito.when(listreminderRepositoryMock.findById(Mockito.anyLong())).thenReturn(listreminder);

        // action
        listreminderService.delete(1L);

        // validate
        Mockito.verify(listreminderRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceListReminderShouldReturnListReminderNotFoundException() {
        // scenario
        Mockito.when(listreminderRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(
                ListReminderNotFoundException.class, () -> listreminderService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingListReminderWithSucess() {
        // scenario
        ListReminderDTO listreminderDTOMock = ListReminderDTOBuilder.newListReminderDTOTestBuilder()
                .id(26480L)
                .uuidExternalApp(UUID.fromString("f8e28487-455e-4988-aaf4-b646251fd290"))
                .uuidExternalUser(UUID.fromString("00be3a2c-fb49-453e-9c0b-fa6db0c58b36"))
                .uuidExternalList(UUID.fromString("44449863-6785-4c29-ac20-8ecde750e459"))
                .title("nBahunhYzqctqChxJqcD8COI9msFvuTbaF5NjH9G529AyKK4YY")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ListReminder listreminderMock = ListReminderModelBuilder.newListReminderModelTestBuilder()
                .id(listreminderDTOMock.getId())
                .uuidExternalApp(listreminderDTOMock.getUuidExternalApp())
                .uuidExternalUser(listreminderDTOMock.getUuidExternalUser())
                .uuidExternalList(listreminderDTOMock.getUuidExternalList())
                .title(listreminderDTOMock.getTitle())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ListReminder listreminderSavedMock = ListReminderModelBuilder.newListReminderModelTestBuilder()
                .id(listreminderDTOMock.getId())
                .uuidExternalApp(listreminderDTOMock.getUuidExternalApp())
                .uuidExternalUser(listreminderDTOMock.getUuidExternalUser())
                .uuidExternalList(listreminderDTOMock.getUuidExternalList())
                .title(listreminderDTOMock.getTitle())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(listreminderRepositoryMock.save(listreminderMock)).thenReturn(listreminderSavedMock);

        // action
        ListReminderDTO listreminderSaved = listreminderService.salvar(listreminderDTOMock);

        // validate
        Assertions.assertInstanceOf(ListReminderDTO.class, listreminderSaved);
        Assertions.assertNotNull(listreminderSaved.getId());
    }

    @Test
    public void ShouldSaveNewListReminderWithSucess() {
        // scenario
        ListReminderDTO listreminderDTOMock = ListReminderDTOBuilder.newListReminderDTOTestBuilder()
                .id(null)
                .uuidExternalApp(UUID.fromString("fcca9b24-746d-45bc-90ea-f5d5d5596d0b"))
                .uuidExternalUser(UUID.fromString("d9e9860f-6218-46ca-869a-92baab6c0aab"))
                .uuidExternalList(UUID.fromString("87138c6a-97a6-4d7e-8b23-1a5e7e940934"))
                .title("GY1C2BHrdPr8k9POdX3PiDTbJmCVzep3bJ9wVeNhKzxIda9cbj")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ListReminder listreminderModelMock = ListReminderModelBuilder.newListReminderModelTestBuilder()
                .id(null)
                .uuidExternalApp(listreminderDTOMock.getUuidExternalApp())
                .uuidExternalUser(listreminderDTOMock.getUuidExternalUser())
                .uuidExternalList(listreminderDTOMock.getUuidExternalList())
                .title(listreminderDTOMock.getTitle())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ListReminder listreminderSavedMock = ListReminderModelBuilder.newListReminderModelTestBuilder()
                .id(501L)
                .uuidExternalApp(listreminderDTOMock.getUuidExternalApp())
                .uuidExternalUser(listreminderDTOMock.getUuidExternalUser())
                .uuidExternalList(listreminderDTOMock.getUuidExternalList())
                .title(listreminderDTOMock.getTitle())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(listreminderRepositoryMock.save(listreminderModelMock)).thenReturn(listreminderSavedMock);

        // action
        ListReminderDTO listreminderSaved = listreminderService.salvar(listreminderDTOMock);

        // validate
        Assertions.assertInstanceOf(ListReminderDTO.class, listreminderSaved);
        Assertions.assertNotNull(listreminderSaved.getId());
        Assertions.assertEquals("P",listreminderSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapListReminderDTOMock = new HashMap<>();
        mapListReminderDTOMock.put(ListReminderConstantes.UUIDEXTERNALAPP,UUID.fromString("e068dfc0-8a53-4745-b86b-bc243795e062"));
        mapListReminderDTOMock.put(ListReminderConstantes.UUIDEXTERNALUSER,UUID.fromString("7e4d9bba-a06f-416f-a274-fd5c4bdc7ff0"));
        mapListReminderDTOMock.put(ListReminderConstantes.UUIDEXTERNALLIST,UUID.fromString("79efc526-6c7f-4ca6-b188-147cf12e08a6"));
        mapListReminderDTOMock.put(ListReminderConstantes.TITLE,"EjVtjlzwXJLiWFBkgX1NurclW4TL8zNS80g2nne5GyqDDtWOVW");
        mapListReminderDTOMock.put(ListReminderConstantes.STATUS,"AcLRTSpqw00WxtOMReF0FtGYN7Jt6V8fHnzGGadww7dqnuCFui");


        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(
                ListReminderModelBuilder.newListReminderModelTestBuilder()
                        .id(13623L)
                        .uuidExternalApp(UUID.fromString("a578f1f2-a97c-4ed1-9fe3-ec0d5da30e94"))
                        .uuidExternalUser(UUID.fromString("e58b492a-eab8-4bc9-ac51-7a98e26438a2"))
                        .uuidExternalList(UUID.fromString("62657e48-9982-4466-8f6b-32f1260db269"))
                        .title("jglfx9HhOp8A57b20Bch2gwjtnxP2fszpiEJczlIcj3ouLXMBN")
                        .status("JeMHSzBqwsD7nXSA7aYpwHkUaWiPyYNpDmTNJfHtgTUwlfWSsr")

                        .now()
        );

        Mockito.when(listreminderRepositoryMock.findById(1L)).thenReturn(listreminderModelMock);

        // action
        boolean executed = listreminderService.partialUpdate(1L, mapListReminderDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnListReminderNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapListReminderDTOMock = new HashMap<>();
        mapListReminderDTOMock.put(ListReminderConstantes.UUIDEXTERNALAPP,UUID.fromString("00d15e0c-8020-4dc9-a3e9-1b93039e38ca"));
        mapListReminderDTOMock.put(ListReminderConstantes.UUIDEXTERNALUSER,UUID.fromString("645a0bf9-5527-42ae-9b5d-0c20bb0dd7f0"));
        mapListReminderDTOMock.put(ListReminderConstantes.UUIDEXTERNALLIST,UUID.fromString("5ed07f9a-ea86-4954-9314-255329c3a37f"));
        mapListReminderDTOMock.put(ListReminderConstantes.TITLE,"0l33T3lKPTCS0MmFSAjeOAwU7TqlyzokADV9i0LMjpQzG0LFX5");
        mapListReminderDTOMock.put(ListReminderConstantes.STATUS,"OX9Calu1Tc555hf11OeI8rp94hCBWhNcoqTVxACDrVaUP8Ykme");


        Mockito.when(listreminderRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.partialUpdate(1L, mapListReminderDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("ListReminder não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnListReminderListWhenFindAllListReminderByIdAndStatus() {
        // scenario
        List<ListReminder> listreminders = Arrays.asList(
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now()
        );

        Mockito.when(listreminderRepositoryMock.findAllByIdAndStatus(5567L, "A")).thenReturn(listreminders);

        // action
        List<ListReminderDTO> result = listreminderService.findAllListReminderByIdAndStatus(5567L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnListReminderListWhenFindAllListReminderByUuidExternalAppAndStatus() {
        // scenario
        List<ListReminder> listreminders = Arrays.asList(
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now()
        );

        Mockito.when(listreminderRepositoryMock.findAllByUuidExternalAppAndStatus(UUID.fromString("22953669-743c-44e8-b647-5bd708c01c23"), "A")).thenReturn(listreminders);

        // action
        List<ListReminderDTO> result = listreminderService.findAllListReminderByUuidExternalAppAndStatus(UUID.fromString("22953669-743c-44e8-b647-5bd708c01c23"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnListReminderListWhenFindAllListReminderByUuidExternalUserAndStatus() {
        // scenario
        List<ListReminder> listreminders = Arrays.asList(
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now()
        );

        Mockito.when(listreminderRepositoryMock.findAllByUuidExternalUserAndStatus(UUID.fromString("ebaa9ea4-f9c1-45fa-ab5b-384b35a3c4d2"), "A")).thenReturn(listreminders);

        // action
        List<ListReminderDTO> result = listreminderService.findAllListReminderByUuidExternalUserAndStatus(UUID.fromString("ebaa9ea4-f9c1-45fa-ab5b-384b35a3c4d2"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnListReminderListWhenFindAllListReminderByUuidExternalListAndStatus() {
        // scenario
        List<ListReminder> listreminders = Arrays.asList(
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now()
        );

        Mockito.when(listreminderRepositoryMock.findAllByUuidExternalListAndStatus(UUID.fromString("b08854dc-ab50-42b4-9a65-ad544eadbdad"), "A")).thenReturn(listreminders);

        // action
        List<ListReminderDTO> result = listreminderService.findAllListReminderByUuidExternalListAndStatus(UUID.fromString("b08854dc-ab50-42b4-9a65-ad544eadbdad"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnListReminderListWhenFindAllListReminderByTitleAndStatus() {
        // scenario
        List<ListReminder> listreminders = Arrays.asList(
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now()
        );

        Mockito.when(listreminderRepositoryMock.findAllByTitleAndStatus("wAppCo2FQcTAYGmMQF5tcqGiuTRy9aIdS6yWIw83yUBQgJGFfM", "A")).thenReturn(listreminders);

        // action
        List<ListReminderDTO> result = listreminderService.findAllListReminderByTitleAndStatus("wAppCo2FQcTAYGmMQF5tcqGiuTRy9aIdS6yWIw83yUBQgJGFfM", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnListReminderListWhenFindAllListReminderByDateCreatedAndStatus() {
        // scenario
        List<ListReminder> listreminders = Arrays.asList(
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now()
        );

        Mockito.when(listreminderRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(listreminders);

        // action
        List<ListReminderDTO> result = listreminderService.findAllListReminderByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnListReminderListWhenFindAllListReminderByDateUpdatedAndStatus() {
        // scenario
        List<ListReminder> listreminders = Arrays.asList(
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now(),
            ListReminderModelBuilder.newListReminderModelTestBuilder().now()
        );

        Mockito.when(listreminderRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(listreminders);

        // action
        List<ListReminderDTO> result = listreminderService.findAllListReminderByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentListReminderDTOWhenFindListReminderByIdAndStatus() {
        // scenario
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        Mockito.when(listreminderRepositoryMock.loadMaxIdByIdAndStatus(77374L, "A")).thenReturn(1L);
        Mockito.when(listreminderRepositoryMock.findById(1L)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByIdAndStatus(77374L, "A");

        // validate
        Assertions.assertInstanceOf(ListReminderDTO.class,result);
    }
    @Test
    public void shouldReturnListReminderNotFoundExceptionWhenNonExistenceListReminderIdAndStatus() {
        // scenario
        Mockito.when(listreminderRepositoryMock.loadMaxIdByIdAndStatus(77374L, "A")).thenReturn(0L);
        Mockito.when(listreminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByIdAndStatus(77374L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentListReminderDTOWhenFindListReminderByUuidExternalAppAndStatus() {
        // scenario
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("81cd467a-bf21-4e46-a0be-139e9c09445a"), "A")).thenReturn(1L);
        Mockito.when(listreminderRepositoryMock.findById(1L)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByUuidExternalAppAndStatus(UUID.fromString("81cd467a-bf21-4e46-a0be-139e9c09445a"), "A");

        // validate
        Assertions.assertInstanceOf(ListReminderDTO.class,result);
    }
    @Test
    public void shouldReturnListReminderNotFoundExceptionWhenNonExistenceListReminderUuidExternalAppAndStatus() {
        // scenario
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("81cd467a-bf21-4e46-a0be-139e9c09445a"), "A")).thenReturn(0L);
        Mockito.when(listreminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByUuidExternalAppAndStatus(UUID.fromString("81cd467a-bf21-4e46-a0be-139e9c09445a"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALAPP));
    }
    @Test
    public void shouldReturnExistentListReminderDTOWhenFindListReminderByUuidExternalUserAndStatus() {
        // scenario
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(UUID.fromString("9fadff60-7e38-4a9b-b16a-96673645b880"), "A")).thenReturn(1L);
        Mockito.when(listreminderRepositoryMock.findById(1L)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByUuidExternalUserAndStatus(UUID.fromString("9fadff60-7e38-4a9b-b16a-96673645b880"), "A");

        // validate
        Assertions.assertInstanceOf(ListReminderDTO.class,result);
    }
    @Test
    public void shouldReturnListReminderNotFoundExceptionWhenNonExistenceListReminderUuidExternalUserAndStatus() {
        // scenario
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(UUID.fromString("9fadff60-7e38-4a9b-b16a-96673645b880"), "A")).thenReturn(0L);
        Mockito.when(listreminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByUuidExternalUserAndStatus(UUID.fromString("9fadff60-7e38-4a9b-b16a-96673645b880"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALUSER));
    }
    @Test
    public void shouldReturnExistentListReminderDTOWhenFindListReminderByUuidExternalListAndStatus() {
        // scenario
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalListAndStatus(UUID.fromString("91acc08b-1ad8-4263-9e3d-a940e5af4ce1"), "A")).thenReturn(1L);
        Mockito.when(listreminderRepositoryMock.findById(1L)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByUuidExternalListAndStatus(UUID.fromString("91acc08b-1ad8-4263-9e3d-a940e5af4ce1"), "A");

        // validate
        Assertions.assertInstanceOf(ListReminderDTO.class,result);
    }
    @Test
    public void shouldReturnListReminderNotFoundExceptionWhenNonExistenceListReminderUuidExternalListAndStatus() {
        // scenario
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalListAndStatus(UUID.fromString("91acc08b-1ad8-4263-9e3d-a940e5af4ce1"), "A")).thenReturn(0L);
        Mockito.when(listreminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByUuidExternalListAndStatus(UUID.fromString("91acc08b-1ad8-4263-9e3d-a940e5af4ce1"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALLIST));
    }
    @Test
    public void shouldReturnExistentListReminderDTOWhenFindListReminderByTitleAndStatus() {
        // scenario
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder().now());
        Mockito.when(listreminderRepositoryMock.loadMaxIdByTitleAndStatus("5oTGlxbq0VGxxM5DpH135ArKdAGGG8wROFjYn58OjJ10YC0GyQ", "A")).thenReturn(1L);
        Mockito.when(listreminderRepositoryMock.findById(1L)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByTitleAndStatus("5oTGlxbq0VGxxM5DpH135ArKdAGGG8wROFjYn58OjJ10YC0GyQ", "A");

        // validate
        Assertions.assertInstanceOf(ListReminderDTO.class,result);
    }
    @Test
    public void shouldReturnListReminderNotFoundExceptionWhenNonExistenceListReminderTitleAndStatus() {
        // scenario
        Mockito.when(listreminderRepositoryMock.loadMaxIdByTitleAndStatus("5oTGlxbq0VGxxM5DpH135ArKdAGGG8wROFjYn58OjJ10YC0GyQ", "A")).thenReturn(0L);
        Mockito.when(listreminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByTitleAndStatus("5oTGlxbq0VGxxM5DpH135ArKdAGGG8wROFjYn58OjJ10YC0GyQ", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_TITLE));
    }

    @Test
    public void shouldReturnListReminderDTOWhenUpdateExistingUuidExternalAppById() {
        // scenario
        UUID uuidExternalAppUpdateMock = UUID.fromString("8b82ba00-9f88-4fd2-a446-dab783067148");
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(listreminderRepositoryMock.findById(420L)).thenReturn(listreminderModelMock);
        Mockito.doNothing().when(listreminderRepositoryMock).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // action
        listreminderService.updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // validate
        Mockito.verify(listreminderRepositoryMock,Mockito.times(1)).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);
    }
    @Test
    public void shouldReturnListReminderDTOWhenUpdateExistingUuidExternalUserById() {
        // scenario
        UUID uuidExternalUserUpdateMock = UUID.fromString("58579e82-9520-411a-88fb-556731606330");
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(listreminderRepositoryMock.findById(420L)).thenReturn(listreminderModelMock);
        Mockito.doNothing().when(listreminderRepositoryMock).updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);

        // action
        listreminderService.updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);

        // validate
        Mockito.verify(listreminderRepositoryMock,Mockito.times(1)).updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);
    }
    @Test
    public void shouldReturnListReminderDTOWhenUpdateExistingUuidExternalListById() {
        // scenario
        UUID uuidExternalListUpdateMock = UUID.fromString("230677ce-467a-400a-9e0e-6132b781866c");
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(listreminderRepositoryMock.findById(420L)).thenReturn(listreminderModelMock);
        Mockito.doNothing().when(listreminderRepositoryMock).updateUuidExternalListById(420L, uuidExternalListUpdateMock);

        // action
        listreminderService.updateUuidExternalListById(420L, uuidExternalListUpdateMock);

        // validate
        Mockito.verify(listreminderRepositoryMock,Mockito.times(1)).updateUuidExternalListById(420L, uuidExternalListUpdateMock);
    }
    @Test
    public void shouldReturnListReminderDTOWhenUpdateExistingTitleById() {
        // scenario
        String titleUpdateMock = "Bio2NI32GIm39QQACKOTzruSpfskh22RDdO5GSzYohoeB0Ljm8";
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(listreminderRepositoryMock.findById(420L)).thenReturn(listreminderModelMock);
        Mockito.doNothing().when(listreminderRepositoryMock).updateTitleById(420L, titleUpdateMock);

        // action
        listreminderService.updateTitleById(420L, titleUpdateMock);

        // validate
        Mockito.verify(listreminderRepositoryMock,Mockito.times(1)).updateTitleById(420L, titleUpdateMock);
    }



    @Test
    public void showReturnExistingListReminderDTOWhenFindListReminderByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 88450L;
        Long maxIdMock = 1972L;
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(listreminderRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(maxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnListReminderNotFoundExceptionWhenNonExistenceFindListReminderByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 88450L;
        Long noMaxIdMock = 0L;
        Optional<ListReminder> listreminderModelMock = Optional.empty();
        Mockito.when(listreminderRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(noMaxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingListReminderDTOWhenFindListReminderByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("71ffa371-93f7-427d-b1b0-eee6ad612f0d");
        Long maxIdMock = 1972L;
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                .uuidExternalApp(uuidExternalAppMock)
                .now()
        );
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(maxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(maxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByUuidExternalAppAndStatus(uuidExternalAppMock);

        // validate
        Assertions.assertEquals(uuidExternalAppMock, result.getUuidExternalApp());

    }
    @Test
    public void showReturnListReminderNotFoundExceptionWhenNonExistenceFindListReminderByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("71ffa371-93f7-427d-b1b0-eee6ad612f0d");
        Long noMaxIdMock = 0L;
        Optional<ListReminder> listreminderModelMock = Optional.empty();
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(noMaxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByUuidExternalAppAndStatus(uuidExternalAppMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALAPP));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingListReminderDTOWhenFindListReminderByUuidExternalUserAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalUserMock = UUID.fromString("6aa1d931-1d16-4ebc-9e52-3ea20caa294b");
        Long maxIdMock = 1972L;
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                .uuidExternalUser(uuidExternalUserMock)
                .now()
        );
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUserMock, "A")).thenReturn(maxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(maxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByUuidExternalUserAndStatus(uuidExternalUserMock);

        // validate
        Assertions.assertEquals(uuidExternalUserMock, result.getUuidExternalUser());

    }
    @Test
    public void showReturnListReminderNotFoundExceptionWhenNonExistenceFindListReminderByUuidExternalUserAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalUserMock = UUID.fromString("6aa1d931-1d16-4ebc-9e52-3ea20caa294b");
        Long noMaxIdMock = 0L;
        Optional<ListReminder> listreminderModelMock = Optional.empty();
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUserMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(noMaxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByUuidExternalUserAndStatus(uuidExternalUserMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALUSER));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingListReminderDTOWhenFindListReminderByUuidExternalListAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalListMock = UUID.fromString("eeeed169-4ff1-40fe-bcba-f49caa3044ff");
        Long maxIdMock = 1972L;
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                .uuidExternalList(uuidExternalListMock)
                .now()
        );
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalListAndStatus(uuidExternalListMock, "A")).thenReturn(maxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(maxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByUuidExternalListAndStatus(uuidExternalListMock);

        // validate
        Assertions.assertEquals(uuidExternalListMock, result.getUuidExternalList());

    }
    @Test
    public void showReturnListReminderNotFoundExceptionWhenNonExistenceFindListReminderByUuidExternalListAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalListMock = UUID.fromString("eeeed169-4ff1-40fe-bcba-f49caa3044ff");
        Long noMaxIdMock = 0L;
        Optional<ListReminder> listreminderModelMock = Optional.empty();
        Mockito.when(listreminderRepositoryMock.loadMaxIdByUuidExternalListAndStatus(uuidExternalListMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(noMaxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByUuidExternalListAndStatus(uuidExternalListMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALLIST));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingListReminderDTOWhenFindListReminderByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "lNw6i25IKVxxcyhtA0JHF1K7dmbpEHxMqoBPV83BEzaddPl0QK";
        Long maxIdMock = 1972L;
        Optional<ListReminder> listreminderModelMock = Optional.ofNullable(ListReminderModelBuilder.newListReminderModelTestBuilder()
                .title(titleMock)
                .now()
        );
        Mockito.when(listreminderRepositoryMock.loadMaxIdByTitleAndStatus(titleMock, "A")).thenReturn(maxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(maxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderDTO result = listreminderService.findListReminderByTitleAndStatus(titleMock);

        // validate
        Assertions.assertEquals(titleMock, result.getTitle());

    }
    @Test
    public void showReturnListReminderNotFoundExceptionWhenNonExistenceFindListReminderByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "lNw6i25IKVxxcyhtA0JHF1K7dmbpEHxMqoBPV83BEzaddPl0QK";
        Long noMaxIdMock = 0L;
        Optional<ListReminder> listreminderModelMock = Optional.empty();
        Mockito.when(listreminderRepositoryMock.loadMaxIdByTitleAndStatus(titleMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(listreminderRepositoryMock.findById(noMaxIdMock)).thenReturn(listreminderModelMock);

        // action
        ListReminderNotFoundException exception = Assertions.assertThrows(ListReminderNotFoundException.class,
                ()->listreminderService.findListReminderByTitleAndStatus(titleMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(LISTREMINDER_NOTFOUND_WITH_TITLE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

