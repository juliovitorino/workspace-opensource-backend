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

package br.com.jcv.reaction.corelayer.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.reaction.corelayer.builder.ReactionEventDTOBuilder;
import br.com.jcv.reaction.corelayer.builder.ReactionEventModelBuilder;
import br.com.jcv.reaction.corelayer.dto.ReactionEventDTO;
import br.com.jcv.reaction.corelayer.exception.ReactionEventNotFoundException;
import br.com.jcv.reaction.corelayer.model.ReactionEvent;
import br.com.jcv.reaction.corelayer.repository.ReactionEventRepository;
import br.com.jcv.reaction.corelayer.service.ReactionEventService;
import br.com.jcv.reaction.corelayer.constantes.ReactionEventConstantes;
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
public class ReactionEventServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String REACTIONEVENT_NOTFOUND_WITH_ID = "ReactionEvent não encontrada com id = ";
    public static final String REACTIONEVENT_NOTFOUND_WITH_REACTIONID = "ReactionEvent não encontrada com reactionId = ";
    public static final String REACTIONEVENT_NOTFOUND_WITH_EXTERNALITEMUUID = "ReactionEvent não encontrada com externalItemUUID = ";
    public static final String REACTIONEVENT_NOTFOUND_WITH_EXTERNALAPPUUID = "ReactionEvent não encontrada com externalAppUUID = ";
    public static final String REACTIONEVENT_NOTFOUND_WITH_EXTERNALUSERUUID = "ReactionEvent não encontrada com externalUserUUID = ";
    public static final String REACTIONEVENT_NOTFOUND_WITH_STATUS = "ReactionEvent não encontrada com status = ";
    public static final String REACTIONEVENT_NOTFOUND_WITH_DATECREATED = "ReactionEvent não encontrada com dateCreated = ";
    public static final String REACTIONEVENT_NOTFOUND_WITH_DATEUPDATED = "ReactionEvent não encontrada com dateUpdated = ";


    @Mock
    private ReactionEventRepository reactioneventRepositoryMock;

    @InjectMocks
    private ReactionEventService reactioneventService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        reactioneventService = new ReactionEventServiceImpl();
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
    public void shouldReturnListOfReactionEventWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 15420L;
        Long reactionId = 8330L;
        Long externalItemUUID = 85467L;
        Long externalAppUUID = 77531L;
        Long externalUserUUID = 65872L;
        String status = "OgsIe7VNWCxrt0rhy9BoP5jQo9UnbxjB8JtrNCBhGHXoRF0oKa";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("reactionId", reactionId);
        mapFieldsRequestMock.put("externalItemUUID", externalItemUUID);
        mapFieldsRequestMock.put("externalAppUUID", externalAppUUID);
        mapFieldsRequestMock.put("externalUserUUID", externalUserUUID);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<ReactionEvent> reactioneventsFromRepository = new ArrayList<>();
        reactioneventsFromRepository.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        reactioneventsFromRepository.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        reactioneventsFromRepository.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        reactioneventsFromRepository.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());

        Mockito.when(reactioneventRepositoryMock.findReactionEventByFilter(
            id,
            reactionId,
            externalItemUUID,
            externalAppUUID,
            externalUserUUID,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(reactioneventsFromRepository);

        // action
        List<ReactionEventDTO> result = reactioneventService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithReactionEventListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 60427L;
        Long reactionId = 17847L;
        Long externalItemUUID = 3823L;
        Long externalAppUUID = 20044L;
        Long externalUserUUID = 83801L;
        String status = "5yy1jOz76cuIK0CQ87L8aMSCXKQ3ztTFhIBicj0GgCaFy37kmh";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("reactionId", reactionId);
        mapFieldsRequestMock.put("externalItemUUID", externalItemUUID);
        mapFieldsRequestMock.put("externalAppUUID", externalAppUUID);
        mapFieldsRequestMock.put("externalUserUUID", externalUserUUID);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<ReactionEvent> reactioneventsFromRepository = new ArrayList<>();
        reactioneventsFromRepository.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        reactioneventsFromRepository.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        reactioneventsFromRepository.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        reactioneventsFromRepository.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());

        List<ReactionEventDTO> reactioneventsFiltered = reactioneventsFromRepository
                .stream()
                .map(m->reactioneventService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageReactionEventItems", reactioneventsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<ReactionEvent> pagedResponse =
                new PageImpl<>(reactioneventsFromRepository,
                        pageableMock,
                        reactioneventsFromRepository.size());

        Mockito.when(reactioneventRepositoryMock.findReactionEventByFilter(pageableMock,
            id,
            reactionId,
            externalItemUUID,
            externalAppUUID,
            externalUserUUID,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = reactioneventService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<ReactionEventDTO> reactioneventsResult = (List<ReactionEventDTO>) result.get("pageReactionEventItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, reactioneventsResult.size());
    }


    @Test
    public void showReturnListOfReactionEventWhenAskedForFindAllByStatus() {
        // scenario
        List<ReactionEvent> listOfReactionEventModelMock = new ArrayList<>();
        listOfReactionEventModelMock.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        listOfReactionEventModelMock.add(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());

        Mockito.when(reactioneventRepositoryMock.findAllByStatus("A")).thenReturn(listOfReactionEventModelMock);

        // action
        List<ReactionEventDTO> listOfReactionEvents = reactioneventService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfReactionEvents.isEmpty());
        Assertions.assertEquals(2, listOfReactionEvents.size());
    }
    @Test
    public void shouldReturnReactionEventNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 48402L;
        Optional<ReactionEvent> reactioneventNonExistentMock = Optional.empty();
        Mockito.when(reactioneventRepositoryMock.findById(idMock)).thenReturn(reactioneventNonExistentMock);

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowReactionEventNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 30655L;
        Mockito.when(reactioneventRepositoryMock.findById(idMock))
                .thenThrow(new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                REACTIONEVENT_NOTFOUND_WITH_ID ));

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnReactionEventDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 70637L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(
                ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                        .id(idMock)
                        .reactionId(61873L)
                        .externalItemUUID(33145L)
                        .externalAppUUID(44770L)
                        .externalUserUUID(48676L)

                        .status("X")
                        .now()
        );
        ReactionEvent reactioneventToSaveMock = reactioneventModelMock.orElse(null);
        ReactionEvent reactioneventSavedMck = ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                        .id(75201L)
                        .reactionId(21670L)
                        .externalItemUUID(74322L)
                        .externalAppUUID(30020L)
                        .externalUserUUID(68545L)

                        .status("A")
                        .now();
        Mockito.when(reactioneventRepositoryMock.findById(idMock)).thenReturn(reactioneventModelMock);
        Mockito.when(reactioneventRepositoryMock.save(reactioneventToSaveMock)).thenReturn(reactioneventSavedMck);

        // action
        ReactionEventDTO result = reactioneventService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchReactionEventByAnyNonExistenceIdAndReturnReactionEventNotFoundException() {
        // scenario
        Mockito.when(reactioneventRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()-> reactioneventService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchReactionEventByIdAndReturnDTO() {
        // scenario
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .id(231L)
                .reactionId(1802L)
                .externalItemUUID(46048L)
                .externalAppUUID(82010L)
                .externalUserUUID(22400L)

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(reactioneventRepositoryMock.findById(Mockito.anyLong())).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findById(1L);

        // validate
        Assertions.assertInstanceOf(ReactionEventDTO.class,result);
    }
    @Test
    public void shouldDeleteReactionEventByIdWithSucess() {
        // scenario
        Optional<ReactionEvent> reactionevent = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder().id(1L).now());
        Mockito.when(reactioneventRepositoryMock.findById(Mockito.anyLong())).thenReturn(reactionevent);

        // action
        reactioneventService.delete(1L);

        // validate
        Mockito.verify(reactioneventRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceReactionEventShouldReturnReactionEventNotFoundException() {
        // scenario
        Mockito.when(reactioneventRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(
                ReactionEventNotFoundException.class, () -> reactioneventService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingReactionEventWithSucess() {
        // scenario
        ReactionEventDTO reactioneventDTOMock = ReactionEventDTOBuilder.newReactionEventDTOTestBuilder()
                .id(51177L)
                .reactionId(3370L)
                .externalItemUUID(57024L)
                .externalAppUUID(23400L)
                .externalUserUUID(35565L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ReactionEvent reactioneventMock = ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .id(reactioneventDTOMock.getId())
                .reactionId(reactioneventDTOMock.getReactionId())
                .externalItemUUID(reactioneventDTOMock.getExternalItemUUID())
                .externalAppUUID(reactioneventDTOMock.getExternalAppUUID())
                .externalUserUUID(reactioneventDTOMock.getExternalUserUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ReactionEvent reactioneventSavedMock = ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .id(reactioneventDTOMock.getId())
                .reactionId(reactioneventDTOMock.getReactionId())
                .externalItemUUID(reactioneventDTOMock.getExternalItemUUID())
                .externalAppUUID(reactioneventDTOMock.getExternalAppUUID())
                .externalUserUUID(reactioneventDTOMock.getExternalUserUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(reactioneventRepositoryMock.save(reactioneventMock)).thenReturn(reactioneventSavedMock);

        // action
        ReactionEventDTO reactioneventSaved = reactioneventService.salvar(reactioneventDTOMock);

        // validate
        Assertions.assertInstanceOf(ReactionEventDTO.class, reactioneventSaved);
        Assertions.assertNotNull(reactioneventSaved.getId());
    }

    @Test
    public void ShouldSaveNewReactionEventWithSucess() {
        // scenario
        ReactionEventDTO reactioneventDTOMock = ReactionEventDTOBuilder.newReactionEventDTOTestBuilder()
                .id(null)
                .reactionId(10774L)
                .externalItemUUID(78080L)
                .externalAppUUID(83175L)
                .externalUserUUID(83744L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ReactionEvent reactioneventModelMock = ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .id(null)
                .reactionId(reactioneventDTOMock.getReactionId())
                .externalItemUUID(reactioneventDTOMock.getExternalItemUUID())
                .externalAppUUID(reactioneventDTOMock.getExternalAppUUID())
                .externalUserUUID(reactioneventDTOMock.getExternalUserUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ReactionEvent reactioneventSavedMock = ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .id(501L)
                .reactionId(reactioneventDTOMock.getReactionId())
                .externalItemUUID(reactioneventDTOMock.getExternalItemUUID())
                .externalAppUUID(reactioneventDTOMock.getExternalAppUUID())
                .externalUserUUID(reactioneventDTOMock.getExternalUserUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(reactioneventRepositoryMock.save(reactioneventModelMock)).thenReturn(reactioneventSavedMock);

        // action
        ReactionEventDTO reactioneventSaved = reactioneventService.salvar(reactioneventDTOMock);

        // validate
        Assertions.assertInstanceOf(ReactionEventDTO.class, reactioneventSaved);
        Assertions.assertNotNull(reactioneventSaved.getId());
        Assertions.assertEquals("P",reactioneventSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapReactionEventDTOMock = new HashMap<>();
        mapReactionEventDTOMock.put(ReactionEventConstantes.REACTIONID,82011L);
        mapReactionEventDTOMock.put(ReactionEventConstantes.EXTERNALITEMUUID,77006L);
        mapReactionEventDTOMock.put(ReactionEventConstantes.EXTERNALAPPUUID,47601L);
        mapReactionEventDTOMock.put(ReactionEventConstantes.EXTERNALUSERUUID,68714L);
        mapReactionEventDTOMock.put(ReactionEventConstantes.STATUS,"d33Ia1BkVw8AnXnwTEP7993acriyAxEck1Oal0oWLQY9XdvALY");


        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(
                ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                        .id(4058L)
                        .reactionId(4080L)
                        .externalItemUUID(30813L)
                        .externalAppUUID(27872L)
                        .externalUserUUID(63508L)
                        .status("N6Y01b20gv0ahSygsBKybVEM6cMECPRazPfWVjIvArhc2KwSTX")

                        .now()
        );

        Mockito.when(reactioneventRepositoryMock.findById(1L)).thenReturn(reactioneventModelMock);

        // action
        boolean executed = reactioneventService.partialUpdate(1L, mapReactionEventDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnReactionEventNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapReactionEventDTOMock = new HashMap<>();
        mapReactionEventDTOMock.put(ReactionEventConstantes.REACTIONID,56406L);
        mapReactionEventDTOMock.put(ReactionEventConstantes.EXTERNALITEMUUID,28260L);
        mapReactionEventDTOMock.put(ReactionEventConstantes.EXTERNALAPPUUID,20567L);
        mapReactionEventDTOMock.put(ReactionEventConstantes.EXTERNALUSERUUID,6102L);
        mapReactionEventDTOMock.put(ReactionEventConstantes.STATUS,"9fbo1TW9GGC0Wg4dQs7f9R0EuGMA7BB2DRW5lQRhlMmS2lSiIg");


        Mockito.when(reactioneventRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.partialUpdate(1L, mapReactionEventDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("ReactionEvent não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnReactionEventListWhenFindAllReactionEventByIdAndStatus() {
        // scenario
        List<ReactionEvent> reactionevents = Arrays.asList(
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now()
        );

        Mockito.when(reactioneventRepositoryMock.findAllByIdAndStatus(17605L, "A")).thenReturn(reactionevents);

        // action
        List<ReactionEventDTO> result = reactioneventService.findAllReactionEventByIdAndStatus(17605L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionEventListWhenFindAllReactionEventByReactionIdAndStatus() {
        // scenario
        List<ReactionEvent> reactionevents = Arrays.asList(
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now()
        );

        Mockito.when(reactioneventRepositoryMock.findAllByReactionIdAndStatus(47787L, "A")).thenReturn(reactionevents);

        // action
        List<ReactionEventDTO> result = reactioneventService.findAllReactionEventByReactionIdAndStatus(47787L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionEventListWhenFindAllReactionEventByExternalItemUUIDAndStatus() {
        // scenario
        List<ReactionEvent> reactionevents = Arrays.asList(
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now()
        );

        Mockito.when(reactioneventRepositoryMock.findAllByExternalItemUUIDAndStatus(33688L, "A")).thenReturn(reactionevents);

        // action
        List<ReactionEventDTO> result = reactioneventService.findAllReactionEventByExternalItemUUIDAndStatus(33688L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionEventListWhenFindAllReactionEventByExternalAppUUIDAndStatus() {
        // scenario
        List<ReactionEvent> reactionevents = Arrays.asList(
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now()
        );

        Mockito.when(reactioneventRepositoryMock.findAllByExternalAppUUIDAndStatus(6476L, "A")).thenReturn(reactionevents);

        // action
        List<ReactionEventDTO> result = reactioneventService.findAllReactionEventByExternalAppUUIDAndStatus(6476L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionEventListWhenFindAllReactionEventByExternalUserUUIDAndStatus() {
        // scenario
        List<ReactionEvent> reactionevents = Arrays.asList(
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now()
        );

        Mockito.when(reactioneventRepositoryMock.findAllByExternalUserUUIDAndStatus(13313L, "A")).thenReturn(reactionevents);

        // action
        List<ReactionEventDTO> result = reactioneventService.findAllReactionEventByExternalUserUUIDAndStatus(13313L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionEventListWhenFindAllReactionEventByDateCreatedAndStatus() {
        // scenario
        List<ReactionEvent> reactionevents = Arrays.asList(
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now()
        );

        Mockito.when(reactioneventRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reactionevents);

        // action
        List<ReactionEventDTO> result = reactioneventService.findAllReactionEventByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionEventListWhenFindAllReactionEventByDateUpdatedAndStatus() {
        // scenario
        List<ReactionEvent> reactionevents = Arrays.asList(
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now(),
            ReactionEventModelBuilder.newReactionEventModelTestBuilder().now()
        );

        Mockito.when(reactioneventRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reactionevents);

        // action
        List<ReactionEventDTO> result = reactioneventService.findAllReactionEventByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentReactionEventDTOWhenFindReactionEventByIdAndStatus() {
        // scenario
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByIdAndStatus(37422L, "A")).thenReturn(1L);
        Mockito.when(reactioneventRepositoryMock.findById(1L)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByIdAndStatus(37422L, "A");

        // validate
        Assertions.assertInstanceOf(ReactionEventDTO.class,result);
    }
    @Test
    public void shouldReturnReactionEventNotFoundExceptionWhenNonExistenceReactionEventIdAndStatus() {
        // scenario
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByIdAndStatus(37422L, "A")).thenReturn(0L);
        Mockito.when(reactioneventRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByIdAndStatus(37422L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentReactionEventDTOWhenFindReactionEventByReactionIdAndStatus() {
        // scenario
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByReactionIdAndStatus(11206L, "A")).thenReturn(1L);
        Mockito.when(reactioneventRepositoryMock.findById(1L)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByReactionIdAndStatus(11206L, "A");

        // validate
        Assertions.assertInstanceOf(ReactionEventDTO.class,result);
    }
    @Test
    public void shouldReturnReactionEventNotFoundExceptionWhenNonExistenceReactionEventReactionIdAndStatus() {
        // scenario
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByReactionIdAndStatus(11206L, "A")).thenReturn(0L);
        Mockito.when(reactioneventRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByReactionIdAndStatus(11206L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_REACTIONID));
    }
    @Test
    public void shouldReturnExistentReactionEventDTOWhenFindReactionEventByExternalItemUUIDAndStatus() {
        // scenario
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalItemUUIDAndStatus(35518L, "A")).thenReturn(1L);
        Mockito.when(reactioneventRepositoryMock.findById(1L)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByExternalItemUUIDAndStatus(35518L, "A");

        // validate
        Assertions.assertInstanceOf(ReactionEventDTO.class,result);
    }
    @Test
    public void shouldReturnReactionEventNotFoundExceptionWhenNonExistenceReactionEventExternalItemUUIDAndStatus() {
        // scenario
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalItemUUIDAndStatus(35518L, "A")).thenReturn(0L);
        Mockito.when(reactioneventRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByExternalItemUUIDAndStatus(35518L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_EXTERNALITEMUUID));
    }
    @Test
    public void shouldReturnExistentReactionEventDTOWhenFindReactionEventByExternalAppUUIDAndStatus() {
        // scenario
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalAppUUIDAndStatus(33080L, "A")).thenReturn(1L);
        Mockito.when(reactioneventRepositoryMock.findById(1L)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByExternalAppUUIDAndStatus(33080L, "A");

        // validate
        Assertions.assertInstanceOf(ReactionEventDTO.class,result);
    }
    @Test
    public void shouldReturnReactionEventNotFoundExceptionWhenNonExistenceReactionEventExternalAppUUIDAndStatus() {
        // scenario
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalAppUUIDAndStatus(33080L, "A")).thenReturn(0L);
        Mockito.when(reactioneventRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByExternalAppUUIDAndStatus(33080L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_EXTERNALAPPUUID));
    }
    @Test
    public void shouldReturnExistentReactionEventDTOWhenFindReactionEventByExternalUserUUIDAndStatus() {
        // scenario
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder().now());
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalUserUUIDAndStatus(52225L, "A")).thenReturn(1L);
        Mockito.when(reactioneventRepositoryMock.findById(1L)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByExternalUserUUIDAndStatus(52225L, "A");

        // validate
        Assertions.assertInstanceOf(ReactionEventDTO.class,result);
    }
    @Test
    public void shouldReturnReactionEventNotFoundExceptionWhenNonExistenceReactionEventExternalUserUUIDAndStatus() {
        // scenario
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalUserUUIDAndStatus(52225L, "A")).thenReturn(0L);
        Mockito.when(reactioneventRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByExternalUserUUIDAndStatus(52225L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_EXTERNALUSERUUID));
    }

    @Test
    public void shouldReturnReactionEventDTOWhenUpdateExistingReactionIdById() {
        // scenario
        Long reactionIdUpdateMock = 37368L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reactioneventRepositoryMock.findById(420L)).thenReturn(reactioneventModelMock);
        Mockito.doNothing().when(reactioneventRepositoryMock).updateReactionIdById(420L, reactionIdUpdateMock);

        // action
        reactioneventService.updateReactionIdById(420L, reactionIdUpdateMock);

        // validate
        Mockito.verify(reactioneventRepositoryMock,Mockito.times(1)).updateReactionIdById(420L, reactionIdUpdateMock);
    }
    @Test
    public void shouldReturnReactionEventDTOWhenUpdateExistingExternalItemUUIDById() {
        // scenario
        Long externalItemUUIDUpdateMock = 36444L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reactioneventRepositoryMock.findById(420L)).thenReturn(reactioneventModelMock);
        Mockito.doNothing().when(reactioneventRepositoryMock).updateExternalItemUUIDById(420L, externalItemUUIDUpdateMock);

        // action
        reactioneventService.updateExternalItemUUIDById(420L, externalItemUUIDUpdateMock);

        // validate
        Mockito.verify(reactioneventRepositoryMock,Mockito.times(1)).updateExternalItemUUIDById(420L, externalItemUUIDUpdateMock);
    }
    @Test
    public void shouldReturnReactionEventDTOWhenUpdateExistingExternalAppUUIDById() {
        // scenario
        Long externalAppUUIDUpdateMock = 12630L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reactioneventRepositoryMock.findById(420L)).thenReturn(reactioneventModelMock);
        Mockito.doNothing().when(reactioneventRepositoryMock).updateExternalAppUUIDById(420L, externalAppUUIDUpdateMock);

        // action
        reactioneventService.updateExternalAppUUIDById(420L, externalAppUUIDUpdateMock);

        // validate
        Mockito.verify(reactioneventRepositoryMock,Mockito.times(1)).updateExternalAppUUIDById(420L, externalAppUUIDUpdateMock);
    }
    @Test
    public void shouldReturnReactionEventDTOWhenUpdateExistingExternalUserUUIDById() {
        // scenario
        Long externalUserUUIDUpdateMock = 45070L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reactioneventRepositoryMock.findById(420L)).thenReturn(reactioneventModelMock);
        Mockito.doNothing().when(reactioneventRepositoryMock).updateExternalUserUUIDById(420L, externalUserUUIDUpdateMock);

        // action
        reactioneventService.updateExternalUserUUIDById(420L, externalUserUUIDUpdateMock);

        // validate
        Mockito.verify(reactioneventRepositoryMock,Mockito.times(1)).updateExternalUserUUIDById(420L, externalUserUUIDUpdateMock);
    }



    @Test
    public void showReturnExistingReactionEventDTOWhenFindReactionEventByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 5766L;
        Long maxIdMock = 1972L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(maxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnReactionEventNotFoundExceptionWhenNonExistenceFindReactionEventByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 5766L;
        Long noMaxIdMock = 0L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.empty();
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(noMaxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReactionEventDTOWhenFindReactionEventByReactionIdAndStatusActiveAnonimous() {
        // scenario
        Long reactionIdMock = 77118L;
        Long maxIdMock = 1972L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .reactionId(reactionIdMock)
                .now()
        );
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByReactionIdAndStatus(reactionIdMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(maxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByReactionIdAndStatus(reactionIdMock);

        // validate
        Assertions.assertEquals(reactionIdMock, result.getReactionId());

    }
    @Test
    public void showReturnReactionEventNotFoundExceptionWhenNonExistenceFindReactionEventByReactionIdAndStatusActiveAnonimous() {
        // scenario
        Long reactionIdMock = 77118L;
        Long noMaxIdMock = 0L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.empty();
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByReactionIdAndStatus(reactionIdMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(noMaxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByReactionIdAndStatus(reactionIdMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_REACTIONID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReactionEventDTOWhenFindReactionEventByExternalItemUUIDAndStatusActiveAnonimous() {
        // scenario
        Long externalItemUUIDMock = 16418L;
        Long maxIdMock = 1972L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .externalItemUUID(externalItemUUIDMock)
                .now()
        );
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalItemUUIDAndStatus(externalItemUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(maxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByExternalItemUUIDAndStatus(externalItemUUIDMock);

        // validate
        Assertions.assertEquals(externalItemUUIDMock, result.getExternalItemUUID());

    }
    @Test
    public void showReturnReactionEventNotFoundExceptionWhenNonExistenceFindReactionEventByExternalItemUUIDAndStatusActiveAnonimous() {
        // scenario
        Long externalItemUUIDMock = 16418L;
        Long noMaxIdMock = 0L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.empty();
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalItemUUIDAndStatus(externalItemUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(noMaxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByExternalItemUUIDAndStatus(externalItemUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_EXTERNALITEMUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReactionEventDTOWhenFindReactionEventByExternalAppUUIDAndStatusActiveAnonimous() {
        // scenario
        Long externalAppUUIDMock = 87516L;
        Long maxIdMock = 1972L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .externalAppUUID(externalAppUUIDMock)
                .now()
        );
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalAppUUIDAndStatus(externalAppUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(maxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByExternalAppUUIDAndStatus(externalAppUUIDMock);

        // validate
        Assertions.assertEquals(externalAppUUIDMock, result.getExternalAppUUID());

    }
    @Test
    public void showReturnReactionEventNotFoundExceptionWhenNonExistenceFindReactionEventByExternalAppUUIDAndStatusActiveAnonimous() {
        // scenario
        Long externalAppUUIDMock = 87516L;
        Long noMaxIdMock = 0L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.empty();
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalAppUUIDAndStatus(externalAppUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(noMaxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByExternalAppUUIDAndStatus(externalAppUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_EXTERNALAPPUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReactionEventDTOWhenFindReactionEventByExternalUserUUIDAndStatusActiveAnonimous() {
        // scenario
        Long externalUserUUIDMock = 27580L;
        Long maxIdMock = 1972L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.ofNullable(ReactionEventModelBuilder.newReactionEventModelTestBuilder()
                .externalUserUUID(externalUserUUIDMock)
                .now()
        );
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalUserUUIDAndStatus(externalUserUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(maxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventDTO result = reactioneventService.findReactionEventByExternalUserUUIDAndStatus(externalUserUUIDMock);

        // validate
        Assertions.assertEquals(externalUserUUIDMock, result.getExternalUserUUID());

    }
    @Test
    public void showReturnReactionEventNotFoundExceptionWhenNonExistenceFindReactionEventByExternalUserUUIDAndStatusActiveAnonimous() {
        // scenario
        Long externalUserUUIDMock = 27580L;
        Long noMaxIdMock = 0L;
        Optional<ReactionEvent> reactioneventModelMock = Optional.empty();
        Mockito.when(reactioneventRepositoryMock.loadMaxIdByExternalUserUUIDAndStatus(externalUserUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactioneventRepositoryMock.findById(noMaxIdMock)).thenReturn(reactioneventModelMock);

        // action
        ReactionEventNotFoundException exception = Assertions.assertThrows(ReactionEventNotFoundException.class,
                ()->reactioneventService.findReactionEventByExternalUserUUIDAndStatus(externalUserUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTIONEVENT_NOTFOUND_WITH_EXTERNALUSERUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

