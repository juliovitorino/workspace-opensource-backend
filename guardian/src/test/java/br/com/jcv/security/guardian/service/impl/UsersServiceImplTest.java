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

@TestInstance(PER_CLASS)
public class UsersServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String USERS_NOTFOUND_WITH_ID = "Users não encontrada com id = ";
    public static final String USERS_NOTFOUND_WITH_NAME = "Users não encontrada com name = ";
    public static final String USERS_NOTFOUND_WITH_EMAIL = "Users não encontrada com email = ";
    public static final String USERS_NOTFOUND_WITH_ENCODEDPASSPHRASE = "Users não encontrada com encodedPassPhrase = ";
    public static final String USERS_NOTFOUND_WITH_ENCODED_PWD = "Users não encontrada com encodedPassPhrase = ";
    public static final String USERS_NOTFOUND_WITH_IDUSERUUID = "Users não encontrada com idUserUUID = ";
    public static final String USERS_NOTFOUND_WITH_BIRTHDAY = "Users não encontrada com birthday = ";
    public static final String USERS_NOTFOUND_WITH_STATUS = "Users não encontrada com status = ";
    public static final String USERS_NOTFOUND_WITH_DATECREATED = "Users não encontrada com dateCreated = ";
    public static final String USERS_NOTFOUND_WITH_DATEUPDATED = "Users não encontrada com dateUpdated = ";


    @Mock
    private UsersRepository usersRepositoryMock;

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
        Long id = 82063L;
        String name = "zK8EooDWMRzg0RxuOtPWdsUJ0DH4nmhb50NAbQMDfNGTe13GXD";
        String email = "3sh03brGCpNt8pSQzeExyIz2lKfsfNsJfE9S2ie2LvMyQfEsYz";
        String encodedPassPhrase = "pIS90rBtNN0VGWo62fbomfoW1TBR8j43qjPq0NKibfYsd9OG8S";
        UUID idUserUUID = UUID.fromString("ee781a8b-8efb-41df-b9ca-e587b27feac7");
        String birthday = "2025-10-07";
        String status = "z4TGPga00lhU8BnzpEsSC6U0rhC64PIzoRDvtCcCreglRLbTSg";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("email", email);
        mapFieldsRequestMock.put("encodedPassPhrase", encodedPassPhrase);
        mapFieldsRequestMock.put("idUserUUID", idUserUUID);
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
            email,
            encodedPassPhrase,
            idUserUUID,
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
        Long id = 22321L;
        String name = "Hq4duowQjK078wMAVAH8IhJOFzLT6leznpg5XfsXp9QGEILNxn";
        String email = "o8L5ASz3xQAul2O5rWMO0INjhDnlQauPSXQX2NmwIfl7YpDyym";
        String encodedPassPhrase = "JyUBe0aB0Rh8delLnD6DICYEtGFt7stO1MLRYPIEbfKmhDBYfB";
        UUID idUserUUID = UUID.fromString("12169d2d-dd4e-4fa1-b11b-8ca3637aa764");
        String birthday = "2025-10-07";
        String status = "o2POlgAfNlXIElUGI9jzA1PvcFWNXUDNaJ4INawU8rlerQ36dg";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("email", email);
        mapFieldsRequestMock.put("encodedPassPhrase", encodedPassPhrase);
        mapFieldsRequestMock.put("idUserUUID", idUserUUID);
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
            email,
            encodedPassPhrase,
            idUserUUID,
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
        Long idMock = 11061L;
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
        Long idMock = 78770L;
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
        Long idMock = 68586L;
        Optional<Users> usersModelMock = Optional.ofNullable(
                UsersModelBuilder.newUsersModelTestBuilder()
                        .id(idMock)
                        .name("HAtnDnBnr8TgJdTXdgoCGqFMJ6izAI7DFfDSV8j0dj6OacXCXA")
                        .email("cJ48042ulVPlLRMAVA2cwPuXxN6SidsDF70lUKhDOFkFWjw3SK")
                        .encodedPassPhrase("9ythn1ca0HGNSXz11FArWySPBdrkx0xUYjaFd0ANGBzFViaHmm")
                        .idUserUUID(UUID.fromString("fe4d83ff-6f7f-4717-b8d4-b4f018ff80d5"))
                        .birthday(LocalDate.of(4800,12,12))

                        .status("X")
                        .now()
        );
        Users usersToSaveMock = usersModelMock.orElse(null);
        Users usersSavedMck = UsersModelBuilder.newUsersModelTestBuilder()
                        .id(35028L)
                        .name("Q2qcRALQH0Eie7pvStp0U8UdlCG3uWvb0Sq3WUx02p6GH3Hsnh")
                        .email("MWYrJOSfc34NYVh2ArYYiTTwW7W0Vb2NCmQSMHzBX8Pp2tprtM")
                        .encodedPassPhrase("B9uyCg7fnvKW1tQuYWTM1IEhJ5ccfALVX9XV43vNN7K1rj0iXr")
                        .idUserUUID(UUID.fromString("d704455f-7655-4290-8d03-f6b3bd76fcc2"))
                        .birthday(LocalDate.of(7288,6,3))

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
                .id(25352L)
                .name("7bl78KGNcGwrDIG2GuaUvxpYcq2hvy6sByvTjafLKMRgqRg9js")
                .email("EMXdFL7gapHuN3VIWIes4eNWqbSVU03agf3HVGicDSDvReDIQD")
                .encodedPassPhrase("b0lKug5s0s8in5xXir60rMUW9EjFYlEJiT99TO01KpdFOy1ely")
                .idUserUUID(UUID.fromString("43325d69-c209-4b2a-bae8-2eba90badbfc"))
                .birthday(LocalDate.of(8402,6,30))

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
                .id(64767L)
                .name("u9fmIMq4u9TdijEaOihWpH90ms2yg1wxV8bm9WL4E5t4dbs8Nh")
                .email("tg6sNOxDRieXzrvKhJdlKbJ3K5LKFbDa9RdGzsubyYSPDeaeOY")
                .encodedPassPhrase("eXEocc9T0btgR2n2xNt2GXGnHPrIb6D8g23auoyytaOOr6v0fJ")
                .idUserUUID(UUID.fromString("cfa33f3f-623c-43eb-8390-1be18c365bc4"))
                .birthday(LocalDate.of(707,8,11))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(usersDTOMock.getId())
                .name(usersDTOMock.getName())
                .email(usersDTOMock.getEmail())
                .encodedPassPhrase(usersDTOMock.getEncodedPassPhrase())
                .idUserUUID(usersDTOMock.getIdUserUUID())
                .birthday(usersDTOMock.getBirthday())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersSavedMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(usersDTOMock.getId())
                .name(usersDTOMock.getName())
                .email(usersDTOMock.getEmail())
                .encodedPassPhrase(usersDTOMock.getEncodedPassPhrase())
                .idUserUUID(usersDTOMock.getIdUserUUID())
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
                .name("m0P6wQ31vPYvwk8LCpB0SyyrvpRdjOnG0qJqSPfftTBtcpktYg")
                .email("bbGLvTp9HFbylHyY7b0NNL6VP0eIuIW6rktdentDtOEBozihMI")
                .encodedPassPhrase("mMre1Qo18uRMx1is81vN73GG2AJd1SG1U02AHeePuFBmjHIqAc")
                .idUserUUID(UUID.fromString("12c3067e-a903-4ba2-8794-3a2493891c41"))
                .birthday(LocalDate.of(4505,11,16))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersModelMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(null)
                .name(usersDTOMock.getName())
                .email(usersDTOMock.getEmail())
                .encodedPassPhrase(usersDTOMock.getEncodedPassPhrase())
                .idUserUUID(usersDTOMock.getIdUserUUID())
                .birthday(usersDTOMock.getBirthday())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersSavedMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(501L)
                .name(usersDTOMock.getName())
                .email(usersDTOMock.getEmail())
                .encodedPassPhrase(usersDTOMock.getEncodedPassPhrase())
                .idUserUUID(usersDTOMock.getIdUserUUID())
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
        mapUsersDTOMock.put(UsersConstantes.NAME,"EQPIHGcu0eMj2keQyRMHMDvxGFndwTeMCWX9HW0oBwk7yTfaD1");
        mapUsersDTOMock.put(UsersConstantes.EMAIL,"yqmVKbDuVea29MsE6GhvJ6NknX8K1H3W0emLQ0anh4r0nAhspk");
        mapUsersDTOMock.put(UsersConstantes.ENCODEDPASSPHRASE,"KVbkCL7dH4X6WFhiiu128S11YMeaf3MJOKuLFlAuky4jDAIuUC");
        mapUsersDTOMock.put(UsersConstantes.IDUSERUUID,UUID.fromString("3d33e12c-7470-4e70-8544-40ffe52e9387"));
        mapUsersDTOMock.put(UsersConstantes.BIRTHDAY,LocalDate.of(1450,1,21));
        mapUsersDTOMock.put(UsersConstantes.STATUS,"aydjFy2rPk2bLffww5MJdwWBuHNsBS1AN0tCX0Hzk0gdnfdW5z");


        Optional<Users> usersModelMock = Optional.ofNullable(
                UsersModelBuilder.newUsersModelTestBuilder()
                        .id(18800L)
                        .name("GiBz8nAT9qgMfaBJzG07H60QmCI8yvfL3nxWK5irFbPnTf5Cnx")
                        .email("EhjMuBgnMWTNQawtUkkSrnCOwkubjhGtYPGe0SSdx7R1z6Xt8i")
                        .encodedPassPhrase("nv0opM4Cq4QlLYemFpdjyQGcT0TkC54R6f6kJkIXPxSw3Yaqan")
                        .idUserUUID(UUID.fromString("e6d83c5f-29dd-46dc-a264-0c9eec66c12f"))
                        .birthday(LocalDate.of(4031,7,31))
                        .status("3ctfi1xLYYuMmoEkj223MOJgGKFk5jil2oqh3h50sNNpP6UTB0")

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
        mapUsersDTOMock.put(UsersConstantes.NAME,"IpXn49xqPD0bcaPbByNhxhJqiXNsGeRr854dIE6nt1nJDAI0Id");
        mapUsersDTOMock.put(UsersConstantes.EMAIL,"Q7nJOcElQhI1vB0lIF1Sfu8bow0F8BQq0dqo8uB50BN7FmbeUL");
        mapUsersDTOMock.put(UsersConstantes.ENCODEDPASSPHRASE,"IOENPl4HRtqf3JLwhxJS5wSF10lK5QID65SaF0DfQSIAwHngvC");
        mapUsersDTOMock.put(UsersConstantes.IDUSERUUID,UUID.fromString("b149825d-580f-4a01-8aa8-105af39f1a14"));
        mapUsersDTOMock.put(UsersConstantes.BIRTHDAY,LocalDate.of(505,5,23));
        mapUsersDTOMock.put(UsersConstantes.STATUS,"4NwggrMT0lL0YP5Ge8qJuxIOiUET8RyKdJiDPIzrc0TtNQXrYG");


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

        Mockito.when(usersRepositoryMock.findAllByIdAndStatus(75566L, "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByIdAndStatus(75566L, "A");

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

        Mockito.when(usersRepositoryMock.findAllByNameAndStatus("7AmMeDM170ROSgY67JCfBBUI3QgqzfmV4CVDFKnXvpigs7Y9il", "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByNameAndStatus("7AmMeDM170ROSgY67JCfBBUI3QgqzfmV4CVDFKnXvpigs7Y9il", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUsersListWhenFindAllUsersByEmailAndStatus() {
        // scenario
        List<Users> userss = Arrays.asList(
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now()
        );

        Mockito.when(usersRepositoryMock.findAllByEmailAndStatus("viSJVLLSorqY8BrItnYAdf0HzjBTxOhzmqgOO1Yvjh7qhD1M2J", "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByEmailAndStatus("viSJVLLSorqY8BrItnYAdf0HzjBTxOhzmqgOO1Yvjh7qhD1M2J", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUsersListWhenFindAllUsersByEncodedPassPhraseAndStatus() {
        // scenario
        List<Users> userss = Arrays.asList(
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now()
        );

        Mockito.when(usersRepositoryMock.findAllByEncodedPassPhraseAndStatus("Ilp4rqLeatFbSttoy0G0MI43fxMQ9HEqODVlEx0Fyv0Tm3nxJs", "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByEncodedPassPhraseAndStatus("Ilp4rqLeatFbSttoy0G0MI43fxMQ9HEqODVlEx0Fyv0Tm3nxJs", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUsersListWhenFindAllUsersByIdUserUUIDAndStatus() {
        // scenario
        List<Users> userss = Arrays.asList(
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now(),
            UsersModelBuilder.newUsersModelTestBuilder().now()
        );

        Mockito.when(usersRepositoryMock.findAllByIdUserUUIDAndStatus(UUID.fromString("227ad249-369d-4d2c-a972-8a817a1fc9fd"), "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByIdUserUUIDAndStatus(UUID.fromString("227ad249-369d-4d2c-a972-8a817a1fc9fd"), "A");

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

        Mockito.when(usersRepositoryMock.findAllByBirthdayAndStatus(LocalDate.of(5006,7,6), "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByBirthdayAndStatus(LocalDate.of(5006,7,6), "A");

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
        Mockito.when(usersRepositoryMock.loadMaxIdByIdAndStatus(1474L, "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByIdAndStatus(1474L, "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersIdAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByIdAndStatus(1474L, "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByIdAndStatus(1474L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByNameAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByNameAndStatus("w8jd8nVNqDcXxF9RbV5NG0d8c80BKds3qLD3uM23MQOPVjrvO0", "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByNameAndStatus("w8jd8nVNqDcXxF9RbV5NG0d8c80BKds3qLD3uM23MQOPVjrvO0", "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersNameAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByNameAndStatus("w8jd8nVNqDcXxF9RbV5NG0d8c80BKds3qLD3uM23MQOPVjrvO0", "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByNameAndStatus("w8jd8nVNqDcXxF9RbV5NG0d8c80BKds3qLD3uM23MQOPVjrvO0", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_NAME));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByEmailAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByEmailAndStatus("Rond089hkd663kpI9hMl0Ve1cPOOiQW1pLUY0wUrv0fBhXm9Pa", "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByEmailAndStatus("Rond089hkd663kpI9hMl0Ve1cPOOiQW1pLUY0wUrv0fBhXm9Pa", "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersEmailAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByEmailAndStatus("Rond089hkd663kpI9hMl0Ve1cPOOiQW1pLUY0wUrv0fBhXm9Pa", "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByEmailAndStatus("Rond089hkd663kpI9hMl0Ve1cPOOiQW1pLUY0wUrv0fBhXm9Pa", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_EMAIL));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByEncodedPassPhraseAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus("iB33qwruyDzf4M2BKzDyAM7L1XnaAhUmaK6oupvl5aKbfXkYdf", "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByEncodedPassPhraseAndStatus("iB33qwruyDzf4M2BKzDyAM7L1XnaAhUmaK6oupvl5aKbfXkYdf", "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersEncodedPassPhraseAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus("iB33qwruyDzf4M2BKzDyAM7L1XnaAhUmaK6oupvl5aKbfXkYdf", "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByEncodedPassPhraseAndStatus("iB33qwruyDzf4M2BKzDyAM7L1XnaAhUmaK6oupvl5aKbfXkYdf", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ENCODED_PWD));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByIdUserUUIDAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(UUID.fromString("93587e8e-9cd8-4fcd-85cb-6078f7b62f32"), "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByIdUserUUIDAndStatus(UUID.fromString("93587e8e-9cd8-4fcd-85cb-6078f7b62f32"), "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersIdUserUUIDAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(UUID.fromString("93587e8e-9cd8-4fcd-85cb-6078f7b62f32"), "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByIdUserUUIDAndStatus(UUID.fromString("93587e8e-9cd8-4fcd-85cb-6078f7b62f32"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_IDUSERUUID));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByBirthdayAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByBirthdayAndStatus(LocalDate.of(1800,3,11), "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByBirthdayAndStatus(LocalDate.of(1800,3,11), "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersBirthdayAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByBirthdayAndStatus(LocalDate.of(1800,3,11), "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByBirthdayAndStatus(LocalDate.of(1800,3,11), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_BIRTHDAY));
    }

    @Test
    public void shouldReturnUsersDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "3UTpjkYN5rmmfzx0RjQJ0fVMRkYswyNsCDn0OQ00VMFeYcxLkL";
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
    public void shouldReturnUsersDTOWhenUpdateExistingEmailById() {
        // scenario
        String emailUpdateMock = "HjegVBdzu23X6LJj0hKGFUFCynwI2hedwMqsUrB1v7jhsf943z";
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(usersRepositoryMock.findById(420L)).thenReturn(usersModelMock);
        Mockito.doNothing().when(usersRepositoryMock).updateEmailById(420L, emailUpdateMock);

        // action
        usersService.updateEmailById(420L, emailUpdateMock);

        // validate
        Mockito.verify(usersRepositoryMock,Mockito.times(1)).updateEmailById(420L, emailUpdateMock);
    }
    @Test
    public void shouldReturnUsersDTOWhenUpdateExistingEncodedPassPhraseById() {
        // scenario
        String encodedPassPhraseUpdateMock = "rz130AJNn84mo7HPwk1D1dkcSY0X0cLl5BYQKLbctNLRYn4t0F";
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(usersRepositoryMock.findById(420L)).thenReturn(usersModelMock);
        Mockito.doNothing().when(usersRepositoryMock).updateEncodedPassPhraseById(420L, encodedPassPhraseUpdateMock);

        // action
        usersService.updateEncodedPassPhraseById(420L, encodedPassPhraseUpdateMock);

        // validate
        Mockito.verify(usersRepositoryMock,Mockito.times(1)).updateEncodedPassPhraseById(420L, encodedPassPhraseUpdateMock);
    }
    @Test
    public void shouldReturnUsersDTOWhenUpdateExistingIdUserUUIDById() {
        // scenario
        UUID idUserUUIDUpdateMock = UUID.fromString("a1843839-0d2e-46e5-b102-2d6eb1600c0c");
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(usersRepositoryMock.findById(420L)).thenReturn(usersModelMock);
        Mockito.doNothing().when(usersRepositoryMock).updateIdUserUUIDById(420L, idUserUUIDUpdateMock);

        // action
        usersService.updateIdUserUUIDById(420L, idUserUUIDUpdateMock);

        // validate
        Mockito.verify(usersRepositoryMock,Mockito.times(1)).updateIdUserUUIDById(420L, idUserUUIDUpdateMock);
    }
    @Test
    public void shouldReturnUsersDTOWhenUpdateExistingBirthdayById() {
        // scenario
        LocalDate birthdayUpdateMock = LocalDate.of(1808,2,18);
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
        Long idMock = 20352L;
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
        Long idMock = 20352L;
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
        String nameMock = "xTvCAiThiqrn0qzVHmT06RMp7CxjIcBtdjoMpzdkSUC4DyPsIO";
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
        String nameMock = "xTvCAiThiqrn0qzVHmT06RMp7CxjIcBtdjoMpzdkSUC4DyPsIO";
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
    public void showReturnExistingUsersDTOWhenFindUsersByEmailAndStatusActiveAnonimous() {
        // scenario
        String emailMock = "eOQ6FJ09qykdKkvquUzKg1BWl0mASyH0roXk9MGhAGXQFqfwgM";
        Long maxIdMock = 1972L;
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                .email(emailMock)
                .now()
        );
        Mockito.when(usersRepositoryMock.loadMaxIdByEmailAndStatus(emailMock, "A")).thenReturn(maxIdMock);
        Mockito.when(usersRepositoryMock.findById(maxIdMock)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByEmailAndStatus(emailMock);

        // validate
        Assertions.assertEquals(emailMock, result.getEmail());

    }
    @Test
    public void showReturnUsersNotFoundExceptionWhenNonExistenceFindUsersByEmailAndStatusActiveAnonimous() {
        // scenario
        String emailMock = "eOQ6FJ09qykdKkvquUzKg1BWl0mASyH0roXk9MGhAGXQFqfwgM";
        Long noMaxIdMock = 0L;
        Optional<Users> usersModelMock = Optional.empty();
        Mockito.when(usersRepositoryMock.loadMaxIdByEmailAndStatus(emailMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(usersRepositoryMock.findById(noMaxIdMock)).thenReturn(usersModelMock);

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByEmailAndStatus(emailMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_EMAIL));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUsersDTOWhenFindUsersByEncodedPassPhraseAndStatusActiveAnonimous() {
        // scenario
        String encodedPassPhraseMock = "0yggBfuhLzJu0xqjVuDlelFlHBRNFVS3rO7Gqmem0je1UB0LCE";
        Long maxIdMock = 1972L;
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                .encodedPassPhrase(encodedPassPhraseMock)
                .now()
        );
        Mockito.when(usersRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus(encodedPassPhraseMock, "A")).thenReturn(maxIdMock);
        Mockito.when(usersRepositoryMock.findById(maxIdMock)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByEncodedPassPhraseAndStatus(encodedPassPhraseMock);

        // validate
        Assertions.assertEquals(encodedPassPhraseMock, result.getEncodedPassPhrase());

    }
    @Test
    public void showReturnUsersNotFoundExceptionWhenNonExistenceFindUsersByEncodedPassPhraseAndStatusActiveAnonimous() {
        // scenario
        String encodedPassPhraseMock = "0yggBfuhLzJu0xqjVuDlelFlHBRNFVS3rO7Gqmem0je1UB0LCE";
        Long noMaxIdMock = 0L;
        Optional<Users> usersModelMock = Optional.empty();
        Mockito.when(usersRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus(encodedPassPhraseMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(usersRepositoryMock.findById(noMaxIdMock)).thenReturn(usersModelMock);

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByEncodedPassPhraseAndStatus(encodedPassPhraseMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ENCODED_PWD));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUsersDTOWhenFindUsersByIdUserUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID idUserUUIDMock = UUID.fromString("e6213868-9e41-42f1-96dc-a7177bc1c74e");
        Long maxIdMock = 1972L;
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder()
                .idUserUUID(idUserUUIDMock)
                .now()
        );
        Mockito.when(usersRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(idUserUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(usersRepositoryMock.findById(maxIdMock)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByIdUserUUIDAndStatus(idUserUUIDMock);

        // validate
        Assertions.assertEquals(idUserUUIDMock, result.getIdUserUUID());

    }
    @Test
    public void showReturnUsersNotFoundExceptionWhenNonExistenceFindUsersByIdUserUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID idUserUUIDMock = UUID.fromString("e6213868-9e41-42f1-96dc-a7177bc1c74e");
        Long noMaxIdMock = 0L;
        Optional<Users> usersModelMock = Optional.empty();
        Mockito.when(usersRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(idUserUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(usersRepositoryMock.findById(noMaxIdMock)).thenReturn(usersModelMock);

        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByIdUserUUIDAndStatus(idUserUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_IDUSERUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUsersDTOWhenFindUsersByBirthdayAndStatusActiveAnonimous() {
        // scenario
        LocalDate birthdayMock = LocalDate.of(51,1,6);
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
        LocalDate birthdayMock = LocalDate.of(51,1,6);
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

