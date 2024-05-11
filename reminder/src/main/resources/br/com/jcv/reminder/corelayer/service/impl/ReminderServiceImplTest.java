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
import br.com.jcv.reminder.corelayer.builder.ReminderDTOBuilder;
import br.com.jcv.reminder.corelayer.builder.ReminderModelBuilder;
import br.com.jcv.reminder.corelayer.dto.ReminderDTO;
import br.com.jcv.reminder.corelayer.exception.ReminderNotFoundException;
import br.com.jcv.reminder.corelayer.model.Reminder;
import br.com.jcv.reminder.corelayer.repository.ReminderRepository;
import br.com.jcv.reminder.corelayer.service.ReminderService;
import br.com.jcv.reminder.corelayer.constantes.ReminderConstantes;
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
public class ReminderServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String REMINDER_NOTFOUND_WITH_ID = "Reminder não encontrada com id = ";
    public static final String REMINDER_NOTFOUND_WITH_IDLIST = "Reminder não encontrada com idList = ";
    public static final String REMINDER_NOTFOUND_WITH_TITLE = "Reminder não encontrada com title = ";
    public static final String REMINDER_NOTFOUND_WITH_NOTE = "Reminder não encontrada com note = ";
    public static final String REMINDER_NOTFOUND_WITH_TAGS = "Reminder não encontrada com tags = ";
    public static final String REMINDER_NOTFOUND_WITH_FULLURLIMAGE = "Reminder não encontrada com fullUrlImage = ";
    public static final String REMINDER_NOTFOUND_WITH_URL = "Reminder não encontrada com url = ";
    public static final String REMINDER_NOTFOUND_WITH_PRIORITY = "Reminder não encontrada com priority = ";
    public static final String REMINDER_NOTFOUND_WITH_FLAG = "Reminder não encontrada com flag = ";
    public static final String REMINDER_NOTFOUND_WITH_DUEDATE = "Reminder não encontrada com dueDate = ";
    public static final String REMINDER_NOTFOUND_WITH_STATUS = "Reminder não encontrada com status = ";
    public static final String REMINDER_NOTFOUND_WITH_DATECREATED = "Reminder não encontrada com dateCreated = ";
    public static final String REMINDER_NOTFOUND_WITH_DATEUPDATED = "Reminder não encontrada com dateUpdated = ";


    @Mock
    private ReminderRepository reminderRepositoryMock;

    @InjectMocks
    private ReminderService reminderService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        reminderService = new ReminderServiceImpl();
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
    public void shouldReturnListOfReminderWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 25161L;
        Long idList = 81400L;
        String title = "ICppxDCQDhQGM6bwRN1qi4YA6YmURDD49RHM603crLUAyNfASP";
        String note = "X5W2r0HN4CK84KYnG50YQkRhQRhE0kO0iCtnFrFMVRcbeq8isu";
        String tags = "1mj0evbsDCLOYk0lvvr4ecoDJ022n1oxLRUeY20Of02D9iTWrn";
        String fullUrlImage = "iT5840zQTxJrQUdOeNo3S9raC56R5CUu8s0m6S5F6oHs93lFtz";
        String url = "70pG6or4gAAa5vKbuNNn6nH7qJOCg3AUzBTvYOd0topgfNT4sM";
        String priority = "rTpOMhTjUCDgjB4EFJbac7FzO7BxQAy77Y3DPFV6R0uTwjkhNX";
        String flag = "0G7iRWVrfgyJxLYPNhxIlhI6s2wV0SlbAzHJlvoC4UO0xm1voC";
        String dueDate = "2025-10-07";
        String status = "eDI4f4I2yCn0gjkHbj9B0lnnnbSWX07DXnCjDmYquAKJOglvLr";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idList", idList);
        mapFieldsRequestMock.put("title", title);
        mapFieldsRequestMock.put("note", note);
        mapFieldsRequestMock.put("tags", tags);
        mapFieldsRequestMock.put("fullUrlImage", fullUrlImage);
        mapFieldsRequestMock.put("url", url);
        mapFieldsRequestMock.put("priority", priority);
        mapFieldsRequestMock.put("flag", flag);
        mapFieldsRequestMock.put("dueDate", dueDate);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Reminder> remindersFromRepository = new ArrayList<>();
        remindersFromRepository.add(ReminderModelBuilder.newReminderModelTestBuilder().now());
        remindersFromRepository.add(ReminderModelBuilder.newReminderModelTestBuilder().now());
        remindersFromRepository.add(ReminderModelBuilder.newReminderModelTestBuilder().now());
        remindersFromRepository.add(ReminderModelBuilder.newReminderModelTestBuilder().now());

        Mockito.when(reminderRepositoryMock.findReminderByFilter(
            id,
            idList,
            title,
            note,
            tags,
            fullUrlImage,
            url,
            priority,
            flag,
            dueDate,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(remindersFromRepository);

        // action
        List<ReminderDTO> result = reminderService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithReminderListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 24350L;
        Long idList = 22207L;
        String title = "I0p6d5v9NhkXsGOJJJuRiCiOE4uNdkugIt6jtOdpAw9poMG5q4";
        String note = "vyKh3h0YGtAVGEBju90Vw3qRGiLlXhbCsdBHxF3Fk3cUOw02id";
        String tags = "WvOfCmox47W14L6o6oVzyLdfovzAJuxa7rmauuCe7S9avcdk3s";
        String fullUrlImage = "4g2pm4XymgNjwH5ATfmgX2RLV52kRXMsnW0Ur4PEfwp8twwjU0";
        String url = "azopnD9yH7mPDMjlv2bzVF4eRhmQzCyz00QB1Uh0aledtK4Ik7";
        String priority = "HqrYADCd5bpLIPgO1OaUvhFMSQCQ66nG4sz4n9B8QjnmJucybj";
        String flag = "9O7qlc02JiivbzuWcjY1Hjze0uzixG7FNV2Lcq2tk3MyrCUxP6";
        String dueDate = "2025-10-07";
        String status = "aCBXqJKSYXqyOYv9gCvtgvYHFfVp9KjVeuEruklVXY22QlSqLD";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idList", idList);
        mapFieldsRequestMock.put("title", title);
        mapFieldsRequestMock.put("note", note);
        mapFieldsRequestMock.put("tags", tags);
        mapFieldsRequestMock.put("fullUrlImage", fullUrlImage);
        mapFieldsRequestMock.put("url", url);
        mapFieldsRequestMock.put("priority", priority);
        mapFieldsRequestMock.put("flag", flag);
        mapFieldsRequestMock.put("dueDate", dueDate);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Reminder> remindersFromRepository = new ArrayList<>();
        remindersFromRepository.add(ReminderModelBuilder.newReminderModelTestBuilder().now());
        remindersFromRepository.add(ReminderModelBuilder.newReminderModelTestBuilder().now());
        remindersFromRepository.add(ReminderModelBuilder.newReminderModelTestBuilder().now());
        remindersFromRepository.add(ReminderModelBuilder.newReminderModelTestBuilder().now());

        List<ReminderDTO> remindersFiltered = remindersFromRepository
                .stream()
                .map(m->reminderService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageReminderItems", remindersFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<Reminder> pagedResponse =
                new PageImpl<>(remindersFromRepository,
                        pageableMock,
                        remindersFromRepository.size());

        Mockito.when(reminderRepositoryMock.findReminderByFilter(pageableMock,
            id,
            idList,
            title,
            note,
            tags,
            fullUrlImage,
            url,
            priority,
            flag,
            dueDate,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = reminderService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<ReminderDTO> remindersResult = (List<ReminderDTO>) result.get("pageReminderItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, remindersResult.size());
    }


    @Test
    public void showReturnListOfReminderWhenAskedForFindAllByStatus() {
        // scenario
        List<Reminder> listOfReminderModelMock = new ArrayList<>();
        listOfReminderModelMock.add(ReminderModelBuilder.newReminderModelTestBuilder().now());
        listOfReminderModelMock.add(ReminderModelBuilder.newReminderModelTestBuilder().now());

        Mockito.when(reminderRepositoryMock.findAllByStatus("A")).thenReturn(listOfReminderModelMock);

        // action
        List<ReminderDTO> listOfReminders = reminderService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfReminders.isEmpty());
        Assertions.assertEquals(2, listOfReminders.size());
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 64434L;
        Optional<Reminder> reminderNonExistentMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.findById(idMock)).thenReturn(reminderNonExistentMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowReminderNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 554L;
        Mockito.when(reminderRepositoryMock.findById(idMock))
                .thenThrow(new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                REMINDER_NOTFOUND_WITH_ID ));

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnReminderDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 70524L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(
                ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(idMock)
                        .idList(6115L)
                        .title("c3uwkETKzLIulUScQiBVKOUEPBVr6vFWAxBVF017hE5oxGPf1w")
                        .note("y7or11LbFIoSdIYhf0gT2ShayGMh674gBNPTJSLsoQLYN846CH")
                        .tags("GidrCK0P7XeunzQUkrMOHz2zRbVViaPAYHbvS6gaJQYqm4Bk8J")
                        .fullUrlImage("AIR8lIAB38VNQtwv05Rp3cM53SNPxtPJ9EwzadUm6VPS7FxskR")
                        .url("Wky4pk6mDokHg2FvlQvjc9sG0rKNKMKnGph6dLnCdwAUINUBpH")
                        .priority("uoj9ARpnLupDIL09tNWbWJFlhEn4PTA2C0lOxOzh0QSAXJXG5s")
                        .flag("sIBb6NNUA12InodERYyQLvbm9zeiNMD4J4agAxRtL6tyAtgFeT")
                        .dueDate(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                        .status("X")
                        .now()
        );
        Reminder reminderToSaveMock = reminderModelMock.orElse(null);
        Reminder reminderSavedMck = ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(55827L)
                        .idList(1103L)
                        .title("hVON8d9Y70YBOKBXy0IfgXxNzQD7bQJqYGEQNldg4K5jdxkFal")
                        .note("CCnx2YQW1d50PTJBpuCAg6V6p2xUgV5i0TQq2KzGyHrerNVjOq")
                        .tags("JJGL5Vs9kLl94ekkeYr5qPiwJrGqxBBXXuRegh3Dvshp1AqHkI")
                        .fullUrlImage("JQ6ej3G8azoRzM6RA5I10oCGx8Q443WUdvjazO0V9MNg42bEO0")
                        .url("uBr4OrI2YGuPHaMmikTxO7ebSQDKc6OGLaUFsDE50Q0UJ6Sbm4")
                        .priority("KmdUerWdtPXBRW908A2L3fN3ypIchYIvBOfxmhrqyaz130NAvA")
                        .flag("wNMWen6F9Fy2zamQbBIIjhUhakdtHe6ovar5dtw0knIUdiDFwN")
                        .dueDate(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                        .status("A")
                        .now();
        Mockito.when(reminderRepositoryMock.findById(idMock)).thenReturn(reminderModelMock);
        Mockito.when(reminderRepositoryMock.save(reminderToSaveMock)).thenReturn(reminderSavedMck);

        // action
        ReminderDTO result = reminderService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchReminderByAnyNonExistenceIdAndReturnReminderNotFoundException() {
        // scenario
        Mockito.when(reminderRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()-> reminderService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchReminderByIdAndReturnDTO() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .id(11784L)
                .idList(823L)
                .title("yt2e9jx5WMR3icxVpiUmBXjrwxsxzr1nIjWRB482YaGBIVQzGe")
                .note("b4Au7nUSxmjn1Sdj2LyF2pvD08VWIBw9SsXsorUqQGOuKI8D7I")
                .tags("KQA9TI6SM2uwC1kMV5Pagtvh2mYPYDMyXv0QX2NXUJv0eGAMES")
                .fullUrlImage("YzqYnJ6aBYoAtv90u5k8Y6M7032olWSmygnNn9Rtf2LCoc1yCG")
                .url("plWorUAmV5lBV45ttPICAAFHPCuH00q0Uuaa9ml6czmHivnimp")
                .priority("88yyrYSLP4mkDXWCKLp2YIBIDYVBlH08GfUS3FQS6Ml04kdq8X")
                .flag("si7VkSu6eYUMui4nejNweFJtbFOs19y5pX1dy7Jiju2RJIf8wt")
                .dueDate(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(reminderRepositoryMock.findById(Mockito.anyLong())).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findById(1L);

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldDeleteReminderByIdWithSucess() {
        // scenario
        Optional<Reminder> reminder = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().id(1L).now());
        Mockito.when(reminderRepositoryMock.findById(Mockito.anyLong())).thenReturn(reminder);

        // action
        reminderService.delete(1L);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceReminderShouldReturnReminderNotFoundException() {
        // scenario
        Mockito.when(reminderRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(
                ReminderNotFoundException.class, () -> reminderService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingReminderWithSucess() {
        // scenario
        ReminderDTO reminderDTOMock = ReminderDTOBuilder.newReminderDTOTestBuilder()
                .id(55007L)
                .idList(6672L)
                .title("F12ivLDJuFahTIG8r9P2Bev1lbCATgavxbyubnNdIRHHRXLz91")
                .note("z9d0F0YsK7Bl9CX4NKmbSgnEpqrRvx1S6M1iMM4O8LYCk7QmsD")
                .tags("KoYBBHK2HOKzTqbaK70vWY8xQWM24x6Gb955BzVjzPzADqCggY")
                .fullUrlImage("Sql9TO60kMrKsK4i2u2918ry7tczugmITUsJU8fRSauAgrXffz")
                .url("Gio7FEolngyGUqqtdH9np6jWdtIG04pjK7CwhuoWHnoJO31SQF")
                .priority("h8fdulFr8s8qiYXyGW2yC6E5SqKb1XKicL0PAJkR0xU1Aeapd4")
                .flag("lxgSSqQjMX0ucU9j9YyfOlRH3QCif2TgGT4D6f90N1mH6S65S3")
                .dueDate(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Reminder reminderMock = ReminderModelBuilder.newReminderModelTestBuilder()
                .id(reminderDTOMock.getId())
                .idList(reminderDTOMock.getIdList())
                .title(reminderDTOMock.getTitle())
                .note(reminderDTOMock.getNote())
                .tags(reminderDTOMock.getTags())
                .fullUrlImage(reminderDTOMock.getFullUrlImage())
                .url(reminderDTOMock.getUrl())
                .priority(reminderDTOMock.getPriority())
                .flag(reminderDTOMock.getFlag())
                .dueDate(reminderDTOMock.getDueDate())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Reminder reminderSavedMock = ReminderModelBuilder.newReminderModelTestBuilder()
                .id(reminderDTOMock.getId())
                .idList(reminderDTOMock.getIdList())
                .title(reminderDTOMock.getTitle())
                .note(reminderDTOMock.getNote())
                .tags(reminderDTOMock.getTags())
                .fullUrlImage(reminderDTOMock.getFullUrlImage())
                .url(reminderDTOMock.getUrl())
                .priority(reminderDTOMock.getPriority())
                .flag(reminderDTOMock.getFlag())
                .dueDate(reminderDTOMock.getDueDate())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(reminderRepositoryMock.save(reminderMock)).thenReturn(reminderSavedMock);

        // action
        ReminderDTO reminderSaved = reminderService.salvar(reminderDTOMock);

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class, reminderSaved);
        Assertions.assertNotNull(reminderSaved.getId());
    }

    @Test
    public void ShouldSaveNewReminderWithSucess() {
        // scenario
        ReminderDTO reminderDTOMock = ReminderDTOBuilder.newReminderDTOTestBuilder()
                .id(null)
                .idList(78668L)
                .title("v6I3z8ssENxeSwc7HWgmLjq4GmINHvIGkFoU3PcoEBG5KPgQMb")
                .note("Eo4hdIrFmAw4bTwlWSJFmxdlY27iTExVIMJ28EqlPFSvRV8MlT")
                .tags("M0bcDokyWV2FX544vRGviscXVzcj7R21XqFClwsLGHetb1ldkU")
                .fullUrlImage("fFgMexFqDmAyITE0QLddCQCo1IlBLMm5AM6nUWA5hHItHRuzy1")
                .url("DFTb6zhY618gVywH7uaEFlRcoaUUX1WRViPu8GjebelgUfzf3Y")
                .priority("KTwo714NuWdfSqAqmy7QhB105C4SqmEt4drduI0arFTUEdV6T7")
                .flag("xUYq3rSG50pXIceJVUSkM1KigBWIwDT2URxYyUuOLD9RccFPHx")
                .dueDate(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Reminder reminderModelMock = ReminderModelBuilder.newReminderModelTestBuilder()
                .id(null)
                .idList(reminderDTOMock.getIdList())
                .title(reminderDTOMock.getTitle())
                .note(reminderDTOMock.getNote())
                .tags(reminderDTOMock.getTags())
                .fullUrlImage(reminderDTOMock.getFullUrlImage())
                .url(reminderDTOMock.getUrl())
                .priority(reminderDTOMock.getPriority())
                .flag(reminderDTOMock.getFlag())
                .dueDate(reminderDTOMock.getDueDate())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Reminder reminderSavedMock = ReminderModelBuilder.newReminderModelTestBuilder()
                .id(501L)
                .idList(reminderDTOMock.getIdList())
                .title(reminderDTOMock.getTitle())
                .note(reminderDTOMock.getNote())
                .tags(reminderDTOMock.getTags())
                .fullUrlImage(reminderDTOMock.getFullUrlImage())
                .url(reminderDTOMock.getUrl())
                .priority(reminderDTOMock.getPriority())
                .flag(reminderDTOMock.getFlag())
                .dueDate(reminderDTOMock.getDueDate())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(reminderRepositoryMock.save(reminderModelMock)).thenReturn(reminderSavedMock);

        // action
        ReminderDTO reminderSaved = reminderService.salvar(reminderDTOMock);

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class, reminderSaved);
        Assertions.assertNotNull(reminderSaved.getId());
        Assertions.assertEquals("P",reminderSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapReminderDTOMock = new HashMap<>();
        mapReminderDTOMock.put(ReminderConstantes.IDLIST,3278L);
        mapReminderDTOMock.put(ReminderConstantes.TITLE,"Xo0vnU56HfYrlc6x37xWpdpgMh5Gnh5oeDDuR7j6YI20hYlwsM");
        mapReminderDTOMock.put(ReminderConstantes.NOTE,"FvRJqSMg7lyHuocViuU9TuTCzYn8baNLeK0iG4l6xecbYRoEu0");
        mapReminderDTOMock.put(ReminderConstantes.TAGS,"Vk43EXzI0G9biYAYWTcMgYVj2MIev3tM9zTeIpAc3FSiX3FdMh");
        mapReminderDTOMock.put(ReminderConstantes.FULLURLIMAGE,"Xq370c2inWFEBpSsCX0TsBWivrHY1s19hHJ7RMsdaTWqt65Czj");
        mapReminderDTOMock.put(ReminderConstantes.URL,"gjCD0zlisJQkitEDLregACUSt11izfxisOJzLKLbxu5kbsm60c");
        mapReminderDTOMock.put(ReminderConstantes.PRIORITY,"Ajm8fzAa3vexGItwQbfxcY5oL9VU0o3KfLYcoEg0eKFPtfMzVI");
        mapReminderDTOMock.put(ReminderConstantes.FLAG,"xWLiPQAbGsTSLaJLuJ7RNFikDoo9MCitqO8jmtODMEnNyvwvir");
        mapReminderDTOMock.put(ReminderConstantes.DUEDATE,Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        mapReminderDTOMock.put(ReminderConstantes.STATUS,"03I4FrofOwOacf0FSrdKeoN9usucNtPsssp6DduwHvwXFKNqOy");


        Optional<Reminder> reminderModelMock = Optional.ofNullable(
                ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(41116L)
                        .idList(482L)
                        .title("Qh83NiLgmcsY5ehPh81rnqg0so9MV2bFVWK4QE6AxPiIx2x1tl")
                        .note("wPi9mFvlUK2bbEdFTjDtSEJm35K4nkLPlTDUOF8R6NwfPy8D7f")
                        .tags("zkYPCNP4sKFbk00GFzl72xtLYcATVsDCCJetG3p3WJGpsNbjBL")
                        .fullUrlImage("CIeCM4esTLJq2VVyMHfhjf4OzTbGzFeI56bHI5at4kGSk6vbsv")
                        .url("G0FhmDxFEOxDnqpYWeNWnlNVMDJ7BViptKQU90GHfySenh0kxp")
                        .priority("ARunPvK0vvgXEMdFU0v85fATCxcuDStKt9Tt86oVbEmLxjjQr6")
                        .flag("RS8Xf488NUH70CItS1QqMvoAoSaLlfXK9teLYXtvjXb0fX0LTI")
                        .dueDate(Date.from(LocalDate.of(2027,7,25).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        .status("lRItxS3ckvrfJ54hjrseiOpqhIfcFvFAUF0FKvifcEwB96D1pj")

                        .now()
        );

        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        boolean executed = reminderService.partialUpdate(1L, mapReminderDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapReminderDTOMock = new HashMap<>();
        mapReminderDTOMock.put(ReminderConstantes.IDLIST,32874L);
        mapReminderDTOMock.put(ReminderConstantes.TITLE,"5BFYrtOi2gdzkDEikd63VG3f0niUxtfFsSJwQdcsSQrmt91dhq");
        mapReminderDTOMock.put(ReminderConstantes.NOTE,"7HilpVjynFqBUEnd8RoxiXTYwqvkEcRW7BrkxO3naPCPElx0Y3");
        mapReminderDTOMock.put(ReminderConstantes.TAGS,"T02ocxPBL4yXlnPvOpN1tnwyAi09HJcgbLbrSoiTdj14GoOHmx");
        mapReminderDTOMock.put(ReminderConstantes.FULLURLIMAGE,"8zEnVgetEFMm950f6U1qeWrfUIQiVIukiMMaymv96e5ntYuowE");
        mapReminderDTOMock.put(ReminderConstantes.URL,"huvgKDvHB02DDGt1DYiAQyddABRKeNW4D1XneykdSw32qIoOUK");
        mapReminderDTOMock.put(ReminderConstantes.PRIORITY,"V168q5Tf4g20g1TySbH2LCRtS0DquuAEFMjCVR8bKGwoT0pJo0");
        mapReminderDTOMock.put(ReminderConstantes.FLAG,"DG7FqaV97OhzjJHVfUqSRX32jawAOTNDq00TyWq0g8MFf98Iz9");
        mapReminderDTOMock.put(ReminderConstantes.DUEDATE,Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        mapReminderDTOMock.put(ReminderConstantes.STATUS,"oq9uDN0hbS9e9G9NQwDUM4mjzQwkK3BvyQp0bL2Da7GGcjsrP7");


        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.partialUpdate(1L, mapReminderDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("Reminder não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnReminderListWhenFindAllReminderByIdAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByIdAndStatus(47030L, "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByIdAndStatus(47030L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByIdListAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByIdListAndStatus(43042L, "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByIdListAndStatus(43042L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByTitleAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByTitleAndStatus("d3vkatevfwbw2NHyJrUkrfcAJ0wfwGtvrjrbcHJzcin1kxqSE1", "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByTitleAndStatus("d3vkatevfwbw2NHyJrUkrfcAJ0wfwGtvrjrbcHJzcin1kxqSE1", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByNoteAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByNoteAndStatus("q4R1L32vuNDB6o0gqEF0W2JRLuRo8YVn2eUEsiH9hxiWKhXu8x", "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByNoteAndStatus("q4R1L32vuNDB6o0gqEF0W2JRLuRo8YVn2eUEsiH9hxiWKhXu8x", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByTagsAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByTagsAndStatus("BBSPO4vsn0JpllH6g0R0Hl8xotKVO1CBFjjwb6ahjt5MpsoWxm", "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByTagsAndStatus("BBSPO4vsn0JpllH6g0R0Hl8xotKVO1CBFjjwb6ahjt5MpsoWxm", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByFullUrlImageAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByFullUrlImageAndStatus("U3hrMnRBdtvczWLEQJu7rHsTthNz25x9Aagx3ozzVeTOb901uA", "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByFullUrlImageAndStatus("U3hrMnRBdtvczWLEQJu7rHsTthNz25x9Aagx3ozzVeTOb901uA", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByUrlAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByUrlAndStatus("WKgigY9JNUzXXb1ueSuQvStruvxENwUbIGQsbqlDoBgRmtH0W0", "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByUrlAndStatus("WKgigY9JNUzXXb1ueSuQvStruvxENwUbIGQsbqlDoBgRmtH0W0", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByPriorityAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByPriorityAndStatus("Q8Q9V3EcdoVcJLTyWt0aodpTNyPpKadxrhVvzQaWFFzSCBijhw", "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByPriorityAndStatus("Q8Q9V3EcdoVcJLTyWt0aodpTNyPpKadxrhVvzQaWFFzSCBijhw", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByFlagAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByFlagAndStatus("bkdH5BBTjKv0mQTNeX0XqSAvxblVl46bvGwFH21YiSS6l42SAL", "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByFlagAndStatus("bkdH5BBTjKv0mQTNeX0XqSAvxblVl46bvGwFH21YiSS6l42SAL", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByDueDateAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByDueDateAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByDueDateAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByDateCreatedAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReminderListWhenFindAllReminderByDateUpdatedAndStatus() {
        // scenario
        List<Reminder> reminders = Arrays.asList(
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now(),
            ReminderModelBuilder.newReminderModelTestBuilder().now()
        );

        Mockito.when(reminderRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reminders);

        // action
        List<ReminderDTO> result = reminderService.findAllReminderByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByIdAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByIdAndStatus(75100L, "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByIdAndStatus(75100L, "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderIdAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByIdAndStatus(75100L, "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByIdAndStatus(75100L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByIdListAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByIdListAndStatus(10100L, "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByIdListAndStatus(10100L, "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderIdListAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByIdListAndStatus(10100L, "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByIdListAndStatus(10100L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_IDLIST));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByTitleAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByTitleAndStatus("scTIuWlb20otFB3vBzKkvkpm2Q0CjjKY7XYI8n1u2ob9TMnozt", "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByTitleAndStatus("scTIuWlb20otFB3vBzKkvkpm2Q0CjjKY7XYI8n1u2ob9TMnozt", "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderTitleAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByTitleAndStatus("scTIuWlb20otFB3vBzKkvkpm2Q0CjjKY7XYI8n1u2ob9TMnozt", "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByTitleAndStatus("scTIuWlb20otFB3vBzKkvkpm2Q0CjjKY7XYI8n1u2ob9TMnozt", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_TITLE));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByNoteAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByNoteAndStatus("ClsKG2YcCjmowmUwF9k2iewJ6oDgXMdTaKj5c4nlKM7eqS69Ra", "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByNoteAndStatus("ClsKG2YcCjmowmUwF9k2iewJ6oDgXMdTaKj5c4nlKM7eqS69Ra", "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderNoteAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByNoteAndStatus("ClsKG2YcCjmowmUwF9k2iewJ6oDgXMdTaKj5c4nlKM7eqS69Ra", "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByNoteAndStatus("ClsKG2YcCjmowmUwF9k2iewJ6oDgXMdTaKj5c4nlKM7eqS69Ra", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_NOTE));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByTagsAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByTagsAndStatus("LUjs611vjhoB0y7vpakNmVV0hydrOgb6DLSmOifgmep3W6hjl3", "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByTagsAndStatus("LUjs611vjhoB0y7vpakNmVV0hydrOgb6DLSmOifgmep3W6hjl3", "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderTagsAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByTagsAndStatus("LUjs611vjhoB0y7vpakNmVV0hydrOgb6DLSmOifgmep3W6hjl3", "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByTagsAndStatus("LUjs611vjhoB0y7vpakNmVV0hydrOgb6DLSmOifgmep3W6hjl3", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_TAGS));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByFullUrlImageAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByFullUrlImageAndStatus("4Gtylh795fE0KuPItuMVqzTyct0XCgFHpre9UvxSHVONnCrT71", "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByFullUrlImageAndStatus("4Gtylh795fE0KuPItuMVqzTyct0XCgFHpre9UvxSHVONnCrT71", "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderFullUrlImageAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByFullUrlImageAndStatus("4Gtylh795fE0KuPItuMVqzTyct0XCgFHpre9UvxSHVONnCrT71", "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByFullUrlImageAndStatus("4Gtylh795fE0KuPItuMVqzTyct0XCgFHpre9UvxSHVONnCrT71", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_FULLURLIMAGE));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByUrlAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByUrlAndStatus("1Qucef8Wvdbn6n5rALnU5vJcNAGLXsYfxiwyHMJFYX4KVFDWrW", "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByUrlAndStatus("1Qucef8Wvdbn6n5rALnU5vJcNAGLXsYfxiwyHMJFYX4KVFDWrW", "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderUrlAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByUrlAndStatus("1Qucef8Wvdbn6n5rALnU5vJcNAGLXsYfxiwyHMJFYX4KVFDWrW", "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByUrlAndStatus("1Qucef8Wvdbn6n5rALnU5vJcNAGLXsYfxiwyHMJFYX4KVFDWrW", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_URL));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByPriorityAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByPriorityAndStatus("Sy4vLOKzNCa7qrsiOwbMrxz9qdVpO0l9OvToLAjnQTYq52lIuD", "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByPriorityAndStatus("Sy4vLOKzNCa7qrsiOwbMrxz9qdVpO0l9OvToLAjnQTYq52lIuD", "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderPriorityAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByPriorityAndStatus("Sy4vLOKzNCa7qrsiOwbMrxz9qdVpO0l9OvToLAjnQTYq52lIuD", "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByPriorityAndStatus("Sy4vLOKzNCa7qrsiOwbMrxz9qdVpO0l9OvToLAjnQTYq52lIuD", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_PRIORITY));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByFlagAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByFlagAndStatus("NS3WrFmwJfwUg40N6igJ771ENTzP84j8RnrGV76jIz7rcwytXI", "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByFlagAndStatus("NS3WrFmwJfwUg40N6igJ771ENTzP84j8RnrGV76jIz7rcwytXI", "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderFlagAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByFlagAndStatus("NS3WrFmwJfwUg40N6igJ771ENTzP84j8RnrGV76jIz7rcwytXI", "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByFlagAndStatus("NS3WrFmwJfwUg40N6igJ771ENTzP84j8RnrGV76jIz7rcwytXI", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_FLAG));
    }
    @Test
    public void shouldReturnExistentReminderDTOWhenFindReminderByDueDateAndStatus() {
        // scenario
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder().now());
        Mockito.when(reminderRepositoryMock.loadMaxIdByDueDateAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(1L);
        Mockito.when(reminderRepositoryMock.findById(1L)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByDueDateAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(ReminderDTO.class,result);
    }
    @Test
    public void shouldReturnReminderNotFoundExceptionWhenNonExistenceReminderDueDateAndStatus() {
        // scenario
        Mockito.when(reminderRepositoryMock.loadMaxIdByDueDateAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(0L);
        Mockito.when(reminderRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByDueDateAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_DUEDATE));
    }

    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingIdListById() {
        // scenario
        Long idListUpdateMock = 76722L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updateIdListById(420L, idListUpdateMock);

        // action
        reminderService.updateIdListById(420L, idListUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updateIdListById(420L, idListUpdateMock);
    }
    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingTitleById() {
        // scenario
        String titleUpdateMock = "VHwJ9ESc3VbXssmaTVPMM9YpKEVg0xtp2QuvNgqq0NfWj8uk3k";
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updateTitleById(420L, titleUpdateMock);

        // action
        reminderService.updateTitleById(420L, titleUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updateTitleById(420L, titleUpdateMock);
    }
    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingNoteById() {
        // scenario
        String noteUpdateMock = "Oo67ti3A9UQ12vsI01zhdoesCRhRHMsGfYEnykS7eQVaO62oxw";
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updateNoteById(420L, noteUpdateMock);

        // action
        reminderService.updateNoteById(420L, noteUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updateNoteById(420L, noteUpdateMock);
    }
    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingTagsById() {
        // scenario
        String tagsUpdateMock = "iepye1uGslMh0wTI7hNCvqHC7s5u4CyWxCYEJc8NVUBMOJv93K";
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updateTagsById(420L, tagsUpdateMock);

        // action
        reminderService.updateTagsById(420L, tagsUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updateTagsById(420L, tagsUpdateMock);
    }
    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingFullUrlImageById() {
        // scenario
        String fullUrlImageUpdateMock = "qAa0aICLOkXMMHXN5AGEQ5CQjHR0EGA2A5BPFmz1UeJBgjD0gK";
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updateFullUrlImageById(420L, fullUrlImageUpdateMock);

        // action
        reminderService.updateFullUrlImageById(420L, fullUrlImageUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updateFullUrlImageById(420L, fullUrlImageUpdateMock);
    }
    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingUrlById() {
        // scenario
        String urlUpdateMock = "rmuijkUVCLPqKCzzlnlPUKojFsmSw5cx0KkdX2t7q0Xv4KRDQT";
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updateUrlById(420L, urlUpdateMock);

        // action
        reminderService.updateUrlById(420L, urlUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updateUrlById(420L, urlUpdateMock);
    }
    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingPriorityById() {
        // scenario
        String priorityUpdateMock = "11LxDPRYXjx9CSad60v0AmmMNfzUSU7IK803P0jRbRz9tqijQi";
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updatePriorityById(420L, priorityUpdateMock);

        // action
        reminderService.updatePriorityById(420L, priorityUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updatePriorityById(420L, priorityUpdateMock);
    }
    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingFlagById() {
        // scenario
        String flagUpdateMock = "2dGpABAqFCjJwnlTaHcWCqyEcXVUqfawHGtBqzbO6OgsN0QLN0";
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updateFlagById(420L, flagUpdateMock);

        // action
        reminderService.updateFlagById(420L, flagUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updateFlagById(420L, flagUpdateMock);
    }
    @Test
    public void shouldReturnReminderDTOWhenUpdateExistingDueDateById() {
        // scenario
        Date dueDateUpdateMock = Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reminderRepositoryMock.findById(420L)).thenReturn(reminderModelMock);
        Mockito.doNothing().when(reminderRepositoryMock).updateDueDateById(420L, dueDateUpdateMock);

        // action
        reminderService.updateDueDateById(420L, dueDateUpdateMock);

        // validate
        Mockito.verify(reminderRepositoryMock,Mockito.times(1)).updateDueDateById(420L, dueDateUpdateMock);
    }



    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 60425L;
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 60425L;
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByIdListAndStatusActiveAnonimous() {
        // scenario
        Long idListMock = 76087L;
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .idList(idListMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByIdListAndStatus(idListMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByIdListAndStatus(idListMock);

        // validate
        Assertions.assertEquals(idListMock, result.getIdList());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByIdListAndStatusActiveAnonimous() {
        // scenario
        Long idListMock = 76087L;
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByIdListAndStatus(idListMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByIdListAndStatus(idListMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_IDLIST));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "ta5GuaHoGQtVVmLg7jbaVmNN97wmTpEHPymiPADC1IGnMOWlDX";
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .title(titleMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByTitleAndStatus(titleMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByTitleAndStatus(titleMock);

        // validate
        Assertions.assertEquals(titleMock, result.getTitle());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "ta5GuaHoGQtVVmLg7jbaVmNN97wmTpEHPymiPADC1IGnMOWlDX";
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByTitleAndStatus(titleMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByTitleAndStatus(titleMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_TITLE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByNoteAndStatusActiveAnonimous() {
        // scenario
        String noteMock = "H9P1URy832MW1u1yXYk4Vb5WDNNgURpsaC69SOK2Il4vu0WYvC";
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .note(noteMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByNoteAndStatus(noteMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByNoteAndStatus(noteMock);

        // validate
        Assertions.assertEquals(noteMock, result.getNote());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByNoteAndStatusActiveAnonimous() {
        // scenario
        String noteMock = "H9P1URy832MW1u1yXYk4Vb5WDNNgURpsaC69SOK2Il4vu0WYvC";
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByNoteAndStatus(noteMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByNoteAndStatus(noteMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_NOTE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByTagsAndStatusActiveAnonimous() {
        // scenario
        String tagsMock = "FHnOPdg3TvWP5pXvMXB5auDjRW4d9GMvCDoL7tKOLnCvQ4AuE5";
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .tags(tagsMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByTagsAndStatus(tagsMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByTagsAndStatus(tagsMock);

        // validate
        Assertions.assertEquals(tagsMock, result.getTags());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByTagsAndStatusActiveAnonimous() {
        // scenario
        String tagsMock = "FHnOPdg3TvWP5pXvMXB5auDjRW4d9GMvCDoL7tKOLnCvQ4AuE5";
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByTagsAndStatus(tagsMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByTagsAndStatus(tagsMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_TAGS));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByFullUrlImageAndStatusActiveAnonimous() {
        // scenario
        String fullUrlImageMock = "GeKrRGST3mUOFUpwCrm50QlRLm8nr9YyXeDnj7gLTFYQMtuMDY";
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .fullUrlImage(fullUrlImageMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByFullUrlImageAndStatus(fullUrlImageMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByFullUrlImageAndStatus(fullUrlImageMock);

        // validate
        Assertions.assertEquals(fullUrlImageMock, result.getFullUrlImage());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByFullUrlImageAndStatusActiveAnonimous() {
        // scenario
        String fullUrlImageMock = "GeKrRGST3mUOFUpwCrm50QlRLm8nr9YyXeDnj7gLTFYQMtuMDY";
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByFullUrlImageAndStatus(fullUrlImageMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByFullUrlImageAndStatus(fullUrlImageMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_FULLURLIMAGE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByUrlAndStatusActiveAnonimous() {
        // scenario
        String urlMock = "r9D6K05S81jnhcV2QknRbz2vhaqRuSCT45836K7Q8Ia91L0guR";
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .url(urlMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByUrlAndStatus(urlMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByUrlAndStatus(urlMock);

        // validate
        Assertions.assertEquals(urlMock, result.getUrl());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByUrlAndStatusActiveAnonimous() {
        // scenario
        String urlMock = "r9D6K05S81jnhcV2QknRbz2vhaqRuSCT45836K7Q8Ia91L0guR";
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByUrlAndStatus(urlMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByUrlAndStatus(urlMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_URL));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByPriorityAndStatusActiveAnonimous() {
        // scenario
        String priorityMock = "RBLktwNhweP4JSVlJgQdPyDk7exgF6q0qvKyxhiat2JD4yo2vN";
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .priority(priorityMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByPriorityAndStatus(priorityMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByPriorityAndStatus(priorityMock);

        // validate
        Assertions.assertEquals(priorityMock, result.getPriority());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByPriorityAndStatusActiveAnonimous() {
        // scenario
        String priorityMock = "RBLktwNhweP4JSVlJgQdPyDk7exgF6q0qvKyxhiat2JD4yo2vN";
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByPriorityAndStatus(priorityMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByPriorityAndStatus(priorityMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_PRIORITY));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByFlagAndStatusActiveAnonimous() {
        // scenario
        String flagMock = "VcFqI0rQaO6KDHvPuSBc0OE6OOKHHjVhwmWo0eFO35NnbPRn86";
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .flag(flagMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByFlagAndStatus(flagMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByFlagAndStatus(flagMock);

        // validate
        Assertions.assertEquals(flagMock, result.getFlag());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByFlagAndStatusActiveAnonimous() {
        // scenario
        String flagMock = "VcFqI0rQaO6KDHvPuSBc0OE6OOKHHjVhwmWo0eFO35NnbPRn86";
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByFlagAndStatus(flagMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByFlagAndStatus(flagMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_FLAG));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReminderDTOWhenFindReminderByDueDateAndStatusActiveAnonimous() {
        // scenario
        Date dueDateMock = Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Long maxIdMock = 1972L;
        Optional<Reminder> reminderModelMock = Optional.ofNullable(ReminderModelBuilder.newReminderModelTestBuilder()
                .dueDate(dueDateMock)
                .now()
        );
        Mockito.when(reminderRepositoryMock.loadMaxIdByDueDateAndStatus(dueDateMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reminderRepositoryMock.findById(maxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderDTO result = reminderService.findReminderByDueDateAndStatus(dueDateMock);

        // validate
        Assertions.assertEquals(dueDateMock, result.getDueDate());

    }
    @Test
    public void showReturnReminderNotFoundExceptionWhenNonExistenceFindReminderByDueDateAndStatusActiveAnonimous() {
        // scenario
        Date dueDateMock = Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Long noMaxIdMock = 0L;
        Optional<Reminder> reminderModelMock = Optional.empty();
        Mockito.when(reminderRepositoryMock.loadMaxIdByDueDateAndStatus(dueDateMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reminderRepositoryMock.findById(noMaxIdMock)).thenReturn(reminderModelMock);

        // action
        ReminderNotFoundException exception = Assertions.assertThrows(ReminderNotFoundException.class,
                ()->reminderService.findReminderByDueDateAndStatus(dueDateMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REMINDER_NOTFOUND_WITH_DUEDATE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

