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
        Long id = 78001L;
        String name = "632owRG3VKm5PmBh988yE0aYhtAeJwyenVrmSD7XqlRs908cfi";
        String encodedPassPhrase = "fQFua3Mm1WqWt95RLAhnmKolWhUOnAgBuz40efhYEF1LLNKAq7";
        UUID idUserUUID = UUID.fromString("8357a668-ec15-40a3-8ef2-5079d2606ce8");
        String birthday = "2025-10-07";
        String status = "DnlnJeGTGngroa4lCyqoifyJbvgwoOYe6F2irOBJSS3nhHcWuK";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
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
        Long id = 81747L;
        String name = "Y0Q2ah3MXMPpeL8JFOeR0rfpgAqAPSt1KvPPX3MbKam7rUennz";
        String encodedPassPhrase = "Tf7XtzGyqk08W053Ic8HnxlTa10bIuPbzlsCnPNc2l046EIrih";
        UUID idUserUUID = UUID.fromString("22184acc-32bb-44c0-b597-99641a658c4d");
        String birthday = "2025-10-07";
        String status = "UBuNyfaB0WRqN5JHzrNGUVlpwCYdqMm3nsQcLWq9plPeALT5d4";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
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
        Long idMock = 60408L;
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
        Long idMock = 23845L;
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
        Long idMock = 67640L;
        Optional<Users> usersModelMock = Optional.ofNullable(
                UsersModelBuilder.newUsersModelTestBuilder()
                        .id(idMock)
                        .name("Vp7fD3jGgk8MzC0tF5lfJ1tnC5wDjhwP5QbWB9oJj6D8d2Kgtt")
                        .encodedPassPhrase("BtxAmrxWIXeCIAQBoOCal1YF8O9dAjMURBNJrd41gCgdbAGVII")
                        .idUserUUID(UUID.fromString("e0319cac-a94a-45b9-a6cf-87147265eaec"))
                        .birthday(LocalDate.of(557,1,5))

                        .status("X")
                        .now()
        );
        Users usersToSaveMock = usersModelMock.orElse(null);
        Users usersSavedMck = UsersModelBuilder.newUsersModelTestBuilder()
                        .id(10028L)
                        .name("5xo1uVPJY2DlNwxqbOnq4F3WrgWVFWD8YfR5gV6l6YtTmOaVw8")
                        .encodedPassPhrase("yPECVx4OQxfPYWxytJ6mRyGU1fEr5NwzwcdLAeTQP2ToxrrsDG")
                        .idUserUUID(UUID.fromString("a8dbb27f-4a69-4680-8dfc-592699ec86ab"))
                        .birthday(LocalDate.of(1717,10,31))

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
                .id(38030L)
                .name("xtS32nd0P4ll5Xfd1AwRCFagt3gesIx3z0SdV1JFTO840cxxRx")
                .encodedPassPhrase("E0Bui1KorA1VpIBfCbYNNfcjd5B49XpAhx3EutVSnkAl1iSA0X")
                .idUserUUID(UUID.fromString("050a0f77-54a7-4556-8895-7d73800101f4"))
                .birthday(LocalDate.of(8844,4,18))

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
                .id(3038L)
                .name("DuK0HUSwo5UkkV8zAK83su4O997ChY0gqahB7e7sHo6CrMxBV9")
                .encodedPassPhrase("h7r6MosrB6JpwUTrHVsez4DMVBD6F3aHdIqNduVMFzGDnSo07F")
                .idUserUUID(UUID.fromString("703129b0-36e9-498d-8580-d59538d7dad5"))
                .birthday(LocalDate.of(837,10,25))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(usersDTOMock.getId())
                .name(usersDTOMock.getName())
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
                .name("Ydl6G2jkAPIUcCr59RBikb6VA7UgFnevQggcEuCnWwdKhbIbOX")
                .encodedPassPhrase("dTmX1UsUSKrRcc5orODt9092vIVYhgUGrxpqVFWsvzjF2I7qbI")
                .idUserUUID(UUID.fromString("afce04dc-9c66-48db-b886-590cd45b3bb2"))
                .birthday(LocalDate.of(6043,4,2))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Users usersModelMock = UsersModelBuilder.newUsersModelTestBuilder()
                .id(null)
                .name(usersDTOMock.getName())
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
        mapUsersDTOMock.put(UsersConstantes.NAME,"ruJyTkvSsLvMb7UVuVtKEpAeEw3PEu2WWTkyvfBuU7Dmzgvq2v");
        mapUsersDTOMock.put(UsersConstantes.ENCODED_PWD,"YUtdV1BObI6KLajwcSm9u7u8hLN64WpkJ4tSEMReIdfHMDlKop");
        mapUsersDTOMock.put(UsersConstantes.IDUSERUUID,UUID.fromString("f50dc708-bfbc-4533-83a4-2eaef1b6b841"));
        mapUsersDTOMock.put(UsersConstantes.BIRTHDAY,LocalDate.of(6076,5,3));
        mapUsersDTOMock.put(UsersConstantes.STATUS,"5vOPl2q4Kcx2VGI9i0AFKaK0jGcS3Brk30PErz195ilO5CA1YF");


        Optional<Users> usersModelMock = Optional.ofNullable(
                UsersModelBuilder.newUsersModelTestBuilder()
                        .id(80701L)
                        .name("fW60U3IARU1NOHiFzDNoX1DUX0IxzwSGm7Bqs97smFPCXdy5G5")
                        .encodedPassPhrase("5EkV25FloxOsf9bdTTVIOiSaHhcsMCeO5RFUpT2ucqplzG1zSK")
                        .idUserUUID(UUID.fromString("5eecd0da-d884-46ff-8aad-0acaa1519892"))
                        .birthday(LocalDate.of(5336,7,10))
                        .status("v2ewbUpxMY40YTFBrI5lolYSlE0kfN2Ws3U28xvAss6xcevsh7")

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
        mapUsersDTOMock.put(UsersConstantes.NAME,"hzt6u1b3Lw9f7iEjpy65rAhS3sAMJutXDJ0UvLdNoyjLcoW0F9");
        mapUsersDTOMock.put(UsersConstantes.ENCODED_PWD,"cxumwIogBnzbjkdF4IwCiDCiB9u098F6U7nFoQ0yhJzUhNIAXi");
        mapUsersDTOMock.put(UsersConstantes.IDUSERUUID,UUID.fromString("e53ccf8a-57cf-4e2e-abad-260c7b44da60"));
        mapUsersDTOMock.put(UsersConstantes.BIRTHDAY,LocalDate.of(1424,3,22));
        mapUsersDTOMock.put(UsersConstantes.STATUS,"LJInij4ymigXIKHSJS0oWjcbdmiaWqSto547jazIUHNSLg7PG8");


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

        Mockito.when(usersRepositoryMock.findAllByIdAndStatus(27115L, "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByIdAndStatus(27115L, "A");

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

        Mockito.when(usersRepositoryMock.findAllByNameAndStatus("2tMaA6zMfEzxO923QetN17CFETRTniDMhip9nirzpFibnqGgqM", "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByNameAndStatus("2tMaA6zMfEzxO923QetN17CFETRTniDMhip9nirzpFibnqGgqM", "A");

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

        Mockito.when(usersRepositoryMock.findAllByEncodedPassPhraseAndStatus("GHh4cDwoXxU0823DEwisVos0v2slJbsMo531hWXd43wH0lQKoE", "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByEncodedPassPhraseAndStatus("GHh4cDwoXxU0823DEwisVos0v2slJbsMo531hWXd43wH0lQKoE", "A");

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

        Mockito.when(usersRepositoryMock.findAllByIdUserUUIDAndStatus(UUID.fromString("113e1b61-5ec4-4714-b175-b112e8125822"), "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByIdUserUUIDAndStatus(UUID.fromString("113e1b61-5ec4-4714-b175-b112e8125822"), "A");

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

        Mockito.when(usersRepositoryMock.findAllByBirthdayAndStatus(LocalDate.of(2180,9,2), "A")).thenReturn(userss);

        // action
        List<UsersDTO> result = usersService.findAllUsersByBirthdayAndStatus(LocalDate.of(2180,9,2), "A");

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
        Mockito.when(usersRepositoryMock.loadMaxIdByIdAndStatus(62583L, "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByIdAndStatus(62583L, "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersIdAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByIdAndStatus(62583L, "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByIdAndStatus(62583L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByNameAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByNameAndStatus("W6tGRN7kiwzUoBOHQoF2fLQCFfsawJ1ljEvlRCxznMqTb2CSbd", "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByNameAndStatus("W6tGRN7kiwzUoBOHQoF2fLQCFfsawJ1ljEvlRCxznMqTb2CSbd", "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersNameAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByNameAndStatus("W6tGRN7kiwzUoBOHQoF2fLQCFfsawJ1ljEvlRCxznMqTb2CSbd", "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByNameAndStatus("W6tGRN7kiwzUoBOHQoF2fLQCFfsawJ1ljEvlRCxznMqTb2CSbd", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_NAME));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByEncodedPassPhraseAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus("7AUIxYXm5n7xXhYvziemAzanXfLhA8s1OkM1NIFOv1QvROXMxJ", "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByEncodedPassPhraseAndStatus("7AUIxYXm5n7xXhYvziemAzanXfLhA8s1OkM1NIFOv1QvROXMxJ", "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersEncodedPassPhraseAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus("7AUIxYXm5n7xXhYvziemAzanXfLhA8s1OkM1NIFOv1QvROXMxJ", "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByEncodedPassPhraseAndStatus("7AUIxYXm5n7xXhYvziemAzanXfLhA8s1OkM1NIFOv1QvROXMxJ", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_ENCODED_PWD));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByIdUserUUIDAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(UUID.fromString("6bb31641-3c0c-488c-930a-559378b633f9"), "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByIdUserUUIDAndStatus(UUID.fromString("6bb31641-3c0c-488c-930a-559378b633f9"), "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersIdUserUUIDAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByIdUserUUIDAndStatus(UUID.fromString("6bb31641-3c0c-488c-930a-559378b633f9"), "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByIdUserUUIDAndStatus(UUID.fromString("6bb31641-3c0c-488c-930a-559378b633f9"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_IDUSERUUID));
    }
    @Test
    public void shouldReturnExistentUsersDTOWhenFindUsersByBirthdayAndStatus() {
        // scenario
        Optional<Users> usersModelMock = Optional.ofNullable(UsersModelBuilder.newUsersModelTestBuilder().now());
        Mockito.when(usersRepositoryMock.loadMaxIdByBirthdayAndStatus(LocalDate.of(6167,5,6), "A")).thenReturn(1L);
        Mockito.when(usersRepositoryMock.findById(1L)).thenReturn(usersModelMock);

        // action
        UsersDTO result = usersService.findUsersByBirthdayAndStatus(LocalDate.of(6167,5,6), "A");

        // validate
        Assertions.assertInstanceOf(UsersDTO.class,result);
    }
    @Test
    public void shouldReturnUsersNotFoundExceptionWhenNonExistenceUsersBirthdayAndStatus() {
        // scenario
        Mockito.when(usersRepositoryMock.loadMaxIdByBirthdayAndStatus(LocalDate.of(6167,5,6), "A")).thenReturn(0L);
        Mockito.when(usersRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UsersNotFoundException exception = Assertions.assertThrows(UsersNotFoundException.class,
                ()->usersService.findUsersByBirthdayAndStatus(LocalDate.of(6167,5,6), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(USERS_NOTFOUND_WITH_BIRTHDAY));
    }

    @Test
    public void shouldReturnUsersDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "89z5BKMHusgeIQotQuXz3y2HeHXEhrmSLvsLm46Gll6lnE5xAR";
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
    public void shouldReturnUsersDTOWhenUpdateExistingEncodedPassPhraseById() {
        // scenario
        String encodedPassPhraseUpdateMock = "tJ4kj3X99U03xX8Ra5arhJ0mVEVg5QmOV2DUt0X8i9CLB6TBY8";
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
        UUID idUserUUIDUpdateMock = UUID.fromString("27677eec-4395-4558-860c-2f39e1f33ac4");
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
        LocalDate birthdayUpdateMock = LocalDate.of(4182,12,16);
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
        Long idMock = 87422L;
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
        Long idMock = 87422L;
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
        String nameMock = "KC5RjWllcJRMdf5dpLKK0qew3IwOti61YpEcKYTik5jHiT9H5s";
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
        String nameMock = "KC5RjWllcJRMdf5dpLKK0qew3IwOti61YpEcKYTik5jHiT9H5s";
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
    public void showReturnExistingUsersDTOWhenFindUsersByEncodedPassPhraseAndStatusActiveAnonimous() {
        // scenario
        String encodedPassPhraseMock = "YDLxu5JYsmMgdJRuCAOrGprcMFoWhmTgemfie3vOpk4DoLlIBD";
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
        String encodedPassPhraseMock = "YDLxu5JYsmMgdJRuCAOrGprcMFoWhmTgemfie3vOpk4DoLlIBD";
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
        UUID idUserUUIDMock = UUID.fromString("7f019dfd-ded2-4af6-bb00-eedb82005504");
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
        UUID idUserUUIDMock = UUID.fromString("7f019dfd-ded2-4af6-bb00-eedb82005504");
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
        LocalDate birthdayMock = LocalDate.of(6022,12,15);
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
        LocalDate birthdayMock = LocalDate.of(6022,12,15);
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

