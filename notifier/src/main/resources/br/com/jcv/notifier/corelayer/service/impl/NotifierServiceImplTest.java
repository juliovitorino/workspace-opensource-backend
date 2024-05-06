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

package br.com.jcv.notifier.corelayer.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.notifier.corelayer.builder.NotifierDTOBuilder;
import br.com.jcv.notifier.corelayer.builder.NotifierModelBuilder;
import br.com.jcv.notifier.corelayer.dto.NotifierDTO;
import br.com.jcv.notifier.corelayer.exception.NotifierNotFoundException;
import br.com.jcv.notifier.corelayer.model.Notifier;
import br.com.jcv.notifier.corelayer.repository.NotifierRepository;
import br.com.jcv.notifier.corelayer.service.NotifierService;
import br.com.jcv.notifier.corelayer.constantes.NotifierConstantes;
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
    public static final String NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALAPP = "Notifier não encontrada com uuidExternalApp = ";
    public static final String NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALUSER = "Notifier não encontrada com uuidExternalUser = ";
    public static final String NOTIFIER_NOTFOUND_WITH_TYPE = "Notifier não encontrada com type = ";
    public static final String NOTIFIER_NOTFOUND_WITH_KEY = "Notifier não encontrada com key = ";
    public static final String NOTIFIER_NOTFOUND_WITH_TITLE = "Notifier não encontrada com title = ";
    public static final String NOTIFIER_NOTFOUND_WITH_DESCRIPTION = "Notifier não encontrada com description = ";
    public static final String NOTIFIER_NOTFOUND_WITH_URLIMAGE = "Notifier não encontrada com urlImage = ";
    public static final String NOTIFIER_NOTFOUND_WITH_ICONCLASS = "Notifier não encontrada com iconClass = ";
    public static final String NOTIFIER_NOTFOUND_WITH_URLFOLLOW = "Notifier não encontrada com urlFollow = ";
    public static final String NOTIFIER_NOTFOUND_WITH_OBJECTFREE = "Notifier não encontrada com objectFree = ";
    public static final String NOTIFIER_NOTFOUND_WITH_SEENINDICATOR = "Notifier não encontrada com seenIndicator = ";
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
        Long id = 30008L;
        UUID uuidExternalApp = UUID.fromString("c0fdd57e-4635-41e7-aee1-c15bed3c338c");
        UUID uuidExternalUser = UUID.fromString("d538f498-168a-4e1d-a196-5fb12bf05f5c");
        String type = "907Stkburpg8BDRbieBjFhQxJusRSdpvDfQop7FPmHeR9IFWou";
        String key = "EgRpkIp7jWVQV1cY9gLHWXT8qKYA0uBUdwzGfw1CuDE8JH7Ye4";
        String title = "PSvNRQjA5T8MfF2BDOW4r0bwrJ0n09FtOVPofjWJlxn7J3Tp6i";
        String description = "K15LyN6bW4nH6AnMy8Pc364eHI4cn1nnH0HU07q2Hj45e0R0xB";
        String urlImage = "Y0GfpIQC1ywvsTP0Ermb5Tjrm71RL57HTAc7Xjmj4lLp4wdnkd";
        String iconClass = "KHSJzxi9toXtzeNNiG9TPxuBfFkEzWEiflLNzQPyDUUgf4bHcP";
        String urlFollow = "Wm2h1lJVpEfGVnvsjUoCYScneV1CleuH6GgRgP5hs8YYQkYyEJ";
        String objectFree = "kgWj8g82Pm4ESQijbybuWJPOrbFQ4TMEDQVyqvV4H6L12jra6s";
        String seenIndicator = "fY3INQEeveX1WoW22T3iaNLUd905Es1mFt6nzWh1ARSxoFw4Rf";
        String status = "Obn7IkDBaGV02LQeoga8zQ8H3eOfiAsIzYunaTFHdxXBW57PAy";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("uuidExternalApp", uuidExternalApp);
        mapFieldsRequestMock.put("uuidExternalUser", uuidExternalUser);
        mapFieldsRequestMock.put("type", type);
        mapFieldsRequestMock.put("key", key);
        mapFieldsRequestMock.put("title", title);
        mapFieldsRequestMock.put("description", description);
        mapFieldsRequestMock.put("urlImage", urlImage);
        mapFieldsRequestMock.put("iconClass", iconClass);
        mapFieldsRequestMock.put("urlFollow", urlFollow);
        mapFieldsRequestMock.put("objectFree", objectFree);
        mapFieldsRequestMock.put("seenIndicator", seenIndicator);
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
            uuidExternalApp,
            uuidExternalUser,
            type,
            key,
            title,
            description,
            urlImage,
            iconClass,
            urlFollow,
            objectFree,
            seenIndicator,
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
        Long id = 3357L;
        UUID uuidExternalApp = UUID.fromString("1c08d0b5-9954-4ae2-bcd0-5e4b9cb5191c");
        UUID uuidExternalUser = UUID.fromString("1b701272-a9fb-4618-a166-4a3172888668");
        String type = "lnejN5RJQtsUE1iBx3LHAC0q05imuvy0PE1F4AbgLJzol0RRYS";
        String key = "8Xvadqp2pOVfbVtq0JXwPGuLnuCX5KgCHg3KpsNHFbLE7pGCU0";
        String title = "uoaam6ac4LBNIK0fDtKdKQfzLRznoUdv2ml8MyoNHVb6YevfsL";
        String description = "nNIw5v2CMoKDSqwNtsabGkuqMglhIJ1MbyejqyMaXI5mAguQDO";
        String urlImage = "2bvk1OmmKAJzRmC6w7YATCPB2TdFtH29d8g17uSp8kGp9slQS3";
        String iconClass = "Y5AMJTfEWFRiKQBLTolTHGg2YblHVxA8pi3jSlXLWiNIqpVVwv";
        String urlFollow = "RzpvyOIrVlMgBSMUmGCOYDMghYmY3G9ymFIBuQ14FADnSzFwqd";
        String objectFree = "kRzsR9E8GNxGfvfLbefuk7TqLtUAnhO34sap6QjFodFxHpsSIt";
        String seenIndicator = "DHm9iTM0RwAtO5KM0pkyEBTCjNychr0pxsrsRHHRVbAFQJToJE";
        String status = "eR41t0Q8xiCx9q5lpKRxX1WiaiUCLuOK9dYdITS8yLEG8yXhrw";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("uuidExternalApp", uuidExternalApp);
        mapFieldsRequestMock.put("uuidExternalUser", uuidExternalUser);
        mapFieldsRequestMock.put("type", type);
        mapFieldsRequestMock.put("key", key);
        mapFieldsRequestMock.put("title", title);
        mapFieldsRequestMock.put("description", description);
        mapFieldsRequestMock.put("urlImage", urlImage);
        mapFieldsRequestMock.put("iconClass", iconClass);
        mapFieldsRequestMock.put("urlFollow", urlFollow);
        mapFieldsRequestMock.put("objectFree", objectFree);
        mapFieldsRequestMock.put("seenIndicator", seenIndicator);
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
            uuidExternalApp,
            uuidExternalUser,
            type,
            key,
            title,
            description,
            urlImage,
            iconClass,
            urlFollow,
            objectFree,
            seenIndicator,
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
        Long idMock = 50405L;
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
        Long idMock = 32340L;
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
        Long idMock = 60864L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(
                NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(idMock)
                        .uuidExternalApp(UUID.fromString("07bd1fe1-3484-47ab-9565-1dc441073c01"))
                        .uuidExternalUser(UUID.fromString("92e64299-cf30-42c8-be7f-1fb3d462ded3"))
                        .type("uELHnRHzVzsKpMSlj1cjh2IYn8evU1PwYS6naJxlg1bxorikuL")
                        .key("hh6WGSPjcvb8YBq1a909qDOYrV5Bel72GffGXEA3lfthNfWmq7")
                        .title("Ah7tBWO8LE3nT4e8EGEpzfISWiN3vJSiBeS8Cvf3KmLAwzO7k9")
                        .description("mrVmAx4q63lk200kMB3jdCqgPeeYE5usqxln7PnBEPIEs8mlp2")
                        .urlImage("4VaXfwX20d08nK0hgIQ4pCwfUY09eT05ETR7WpHKh1aEzGqcYA")
                        .iconClass("gA863WTsnkmAHkrg8HX0X7Lvu41soTvuO7j5UdWBKQMkbu0yGu")
                        .urlFollow("Gbpck5nPlxPG9zIMmjiBuYnpvqq4KdVa6qr7JvBOAs0HAxYmMh")
                        .objectFree("JaAfHulyzGBn0PEIziNGrxHHJ90hEAl0E1MmhecO90H0gi2vzb")
                        .seenIndicator("27608z1AXFGKqnJaaFsxBlBAmLX05yKkNfVTrPLJVHiBqgYObK")

                        .status("X")
                        .now()
        );
        Notifier notifierToSaveMock = notifierModelMock.orElse(null);
        Notifier notifierSavedMck = NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(5840L)
                        .uuidExternalApp(UUID.fromString("5403b652-ca89-41c3-a933-976ec2a70631"))
                        .uuidExternalUser(UUID.fromString("fba8c7f0-3fe7-4640-bef2-e0902a297ee5"))
                        .type("RRakNH2q2B9wz4nVe3BiEDXwxcOF05Jd3tU1uAHOqn1otsvPUB")
                        .key("oAoQj3lrOLu3MUtwI19z0oth94FomOnF1b0NnEn4NodpylPF4Q")
                        .title("wiabxYE0O6CLeijzGmXogR7z3JRDpSlw8ettA0h7MwYim9NBR0")
                        .description("rND02h03YFVGF0ttlF6a4YfN46LA7sd5bpb6pe1890I8stusN0")
                        .urlImage("YwV414TxY631QEdpFHitK5zbbwzSd4h5KBNaP3pkHSSBSXzURt")
                        .iconClass("l06DDkM4LrmDhU41pvtU8cVxPfpDOmXAQkEkb240PPcyW87sJy")
                        .urlFollow("JgTt2hkQ1gGQAsLfaMeFtdwM2d82WTmUNCRlIQ1t01HbD3Oxta")
                        .objectFree("oLLbaKwI6QzOwDva554XUr2rz4Jp9Bmm50QcvQJw8I0yVmAJWQ")
                        .seenIndicator("UUX1ocHU7z4qemeb5PcltVqoD1XtBfoOxzxgTnE9VMmmnb0fAl")

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
                .id(74530L)
                .uuidExternalApp(UUID.fromString("8288bd60-f7f1-4bf4-bbfb-2935d5968565"))
                .uuidExternalUser(UUID.fromString("14c6af40-2c7b-4649-8171-47c7b5326e8c"))
                .type("gLkIfeqigO3D4oQnJzTvYeQ9UoSstoVzBFRbcuL6WUhgA8F71B")
                .key("3oa1gyztkg0y3D2WKHpQBYQfSeeFvtgfBJ4yXvFa3N2IwVAaVj")
                .title("t0So5wOrTgVW0FgYGJQpz5udymI1PJ0FsA4LG4DigcJw6r5DqD")
                .description("hPTl75DtonEOI0IjxiJPjAorxNPXuuucG889Twu1yO1aiHSpul")
                .urlImage("UPoUMvsIfe86KdmGfiWDlhIrK3WQJj6vW2xNYcBxSo5FI36Uuv")
                .iconClass("zfvuTK615sbXEsMNfKYcfn9FFFivT27QESXgklIX2qPSl3WPUM")
                .urlFollow("ElVrVtcmM0C5JIHnhbfumLhv31G5NgB8i6uBpdUlgX5QB6r5lD")
                .objectFree("FCYWI0lmLcBtmWTJxmo3WdqU08x8PgqXdNY2lHJdiKdhsAqkKp")
                .seenIndicator("9MGv11tm1uQjepPcdOOibdkC0c82Hor8LJANDEK3x5elCp5dxi")

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
                .id(13056L)
                .uuidExternalApp(UUID.fromString("685b452d-1d57-4c86-b677-22a5fd4eb901"))
                .uuidExternalUser(UUID.fromString("9f0f6e18-29e9-43b2-abf3-38c736c0b060"))
                .type("2iTJaDSY4O0QIMod2TBOWhnhIOlA3Fp4QHi2TWWAR1vtarnMIz")
                .key("hydXiPbwLAH002yHuzF5gHSY9uEKIawG6vblE3FKP7QALWF1Mz")
                .title("77fvEPwiuVqXJl5nb2bhqp5d404uQmlWoXOjFuQC6UwJt8hnhN")
                .description("GhBO4VpseYaUwq5Rzmg9J7XsnqfdWTkX6GrIf2OxwhvYpsyiz0")
                .urlImage("gcAQ8YArgxnNt4Xgsstb15srBrTD6lvoaw206ClrSpOWnKjYp7")
                .iconClass("k3QUGLu0M0nK0932xI0qw0xDHjyJ83BQJRAWduEdj9hDIMNdJJ")
                .urlFollow("T2K07NTGbU0ks7LAH3rfKqQmL2CWC4B0klBSxBuAOwjdSjzW7t")
                .objectFree("AXEEKJJqfFC5X4sugwY1FtqN1MJrakm4XwTtFFwSJVxvcxlUF5")
                .seenIndicator("nHiR1UGCAVGuChq2NDSzJD7TdsoHGV9DQq3esqJiyFJvb3Uacf")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Notifier notifierMock = NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(notifierDTOMock.getId())
                .uuidExternalApp(notifierDTOMock.getUuidExternalApp())
                .uuidExternalUser(notifierDTOMock.getUuidExternalUser())
                .type(notifierDTOMock.getType())
                .key(notifierDTOMock.getKey())
                .title(notifierDTOMock.getTitle())
                .description(notifierDTOMock.getDescription())
                .urlImage(notifierDTOMock.getUrlImage())
                .iconClass(notifierDTOMock.getIconClass())
                .urlFollow(notifierDTOMock.getUrlFollow())
                .objectFree(notifierDTOMock.getObjectFree())
                .seenIndicator(notifierDTOMock.getSeenIndicator())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Notifier notifierSavedMock = NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(notifierDTOMock.getId())
                .uuidExternalApp(notifierDTOMock.getUuidExternalApp())
                .uuidExternalUser(notifierDTOMock.getUuidExternalUser())
                .type(notifierDTOMock.getType())
                .key(notifierDTOMock.getKey())
                .title(notifierDTOMock.getTitle())
                .description(notifierDTOMock.getDescription())
                .urlImage(notifierDTOMock.getUrlImage())
                .iconClass(notifierDTOMock.getIconClass())
                .urlFollow(notifierDTOMock.getUrlFollow())
                .objectFree(notifierDTOMock.getObjectFree())
                .seenIndicator(notifierDTOMock.getSeenIndicator())

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
                .uuidExternalApp(UUID.fromString("5aea6b04-a347-4461-91b5-c1d80ff9cab5"))
                .uuidExternalUser(UUID.fromString("44e0dccb-d348-4f76-8e9f-f83de2217976"))
                .type("g8lW5oMVYLOMx6hTyRI8iaiuq7RWbCqHk0NBd0TOsCLsEkz7WV")
                .key("vG0BPzkHFRGmjj0M8q74SBpUxK7OXl16adkDar6OicbFY9H8Dm")
                .title("PnDUK3ShtUb3QBoKkrzfiaXI8DzjY041TTummKaPzcAB2HtB9P")
                .description("DC2Oxn1NFtJfG5UTQhoW2wfWj3OW0cbSpP11kowq7il9QaIy37")
                .urlImage("joKOwi9LwhWjslujRyhhf9zzMaay1dm3Q5pE5pIM0VjfwIO7vv")
                .iconClass("S5SQ6sL9rAi3to9kwaYkDJ3xKBruamkuu95jd04Xhg3qQuCrgj")
                .urlFollow("uhXyO0guwyeWs0UkzRrn2DRwzUm9MtGygBUWA7OU7i8b1EtDK9")
                .objectFree("rYkCEhm805yo9MQUygwd7bkUXj5tVohjYrlbogO1uxnuOOKhi0")
                .seenIndicator("4VmK9w4OcMrOfHp7S2XEjF1s09oc5JmNUpmD9pJBPFJCKKMdDr")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Notifier notifierModelMock = NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(null)
                .uuidExternalApp(notifierDTOMock.getUuidExternalApp())
                .uuidExternalUser(notifierDTOMock.getUuidExternalUser())
                .type(notifierDTOMock.getType())
                .key(notifierDTOMock.getKey())
                .title(notifierDTOMock.getTitle())
                .description(notifierDTOMock.getDescription())
                .urlImage(notifierDTOMock.getUrlImage())
                .iconClass(notifierDTOMock.getIconClass())
                .urlFollow(notifierDTOMock.getUrlFollow())
                .objectFree(notifierDTOMock.getObjectFree())
                .seenIndicator(notifierDTOMock.getSeenIndicator())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Notifier notifierSavedMock = NotifierModelBuilder.newNotifierModelTestBuilder()
                .id(501L)
                .uuidExternalApp(notifierDTOMock.getUuidExternalApp())
                .uuidExternalUser(notifierDTOMock.getUuidExternalUser())
                .type(notifierDTOMock.getType())
                .key(notifierDTOMock.getKey())
                .title(notifierDTOMock.getTitle())
                .description(notifierDTOMock.getDescription())
                .urlImage(notifierDTOMock.getUrlImage())
                .iconClass(notifierDTOMock.getIconClass())
                .urlFollow(notifierDTOMock.getUrlFollow())
                .objectFree(notifierDTOMock.getObjectFree())
                .seenIndicator(notifierDTOMock.getSeenIndicator())

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
        mapNotifierDTOMock.put(NotifierConstantes.UUIDEXTERNALAPP,UUID.fromString("fde8fb87-dc37-4cee-af3a-cd190e88f85f"));
        mapNotifierDTOMock.put(NotifierConstantes.UUIDEXTERNALUSER,UUID.fromString("e815b87e-a9a0-4463-8660-7adb1c8ab810"));
        mapNotifierDTOMock.put(NotifierConstantes.TYPE,"7hSW8BeKLUMhCiS5qA1MklusGurUQVPAqFakedRTBF6NseQL4L");
        mapNotifierDTOMock.put(NotifierConstantes.KEY,"1wVinWL6hJpU0fcu8qo0hawH8IMu8gwXp6ODjzgbShDJeIqQtu");
        mapNotifierDTOMock.put(NotifierConstantes.TITLE,"pwoWXYdGkrUJekQBkcEBuqRoeIT7hq9dRJ83nPT0ub4EPV30By");
        mapNotifierDTOMock.put(NotifierConstantes.DESCRIPTION,"SgShM1F3CgwK3HdWhIf2fid2xBX8b0dYJ1zoPP83RuYaoPF0kz");
        mapNotifierDTOMock.put(NotifierConstantes.URLIMAGE,"AsBzifKSli9Gd3B71Fw0Coz99TrjAlh7tzsBaJ5U4H8x8If264");
        mapNotifierDTOMock.put(NotifierConstantes.ICONCLASS,"6FVPvBlEOR5zYrjpDp0JYv1HkLe3lyX9WFMWaQLjDe3qyhe8s8");
        mapNotifierDTOMock.put(NotifierConstantes.URLFOLLOW,"zAQBh5X0kKlYkD4Np3VxSpsICqmYGYeQOPIr1QdGGSc9w3PTU4");
        mapNotifierDTOMock.put(NotifierConstantes.OBJECTFREE,"fEfzeNj1vxsDvCwHWTYPO4ymcF20W4HwEucT04b2MlliX3jLbB");
        mapNotifierDTOMock.put(NotifierConstantes.SEENINDICATOR,"TOrs27VcPcrLEpfAO0ooD0KxAA2ek4w44RLTmzWcWoksX11TXL");
        mapNotifierDTOMock.put(NotifierConstantes.STATUS,"XU9wKVfmlACXWauPfKt05ICT8QqKrEP6nJLRfjox9nWgXtWYv9");


        Optional<Notifier> notifierModelMock = Optional.ofNullable(
                NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(67610L)
                        .uuidExternalApp(UUID.fromString("8af0b983-98bc-4afc-8f77-f2020b66a1a9"))
                        .uuidExternalUser(UUID.fromString("b675045d-97b1-4f56-ad0c-d556b8be5c7a"))
                        .type("vqtSJhGAIxeVhFi4LEDKokWTW5WN0p7Ox0PKFoPngoyfvbp94G")
                        .key("itXU1NXxb2oMkrj62nlADbAOAAfSWDm0dcp000VpYaNJQ5B0WQ")
                        .title("FD4r7JsI8N6wchbbMa95nv0EhJ84BAbgxIhEhnKyfTe3oaDi7n")
                        .description("2fXnXdD00IKqaIN1FtRe02MoeGqUTyVx3yc0xHlG0xYhEMRRdF")
                        .urlImage("BSeg7nWkLHLeCmuz776N9l6MFgL2IrvKWL0EWChxScu0Eexcvq")
                        .iconClass("8SUbxuDCCBCCjsUT1m4knIv5x0z670fIjE6TSGylYCObym8dfb")
                        .urlFollow("mzTN10LeAGzjMdkN2u2tGUwz09tQMDoyOrqG550OsCiroQYVpo")
                        .objectFree("tWhHy7eOWeVDvo4Iw4AtxtSD0JSBUbyKxzWKVRmUTILviy9zah")
                        .seenIndicator("NgSAP1dCgSM0N4cGzlaH8ba4lu0G9YxQ3HUfcMCX0iEjsaqKxq")
                        .status("Q88OoUQvhM1FBJucAOiTssH6nRvIyfWQdE9TWdmYkcujIx0XFE")

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
        mapNotifierDTOMock.put(NotifierConstantes.UUIDEXTERNALAPP,UUID.fromString("c713d314-ff04-4481-b476-c80855e93fde"));
        mapNotifierDTOMock.put(NotifierConstantes.UUIDEXTERNALUSER,UUID.fromString("a362009b-f6de-44cb-9be8-ab9c2639c82f"));
        mapNotifierDTOMock.put(NotifierConstantes.TYPE,"Jqt3x5jJB4Te6Lw04g4yhqayHsrbnDLqTNMa3fKje0XhANn01k");
        mapNotifierDTOMock.put(NotifierConstantes.KEY,"X4oEuFHuI0XpO9IDhiezIRjNEEX0ubFrU2enOVAOWkXSXKUG0f");
        mapNotifierDTOMock.put(NotifierConstantes.TITLE,"6sGB77GyYDnMi8KKTwO76OUecqcYQkR3NTzHnaHewvGAv2V0La");
        mapNotifierDTOMock.put(NotifierConstantes.DESCRIPTION,"7b2rCk0tX4PBF0R8KhjVlTRWWa3e8q9D6XpzXqp7cbM57HB5Up");
        mapNotifierDTOMock.put(NotifierConstantes.URLIMAGE,"rUhy5efC0YodDgu1H0gSVtIPk0SweS0mQzNp1bc4bKi5rjzHND");
        mapNotifierDTOMock.put(NotifierConstantes.ICONCLASS,"ohnPX7Tl0rxPCdf0QqEqVx03rS8o6xNnylLUdQh7r40Nk0Rg6e");
        mapNotifierDTOMock.put(NotifierConstantes.URLFOLLOW,"bqHaa0yeWgWILytz51UYedmj84EusE93w3b8WcJ0kgajtxnAaH");
        mapNotifierDTOMock.put(NotifierConstantes.OBJECTFREE,"g0a46eh9AYE6VSKPqT6Y9LdOoyil0dw1KlyQMTRrCnYK7XKdmr");
        mapNotifierDTOMock.put(NotifierConstantes.SEENINDICATOR,"ImVEXYXuRojaIBVnUT9yxhlwJApXlpgNKTVnLaQRonl3W945VG");
        mapNotifierDTOMock.put(NotifierConstantes.STATUS,"Ct3FfBj8oHsbswzz6IRiQWp5EyesDU8SxySUM0pRCf0X3X0mRM");


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

        Mockito.when(notifierRepositoryMock.findAllByIdAndStatus(52462L, "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByIdAndStatus(52462L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByUuidExternalAppAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByUuidExternalAppAndStatus(UUID.fromString("2adf20e8-22bd-4e47-8f03-6badff19614c"), "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByUuidExternalAppAndStatus(UUID.fromString("2adf20e8-22bd-4e47-8f03-6badff19614c"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByUuidExternalUserAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByUuidExternalUserAndStatus(UUID.fromString("81922a01-4174-4b9e-ba98-90d4cbee060e"), "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByUuidExternalUserAndStatus(UUID.fromString("81922a01-4174-4b9e-ba98-90d4cbee060e"), "A");

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

        Mockito.when(notifierRepositoryMock.findAllByTypeAndStatus("jCpLHGRIyWyVb5Aqm4TzmlsVuSf1ost4QLLdcaiPRfD5o4zQMq", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByTypeAndStatus("jCpLHGRIyWyVb5Aqm4TzmlsVuSf1ost4QLLdcaiPRfD5o4zQMq", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByKeyAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByKeyAndStatus("0RpNlRpDdPC0SBbxh7AIL8uOHtpQNBrVkPqmLBz5Ewz00kylVY", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByKeyAndStatus("0RpNlRpDdPC0SBbxh7AIL8uOHtpQNBrVkPqmLBz5Ewz00kylVY", "A");

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

        Mockito.when(notifierRepositoryMock.findAllByTitleAndStatus("x7enAxw0BbEuI0xav8haPhocDA9qJvPCRa540NKwgFIjY0RrEk", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByTitleAndStatus("x7enAxw0BbEuI0xav8haPhocDA9qJvPCRa540NKwgFIjY0RrEk", "A");

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

        Mockito.when(notifierRepositoryMock.findAllByDescriptionAndStatus("HwHD1KIMs05yhhikaYd52BGcuJohSqJpwzs01s86pPmN2pCJ4P", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByDescriptionAndStatus("HwHD1KIMs05yhhikaYd52BGcuJohSqJpwzs01s86pPmN2pCJ4P", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByUrlImageAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByUrlImageAndStatus("PxUlu80nrhytHIKpBz416HG9ypOt4amLNVespjMtvlRqupjn5N", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByUrlImageAndStatus("PxUlu80nrhytHIKpBz416HG9ypOt4amLNVespjMtvlRqupjn5N", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByIconClassAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByIconClassAndStatus("CAX0Vks4e8kITydLUJaklCAVzjJXCrq9gUdXIFATjUPwpmEQk7", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByIconClassAndStatus("CAX0Vks4e8kITydLUJaklCAVzjJXCrq9gUdXIFATjUPwpmEQk7", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByUrlFollowAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByUrlFollowAndStatus("i307jFzHtkOhfnvhFRh67jNcqtSms6q4UBc7uKwsMnGU0qYvUR", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByUrlFollowAndStatus("i307jFzHtkOhfnvhFRh67jNcqtSms6q4UBc7uKwsMnGU0qYvUR", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierByObjectFreeAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllByObjectFreeAndStatus("iBURDgo4liQXo0vgGT3iAxbHnX1FHL0qb6e7F8dhhlWey0K4Qc", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierByObjectFreeAndStatus("iBURDgo4liQXo0vgGT3iAxbHnX1FHL0qb6e7F8dhhlWey0K4Qc", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnNotifierListWhenFindAllNotifierBySeenIndicatorAndStatus() {
        // scenario
        List<Notifier> notifiers = Arrays.asList(
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now(),
            NotifierModelBuilder.newNotifierModelTestBuilder().now()
        );

        Mockito.when(notifierRepositoryMock.findAllBySeenIndicatorAndStatus("1L3Wy5o8UKKcqaRwNDPOrNArFCh0QGW2M5aHDJrsLUkNfSOXMq", "A")).thenReturn(notifiers);

        // action
        List<NotifierDTO> result = notifierService.findAllNotifierBySeenIndicatorAndStatus("1L3Wy5o8UKKcqaRwNDPOrNArFCh0QGW2M5aHDJrsLUkNfSOXMq", "A");

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
        Mockito.when(notifierRepositoryMock.loadMaxIdByIdAndStatus(7012L, "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByIdAndStatus(7012L, "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierIdAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByIdAndStatus(7012L, "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByIdAndStatus(7012L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByUuidExternalAppAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("69c4c02c-2ca1-436e-8bd9-2da675b54009"), "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUuidExternalAppAndStatus(UUID.fromString("69c4c02c-2ca1-436e-8bd9-2da675b54009"), "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierUuidExternalAppAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(UUID.fromString("69c4c02c-2ca1-436e-8bd9-2da675b54009"), "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUuidExternalAppAndStatus(UUID.fromString("69c4c02c-2ca1-436e-8bd9-2da675b54009"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALAPP));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByUuidExternalUserAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(UUID.fromString("11a8957a-15f8-4a53-b617-4fc89c893d9d"), "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUuidExternalUserAndStatus(UUID.fromString("11a8957a-15f8-4a53-b617-4fc89c893d9d"), "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierUuidExternalUserAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(UUID.fromString("11a8957a-15f8-4a53-b617-4fc89c893d9d"), "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUuidExternalUserAndStatus(UUID.fromString("11a8957a-15f8-4a53-b617-4fc89c893d9d"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALUSER));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByTypeAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByTypeAndStatus("obHSe0acigxJSDIz7ibLxgFBhnIw77utmhyDlhkRgwCzOBmdlS", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByTypeAndStatus("obHSe0acigxJSDIz7ibLxgFBhnIw77utmhyDlhkRgwCzOBmdlS", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierTypeAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByTypeAndStatus("obHSe0acigxJSDIz7ibLxgFBhnIw77utmhyDlhkRgwCzOBmdlS", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByTypeAndStatus("obHSe0acigxJSDIz7ibLxgFBhnIw77utmhyDlhkRgwCzOBmdlS", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_TYPE));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByKeyAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByKeyAndStatus("F5ApJt4oySKqI1MldUqEkk8opcfwbfntePPjt77qiMSFARjcWc", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByKeyAndStatus("F5ApJt4oySKqI1MldUqEkk8opcfwbfntePPjt77qiMSFARjcWc", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierKeyAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByKeyAndStatus("F5ApJt4oySKqI1MldUqEkk8opcfwbfntePPjt77qiMSFARjcWc", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByKeyAndStatus("F5ApJt4oySKqI1MldUqEkk8opcfwbfntePPjt77qiMSFARjcWc", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_KEY));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByTitleAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByTitleAndStatus("t5B6lyGzyNI4Nr8fqsorRhUDaWkaMVucfdadjTC1ghPi9rJS51", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByTitleAndStatus("t5B6lyGzyNI4Nr8fqsorRhUDaWkaMVucfdadjTC1ghPi9rJS51", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierTitleAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByTitleAndStatus("t5B6lyGzyNI4Nr8fqsorRhUDaWkaMVucfdadjTC1ghPi9rJS51", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByTitleAndStatus("t5B6lyGzyNI4Nr8fqsorRhUDaWkaMVucfdadjTC1ghPi9rJS51", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_TITLE));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByDescriptionAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByDescriptionAndStatus("4JwjtqAe7TlCOcGqjILGwRhehuPWD8Nmn6P9EiOe8cLGCRmW0j", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByDescriptionAndStatus("4JwjtqAe7TlCOcGqjILGwRhehuPWD8Nmn6P9EiOe8cLGCRmW0j", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierDescriptionAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByDescriptionAndStatus("4JwjtqAe7TlCOcGqjILGwRhehuPWD8Nmn6P9EiOe8cLGCRmW0j", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByDescriptionAndStatus("4JwjtqAe7TlCOcGqjILGwRhehuPWD8Nmn6P9EiOe8cLGCRmW0j", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_DESCRIPTION));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByUrlImageAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByUrlImageAndStatus("YsY2c81lAEcUyyEJ1wxnKcoG04Tk3cWRjP1lwyPraEH7LnPgjk", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUrlImageAndStatus("YsY2c81lAEcUyyEJ1wxnKcoG04Tk3cWRjP1lwyPraEH7LnPgjk", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierUrlImageAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByUrlImageAndStatus("YsY2c81lAEcUyyEJ1wxnKcoG04Tk3cWRjP1lwyPraEH7LnPgjk", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUrlImageAndStatus("YsY2c81lAEcUyyEJ1wxnKcoG04Tk3cWRjP1lwyPraEH7LnPgjk", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_URLIMAGE));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByIconClassAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByIconClassAndStatus("mas8NmPN87Wd4kwpWPid2kQOSeu33vIU7MC1VHaBUst624OVB5", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByIconClassAndStatus("mas8NmPN87Wd4kwpWPid2kQOSeu33vIU7MC1VHaBUst624OVB5", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierIconClassAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByIconClassAndStatus("mas8NmPN87Wd4kwpWPid2kQOSeu33vIU7MC1VHaBUst624OVB5", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByIconClassAndStatus("mas8NmPN87Wd4kwpWPid2kQOSeu33vIU7MC1VHaBUst624OVB5", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ICONCLASS));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByUrlFollowAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByUrlFollowAndStatus("ObUarz1w8yRXbQeVHImInFOAzJrxe7IH1JYNx0CpJPBRbBkrK0", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUrlFollowAndStatus("ObUarz1w8yRXbQeVHImInFOAzJrxe7IH1JYNx0CpJPBRbBkrK0", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierUrlFollowAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByUrlFollowAndStatus("ObUarz1w8yRXbQeVHImInFOAzJrxe7IH1JYNx0CpJPBRbBkrK0", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUrlFollowAndStatus("ObUarz1w8yRXbQeVHImInFOAzJrxe7IH1JYNx0CpJPBRbBkrK0", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_URLFOLLOW));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierByObjectFreeAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdByObjectFreeAndStatus("3PYD2c0s1DO4R1Qqe0iahaynDdGF9fjiLc1nIUP5zTNe5wz0mM", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByObjectFreeAndStatus("3PYD2c0s1DO4R1Qqe0iahaynDdGF9fjiLc1nIUP5zTNe5wz0mM", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierObjectFreeAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdByObjectFreeAndStatus("3PYD2c0s1DO4R1Qqe0iahaynDdGF9fjiLc1nIUP5zTNe5wz0mM", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByObjectFreeAndStatus("3PYD2c0s1DO4R1Qqe0iahaynDdGF9fjiLc1nIUP5zTNe5wz0mM", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_OBJECTFREE));
    }
    @Test
    public void shouldReturnExistentNotifierDTOWhenFindNotifierBySeenIndicatorAndStatus() {
        // scenario
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder().now());
        Mockito.when(notifierRepositoryMock.loadMaxIdBySeenIndicatorAndStatus("Gz1uPnRUXiR27TC8ydcNcULcANXEKYf0lmNjMeJW3G5tjP7O4i", "A")).thenReturn(1L);
        Mockito.when(notifierRepositoryMock.findById(1L)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierBySeenIndicatorAndStatus("Gz1uPnRUXiR27TC8ydcNcULcANXEKYf0lmNjMeJW3G5tjP7O4i", "A");

        // validate
        Assertions.assertInstanceOf(NotifierDTO.class,result);
    }
    @Test
    public void shouldReturnNotifierNotFoundExceptionWhenNonExistenceNotifierSeenIndicatorAndStatus() {
        // scenario
        Mockito.when(notifierRepositoryMock.loadMaxIdBySeenIndicatorAndStatus("Gz1uPnRUXiR27TC8ydcNcULcANXEKYf0lmNjMeJW3G5tjP7O4i", "A")).thenReturn(0L);
        Mockito.when(notifierRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierBySeenIndicatorAndStatus("Gz1uPnRUXiR27TC8ydcNcULcANXEKYf0lmNjMeJW3G5tjP7O4i", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_SEENINDICATOR));
    }

    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingUuidExternalAppById() {
        // scenario
        UUID uuidExternalAppUpdateMock = UUID.fromString("028f37ef-d458-4f70-a611-58bcba92bd16");
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // action
        notifierService.updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateUuidExternalAppById(420L, uuidExternalAppUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingUuidExternalUserById() {
        // scenario
        UUID uuidExternalUserUpdateMock = UUID.fromString("ad5561f0-af19-4fdb-bf54-ad6ebc401585");
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);

        // action
        notifierService.updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateUuidExternalUserById(420L, uuidExternalUserUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingTypeById() {
        // scenario
        String typeUpdateMock = "MEBKtojwTDbCstJjpmiCMGDtxD520oKEvacT8nz2S6S6sM2ueE";
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
    public void shouldReturnNotifierDTOWhenUpdateExistingKeyById() {
        // scenario
        String keyUpdateMock = "Q7DT77fcG0XdA2msSlHKeJL5yp1FtceeYSLxWz8X7k75WDwTHE";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateKeyById(420L, keyUpdateMock);

        // action
        notifierService.updateKeyById(420L, keyUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateKeyById(420L, keyUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingTitleById() {
        // scenario
        String titleUpdateMock = "P8ADD5xnjETrHmOgMUmXJlmWn3a6NN8VM0gM1r2nvrLGncXiiC";
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
        String descriptionUpdateMock = "cbm3Qltd8BIkn5e3W4i2M2pqmGAXFwel5F8sSlvWd761PiyNPV";
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
    public void shouldReturnNotifierDTOWhenUpdateExistingUrlImageById() {
        // scenario
        String urlImageUpdateMock = "oxDGLuxHbJYu6vMW8idV9OzmTgCaSJR90kExjrn70wRu9moqqx";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateUrlImageById(420L, urlImageUpdateMock);

        // action
        notifierService.updateUrlImageById(420L, urlImageUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateUrlImageById(420L, urlImageUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingIconClassById() {
        // scenario
        String iconClassUpdateMock = "CFIsgl561nPBtGhDKQD6pglgAEdpgJoHxpePapbjcaPOPDyVSy";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateIconClassById(420L, iconClassUpdateMock);

        // action
        notifierService.updateIconClassById(420L, iconClassUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateIconClassById(420L, iconClassUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingUrlFollowById() {
        // scenario
        String urlFollowUpdateMock = "pEwce9lk5W376xxT8b8kw7GjEPpmWawiyswR8wS1Bh9gLmES5Q";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateUrlFollowById(420L, urlFollowUpdateMock);

        // action
        notifierService.updateUrlFollowById(420L, urlFollowUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateUrlFollowById(420L, urlFollowUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingObjectFreeById() {
        // scenario
        String objectFreeUpdateMock = "KzlrQqhWHsEasz4HiUxKky4etlaNrfaLQ0i7pRfluQnLAjKpI0";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateObjectFreeById(420L, objectFreeUpdateMock);

        // action
        notifierService.updateObjectFreeById(420L, objectFreeUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateObjectFreeById(420L, objectFreeUpdateMock);
    }
    @Test
    public void shouldReturnNotifierDTOWhenUpdateExistingSeenIndicatorById() {
        // scenario
        String seenIndicatorUpdateMock = "a8STDkTU1mjG4tpN1mwJJIpxN0ckxkO0QpkNktf4czt1qPryNa";
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(notifierRepositoryMock.findById(420L)).thenReturn(notifierModelMock);
        Mockito.doNothing().when(notifierRepositoryMock).updateSeenIndicatorById(420L, seenIndicatorUpdateMock);

        // action
        notifierService.updateSeenIndicatorById(420L, seenIndicatorUpdateMock);

        // validate
        Mockito.verify(notifierRepositoryMock,Mockito.times(1)).updateSeenIndicatorById(420L, seenIndicatorUpdateMock);
    }



    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 2846L;
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
        Long idMock = 2846L;
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
    public void showReturnExistingNotifierDTOWhenFindNotifierByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("ea3411fd-d0a5-4293-9c57-d2ca63d4ffc1");
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .uuidExternalApp(uuidExternalAppMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUuidExternalAppAndStatus(uuidExternalAppMock);

        // validate
        Assertions.assertEquals(uuidExternalAppMock, result.getUuidExternalApp());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByUuidExternalAppAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalAppMock = UUID.fromString("ea3411fd-d0a5-4293-9c57-d2ca63d4ffc1");
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByUuidExternalAppAndStatus(uuidExternalAppMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUuidExternalAppAndStatus(uuidExternalAppMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALAPP));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByUuidExternalUserAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalUserMock = UUID.fromString("6e06be7a-c2dc-403f-a872-305fd3d60b74");
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .uuidExternalUser(uuidExternalUserMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUserMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUuidExternalUserAndStatus(uuidExternalUserMock);

        // validate
        Assertions.assertEquals(uuidExternalUserMock, result.getUuidExternalUser());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByUuidExternalUserAndStatusActiveAnonimous() {
        // scenario
        UUID uuidExternalUserMock = UUID.fromString("6e06be7a-c2dc-403f-a872-305fd3d60b74");
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUserMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUuidExternalUserAndStatus(uuidExternalUserMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALUSER));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByTypeAndStatusActiveAnonimous() {
        // scenario
        String typeMock = "scMGP7sGU6m8nY8K6HSxrQ4pHG0lgMioXH8FenJSkMkTnDLrBx";
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
        String typeMock = "scMGP7sGU6m8nY8K6HSxrQ4pHG0lgMioXH8FenJSkMkTnDLrBx";
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
    public void showReturnExistingNotifierDTOWhenFindNotifierByKeyAndStatusActiveAnonimous() {
        // scenario
        String keyMock = "pgEzPD4OLEdGEaOzKiOnxL6a1i85zHhChXV4lAN9NEsfCys9tq";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .key(keyMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByKeyAndStatus(keyMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByKeyAndStatus(keyMock);

        // validate
        Assertions.assertEquals(keyMock, result.getKey());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByKeyAndStatusActiveAnonimous() {
        // scenario
        String keyMock = "pgEzPD4OLEdGEaOzKiOnxL6a1i85zHhChXV4lAN9NEsfCys9tq";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByKeyAndStatus(keyMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByKeyAndStatus(keyMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_KEY));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByTitleAndStatusActiveAnonimous() {
        // scenario
        String titleMock = "jL4ltyxj2GK9Kg61Mp303AMXj2wkCM1bXid8IBJAwoNqcqimNo";
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
        String titleMock = "jL4ltyxj2GK9Kg61Mp303AMXj2wkCM1bXid8IBJAwoNqcqimNo";
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
        String descriptionMock = "CYO005LzQD70Mn9QVviJu5jtImIzN7bc0xgcwumNRkjvmMeAR2";
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
        String descriptionMock = "CYO005LzQD70Mn9QVviJu5jtImIzN7bc0xgcwumNRkjvmMeAR2";
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
    public void showReturnExistingNotifierDTOWhenFindNotifierByUrlImageAndStatusActiveAnonimous() {
        // scenario
        String urlImageMock = "1F0qqtXuiRsAqE9MMYwbhTU8l73EzaGRdjaJg0w0Pu5CDYcOf8";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .urlImage(urlImageMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByUrlImageAndStatus(urlImageMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUrlImageAndStatus(urlImageMock);

        // validate
        Assertions.assertEquals(urlImageMock, result.getUrlImage());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByUrlImageAndStatusActiveAnonimous() {
        // scenario
        String urlImageMock = "1F0qqtXuiRsAqE9MMYwbhTU8l73EzaGRdjaJg0w0Pu5CDYcOf8";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByUrlImageAndStatus(urlImageMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUrlImageAndStatus(urlImageMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_URLIMAGE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByIconClassAndStatusActiveAnonimous() {
        // scenario
        String iconClassMock = "doE5I43HJRd28jDq1TVfFFj18QFE3nP4zhaxjXUra2NBDH6G5l";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .iconClass(iconClassMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByIconClassAndStatus(iconClassMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByIconClassAndStatus(iconClassMock);

        // validate
        Assertions.assertEquals(iconClassMock, result.getIconClass());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByIconClassAndStatusActiveAnonimous() {
        // scenario
        String iconClassMock = "doE5I43HJRd28jDq1TVfFFj18QFE3nP4zhaxjXUra2NBDH6G5l";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByIconClassAndStatus(iconClassMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByIconClassAndStatus(iconClassMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_ICONCLASS));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByUrlFollowAndStatusActiveAnonimous() {
        // scenario
        String urlFollowMock = "cfeCIeEfsoEBUOXvC60ixjvRcq0EvVWFHxnQujaBwaMv3004G8";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .urlFollow(urlFollowMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByUrlFollowAndStatus(urlFollowMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByUrlFollowAndStatus(urlFollowMock);

        // validate
        Assertions.assertEquals(urlFollowMock, result.getUrlFollow());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByUrlFollowAndStatusActiveAnonimous() {
        // scenario
        String urlFollowMock = "cfeCIeEfsoEBUOXvC60ixjvRcq0EvVWFHxnQujaBwaMv3004G8";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByUrlFollowAndStatus(urlFollowMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByUrlFollowAndStatus(urlFollowMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_URLFOLLOW));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierByObjectFreeAndStatusActiveAnonimous() {
        // scenario
        String objectFreeMock = "XQuwEOv3sG8Xe4BQztVbBpP4E8sYiswPxxl9EJyN52wvhsJQRf";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .objectFree(objectFreeMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdByObjectFreeAndStatus(objectFreeMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierByObjectFreeAndStatus(objectFreeMock);

        // validate
        Assertions.assertEquals(objectFreeMock, result.getObjectFree());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierByObjectFreeAndStatusActiveAnonimous() {
        // scenario
        String objectFreeMock = "XQuwEOv3sG8Xe4BQztVbBpP4E8sYiswPxxl9EJyN52wvhsJQRf";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdByObjectFreeAndStatus(objectFreeMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierByObjectFreeAndStatus(objectFreeMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_OBJECTFREE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingNotifierDTOWhenFindNotifierBySeenIndicatorAndStatusActiveAnonimous() {
        // scenario
        String seenIndicatorMock = "3IGHMX4OQKQbspVP7hQ0PRaQqg5kPMLMqEJLBAwC0SVk5DeYB0";
        Long maxIdMock = 1972L;
        Optional<Notifier> notifierModelMock = Optional.ofNullable(NotifierModelBuilder.newNotifierModelTestBuilder()
                .seenIndicator(seenIndicatorMock)
                .now()
        );
        Mockito.when(notifierRepositoryMock.loadMaxIdBySeenIndicatorAndStatus(seenIndicatorMock, "A")).thenReturn(maxIdMock);
        Mockito.when(notifierRepositoryMock.findById(maxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierDTO result = notifierService.findNotifierBySeenIndicatorAndStatus(seenIndicatorMock);

        // validate
        Assertions.assertEquals(seenIndicatorMock, result.getSeenIndicator());

    }
    @Test
    public void showReturnNotifierNotFoundExceptionWhenNonExistenceFindNotifierBySeenIndicatorAndStatusActiveAnonimous() {
        // scenario
        String seenIndicatorMock = "3IGHMX4OQKQbspVP7hQ0PRaQqg5kPMLMqEJLBAwC0SVk5DeYB0";
        Long noMaxIdMock = 0L;
        Optional<Notifier> notifierModelMock = Optional.empty();
        Mockito.when(notifierRepositoryMock.loadMaxIdBySeenIndicatorAndStatus(seenIndicatorMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(notifierRepositoryMock.findById(noMaxIdMock)).thenReturn(notifierModelMock);

        // action
        NotifierNotFoundException exception = Assertions.assertThrows(NotifierNotFoundException.class,
                ()->notifierService.findNotifierBySeenIndicatorAndStatus(seenIndicatorMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(NOTIFIER_NOTFOUND_WITH_SEENINDICATOR));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

