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
import br.com.jcv.security.guardian.builder.GApplicationDTOBuilder;
import br.com.jcv.security.guardian.builder.GApplicationModelBuilder;
import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.exception.GApplicationNotFoundException;
import br.com.jcv.security.guardian.model.GApplication;
import br.com.jcv.security.guardian.repository.GApplicationRepository;
import br.com.jcv.security.guardian.service.GApplicationService;
import br.com.jcv.security.guardian.constantes.GApplicationConstantes;
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
public class GApplicationServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String GAPPLICATION_NOTFOUND_WITH_ID = "GApplication não encontrada com id = ";
    public static final String GAPPLICATION_NOTFOUND_WITH_NAME = "GApplication não encontrada com name = ";
    public static final String GAPPLICATION_NOTFOUND_WITH_EXTERNALCODEUUID = "GApplication não encontrada com externalCodeUUID = ";
    public static final String GAPPLICATION_NOTFOUND_WITH_STATUS = "GApplication não encontrada com status = ";
    public static final String GAPPLICATION_NOTFOUND_WITH_DATECREATED = "GApplication não encontrada com dateCreated = ";
    public static final String GAPPLICATION_NOTFOUND_WITH_DATEUPDATED = "GApplication não encontrada com dateUpdated = ";


    @Mock
    private GApplicationRepository gapplicationRepositoryMock;

    @InjectMocks
    private GApplicationService gapplicationService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        gapplicationService = new GApplicationServiceImpl();
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
    public void shouldReturnListOfGApplicationWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 58758L;
        String name = "ENq9wzh551kExfX8Ua9V4zDX81rx1QUD3F8pti0m7XqeLvxG0K";
        UUID externalCodeUUID = UUID.fromString("aefc63a1-307e-42af-b512-c792e3b45d13");
        String status = "zL4YB3qGBerROqy0Vvd12eG91QwVv5JL8mUBCMfHsMy3UAjLXY";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("externalCodeUUID", externalCodeUUID);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<GApplication> gapplicationsFromRepository = new ArrayList<>();
        gapplicationsFromRepository.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        gapplicationsFromRepository.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        gapplicationsFromRepository.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        gapplicationsFromRepository.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());

        Mockito.when(gapplicationRepositoryMock.findGApplicationByFilter(
            id,
            name,
            externalCodeUUID,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(gapplicationsFromRepository);

        // action
        List<GApplicationDTO> result = gapplicationService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithGApplicationListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 53062L;
        String name = "ehoDvia8lVEWLRpnkYLGd4lvcwGkfRnG8rAkyzjInbYvIO2nmH";
        UUID externalCodeUUID = UUID.fromString("c8095698-5baf-4611-86fb-b494828f874d");
        String status = "lO0JwqP4M84IdrAmemEAkq5uNCqGJk2RLKNilHjPzOJ9cPW48f";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("externalCodeUUID", externalCodeUUID);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<GApplication> gapplicationsFromRepository = new ArrayList<>();
        gapplicationsFromRepository.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        gapplicationsFromRepository.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        gapplicationsFromRepository.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        gapplicationsFromRepository.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());

        List<GApplicationDTO> gapplicationsFiltered = gapplicationsFromRepository
                .stream()
                .map(m->gapplicationService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageGApplicationItems", gapplicationsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<GApplication> pagedResponse =
                new PageImpl<>(gapplicationsFromRepository,
                        pageableMock,
                        gapplicationsFromRepository.size());

        Mockito.when(gapplicationRepositoryMock.findGApplicationByFilter(pageableMock,
            id,
            name,
            externalCodeUUID,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = gapplicationService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<GApplicationDTO> gapplicationsResult = (List<GApplicationDTO>) result.get("pageGApplicationItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, gapplicationsResult.size());
    }


    @Test
    public void showReturnListOfGApplicationWhenAskedForFindAllByStatus() {
        // scenario
        List<GApplication> listOfGApplicationModelMock = new ArrayList<>();
        listOfGApplicationModelMock.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        listOfGApplicationModelMock.add(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());

        Mockito.when(gapplicationRepositoryMock.findAllByStatus("A")).thenReturn(listOfGApplicationModelMock);

        // action
        List<GApplicationDTO> listOfGApplications = gapplicationService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfGApplications.isEmpty());
        Assertions.assertEquals(2, listOfGApplications.size());
    }
    @Test
    public void shouldReturnGApplicationNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 36053L;
        Optional<GApplication> gapplicationNonExistentMock = Optional.empty();
        Mockito.when(gapplicationRepositoryMock.findById(idMock)).thenReturn(gapplicationNonExistentMock);

        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowGApplicationNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 35347L;
        Mockito.when(gapplicationRepositoryMock.findById(idMock))
                .thenThrow(new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                GAPPLICATION_NOTFOUND_WITH_ID ));

        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnGApplicationDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 83L;
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(
                GApplicationModelBuilder.newGApplicationModelTestBuilder()
                        .id(idMock)
                        .name("PtHxLKOB7RzyrGYQKLPLXM4SfIVTsvUdk1OS0bsKfYsD79rRKr")
                        .externalCodeUUID(UUID.fromString("64d84f48-0ab5-4f0a-bd9a-fd7365571997"))

                        .status("X")
                        .now()
        );
        GApplication gapplicationToSaveMock = gapplicationModelMock.orElse(null);
        GApplication gapplicationSavedMck = GApplicationModelBuilder.newGApplicationModelTestBuilder()
                        .id(77106L)
                        .name("NB4afaiye450BMaJUr23ICc2ePEju0lFBFlPXKNQhFoNmnnexz")
                        .externalCodeUUID(UUID.fromString("5e82da0f-17e3-4e8f-9418-6e79814482e4"))

                        .status("A")
                        .now();
        Mockito.when(gapplicationRepositoryMock.findById(idMock)).thenReturn(gapplicationModelMock);
        Mockito.when(gapplicationRepositoryMock.save(gapplicationToSaveMock)).thenReturn(gapplicationSavedMck);

        // action
        GApplicationDTO result = gapplicationService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchGApplicationByAnyNonExistenceIdAndReturnGApplicationNotFoundException() {
        // scenario
        Mockito.when(gapplicationRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()-> gapplicationService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchGApplicationByIdAndReturnDTO() {
        // scenario
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder()
                .id(74102L)
                .name("GKeUtf7QblPJu5HBBwCL5JxMrHgGx0tcrRaARG4cn60e020JCg")
                .externalCodeUUID(UUID.fromString("21688a15-4d28-4824-a8ae-0ed075a086f2"))

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(gapplicationRepositoryMock.findById(Mockito.anyLong())).thenReturn(gapplicationModelMock);

        // action
        GApplicationDTO result = gapplicationService.findById(1L);

        // validate
        Assertions.assertInstanceOf(GApplicationDTO.class,result);
    }
    @Test
    public void shouldDeleteGApplicationByIdWithSucess() {
        // scenario
        Optional<GApplication> gapplication = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder().id(1L).now());
        Mockito.when(gapplicationRepositoryMock.findById(Mockito.anyLong())).thenReturn(gapplication);

        // action
        gapplicationService.delete(1L);

        // validate
        Mockito.verify(gapplicationRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceGApplicationShouldReturnGApplicationNotFoundException() {
        // scenario
        Mockito.when(gapplicationRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(
                GApplicationNotFoundException.class, () -> gapplicationService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingGApplicationWithSucess() {
        // scenario
        GApplicationDTO gapplicationDTOMock = GApplicationDTOBuilder.newGApplicationDTOTestBuilder()
                .id(82520L)
                .name("0NxYv8TP6R38IxjkOJUyT2U1eGJvu1Ag83Nl1agKNKDpRBdCJG")
                .externalCodeUUID(UUID.fromString("65197d9e-aa0a-4e2a-9be2-8b3dd8547264"))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GApplication gapplicationMock = GApplicationModelBuilder.newGApplicationModelTestBuilder()
                .id(gapplicationDTOMock.getId())
                .name(gapplicationDTOMock.getName())
                .externalCodeUUID(gapplicationDTOMock.getExternalCodeUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GApplication gapplicationSavedMock = GApplicationModelBuilder.newGApplicationModelTestBuilder()
                .id(gapplicationDTOMock.getId())
                .name(gapplicationDTOMock.getName())
                .externalCodeUUID(gapplicationDTOMock.getExternalCodeUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(gapplicationRepositoryMock.save(gapplicationMock)).thenReturn(gapplicationSavedMock);

        // action
        GApplicationDTO gapplicationSaved = gapplicationService.salvar(gapplicationDTOMock);

        // validate
        Assertions.assertInstanceOf(GApplicationDTO.class, gapplicationSaved);
        Assertions.assertNotNull(gapplicationSaved.getId());
    }

    @Test
    public void ShouldSaveNewGApplicationWithSucess() {
        // scenario
        GApplicationDTO gapplicationDTOMock = GApplicationDTOBuilder.newGApplicationDTOTestBuilder()
                .id(null)
                .name("7cNiYIXUvnyBwzGe0SN3DLK9CjY940fqpvTjj3R8gC54wu8ky4")
                .externalCodeUUID(UUID.fromString("4df0992d-006b-4cc8-a8e0-d18948459c4e"))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GApplication gapplicationModelMock = GApplicationModelBuilder.newGApplicationModelTestBuilder()
                .id(null)
                .name(gapplicationDTOMock.getName())
                .externalCodeUUID(gapplicationDTOMock.getExternalCodeUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        GApplication gapplicationSavedMock = GApplicationModelBuilder.newGApplicationModelTestBuilder()
                .id(501L)
                .name(gapplicationDTOMock.getName())
                .externalCodeUUID(gapplicationDTOMock.getExternalCodeUUID())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(gapplicationRepositoryMock.save(gapplicationModelMock)).thenReturn(gapplicationSavedMock);

        // action
        GApplicationDTO gapplicationSaved = gapplicationService.salvar(gapplicationDTOMock);

        // validate
        Assertions.assertInstanceOf(GApplicationDTO.class, gapplicationSaved);
        Assertions.assertNotNull(gapplicationSaved.getId());
        Assertions.assertEquals("P",gapplicationSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapGApplicationDTOMock = new HashMap<>();
        mapGApplicationDTOMock.put(GApplicationConstantes.NAME,"hjq6PCaR081imgNeTYOXReljWkRgujEUbd5Pkbc1P35p5MNf7U");
        mapGApplicationDTOMock.put(GApplicationConstantes.EXTERNALCODEUUID,UUID.fromString("836e6423-d9c4-469a-bbca-ef7c6d041f0c"));
        mapGApplicationDTOMock.put(GApplicationConstantes.STATUS,"oEpaH0drhRyGQc0QrCMT879IVJjYPwnEf93eNQliPtq0r9IRI9");


        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(
                GApplicationModelBuilder.newGApplicationModelTestBuilder()
                        .id(44348L)
                        .name("JEdIuPBqF7ngtipT2XS7XJdcEjFIHQj1Mr6dEi7GdYEDJlOMOt")
                        .externalCodeUUID(UUID.fromString("a7ea4f1d-beb7-44ff-9cfb-fd90552c5048"))
                        .status("Uqwqyl52E6Y54K1YVHkN0y3jbR01Xne4RFAtc18WqLWKIkgBEy")

                        .now()
        );

        Mockito.when(gapplicationRepositoryMock.findById(1L)).thenReturn(gapplicationModelMock);

        // action
        boolean executed = gapplicationService.partialUpdate(1L, mapGApplicationDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnGApplicationNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapGApplicationDTOMock = new HashMap<>();
        mapGApplicationDTOMock.put(GApplicationConstantes.NAME,"RDxJc8MbHujyI7yATfA7kVKFB2JGKI04MOGdxWqUtsUCFsdJmh");
        mapGApplicationDTOMock.put(GApplicationConstantes.EXTERNALCODEUUID,UUID.fromString("967d1886-d8e2-4382-ad3b-27c9b5ba2215"));
        mapGApplicationDTOMock.put(GApplicationConstantes.STATUS,"asicraNHMFIpRl05rmcOzb0BoRBrqCbpKw0VjAQKucgzLgIG1M");


        Mockito.when(gapplicationRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.partialUpdate(1L, mapGApplicationDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("GApplication não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnGApplicationListWhenFindAllGApplicationByIdAndStatus() {
        // scenario
        List<GApplication> gapplications = Arrays.asList(
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now()
        );

        Mockito.when(gapplicationRepositoryMock.findAllByIdAndStatus(42611L, "A")).thenReturn(gapplications);

        // action
        List<GApplicationDTO> result = gapplicationService.findAllGApplicationByIdAndStatus(42611L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGApplicationListWhenFindAllGApplicationByNameAndStatus() {
        // scenario
        List<GApplication> gapplications = Arrays.asList(
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now()
        );

        Mockito.when(gapplicationRepositoryMock.findAllByNameAndStatus("BxsAeMdFisQm8rh3Nd1V6ntCHeSU6NYGP8ehonuOYBliQfrUot", "A")).thenReturn(gapplications);

        // action
        List<GApplicationDTO> result = gapplicationService.findAllGApplicationByNameAndStatus("BxsAeMdFisQm8rh3Nd1V6ntCHeSU6NYGP8ehonuOYBliQfrUot", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGApplicationListWhenFindAllGApplicationByExternalCodeUUIDAndStatus() {
        // scenario
        List<GApplication> gapplications = Arrays.asList(
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now()
        );

        Mockito.when(gapplicationRepositoryMock.findAllByExternalCodeUUIDAndStatus(UUID.fromString("bccae604-9269-4a0d-a08f-07bdfb25af8c"), "A")).thenReturn(gapplications);

        // action
        List<GApplicationDTO> result = gapplicationService.findAllGApplicationByExternalCodeUUIDAndStatus(UUID.fromString("bccae604-9269-4a0d-a08f-07bdfb25af8c"), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGApplicationListWhenFindAllGApplicationByDateCreatedAndStatus() {
        // scenario
        List<GApplication> gapplications = Arrays.asList(
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now()
        );

        Mockito.when(gapplicationRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(gapplications);

        // action
        List<GApplicationDTO> result = gapplicationService.findAllGApplicationByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnGApplicationListWhenFindAllGApplicationByDateUpdatedAndStatus() {
        // scenario
        List<GApplication> gapplications = Arrays.asList(
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now(),
            GApplicationModelBuilder.newGApplicationModelTestBuilder().now()
        );

        Mockito.when(gapplicationRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(gapplications);

        // action
        List<GApplicationDTO> result = gapplicationService.findAllGApplicationByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentGApplicationDTOWhenFindGApplicationByIdAndStatus() {
        // scenario
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByIdAndStatus(64104L, "A")).thenReturn(1L);
        Mockito.when(gapplicationRepositoryMock.findById(1L)).thenReturn(gapplicationModelMock);

        // action
        GApplicationDTO result = gapplicationService.findGApplicationByIdAndStatus(64104L, "A");

        // validate
        Assertions.assertInstanceOf(GApplicationDTO.class,result);
    }
    @Test
    public void shouldReturnGApplicationNotFoundExceptionWhenNonExistenceGApplicationIdAndStatus() {
        // scenario
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByIdAndStatus(64104L, "A")).thenReturn(0L);
        Mockito.when(gapplicationRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.findGApplicationByIdAndStatus(64104L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentGApplicationDTOWhenFindGApplicationByNameAndStatus() {
        // scenario
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByNameAndStatus("pmSTEa0K0svQ6obJVpabjnM6NuVTfJ5eg0MhkP5LWKTvHrXnYG", "A")).thenReturn(1L);
        Mockito.when(gapplicationRepositoryMock.findById(1L)).thenReturn(gapplicationModelMock);

        // action
        GApplicationDTO result = gapplicationService.findGApplicationByNameAndStatus("pmSTEa0K0svQ6obJVpabjnM6NuVTfJ5eg0MhkP5LWKTvHrXnYG", "A");

        // validate
        Assertions.assertInstanceOf(GApplicationDTO.class,result);
    }
    @Test
    public void shouldReturnGApplicationNotFoundExceptionWhenNonExistenceGApplicationNameAndStatus() {
        // scenario
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByNameAndStatus("pmSTEa0K0svQ6obJVpabjnM6NuVTfJ5eg0MhkP5LWKTvHrXnYG", "A")).thenReturn(0L);
        Mockito.when(gapplicationRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.findGApplicationByNameAndStatus("pmSTEa0K0svQ6obJVpabjnM6NuVTfJ5eg0MhkP5LWKTvHrXnYG", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_NAME));
    }
    @Test
    public void shouldReturnExistentGApplicationDTOWhenFindGApplicationByExternalCodeUUIDAndStatus() {
        // scenario
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder().now());
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByExternalCodeUUIDAndStatus(UUID.fromString("83cac6a7-3277-45ee-8fdd-3f3fb5ab0cd0"), "A")).thenReturn(1L);
        Mockito.when(gapplicationRepositoryMock.findById(1L)).thenReturn(gapplicationModelMock);

        // action
        GApplicationDTO result = gapplicationService.findGApplicationByExternalCodeUUIDAndStatus(UUID.fromString("83cac6a7-3277-45ee-8fdd-3f3fb5ab0cd0"), "A");

        // validate
        Assertions.assertInstanceOf(GApplicationDTO.class,result);
    }
    @Test
    public void shouldReturnGApplicationNotFoundExceptionWhenNonExistenceGApplicationExternalCodeUUIDAndStatus() {
        // scenario
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByExternalCodeUUIDAndStatus(UUID.fromString("83cac6a7-3277-45ee-8fdd-3f3fb5ab0cd0"), "A")).thenReturn(0L);
        Mockito.when(gapplicationRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.findGApplicationByExternalCodeUUIDAndStatus(UUID.fromString("83cac6a7-3277-45ee-8fdd-3f3fb5ab0cd0"), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_EXTERNALCODEUUID));
    }

    @Test
    public void shouldReturnGApplicationDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "Mp9Fi7DJJOIEEKb2Wyq020g46D0i9iLs9qVB21BjjxYLQ9XCvq";
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(gapplicationRepositoryMock.findById(420L)).thenReturn(gapplicationModelMock);
        Mockito.doNothing().when(gapplicationRepositoryMock).updateNameById(420L, nameUpdateMock);

        // action
        gapplicationService.updateNameById(420L, nameUpdateMock);

        // validate
        Mockito.verify(gapplicationRepositoryMock,Mockito.times(1)).updateNameById(420L, nameUpdateMock);
    }
    @Test
    public void shouldReturnGApplicationDTOWhenUpdateExistingExternalCodeUUIDById() {
        // scenario
        UUID externalCodeUUIDUpdateMock = UUID.fromString("37923763-ba74-4577-ace2-7193c8a1e8ba");
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(gapplicationRepositoryMock.findById(420L)).thenReturn(gapplicationModelMock);
        Mockito.doNothing().when(gapplicationRepositoryMock).updateExternalCodeUUIDById(420L, externalCodeUUIDUpdateMock);

        // action
        gapplicationService.updateExternalCodeUUIDById(420L, externalCodeUUIDUpdateMock);

        // validate
        Mockito.verify(gapplicationRepositoryMock,Mockito.times(1)).updateExternalCodeUUIDById(420L, externalCodeUUIDUpdateMock);
    }



    @Test
    public void showReturnExistingGApplicationDTOWhenFindGApplicationByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 71550L;
        Long maxIdMock = 1972L;
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(gapplicationRepositoryMock.findById(maxIdMock)).thenReturn(gapplicationModelMock);

        // action
        GApplicationDTO result = gapplicationService.findGApplicationByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnGApplicationNotFoundExceptionWhenNonExistenceFindGApplicationByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 71550L;
        Long noMaxIdMock = 0L;
        Optional<GApplication> gapplicationModelMock = Optional.empty();
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(gapplicationRepositoryMock.findById(noMaxIdMock)).thenReturn(gapplicationModelMock);

        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.findGApplicationByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGApplicationDTOWhenFindGApplicationByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "D0iulGxpCgTeCD9E3wPbJxi8qt8v8gWCnuc17n0rRBFk8m70YM";
        Long maxIdMock = 1972L;
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder()
                .name(nameMock)
                .now()
        );
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(maxIdMock);
        Mockito.when(gapplicationRepositoryMock.findById(maxIdMock)).thenReturn(gapplicationModelMock);

        // action
        GApplicationDTO result = gapplicationService.findGApplicationByNameAndStatus(nameMock);

        // validate
        Assertions.assertEquals(nameMock, result.getName());

    }
    @Test
    public void showReturnGApplicationNotFoundExceptionWhenNonExistenceFindGApplicationByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "D0iulGxpCgTeCD9E3wPbJxi8qt8v8gWCnuc17n0rRBFk8m70YM";
        Long noMaxIdMock = 0L;
        Optional<GApplication> gapplicationModelMock = Optional.empty();
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(gapplicationRepositoryMock.findById(noMaxIdMock)).thenReturn(gapplicationModelMock);

        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.findGApplicationByNameAndStatus(nameMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_NAME));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingGApplicationDTOWhenFindGApplicationByExternalCodeUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID externalCodeUUIDMock = UUID.fromString("6f59279c-07f2-430d-8921-6682a705dc37");
        Long maxIdMock = 1972L;
        Optional<GApplication> gapplicationModelMock = Optional.ofNullable(GApplicationModelBuilder.newGApplicationModelTestBuilder()
                .externalCodeUUID(externalCodeUUIDMock)
                .now()
        );
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByExternalCodeUUIDAndStatus(externalCodeUUIDMock, "A")).thenReturn(maxIdMock);
        Mockito.when(gapplicationRepositoryMock.findById(maxIdMock)).thenReturn(gapplicationModelMock);

        // action
        GApplicationDTO result = gapplicationService.findGApplicationByExternalCodeUUIDAndStatus(externalCodeUUIDMock);

        // validate
        Assertions.assertEquals(externalCodeUUIDMock, result.getExternalCodeUUID());

    }
    @Test
    public void showReturnGApplicationNotFoundExceptionWhenNonExistenceFindGApplicationByExternalCodeUUIDAndStatusActiveAnonimous() {
        // scenario
        UUID externalCodeUUIDMock = UUID.fromString("6f59279c-07f2-430d-8921-6682a705dc37");
        Long noMaxIdMock = 0L;
        Optional<GApplication> gapplicationModelMock = Optional.empty();
        Mockito.when(gapplicationRepositoryMock.loadMaxIdByExternalCodeUUIDAndStatus(externalCodeUUIDMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(gapplicationRepositoryMock.findById(noMaxIdMock)).thenReturn(gapplicationModelMock);

        // action
        GApplicationNotFoundException exception = Assertions.assertThrows(GApplicationNotFoundException.class,
                ()->gapplicationService.findGApplicationByExternalCodeUUIDAndStatus(externalCodeUUIDMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(GAPPLICATION_NOTFOUND_WITH_EXTERNALCODEUUID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

