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
import br.com.jcv.security.guardian.builder.GroupRoleDTOBuilder;
import br.com.jcv.security.guardian.builder.GroupRoleModelBuilder;
import br.com.jcv.security.guardian.dto.GroupRoleDTO;
import br.com.jcv.security.guardian.exception.GroupRoleNotFoundException;
import br.com.jcv.security.guardian.model.GroupRole;
import br.com.jcv.security.guardian.repository.GroupRoleRepository;
import br.com.jcv.security.guardian.service.GroupRoleService;
import br.com.jcv.security.guardian.constantes.GroupRoleConstantes;
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
public class GroupRoleServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String GROUPROLE_NOTFOUND_WITH_ID = "GroupRole não encontrada com id = ";
    public static final String GROUPROLE_NOTFOUND_WITH_IDROLE = "GroupRole não encontrada com idRole = ";
    public static final String GROUPROLE_NOTFOUND_WITH_IDGROUP = "GroupRole não encontrada com idGroup = ";
    public static final String GROUPROLE_NOTFOUND_WITH_STATUS = "GroupRole não encontrada com status = ";
    public static final String GROUPROLE_NOTFOUND_WITH_DATECREATED = "GroupRole não encontrada com dateCreated = ";
    public static final String GROUPROLE_NOTFOUND_WITH_DATEUPDATED = "GroupRole não encontrada com dateUpdated = ";


    @Mock
    private GroupRoleRepository grouproleRepositoryMock;

    @InjectMocks
    private GroupRoleService grouproleService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        grouproleService = new GroupRoleServiceImpl();
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
    public void shouldReturnListOfGroupRoleWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 82043L;
        Long idRole = 1250L;
        Long idGroup = 63016L;
        String status = "H0sQn8SdJWjtwnodkrCe8LhzNwrAIror0OO8nmxJIHcLydi5Dh";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idRole", idRole);
        mapFieldsRequestMock.put("idGroup", idGroup);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<GroupRole> grouprolesFromRepository = new ArrayList<>();
        grouprolesFromRepository.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        grouprolesFromRepository.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        grouprolesFromRepository.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        grouprolesFromRepository.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());

        Mockito.when(grouproleRepositoryMock.findGroupRoleByFilter(
            id,
            idRole,
            idGroup,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(grouprolesFromRepository);

        // action
        List<GroupRoleDTO> result = grouproleService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithGroupRoleListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 26062L;
        Long idRole = 86608L;
        Long idGroup = 41412L;
        String status = "SyKrEzXhNHXaF4wcuHTHrOcL46gFcNjodXoVkpsmKqNjn9N7oP";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idRole", idRole);
        mapFieldsRequestMock.put("idGroup", idGroup);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<GroupRole> grouprolesFromRepository = new ArrayList<>();
        grouprolesFromRepository.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        grouprolesFromRepository.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        grouprolesFromRepository.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        grouprolesFromRepository.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());

        List<GroupRoleDTO> grouprolesFiltered = grouprolesFromRepository
                .stream()
                .map(m->grouproleService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageGroupRoleItems", grouprolesFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<GroupRole> pagedResponse =
                new PageImpl<>(grouprolesFromRepository,
                        pageableMock,
                        grouprolesFromRepository.size());

        Mockito.when(grouproleRepositoryMock.findGroupRoleByFilter(pageableMock,
            id,
            idRole,
            idGroup,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = grouproleService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<GroupRoleDTO> grouprolesResult = (List<GroupRoleDTO>) result.get("pageGroupRoleItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, grouprolesResult.size());
    }


    @Test
    public void showReturnListOfGroupRoleWhenAskedForFindAllByStatus() {
        // scenario
        List<GroupRole> listOfGroupRoleModelMock = new ArrayList<>();
        listOfGroupRoleModelMock.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        listOfGroupRoleModelMock.add(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());

        Mockito.when(grouproleRepositoryMock.findAllByStatus("A")).thenReturn(listOfGroupRoleModelMock);

        // action
        List<GroupRoleDTO> listOfGroupRoles = grouproleService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfGroupRoles.isEmpty());
        Assertions.assertEquals(2, listOfGroupRoles.size());
    }
    @Test
    public void shouldReturnGroupRoleNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 20867L;
        Optional<GroupRole> grouproleNonExistentMock = Optional.empty();
        Mockito.when(grouproleRepositoryMock.findById(idMock)).thenReturn(grouproleNonExistentMock);

        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowGroupRoleNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 1118L;
        Mockito.when(grouproleRepositoryMock.findById(idMock))
                .thenThrow(new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                GROUPROLE_NOTFOUND_WITH_ID ));

        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnGroupRoleDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 80505L;
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(
                GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                        .id(idMock)
                        .idRole(33070L)
                        .idGroup(203L)

                        .status("X")
                        .now()
        );
        GroupRole grouproleToSaveMock = grouproleModelMock.orElse(null);
        GroupRole grouproleSavedMck = GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                        .id(73850L)
                        .idRole(30203L)
                        .idGroup(7036L)

                        .status("A")
                        .now();
        Mockito.when(grouproleRepositoryMock.findById(idMock)).thenReturn(grouproleModelMock);
        Mockito.when(grouproleRepositoryMock.save(grouproleToSaveMock)).thenReturn(grouproleSavedMck);

        // action
        GroupRoleDTO result = grouproleService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchGroupRoleByAnyNonExistenceIdAndReturnGroupRoleNotFoundException() {
        // scenario
        Mockito.when(grouproleRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()-> grouproleService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchGroupRoleByIdAndReturnDTO() {
        // scenario
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                .id(83482L)
                .idRole(88174L)
                .idGroup(68208L)

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(grouproleRepositoryMock.findById(Mockito.anyLong())).thenReturn(grouproleModelMock);

        // action
        GroupRoleDTO result = grouproleService.findById(1L);

        // validate
        Assertions.assertInstanceOf(GroupRoleDTO.class,result);
    }
    @Test
    public void shouldDeleteGroupRoleByIdWithSucess() {
        // scenario
        Optional<GroupRole> grouprole = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().id(1L).now());
        Mockito.when(grouproleRepositoryMock.findById(Mockito.anyLong())).thenReturn(grouprole);

        // action
        grouproleService.delete(1L);

        // validate
        Mockito.verify(grouproleRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceGroupRoleShouldReturnGroupRoleNotFoundException() {
        // scenario
        Mockito.when(grouproleRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(
                GroupRoleNotFoundException.class, () -> grouproleService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingGroupRoleWithSucess() {
        // scenario
        GroupRoleDTO grouproleDTOMock = GroupRoleDTOBuilder.newGroupRoleDTOTestBuilder()
                .id(25633L)
                .idRole(76048L)
                .idGroup(82360L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupRole grouproleMock = GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                .id(grouproleDTOMock.getId())
                .idRole(grouproleDTOMock.getIdRole())
                .idGroup(grouproleDTOMock.getIdGroup())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupRole grouproleSavedMock = GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                .id(grouproleDTOMock.getId())
                .idRole(grouproleDTOMock.getIdRole())
                .idGroup(grouproleDTOMock.getIdGroup())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(grouproleRepositoryMock.save(grouproleMock)).thenReturn(grouproleSavedMock);

        // action
        GroupRoleDTO grouproleSaved = grouproleService.salvar(grouproleDTOMock);

        // validate
        Assertions.assertInstanceOf(GroupRoleDTO.class, grouproleSaved);
        Assertions.assertNotNull(grouproleSaved.getId());
    }

    @Test
    public void ShouldSaveNewGroupRoleWithSucess() {
        // scenario
        GroupRoleDTO grouproleDTOMock = GroupRoleDTOBuilder.newGroupRoleDTOTestBuilder()
                .id(null)
                .idRole(27280L)
                .idGroup(23856L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupRole grouproleModelMock = GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                .id(null)
                .idRole(grouproleDTOMock.getIdRole())
                .idGroup(grouproleDTOMock.getIdGroup())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupRole grouproleSavedMock = GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                .id(501L)
                .idRole(grouproleDTOMock.getIdRole())
                .idGroup(grouproleDTOMock.getIdGroup())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(grouproleRepositoryMock.save(grouproleModelMock)).thenReturn(grouproleSavedMock);

        // action
        GroupRoleDTO grouproleSaved = grouproleService.salvar(grouproleDTOMock);

        // validate
        Assertions.assertInstanceOf(GroupRoleDTO.class, grouproleSaved);
        Assertions.assertNotNull(grouproleSaved.getId());
        Assertions.assertEquals("P",grouproleSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapGroupRoleDTOMock = new HashMap<>();
        mapGroupRoleDTOMock.put(GroupRoleConstantes.IDROLE,20535L);
        mapGroupRoleDTOMock.put(GroupRoleConstantes.IDGROUP,70560L);
        mapGroupRoleDTOMock.put(GroupRoleConstantes.STATUS,"EYqmrfyKO2crvvDk7SqKt26SiLIt2wvX7y0GvXP9sdJkOk7nkc");


        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(
                GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                        .id(57218L)
                        .idRole(5460L)
                        .idGroup(50341L)
                        .status("n6NIUcYEfrvORmVzvaEyIa4L6NICuqYeXbYRg8kCDgCcPA6L73")

                        .now()
        );

        Mockito.when(grouproleRepositoryMock.findById(1L)).thenReturn(grouproleModelMock);

        // action
        boolean executed = grouproleService.partialUpdate(1L, mapGroupRoleDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnGroupRoleNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapGroupRoleDTOMock = new HashMap<>();
        mapGroupRoleDTOMock.put(GroupRoleConstantes.IDROLE,66706L);
        mapGroupRoleDTOMock.put(GroupRoleConstantes.IDGROUP,61863L);
        mapGroupRoleDTOMock.put(GroupRoleConstantes.STATUS,"hfHuDWB3l9YQIyFz6QzsxTcou8lVEp6wyhAaUR0zP5F0dBlMAM");


        Mockito.when(grouproleRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.partialUpdate(1L, mapGroupRoleDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("GroupRole não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnGroupRoleListWhenFindAllGroupRoleByIdAndStatus() {
        // scenario
        List<GroupRole> grouproles = Arrays.asList(
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now()
        );

        Mockito.when(grouproleRepositoryMock.findAllByIdAndStatus(2263L, "A")).thenReturn(grouproles);

        // action
        List<GroupRoleDTO> result = grouproleService.findAllGroupRoleByIdAndStatus(2263L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupRoleListWhenFindAllGroupRoleByIdRoleAndStatus() {
        // scenario
        List<GroupRole> grouproles = Arrays.asList(
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now()
        );

        Mockito.when(grouproleRepositoryMock.findAllByIdRoleAndStatus(72704L, "A")).thenReturn(grouproles);

        // action
        List<GroupRoleDTO> result = grouproleService.findAllGroupRoleByIdRoleAndStatus(72704L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupRoleListWhenFindAllGroupRoleByIdGroupAndStatus() {
        // scenario
        List<GroupRole> grouproles = Arrays.asList(
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now()
        );

        Mockito.when(grouproleRepositoryMock.findAllByIdGroupAndStatus(14118L, "A")).thenReturn(grouproles);

        // action
        List<GroupRoleDTO> result = grouproleService.findAllGroupRoleByIdGroupAndStatus(14118L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupRoleListWhenFindAllGroupRoleByDateCreatedAndStatus() {
        // scenario
        List<GroupRole> grouproles = Arrays.asList(
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now()
        );

        Mockito.when(grouproleRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(grouproles);

        // action
        List<GroupRoleDTO> result = grouproleService.findAllGroupRoleByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupRoleListWhenFindAllGroupRoleByDateUpdatedAndStatus() {
        // scenario
        List<GroupRole> grouproles = Arrays.asList(
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now(),
            GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now()
        );

        Mockito.when(grouproleRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(grouproles);

        // action
        List<GroupRoleDTO> result = grouproleService.findAllGroupRoleByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentGroupRoleDTOWhenFindGroupRoleByIdAndStatus() {
        // scenario
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdAndStatus(5088L, "A")).thenReturn(1L);
        Mockito.when(grouproleRepositoryMock.findById(1L)).thenReturn(grouproleModelMock);

        // action
        GroupRoleDTO result = grouproleService.findGroupRoleByIdAndStatus(5088L, "A");

        // validate
        Assertions.assertInstanceOf(GroupRoleDTO.class,result);
    }
    @Test
    public void shouldReturnGroupRoleNotFoundExceptionWhenNonExistenceGroupRoleIdAndStatus() {
        // scenario
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdAndStatus(5088L, "A")).thenReturn(0L);
        Mockito.when(grouproleRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.findGroupRoleByIdAndStatus(5088L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentGroupRoleDTOWhenFindGroupRoleByIdRoleAndStatus() {
        // scenario
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdRoleAndStatus(30214L, "A")).thenReturn(1L);
        Mockito.when(grouproleRepositoryMock.findById(1L)).thenReturn(grouproleModelMock);

        // action
        GroupRoleDTO result = grouproleService.findGroupRoleByIdRoleAndStatus(30214L, "A");

        // validate
        Assertions.assertInstanceOf(GroupRoleDTO.class,result);
    }
    @Test
    public void shouldReturnGroupRoleNotFoundExceptionWhenNonExistenceGroupRoleIdRoleAndStatus() {
        // scenario
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdRoleAndStatus(30214L, "A")).thenReturn(0L);
        Mockito.when(grouproleRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.findGroupRoleByIdRoleAndStatus(30214L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_IDROLE));
    }
    @Test
    public void shouldReturnExistentGroupRoleDTOWhenFindGroupRoleByIdGroupAndStatus() {
        // scenario
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder().now());
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdGroupAndStatus(88074L, "A")).thenReturn(1L);
        Mockito.when(grouproleRepositoryMock.findById(1L)).thenReturn(grouproleModelMock);

        // action
        GroupRoleDTO result = grouproleService.findGroupRoleByIdGroupAndStatus(88074L, "A");

        // validate
        Assertions.assertInstanceOf(GroupRoleDTO.class,result);
    }
    @Test
    public void shouldReturnGroupRoleNotFoundExceptionWhenNonExistenceGroupRoleIdGroupAndStatus() {
        // scenario
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdGroupAndStatus(88074L, "A")).thenReturn(0L);
        Mockito.when(grouproleRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.findGroupRoleByIdGroupAndStatus(88074L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_IDGROUP));
    }

    @Test
    public void shouldReturnGroupRoleDTOWhenUpdateExistingIdRoleById() {
        // scenario
        Long idRoleUpdateMock = 10035L;
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(grouproleRepositoryMock.findById(420L)).thenReturn(grouproleModelMock);
        Mockito.doNothing().when(grouproleRepositoryMock).updateIdRoleById(420L, idRoleUpdateMock);

        // action
        grouproleService.updateIdRoleById(420L, idRoleUpdateMock);

        // validate
        Mockito.verify(grouproleRepositoryMock,Mockito.times(1)).updateIdRoleById(420L, idRoleUpdateMock);
    }
    @Test
    public void shouldReturnGroupRoleDTOWhenUpdateExistingIdGroupById() {
        // scenario
        Long idGroupUpdateMock = 18031L;
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(grouproleRepositoryMock.findById(420L)).thenReturn(grouproleModelMock);
        Mockito.doNothing().when(grouproleRepositoryMock).updateIdGroupById(420L, idGroupUpdateMock);

        // action
        grouproleService.updateIdGroupById(420L, idGroupUpdateMock);

        // validate
        Mockito.verify(grouproleRepositoryMock,Mockito.times(1)).updateIdGroupById(420L, idGroupUpdateMock);
    }



    @Test
    public void showReturnExistingGroupRoleDTOWhenFindGroupRoleByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 10864L;
        Long maxIdMock = 1972L;
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(grouproleRepositoryMock.findById(maxIdMock)).thenReturn(grouproleModelMock);

        // action
        GroupRoleDTO result = grouproleService.findGroupRoleByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnGroupRoleNotFoundExceptionWhenNonExistenceFindGroupRoleByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 10864L;
        Long noMaxIdMock = 0L;
        Optional<GroupRole> grouproleModelMock = Optional.empty();
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(grouproleRepositoryMock.findById(noMaxIdMock)).thenReturn(grouproleModelMock);

        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.findGroupRoleByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGroupRoleDTOWhenFindGroupRoleByIdRoleAndStatusActiveAnonimous() {
        // scenario
        Long idRoleMock = 7162L;
        Long maxIdMock = 1972L;
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                .idRole(idRoleMock)
                .now()
        );
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdRoleAndStatus(idRoleMock, "A")).thenReturn(maxIdMock);
        Mockito.when(grouproleRepositoryMock.findById(maxIdMock)).thenReturn(grouproleModelMock);

        // action
        GroupRoleDTO result = grouproleService.findGroupRoleByIdRoleAndStatus(idRoleMock);

        // validate
        Assertions.assertEquals(idRoleMock, result.getIdRole());

    }
    @Test
    public void showReturnGroupRoleNotFoundExceptionWhenNonExistenceFindGroupRoleByIdRoleAndStatusActiveAnonimous() {
        // scenario
        Long idRoleMock = 7162L;
        Long noMaxIdMock = 0L;
        Optional<GroupRole> grouproleModelMock = Optional.empty();
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdRoleAndStatus(idRoleMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(grouproleRepositoryMock.findById(noMaxIdMock)).thenReturn(grouproleModelMock);

        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.findGroupRoleByIdRoleAndStatus(idRoleMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_IDROLE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGroupRoleDTOWhenFindGroupRoleByIdGroupAndStatusActiveAnonimous() {
        // scenario
        Long idGroupMock = 18031L;
        Long maxIdMock = 1972L;
        Optional<GroupRole> grouproleModelMock = Optional.ofNullable(GroupRoleModelBuilder.newGroupRoleModelTestBuilder()
                .idGroup(idGroupMock)
                .now()
        );
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdGroupAndStatus(idGroupMock, "A")).thenReturn(maxIdMock);
        Mockito.when(grouproleRepositoryMock.findById(maxIdMock)).thenReturn(grouproleModelMock);

        // action
        GroupRoleDTO result = grouproleService.findGroupRoleByIdGroupAndStatus(idGroupMock);

        // validate
        Assertions.assertEquals(idGroupMock, result.getIdGroup());

    }
    @Test
    public void showReturnGroupRoleNotFoundExceptionWhenNonExistenceFindGroupRoleByIdGroupAndStatusActiveAnonimous() {
        // scenario
        Long idGroupMock = 18031L;
        Long noMaxIdMock = 0L;
        Optional<GroupRole> grouproleModelMock = Optional.empty();
        Mockito.when(grouproleRepositoryMock.loadMaxIdByIdGroupAndStatus(idGroupMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(grouproleRepositoryMock.findById(noMaxIdMock)).thenReturn(grouproleModelMock);

        // action
        GroupRoleNotFoundException exception = Assertions.assertThrows(GroupRoleNotFoundException.class,
                ()->grouproleService.findGroupRoleByIdGroupAndStatus(idGroupMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPROLE_NOTFOUND_WITH_IDGROUP));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

