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
import br.com.jcv.security.guardian.builder.GroupDTOBuilder;
import br.com.jcv.security.guardian.builder.GroupModelBuilder;
import br.com.jcv.security.guardian.dto.GroupDTO;
import br.com.jcv.security.guardian.exception.GroupNotFoundException;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.Group;
import br.com.jcv.security.guardian.repository.GroupRepository;
import br.com.jcv.security.guardian.service.GroupService;
import br.com.jcv.security.guardian.constantes.GroupConstantes;
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
public class GroupServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String GROUP_NOTFOUND_WITH_ID = "Group não encontrada com id = ";
    public static final String GROUP_NOTFOUND_WITH_NAME = "Group não encontrada com name = ";
    public static final String GROUP_NOTFOUND_WITH_STATUS = "Group não encontrada com status = ";
    public static final String GROUP_NOTFOUND_WITH_DATECREATED = "Group não encontrada com dateCreated = ";
    public static final String GROUP_NOTFOUND_WITH_DATEUPDATED = "Group não encontrada com dateUpdated = ";


    @Mock
    private GroupRepository groupRepositoryMock;
    @Mock
    private CacheProvider redisProviderMock;
    @Mock
    private Gson gsonMock;
    @InjectMocks
    private GroupService groupService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        groupService = new GroupServiceImpl();
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
    public void shouldReturnListOfGroupWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 80683L;
        String name ="03560a92-70cf-41f9-a9c2-d509fc50a60d";
        String status = "0W0t0XbgQWDPU0LUEljCRtkEQ0Ue22LmgIFyfGmc7poxH59IIB";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Group> groupsFromRepository = new ArrayList<>();
        groupsFromRepository.add(GroupModelBuilder.newGroupModelTestBuilder().now());
        groupsFromRepository.add(GroupModelBuilder.newGroupModelTestBuilder().now());
        groupsFromRepository.add(GroupModelBuilder.newGroupModelTestBuilder().now());
        groupsFromRepository.add(GroupModelBuilder.newGroupModelTestBuilder().now());

        Mockito.when(groupRepositoryMock.findGroupByFilter(
            id,
            name,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(groupsFromRepository);

        // action
        List<GroupDTO> result = groupService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithGroupListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 76070L;
        String name ="6adbdb33-18ca-4d73-84bb-abecdfa11f4f";
        String status = "P6vmMszdQMBvl11y6UDJ02uHP7amSF0zcXNB7ofYuYBEYTz8sX";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Group> groupsFromRepository = new ArrayList<>();
        groupsFromRepository.add(GroupModelBuilder.newGroupModelTestBuilder().now());
        groupsFromRepository.add(GroupModelBuilder.newGroupModelTestBuilder().now());
        groupsFromRepository.add(GroupModelBuilder.newGroupModelTestBuilder().now());
        groupsFromRepository.add(GroupModelBuilder.newGroupModelTestBuilder().now());

        List<GroupDTO> groupsFiltered = groupsFromRepository
                .stream()
                .map(m->groupService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageGroupItems", groupsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<Group> pagedResponse =
                new PageImpl<>(groupsFromRepository,
                        pageableMock,
                        groupsFromRepository.size());

        Mockito.when(groupRepositoryMock.findGroupByFilter(pageableMock,
            id,
            name,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = groupService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<GroupDTO> groupsResult = (List<GroupDTO>) result.get("pageGroupItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, groupsResult.size());
    }


    @Test
    public void showReturnListOfGroupWhenAskedForFindAllByStatus() {
        // scenario
        List<Group> listOfGroupModelMock = new ArrayList<>();
        listOfGroupModelMock.add(GroupModelBuilder.newGroupModelTestBuilder().now());
        listOfGroupModelMock.add(GroupModelBuilder.newGroupModelTestBuilder().now());

        Mockito.when(groupRepositoryMock.findAllByStatus("A")).thenReturn(listOfGroupModelMock);

        // action
        List<GroupDTO> listOfGroups = groupService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfGroups.isEmpty());
        Assertions.assertEquals(2, listOfGroups.size());
    }
    @Test
    public void shouldReturnGroupNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 32015L;
        Optional<Group> groupNonExistentMock = Optional.empty();
        Mockito.when(groupRepositoryMock.findById(idMock)).thenReturn(groupNonExistentMock);

        // action
        GroupNotFoundException exception = Assertions.assertThrows(GroupNotFoundException.class,
                ()->groupService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUP_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowGroupNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 77076L;
        Mockito.when(groupRepositoryMock.findById(idMock))
                .thenThrow(new GroupNotFoundException(GROUP_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                GROUP_NOTFOUND_WITH_ID ));

        // action
        GroupNotFoundException exception = Assertions.assertThrows(GroupNotFoundException.class,
                ()->groupService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUP_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnGroupDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 57440L;
        Optional<Group> groupModelMock = Optional.ofNullable(
                GroupModelBuilder.newGroupModelTestBuilder()
                        .id(idMock)
                        .name("29204932-8967-463f-a1f2-a86204f277ec")
                        .status("X")
                        .now()
        );
        Group groupToSaveMock = groupModelMock.orElse(null);
        Group groupSavedMck = GroupModelBuilder.newGroupModelTestBuilder()
                        .id(57756L)
                        .name("19747ebc-3112-4c09-9f8a-8dd084f591a6")
                        .status("A")
                        .now();
        Mockito.when(groupRepositoryMock.findById(idMock)).thenReturn(groupModelMock);
        Mockito.when(groupRepositoryMock.save(groupToSaveMock)).thenReturn(groupSavedMck);

        // action
        GroupDTO result = groupService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchGroupByAnyNonExistenceIdAndReturnGroupNotFoundException() {
        // scenario
        Mockito.when(groupRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GroupNotFoundException exception = Assertions.assertThrows(GroupNotFoundException.class,
                ()-> groupService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUP_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchGroupByIdAndReturnDTO() {
        // scenario
        Optional<Group> groupModelMock = Optional.ofNullable(GroupModelBuilder.newGroupModelTestBuilder()
                .id(37505L)
                .name("07613c09-e207-47ff-8aa1-3a5c8bd74e05")
                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(groupRepositoryMock.findById(Mockito.anyLong())).thenReturn(groupModelMock);

        // action
        GroupDTO result = groupService.findById(1L);

        // validate
        Assertions.assertInstanceOf(GroupDTO.class,result);
    }
    @Test
    public void shouldDeleteGroupByIdWithSucess() {
        // scenario
        Optional<Group> group = Optional.ofNullable(GroupModelBuilder.newGroupModelTestBuilder().id(1L).now());
        Mockito.when(groupRepositoryMock.findById(Mockito.anyLong())).thenReturn(group);

        // action
        groupService.delete(1L);

        // validate
        Mockito.verify(groupRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceGroupShouldReturnGroupNotFoundException() {
        // scenario
        Mockito.when(groupRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GroupNotFoundException exception = Assertions.assertThrows(
                GroupNotFoundException.class, () -> groupService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUP_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingGroupWithSucess() {
        // scenario
        GroupDTO groupDTOMock = GroupDTOBuilder.newGroupDTOTestBuilder()
                .id(38165L)
                .name("a3da1330-b5ca-46b3-9937-eb4886d7636e")
                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Group groupMock = GroupModelBuilder.newGroupModelTestBuilder()
                .id(groupDTOMock.getId())
                .name(groupDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Group groupSavedMock = GroupModelBuilder.newGroupModelTestBuilder()
                .id(groupDTOMock.getId())
                .name(groupDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(groupRepositoryMock.save(groupMock)).thenReturn(groupSavedMock);

        // action
        GroupDTO groupSaved = groupService.salvar(groupDTOMock);

        // validate
        Assertions.assertInstanceOf(GroupDTO.class, groupSaved);
        Assertions.assertNotNull(groupSaved.getId());
    }

    @Test
    public void ShouldSaveNewGroupWithSucess() {
        // scenario
        GroupDTO groupDTOMock = GroupDTOBuilder.newGroupDTOTestBuilder()
                .id(null)
                .name("53a87d38-0dd2-48f7-a25b-e0f7a632d777")
                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Group groupModelMock = GroupModelBuilder.newGroupModelTestBuilder()
                .id(null)
                .name(groupDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Group groupSavedMock = GroupModelBuilder.newGroupModelTestBuilder()
                .id(501L)
                .name(groupDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(groupRepositoryMock.save(groupModelMock)).thenReturn(groupSavedMock);

        // action
        GroupDTO groupSaved = groupService.salvar(groupDTOMock);

        // validate
        Assertions.assertInstanceOf(GroupDTO.class, groupSaved);
        Assertions.assertNotNull(groupSaved.getId());
        Assertions.assertEquals("P",groupSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapGroupDTOMock = new HashMap<>();
        mapGroupDTOMock.put(GroupConstantes.NAME,UUID.fromString("720d26cc-fdc5-4818-87c2-11fc8c4c1000"));
        mapGroupDTOMock.put(GroupConstantes.STATUS,"M2mlYHnlUtXpgBLanpbRl9dkoFkn12A0kv77sr7C9yP0f6LfG1");


        Optional<Group> groupModelMock = Optional.ofNullable(
                GroupModelBuilder.newGroupModelTestBuilder()
                        .id(147L)
                        .name("7acde3c6-af62-4745-b3c0-380964a5efb5")
                        .status("pjx60iWaF2MAsbqTAtRdxFyKUWjTLwWOit4cEwrrTavgxtbvlU")

                        .now()
        );

        Mockito.when(groupRepositoryMock.findById(1L)).thenReturn(groupModelMock);

        // action
        boolean executed = groupService.partialUpdate(1L, mapGroupDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnGroupNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapGroupDTOMock = new HashMap<>();
        mapGroupDTOMock.put(GroupConstantes.NAME,UUID.fromString("3192ccc8-86f7-479a-aec3-dac60d41a079"));
        mapGroupDTOMock.put(GroupConstantes.STATUS,"U0Tcu8FqoQSGd7bUwdcfA3tnPo7wBYUKEgcUOVk0UyiHDRfxnS");


        Mockito.when(groupRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        GroupNotFoundException exception = Assertions.assertThrows(GroupNotFoundException.class,
                ()->groupService.partialUpdate(1L, mapGroupDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("Group não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnGroupListWhenFindAllGroupByIdAndStatus() {
        // scenario
        List<Group> groups = Arrays.asList(
            GroupModelBuilder.newGroupModelTestBuilder().now(),
            GroupModelBuilder.newGroupModelTestBuilder().now(),
            GroupModelBuilder.newGroupModelTestBuilder().now()
        );

        Mockito.when(groupRepositoryMock.findAllByIdAndStatus(40240L, "A")).thenReturn(groups);

        // action
        List<GroupDTO> result = groupService.findAllGroupByIdAndStatus(40240L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupListWhenFindAllGroupByNameAndStatus() {
        // scenario
        List<Group> groups = Arrays.asList(
            GroupModelBuilder.newGroupModelTestBuilder().now(),
            GroupModelBuilder.newGroupModelTestBuilder().now(),
            GroupModelBuilder.newGroupModelTestBuilder().now()
        );

        Mockito.when(groupRepositoryMock.findAllByNameAndStatus("41b8a72e-e2af-412b-9763-0a9ac0008e68", "A")).thenReturn(groups);

        // action
        List<GroupDTO> result = groupService.findAllGroupByNameAndStatus("41b8a72e-e2af-412b-9763-0a9ac0008e68", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupListWhenFindAllGroupByDateCreatedAndStatus() {
        // scenario
        List<Group> groups = Arrays.asList(
            GroupModelBuilder.newGroupModelTestBuilder().now(),
            GroupModelBuilder.newGroupModelTestBuilder().now(),
            GroupModelBuilder.newGroupModelTestBuilder().now()
        );

        Mockito.when(groupRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(groups);

        // action
        List<GroupDTO> result = groupService.findAllGroupByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupListWhenFindAllGroupByDateUpdatedAndStatus() {
        // scenario
        List<Group> groups = Arrays.asList(
            GroupModelBuilder.newGroupModelTestBuilder().now(),
            GroupModelBuilder.newGroupModelTestBuilder().now(),
            GroupModelBuilder.newGroupModelTestBuilder().now()
        );

        Mockito.when(groupRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(groups);

        // action
        List<GroupDTO> result = groupService.findAllGroupByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentGroupDTOWhenFindGroupByIdAndStatus() {
        // scenario
        Optional<Group> groupModelMock = Optional.ofNullable(GroupModelBuilder.newGroupModelTestBuilder().now());
        Mockito.when(groupRepositoryMock.loadMaxIdByIdAndStatus(2502L, "A")).thenReturn(1L);
        Mockito.when(groupRepositoryMock.findById(1L)).thenReturn(groupModelMock);

        // action
        GroupDTO result = groupService.findGroupByIdAndStatus(2502L, "A");

        // validate
        Assertions.assertInstanceOf(GroupDTO.class,result);
    }
    @Test
    public void shouldReturnGroupNotFoundExceptionWhenNonExistenceGroupIdAndStatus() {
        // scenario
        Mockito.when(groupRepositoryMock.loadMaxIdByIdAndStatus(2502L, "A")).thenReturn(0L);
        Mockito.when(groupRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupNotFoundException exception = Assertions.assertThrows(GroupNotFoundException.class,
                ()->groupService.findGroupByIdAndStatus(2502L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUP_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentGroupDTOWhenFindGroupByNameAndStatus() {
        // scenario
        Optional<Group> groupModelMock = Optional.ofNullable(GroupModelBuilder.newGroupModelTestBuilder().now());
        Mockito.when(groupRepositoryMock.loadMaxIdByNameAndStatus("1bb0d517-47a0-4177-9cc7-3edca3cf78a7", "A")).thenReturn(1L);
        Mockito.when(groupRepositoryMock.findById(1L)).thenReturn(groupModelMock);

        // action
        GroupDTO result = groupService.findGroupByNameAndStatus("1bb0d517-47a0-4177-9cc7-3edca3cf78a7", "A");

        // validate
        Assertions.assertInstanceOf(GroupDTO.class,result);
    }
    @Test
    public void shouldReturnGroupNotFoundExceptionWhenNonExistenceGroupNameAndStatus() {
        // scenario
        Mockito.when(groupRepositoryMock.loadMaxIdByNameAndStatus("1bb0d517-47a0-4177-9cc7-3edca3cf78a7", "A")).thenReturn(0L);
        Mockito.when(groupRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupNotFoundException exception = Assertions.assertThrows(GroupNotFoundException.class,
                ()->groupService.findGroupByNameAndStatus("1bb0d517-47a0-4177-9cc7-3edca3cf78a7", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUP_NOTFOUND_WITH_NAME));
    }

    @Test
    public void shouldReturnGroupDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "9967b030-7252-42d8-b20a-971787596f86";
        Optional<Group> groupModelMock = Optional.ofNullable(GroupModelBuilder.newGroupModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(groupRepositoryMock.findById(420L)).thenReturn(groupModelMock);
        Mockito.doNothing().when(groupRepositoryMock).updateNameById(420L, nameUpdateMock);

        // action
        groupService.updateNameById(420L, nameUpdateMock);

        // validate
        Mockito.verify(groupRepositoryMock,Mockito.times(1)).updateNameById(420L, nameUpdateMock);
    }



    @Test
    public void showReturnExistingGroupDTOWhenFindGroupByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 14840L;
        Long maxIdMock = 1972L;
        Optional<Group> groupModelMock = Optional.ofNullable(GroupModelBuilder.newGroupModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(groupRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(groupRepositoryMock.findById(maxIdMock)).thenReturn(groupModelMock);

        // action
        GroupDTO result = groupService.findGroupByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnGroupNotFoundExceptionWhenNonExistenceFindGroupByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 14840L;
        Long noMaxIdMock = 0L;
        Optional<Group> groupModelMock = Optional.empty();
        Mockito.when(groupRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(groupRepositoryMock.findById(noMaxIdMock)).thenReturn(groupModelMock);

        // action
        GroupNotFoundException exception = Assertions.assertThrows(GroupNotFoundException.class,
                ()->groupService.findGroupByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUP_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGroupDTOWhenFindGroupByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "478c972b-7322-41d3-9120-3791e514c222";
        Long maxIdMock = 1972L;
        Optional<Group> groupModelMock = Optional.ofNullable(GroupModelBuilder.newGroupModelTestBuilder()
                .name(nameMock)
                .now()
        );
        Mockito.when(groupRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(maxIdMock);
        Mockito.when(groupRepositoryMock.findById(maxIdMock)).thenReturn(groupModelMock);

        // action
        GroupDTO result = groupService.findGroupByNameAndStatus(nameMock);

        // validate
        Assertions.assertEquals(nameMock, result.getName());

    }
    @Test
    public void showReturnGroupNotFoundExceptionWhenNonExistenceFindGroupByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "478c972b-7322-41d3-9120-3791e514c222";
        Long noMaxIdMock = 0L;
        Optional<Group> groupModelMock = Optional.empty();
        Mockito.when(groupRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(groupRepositoryMock.findById(noMaxIdMock)).thenReturn(groupModelMock);

        // action
        GroupNotFoundException exception = Assertions.assertThrows(GroupNotFoundException.class,
                ()->groupService.findGroupByNameAndStatus(nameMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUP_NOTFOUND_WITH_NAME));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

