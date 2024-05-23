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

package br.com.jcv.bei.corelayer.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.bei.corelayer.builder.EconomicIndexDataDTOBuilder;
import br.com.jcv.bei.corelayer.builder.EconomicIndexDataModelBuilder;
import br.com.jcv.bei.corelayer.dto.EconomicIndexDataDTO;
import br.com.jcv.bei.corelayer.exception.EconomicIndexDataNotFoundException;
import br.com.jcv.bei.corelayer.model.EconomicIndexData;
import br.com.jcv.bei.corelayer.repository.EconomicIndexDataRepository;
import br.com.jcv.bei.corelayer.service.EconomicIndexDataService;
import br.com.jcv.bei.corelayer.constantes.EconomicIndexDataConstantes;
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
public class EconomicIndexDataServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String ECONOMICINDEXDATA_NOTFOUND_WITH_ID = "EconomicIndexData não encontrada com id = ";
    public static final String ECONOMICINDEXDATA_NOTFOUND_WITH_ECONOMICINDEXID = "EconomicIndexData não encontrada com economicIndexId = ";
    public static final String ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXDATE = "EconomicIndexData não encontrada com indexDate = ";
    public static final String ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXVALUE = "EconomicIndexData não encontrada com indexValue = ";
    public static final String ECONOMICINDEXDATA_NOTFOUND_WITH_STATUS = "EconomicIndexData não encontrada com status = ";
    public static final String ECONOMICINDEXDATA_NOTFOUND_WITH_DATECREATED = "EconomicIndexData não encontrada com dateCreated = ";
    public static final String ECONOMICINDEXDATA_NOTFOUND_WITH_DATEUPDATED = "EconomicIndexData não encontrada com dateUpdated = ";


    @Mock
    private EconomicIndexDataRepository economicindexdataRepositoryMock;

    @InjectMocks
    private EconomicIndexDataService economicindexdataService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        economicindexdataService = new EconomicIndexDataServiceImpl();
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
    public void shouldReturnListOfEconomicIndexDataWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 34423L;
        Long economicIndexId = 66040L;
        String indexDate = "2025-10-07";
        Double indexValue = 8073.0;
        String status = "olsRTkwf0itI0VCW87DcUo6hw4hgQ2xJMBurOla09PUit2KDmy";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("economicIndexId", economicIndexId);
        mapFieldsRequestMock.put("indexDate", indexDate);
        mapFieldsRequestMock.put("indexValue", indexValue);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<EconomicIndexData> economicindexdatasFromRepository = new ArrayList<>();
        economicindexdatasFromRepository.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        economicindexdatasFromRepository.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        economicindexdatasFromRepository.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        economicindexdatasFromRepository.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());

        Mockito.when(economicindexdataRepositoryMock.findEconomicIndexDataByFilter(
            id,
            economicIndexId,
            indexDate,
            indexValue,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(economicindexdatasFromRepository);

        // action
        List<EconomicIndexDataDTO> result = economicindexdataService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithEconomicIndexDataListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 73588L;
        Long economicIndexId = 17088L;
        String indexDate = "2025-10-07";
        Double indexValue = 3754.0;
        String status = "RauptpNGla0qJWDGpU6yTlmMjsojoixf2Uw9XRFyxNOzwmpsxl";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("economicIndexId", economicIndexId);
        mapFieldsRequestMock.put("indexDate", indexDate);
        mapFieldsRequestMock.put("indexValue", indexValue);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<EconomicIndexData> economicindexdatasFromRepository = new ArrayList<>();
        economicindexdatasFromRepository.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        economicindexdatasFromRepository.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        economicindexdatasFromRepository.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        economicindexdatasFromRepository.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());

        List<EconomicIndexDataDTO> economicindexdatasFiltered = economicindexdatasFromRepository
                .stream()
                .map(m->economicindexdataService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageEconomicIndexDataItems", economicindexdatasFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<EconomicIndexData> pagedResponse =
                new PageImpl<>(economicindexdatasFromRepository,
                        pageableMock,
                        economicindexdatasFromRepository.size());

        Mockito.when(economicindexdataRepositoryMock.findEconomicIndexDataByFilter(pageableMock,
            id,
            economicIndexId,
            indexDate,
            indexValue,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = economicindexdataService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<EconomicIndexDataDTO> economicindexdatasResult = (List<EconomicIndexDataDTO>) result.get("pageEconomicIndexDataItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, economicindexdatasResult.size());
    }


    @Test
    public void showReturnListOfEconomicIndexDataWhenAskedForFindAllByStatus() {
        // scenario
        List<EconomicIndexData> listOfEconomicIndexDataModelMock = new ArrayList<>();
        listOfEconomicIndexDataModelMock.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        listOfEconomicIndexDataModelMock.add(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());

        Mockito.when(economicindexdataRepositoryMock.findAllByStatus("A")).thenReturn(listOfEconomicIndexDataModelMock);

        // action
        List<EconomicIndexDataDTO> listOfEconomicIndexDatas = economicindexdataService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfEconomicIndexDatas.isEmpty());
        Assertions.assertEquals(2, listOfEconomicIndexDatas.size());
    }
    @Test
    public void shouldReturnEconomicIndexDataNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 7015L;
        Optional<EconomicIndexData> economicindexdataNonExistentMock = Optional.empty();
        Mockito.when(economicindexdataRepositoryMock.findById(idMock)).thenReturn(economicindexdataNonExistentMock);

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowEconomicIndexDataNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 72522L;
        Mockito.when(economicindexdataRepositoryMock.findById(idMock))
                .thenThrow(new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                ECONOMICINDEXDATA_NOTFOUND_WITH_ID ));

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnEconomicIndexDataDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 21152L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(
                EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                        .id(idMock)
                        .economicIndexId(76082L)
                        .indexDate(LocalDate.of(1662,4,12))
                        .indexValue(308.0)

                        .status("X")
                        .now()
        );
        EconomicIndexData economicindexdataToSaveMock = economicindexdataModelMock.orElse(null);
        EconomicIndexData economicindexdataSavedMck = EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                        .id(65268L)
                        .economicIndexId(78835L)
                        .indexDate(LocalDate.of(5624,9,5))
                        .indexValue(4677.0)

                        .status("A")
                        .now();
        Mockito.when(economicindexdataRepositoryMock.findById(idMock)).thenReturn(economicindexdataModelMock);
        Mockito.when(economicindexdataRepositoryMock.save(economicindexdataToSaveMock)).thenReturn(economicindexdataSavedMck);

        // action
        EconomicIndexDataDTO result = economicindexdataService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchEconomicIndexDataByAnyNonExistenceIdAndReturnEconomicIndexDataNotFoundException() {
        // scenario
        Mockito.when(economicindexdataRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()-> economicindexdataService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchEconomicIndexDataByIdAndReturnDTO() {
        // scenario
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .id(61541L)
                .economicIndexId(66416L)
                .indexDate(LocalDate.of(3444,12,17))
                .indexValue(2068.0)

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(economicindexdataRepositoryMock.findById(Mockito.anyLong())).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findById(1L);

        // validate
        Assertions.assertInstanceOf(EconomicIndexDataDTO.class,result);
    }
    @Test
    public void shouldDeleteEconomicIndexDataByIdWithSucess() {
        // scenario
        Optional<EconomicIndexData> economicindexdata = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().id(1L).now());
        Mockito.when(economicindexdataRepositoryMock.findById(Mockito.anyLong())).thenReturn(economicindexdata);

        // action
        economicindexdataService.delete(1L);

        // validate
        Mockito.verify(economicindexdataRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceEconomicIndexDataShouldReturnEconomicIndexDataNotFoundException() {
        // scenario
        Mockito.when(economicindexdataRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(
                EconomicIndexDataNotFoundException.class, () -> economicindexdataService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingEconomicIndexDataWithSucess() {
        // scenario
        EconomicIndexDataDTO economicindexdataDTOMock = EconomicIndexDataDTOBuilder.newEconomicIndexDataDTOTestBuilder()
                .id(56353L)
                .economicIndexId(6615L)
                .indexDate(LocalDate.of(8723,4,13))
                .indexValue(473.0)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        EconomicIndexData economicindexdataMock = EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .id(economicindexdataDTOMock.getId())
                .economicIndexId(economicindexdataDTOMock.getEconomicIndexId())
                .indexDate(economicindexdataDTOMock.getIndexDate())
                .indexValue(economicindexdataDTOMock.getIndexValue())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        EconomicIndexData economicindexdataSavedMock = EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .id(economicindexdataDTOMock.getId())
                .economicIndexId(economicindexdataDTOMock.getEconomicIndexId())
                .indexDate(economicindexdataDTOMock.getIndexDate())
                .indexValue(economicindexdataDTOMock.getIndexValue())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(economicindexdataRepositoryMock.save(economicindexdataMock)).thenReturn(economicindexdataSavedMock);

        // action
        EconomicIndexDataDTO economicindexdataSaved = economicindexdataService.salvar(economicindexdataDTOMock);

        // validate
        Assertions.assertInstanceOf(EconomicIndexDataDTO.class, economicindexdataSaved);
        Assertions.assertNotNull(economicindexdataSaved.getId());
    }

    @Test
    public void ShouldSaveNewEconomicIndexDataWithSucess() {
        // scenario
        EconomicIndexDataDTO economicindexdataDTOMock = EconomicIndexDataDTOBuilder.newEconomicIndexDataDTOTestBuilder()
                .id(null)
                .economicIndexId(16743L)
                .indexDate(LocalDate.of(3080,9,14))
                .indexValue(5333.0)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        EconomicIndexData economicindexdataModelMock = EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .id(null)
                .economicIndexId(economicindexdataDTOMock.getEconomicIndexId())
                .indexDate(economicindexdataDTOMock.getIndexDate())
                .indexValue(economicindexdataDTOMock.getIndexValue())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        EconomicIndexData economicindexdataSavedMock = EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .id(501L)
                .economicIndexId(economicindexdataDTOMock.getEconomicIndexId())
                .indexDate(economicindexdataDTOMock.getIndexDate())
                .indexValue(economicindexdataDTOMock.getIndexValue())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(economicindexdataRepositoryMock.save(economicindexdataModelMock)).thenReturn(economicindexdataSavedMock);

        // action
        EconomicIndexDataDTO economicindexdataSaved = economicindexdataService.salvar(economicindexdataDTOMock);

        // validate
        Assertions.assertInstanceOf(EconomicIndexDataDTO.class, economicindexdataSaved);
        Assertions.assertNotNull(economicindexdataSaved.getId());
        Assertions.assertEquals("P",economicindexdataSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapEconomicIndexDataDTOMock = new HashMap<>();
        mapEconomicIndexDataDTOMock.put(EconomicIndexDataConstantes.ECONOMICINDEXID,8011L);
        mapEconomicIndexDataDTOMock.put(EconomicIndexDataConstantes.INDEXDATE,LocalDate.of(7503,11,4));
        mapEconomicIndexDataDTOMock.put(EconomicIndexDataConstantes.INDEXVALUE,8664.0);
        mapEconomicIndexDataDTOMock.put(EconomicIndexDataConstantes.STATUS,"xv5B6abP7DL44GirLU0UlxU9Kl6SKXllxjSsSXAjsohG0ldGhU");


        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(
                EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                        .id(46707L)
                        .economicIndexId(70767L)
                        .indexDate(LocalDate.of(7010,3,30))
                        .indexValue(1023.0)
                        .status("CVqIptRNtRSNQbe52zIM5ctIpRVve6t6BVysgv6pu2MTNGa4HJ")

                        .now()
        );

        Mockito.when(economicindexdataRepositoryMock.findById(1L)).thenReturn(economicindexdataModelMock);

        // action
        boolean executed = economicindexdataService.partialUpdate(1L, mapEconomicIndexDataDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnEconomicIndexDataNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapEconomicIndexDataDTOMock = new HashMap<>();
        mapEconomicIndexDataDTOMock.put(EconomicIndexDataConstantes.ECONOMICINDEXID,63886L);
        mapEconomicIndexDataDTOMock.put(EconomicIndexDataConstantes.INDEXDATE,LocalDate.of(486,4,10));
        mapEconomicIndexDataDTOMock.put(EconomicIndexDataConstantes.INDEXVALUE,4577.0);
        mapEconomicIndexDataDTOMock.put(EconomicIndexDataConstantes.STATUS,"1NiADpObqUQL5b3bf3zhobzgnlI4KiaTcigj2NFoR1BhPQCrMd");


        Mockito.when(economicindexdataRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.partialUpdate(1L, mapEconomicIndexDataDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("EconomicIndexData não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnEconomicIndexDataListWhenFindAllEconomicIndexDataByIdAndStatus() {
        // scenario
        List<EconomicIndexData> economicindexdatas = Arrays.asList(
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now()
        );

        Mockito.when(economicindexdataRepositoryMock.findAllByIdAndStatus(32280L, "A")).thenReturn(economicindexdatas);

        // action
        List<EconomicIndexDataDTO> result = economicindexdataService.findAllEconomicIndexDataByIdAndStatus(32280L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexDataListWhenFindAllEconomicIndexDataByEconomicIndexIdAndStatus() {
        // scenario
        List<EconomicIndexData> economicindexdatas = Arrays.asList(
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now()
        );

        Mockito.when(economicindexdataRepositoryMock.findAllByEconomicIndexIdAndStatus(7543L, "A")).thenReturn(economicindexdatas);

        // action
        List<EconomicIndexDataDTO> result = economicindexdataService.findAllEconomicIndexDataByEconomicIndexIdAndStatus(7543L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexDataListWhenFindAllEconomicIndexDataByIndexDateAndStatus() {
        // scenario
        List<EconomicIndexData> economicindexdatas = Arrays.asList(
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now()
        );

        Mockito.when(economicindexdataRepositoryMock.findAllByIndexDateAndStatus(LocalDate.of(1273,4,4), "A")).thenReturn(economicindexdatas);

        // action
        List<EconomicIndexDataDTO> result = economicindexdataService.findAllEconomicIndexDataByIndexDateAndStatus(LocalDate.of(1273,4,4), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexDataListWhenFindAllEconomicIndexDataByIndexValueAndStatus() {
        // scenario
        List<EconomicIndexData> economicindexdatas = Arrays.asList(
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now()
        );

        Mockito.when(economicindexdataRepositoryMock.findAllByIndexValueAndStatus(8024.0, "A")).thenReturn(economicindexdatas);

        // action
        List<EconomicIndexDataDTO> result = economicindexdataService.findAllEconomicIndexDataByIndexValueAndStatus(8024.0, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexDataListWhenFindAllEconomicIndexDataByDateCreatedAndStatus() {
        // scenario
        List<EconomicIndexData> economicindexdatas = Arrays.asList(
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now()
        );

        Mockito.when(economicindexdataRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(economicindexdatas);

        // action
        List<EconomicIndexDataDTO> result = economicindexdataService.findAllEconomicIndexDataByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexDataListWhenFindAllEconomicIndexDataByDateUpdatedAndStatus() {
        // scenario
        List<EconomicIndexData> economicindexdatas = Arrays.asList(
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now(),
            EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now()
        );

        Mockito.when(economicindexdataRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(economicindexdatas);

        // action
        List<EconomicIndexDataDTO> result = economicindexdataService.findAllEconomicIndexDataByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentEconomicIndexDataDTOWhenFindEconomicIndexDataByIdAndStatus() {
        // scenario
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIdAndStatus(7048L, "A")).thenReturn(1L);
        Mockito.when(economicindexdataRepositoryMock.findById(1L)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findEconomicIndexDataByIdAndStatus(7048L, "A");

        // validate
        Assertions.assertInstanceOf(EconomicIndexDataDTO.class,result);
    }
    @Test
    public void shouldReturnEconomicIndexDataNotFoundExceptionWhenNonExistenceEconomicIndexDataIdAndStatus() {
        // scenario
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIdAndStatus(7048L, "A")).thenReturn(0L);
        Mockito.when(economicindexdataRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.findEconomicIndexDataByIdAndStatus(7048L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentEconomicIndexDataDTOWhenFindEconomicIndexDataByEconomicIndexIdAndStatus() {
        // scenario
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByEconomicIndexIdAndStatus(28422L, "A")).thenReturn(1L);
        Mockito.when(economicindexdataRepositoryMock.findById(1L)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findEconomicIndexDataByEconomicIndexIdAndStatus(28422L, "A");

        // validate
        Assertions.assertInstanceOf(EconomicIndexDataDTO.class,result);
    }
    @Test
    public void shouldReturnEconomicIndexDataNotFoundExceptionWhenNonExistenceEconomicIndexDataEconomicIndexIdAndStatus() {
        // scenario
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByEconomicIndexIdAndStatus(28422L, "A")).thenReturn(0L);
        Mockito.when(economicindexdataRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.findEconomicIndexDataByEconomicIndexIdAndStatus(28422L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_ECONOMICINDEXID));
    }
    @Test
    public void shouldReturnExistentEconomicIndexDataDTOWhenFindEconomicIndexDataByIndexDateAndStatus() {
        // scenario
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIndexDateAndStatus(LocalDate.of(3878,7,6), "A")).thenReturn(1L);
        Mockito.when(economicindexdataRepositoryMock.findById(1L)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findEconomicIndexDataByIndexDateAndStatus(LocalDate.of(3878,7,6), "A");

        // validate
        Assertions.assertInstanceOf(EconomicIndexDataDTO.class,result);
    }
    @Test
    public void shouldReturnEconomicIndexDataNotFoundExceptionWhenNonExistenceEconomicIndexDataIndexDateAndStatus() {
        // scenario
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIndexDateAndStatus(LocalDate.of(3878,7,6), "A")).thenReturn(0L);
        Mockito.when(economicindexdataRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.findEconomicIndexDataByIndexDateAndStatus(LocalDate.of(3878,7,6), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXDATE));
    }
    @Test
    public void shouldReturnExistentEconomicIndexDataDTOWhenFindEconomicIndexDataByIndexValueAndStatus() {
        // scenario
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder().now());
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIndexValueAndStatus(1768.0, "A")).thenReturn(1L);
        Mockito.when(economicindexdataRepositoryMock.findById(1L)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findEconomicIndexDataByIndexValueAndStatus(1768.0, "A");

        // validate
        Assertions.assertInstanceOf(EconomicIndexDataDTO.class,result);
    }
    @Test
    public void shouldReturnEconomicIndexDataNotFoundExceptionWhenNonExistenceEconomicIndexDataIndexValueAndStatus() {
        // scenario
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIndexValueAndStatus(1768.0, "A")).thenReturn(0L);
        Mockito.when(economicindexdataRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.findEconomicIndexDataByIndexValueAndStatus(1768.0, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXVALUE));
    }

    @Test
    public void shouldReturnEconomicIndexDataDTOWhenUpdateExistingEconomicIndexIdById() {
        // scenario
        Long economicIndexIdUpdateMock = 42247L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(economicindexdataRepositoryMock.findById(420L)).thenReturn(economicindexdataModelMock);
        Mockito.doNothing().when(economicindexdataRepositoryMock).updateEconomicIndexIdById(420L, economicIndexIdUpdateMock);

        // action
        economicindexdataService.updateEconomicIndexIdById(420L, economicIndexIdUpdateMock);

        // validate
        Mockito.verify(economicindexdataRepositoryMock,Mockito.times(1)).updateEconomicIndexIdById(420L, economicIndexIdUpdateMock);
    }
    @Test
    public void shouldReturnEconomicIndexDataDTOWhenUpdateExistingIndexDateById() {
        // scenario
        LocalDate indexDateUpdateMock = LocalDate.of(1566,12,27);
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(economicindexdataRepositoryMock.findById(420L)).thenReturn(economicindexdataModelMock);
        Mockito.doNothing().when(economicindexdataRepositoryMock).updateIndexDateById(420L, indexDateUpdateMock);

        // action
        economicindexdataService.updateIndexDateById(420L, indexDateUpdateMock);

        // validate
        Mockito.verify(economicindexdataRepositoryMock,Mockito.times(1)).updateIndexDateById(420L, indexDateUpdateMock);
    }
    @Test
    public void shouldReturnEconomicIndexDataDTOWhenUpdateExistingIndexValueById() {
        // scenario
        Double indexValueUpdateMock = 4367.0;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(economicindexdataRepositoryMock.findById(420L)).thenReturn(economicindexdataModelMock);
        Mockito.doNothing().when(economicindexdataRepositoryMock).updateIndexValueById(420L, indexValueUpdateMock);

        // action
        economicindexdataService.updateIndexValueById(420L, indexValueUpdateMock);

        // validate
        Mockito.verify(economicindexdataRepositoryMock,Mockito.times(1)).updateIndexValueById(420L, indexValueUpdateMock);
    }



    @Test
    public void showReturnExistingEconomicIndexDataDTOWhenFindEconomicIndexDataByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 57565L;
        Long maxIdMock = 1972L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(economicindexdataRepositoryMock.findById(maxIdMock)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findEconomicIndexDataByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnEconomicIndexDataNotFoundExceptionWhenNonExistenceFindEconomicIndexDataByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 57565L;
        Long noMaxIdMock = 0L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.empty();
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(economicindexdataRepositoryMock.findById(noMaxIdMock)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.findEconomicIndexDataByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingEconomicIndexDataDTOWhenFindEconomicIndexDataByEconomicIndexIdAndStatusActiveAnonimous() {
        // scenario
        Long economicIndexIdMock = 42300L;
        Long maxIdMock = 1972L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .economicIndexId(economicIndexIdMock)
                .now()
        );
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByEconomicIndexIdAndStatus(economicIndexIdMock, "A")).thenReturn(maxIdMock);
        Mockito.when(economicindexdataRepositoryMock.findById(maxIdMock)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findEconomicIndexDataByEconomicIndexIdAndStatus(economicIndexIdMock);

        // validate
        Assertions.assertEquals(economicIndexIdMock, result.getEconomicIndexId());

    }
    @Test
    public void showReturnEconomicIndexDataNotFoundExceptionWhenNonExistenceFindEconomicIndexDataByEconomicIndexIdAndStatusActiveAnonimous() {
        // scenario
        Long economicIndexIdMock = 42300L;
        Long noMaxIdMock = 0L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.empty();
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByEconomicIndexIdAndStatus(economicIndexIdMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(economicindexdataRepositoryMock.findById(noMaxIdMock)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.findEconomicIndexDataByEconomicIndexIdAndStatus(economicIndexIdMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_ECONOMICINDEXID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingEconomicIndexDataDTOWhenFindEconomicIndexDataByIndexDateAndStatusActiveAnonimous() {
        // scenario
        LocalDate indexDateMock = LocalDate.of(2471,3,19);
        Long maxIdMock = 1972L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .indexDate(indexDateMock)
                .now()
        );
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIndexDateAndStatus(indexDateMock, "A")).thenReturn(maxIdMock);
        Mockito.when(economicindexdataRepositoryMock.findById(maxIdMock)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findEconomicIndexDataByIndexDateAndStatus(indexDateMock);

        // validate
        Assertions.assertEquals(indexDateMock, result.getIndexDate());

    }
    @Test
    public void showReturnEconomicIndexDataNotFoundExceptionWhenNonExistenceFindEconomicIndexDataByIndexDateAndStatusActiveAnonimous() {
        // scenario
        LocalDate indexDateMock = LocalDate.of(2471,3,19);
        Long noMaxIdMock = 0L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.empty();
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIndexDateAndStatus(indexDateMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(economicindexdataRepositoryMock.findById(noMaxIdMock)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.findEconomicIndexDataByIndexDateAndStatus(indexDateMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXDATE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingEconomicIndexDataDTOWhenFindEconomicIndexDataByIndexValueAndStatusActiveAnonimous() {
        // scenario
        Double indexValueMock = 7105.0;
        Long maxIdMock = 1972L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.ofNullable(EconomicIndexDataModelBuilder.newEconomicIndexDataModelTestBuilder()
                .indexValue(indexValueMock)
                .now()
        );
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIndexValueAndStatus(indexValueMock, "A")).thenReturn(maxIdMock);
        Mockito.when(economicindexdataRepositoryMock.findById(maxIdMock)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataDTO result = economicindexdataService.findEconomicIndexDataByIndexValueAndStatus(indexValueMock);

        // validate
        Assertions.assertEquals(indexValueMock, result.getIndexValue());

    }
    @Test
    public void showReturnEconomicIndexDataNotFoundExceptionWhenNonExistenceFindEconomicIndexDataByIndexValueAndStatusActiveAnonimous() {
        // scenario
        Double indexValueMock = 7105.0;
        Long noMaxIdMock = 0L;
        Optional<EconomicIndexData> economicindexdataModelMock = Optional.empty();
        Mockito.when(economicindexdataRepositoryMock.loadMaxIdByIndexValueAndStatus(indexValueMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(economicindexdataRepositoryMock.findById(noMaxIdMock)).thenReturn(economicindexdataModelMock);

        // action
        EconomicIndexDataNotFoundException exception = Assertions.assertThrows(EconomicIndexDataNotFoundException.class,
                ()->economicindexdataService.findEconomicIndexDataByIndexValueAndStatus(indexValueMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXVALUE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

