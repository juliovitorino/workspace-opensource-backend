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
import br.com.jcv.reaction.corelayer.builder.GroupReactionDTOBuilder;
import br.com.jcv.reaction.corelayer.builder.GroupReactionModelBuilder;
import br.com.jcv.reaction.corelayer.dto.GroupReactionDTO;
import br.com.jcv.reaction.corelayer.exception.GroupReactionNotFoundException;
import br.com.jcv.reaction.corelayer.model.GroupReaction;
import br.com.jcv.reaction.corelayer.repository.GroupReactionRepository;
import br.com.jcv.reaction.corelayer.service.GroupReactionService;
import br.com.jcv.reaction.corelayer.constantes.GroupReactionConstantes;
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
public class GroupReactionServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String GROUPREACTION_NOTFOUND_WITH_ID = "GroupReaction não encontrada com id = ";
    public static final String GROUPREACTION_NOTFOUND_WITH_GROUPID = "GroupReaction não encontrada com groupId = ";
    public static final String GROUPREACTION_NOTFOUND_WITH_REACTIONID = "GroupReaction não encontrada com reactionId = ";
    public static final String GROUPREACTION_NOTFOUND_WITH_STATUS = "GroupReaction não encontrada com status = ";
    public static final String GROUPREACTION_NOTFOUND_WITH_DATECREATED = "GroupReaction não encontrada com dateCreated = ";
    public static final String GROUPREACTION_NOTFOUND_WITH_DATEUPDATED = "GroupReaction não encontrada com dateUpdated = ";


    @Mock
    private GroupReactionRepository groupreactionRepositoryMock;

    @InjectMocks
    private GroupReactionService groupreactionService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        groupreactionService = new GroupReactionServiceImpl();
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
    public void shouldReturnListOfGroupReactionWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 77810L;
        Long groupId = 6530L;
        Long reactionId = 80860L;
        String status = "xYocq2dIkfXhq5k7Ep25Mvv0y2RTg9vLkjgE24QyKc73JeGQBM";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("groupId", groupId);
        mapFieldsRequestMock.put("reactionId", reactionId);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<GroupReaction> groupreactionsFromRepository = new ArrayList<>();
        groupreactionsFromRepository.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        groupreactionsFromRepository.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        groupreactionsFromRepository.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        groupreactionsFromRepository.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());

        Mockito.when(groupreactionRepositoryMock.findGroupReactionByFilter(
            id,
            groupId,
            reactionId,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(groupreactionsFromRepository);

        // action
        List<GroupReactionDTO> result = groupreactionService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithGroupReactionListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 72768L;
        Long groupId = 80060L;
        Long reactionId = 24883L;
        String status = "qKTQiwzQp2VLvbspOKA1cDTFoyTeefkQYgSuqJilbHb7dIzFjY";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("groupId", groupId);
        mapFieldsRequestMock.put("reactionId", reactionId);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<GroupReaction> groupreactionsFromRepository = new ArrayList<>();
        groupreactionsFromRepository.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        groupreactionsFromRepository.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        groupreactionsFromRepository.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        groupreactionsFromRepository.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());

        List<GroupReactionDTO> groupreactionsFiltered = groupreactionsFromRepository
                .stream()
                .map(m->groupreactionService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageGroupReactionItems", groupreactionsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<GroupReaction> pagedResponse =
                new PageImpl<>(groupreactionsFromRepository,
                        pageableMock,
                        groupreactionsFromRepository.size());

        Mockito.when(groupreactionRepositoryMock.findGroupReactionByFilter(pageableMock,
            id,
            groupId,
            reactionId,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = groupreactionService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<GroupReactionDTO> groupreactionsResult = (List<GroupReactionDTO>) result.get("pageGroupReactionItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, groupreactionsResult.size());
    }


    @Test
    public void showReturnListOfGroupReactionWhenAskedForFindAllByStatus() {
        // scenario
        List<GroupReaction> listOfGroupReactionModelMock = new ArrayList<>();
        listOfGroupReactionModelMock.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        listOfGroupReactionModelMock.add(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());

        Mockito.when(groupreactionRepositoryMock.findAllByStatus("A")).thenReturn(listOfGroupReactionModelMock);

        // action
        List<GroupReactionDTO> listOfGroupReactions = groupreactionService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfGroupReactions.isEmpty());
        Assertions.assertEquals(2, listOfGroupReactions.size());
    }
    @Test
    public void shouldReturnGroupReactionNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 76087L;
        Optional<GroupReaction> groupreactionNonExistentMock = Optional.empty();
        Mockito.when(groupreactionRepositoryMock.findById(idMock)).thenReturn(groupreactionNonExistentMock);

        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowGroupReactionNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 4171L;
        Mockito.when(groupreactionRepositoryMock.findById(idMock))
                .thenThrow(new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                GROUPREACTION_NOTFOUND_WITH_ID ));

        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnGroupReactionDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 5132L;
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(
                GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                        .id(idMock)
                        .groupId(15406L)
                        .reactionId(62700L)

                        .status("X")
                        .now()
        );
        GroupReaction groupreactionToSaveMock = groupreactionModelMock.orElse(null);
        GroupReaction groupreactionSavedMck = GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                        .id(8757L)
                        .groupId(61361L)
                        .reactionId(54058L)

                        .status("A")
                        .now();
        Mockito.when(groupreactionRepositoryMock.findById(idMock)).thenReturn(groupreactionModelMock);
        Mockito.when(groupreactionRepositoryMock.save(groupreactionToSaveMock)).thenReturn(groupreactionSavedMck);

        // action
        GroupReactionDTO result = groupreactionService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchGroupReactionByAnyNonExistenceIdAndReturnGroupReactionNotFoundException() {
        // scenario
        Mockito.when(groupreactionRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()-> groupreactionService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchGroupReactionByIdAndReturnDTO() {
        // scenario
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                .id(73254L)
                .groupId(82650L)
                .reactionId(50512L)

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(groupreactionRepositoryMock.findById(Mockito.anyLong())).thenReturn(groupreactionModelMock);

        // action
        GroupReactionDTO result = groupreactionService.findById(1L);

        // validate
        Assertions.assertInstanceOf(GroupReactionDTO.class,result);
    }
    @Test
    public void shouldDeleteGroupReactionByIdWithSucess() {
        // scenario
        Optional<GroupReaction> groupreaction = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().id(1L).now());
        Mockito.when(groupreactionRepositoryMock.findById(Mockito.anyLong())).thenReturn(groupreaction);

        // action
        groupreactionService.delete(1L);

        // validate
        Mockito.verify(groupreactionRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceGroupReactionShouldReturnGroupReactionNotFoundException() {
        // scenario
        Mockito.when(groupreactionRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(
                GroupReactionNotFoundException.class, () -> groupreactionService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingGroupReactionWithSucess() {
        // scenario
        GroupReactionDTO groupreactionDTOMock = GroupReactionDTOBuilder.newGroupReactionDTOTestBuilder()
                .id(26677L)
                .groupId(8082L)
                .reactionId(78510L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupReaction groupreactionMock = GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                .id(groupreactionDTOMock.getId())
                .groupId(groupreactionDTOMock.getGroupId())
                .reactionId(groupreactionDTOMock.getReactionId())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupReaction groupreactionSavedMock = GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                .id(groupreactionDTOMock.getId())
                .groupId(groupreactionDTOMock.getGroupId())
                .reactionId(groupreactionDTOMock.getReactionId())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(groupreactionRepositoryMock.save(groupreactionMock)).thenReturn(groupreactionSavedMock);

        // action
        GroupReactionDTO groupreactionSaved = groupreactionService.salvar(groupreactionDTOMock);

        // validate
        Assertions.assertInstanceOf(GroupReactionDTO.class, groupreactionSaved);
        Assertions.assertNotNull(groupreactionSaved.getId());
    }

    @Test
    public void ShouldSaveNewGroupReactionWithSucess() {
        // scenario
        GroupReactionDTO groupreactionDTOMock = GroupReactionDTOBuilder.newGroupReactionDTOTestBuilder()
                .id(null)
                .groupId(28340L)
                .reactionId(14032L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupReaction groupreactionModelMock = GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                .id(null)
                .groupId(groupreactionDTOMock.getGroupId())
                .reactionId(groupreactionDTOMock.getReactionId())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupReaction groupreactionSavedMock = GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                .id(501L)
                .groupId(groupreactionDTOMock.getGroupId())
                .reactionId(groupreactionDTOMock.getReactionId())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(groupreactionRepositoryMock.save(groupreactionModelMock)).thenReturn(groupreactionSavedMock);

        // action
        GroupReactionDTO groupreactionSaved = groupreactionService.salvar(groupreactionDTOMock);

        // validate
        Assertions.assertInstanceOf(GroupReactionDTO.class, groupreactionSaved);
        Assertions.assertNotNull(groupreactionSaved.getId());
        Assertions.assertEquals("P",groupreactionSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapGroupReactionDTOMock = new HashMap<>();
        mapGroupReactionDTOMock.put(GroupReactionConstantes.GROUPID,11450L);
        mapGroupReactionDTOMock.put(GroupReactionConstantes.REACTIONID,20065L);
        mapGroupReactionDTOMock.put(GroupReactionConstantes.STATUS,"B8LUsINpqzrKLKUszP6eAudC9R0ozFtSQRMDpNDC2MeXQeb0ur");


        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(
                GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                        .id(68886L)
                        .groupId(37670L)
                        .reactionId(51252L)
                        .status("uzCM82fprLsyUkmqg3bTrkWTi1boLCapt3EEIXhxhJyFK54IYg")

                        .now()
        );

        Mockito.when(groupreactionRepositoryMock.findById(1L)).thenReturn(groupreactionModelMock);

        // action
        boolean executed = groupreactionService.partialUpdate(1L, mapGroupReactionDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnGroupReactionNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapGroupReactionDTOMock = new HashMap<>();
        mapGroupReactionDTOMock.put(GroupReactionConstantes.GROUPID,1151L);
        mapGroupReactionDTOMock.put(GroupReactionConstantes.REACTIONID,10810L);
        mapGroupReactionDTOMock.put(GroupReactionConstantes.STATUS,"QQ6NHwe0wlzA0lqwoVB9e92SNkwVIPHqxRdup3Q5Se1Dx42M8n");


        Mockito.when(groupreactionRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.partialUpdate(1L, mapGroupReactionDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("GroupReaction não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnGroupReactionListWhenFindAllGroupReactionByIdAndStatus() {
        // scenario
        List<GroupReaction> groupreactions = Arrays.asList(
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now()
        );

        Mockito.when(groupreactionRepositoryMock.findAllByIdAndStatus(17682L, "A")).thenReturn(groupreactions);

        // action
        List<GroupReactionDTO> result = groupreactionService.findAllGroupReactionByIdAndStatus(17682L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupReactionListWhenFindAllGroupReactionByGroupIdAndStatus() {
        // scenario
        List<GroupReaction> groupreactions = Arrays.asList(
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now()
        );

        Mockito.when(groupreactionRepositoryMock.findAllByGroupIdAndStatus(8555L, "A")).thenReturn(groupreactions);

        // action
        List<GroupReactionDTO> result = groupreactionService.findAllGroupReactionByGroupIdAndStatus(8555L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupReactionListWhenFindAllGroupReactionByReactionIdAndStatus() {
        // scenario
        List<GroupReaction> groupreactions = Arrays.asList(
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now()
        );

        Mockito.when(groupreactionRepositoryMock.findAllByReactionIdAndStatus(32051L, "A")).thenReturn(groupreactions);

        // action
        List<GroupReactionDTO> result = groupreactionService.findAllGroupReactionByReactionIdAndStatus(32051L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupReactionListWhenFindAllGroupReactionByDateCreatedAndStatus() {
        // scenario
        List<GroupReaction> groupreactions = Arrays.asList(
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now()
        );

        Mockito.when(groupreactionRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(groupreactions);

        // action
        List<GroupReactionDTO> result = groupreactionService.findAllGroupReactionByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupReactionListWhenFindAllGroupReactionByDateUpdatedAndStatus() {
        // scenario
        List<GroupReaction> groupreactions = Arrays.asList(
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now(),
            GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now()
        );

        Mockito.when(groupreactionRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(groupreactions);

        // action
        List<GroupReactionDTO> result = groupreactionService.findAllGroupReactionByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentGroupReactionDTOWhenFindGroupReactionByIdAndStatus() {
        // scenario
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByIdAndStatus(50781L, "A")).thenReturn(1L);
        Mockito.when(groupreactionRepositoryMock.findById(1L)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionDTO result = groupreactionService.findGroupReactionByIdAndStatus(50781L, "A");

        // validate
        Assertions.assertInstanceOf(GroupReactionDTO.class,result);
    }
    @Test
    public void shouldReturnGroupReactionNotFoundExceptionWhenNonExistenceGroupReactionIdAndStatus() {
        // scenario
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByIdAndStatus(50781L, "A")).thenReturn(0L);
        Mockito.when(groupreactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.findGroupReactionByIdAndStatus(50781L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentGroupReactionDTOWhenFindGroupReactionByGroupIdAndStatus() {
        // scenario
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByGroupIdAndStatus(26641L, "A")).thenReturn(1L);
        Mockito.when(groupreactionRepositoryMock.findById(1L)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionDTO result = groupreactionService.findGroupReactionByGroupIdAndStatus(26641L, "A");

        // validate
        Assertions.assertInstanceOf(GroupReactionDTO.class,result);
    }
    @Test
    public void shouldReturnGroupReactionNotFoundExceptionWhenNonExistenceGroupReactionGroupIdAndStatus() {
        // scenario
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByGroupIdAndStatus(26641L, "A")).thenReturn(0L);
        Mockito.when(groupreactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.findGroupReactionByGroupIdAndStatus(26641L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_GROUPID));
    }
    @Test
    public void shouldReturnExistentGroupReactionDTOWhenFindGroupReactionByReactionIdAndStatus() {
        // scenario
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder().now());
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByReactionIdAndStatus(66152L, "A")).thenReturn(1L);
        Mockito.when(groupreactionRepositoryMock.findById(1L)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionDTO result = groupreactionService.findGroupReactionByReactionIdAndStatus(66152L, "A");

        // validate
        Assertions.assertInstanceOf(GroupReactionDTO.class,result);
    }
    @Test
    public void shouldReturnGroupReactionNotFoundExceptionWhenNonExistenceGroupReactionReactionIdAndStatus() {
        // scenario
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByReactionIdAndStatus(66152L, "A")).thenReturn(0L);
        Mockito.when(groupreactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.findGroupReactionByReactionIdAndStatus(66152L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_REACTIONID));
    }

    @Test
    public void shouldReturnGroupReactionDTOWhenUpdateExistingGroupIdById() {
        // scenario
        Long groupIdUpdateMock = 32371L;
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(groupreactionRepositoryMock.findById(420L)).thenReturn(groupreactionModelMock);
        Mockito.doNothing().when(groupreactionRepositoryMock).updateGroupIdById(420L, groupIdUpdateMock);

        // action
        groupreactionService.updateGroupIdById(420L, groupIdUpdateMock);

        // validate
        Mockito.verify(groupreactionRepositoryMock,Mockito.times(1)).updateGroupIdById(420L, groupIdUpdateMock);
    }
    @Test
    public void shouldReturnGroupReactionDTOWhenUpdateExistingReactionIdById() {
        // scenario
        Long reactionIdUpdateMock = 72841L;
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(groupreactionRepositoryMock.findById(420L)).thenReturn(groupreactionModelMock);
        Mockito.doNothing().when(groupreactionRepositoryMock).updateReactionIdById(420L, reactionIdUpdateMock);

        // action
        groupreactionService.updateReactionIdById(420L, reactionIdUpdateMock);

        // validate
        Mockito.verify(groupreactionRepositoryMock,Mockito.times(1)).updateReactionIdById(420L, reactionIdUpdateMock);
    }



    @Test
    public void showReturnExistingGroupReactionDTOWhenFindGroupReactionByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 3571L;
        Long maxIdMock = 1972L;
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(groupreactionRepositoryMock.findById(maxIdMock)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionDTO result = groupreactionService.findGroupReactionByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnGroupReactionNotFoundExceptionWhenNonExistenceFindGroupReactionByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 3571L;
        Long noMaxIdMock = 0L;
        Optional<GroupReaction> groupreactionModelMock = Optional.empty();
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(groupreactionRepositoryMock.findById(noMaxIdMock)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.findGroupReactionByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGroupReactionDTOWhenFindGroupReactionByGroupIdAndStatusActiveAnonimous() {
        // scenario
        Long groupIdMock = 14800L;
        Long maxIdMock = 1972L;
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                .groupId(groupIdMock)
                .now()
        );
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByGroupIdAndStatus(groupIdMock, "A")).thenReturn(maxIdMock);
        Mockito.when(groupreactionRepositoryMock.findById(maxIdMock)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionDTO result = groupreactionService.findGroupReactionByGroupIdAndStatus(groupIdMock);

        // validate
        Assertions.assertEquals(groupIdMock, result.getGroupId());

    }
    @Test
    public void showReturnGroupReactionNotFoundExceptionWhenNonExistenceFindGroupReactionByGroupIdAndStatusActiveAnonimous() {
        // scenario
        Long groupIdMock = 14800L;
        Long noMaxIdMock = 0L;
        Optional<GroupReaction> groupreactionModelMock = Optional.empty();
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByGroupIdAndStatus(groupIdMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(groupreactionRepositoryMock.findById(noMaxIdMock)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.findGroupReactionByGroupIdAndStatus(groupIdMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_GROUPID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGroupReactionDTOWhenFindGroupReactionByReactionIdAndStatusActiveAnonimous() {
        // scenario
        Long reactionIdMock = 30031L;
        Long maxIdMock = 1972L;
        Optional<GroupReaction> groupreactionModelMock = Optional.ofNullable(GroupReactionModelBuilder.newGroupReactionModelTestBuilder()
                .reactionId(reactionIdMock)
                .now()
        );
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByReactionIdAndStatus(reactionIdMock, "A")).thenReturn(maxIdMock);
        Mockito.when(groupreactionRepositoryMock.findById(maxIdMock)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionDTO result = groupreactionService.findGroupReactionByReactionIdAndStatus(reactionIdMock);

        // validate
        Assertions.assertEquals(reactionIdMock, result.getReactionId());

    }
    @Test
    public void showReturnGroupReactionNotFoundExceptionWhenNonExistenceFindGroupReactionByReactionIdAndStatusActiveAnonimous() {
        // scenario
        Long reactionIdMock = 30031L;
        Long noMaxIdMock = 0L;
        Optional<GroupReaction> groupreactionModelMock = Optional.empty();
        Mockito.when(groupreactionRepositoryMock.loadMaxIdByReactionIdAndStatus(reactionIdMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(groupreactionRepositoryMock.findById(noMaxIdMock)).thenReturn(groupreactionModelMock);

        // action
        GroupReactionNotFoundException exception = Assertions.assertThrows(GroupReactionNotFoundException.class,
                ()->groupreactionService.findGroupReactionByReactionIdAndStatus(reactionIdMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPREACTION_NOTFOUND_WITH_REACTIONID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

