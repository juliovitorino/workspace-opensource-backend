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
import br.com.jcv.security.guardian.builder.UsersDTOBuilder;
import br.com.jcv.security.guardian.builder.UsersModelBuilder;
import br.com.jcv.security.guardian.dto.UsersDTO;
import br.com.jcv.security.guardian.exception.UsersNotFoundException;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.Users;
import br.com.jcv.security.guardian.repository.UsersRepository;
import br.com.jcv.security.guardian.service.UsersService;
import br.com.jcv.security.guardian.constantes.UsersConstantes;
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
public class UsersServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String USERS_NOTFOUND_WITH_ID = "Users não encontrada com id = ";
    public static final String USERS_NOTFOUND_WITH_NAME = "Users não encontrada com name = ";
    public static final String USERS_NOTFOUND_WITH_BIRTHDAY = "Users não encontrada com birthday = ";
    public static final String USERS_NOTFOUND_WITH_STATUS = "Users não encontrada com status = ";
    public static final String USERS_NOTFOUND_WITH_DATECREATED = "Users não encontrada com dateCreated = ";
    public static final String USERS_NOTFOUND_WITH_DATEUPDATED = "Users não encontrada com dateUpdated = ";


    @Mock
    private UsersRepository usersRepositoryMock;
    @Mock
    private CacheProvider redisProviderMock;
    @Mock
    private Gson gsonMock;
    @InjectMocks
    private UsersService usersService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        usersService = new UsersServiceImpl();
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
    public void shouldReturnListOfUsersWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 477L;
        String name = "EA3Bq690zHhmqbza2gg3iOkYKbi0rzSHLTLqi4spCpCMxGpkGo";
        String birthday = "2025-10-07";
        String status = "DdXBkXDiebM9OW2s8f24QlXSI0L9RNGE8bf0r9GjO34UjeHxvH";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("birthday", birthday);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Users> userssFromRepository = new ArrayList<>();
        userssFromRepository.add(UsersModelBuilder.newUsersModelTestBuilder().now());
        userssFromRepository.add(UsersModelBuilder.newUsersModelTestBuilder().now());
        userssFromRepository.add(UsersModelBuilder.newUsersModelTestBuilder().now());
        userssFromRepository.add(UsersModelBuilder.newUsersModelTestBuilder().now());

        Mockito.when(usersRepositoryMock.findUsersByFilter(
            id,
            name,
            birthday,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(userssFromRepository);

        // action
        List<UsersDTO> result = usersService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithUsersListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 185L;
        String name = "sE5poJP0hLeWCqlnsAD3mG7x6o5i750wTA64ONGTAyxn2G63pN";
        String birthday = "2025-10-07";
        String status = "QYWq5qt3i87VeFUh0pwBsvgyx5F257pxyQ0vBI5sOyDXoEw08D";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("birthday", birthday);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Users> userssFromRepository = new ArrayList<>();
        userssFromRepository.add(UsersModelBuilder.newUsersModelTestBuilder().now());
        userssFromRepository.add(UsersModelBuilder.newUsersModelTestBuilder().now());
        userssFromRepository.add(UsersModelBuilder.newUsersModelTestBuilder().now());
        userssFromRepository.add(UsersModelBuilder.newUsersModelTestBuilder().now());

        List<UsersDTO> userssFiltered = userssFromRepository
                .stream()
                .map(m->usersService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageUsersItems", userssFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<Users> pagedResponse =
                new PageImpl<>(userssFromRepository,
                        pageableMock,
                        userssFromRepository.size());

        Mockito.when(usersRepositoryMock.findUsersByFilter(pageableMock,
            id,
            name,
            birthday,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = usersService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<UsersDTO> userssResult = (List<UsersDTO>) result.get("pageUsersItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, userssResult.size());
    }


    @Test
    public void showReturnListOfUsersWhenAskedForFindAllByStatus() {
        // scenario
        List<Users> listOfUsersModelMock = new ArrayList<>();
        listOfUsersModelMock.add(UsersModelBuilder.newUsersModelTestBuilder().now());
        listOfUsersModelMock.add(UsersModelBuilder.newUsersModelTestBuilder().now());

        Mockito.when(usersRepositoryMock.findAllByStatus("A")).thenReturn(listOfUsersModelMock);

        // action
        List<UsersDTO> listOfUserss = usersService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfUserss.isEmpty());
        Assertions.assertEquals(2, listOfUserss.size());
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 70005L;
        Optional<Users> usersNonExistentMock = Optional.empty();
        Mockito.when(usersRepositoryMock.findById(idMock)).thenReturn(usersNonExistentMock);

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowUsersNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 3054L;
        Mockito.when(usersRepositoryMock.findById(idMock))
                .thenThrow(new UsersNotFoundException(USERS_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                USERS_NOTFOUND_WITH_ID ));

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnUsersDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 44123L;
        Optional<Users> usersModelMock = Optional.ofNullable(
                UsersModelBuilder.newUsersModelTestBuilder()
                        .id(idMock)
                        .name("vzB1RWFIFnsLG2lE10cxAAELmdcViJGif8pSUA6rYvsC49uvUi")
                        .birthday(LocalDate.of(844,8,2))

                        .status("X")
                        .now()
        );
        Users usersToSaveMock = usersModelMock.orElse(null);
        Users usersSavedMck = UsersModelBuilder.newUsersModelTestBuilder()
                        .id(35258L)
                        .name("mq86SDKP2FoEI5qnTRLL6iHDL2rkjhgoojOjbLDpUzRB2a4yx2")
                        .birthday(LocalDate.of(805,12,23))

                        .status("A")
                        .now();
        Mockito.when(usersRepositoryMock.findById(idMock)).thenReturn(usersModelMock);
        Mockito.when(usersRepositoryMock.save(usersToSaveMock)).thenReturn(usersSavedMck);

        // action
        UsersDTO result = usersService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchUsersByAnyNonExistenceIdAndReturnUsersNotFoundException() {
        // scenario
        Mockito.when(usersRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()-> usersService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchUsersByIdAndReturnDTO() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                .id(80011L)
                .name("BrBoAmVp4WS7xxCC20Y09uVJR0E0ATdlSlEVlSQzJz7kJ6IQ45")
                .birthday(LocalDate.of(3138,9,20))

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(usersRepositoryMock.findById(Mockito.anyLong())).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findById(1L);

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldDeleteUsersByIdWithSucess() {
        // scenario
        Optional<Users> users = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().id(1L).now());
        Mockito.when(usersRepositoryMock.findById(Mockito.anyLong())).thenReturn(users);

        // action
        usersService.delete(1L);

        // validate
        Mockito.verify(usersRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceUsersShouldReturnUsersNotFoundException() {
        // scenario
        Mockito.when(usersRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        UsersNotFoundException exception = Assertions.assertThrows(
                UsersNotFoundException.class, () -> usersService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingUsersWithSucess() {
        // scenario
        UsersDTO usersDTOMock = UsersDTOBuilder.newUsersDTOTestBuilder()
                .id(16000L)
                .name("dgaCnyM0KV7Xl1puxtzq59VQ3te2vqr0tRPJeuKmpxzM7yQTPO")
                .birthday(LocalDate.of(3840,2,2))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(usersDTOMock.getId())
                .name(usersDTOMock.getName())
                .birthday(usersDTOMock.getBirthday())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersSavedMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(usersDTOMock.getId())
                .name(usersDTOMock.getName())
                .birthday(usersDTOMock.getBirthday())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(usersRepositoryMock.save(usersMock)).thenReturn(usersSavedMock);

        // action
        UsersDTO usersSaved = usersService.salvar(usersDTOMock);

        // validate
        Assertions.assertInstanceOf(UsersDTO.class, usersSaved);
        Assertions.assertNotNull(usersSaved.getId());
    }

    @Test
    public void ShouldSaveNewUsersWithSucess() {
        // scenario
        UsersDTO usersDTOMock = UsersDTOBuilder.newUsersDTOTestBuilder()
                .id(null)
                .name("aqVCRD4SjEL5511aeLhgsyBsy98k2v3GcjMlqkUTGJCm3XgVjl")
                .birthday(LocalDate.of(1003,1,4))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersModelMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(null)
                .name(usersDTOMock.getName())
                .birthday(usersDTOMock.getBirthday())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersSavedMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(501L)
                .name(usersDTOMock.getName())
                .birthday(usersDTOMock.getBirthday())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(usersRepositoryMock.save(usersModelMock)).thenReturn(usersSavedMock);

        // action
        UsersDTO usersSaved = usersService.salvar(usersDTOMock);

        // validate
        Assertions.assertInstanceOf(UsersDTO.class, usersSaved);
        Assertions.assertNotNull(usersSaved.getId());
        Assertions.assertEquals("P",usersSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapUsersDTOMock = new HashMap<>();
        mapUsersDTOMock.put(UsersConstantes.NAME,"b6psYkV09x2ws9WyjjMJxbPOUjG8qXxnQdupRJeeFq2pPY69PY");
        mapUsersDTOMock.put(UsersConstantes.BIRTHDAY,LocalDate.of(6021,5,21));
        mapUsersDTOMock.put(UsersConstantes.STATUS,"qbKFtyjj1S0EaB9zSfPpIaBMP8l6k3plAuWhonLinr1ph5xfhx");


        Optional<Users> usersModelMock = Optional.ofNullable(
                UsersModelBuilder.newUsersModelTestBuilder()
                        .id(56460L)
                        .name("0IpvuQgwIAXWjXNeK2WTyB9T71c1fVDOG2KWBLE1oPQfoJrh3a")
                        .birthday(LocalDate.of(3277,5,29))
                        .status("y8TOMw1QQXj8mtwow1zwXOvKdkagErb1LNHPFiNoVG1DPHOBSJ")

                        .now()
        );

        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        boolean executed = usersService.partialUpdate(1L, mapUsersDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapUsersDTOMock = new HashMap<>();
        mapUsersDTOMock.put(UsersConstantes.NAME,"8WFuC0sjswy7MlFKkloc6GzM9EuVOHTfRwUx609BCSa0PE8mtl");
        mapUsersDTOMock.put(UsersConstantes.BIRTHDAY,LocalDate.of(4005,3,8));
        mapUsersDTOMock.put(UsersConstantes.STATUS,"IbhQE3vdKXnSAi11n6TWTF4x6wbOmqJFQW8xYW3ncqnkqQmo4S");


        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.partialUpdate(1L, mapUsersDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("Users não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnUsersListWhenFindAllUsersByIdAndStatus() {
        // scenario
        List<Users> userss = Arrays.asList(
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now()
        );

        Mockito.when(usersRepositoryMock.findAllByIdAndStatus(27377L, "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByIdAndStatus(27377L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUsersListWhenFindAllUsersByNameAndStatus() {
        // scenario
        List<Users> userss = Arrays.asList(
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now()
        );

        Mockito.when(usersRepositoryMock.findAllByNameAndStatus("IKiwmQhHEmIrTnExaa3vooskXXDdsGApLbG6uu0r0RiuHkric4", "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByNameAndStatus("IKiwmQhHEmIrTnExaa3vooskXXDdsGApLbG6uu0r0RiuHkric4", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUsersListWhenFindAllUsersByBirthdayAndStatus() {
        // scenario
        List<Users> userss = Arrays.asList(
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now()
        );

        Mockito.when(usersRepositoryMock.findAllByBirthdayAndStatus(LocalDate.of(42,11,3), "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByBirthdayAndStatus(LocalDate.of(42,11,3), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUsersListWhenFindAllUsersByDateCreatedAndStatus() {
        // scenario
        List<Users> userss = Arrays.asList(
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now()
        );

        Mockito.when(usersRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUsersListWhenFindAllUsersByDateUpdatedAndStatus() {
        // scenario
        List<Users> userss = Arrays.asList(
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now()
        );

        Mockito.when(usersRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByIdAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByIdAndStatus(46180L, "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByIdAndStatus(46180L, "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersIdAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByIdAndStatus(46180L, "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByIdAndStatus(46180L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByNameAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByNameAndStatus("uBp36pYEDlwUlqhVDWAqb5cEagNLJEO0gj3KeiPs7648WVUkdu", "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByNameAndStatus("uBp36pYEDlwUlqhVDWAqb5cEagNLJEO0gj3KeiPs7648WVUkdu", "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersNameAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByNameAndStatus("uBp36pYEDlwUlqhVDWAqb5cEagNLJEO0gj3KeiPs7648WVUkdu", "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByNameAndStatus("uBp36pYEDlwUlqhVDWAqb5cEagNLJEO0gj3KeiPs7648WVUkdu", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_NAME));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByBirthdayAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByBirthdayAndStatus(LocalDate.of(8804,12,27), "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByBirthdayAndStatus(LocalDate.of(8804,12,27), "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersBirthdayAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByBirthdayAndStatus(LocalDate.of(8804,12,27), "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByBirthdayAndStatus(LocalDate.of(8804,12,27), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_BIRTHDAY));
    }

    @Test
    public void shouldReturnUsersDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "HVsmVdX6LlOKrfsTVntn4eswxRWP1e67Pfhgb2XVnHek9csuMC";
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(usersRepositoryMock.findById(420L)).thenReturn(usersModelMock);
        Mockito.doNothing().when(usersRepositoryMock).updateNameById(420L, nameUpdateMock);

        // action
        usersService.updateNameById(420L, nameUpdateMock);

        // validate
        Mockito.verify(usersRepositoryMock,Mockito.times(1)).updateNameById(420L, nameUpdateMock);
    }
    @Test
    public void shouldReturnUsersDTOWhenUpdateExistingBirthdayById() {
        // scenario
        LocalDate birthdayUpdateMock = LocalDate.of(3541,5,20);
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(usersRepositoryMock.findById(420L)).thenReturn(usersModelMock);
        Mockito.doNothing().when(usersRepositoryMock).updateBirthdayById(420L, birthdayUpdateMock);

        // action
        usersService.updateBirthdayById(420L, birthdayUpdateMock);

        // validate
        Mockito.verify(usersRepositoryMock,Mockito.times(1)).updateBirthdayById(420L, birthdayUpdateMock);
    }



    @Test
    public void showReturnExistingUsersDTOWhenFindUsersByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 7817L;
        Long maxIdMock = 1972L;
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(usersRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(usersRepositoryMock.findById(maxIdMock)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnUsersNotFoundExceptionWhenNonExistenceFindUsersByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 7817L;
        Long noMaxIdMock = 0L;
        Optional<Users> usersModelMock = Optional.empty();
        Mockito.when(usersRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(usersRepositoryMock.findById(noMaxIdMock)).thenReturn(usersModelMock);

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUsersDTOWhenFindUsersByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "Cvir0kROppoHMTkE0Tnf0w6N73x0aY0mYkGukh2CbpWFNMCXxI";
        Long maxIdMock = 1972L;
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                .name(nameMock)
                .now()
        );
        Mockito.when(usersRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(maxIdMock);
        Mockito.when(usersRepositoryMock.findById(maxIdMock)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByNameAndStatus(nameMock);

        // validate
        Assertions.assertEquals(nameMock, result.getName());

    }
    @Test
    public void showReturnUsersNotFoundExceptionWhenNonExistenceFindUsersByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "Cvir0kROppoHMTkE0Tnf0w6N73x0aY0mYkGukh2CbpWFNMCXxI";
        Long noMaxIdMock = 0L;
        Optional<Users> usersModelMock = Optional.empty();
        Mockito.when(usersRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(usersRepositoryMock.findById(noMaxIdMock)).thenReturn(usersModelMock);

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByNameAndStatus(nameMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_NAME));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUsersDTOWhenFindUsersByBirthdayAndStatusActiveAnonimous() {
        // scenario
        LocalDate birthdayMock = LocalDate.of(570,1,4);
        Long maxIdMock = 1972L;
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                .birthday(birthdayMock)
                .now()
        );
        Mockito.when(usersRepositoryMock.loadMaxIdByBirthdayAndStatus(birthdayMock, "A")).thenReturn(maxIdMock);
        Mockito.when(usersRepositoryMock.findById(maxIdMock)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByBirthdayAndStatus(birthdayMock);

        // validate
        Assertions.assertEquals(birthdayMock, result.getBirthday());

    }
    @Test
    public void showReturnUsersNotFoundExceptionWhenNonExistenceFindUsersByBirthdayAndStatusActiveAnonimous() {
        // scenario
        LocalDate birthdayMock = LocalDate.of(570,1,4);
        Long noMaxIdMock = 0L;
        Optional<Users> usersModelMock = Optional.empty();
        Mockito.when(usersRepositoryMock.loadMaxIdByBirthdayAndStatus(birthdayMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(usersRepositoryMock.findById(noMaxIdMock)).thenReturn(usersModelMock);

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByBirthdayAndStatus(birthdayMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_BIRTHDAY));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

