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
import br.com.jcv.security.guardian.builder.UserRoleDTOBuilder;
import br.com.jcv.security.guardian.builder.UserRoleModelBuilder;
import br.com.jcv.security.guardian.dto.UserRoleDTO;
import br.com.jcv.security.guardian.exception.UserRoleNotFoundException;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.UserRole;
import br.com.jcv.security.guardian.repository.UserRoleRepository;
import br.com.jcv.security.guardian.service.UserRoleService;
import br.com.jcv.security.guardian.constantes.UserRoleConstantes;
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
public class UserRoleServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String USERROLE_NOTFOUND_WITH_ID = "UserRole não encontrada com id = ";
    public static final String USERROLE_NOTFOUND_WITH_IDROLE = "UserRole não encontrada com idRole = ";
    public static final String USERROLE_NOTFOUND_WITH_IDUSER = "UserRole não encontrada com idUser = ";
    public static final String USERROLE_NOTFOUND_WITH_STATUS = "UserRole não encontrada com status = ";
    public static final String USERROLE_NOTFOUND_WITH_DATECREATED = "UserRole não encontrada com dateCreated = ";
    public static final String USERROLE_NOTFOUND_WITH_DATEUPDATED = "UserRole não encontrada com dateUpdated = ";


    @Mock
    private UserRoleRepository userroleRepositoryMock;
    @Mock
    private CacheProvider redisProviderMock;
    @Mock
    private Gson gsonMock;
    @InjectMocks
    private UserRoleService userroleService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        userroleService = new UserRoleServiceImpl();
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
    public void shouldReturnListOfUserRoleWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 67822L;
        Long idRole = 61266L;
        Long idUser = 42020L;
        String status = "bCempIUCoe3Xqhx9HsWvSitgq7q5yHOIxYGx4xjJTVTTkxva6f";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idRole", idRole);
        mapFieldsRequestMock.put("idUser", idUser);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<UserRole> userrolesFromRepository = new ArrayList<>();
        userrolesFromRepository.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        userrolesFromRepository.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        userrolesFromRepository.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        userrolesFromRepository.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());

        Mockito.when(userroleRepositoryMock.findUserRoleByFilter(
            id,
            idRole,
            idUser,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(userrolesFromRepository);

        // action
        List<UserRoleDTO> result = userroleService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithUserRoleListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 20025L;
        Long idRole = 46870L;
        Long idUser = 12804L;
        String status = "KWrrTSMQlY0Sbu0BQpSG1ckEYdWj5vWGneB3pApUglRsGIRsxu";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idRole", idRole);
        mapFieldsRequestMock.put("idUser", idUser);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<UserRole> userrolesFromRepository = new ArrayList<>();
        userrolesFromRepository.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        userrolesFromRepository.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        userrolesFromRepository.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        userrolesFromRepository.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());

        List<UserRoleDTO> userrolesFiltered = userrolesFromRepository
                .stream()
                .map(m->userroleService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageUserRoleItems", userrolesFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<UserRole> pagedResponse =
                new PageImpl<>(userrolesFromRepository,
                        pageableMock,
                        userrolesFromRepository.size());

        Mockito.when(userroleRepositoryMock.findUserRoleByFilter(pageableMock,
            id,
            idRole,
            idUser,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = userroleService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<UserRoleDTO> userrolesResult = (List<UserRoleDTO>) result.get("pageUserRoleItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, userrolesResult.size());
    }


    @Test
    public void showReturnListOfUserRoleWhenAskedForFindAllByStatus() {
        // scenario
        List<UserRole> listOfUserRoleModelMock = new ArrayList<>();
        listOfUserRoleModelMock.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        listOfUserRoleModelMock.add(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());

        Mockito.when(userroleRepositoryMock.findAllByStatus("A")).thenReturn(listOfUserRoleModelMock);

        // action
        List<UserRoleDTO> listOfUserRoles = userroleService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfUserRoles.isEmpty());
        Assertions.assertEquals(2, listOfUserRoles.size());
    }
    @Test
    public void shouldReturnUserRoleNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 65217L;
        Optional<UserRole> userroleNonExistentMock = Optional.empty();
        Mockito.when(userroleRepositoryMock.findById(idMock)).thenReturn(userroleNonExistentMock);

        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowUserRoleNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 86741L;
        Mockito.when(userroleRepositoryMock.findById(idMock))
                .thenThrow(new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                USERROLE_NOTFOUND_WITH_ID ));

        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnUserRoleDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 67472L;
        Optional<UserRole> userroleModelMock = Optional.ofNullable(
                UserRoleModelBuilder.newUserRoleModelTestBuilder()
                        .id(idMock)
                        .idRole(80181L)
                        .idUser(22130L)

                        .status("X")
                        .now()
        );
        UserRole userroleToSaveMock = userroleModelMock.orElse(null);
        UserRole userroleSavedMck = UserRoleModelBuilder.newUserRoleModelTestBuilder()
                        .id(48437L)
                        .idRole(55707L)
                        .idUser(3468L)

                        .status("A")
                        .now();
        Mockito.when(userroleRepositoryMock.findById(idMock)).thenReturn(userroleModelMock);
        Mockito.when(userroleRepositoryMock.save(userroleToSaveMock)).thenReturn(userroleSavedMck);

        // action
        UserRoleDTO result = userroleService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchUserRoleByAnyNonExistenceIdAndReturnUserRoleNotFoundException() {
        // scenario
        Mockito.when(userroleRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()-> userroleService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchUserRoleByIdAndReturnDTO() {
        // scenario
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder()
                .id(11010L)
                .idRole(17605L)
                .idUser(4035L)

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(userroleRepositoryMock.findById(Mockito.anyLong())).thenReturn(userroleModelMock);

        // action
        UserRoleDTO result = userroleService.findById(1L);

        // validate
        Assertions.assertInstanceOf(UserRoleDTO.class,result);
    }
    @Test
    public void shouldDeleteUserRoleByIdWithSucess() {
        // scenario
        Optional<UserRole> userrole = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder().id(1L).now());
        Mockito.when(userroleRepositoryMock.findById(Mockito.anyLong())).thenReturn(userrole);

        // action
        userroleService.delete(1L);

        // validate
        Mockito.verify(userroleRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceUserRoleShouldReturnUserRoleNotFoundException() {
        // scenario
        Mockito.when(userroleRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(
                UserRoleNotFoundException.class, () -> userroleService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingUserRoleWithSucess() {
        // scenario
        UserRoleDTO userroleDTOMock = UserRoleDTOBuilder.newUserRoleDTOTestBuilder()
                .id(44102L)
                .idRole(1137L)
                .idUser(57542L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        UserRole userroleMock = UserRoleModelBuilder.newUserRoleModelTestBuilder()
                .id(userroleDTOMock.getId())
                .idRole(userroleDTOMock.getIdRole())
                .idUser(userroleDTOMock.getIdUser())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        UserRole userroleSavedMock = UserRoleModelBuilder.newUserRoleModelTestBuilder()
                .id(userroleDTOMock.getId())
                .idRole(userroleDTOMock.getIdRole())
                .idUser(userroleDTOMock.getIdUser())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(userroleRepositoryMock.save(userroleMock)).thenReturn(userroleSavedMock);

        // action
        UserRoleDTO userroleSaved = userroleService.salvar(userroleDTOMock);

        // validate
        Assertions.assertInstanceOf(UserRoleDTO.class, userroleSaved);
        Assertions.assertNotNull(userroleSaved.getId());
    }

    @Test
    public void ShouldSaveNewUserRoleWithSucess() {
        // scenario
        UserRoleDTO userroleDTOMock = UserRoleDTOBuilder.newUserRoleDTOTestBuilder()
                .id(null)
                .idRole(55376L)
                .idUser(48362L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        UserRole userroleModelMock = UserRoleModelBuilder.newUserRoleModelTestBuilder()
                .id(null)
                .idRole(userroleDTOMock.getIdRole())
                .idUser(userroleDTOMock.getIdUser())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        UserRole userroleSavedMock = UserRoleModelBuilder.newUserRoleModelTestBuilder()
                .id(501L)
                .idRole(userroleDTOMock.getIdRole())
                .idUser(userroleDTOMock.getIdUser())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(userroleRepositoryMock.save(userroleModelMock)).thenReturn(userroleSavedMock);

        // action
        UserRoleDTO userroleSaved = userroleService.salvar(userroleDTOMock);

        // validate
        Assertions.assertInstanceOf(UserRoleDTO.class, userroleSaved);
        Assertions.assertNotNull(userroleSaved.getId());
        Assertions.assertEquals("P",userroleSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapUserRoleDTOMock = new HashMap<>();
        mapUserRoleDTOMock.put(UserRoleConstantes.IDROLE,67553L);
        mapUserRoleDTOMock.put(UserRoleConstantes.IDUSER,30168L);
        mapUserRoleDTOMock.put(UserRoleConstantes.STATUS,"EY3i6UjxGhXocMb0lfUicHkNm8ONPSgqtaIOfKGGdsuOrVBxVC");


        Optional<UserRole> userroleModelMock = Optional.ofNullable(
                UserRoleModelBuilder.newUserRoleModelTestBuilder()
                        .id(57263L)
                        .idRole(50760L)
                        .idUser(74800L)
                        .status("ASi3Dk2ynQ4J2Fk3lPyKE866T78INCgblh4vNTnsFFm0ChcB09")

                        .now()
        );

        Mockito.when(userroleRepositoryMock.findById(1L)).thenReturn(userroleModelMock);

        // action
        boolean executed = userroleService.partialUpdate(1L, mapUserRoleDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnUserRoleNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapUserRoleDTOMock = new HashMap<>();
        mapUserRoleDTOMock.put(UserRoleConstantes.IDROLE,43243L);
        mapUserRoleDTOMock.put(UserRoleConstantes.IDUSER,10402L);
        mapUserRoleDTOMock.put(UserRoleConstantes.STATUS,"98ADoSpOnndJGfnEPk7OKz9aJVCp4PnMuT1JMJ9IT7vW0zQNWu");


        Mockito.when(userroleRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.partialUpdate(1L, mapUserRoleDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("UserRole não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnUserRoleListWhenFindAllUserRoleByIdAndStatus() {
        // scenario
        List<UserRole> userroles = Arrays.asList(
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now()
        );

        Mockito.when(userroleRepositoryMock.findAllByIdAndStatus(13701L, "A")).thenReturn(userroles);

        // action
        List<UserRoleDTO> result = userroleService.findAllUserRoleByIdAndStatus(13701L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserRoleListWhenFindAllUserRoleByIdRoleAndStatus() {
        // scenario
        List<UserRole> userroles = Arrays.asList(
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now()
        );

        Mockito.when(userroleRepositoryMock.findAllByIdRoleAndStatus(83515L, "A")).thenReturn(userroles);

        // action
        List<UserRoleDTO> result = userroleService.findAllUserRoleByIdRoleAndStatus(83515L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserRoleListWhenFindAllUserRoleByIdUserAndStatus() {
        // scenario
        List<UserRole> userroles = Arrays.asList(
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now()
        );

        Mockito.when(userroleRepositoryMock.findAllByIdUserAndStatus(45080L, "A")).thenReturn(userroles);

        // action
        List<UserRoleDTO> result = userroleService.findAllUserRoleByIdUserAndStatus(45080L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserRoleListWhenFindAllUserRoleByDateCreatedAndStatus() {
        // scenario
        List<UserRole> userroles = Arrays.asList(
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now()
        );

        Mockito.when(userroleRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(userroles);

        // action
        List<UserRoleDTO> result = userroleService.findAllUserRoleByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUserRoleListWhenFindAllUserRoleByDateUpdatedAndStatus() {
        // scenario
        List<UserRole> userroles = Arrays.asList(
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now(),
            UserRoleModelBuilder.newUserRoleModelTestBuilder().now()
        );

        Mockito.when(userroleRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(userroles);

        // action
        List<UserRoleDTO> result = userroleService.findAllUserRoleByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentUserRoleDTOWhenFindUserRoleByIdAndStatus() {
        // scenario
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdAndStatus(2607L, "A")).thenReturn(1L);
        Mockito.when(userroleRepositoryMock.findById(1L)).thenReturn(userroleModelMock);

        // action
        UserRoleDTO result = userroleService.findUserRoleByIdAndStatus(2607L, "A");

        // validate
        Assertions.assertInstanceOf(UserRoleDTO.class,result);
    }
    @Test
    public void shouldReturnUserRoleNotFoundExceptionWhenNonExistenceUserRoleIdAndStatus() {
        // scenario
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdAndStatus(2607L, "A")).thenReturn(0L);
        Mockito.when(userroleRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.findUserRoleByIdAndStatus(2607L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentUserRoleDTOWhenFindUserRoleByIdRoleAndStatus() {
        // scenario
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdRoleAndStatus(58175L, "A")).thenReturn(1L);
        Mockito.when(userroleRepositoryMock.findById(1L)).thenReturn(userroleModelMock);

        // action
        UserRoleDTO result = userroleService.findUserRoleByIdRoleAndStatus(58175L, "A");

        // validate
        Assertions.assertInstanceOf(UserRoleDTO.class,result);
    }
    @Test
    public void shouldReturnUserRoleNotFoundExceptionWhenNonExistenceUserRoleIdRoleAndStatus() {
        // scenario
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdRoleAndStatus(58175L, "A")).thenReturn(0L);
        Mockito.when(userroleRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.findUserRoleByIdRoleAndStatus(58175L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_IDROLE));
    }
    @Test
    public void shouldReturnExistentUserRoleDTOWhenFindUserRoleByIdUserAndStatus() {
        // scenario
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder().now());
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdUserAndStatus(12372L, "A")).thenReturn(1L);
        Mockito.when(userroleRepositoryMock.findById(1L)).thenReturn(userroleModelMock);

        // action
        UserRoleDTO result = userroleService.findUserRoleByIdUserAndStatus(12372L, "A");

        // validate
        Assertions.assertInstanceOf(UserRoleDTO.class,result);
    }
    @Test
    public void shouldReturnUserRoleNotFoundExceptionWhenNonExistenceUserRoleIdUserAndStatus() {
        // scenario
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdUserAndStatus(12372L, "A")).thenReturn(0L);
        Mockito.when(userroleRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.findUserRoleByIdUserAndStatus(12372L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_IDUSER));
    }

    @Test
    public void shouldReturnUserRoleDTOWhenUpdateExistingIdRoleById() {
        // scenario
        Long idRoleUpdateMock = 84173L;
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(userroleRepositoryMock.findById(420L)).thenReturn(userroleModelMock);
        Mockito.doNothing().when(userroleRepositoryMock).updateIdRoleById(420L, idRoleUpdateMock);

        // action
        userroleService.updateIdRoleById(420L, idRoleUpdateMock);

        // validate
        Mockito.verify(userroleRepositoryMock,Mockito.times(1)).updateIdRoleById(420L, idRoleUpdateMock);
    }
    @Test
    public void shouldReturnUserRoleDTOWhenUpdateExistingIdUserById() {
        // scenario
        Long idUserUpdateMock = 3480L;
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(userroleRepositoryMock.findById(420L)).thenReturn(userroleModelMock);
        Mockito.doNothing().when(userroleRepositoryMock).updateIdUserById(420L, idUserUpdateMock);

        // action
        userroleService.updateIdUserById(420L, idUserUpdateMock);

        // validate
        Mockito.verify(userroleRepositoryMock,Mockito.times(1)).updateIdUserById(420L, idUserUpdateMock);
    }



    @Test
    public void showReturnExistingUserRoleDTOWhenFindUserRoleByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 24431L;
        Long maxIdMock = 1972L;
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(userroleRepositoryMock.findById(maxIdMock)).thenReturn(userroleModelMock);

        // action
        UserRoleDTO result = userroleService.findUserRoleByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnUserRoleNotFoundExceptionWhenNonExistenceFindUserRoleByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 24431L;
        Long noMaxIdMock = 0L;
        Optional<UserRole> userroleModelMock = Optional.empty();
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(userroleRepositoryMock.findById(noMaxIdMock)).thenReturn(userroleModelMock);

        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.findUserRoleByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUserRoleDTOWhenFindUserRoleByIdRoleAndStatusActiveAnonimous() {
        // scenario
        Long idRoleMock = 1728L;
        Long maxIdMock = 1972L;
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder()
                .idRole(idRoleMock)
                .now()
        );
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdRoleAndStatus(idRoleMock, "A")).thenReturn(maxIdMock);
        Mockito.when(userroleRepositoryMock.findById(maxIdMock)).thenReturn(userroleModelMock);

        // action
        UserRoleDTO result = userroleService.findUserRoleByIdRoleAndStatus(idRoleMock);

        // validate
        Assertions.assertEquals(idRoleMock, result.getIdRole());

    }
    @Test
    public void showReturnUserRoleNotFoundExceptionWhenNonExistenceFindUserRoleByIdRoleAndStatusActiveAnonimous() {
        // scenario
        Long idRoleMock = 1728L;
        Long noMaxIdMock = 0L;
        Optional<UserRole> userroleModelMock = Optional.empty();
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdRoleAndStatus(idRoleMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(userroleRepositoryMock.findById(noMaxIdMock)).thenReturn(userroleModelMock);

        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.findUserRoleByIdRoleAndStatus(idRoleMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_IDROLE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUserRoleDTOWhenFindUserRoleByIdUserAndStatusActiveAnonimous() {
        // scenario
        Long idUserMock = 11521L;
        Long maxIdMock = 1972L;
        Optional<UserRole> userroleModelMock = Optional.ofNullable(UserRoleModelBuilder.newUserRoleModelTestBuilder()
                .idUser(idUserMock)
                .now()
        );
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdUserAndStatus(idUserMock, "A")).thenReturn(maxIdMock);
        Mockito.when(userroleRepositoryMock.findById(maxIdMock)).thenReturn(userroleModelMock);

        // action
        UserRoleDTO result = userroleService.findUserRoleByIdUserAndStatus(idUserMock);

        // validate
        Assertions.assertEquals(idUserMock, result.getIdUser());

    }
    @Test
    public void showReturnUserRoleNotFoundExceptionWhenNonExistenceFindUserRoleByIdUserAndStatusActiveAnonimous() {
        // scenario
        Long idUserMock = 11521L;
        Long noMaxIdMock = 0L;
        Optional<UserRole> userroleModelMock = Optional.empty();
        Mockito.when(userroleRepositoryMock.loadMaxIdByIdUserAndStatus(idUserMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(userroleRepositoryMock.findById(noMaxIdMock)).thenReturn(userroleModelMock);

        // action
        UserRoleNotFoundException exception = Assertions.assertThrows(UserRoleNotFoundException.class,
                ()->userroleService.findUserRoleByIdUserAndStatus(idUserMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERROLE_NOTFOUND_WITH_IDUSER));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

