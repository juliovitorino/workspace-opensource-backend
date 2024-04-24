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
import br.com.jcv.security.guardian.builder.GroupUserDTOBuilder;
import br.com.jcv.security.guardian.builder.GroupUserModelBuilder;
import br.com.jcv.security.guardian.dto.GroupUserDTO;
import br.com.jcv.security.guardian.exception.GroupUserNotFoundException;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.GroupUser;
import br.com.jcv.security.guardian.repository.GroupUserRepository;
import br.com.jcv.security.guardian.service.GroupUserService;
import br.com.jcv.security.guardian.constantes.GroupUserConstantes;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class GroupUserServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String GROUPUSER_NOTFOUND_WITH_ID = "GroupUser não encontrada com id = ";
    public static final String GROUPUSER_NOTFOUND_WITH_IDUSER = "GroupUser não encontrada com idUser = ";
    public static final String GROUPUSER_NOTFOUND_WITH_IDGROUP = "GroupUser não encontrada com idGroup = ";
    public static final String GROUPUSER_NOTFOUND_WITH_STATUS = "GroupUser não encontrada com status = ";
    public static final String GROUPUSER_NOTFOUND_WITH_DATECREATED = "GroupUser não encontrada com dateCreated = ";
    public static final String GROUPUSER_NOTFOUND_WITH_DATEUPDATED = "GroupUser não encontrada com dateUpdated = ";


    @Mock
    private GroupUserRepository groupuserRepositoryMock;
    @Mock
    private CacheProvider redisProviderMock;
    @Mock
    private Gson gsonMock;

    @InjectMocks
    private GroupUserService groupuserService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        groupuserService = new GroupUserServiceImpl();
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
    public void shouldReturnListOfGroupUserWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 43770L;
        Long idUser = 21508L;
        Long idGroup = 3260L;
        String status = "NOySiBzeXGhhh3B0qXtyTIK3Jzmlk73CDVeRCFcRagp3XPhvRf";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idUser", idUser);
        mapFieldsRequestMock.put("idGroup", idGroup);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<GroupUser> groupusersFromRepository = new ArrayList<>();
        groupusersFromRepository.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        groupusersFromRepository.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        groupusersFromRepository.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        groupusersFromRepository.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());

        Mockito.when(groupuserRepositoryMock.findGroupUserByFilter(
            id,
            idUser,
            idGroup,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(groupusersFromRepository);

        // action
        List<GroupUserDTO> result = groupuserService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithGroupUserListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 65641L;
        Long idUser = 6240L;
        Long idGroup = 53167L;
        String status = "juGHr8khdiElCcnORdL26i0Rb4kmOuNEqz2302WhypnBelFOzy";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idUser", idUser);
        mapFieldsRequestMock.put("idGroup", idGroup);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<GroupUser> groupusersFromRepository = new ArrayList<>();
        groupusersFromRepository.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        groupusersFromRepository.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        groupusersFromRepository.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        groupusersFromRepository.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());

        List<GroupUserDTO> groupusersFiltered = groupusersFromRepository
                .stream()
                .map(m->groupuserService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageGroupUserItems", groupusersFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<GroupUser> pagedResponse =
                new PageImpl<>(groupusersFromRepository,
                        pageableMock,
                        groupusersFromRepository.size());

        Mockito.when(groupuserRepositoryMock.findGroupUserByFilter(pageableMock,
            id,
            idUser,
            idGroup,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = groupuserService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<GroupUserDTO> groupusersResult = (List<GroupUserDTO>) result.get("pageGroupUserItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, groupusersResult.size());
    }


    @Test
    public void showReturnListOfGroupUserWhenAskedForFindAllByStatus() {
        // scenario
        List<GroupUser> listOfGroupUserModelMock = new ArrayList<>();
        listOfGroupUserModelMock.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        listOfGroupUserModelMock.add(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());

        Mockito.when(groupuserRepositoryMock.findAllByStatus("A")).thenReturn(listOfGroupUserModelMock);

        // action
        List<GroupUserDTO> listOfGroupUsers = groupuserService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfGroupUsers.isEmpty());
        Assertions.assertEquals(2, listOfGroupUsers.size());
    }
    @Test
    public void shouldReturnGroupUserNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 54711L;
        Optional<GroupUser> groupuserNonExistentMock = Optional.empty();
        Mockito.when(groupuserRepositoryMock.findById(idMock)).thenReturn(groupuserNonExistentMock);

        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowGroupUserNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 50242L;
        Mockito.when(groupuserRepositoryMock.findById(idMock))
                .thenThrow(new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                GROUPUSER_NOTFOUND_WITH_ID ));

        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnGroupUserDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 73878L;
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(
                GroupUserModelBuilder.newGroupUserModelTestBuilder()
                        .id(idMock)
                        .idUser(38283L)
                        .idGroup(47068L)

                        .status("X")
                        .now()
        );
        GroupUser groupuserToSaveMock = groupuserModelMock.orElse(null);
        GroupUser groupuserSavedMck = GroupUserModelBuilder.newGroupUserModelTestBuilder()
                        .id(60855L)
                        .idUser(33358L)
                        .idGroup(13348L)

                        .status("A")
                        .now();
        Mockito.when(groupuserRepositoryMock.findById(idMock)).thenReturn(groupuserModelMock);
        Mockito.when(groupuserRepositoryMock.save(groupuserToSaveMock)).thenReturn(groupuserSavedMck);

        // action
        GroupUserDTO result = groupuserService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchGroupUserByAnyNonExistenceIdAndReturnGroupUserNotFoundException() {
        // scenario
        Mockito.when(groupuserRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()-> groupuserService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchGroupUserByIdAndReturnDTO() {
        // scenario
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder()
                .id(88048L)
                .idUser(37220L)
                .idGroup(14015L)

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(groupuserRepositoryMock.findById(Mockito.anyLong())).thenReturn(groupuserModelMock);

        // action
        GroupUserDTO result = groupuserService.findById(1L);

        // validate
        Assertions.assertInstanceOf(GroupUserDTO.class,result);
    }
    @Test
    public void shouldDeleteGroupUserByIdWithSucess() {
        // scenario
        Optional<GroupUser> groupuser = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder().id(1L).now());
        Mockito.when(groupuserRepositoryMock.findById(Mockito.anyLong())).thenReturn(groupuser);

        // action
        groupuserService.delete(1L);

        // validate
        Mockito.verify(groupuserRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceGroupUserShouldReturnGroupUserNotFoundException() {
        // scenario
        Mockito.when(groupuserRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(
                GroupUserNotFoundException.class, () -> groupuserService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingGroupUserWithSucess() {
        // scenario
        GroupUserDTO groupuserDTOMock = GroupUserDTOBuilder.newGroupUserDTOTestBuilder()
                .id(54505L)
                .idUser(1714L)
                .idGroup(61067L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupUser groupuserMock = GroupUserModelBuilder.newGroupUserModelTestBuilder()
                .id(groupuserDTOMock.getId())
                .idUser(groupuserDTOMock.getIdUser())
                .idGroup(groupuserDTOMock.getIdGroup())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupUser groupuserSavedMock = GroupUserModelBuilder.newGroupUserModelTestBuilder()
                .id(groupuserDTOMock.getId())
                .idUser(groupuserDTOMock.getIdUser())
                .idGroup(groupuserDTOMock.getIdGroup())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(groupuserRepositoryMock.save(groupuserMock)).thenReturn(groupuserSavedMock);

        // action
        GroupUserDTO groupuserSaved = groupuserService.salvar(groupuserDTOMock);

        // validate
        Assertions.assertInstanceOf(GroupUserDTO.class, groupuserSaved);
        Assertions.assertNotNull(groupuserSaved.getId());
    }

    @Test
    public void ShouldSaveNewGroupUserWithSucess() {
        // scenario
        GroupUserDTO groupuserDTOMock = GroupUserDTOBuilder.newGroupUserDTOTestBuilder()
                .id(null)
                .idUser(85574L)
                .idGroup(6226L)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupUser groupuserModelMock = GroupUserModelBuilder.newGroupUserModelTestBuilder()
                .id(null)
                .idUser(groupuserDTOMock.getIdUser())
                .idGroup(groupuserDTOMock.getIdGroup())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GroupUser groupuserSavedMock = GroupUserModelBuilder.newGroupUserModelTestBuilder()
                .id(501L)
                .idUser(groupuserDTOMock.getIdUser())
                .idGroup(groupuserDTOMock.getIdGroup())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(groupuserRepositoryMock.save(groupuserModelMock)).thenReturn(groupuserSavedMock);

        // action
        GroupUserDTO groupuserSaved = groupuserService.salvar(groupuserDTOMock);

        // validate
        Assertions.assertInstanceOf(GroupUserDTO.class, groupuserSaved);
        Assertions.assertNotNull(groupuserSaved.getId());
        Assertions.assertEquals("P",groupuserSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapGroupUserDTOMock = new HashMap<>();
        mapGroupUserDTOMock.put(GroupUserConstantes.IDUSER,82317L);
        mapGroupUserDTOMock.put(GroupUserConstantes.IDGROUP,17050L);
        mapGroupUserDTOMock.put(GroupUserConstantes.STATUS,"JDnSP2AAb200NfX5cshNMqAeIUoFFVQz7F3LAUh9SzdFjBy5La");


        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(
                GroupUserModelBuilder.newGroupUserModelTestBuilder()
                        .id(26245L)
                        .idUser(54033L)
                        .idGroup(16221L)
                        .status("TV3OWQHAnVx8amGB8f0mmhcCpHIlepVvwbFwHNGVIROD8OHqyV")

                        .now()
        );

        Mockito.when(groupuserRepositoryMock.findById(1L)).thenReturn(groupuserModelMock);

        // action
        boolean executed = groupuserService.partialUpdate(1L, mapGroupUserDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnGroupUserNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapGroupUserDTOMock = new HashMap<>();
        mapGroupUserDTOMock.put(GroupUserConstantes.IDUSER,70786L);
        mapGroupUserDTOMock.put(GroupUserConstantes.IDGROUP,14237L);
        mapGroupUserDTOMock.put(GroupUserConstantes.STATUS,"slWtlaP0McghPKqVVRN0Dz7y1sJMXzdQaMwyHe91zGnDkd9yIl");


        Mockito.when(groupuserRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.partialUpdate(1L, mapGroupUserDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("GroupUser não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnGroupUserListWhenFindAllGroupUserByIdAndStatus() {
        // scenario
        List<GroupUser> groupusers = Arrays.asList(
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now()
        );

        Mockito.when(groupuserRepositoryMock.findAllByIdAndStatus(56433L, "A")).thenReturn(groupusers);

        // action
        List<GroupUserDTO> result = groupuserService.findAllGroupUserByIdAndStatus(56433L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupUserListWhenFindAllGroupUserByIdUserAndStatus() {
        // scenario
        List<GroupUser> groupusers = Arrays.asList(
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now()
        );

        Mockito.when(groupuserRepositoryMock.findAllByIdUserAndStatus(30505L, "A")).thenReturn(groupusers);

        // action
        List<GroupUserDTO> result = groupuserService.findAllGroupUserByIdUserAndStatus(30505L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupUserListWhenFindAllGroupUserByIdGroupAndStatus() {
        // scenario
        List<GroupUser> groupusers = Arrays.asList(
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now()
        );

        Mockito.when(groupuserRepositoryMock.findAllByIdGroupAndStatus(75875L, "A")).thenReturn(groupusers);

        // action
        List<GroupUserDTO> result = groupuserService.findAllGroupUserByIdGroupAndStatus(75875L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupUserListWhenFindAllGroupUserByDateCreatedAndStatus() {
        // scenario
        List<GroupUser> groupusers = Arrays.asList(
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now()
        );

        Mockito.when(groupuserRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(groupusers);

        // action
        List<GroupUserDTO> result = groupuserService.findAllGroupUserByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGroupUserListWhenFindAllGroupUserByDateUpdatedAndStatus() {
        // scenario
        List<GroupUser> groupusers = Arrays.asList(
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now(),
            GroupUserModelBuilder.newGroupUserModelTestBuilder().now()
        );

        Mockito.when(groupuserRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(groupusers);

        // action
        List<GroupUserDTO> result = groupuserService.findAllGroupUserByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentGroupUserDTOWhenFindGroupUserByIdAndStatus() {
        // scenario
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdAndStatus(35610L, "A")).thenReturn(1L);
        Mockito.when(groupuserRepositoryMock.findById(1L)).thenReturn(groupuserModelMock);

        // action
        GroupUserDTO result = groupuserService.findGroupUserByIdAndStatus(35610L, "A");

        // validate
        Assertions.assertInstanceOf(GroupUserDTO.class,result);
    }
    @Test
    public void shouldReturnGroupUserNotFoundExceptionWhenNonExistenceGroupUserIdAndStatus() {
        // scenario
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdAndStatus(35610L, "A")).thenReturn(0L);
        Mockito.when(groupuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.findGroupUserByIdAndStatus(35610L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentGroupUserDTOWhenFindGroupUserByIdUserAndStatus() {
        // scenario
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdUserAndStatus(73400L, "A")).thenReturn(1L);
        Mockito.when(groupuserRepositoryMock.findById(1L)).thenReturn(groupuserModelMock);

        // action
        GroupUserDTO result = groupuserService.findGroupUserByIdUserAndStatus(73400L, "A");

        // validate
        Assertions.assertInstanceOf(GroupUserDTO.class,result);
    }
    @Test
    public void shouldReturnGroupUserNotFoundExceptionWhenNonExistenceGroupUserIdUserAndStatus() {
        // scenario
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdUserAndStatus(73400L, "A")).thenReturn(0L);
        Mockito.when(groupuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.findGroupUserByIdUserAndStatus(73400L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_IDUSER));
    }
    @Test
    public void shouldReturnExistentGroupUserDTOWhenFindGroupUserByIdGroupAndStatus() {
        // scenario
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder().now());
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdGroupAndStatus(80385L, "A")).thenReturn(1L);
        Mockito.when(groupuserRepositoryMock.findById(1L)).thenReturn(groupuserModelMock);

        // action
        GroupUserDTO result = groupuserService.findGroupUserByIdGroupAndStatus(80385L, "A");

        // validate
        Assertions.assertInstanceOf(GroupUserDTO.class,result);
    }
    @Test
    public void shouldReturnGroupUserNotFoundExceptionWhenNonExistenceGroupUserIdGroupAndStatus() {
        // scenario
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdGroupAndStatus(80385L, "A")).thenReturn(0L);
        Mockito.when(groupuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.findGroupUserByIdGroupAndStatus(80385L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_IDGROUP));
    }

    @Test
    public void shouldReturnGroupUserDTOWhenUpdateExistingIdUserById() {
        // scenario
        Long idUserUpdateMock = 73686L;
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(groupuserRepositoryMock.findById(420L)).thenReturn(groupuserModelMock);
        Mockito.doNothing().when(groupuserRepositoryMock).updateIdUserById(420L, idUserUpdateMock);

        // action
        groupuserService.updateIdUserById(420L, idUserUpdateMock);

        // validate
        Mockito.verify(groupuserRepositoryMock,Mockito.times(1)).updateIdUserById(420L, idUserUpdateMock);
    }
    @Test
    public void shouldReturnGroupUserDTOWhenUpdateExistingIdGroupById() {
        // scenario
        Long idGroupUpdateMock = 527L;
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(groupuserRepositoryMock.findById(420L)).thenReturn(groupuserModelMock);
        Mockito.doNothing().when(groupuserRepositoryMock).updateIdGroupById(420L, idGroupUpdateMock);

        // action
        groupuserService.updateIdGroupById(420L, idGroupUpdateMock);

        // validate
        Mockito.verify(groupuserRepositoryMock,Mockito.times(1)).updateIdGroupById(420L, idGroupUpdateMock);
    }



    @Test
    public void showReturnExistingGroupUserDTOWhenFindGroupUserByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 2072L;
        Long maxIdMock = 1972L;
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(groupuserRepositoryMock.findById(maxIdMock)).thenReturn(groupuserModelMock);

        // action
        GroupUserDTO result = groupuserService.findGroupUserByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnGroupUserNotFoundExceptionWhenNonExistenceFindGroupUserByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 2072L;
        Long noMaxIdMock = 0L;
        Optional<GroupUser> groupuserModelMock = Optional.empty();
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(groupuserRepositoryMock.findById(noMaxIdMock)).thenReturn(groupuserModelMock);

        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.findGroupUserByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGroupUserDTOWhenFindGroupUserByIdUserAndStatusActiveAnonimous() {
        // scenario
        Long idUserMock = 64157L;
        Long maxIdMock = 1972L;
        final Gson gson = new Gson();
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder()
                .idUser(idUserMock)
                .now()
        );
        GroupUserDTO groupUserDtoMock = GroupUserDTOBuilder.newGroupUserDTOTestBuilder()
                .id(1L)
                .idUser(idUserMock)
                .idGroup(10L)
                .now();
        String groupUserJson = gson.toJson(groupUserDtoMock);

        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdUserAndStatus(idUserMock, "A")).thenReturn(maxIdMock);
        Mockito.when(groupuserRepositoryMock.findById(maxIdMock)).thenReturn(groupuserModelMock);
        Mockito.when(redisProviderMock.getValue(Mockito.anyString(),Mockito.any())).thenReturn(null);
        Mockito.when(gsonMock.toJson(Mockito.any())).thenReturn(groupUserJson);

        // action
        GroupUserDTO result = groupuserService.findGroupUserByIdUserAndStatus(idUserMock);

        // validate
        Assertions.assertEquals(idUserMock, result.getIdUser());

    }
    @Test
    public void showReturnGroupUserNotFoundExceptionWhenNonExistenceFindGroupUserByIdUserAndStatusActiveAnonimous() {
        // scenario
        Long idUserMock = 64157L;
        Long noMaxIdMock = 0L;
        Optional<GroupUser> groupuserModelMock = Optional.empty();
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdUserAndStatus(idUserMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(groupuserRepositoryMock.findById(noMaxIdMock)).thenReturn(groupuserModelMock);

        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.findGroupUserByIdUserAndStatus(idUserMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_IDUSER));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGroupUserDTOWhenFindGroupUserByIdGroupAndStatusActiveAnonimous() {
        // scenario
        Long idGroupMock = 70671L;
        Long maxIdMock = 1972L;
        Optional<GroupUser> groupuserModelMock = Optional.ofNullable(GroupUserModelBuilder.newGroupUserModelTestBuilder()
                .idGroup(idGroupMock)
                .now()
        );
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdGroupAndStatus(idGroupMock, "A")).thenReturn(maxIdMock);
        Mockito.when(groupuserRepositoryMock.findById(maxIdMock)).thenReturn(groupuserModelMock);

        // action
        GroupUserDTO result = groupuserService.findGroupUserByIdGroupAndStatus(idGroupMock);

        // validate
        Assertions.assertEquals(idGroupMock, result.getIdGroup());

    }
    @Test
    public void showReturnGroupUserNotFoundExceptionWhenNonExistenceFindGroupUserByIdGroupAndStatusActiveAnonimous() {
        // scenario
        Long idGroupMock = 70671L;
        Long noMaxIdMock = 0L;
        Optional<GroupUser> groupuserModelMock = Optional.empty();
        Mockito.when(groupuserRepositoryMock.loadMaxIdByIdGroupAndStatus(idGroupMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(groupuserRepositoryMock.findById(noMaxIdMock)).thenReturn(groupuserModelMock);

        // action
        GroupUserNotFoundException exception = Assertions.assertThrows(GroupUserNotFoundException.class,
                ()->groupuserService.findGroupUserByIdGroupAndStatus(idGroupMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GROUPUSER_NOTFOUND_WITH_IDGROUP));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

