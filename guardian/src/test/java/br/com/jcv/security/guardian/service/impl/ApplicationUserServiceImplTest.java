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
import br.com.jcv.security.guardian.builder.ApplicationUserDTOBuilder;
import br.com.jcv.security.guardian.builder.ApplicationUserModelBuilder;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.exception.ApplicationUserNotFoundException;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.ApplicationUser;
import br.com.jcv.security.guardian.repository.ApplicationUserRepository;
import br.com.jcv.security.guardian.service.ApplicationUserService;
import br.com.jcv.security.guardian.constantes.ApplicationUserConstantes;
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
public class ApplicationUserServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String APPLICATIONUSER_NOTFOUND_WITH_ID = "ApplicationUser não encontrada com id = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_IDUSER = "ApplicationUser não encontrada com id = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_EMAIL = "ApplicationUser não encontrada com email = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_ENCODEDPASSPHRASE = "ApplicationUser não encontrada com encodedPassPhrase = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_EXTERNALAPPUSERUUID = "ApplicationUser não encontrada com externalAppUserUUID = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_URLTOKENACTIVATION = "ApplicationUser não encontrada com urlTokenActivation = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_ACTIVATIONCODE = "ApplicationUser não encontrada com activationCode = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_DUEDATEACTIVATION = "ApplicationUser não encontrada com dueDateActivation = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_STATUS = "ApplicationUser não encontrada com status = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_DATECREATED = "ApplicationUser não encontrada com dateCreated = ";
    public static final String APPLICATIONUSER_NOTFOUND_WITH_DATEUPDATED = "ApplicationUser não encontrada com dateUpdated = ";


    @Mock
    private ApplicationUserRepository applicationuserRepositoryMock;

    @Mock
    private CacheProvider redisProviderMock;
    @Mock
    private Gson gsonMock;
    @InjectMocks
    private ApplicationUserService applicationuserService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        applicationuserService = new ApplicationUserServiceImpl();
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
    public void shouldReturnListOfApplicationUserWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 42067L;
        Long idUser = 30077L;
        String email = "9gbKCl83IcwG3l5bUh0TEgCx2UXzi6fG8td6gHiO5cchBtF7UU";
        String encodedPassPhrase = "FxFByqjFhuTaeSXJX6PzHwK0NggPH19FngJ17GwV69mne0Irs3";
        UUID externalAppUserUUID = UUID.fromString("8b4352fe-f553-4c4c-9945-be7b1681a693");
        String urlTokenActivation = "pSVFzA0jPmC4mah92qJME3b8vdSMXWrWikdIdLhXi952GoLoTt";
        String activationCode = "IoRXUwRQHyMn5mayzY0Xzz80NDABQK21Mgo8DKSR9HcliaghFb";
        String dueDateActivation = "2025-10-07";
        String status = "0eCr0aze7jpmUgVTbPGeNd0sxaFGTtSsCB00eudD0xgY2ibWrA";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idUser", idUser);
        mapFieldsRequestMock.put("email", email);
        mapFieldsRequestMock.put("encodedPassPhrase", encodedPassPhrase);
        mapFieldsRequestMock.put("externalAppUserUUID", externalAppUserUUID);
        mapFieldsRequestMock.put("urlTokenActivation", urlTokenActivation);
        mapFieldsRequestMock.put("activationCode", activationCode);
        mapFieldsRequestMock.put("dueDateActivation", dueDateActivation);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<ApplicationUser> applicationusersFromRepository = new ArrayList<>();
        applicationusersFromRepository.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        applicationusersFromRepository.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        applicationusersFromRepository.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        applicationusersFromRepository.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());

        Mockito.when(applicationuserRepositoryMock.findApplicationUserByFilter(
            id,
            idUser,
            email,
            encodedPassPhrase,
            externalAppUserUUID,
            urlTokenActivation,
            activationCode,
            dueDateActivation,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(applicationusersFromRepository);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithApplicationUserListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 10038L;
        Long idUser = 35735L;
        String email = "64UCCFtbLhKSd2aTmO3S4JDolxcHRCMdQz3V9R4O2OaSDduqU9";
        String encodedPassPhrase = "5xgTVUVbnCFxH0uJvRU1Ozeocmls5G15kdTHtoHjc510800v64";
        UUID externalAppUserUUID = UUID.fromString("b6036f77-1f49-49b0-8023-e08305388a27");
        String urlTokenActivation = "7k6Ac7uHEe0zWeI1LQRAVPtxoryLwg4tQHb6sR2bIVY36b4A2w";
        String activationCode = "FXXNFkDiEOUPvu94KRqkaDT7xa7zt0pbNF0ggLEMSM3oIpGMtc";
        String dueDateActivation = "2025-10-07";
        String status = "wEAFz59B67LYpRGsw1w0pP0mNNk7f6b40fXVkeq0iKqqm6jTWf";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("idUser", idUser);
        mapFieldsRequestMock.put("email", email);
        mapFieldsRequestMock.put("encodedPassPhrase", encodedPassPhrase);
        mapFieldsRequestMock.put("externalAppUserUUID", externalAppUserUUID);
        mapFieldsRequestMock.put("urlTokenActivation", urlTokenActivation);
        mapFieldsRequestMock.put("activationCode", activationCode);
        mapFieldsRequestMock.put("dueDateActivation", dueDateActivation);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<ApplicationUser> applicationusersFromRepository = new ArrayList<>();
        applicationusersFromRepository.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        applicationusersFromRepository.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        applicationusersFromRepository.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        applicationusersFromRepository.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());

        List<ApplicationUserDTO> applicationusersFiltered = applicationusersFromRepository
                .stream()
                .map(m->applicationuserService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageApplicationUserItems", applicationusersFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<ApplicationUser> pagedResponse =
                new PageImpl<>(applicationusersFromRepository,
                        pageableMock,
                        applicationusersFromRepository.size());

        Mockito.when(applicationuserRepositoryMock.findApplicationUserByFilter(pageableMock,
            id,
            idUser,
            email,
            encodedPassPhrase,
            externalAppUserUUID,
            urlTokenActivation,
            activationCode,
            dueDateActivation,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = applicationuserService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<ApplicationUserDTO> applicationusersResult = (List<ApplicationUserDTO>) result.get("pageApplicationUserItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, applicationusersResult.size());
    }


    @Test
    public void showReturnListOfApplicationUserWhenAskedForFindAllByStatus() {
        // scenario
        List<ApplicationUser> listOfApplicationUserModelMock = new ArrayList<>();
        listOfApplicationUserModelMock.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        listOfApplicationUserModelMock.add(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());

        Mockito.when(applicationuserRepositoryMock.findAllByStatus("A")).thenReturn(listOfApplicationUserModelMock);

        // action
        List<ApplicationUserDTO> listOfApplicationUsers = applicationuserService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfApplicationUsers.isEmpty());
        Assertions.assertEquals(2, listOfApplicationUsers.size());
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 70162L;
        Optional<ApplicationUser> applicationuserNonExistentMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.findById(idMock)).thenReturn(applicationuserNonExistentMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowApplicationUserNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 6478L;
        Mockito.when(applicationuserRepositoryMock.findById(idMock))
                .thenThrow(new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                APPLICATIONUSER_NOTFOUND_WITH_ID ));

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnApplicationUserDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 22564L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(
                ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(idMock)
                        .email("U1wRthxU6TBHPBuruv0URQUPz27e8Vj7AiNtFArmGGc0SBb6T7")
                        .encodedPassPhrase("VLnSzNwHyTYB3e4ySftkFIKYn4HVQ3lOc8aV8GX0Mn33n3DwfJ")
                        .externalAppUserUUID(UUID.fromString("36d35570-0bb5-4a2e-acda-d7f9803454e9"))
                        .urlTokenActivation("Yxiwd7YXo6zM2KBHo1ldivcFEkNNYPaB5Uz2k6WzcYh4fiYv0O")
                        .activationCode("EdxDe3F9HUbB6F5rhWdVbNaVtJoLcEQLoViA83NpdgqXF0U9Fu")
                        .dueDateActivation(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                        .status("X")
                        .now()
        );
        ApplicationUser applicationuserToSaveMock = applicationuserModelMock.orElse(null);
        ApplicationUser applicationuserSavedMck = ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(6015L)
                        .idApplication(18146L)
                        .idUser(18146L)
                        .email("EBl0u7H0vLAzHzoqVgYXTk60FCCgTfjsTUlTvhX711UT2JjPsP")
                        .encodedPassPhrase("btxbFdwHi9k9dnopkqwgf0F2hh0eqaxKbkin6WyrKktwh2x52F")
                        .externalAppUserUUID(UUID.fromString("66c130e8-8760-46fe-9e59-7a816ee84699"))
                        .urlTokenActivation("0zjVel3Yh55mOIh1R6LUDTkknSSdsXgi7O8JrCg6ys9I9C4JFQ")
                        .activationCode("yGXsu8t0cAaION5YF6Y6jphx95aieMoSrUjuDtUX62LGGfXfv8")
                        .dueDateActivation(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                        .status("A")
                        .now();
        Mockito.when(applicationuserRepositoryMock.findById(idMock)).thenReturn(applicationuserModelMock);
        Mockito.when(applicationuserRepositoryMock.save(applicationuserToSaveMock)).thenReturn(applicationuserSavedMck);

        // action
        ApplicationUserDTO result = applicationuserService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchApplicationUserByAnyNonExistenceIdAndReturnApplicationUserNotFoundException() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()-> applicationuserService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchApplicationUserByIdAndReturnDTO() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .id(23356L)
                .id(88684L)
                .email("OcLuYwFY4twExlVr0JSMFd6VuwQCjgWVcuy17R5UjDds5RAiCD")
                .encodedPassPhrase("yf66QGk0GHDux64oRSNdw7iPGgjz413C8lb4NLJ7hKYIBAEB11")
                .externalAppUserUUID(UUID.fromString("136d70e4-199a-4ff3-9647-fa4b9f29b5ca"))
                .urlTokenActivation("e6gX4rkB1sbgypb1upgffSLApCeVQsi5k8Dfcv7sk7dqptkEi3")
                .activationCode("rzmTVH8VXvXcGClEx09l8cVMeX00Wn6D5D0wThNU9vN9CDPTYa")
                .dueDateActivation(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(applicationuserRepositoryMock.findById(Mockito.anyLong())).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findById(1L);

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldDeleteApplicationUserByIdWithSucess() {
        // scenario
        Optional<ApplicationUser> applicationuser = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().id(1L).now());
        Mockito.when(applicationuserRepositoryMock.findById(Mockito.anyLong())).thenReturn(applicationuser);

        // action
        applicationuserService.delete(1L);

        // validate
        Mockito.verify(applicationuserRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceApplicationUserShouldReturnApplicationUserNotFoundException() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(
                ApplicationUserNotFoundException.class, () -> applicationuserService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingApplicationUserWithSucess() {
        // scenario
        ApplicationUserDTO applicationuserDTOMock = ApplicationUserDTOBuilder.newApplicationUserDTOTestBuilder()
                .id(8442L)
                .idUser(15480L)
                .idApplication(15480L)
                .email("fAnGKFjwpEVPuxbARY9hqnYDckFfu06zGvnooFaV3wbkkSbrWy")
                .encodedPassPhrase("XvNjq56W18NbSC0yk9qJUqhYOJPbcfFEazzyrHNCE2Y9IJflLK")
                .externalAppUserUUID(UUID.fromString("ce77ba53-c911-4b09-a60e-5a868473b02d"))
                .urlTokenActivation("RXVKrrOwIAQRcdaO7OUKESLJRXetDP6QnD3uOXv2F8VuCagqrk")
                .activationCode("lyWrIlYhhgK7GABKU4Xw9igItQ97zqX1C4ArMHxJyx1luFPaJ2")
                .dueDateActivation(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ApplicationUser applicationuserMock = ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .id(applicationuserDTOMock.getId())
                .idUser(applicationuserDTOMock.getIdUser())
                .idApplication(applicationuserDTOMock.getIdApplication())
                .email(applicationuserDTOMock.getEmail())
                .encodedPassPhrase(applicationuserDTOMock.getEncodedPassPhrase())
                .externalAppUserUUID(applicationuserDTOMock.getExternalAppUserUUID())
                .urlTokenActivation(applicationuserDTOMock.getUrlTokenActivation())
                .activationCode(applicationuserDTOMock.getActivationCode())
                .dueDateActivation(applicationuserDTOMock.getDueDateActivation())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ApplicationUser applicationuserSavedMock = ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .id(applicationuserDTOMock.getId())
                .idUser(applicationuserDTOMock.getIdUser())
                .idApplication(applicationuserDTOMock.getIdApplication())
                .email(applicationuserDTOMock.getEmail())
                .encodedPassPhrase(applicationuserDTOMock.getEncodedPassPhrase())
                .externalAppUserUUID(applicationuserDTOMock.getExternalAppUserUUID())
                .urlTokenActivation(applicationuserDTOMock.getUrlTokenActivation())
                .activationCode(applicationuserDTOMock.getActivationCode())
                .dueDateActivation(applicationuserDTOMock.getDueDateActivation())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(applicationuserRepositoryMock.save(applicationuserMock)).thenReturn(applicationuserSavedMock);

        // action
        ApplicationUserDTO applicationuserSaved = applicationuserService.salvar(applicationuserDTOMock);

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class, applicationuserSaved);
        Assertions.assertNotNull(applicationuserSaved.getId());
    }

    @Test
    public void ShouldSaveNewApplicationUserWithSucess() {
        // scenario
        ApplicationUserDTO applicationuserDTOMock = ApplicationUserDTOBuilder.newApplicationUserDTOTestBuilder()
                .id(null)
                .email("3Y400KVgP9OomFuQNvVrL7Aj1PrOwSj6LlebJ7ML13zbSg581h")
                .encodedPassPhrase("7KWg9ehy9YgBVCx4A3OFS10QYt4X6sPMRb37kosrlqwKBnaL85")
                .externalAppUserUUID(UUID.fromString("26f38a80-4f8e-493a-ad9f-0abfc59b5163"))
                .urlTokenActivation("7IoiThbupdqAiu60ycddjpGRLucGMF4ulWyYqXYwkhkPAfWYGT")
                .activationCode("lI6OBbzhNC71wAAuWeH0deEm3z9UQIMIvQc5dFjk7IIQDmvTv6")
                .dueDateActivation(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ApplicationUser applicationuserModelMock = ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .id(null)
                .email(applicationuserDTOMock.getEmail())
                .encodedPassPhrase(applicationuserDTOMock.getEncodedPassPhrase())
                .externalAppUserUUID(applicationuserDTOMock.getExternalAppUserUUID())
                .urlTokenActivation(applicationuserDTOMock.getUrlTokenActivation())
                .activationCode(applicationuserDTOMock.getActivationCode())
                .dueDateActivation(applicationuserDTOMock.getDueDateActivation())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        ApplicationUser applicationuserSavedMock = ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .id(501L)
                .email(applicationuserDTOMock.getEmail())
                .encodedPassPhrase(applicationuserDTOMock.getEncodedPassPhrase())
                .externalAppUserUUID(applicationuserDTOMock.getExternalAppUserUUID())
                .urlTokenActivation(applicationuserDTOMock.getUrlTokenActivation())
                .activationCode(applicationuserDTOMock.getActivationCode())
                .dueDateActivation(applicationuserDTOMock.getDueDateActivation())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(applicationuserRepositoryMock.save(applicationuserModelMock)).thenReturn(applicationuserSavedMock);

        // action
        ApplicationUserDTO applicationuserSaved = applicationuserService.salvar(applicationuserDTOMock);

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class, applicationuserSaved);
        Assertions.assertNotNull(applicationuserSaved.getId());
        Assertions.assertEquals("P",applicationuserSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapApplicationUserDTOMock = new HashMap<>();
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.EMAIL,"EQMliITdLKtT2zvHzd8JQsr2lemrNA6cQYScWag0Dk4yAwhMYK");
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.ENCODEDPASSPHRASE,"yatKMToxO83yjRK0r6wJPDleB0KU7GuWKam07Az8GGss4yGJr3");
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.EXTERNALAPPUSERUUID,UUID.fromString("bf3d3bd2-b3a5-4ddd-8541-b8401c11410a"));
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.URLTOKENACTIVATION,"IJdsRECkpXrCXrlGdY6H9mvWPNNHR0SjPazjsoNNw5vMN3W5aJ");
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.ACTIVATIONCODE,"tecaF8K2DOV6YhHBoWEBQb4mBDo55JeBhyWzliYUa0DYQhfRJS");
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.DUEDATEACTIVATION,Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.STATUS,"PeTqUFRv0OvOEmKphlpUNEKl08hxADb6DpfoqptffTcmFQiWdp");


        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(
                ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(36206L)
                        .id(23100L)
                        .email("XhqI86jWWducQOUhj3mGfnojzszztS21pg6yCsj8TPHa4IsgRS")
                        .encodedPassPhrase("DGSu01QeSoU8rTQhs2islRoLhM0z2lYOEC3osKquq4ircP7VzG")
                        .externalAppUserUUID(UUID.fromString("4df34803-e873-4dfe-80c9-b3223ea468ef"))
                        .urlTokenActivation("P0B0DAlmR4oEPgOUy7rykd8aBnOI9iymtDq0DRdQHJyJQOnF8Q")
                        .activationCode("pp1OD3M4XnRX203XxHItDW1JV0MN1HOsW4IggCxME8jpNO4E6s")
                        .dueDateActivation(Date.from(LocalDate.of(2027,7,25).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        .status("2rqWyvBHYk2kcqVz6TlCRDXCCNkpeARwVsY3iqbNff2hNjoAwB")

                        .now()
        );

        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        boolean executed = applicationuserService.partialUpdate(1L, mapApplicationUserDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapApplicationUserDTOMock = new HashMap<>();
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.EMAIL,"qdHJCdg4VjbaGgsC8gSWLGe7OpyEtpOHdJ9K4FicpQBO48Vrlz");
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.ENCODEDPASSPHRASE,"0n1XMNQ7tnCc0kALedmRgQJEKE6JgMYOGgprO202CjDzVyqzNJ");
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.EXTERNALAPPUSERUUID,UUID.fromString("a78e7344-8117-49b9-a369-8d89c5364a4d"));
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.URLTOKENACTIVATION,"4MK4Qb3Q5XA7A8PTT9IB0qHY1CScNv8Cg9O8430O9GlHr5FyJG");
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.ACTIVATIONCODE,"jweY4WJR02SDR0smFqsLdW7ddfQsSYDwtHD2MaH6JdCNpWl5Op");
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.DUEDATEACTIVATION,Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        mapApplicationUserDTOMock.put(ApplicationUserConstantes.STATUS,"tOzBSNd2GSTg6DVf3teVj26J8WJmfiBr8G8vXKjnb9LiBXsoY7");


        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.partialUpdate(1L, mapApplicationUserDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("ApplicationUser não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByIdAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByIdAndStatus(70371L, "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByIdAndStatus(70371L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByIdUserAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByIdAndStatus(38635L, "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByIdAndStatus(38635L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByEmailAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByEmailAndStatus("AH9kw0ryxVxx6qKH1aXpW5c5iek252X8BFNVoKp38fifQzcLC4", "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByEmailAndStatus("AH9kw0ryxVxx6qKH1aXpW5c5iek252X8BFNVoKp38fifQzcLC4", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByEncodedPassPhraseAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByEncodedPassPhraseAndStatus("SUTTK0RG0IEcl3Nsi2Gts2V2UHslPYmtIUM8pLf59cftKkYxA5", "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByEncodedPassPhraseAndStatus("SUTTK0RG0IEcl3Nsi2Gts2V2UHslPYmtIUM8pLf59cftKkYxA5", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByExternalAppUserUUIDAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByExternalAppUserUUIDAndStatus(UUID.fromString("0c6718ed-b850-4fa3-947b-0436e48f4df8"), "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByExternalAppUserUUIDAndStatus(UUID.fromString("0c6718ed-b850-4fa3-947b-0436e48f4df8"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByUrlTokenActivationAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByUrlTokenActivationAndStatus("TGuHgx80P8XxhSMtCOxEFHWmrWpuJ5jsxs573U6dUiIMy8wqDd", "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByUrlTokenActivationAndStatus("TGuHgx80P8XxhSMtCOxEFHWmrWpuJ5jsxs573U6dUiIMy8wqDd", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByActivationCodeAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByActivationCodeAndStatus("kOpCiU1j4IMz3uuMz9f5aapAKXY8rRTTa8J3aIn80Anuqm8YHu", "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByActivationCodeAndStatus("kOpCiU1j4IMz3uuMz9f5aapAKXY8rRTTa8J3aIn80Anuqm8YHu", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByDueDateActivationAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByDueDateActivationAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByDueDateActivationAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByDateCreatedAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnApplicationUserListWhenFindAllApplicationUserByDateUpdatedAndStatus() {
        // scenario
        List<ApplicationUser> applicationusers = Arrays.asList(
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now(),
            ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now()
        );

        Mockito.when(applicationuserRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(applicationusers);

        // action
        List<ApplicationUserDTO> result = applicationuserService.findAllApplicationUserByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentApplicationUserDTOWhenFindApplicationUserByIdAndStatus() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByIdAndStatus(31632L, "A")).thenReturn(1L);
        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByIdAndStatus(31632L, "A");

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenNonExistenceApplicationUserIdUserAndStatus() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByIdAndStatus(31632L, "A")).thenReturn(0L);
        Mockito.when(applicationuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByIdAndStatus(31632L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentApplicationUserDTOWhenFindApplicationUserByIdUserAndStatus() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByIdUserAndStatus(20052L, "A")).thenReturn(1L);
        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByIdUserAndStatus(20052L, "A");

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenNonExistenceApplicationUserIdAndStatus() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByIdAndStatus(20052L, "A")).thenReturn(0L);
        Mockito.when(applicationuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByIdAndStatus(20052L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentApplicationUserDTOWhenFindApplicationUserByEmailAndStatus() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByEmailAndStatus("Cr6HHrD5sXQjyS8GpB2DDutQxH69NudU5Otq8FGLiXhvSJ7Hps", "A")).thenReturn(1L);
        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByEmailAndStatus("Cr6HHrD5sXQjyS8GpB2DDutQxH69NudU5Otq8FGLiXhvSJ7Hps", "A");

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenNonExistenceApplicationUserEmailAndStatus() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByEmailAndStatus("Cr6HHrD5sXQjyS8GpB2DDutQxH69NudU5Otq8FGLiXhvSJ7Hps", "A")).thenReturn(0L);
        Mockito.when(applicationuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByEmailAndStatus("Cr6HHrD5sXQjyS8GpB2DDutQxH69NudU5Otq8FGLiXhvSJ7Hps", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_EMAIL));
    }
    @Test
    public void shouldReturnExistentApplicationUserDTOWhenFindApplicationUserByEncodedPassPhraseAndStatus() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus("1VaPGAHCioBViBIOEkfbKfnK0kwwKpuXQyNP6Csyx4MWadb5aS", "A")).thenReturn(1L);
        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByEncodedPassPhraseAndStatus("1VaPGAHCioBViBIOEkfbKfnK0kwwKpuXQyNP6Csyx4MWadb5aS", "A");

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenNonExistenceApplicationUserEncodedPassPhraseAndStatus() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus("1VaPGAHCioBViBIOEkfbKfnK0kwwKpuXQyNP6Csyx4MWadb5aS", "A")).thenReturn(0L);
        Mockito.when(applicationuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByEncodedPassPhraseAndStatus("1VaPGAHCioBViBIOEkfbKfnK0kwwKpuXQyNP6Csyx4MWadb5aS", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ENCODEDPASSPHRASE));
    }
    @Test
    public void shouldReturnExistentApplicationUserDTOWhenFindApplicationUserByExternalAppUserUUIDAndStatus() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByExternalAppUserUUIDAndStatus(UUID.fromString("29f32cf2-4a3f-480e-9f38-28c1c27a43a5"), "A")).thenReturn(1L);
        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByExternalAppUserUUIDAndStatus(UUID.fromString("29f32cf2-4a3f-480e-9f38-28c1c27a43a5"), "A");

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenNonExistenceApplicationUserExternalAppUserUUIDAndStatus() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByExternalAppUserUUIDAndStatus(UUID.fromString("29f32cf2-4a3f-480e-9f38-28c1c27a43a5"), "A")).thenReturn(0L);
        Mockito.when(applicationuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByExternalAppUserUUIDAndStatus(UUID.fromString("29f32cf2-4a3f-480e-9f38-28c1c27a43a5"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_EXTERNALAPPUSERUUID));
    }
    @Test
    public void shouldReturnExistentApplicationUserDTOWhenFindApplicationUserByUrlTokenActivationAndStatus() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByUrlTokenActivationAndStatus("X1dmbR3J04lzmEC0gBIwgdmGojqpVPbw0c1uy2FX88qsJwAqw9", "A")).thenReturn(1L);
        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByUrlTokenActivationAndStatus("X1dmbR3J04lzmEC0gBIwgdmGojqpVPbw0c1uy2FX88qsJwAqw9", "A");

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenNonExistenceApplicationUserUrlTokenActivationAndStatus() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByUrlTokenActivationAndStatus("X1dmbR3J04lzmEC0gBIwgdmGojqpVPbw0c1uy2FX88qsJwAqw9", "A")).thenReturn(0L);
        Mockito.when(applicationuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByUrlTokenActivationAndStatus("X1dmbR3J04lzmEC0gBIwgdmGojqpVPbw0c1uy2FX88qsJwAqw9", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_URLTOKENACTIVATION));
    }
    @Test
    public void shouldReturnExistentApplicationUserDTOWhenFindApplicationUserByActivationCodeAndStatus() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByActivationCodeAndStatus("laX6C3nuT7y2VlDHiD1s3la0oFJRJ18VoXxEkmbId3Ig8JpB1w", "A")).thenReturn(1L);
        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByActivationCodeAndStatus("laX6C3nuT7y2VlDHiD1s3la0oFJRJ18VoXxEkmbId3Ig8JpB1w", "A");

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenNonExistenceApplicationUserActivationCodeAndStatus() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByActivationCodeAndStatus("laX6C3nuT7y2VlDHiD1s3la0oFJRJ18VoXxEkmbId3Ig8JpB1w", "A")).thenReturn(0L);
        Mockito.when(applicationuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByActivationCodeAndStatus("laX6C3nuT7y2VlDHiD1s3la0oFJRJ18VoXxEkmbId3Ig8JpB1w", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ACTIVATIONCODE));
    }
    @Test
    public void shouldReturnExistentApplicationUserDTOWhenFindApplicationUserByDueDateActivationAndStatus() {
        // scenario
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder().now());
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByDueDateActivationAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(1L);
        Mockito.when(applicationuserRepositoryMock.findById(1L)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByDueDateActivationAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(ApplicationUserDTO.class,result);
    }
    @Test
    public void shouldReturnApplicationUserNotFoundExceptionWhenNonExistenceApplicationUserDueDateActivationAndStatus() {
        // scenario
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByDueDateActivationAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(0L);
        Mockito.when(applicationuserRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByDueDateActivationAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_DUEDATEACTIVATION));
    }

    @Test
    public void shouldReturnApplicationUserDTOWhenUpdateExistingEmailById() {
        // scenario
        String emailUpdateMock = "HK0AF0BRuhIAyXBP8X4U0CPGcGa7MXfiE0guTWhUjv4cEhdSVN";
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(applicationuserRepositoryMock.findById(420L)).thenReturn(applicationuserModelMock);
        Mockito.doNothing().when(applicationuserRepositoryMock).updateEmailById(420L, emailUpdateMock);

        // action
        applicationuserService.updateEmailById(420L, emailUpdateMock);

        // validate
        Mockito.verify(applicationuserRepositoryMock,Mockito.times(1)).updateEmailById(420L, emailUpdateMock);
    }
    @Test
    public void shouldReturnApplicationUserDTOWhenUpdateExistingEncodedPassPhraseById() {
        // scenario
        String encodedPassPhraseUpdateMock = "4jkalHTOXiAmDPvjLwVKE8vo26BjrC4qEVkeCxfD6cR967kKl6";
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(applicationuserRepositoryMock.findById(420L)).thenReturn(applicationuserModelMock);
        Mockito.doNothing().when(applicationuserRepositoryMock).updateEncodedPassPhraseById(420L, encodedPassPhraseUpdateMock);

        // action
        applicationuserService.updateEncodedPassPhraseById(420L, encodedPassPhraseUpdateMock);

        // validate
        Mockito.verify(applicationuserRepositoryMock,Mockito.times(1)).updateEncodedPassPhraseById(420L, encodedPassPhraseUpdateMock);
    }
    @Test
    public void shouldReturnApplicationUserDTOWhenUpdateExistingExternalAppUserUUIDById() {
        // scenario
        UUID externalAppUserUUIDUpdateMock = UUID.fromString("ee24d041-4c78-47c3-8982-b7d547d0ef23");
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(applicationuserRepositoryMock.findById(420L)).thenReturn(applicationuserModelMock);
        Mockito.doNothing().when(applicationuserRepositoryMock).updateExternalAppUserUUIDById(420L, externalAppUserUUIDUpdateMock);

        // action
        applicationuserService.updateExternalAppUserUUIDById(420L, externalAppUserUUIDUpdateMock);

        // validate
        Mockito.verify(applicationuserRepositoryMock,Mockito.times(1)).updateExternalAppUserUUIDById(420L, externalAppUserUUIDUpdateMock);
    }
    @Test
    public void shouldReturnApplicationUserDTOWhenUpdateExistingUrlTokenActivationById() {
        // scenario
        String urlTokenActivationUpdateMock = "0thfYyGvA0yRrS3py8xWwpVhU0csKWvLH2Ci5qFhYwy3v0U4Ge";
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(applicationuserRepositoryMock.findById(420L)).thenReturn(applicationuserModelMock);
        Mockito.doNothing().when(applicationuserRepositoryMock).updateUrlTokenActivationById(420L, urlTokenActivationUpdateMock);

        // action
        applicationuserService.updateUrlTokenActivationById(420L, urlTokenActivationUpdateMock);

        // validate
        Mockito.verify(applicationuserRepositoryMock,Mockito.times(1)).updateUrlTokenActivationById(420L, urlTokenActivationUpdateMock);
    }
    @Test
    public void shouldReturnApplicationUserDTOWhenUpdateExistingActivationCodeById() {
        // scenario
        String activationCodeUpdateMock = "AWA8VgJs9sqGPEFHKuT8A6fgclvJtClmzEJLQ2uh0JwXcez50x";
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(applicationuserRepositoryMock.findById(420L)).thenReturn(applicationuserModelMock);
        Mockito.doNothing().when(applicationuserRepositoryMock).updateActivationCodeById(420L, activationCodeUpdateMock);

        // action
        applicationuserService.updateActivationCodeById(420L, activationCodeUpdateMock);

        // validate
        Mockito.verify(applicationuserRepositoryMock,Mockito.times(1)).updateActivationCodeById(420L, activationCodeUpdateMock);
    }
    @Test
    public void shouldReturnApplicationUserDTOWhenUpdateExistingDueDateActivationById() {
        // scenario
        Date dueDateActivationUpdateMock = Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(applicationuserRepositoryMock.findById(420L)).thenReturn(applicationuserModelMock);
        Mockito.doNothing().when(applicationuserRepositoryMock).updateDueDateActivationById(420L, dueDateActivationUpdateMock);

        // action
        applicationuserService.updateDueDateActivationById(420L, dueDateActivationUpdateMock);

        // validate
        Mockito.verify(applicationuserRepositoryMock,Mockito.times(1)).updateDueDateActivationById(420L, dueDateActivationUpdateMock);
    }



    @Test
    public void showReturnExistingApplicationUserDTOWhenFindApplicationUserByIdUserAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 62680L;
        Long maxIdMock = 1972L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(maxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnApplicationUserNotFoundExceptionWhenNonExistenceFindApplicationUserByIdUserAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 62680L;
        Long noMaxIdMock = 0L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(noMaxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingApplicationUserDTOWhenFindApplicationUserByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 77702L;
        Long maxIdMock = 1972L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(maxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnApplicationUserNotFoundExceptionWhenNonExistenceFindApplicationUserByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 77702L;
        Long noMaxIdMock = 0L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(noMaxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingApplicationUserDTOWhenFindApplicationUserByEmailAndStatusActiveAnonimous() {
        // scenario
        String emailMock = "xITTt85ESImWhGpJumPdGfhIhIQDfD6d0rYJjQjst3r1kkJciW";
        Long maxIdMock = 1972L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .email(emailMock)
                .now()
        );
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByEmailAndStatus(emailMock, "A")).thenReturn(maxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(maxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByEmailAndStatus(emailMock);

        // validate
        Assertions.assertEquals(emailMock, result.getEmail());

    }
    @Test
    public void showReturnApplicationUserNotFoundExceptionWhenNonExistenceFindApplicationUserByEmailAndStatusActiveAnonimous() {
        // scenario
        String emailMock = "xITTt85ESImWhGpJumPdGfhIhIQDfD6d0rYJjQjst3r1kkJciW";
        Long noMaxIdMock = 0L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByEmailAndStatus(emailMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(noMaxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByEmailAndStatus(emailMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_EMAIL));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingApplicationUserDTOWhenFindApplicationUserByEncodedPassPhraseAndStatusActiveAnonimous() {
        // scenario
        String encodedPassPhraseMock = "0rAzG4GB8vs1kQ0eGxXfaxBaG0MLWFhpEOL39lCbOfkfxF2aVb";
        Long maxIdMock = 1972L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .encodedPassPhrase(encodedPassPhraseMock)
                .now()
        );
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus(encodedPassPhraseMock, "A")).thenReturn(maxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(maxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByEncodedPassPhraseAndStatus(encodedPassPhraseMock);

        // validate
        Assertions.assertEquals(encodedPassPhraseMock, result.getEncodedPassPhrase());

    }
    @Test
    public void showReturnApplicationUserNotFoundExceptionWhenNonExistenceFindApplicationUserByEncodedPassPhraseAndStatusActiveAnonimous() {
        // scenario
        String encodedPassPhraseMock = "0rAzG4GB8vs1kQ0eGxXfaxBaG0MLWFhpEOL39lCbOfkfxF2aVb";
        Long noMaxIdMock = 0L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByEncodedPassPhraseAndStatus(encodedPassPhraseMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(noMaxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByEncodedPassPhraseAndStatus(encodedPassPhraseMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ENCODEDPASSPHRASE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingApplicationUserDTOWhenFindApplicationUserByExternalAppUserUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID externalAppUserUUIDMock = UUID.fromString("a656c138-9224-4a91-bc28-9dc5d303d27a");
        Long maxIdMock = 1972L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .externalAppUserUUID(externalAppUserUUIDMock)
                .now()
        );
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByExternalAppUserUUIDAndStatus(externalAppUserUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(maxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByExternalAppUserUUIDAndStatus(externalAppUserUUIDMock);

        // validate
        Assertions.assertEquals(externalAppUserUUIDMock, result.getExternalAppUserUUID());

    }
    @Test
    public void showReturnApplicationUserNotFoundExceptionWhenNonExistenceFindApplicationUserByExternalAppUserUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID externalAppUserUUIDMock = UUID.fromString("a656c138-9224-4a91-bc28-9dc5d303d27a");
        Long noMaxIdMock = 0L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByExternalAppUserUUIDAndStatus(externalAppUserUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(noMaxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByExternalAppUserUUIDAndStatus(externalAppUserUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_EXTERNALAPPUSERUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingApplicationUserDTOWhenFindApplicationUserByUrlTokenActivationAndStatusActiveAnonimous() {
        // scenario
        String urlTokenActivationMock = "X52yYUgcoSLd6dQKcN8h1qXq5sqq7hgujemkcChgkl0explwkv";
        Long maxIdMock = 1972L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .urlTokenActivation(urlTokenActivationMock)
                .now()
        );
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByUrlTokenActivationAndStatus(urlTokenActivationMock, "A")).thenReturn(maxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(maxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByUrlTokenActivationAndStatus(urlTokenActivationMock);

        // validate
        Assertions.assertEquals(urlTokenActivationMock, result.getUrlTokenActivation());

    }
    @Test
    public void showReturnApplicationUserNotFoundExceptionWhenNonExistenceFindApplicationUserByUrlTokenActivationAndStatusActiveAnonimous() {
        // scenario
        String urlTokenActivationMock = "X52yYUgcoSLd6dQKcN8h1qXq5sqq7hgujemkcChgkl0explwkv";
        Long noMaxIdMock = 0L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByUrlTokenActivationAndStatus(urlTokenActivationMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(noMaxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByUrlTokenActivationAndStatus(urlTokenActivationMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_URLTOKENACTIVATION));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingApplicationUserDTOWhenFindApplicationUserByActivationCodeAndStatusActiveAnonimous() {
        // scenario
        String activationCodeMock = "0rgX3YbarsxssDsVbT8wGdCk3yQipCfizP3GXUvbdYBHuH91OI";
        Long maxIdMock = 1972L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .activationCode(activationCodeMock)
                .now()
        );
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByActivationCodeAndStatus(activationCodeMock, "A")).thenReturn(maxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(maxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByActivationCodeAndStatus(activationCodeMock);

        // validate
        Assertions.assertEquals(activationCodeMock, result.getActivationCode());

    }
    @Test
    public void showReturnApplicationUserNotFoundExceptionWhenNonExistenceFindApplicationUserByActivationCodeAndStatusActiveAnonimous() {
        // scenario
        String activationCodeMock = "0rgX3YbarsxssDsVbT8wGdCk3yQipCfizP3GXUvbdYBHuH91OI";
        Long noMaxIdMock = 0L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByActivationCodeAndStatus(activationCodeMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(noMaxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByActivationCodeAndStatus(activationCodeMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_ACTIVATIONCODE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingApplicationUserDTOWhenFindApplicationUserByDueDateActivationAndStatusActiveAnonimous() {
        // scenario
        Date dueDateActivationMock = Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Long maxIdMock = 1972L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.ofNullable(ApplicationUserModelBuilder.newApplicationUserModelTestBuilder()
                .dueDateActivation(dueDateActivationMock)
                .now()
        );
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByDueDateActivationAndStatus(dueDateActivationMock, "A")).thenReturn(maxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(maxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserDTO result = applicationuserService.findApplicationUserByDueDateActivationAndStatus(dueDateActivationMock);

        // validate
        Assertions.assertEquals(dueDateActivationMock, result.getDueDateActivation());

    }
    @Test
    public void showReturnApplicationUserNotFoundExceptionWhenNonExistenceFindApplicationUserByDueDateActivationAndStatusActiveAnonimous() {
        // scenario
        Date dueDateActivationMock = Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Long noMaxIdMock = 0L;
        Optional<ApplicationUser> applicationuserModelMock = Optional.empty();
        Mockito.when(applicationuserRepositoryMock.loadMaxIdByDueDateActivationAndStatus(dueDateActivationMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(applicationuserRepositoryMock.findById(noMaxIdMock)).thenReturn(applicationuserModelMock);

        // action
        ApplicationUserNotFoundException exception = Assertions.assertThrows(ApplicationUserNotFoundException.class,
                ()->applicationuserService.findApplicationUserByDueDateActivationAndStatus(dueDateActivationMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(APPLICATIONUSER_NOTFOUND_WITH_DUEDATEACTIVATION));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

