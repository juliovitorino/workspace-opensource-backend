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
import br.com.jcv.security.guardian.builder.RoleDTOBuilder;
import br.com.jcv.security.guardian.builder.RoleModelBuilder;
import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.exception.RoleNotFoundException;
import br.com.jcv.security.guardian.model.Role;
import br.com.jcv.security.guardian.repository.RoleRepository;
import br.com.jcv.security.guardian.service.RoleService;
import br.com.jcv.security.guardian.constantes.RoleConstantes;
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
public class RoleServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String ROLE_NOTFOUND_WITH_ID = "Role não encontrada com id = ";
    public static final String ROLE_NOTFOUND_WITH_NAME = "Role não encontrada com name = ";
    public static final String ROLE_NOTFOUND_WITH_STATUS = "Role não encontrada com status = ";
    public static final String ROLE_NOTFOUND_WITH_DATECREATED = "Role não encontrada com dateCreated = ";
    public static final String ROLE_NOTFOUND_WITH_DATEUPDATED = "Role não encontrada com dateUpdated = ";


    @Mock
    private RoleRepository roleRepositoryMock;

    @InjectMocks
    private RoleService roleService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        roleService = new RoleServiceImpl();
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
    public void shouldReturnListOfRoleWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 43L;
        String name = "98f6305a-c25a-44ff-80f9-ca81ef5ddc26";
        String status = "O0xzwUNs1jUCb5qP2Fr1QP0zOcYBzBtbBosub9sdtvFC9wvhGh";
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

        List<Role> rolesFromRepository = new ArrayList<>();
        rolesFromRepository.add(RoleModelBuilder.newRoleModelTestBuilder().now());
        rolesFromRepository.add(RoleModelBuilder.newRoleModelTestBuilder().now());
        rolesFromRepository.add(RoleModelBuilder.newRoleModelTestBuilder().now());
        rolesFromRepository.add(RoleModelBuilder.newRoleModelTestBuilder().now());

        Mockito.when(roleRepositoryMock.findRoleByFilter(
            id,
            name,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(rolesFromRepository);

        // action
        List<RoleDTO> result = roleService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithRoleListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 57343L;
        String name = "329669be-3306-47e1-bfe9-975a4b20bf0e";
        String status = "KA6esOoh4zMq3LN1VucPodc9oPXtIei17waDs1eDC09YoIVJbC";
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

        List<Role> rolesFromRepository = new ArrayList<>();
        rolesFromRepository.add(RoleModelBuilder.newRoleModelTestBuilder().now());
        rolesFromRepository.add(RoleModelBuilder.newRoleModelTestBuilder().now());
        rolesFromRepository.add(RoleModelBuilder.newRoleModelTestBuilder().now());
        rolesFromRepository.add(RoleModelBuilder.newRoleModelTestBuilder().now());

        List<RoleDTO> rolesFiltered = rolesFromRepository
                .stream()
                .map(m->roleService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageRoleItems", rolesFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<Role> pagedResponse =
                new PageImpl<>(rolesFromRepository,
                        pageableMock,
                        rolesFromRepository.size());

        Mockito.when(roleRepositoryMock.findRoleByFilter(pageableMock,
            id,
            name,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = roleService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<RoleDTO> rolesResult = (List<RoleDTO>) result.get("pageRoleItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, rolesResult.size());
    }


    @Test
    public void showReturnListOfRoleWhenAskedForFindAllByStatus() {
        // scenario
        List<Role> listOfRoleModelMock = new ArrayList<>();
        listOfRoleModelMock.add(RoleModelBuilder.newRoleModelTestBuilder().now());
        listOfRoleModelMock.add(RoleModelBuilder.newRoleModelTestBuilder().now());

        Mockito.when(roleRepositoryMock.findAllByStatus("A")).thenReturn(listOfRoleModelMock);

        // action
        List<RoleDTO> listOfRoles = roleService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfRoles.isEmpty());
        Assertions.assertEquals(2, listOfRoles.size());
    }
    @Test
    public void shouldReturnRoleNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 13487L;
        Optional<Role> roleNonExistentMock = Optional.empty();
        Mockito.when(roleRepositoryMock.findById(idMock)).thenReturn(roleNonExistentMock);

        // action
        RoleNotFoundException exception = Assertions.assertThrows(RoleNotFoundException.class,
                ()->roleService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowRoleNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 83640L;
        Mockito.when(roleRepositoryMock.findById(idMock))
                .thenThrow(new RoleNotFoundException(ROLE_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                ROLE_NOTFOUND_WITH_ID ));

        // action
        RoleNotFoundException exception = Assertions.assertThrows(RoleNotFoundException.class,
                ()->roleService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnRoleDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 205L;
        Optional<Role> roleModelMock = Optional.ofNullable(
                RoleModelBuilder.newRoleModelTestBuilder()
                        .id(idMock)
                        .name("a48c772c-6387-4a57-a947-eca8e7412b1f")

                        .status("X")
                        .now()
        );
        Role roleToSaveMock = roleModelMock.orElse(null);
        Role roleSavedMck = RoleModelBuilder.newRoleModelTestBuilder()
                        .id(15001L)
                        .name("1a360796-8cbd-4c8c-ac22-860bfb923ac8")

                        .status("A")
                        .now();
        Mockito.when(roleRepositoryMock.findById(idMock)).thenReturn(roleModelMock);
        Mockito.when(roleRepositoryMock.save(roleToSaveMock)).thenReturn(roleSavedMck);

        // action
        RoleDTO result = roleService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchRoleByAnyNonExistenceIdAndReturnRoleNotFoundException() {
        // scenario
        Mockito.when(roleRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        RoleNotFoundException exception = Assertions.assertThrows(RoleNotFoundException.class,
                ()-> roleService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ROLE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchRoleByIdAndReturnDTO() {
        // scenario
        Optional<Role> roleModelMock = Optional.ofNullable(RoleModelBuilder.newRoleModelTestBuilder()
                .id(4400L)
                .name("4442d20c-12e1-4a93-b845-05adca718d92")
                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(roleRepositoryMock.findById(Mockito.anyLong())).thenReturn(roleModelMock);

        // action
        RoleDTO result = roleService.findById(1L);

        // validate
        Assertions.assertInstanceOf(RoleDTO.class,result);
    }
    @Test
    public void shouldDeleteRoleByIdWithSucess() {
        // scenario
        Optional<Role> role = Optional.ofNullable(RoleModelBuilder.newRoleModelTestBuilder().id(1L).now());
        Mockito.when(roleRepositoryMock.findById(Mockito.anyLong())).thenReturn(role);

        // action
        roleService.delete(1L);

        // validate
        Mockito.verify(roleRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceRoleShouldReturnRoleNotFoundException() {
        // scenario
        Mockito.when(roleRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        RoleNotFoundException exception = Assertions.assertThrows(
                RoleNotFoundException.class, () -> roleService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ROLE_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingRoleWithSucess() {
        // scenario
        RoleDTO roleDTOMock = RoleDTOBuilder.newRoleDTOTestBuilder()
                .id(14500L)
                .name("a5cc3a59-97c7-4ec3-b6b4-4e2988783a06")
                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Role roleMock = RoleModelBuilder.newRoleModelTestBuilder()
                .id(roleDTOMock.getId())
                .name(roleDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Role roleSavedMock = RoleModelBuilder.newRoleModelTestBuilder()
                .id(roleDTOMock.getId())
                .name(roleDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(roleRepositoryMock.save(roleMock)).thenReturn(roleSavedMock);

        // action
        RoleDTO roleSaved = roleService.salvar(roleDTOMock);

        // validate
        Assertions.assertInstanceOf(RoleDTO.class, roleSaved);
        Assertions.assertNotNull(roleSaved.getId());
    }

    @Test
    public void ShouldSaveNewRoleWithSucess() {
        // scenario
        RoleDTO roleDTOMock = RoleDTOBuilder.newRoleDTOTestBuilder()
                .id(null)
                .name("f023387e-ad99-4f74-95ce-d9a9248b2fd2")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Role roleModelMock = RoleModelBuilder.newRoleModelTestBuilder()
                .id(null)
                .name(roleDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Role roleSavedMock = RoleModelBuilder.newRoleModelTestBuilder()
                .id(501L)
                .name(roleDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(roleRepositoryMock.save(roleModelMock)).thenReturn(roleSavedMock);

        // action
        RoleDTO roleSaved = roleService.salvar(roleDTOMock);

        // validate
        Assertions.assertInstanceOf(RoleDTO.class, roleSaved);
        Assertions.assertNotNull(roleSaved.getId());
        Assertions.assertEquals("P",roleSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapRoleDTOMock = new HashMap<>();
        mapRoleDTOMock.put(RoleConstantes.NAME,UUID.fromString("6ec73f90-716f-4a18-aca9-c0d0255fc262"));
        mapRoleDTOMock.put(RoleConstantes.STATUS,"UQM46zRWd2Kqx0F0nNyKq0tcISTbegtsFikzFCuQvUVBUitCOE");


        Optional<Role> roleModelMock = Optional.ofNullable(
                RoleModelBuilder.newRoleModelTestBuilder()
                        .id(35724L)
                        .name("158c3cfc-979f-47ba-b523-83b612fcfb8d")
                        .status("A")
                        .now()
        );

        Mockito.when(roleRepositoryMock.findById(1L)).thenReturn(roleModelMock);

        // action
        boolean executed = roleService.partialUpdate(1L, mapRoleDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnRoleNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapRoleDTOMock = new HashMap<>();
        mapRoleDTOMock.put(RoleConstantes.NAME,UUID.fromString("8dead928-2a84-40c5-9a76-8aa38fc0ddb1"));
        mapRoleDTOMock.put(RoleConstantes.STATUS,"OtCm0iUx5CpfRPGC3CIP45LwRa6A2UdRRV0qpfLq2wmb9t30PO");


        Mockito.when(roleRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        RoleNotFoundException exception = Assertions.assertThrows(RoleNotFoundException.class,
                ()->roleService.partialUpdate(1L, mapRoleDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("Role não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnRoleListWhenFindAllRoleByIdAndStatus() {
        // scenario
        List<Role> roles = Arrays.asList(
            RoleModelBuilder.newRoleModelTestBuilder().now(),
            RoleModelBuilder.newRoleModelTestBuilder().now(),
            RoleModelBuilder.newRoleModelTestBuilder().now()
        );

        Mockito.when(roleRepositoryMock.findAllByIdAndStatus(13631L, "A")).thenReturn(roles);

        // action
        List<RoleDTO> result = roleService.findAllRoleByIdAndStatus(13631L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnRoleListWhenFindAllRoleByNameAndStatus() {
        // scenario
        List<Role> roles = Arrays.asList(
            RoleModelBuilder.newRoleModelTestBuilder().now(),
            RoleModelBuilder.newRoleModelTestBuilder().now(),
            RoleModelBuilder.newRoleModelTestBuilder().now()
        );

        Mockito.when(roleRepositoryMock.findAllByNameAndStatus("14dd6a7c-ece8-4f0d-8527-f9ed743849e8", "A")).thenReturn(roles);

        // action
        List<RoleDTO> result = roleService.findAllRoleByNameAndStatus("14dd6a7c-ece8-4f0d-8527-f9ed743849e8", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnRoleListWhenFindAllRoleByDateCreatedAndStatus() {
        // scenario
        List<Role> roles = Arrays.asList(
            RoleModelBuilder.newRoleModelTestBuilder().now(),
            RoleModelBuilder.newRoleModelTestBuilder().now(),
            RoleModelBuilder.newRoleModelTestBuilder().now()
        );

        Mockito.when(roleRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(roles);

        // action
        List<RoleDTO> result = roleService.findAllRoleByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnRoleListWhenFindAllRoleByDateUpdatedAndStatus() {
        // scenario
        List<Role> roles = Arrays.asList(
            RoleModelBuilder.newRoleModelTestBuilder().now(),
            RoleModelBuilder.newRoleModelTestBuilder().now(),
            RoleModelBuilder.newRoleModelTestBuilder().now()
        );

        Mockito.when(roleRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(roles);

        // action
        List<RoleDTO> result = roleService.findAllRoleByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentRoleDTOWhenFindRoleByIdAndStatus() {
        // scenario
        Optional<Role> roleModelMock = Optional.ofNullable(RoleModelBuilder.newRoleModelTestBuilder().now());
        Mockito.when(roleRepositoryMock.loadMaxIdByIdAndStatus(11000L, "A")).thenReturn(1L);
        Mockito.when(roleRepositoryMock.findById(1L)).thenReturn(roleModelMock);

        // action
        RoleDTO result = roleService.findRoleByIdAndStatus(11000L, "A");

        // validate
        Assertions.assertInstanceOf(RoleDTO.class,result);
    }
    @Test
    public void shouldReturnRoleNotFoundExceptionWhenNonExistenceRoleIdAndStatus() {
        // scenario
        Mockito.when(roleRepositoryMock.loadMaxIdByIdAndStatus(11000L, "A")).thenReturn(0L);
        Mockito.when(roleRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        RoleNotFoundException exception = Assertions.assertThrows(RoleNotFoundException.class,
                ()->roleService.findRoleByIdAndStatus(11000L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ROLE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentRoleDTOWhenFindRoleByNameAndStatus() {
        // scenario
        Optional<Role> roleModelMock = Optional.ofNullable(RoleModelBuilder.newRoleModelTestBuilder().now());
        Mockito.when(roleRepositoryMock.loadMaxIdByNameAndStatus("c116b715-67d5-4622-a188-8970f39e0d8a", "A")).thenReturn(1L);
        Mockito.when(roleRepositoryMock.findById(1L)).thenReturn(roleModelMock);

        // action
        RoleDTO result = roleService.findRoleByNameAndStatus("c116b715-67d5-4622-a188-8970f39e0d8a", "A");

        // validate
        Assertions.assertInstanceOf(RoleDTO.class,result);
    }
    @Test
    public void shouldReturnRoleNotFoundExceptionWhenNonExistenceRoleNameAndStatus() {
        // scenario
        Mockito.when(roleRepositoryMock.loadMaxIdByNameAndStatus("c116b715-67d5-4622-a188-8970f39e0d8a", "A")).thenReturn(0L);
        Mockito.when(roleRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        RoleNotFoundException exception = Assertions.assertThrows(RoleNotFoundException.class,
                ()->roleService.findRoleByNameAndStatus("c116b715-67d5-4622-a188-8970f39e0d8a", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ROLE_NOTFOUND_WITH_NAME));
    }

    @Test
    public void shouldReturnRoleDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "38a4a5b9-1c76-445e-86ff-60b561f051d9";
        Optional<Role> roleModelMock = Optional.ofNullable(RoleModelBuilder.newRoleModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(roleRepositoryMock.findById(420L)).thenReturn(roleModelMock);
        Mockito.doNothing().when(roleRepositoryMock).updateNameById(420L, nameUpdateMock);

        // action
        roleService.updateNameById(420L, nameUpdateMock);

        // validate
        Mockito.verify(roleRepositoryMock,Mockito.times(1)).updateNameById(420L, nameUpdateMock);
    }



    @Test
    public void showReturnExistingRoleDTOWhenFindRoleByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 70758L;
        Long maxIdMock = 1972L;
        Optional<Role> roleModelMock = Optional.ofNullable(RoleModelBuilder.newRoleModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(roleRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(roleRepositoryMock.findById(maxIdMock)).thenReturn(roleModelMock);

        // action
        RoleDTO result = roleService.findRoleByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnRoleNotFoundExceptionWhenNonExistenceFindRoleByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 70758L;
        Long noMaxIdMock = 0L;
        Optional<Role> roleModelMock = Optional.empty();
        Mockito.when(roleRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(roleRepositoryMock.findById(noMaxIdMock)).thenReturn(roleModelMock);

        // action
        RoleNotFoundException exception = Assertions.assertThrows(RoleNotFoundException.class,
                ()->roleService.findRoleByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingRoleDTOWhenFindRoleByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "d2ddd954-042c-4ac7-9279-7aa5fcd5b296";
        Long maxIdMock = 1972L;
        Optional<Role> roleModelMock = Optional.ofNullable(RoleModelBuilder.newRoleModelTestBuilder()
                .name(nameMock)
                .now()
        );
        Mockito.when(roleRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(maxIdMock);
        Mockito.when(roleRepositoryMock.findById(maxIdMock)).thenReturn(roleModelMock);

        // action
        RoleDTO result = roleService.findRoleByNameAndStatus(nameMock);

        // validate
        Assertions.assertEquals(nameMock, result.getName());

    }
    @Test
    public void showReturnRoleNotFoundExceptionWhenNonExistenceFindRoleByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "d2ddd954-042c-4ac7-9279-7aa5fcd5b296";
        Long noMaxIdMock = 0L;
        Optional<Role> roleModelMock = Optional.empty();
        Mockito.when(roleRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(roleRepositoryMock.findById(noMaxIdMock)).thenReturn(roleModelMock);

        // action
        RoleNotFoundException exception = Assertions.assertThrows(RoleNotFoundException.class,
                ()->roleService.findRoleByNameAndStatus(nameMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ROLE_NOTFOUND_WITH_NAME));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

