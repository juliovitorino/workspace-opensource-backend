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

package br.com.jcv.notifier.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.notifier.builder.NotifierDTOBuilder;
import br.com.jcv.notifier.builder.NotifierModelBuilder;
import br.com.jcv.notifier.dto.NotifierDTO;
import br.com.jcv.notifier.exception.NotifierNotFoundException;
import br.com.jcv.notifier.model.Notifier;
import br.com.jcv.notifier.repository.NotifierRepository;
import br.com.jcv.notifier.service.NotifierService;
import br.com.jcv.notifier.constantes.NotifierConstantes;
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
public class NotifierServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String NOTIFIER_NOTFOUND_WITH_ID = "Notifier não encontrada com id = ";
    public static final String NOTIFIER_NOTFOUND_WITH_APPLICATIONUUID = "Notifier não encontrada com applicationUUID = ";
    public static final String NOTIFIER_NOTFOUND_WITH_USERUUID = "Notifier não encontrada com userUUID = ";
    public static final String NOTIFIER_NOTFOUND_WITH_TYPE = "Notifier não encontrada com type = ";
    public static final String NOTIFIER_NOTFOUND_WITH_TITLE = "Notifier não encontrada com title = ";
    public static final String NOTIFIER_NOTFOUND_WITH_DESCRIPTION = "Notifier não encontrada com description = ";
    public static final String NOTIFIER_NOTFOUND_WITH_ISREADED = "Notifier não encontrada com isReaded = ";
    public static final String NOTIFIER_NOTFOUND_WITH_STATUS = "Notifier não encontrada com status = ";
    public static final String NOTIFIER_NOTFOUND_WITH_DATECREATED = "Notifier não encontrada com dateCreated = ";
    public static final String NOTIFIER_NOTFOUND_WITH_DATEUPDATED = "Notifier não encontrada com dateUpdated = ";


    @Mock
    private NotifierRepository notifierRepositoryMock;

    @InjectMocks
    private NotifierService notifierService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        notifierService = new NotifierServiceImpl();
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
    public void shouldReturnListOfNotifierWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 66520L;
        UUID applicationUUID = UUID.fromString("688bb380-369f-4af4-a0f8-d17c614022fa");
        UUID userUUID = UUID.fromString("f3829d40-5199-4faa-bda9-af74252f54df");
        String type = "BHcYsn8EIRfybiB7H5HPTHgwirtbitswcEUwFqs2CT00CIRjCz";
        String title = "6XkGMmD4u0HgqDpsCnBfGWeJ64VDK0hVaTYH5Uv9Bv6kR0NJ68";
        String description = "S0bIG5IE86uXUgxaljfXmvrTQWCKRsUMIR2rcnT1741Pc87hwW";
        String isReaded = "utpRBLMjx5Bbo0PkH8Oz8BXArSqqx8WCRqpbH59AITetamXRsJ";
        String status = "rdoe74mKwLzuMwGSqpHpRFABWFRnohdwGNVgMerYL0PCeF2QpH";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("applicationUUID", applicationUUID);
        mapFieldsRequestMock.put("userUUID", userUUID);
        mapFieldsRequestMock.put("type", type);
        mapFieldsRequestMock.put("title", title);
        mapFieldsRequestMock.put("description", description);
        mapFieldsRequestMock.put("isReaded", isReaded);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Notifier> notifiersFromRepository = new ArrayList<>();
        notifiersFromRepository.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        notifiersFromRepository.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        notifiersFromRepository.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        notifiersFromRepository.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());

        Mockito.when(notifierRepositoryMock.findNotifierByFilter(
            id,
            applicationUUID,
            userUUID,
            type,
            title,
            description,
            isReaded,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(notifiersFromRepository);

        // action
        List<NotifierDTO> result = notifierService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithNotifierListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 7816L;
        UUID applicationUUID = UUID.fromString("48675316-bcac-4c0b-88a3-6fbe5588ed7a");
        UUID userUUID = UUID.fromString("a232d18c-f2a4-4359-9e63-4056e4a1b793");
        String type = "4spo99yEYOSU2OpjARqFbsjiK8vh62V6LcTRk5P00O6kfsS3IO";
        String title = "UFqCy7PpJLImYWvLqlAYBHqRiwsQxXqpEaVjYfd0aCVBWcP0yu";
        String description = "5QsXWV9TLri4Rd0AdxP0JNLXGgxlrT9DrSOW0rF3pMGcAX8VIs";
        String isReaded = "bRIT0s0p2NP4cG4Qox70DyEvNqw0qr02YFeutkmFYKCXKJSt7y";
        String status = "XCrpHhfDN8X9XdHYX4xPED7c47aJSOOWt0PM0HG6cgUTbaXmhD";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("applicationUUID", applicationUUID);
        mapFieldsRequestMock.put("userUUID", userUUID);
        mapFieldsRequestMock.put("type", type);
        mapFieldsRequestMock.put("title", title);
        mapFieldsRequestMock.put("description", description);
        mapFieldsRequestMock.put("isReaded", isReaded);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Notifier> notifiersFromRepository = new ArrayList<>();
        notifiersFromRepository.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        notifiersFromRepository.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        notifiersFromRepository.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        notifiersFromRepository.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());

        List<NotifierDTO> notifiersFiltered = notifiersFromRepository
                .stream()
                .map(m->notifierService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageNotifierItems", notifiersFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<Notifier> pagedResponse =
                new PageImpl<>(notifiersFromRepository,
                        pageableMock,
                        notifiersFromRepository.size());

        Mockito.when(notifierRepositoryMock.findNotifierByFilter(pageableMock,
            id,
            applicationUUID,
            userUUID,
            type,
            title,
            description,
            isReaded,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = notifierService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<NotifierDTO> notifiersResult = (List<NotifierDTO>) result.get("pageNotifierItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, notifiersResult.size());
    }


    @Test
    public void showReturnListOfNotifierWhenAskedForFindAllByStatus() {
        // scenario
        List<Notifier> listOfNotifierModelMock = new ArrayList<>();
        listOfNotifierModelMock.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        listOfNotifierModelMock.add(NotifierModelBuilder.newNotifierModelTestBuilder().now());

        Mockito.when(notifierRepositoryMock.findAllByStatus("A")).thenReturn(listOfNotifierModelMock);

        // action
        List<NotifierDTO> listOfNotifiers = notifierService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfNotifiers.isEmpty());
        Assertions.assertEquals(2, listOfNotifiers.size());
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 55262L;
        Optional<Notifier> notifierNonExistentMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.findById(idMock)).thenReturn(notifierNonExistentMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowNotifierNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 21442L;
        Mockito.when(notifierRepositoryMock.findById(idMock))
                .thenThrow(new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                NOTIFIER_NOTFOUND_WITH_ID ));

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnNotifierDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 30260L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(
                NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(idMock)
                        .applicationUUID(UUID.fromString("7271a58e-6afc-4818-8103-e89329797b7f"))
                        .userUUID(UUID.fromString("5c0f444c-91b5-498f-8e1d-fd62db2bbdde"))
                        .type("VEAIYgfSmasBDoGOzSj3bKNzMnmGkOJu9wKgYUukbVjSSwC0Dq")
                        .title("CGpwowrVTfQSyn3LSc06WmuDWeXwdAn2T3MMvo4Lz1izNDJRyW")
                        .description("rH1tYSsaAKY9pqygVwmiFAfhhXpquKDvHfWdz8gulQ1MdpgoJo")
                        .isReaded("gbL7dCdhpw6NvYE1VNMiHTHuPsc6gKImyVmgSr5XG5ybkIAOoU")

                        .status("X")
                        .now()
        );
        Notifier notifierToSaveMock = notifierModelMock.orElse(null);
        Notifier notifierSavedMck = NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(50773L)
                        .applicationUUID(UUID.fromString("45853afa-3bb3-4225-8a58-3e2f4cd0cc9c"))
                        .userUUID(UUID.fromString("dd7ebc42-2d42-48f9-ba5a-8625840317e5"))
                        .type("iYQdTyVQW0Txz0YJTN8vwFY0ffL07EOi2656VQQQWJdnF1Nc0L")
                        .title("H7dakPBIILv5yEqBy0n40A05mOkfD0eK6B11TlncOr8XMmYdRI")
                        .description("0js8qcIWdlI9pBIPiatnwJ1XhMwV2gx0PdrI06TLABUONCiEdg")
                        .isReaded("Wc1rK0dhhEFBmw4rrBfODpoU4MC7NTgE9jKugbuOjKyKKU9CjU")

                        .status("A")
                        .now();
        Mockito.when(notifierRepositoryMock.findById(idMock)).thenReturn(notifierModelMock);
        Mockito.when(notifierRepositoryMock.save(notifierToSaveMock)).thenReturn(notifierSavedMck);

        // action
        NotifierDTO result = notifierService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchNotifierByAnyNonExistenceIdAndReturnNotifierNotFoundException() {
        // scenario
        Mockito.when(notifierRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()-> notifierService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchNotifierByIdAndReturnDTO() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(81202L)
                .applicationUUID(UUID.fromString("e4ad4c0e-d221-4357-957b-d2503e7acb55"))
                .userUUID(UUID.fromString("11fdbabd-6a2f-4bd8-a879-61548fd7c2be"))
                .type("l4jttEu20Rpe1oNjozW0wLy1HfXGDLHGHU3q2FUSwtDndVJara")
                .title("kqNceJqCMHXl0VjVGrPv71KmjX4LYYjOhWKGvB9fzgTS7vrRHQ")
                .description("pWdOoqxErnJTydE75BFjriBfr4s7UuASs89PUA86v9cHkwyP5Q")
                .isReaded("9V0RxOmLT1ET5pfmAkXexQQ1yT5UYeOIJvunmJnFglrLgF6F8W")

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(notifierRepositoryMock.findById(Mockito.anyLong())).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findById(1L);

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldDeleteNotifierByIdWithSucess() {
        // scenario
        Optional<Notifier> notifier = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().id(1L).now());
        Mockito.when(notifierRepositoryMock.findById(Mockito.anyLong())).thenReturn(notifier);

        // action
        notifierService.delete(1L);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceNotifierShouldReturnNotifierNotFoundException() {
        // scenario
        Mockito.when(notifierRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(
                NotifierNotFoundException.class, () -> notifierService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingNotifierWithSucess() {
        // scenario
        NotifierDTO notifierDTOMock = NotifierDTOBuilder.newNotifierDTOTestBuilder()
                .id(5410L)
                .applicationUUID(UUID.fromString("36d14a50-55ff-4d92-a6f7-08f0d4b6618a"))
                .userUUID(UUID.fromString("45ad740a-ef37-4165-93a1-a9c2e4360c75"))
                .type("0Jb8BCgHgnwgU97iKuMntyoA9yHhqo3Nx7q8XSXxfR8AH2LMbX")
                .title("MyKzL7bVGy08z0NT2fGTbgxa3gUn2eOOsrTtwhkTz6RSDzTP73")
                .description("RVhhkbM6cnAtKoGgU7TM20SjiR57LvKCKIUIt04KCh9uSyfgus")
                .isReaded("sUt2FWpe8zEHOViDzc0mCSSQTM0xikJXNNHKEiy6gmVgi8mavw")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Notifier notifierMock = NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(notifierDTOMock.getId())
                .applicationUUID(notifierDTOMock.getApplicationUUID())
                .userUUID(notifierDTOMock.getUserUUID())
                .type(notifierDTOMock.getType())
                .title(notifierDTOMock.getTitle())
                .description(notifierDTOMock.getDescription())
                .isReaded(notifierDTOMock.getIsReaded())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Notifier notifierSavedMock = NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(notifierDTOMock.getId())
                .applicationUUID(notifierDTOMock.getApplicationUUID())
                .userUUID(notifierDTOMock.getUserUUID())
                .type(notifierDTOMock.getType())
                .title(notifierDTOMock.getTitle())
                .description(notifierDTOMock.getDescription())
                .isReaded(notifierDTOMock.getIsReaded())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(notifierRepositoryMock.save(notifierMock)).thenReturn(notifierSavedMock);

        // action
        NotifierDTO notifierSaved = notifierService.salvar(notifierDTOMock);

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class, notifierSaved);
        Assertions.assertNotNull(notifierSaved.getId());
    }

    @Test
    public void ShouldSaveNewNotifierWithSucess() {
        // scenario
        NotifierDTO notifierDTOMock = NotifierDTOBuilder.newNotifierDTOTestBuilder()
                .id(null)
                .applicationUUID(UUID.fromString("68fc7b74-aee3-4374-b191-f08680a58e39"))
                .userUUID(UUID.fromString("03d8230e-3777-40fe-bf2d-1b57a9352c7e"))
                .type("eQpOrv6XY6t4jihHHUiYJrzNf0qWpYUS4MasyKbx56BpRQP3pF")
                .title("5yrjBVfXYWBk1WPapsNJnK3YEE2FFCfUoBOmOKvz4ddrzwbK5r")
                .description("txKR0Lez0Q4Qxr8aihxPbKuDtp5QhY0nt2RTxtAS3cVhIyxUqb")
                .isReaded("fW4KbeYOQxShHsGnGK1OYp8NeSeR9ToN6iPFINraaXj6OCeqwQ")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Notifier notifierModelMock = NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(null)
                .applicationUUID(notifierDTOMock.getApplicationUUID())
                .userUUID(notifierDTOMock.getUserUUID())
                .type(notifierDTOMock.getType())
                .title(notifierDTOMock.getTitle())
                .description(notifierDTOMock.getDescription())
                .isReaded(notifierDTOMock.getIsReaded())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Notifier notifierSavedMock = NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(501L)
                .applicationUUID(notifierDTOMock.getApplicationUUID())
                .userUUID(notifierDTOMock.getUserUUID())
                .type(notifierDTOMock.getType())
                .title(notifierDTOMock.getTitle())
                .description(notifierDTOMock.getDescription())
                .isReaded(notifierDTOMock.getIsReaded())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(notifierRepositoryMock.save(notifierModelMock)).thenReturn(notifierSavedMock);

        // action
        NotifierDTO notifierSaved = notifierService.salvar(notifierDTOMock);

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class, notifierSaved);
        Assertions.assertNotNull(notifierSaved.getId());
        Assertions.assertEquals("P",notifierSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapNotifierDTOMock = new HashMap<>();
        mapNotifierDTOMock.put(NotifierConstantes.APPLICATIONUUID,UUID.fromString("d136bc96-4da6-4d2a-b78e-d62bd29273f0"));
        mapNotifierDTOMock.put(NotifierConstantes.USERUUID,UUID.fromString("479c6e16-f1fc-4f30-bc0a-14a2d5f86e50"));
        mapNotifierDTOMock.put(NotifierConstantes.TYPE,"qhx6FHWnUehX3K0OLNVoJz7VVplTgJK1kpYTp4p60AdMiiorAw");
        mapNotifierDTOMock.put(NotifierConstantes.TITLE,"SJsCNtQUb1y5AqohYl0pivxHS02NX8y7eXV7c7QoyMB8GRq0na");
        mapNotifierDTOMock.put(NotifierConstantes.DESCRIPTION,"774QqbiJe4lXiu10hUVy8qEo9qHMI2y2NF7tMfwOUhVgwn4oeV");
        mapNotifierDTOMock.put(NotifierConstantes.ISREADED,"AdSV4eq8DMvUQ88aUiQN4OGYJ8ln63qsLLsuszNi3XxaRGmATH");
        mapNotifierDTOMock.put(NotifierConstantes.STATUS,"dOK9IUSuhLdvch8mxhWqKXnDkw54DMmo9YfbxX4gq3aTxqYVTz");


        Optional<Notifier> notifierModelMock = Optional.ofNullable(
                NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(14710L)
                        .applicationUUID(UUID.fromString("f3c1a9ec-d212-44d5-aa9a-1992ba107c73"))
                        .userUUID(UUID.fromString("05106691-856f-49fb-ad03-96854bf7e619"))
                        .type("dNC6gy0IY1U5r7hkslhESaUliqDyV0b4OG1MSreE01iy20YoSh")
                        .title("t9dF0EAzrX9yfKeSR4IW9HjHa4Iq30WQ8AtmxIuizvAUmVgUQP")
                        .description("oNdagN3IO0SHoYvlTDJIfjMDva5cMbgz4FVeo7JcY2P1chwhee")
                        .isReaded("AnmClf058FUu006S5WRKYkTbu35YXL0qLxCvepGfbedKiXhqMW")
                        .status("cTC31fb96w5XR61UuDTv6K0WK80hI05Ok0OVa7esYC5fCCJa7p")

                        .now()
        );

        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        boolean executed = notifierService.partialUpdate(1L, mapNotifierDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapNotifierDTOMock = new HashMap<>();
        mapNotifierDTOMock.put(NotifierConstantes.APPLICATIONUUID,UUID.fromString("1e30a8de-26f2-4432-9f36-a7b41cb58cda"));
        mapNotifierDTOMock.put(NotifierConstantes.USERUUID,UUID.fromString("829190c2-4a81-4090-9355-60da91e6af6b"));
        mapNotifierDTOMock.put(NotifierConstantes.TYPE,"a367RgnRcSxFsF5SHXM7Mz3GsP646LF8alzkIzRLqE2aEcJIcY");
        mapNotifierDTOMock.put(NotifierConstantes.TITLE,"zg6bAs4nPDLpKpiJdJhOHvQv3i0ot4or0yOsFbrkIrlfmwFtPJ");
        mapNotifierDTOMock.put(NotifierConstantes.DESCRIPTION,"p0Nkvnra6XAxt8yxa5A50qM99MbCRspuzM0OQajh1LuIhcavjJ");
        mapNotifierDTOMock.put(NotifierConstantes.ISREADED,"qYVFS5KnDA1mqBkEvSaCBs40L6xtt2tcYUsSDJzvBsGNnG8Cvo");
        mapNotifierDTOMock.put(NotifierConstantes.STATUS,"n0hs84aB0ICcBTQM9BfEX9NYzdnpzPedi4sc8yiiuBIizuqbuD");


        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.partialUpdate(1L, mapNotifierDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("Notifier não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByIdAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByIdAndStatus(3808L, "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByIdAndStatus(3808L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByApplicationUUIDAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByApplicationUUIDAndStatus(UUID.fromString("8b2c75fd-d4ff-4c40-95dc-c47c0ac5f80a"), "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByApplicationUUIDAndStatus(UUID.fromString("8b2c75fd-d4ff-4c40-95dc-c47c0ac5f80a"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByUserUUIDAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByUserUUIDAndStatus(UUID.fromString("4465194e-23f0-4854-8438-9a0ac24bdcc3"), "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByUserUUIDAndStatus(UUID.fromString("4465194e-23f0-4854-8438-9a0ac24bdcc3"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByTypeAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByTypeAndStatus("HpHnp2R6ckT54YEB1EP0GiQ8xoP0EmTAIwS3kGC0hCdjltSF92", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByTypeAndStatus("HpHnp2R6ckT54YEB1EP0GiQ8xoP0EmTAIwS3kGC0hCdjltSF92", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByTitleAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByTitleAndStatus("sb3A572Rl0LX5YRfjGMFRkC3rVloPfYe2utRIWx65mfaIBbRWw", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByTitleAndStatus("sb3A572Rl0LX5YRfjGMFRkC3rVloPfYe2utRIWx65mfaIBbRWw", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByDescriptionAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByDescriptionAndStatus("MOehdy6qhRey35YGcxMC2nRJJ482l3fMGqekk1OyLyoOIeTpx9", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByDescriptionAndStatus("MOehdy6qhRey35YGcxMC2nRJJ482l3fMGqekk1OyLyoOIeTpx9", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByIsReadedAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByIsReadedAndStatus("fIDwQOpc2m6jjry40N3eEV1pzVdm7sJHIAcfCcsTN84TqGbgRU", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByIsReadedAndStatus("fIDwQOpc2m6jjry40N3eEV1pzVdm7sJHIAcfCcsTN84TqGbgRU", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByDateCreatedAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByDateUpdatedAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByIdAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByIdAndStatus(50237L, "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByIdAndStatus(50237L, "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierIdAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByIdAndStatus(50237L, "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByIdAndStatus(50237L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByApplicationUUIDAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByApplicationUUIDAndStatus(UUID.fromString("52882ba3-1671-4c5d-b0d3-eec7f2403452"), "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByApplicationUUIDAndStatus(UUID.fromString("52882ba3-1671-4c5d-b0d3-eec7f2403452"), "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierApplicationUUIDAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByApplicationUUIDAndStatus(UUID.fromString("52882ba3-1671-4c5d-b0d3-eec7f2403452"), "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByApplicationUUIDAndStatus(UUID.fromString("52882ba3-1671-4c5d-b0d3-eec7f2403452"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_APPLICATIONUUID));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByUserUUIDAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByUserUUIDAndStatus(UUID.fromString("dac43bf7-95d4-4b3c-bae3-d33a60012b8d"), "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUserUUIDAndStatus(UUID.fromString("dac43bf7-95d4-4b3c-bae3-d33a60012b8d"), "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierUserUUIDAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByUserUUIDAndStatus(UUID.fromString("dac43bf7-95d4-4b3c-bae3-d33a60012b8d"), "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUserUUIDAndStatus(UUID.fromString("dac43bf7-95d4-4b3c-bae3-d33a60012b8d"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_USERUUID));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByTypeAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByTypeAndStatus("9aWLCohlPWiks8JcThIbsynGqotYJBJA0MXKlIgfmloJeergwI", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByTypeAndStatus("9aWLCohlPWiks8JcThIbsynGqotYJBJA0MXKlIgfmloJeergwI", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierTypeAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByTypeAndStatus("9aWLCohlPWiks8JcThIbsynGqotYJBJA0MXKlIgfmloJeergwI", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByTypeAndStatus("9aWLCohlPWiks8JcThIbsynGqotYJBJA0MXKlIgfmloJeergwI", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_TYPE));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByTitleAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByTitleAndStatus("FMxaJARRAkCMLPNOuIYiTf9XCToEAq5NEX8vUpukUo2cKsTxk4", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByTitleAndStatus("FMxaJARRAkCMLPNOuIYiTf9XCToEAq5NEX8vUpukUo2cKsTxk4", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierTitleAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByTitleAndStatus("FMxaJARRAkCMLPNOuIYiTf9XCToEAq5NEX8vUpukUo2cKsTxk4", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByTitleAndStatus("FMxaJARRAkCMLPNOuIYiTf9XCToEAq5NEX8vUpukUo2cKsTxk4", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_TITLE));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByDescriptionAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByDescriptionAndStatus("0oPPxSvjM9ujA3by0VHHezMGrgL2imKgltPau4oTIAwnzSw4dY", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByDescriptionAndStatus("0oPPxSvjM9ujA3by0VHHezMGrgL2imKgltPau4oTIAwnzSw4dY", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierDescriptionAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByDescriptionAndStatus("0oPPxSvjM9ujA3by0VHHezMGrgL2imKgltPau4oTIAwnzSw4dY", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByDescriptionAndStatus("0oPPxSvjM9ujA3by0VHHezMGrgL2imKgltPau4oTIAwnzSw4dY", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_DESCRIPTION));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByIsReadedAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByIsReadedAndStatus("hYcCtdxjcrmURXehAkTxcSmDEVv2Vs0K0tzIfRyT4swYucR0fl", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByIsReadedAndStatus("hYcCtdxjcrmURXehAkTxcSmDEVv2Vs0K0tzIfRyT4swYucR0fl", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierIsReadedAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByIsReadedAndStatus("hYcCtdxjcrmURXehAkTxcSmDEVv2Vs0K0tzIfRyT4swYucR0fl", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByIsReadedAndStatus("hYcCtdxjcrmURXehAkTxcSmDEVv2Vs0K0tzIfRyT4swYucR0fl", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ISREADED));
    }

    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingApplicationUUIDById() {
        // scenario
        UUID applicationUUIDUpdateMock = UUID.fromString("c15dce65-6290-4a21-a3b5-a74a0aabfef1");
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateApplicationUUIDById(420L, applicationUUIDUpdateMock);

        // action
        notifierService.updateApplicationUUIDById(420L, applicationUUIDUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateApplicationUUIDById(420L, applicationUUIDUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingUserUUIDById() {
        // scenario
        UUID userUUIDUpdateMock = UUID.fromString("c8b8e465-01b3-4db0-8091-10bedc78645a");
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateUserUUIDById(420L, userUUIDUpdateMock);

        // action
        notifierService.updateUserUUIDById(420L, userUUIDUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateUserUUIDById(420L, userUUIDUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingTypeById() {
        // scenario
        String typeUpdateMock = "0yLgcwEQqKlK8ha0r0aBkkdo8YyicNESAf0k3Emttj5kGavK07";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateTypeById(420L, typeUpdateMock);

        // action
        notifierService.updateTypeById(420L, typeUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateTypeById(420L, typeUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingTitleById() {
        // scenario
        String titleUpdateMock = "kTD4d3lUN3rxwIVGDk4JFwOph1ltG70vxJe9bR06E9O9l3TykN";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateTitleById(420L, titleUpdateMock);

        // action
        notifierService.updateTitleById(420L, titleUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateTitleById(420L, titleUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingDescriptionById() {
        // scenario
        String descriptionUpdateMock = "n1FL3g4wuLYDPRonMPHhSUcfulKSDjWIEmoQUGFhrL0nk5wGkL";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateDescriptionById(420L, descriptionUpdateMock);

        // action
        notifierService.updateDescriptionById(420L, descriptionUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateDescriptionById(420L, descriptionUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingIsReadedById() {
        // scenario
        String isReadedUpdateMock = "Y9sVU0tq9Jc7Qa0j1xTbfjCeQemXwjNLvVwW7Nr6Bju5lqmKoS";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateIsReadedById(420L, isReadedUpdateMock);

        // action
        notifierService.updateIsReadedById(420L, isReadedUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateIsReadedById(420L, isReadedUpdateMock);
    }



    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 13076L;
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 13076L;
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByApplicationUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID applicationUUIDMock = UUID.fromString("fbd8021f-eae4-477e-8d9f-3458dfa1c048");
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .applicationUUID(applicationUUIDMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByApplicationUUIDAndStatus(applicationUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByApplicationUUIDAndStatus(applicationUUIDMock);

        // validate
        Assertions.assertEquals(applicationUUIDMock, result.getApplicationUUID());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByApplicationUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID applicationUUIDMock = UUID.fromString("fbd8021f-eae4-477e-8d9f-3458dfa1c048");
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByApplicationUUIDAndStatus(applicationUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByApplicationUUIDAndStatus(applicationUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_APPLICATIONUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByUserUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID userUUIDMock = UUID.fromString("3275ab39-1893-4b31-95a2-65e2e26c5a30");
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .userUUID(userUUIDMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByUserUUIDAndStatus(userUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUserUUIDAndStatus(userUUIDMock);

        // validate
        Assertions.assertEquals(userUUIDMock, result.getUserUUID());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByUserUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID userUUIDMock = UUID.fromString("3275ab39-1893-4b31-95a2-65e2e26c5a30");
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByUserUUIDAndStatus(userUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUserUUIDAndStatus(userUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_USERUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByTypeAndStatusActiveAnonimous() {
        // scenario
        String typeMock = "4KKhCwN0XB6bcNX3tK85u1krq67fmVs9jRRGrJeCgIzmvgobRQ";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .type(typeMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByTypeAndStatus(typeMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByTypeAndStatus(typeMock);

        // validate
        Assertions.assertEquals(typeMock, result.getType());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByTypeAndStatusActiveAnonimous() {
        // scenario
        String typeMock = "4KKhCwN0XB6bcNX3tK85u1krq67fmVs9jRRGrJeCgIzmvgobRQ";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByTypeAndStatus(typeMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByTypeAndStatus(typeMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_TYPE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "rXfb0g75LXTKmTLLBt0YTixxTWp79Sz30zKbaO4vHQBMWl3ijm";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .title(titleMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByTitleAndStatus(titleMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByTitleAndStatus(titleMock);

        // validate
        Assertions.assertEquals(titleMock, result.getTitle());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "rXfb0g75LXTKmTLLBt0YTixxTWp79Sz30zKbaO4vHQBMWl3ijm";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByTitleAndStatus(titleMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByTitleAndStatus(titleMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_TITLE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByDescriptionAndStatusActiveAnonimous() {
        // scenario
        String descriptionMock = "Fejg8UmWzGDxq4Kbgjevzqw4d10CvoG7GhEoi0C2VB5wHBDrIN";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .description(descriptionMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByDescriptionAndStatus(descriptionMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByDescriptionAndStatus(descriptionMock);

        // validate
        Assertions.assertEquals(descriptionMock, result.getDescription());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByDescriptionAndStatusActiveAnonimous() {
        // scenario
        String descriptionMock = "Fejg8UmWzGDxq4Kbgjevzqw4d10CvoG7GhEoi0C2VB5wHBDrIN";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByDescriptionAndStatus(descriptionMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByDescriptionAndStatus(descriptionMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_DESCRIPTION));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByIsReadedAndStatusActiveAnonimous() {
        // scenario
        String isReadedMock = "NDyKeH0FrnVLzgLU8ffh0G7sCmxuCf35MfKLcjl3mwR1ts9NWR";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .isReaded(isReadedMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByIsReadedAndStatus(isReadedMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByIsReadedAndStatus(isReadedMock);

        // validate
        Assertions.assertEquals(isReadedMock, result.getIsReaded());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByIsReadedAndStatusActiveAnonimous() {
        // scenario
        String isReadedMock = "NDyKeH0FrnVLzgLU8ffh0G7sCmxuCf35MfKLcjl3mwR1ts9NWR";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByIsReadedAndStatus(isReadedMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByIsReadedAndStatus(isReadedMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ISREADED));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

