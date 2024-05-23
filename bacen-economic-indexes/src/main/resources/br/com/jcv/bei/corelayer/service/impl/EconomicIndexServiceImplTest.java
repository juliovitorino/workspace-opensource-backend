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
import br.com.jcv.bei.corelayer.builder.EconomicIndexDTOBuilder;
import br.com.jcv.bei.corelayer.builder.EconomicIndexModelBuilder;
import br.com.jcv.bei.corelayer.dto.EconomicIndexDTO;
import br.com.jcv.bei.corelayer.exception.EconomicIndexNotFoundException;
import br.com.jcv.bei.corelayer.model.EconomicIndex;
import br.com.jcv.bei.corelayer.repository.EconomicIndexRepository;
import br.com.jcv.bei.corelayer.service.EconomicIndexService;
import br.com.jcv.bei.corelayer.constantes.EconomicIndexConstantes;
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
public class EconomicIndexServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String ECONOMICINDEX_NOTFOUND_WITH_ID = "EconomicIndex não encontrada com id = ";
    public static final String ECONOMICINDEX_NOTFOUND_WITH_ECONOMICINDEX = "EconomicIndex não encontrada com economicIndex = ";
    public static final String ECONOMICINDEX_NOTFOUND_WITH_BACENSERIECODE = "EconomicIndex não encontrada com bacenSerieCode = ";
    public static final String ECONOMICINDEX_NOTFOUND_WITH_LASTDATEVALUE = "EconomicIndex não encontrada com lastDateValue = ";
    public static final String ECONOMICINDEX_NOTFOUND_WITH_STATUS = "EconomicIndex não encontrada com status = ";
    public static final String ECONOMICINDEX_NOTFOUND_WITH_DATECREATED = "EconomicIndex não encontrada com dateCreated = ";
    public static final String ECONOMICINDEX_NOTFOUND_WITH_DATEUPDATED = "EconomicIndex não encontrada com dateUpdated = ";


    @Mock
    private EconomicIndexRepository economicindexRepositoryMock;

    @InjectMocks
    private EconomicIndexService economicindexService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        economicindexService = new EconomicIndexServiceImpl();
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
    public void shouldReturnListOfEconomicIndexWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 34086L;
        String economicIndex = "3ijGf6fSNaMmKYPHGt9x03DTA0eKwf0AUEIGIegknzIVSB1NP8";
        String bacenSerieCode = "rMSF0RLVWF6I7NdvvPRD64q5V1wRrfM5oexn9i5XejPz9BUjVM";
        String lastDateValue = "2025-10-07";
        String status = "k1J67FHi3dg9OgiEQy5DPml5GWhqSgjHR1POAzYov1f5mPLl3s";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("economicIndex", economicIndex);
        mapFieldsRequestMock.put("bacenSerieCode", bacenSerieCode);
        mapFieldsRequestMock.put("lastDateValue", lastDateValue);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<EconomicIndex> economicindexsFromRepository = new ArrayList<>();
        economicindexsFromRepository.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        economicindexsFromRepository.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        economicindexsFromRepository.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        economicindexsFromRepository.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());

        Mockito.when(economicindexRepositoryMock.findEconomicIndexByFilter(
            id,
            economicIndex,
            bacenSerieCode,
            lastDateValue,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(economicindexsFromRepository);

        // action
        List<EconomicIndexDTO> result = economicindexService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithEconomicIndexListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 71064L;
        String economicIndex = "1MJOGMl1e2XvEn0CbOWhIwr6rJOzK3pb4wN72RHfPKnzSx83VD";
        String bacenSerieCode = "bBKhd4tXYMBAFmuu44jA8707BlF19UEj8easEoSeAKLMgpuRVQ";
        String lastDateValue = "2025-10-07";
        String status = "FBRpbW78w7IWNzGX0MTqgSVLFDAObMRQwg7tjj6qh25el0LvHl";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("economicIndex", economicIndex);
        mapFieldsRequestMock.put("bacenSerieCode", bacenSerieCode);
        mapFieldsRequestMock.put("lastDateValue", lastDateValue);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<EconomicIndex> economicindexsFromRepository = new ArrayList<>();
        economicindexsFromRepository.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        economicindexsFromRepository.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        economicindexsFromRepository.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        economicindexsFromRepository.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());

        List<EconomicIndexDTO> economicindexsFiltered = economicindexsFromRepository
                .stream()
                .map(m->economicindexService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageEconomicIndexItems", economicindexsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<EconomicIndex> pagedResponse =
                new PageImpl<>(economicindexsFromRepository,
                        pageableMock,
                        economicindexsFromRepository.size());

        Mockito.when(economicindexRepositoryMock.findEconomicIndexByFilter(pageableMock,
            id,
            economicIndex,
            bacenSerieCode,
            lastDateValue,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = economicindexService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<EconomicIndexDTO> economicindexsResult = (List<EconomicIndexDTO>) result.get("pageEconomicIndexItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, economicindexsResult.size());
    }


    @Test
    public void showReturnListOfEconomicIndexWhenAskedForFindAllByStatus() {
        // scenario
        List<EconomicIndex> listOfEconomicIndexModelMock = new ArrayList<>();
        listOfEconomicIndexModelMock.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        listOfEconomicIndexModelMock.add(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());

        Mockito.when(economicindexRepositoryMock.findAllByStatus("A")).thenReturn(listOfEconomicIndexModelMock);

        // action
        List<EconomicIndexDTO> listOfEconomicIndexs = economicindexService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfEconomicIndexs.isEmpty());
        Assertions.assertEquals(2, listOfEconomicIndexs.size());
    }
    @Test
    public void shouldReturnEconomicIndexNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 68337L;
        Optional<EconomicIndex> economicindexNonExistentMock = Optional.empty();
        Mockito.when(economicindexRepositoryMock.findById(idMock)).thenReturn(economicindexNonExistentMock);

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowEconomicIndexNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 1306L;
        Mockito.when(economicindexRepositoryMock.findById(idMock))
                .thenThrow(new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                ECONOMICINDEX_NOTFOUND_WITH_ID ));

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnEconomicIndexDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 11782L;
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(
                EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                        .id(idMock)
                        .economicIndex("B5T9rceNGSmibuxpw7GNBhuwNT2yvoP2bm4czkQe7fWEc3h7tN")
                        .bacenSerieCode("7kNuNxSIENoAuGcE7pqjJwm70f0QKLNgmmP97juHbLciwJvbog")
                        .lastDateValue(LocalDate.of(7700,3,3))

                        .status("X")
                        .now()
        );
        EconomicIndex economicindexToSaveMock = economicindexModelMock.orElse(null);
        EconomicIndex economicindexSavedMck = EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                        .id(30001L)
                        .economicIndex("M620vMKnjBmxwfQIdR8TYjgAzia3WUD3PTy68ImppFPT0NJn5u")
                        .bacenSerieCode("U1aX0fegK72H6KeElJBYcaB3cuWYeucRnhB4rj7PmYiLFDYwii")
                        .lastDateValue(LocalDate.of(465,10,11))

                        .status("A")
                        .now();
        Mockito.when(economicindexRepositoryMock.findById(idMock)).thenReturn(economicindexModelMock);
        Mockito.when(economicindexRepositoryMock.save(economicindexToSaveMock)).thenReturn(economicindexSavedMck);

        // action
        EconomicIndexDTO result = economicindexService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchEconomicIndexByAnyNonExistenceIdAndReturnEconomicIndexNotFoundException() {
        // scenario
        Mockito.when(economicindexRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()-> economicindexService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchEconomicIndexByIdAndReturnDTO() {
        // scenario
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .id(60487L)
                .economicIndex("6Mk2JQAQ4ER9WqEg0zLw4sOR3xJdBPGSLAVt8omTL49X44vp75")
                .bacenSerieCode("LcnHma6xhGS96lKocbAXai8aEttSSc0avaPnKBiQVhLpWGTktS")
                .lastDateValue(LocalDate.of(15,6,30))

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(economicindexRepositoryMock.findById(Mockito.anyLong())).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findById(1L);

        // validate
        Assertions.assertInstanceOf(EconomicIndexDTO.class,result);
    }
    @Test
    public void shouldDeleteEconomicIndexByIdWithSucess() {
        // scenario
        Optional<EconomicIndex> economicindex = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().id(1L).now());
        Mockito.when(economicindexRepositoryMock.findById(Mockito.anyLong())).thenReturn(economicindex);

        // action
        economicindexService.delete(1L);

        // validate
        Mockito.verify(economicindexRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceEconomicIndexShouldReturnEconomicIndexNotFoundException() {
        // scenario
        Mockito.when(economicindexRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(
                EconomicIndexNotFoundException.class, () -> economicindexService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingEconomicIndexWithSucess() {
        // scenario
        EconomicIndexDTO economicindexDTOMock = EconomicIndexDTOBuilder.newEconomicIndexDTOTestBuilder()
                .id(36505L)
                .economicIndex("hOCvuTNnptzfpihaQ5tX4RhcEilR0DaTt8tHxzLXRHRWCU860s")
                .bacenSerieCode("fbkQOEbHvElNTqauFgDS00qw0CDjCff6eeO3CPuuwhydUVOqMz")
                .lastDateValue(LocalDate.of(330,5,20))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        EconomicIndex economicindexMock = EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .id(economicindexDTOMock.getId())
                .economicIndex(economicindexDTOMock.getEconomicIndex())
                .bacenSerieCode(economicindexDTOMock.getBacenSerieCode())
                .lastDateValue(economicindexDTOMock.getLastDateValue())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        EconomicIndex economicindexSavedMock = EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .id(economicindexDTOMock.getId())
                .economicIndex(economicindexDTOMock.getEconomicIndex())
                .bacenSerieCode(economicindexDTOMock.getBacenSerieCode())
                .lastDateValue(economicindexDTOMock.getLastDateValue())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(economicindexRepositoryMock.save(economicindexMock)).thenReturn(economicindexSavedMock);

        // action
        EconomicIndexDTO economicindexSaved = economicindexService.salvar(economicindexDTOMock);

        // validate
        Assertions.assertInstanceOf(EconomicIndexDTO.class, economicindexSaved);
        Assertions.assertNotNull(economicindexSaved.getId());
    }

    @Test
    public void ShouldSaveNewEconomicIndexWithSucess() {
        // scenario
        EconomicIndexDTO economicindexDTOMock = EconomicIndexDTOBuilder.newEconomicIndexDTOTestBuilder()
                .id(null)
                .economicIndex("9QYKGORBTIU66dEbLJdxkUCvIyovJ8e0phvFWnAkAFXQSiBfWj")
                .bacenSerieCode("IXrSOQ4twKGX1euIVesO3kJxsEm6pAAEwgi5uw8Foak79klhjE")
                .lastDateValue(LocalDate.of(2151,6,6))

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        EconomicIndex economicindexModelMock = EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .id(null)
                .economicIndex(economicindexDTOMock.getEconomicIndex())
                .bacenSerieCode(economicindexDTOMock.getBacenSerieCode())
                .lastDateValue(economicindexDTOMock.getLastDateValue())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        EconomicIndex economicindexSavedMock = EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .id(501L)
                .economicIndex(economicindexDTOMock.getEconomicIndex())
                .bacenSerieCode(economicindexDTOMock.getBacenSerieCode())
                .lastDateValue(economicindexDTOMock.getLastDateValue())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(economicindexRepositoryMock.save(economicindexModelMock)).thenReturn(economicindexSavedMock);

        // action
        EconomicIndexDTO economicindexSaved = economicindexService.salvar(economicindexDTOMock);

        // validate
        Assertions.assertInstanceOf(EconomicIndexDTO.class, economicindexSaved);
        Assertions.assertNotNull(economicindexSaved.getId());
        Assertions.assertEquals("P",economicindexSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapEconomicIndexDTOMock = new HashMap<>();
        mapEconomicIndexDTOMock.put(EconomicIndexConstantes.ECONOMICINDEX,"qHEfha8oMI2OVk1LqkWC2y8nQJKk7jBqRKLqaRwKudGsR7DAdn");
        mapEconomicIndexDTOMock.put(EconomicIndexConstantes.BACENSERIECODE,"MhcomC7kq49xdynJwAB6tCikxPzcO8wTkYYD83kjBFT4nv5Kjp");
        mapEconomicIndexDTOMock.put(EconomicIndexConstantes.LASTDATEVALUE,LocalDate.of(8112,1,1));
        mapEconomicIndexDTOMock.put(EconomicIndexConstantes.STATUS,"97iDd7SLFNaMX021lERWcKHF7idYM8SBuaEoLvUL4vloMPgfQU");


        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(
                EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                        .id(55303L)
                        .economicIndex("xfYp7dXAGaYOU6yB0Qxd5nwrhn8uBB21mjQ5Ew0JlUFhLJwMv0")
                        .bacenSerieCode("qLrz3bCRc6bN3fAQUYuO8kl5SjrQVLkF34Iq8DItb2vTG8bRcm")
                        .lastDateValue(LocalDate.of(4510,3,8))
                        .status("xrr08E8gCnfkyD3GX5w99SivxeWVKOt0zcHu0jlC0NCnz4Fuze")

                        .now()
        );

        Mockito.when(economicindexRepositoryMock.findById(1L)).thenReturn(economicindexModelMock);

        // action
        boolean executed = economicindexService.partialUpdate(1L, mapEconomicIndexDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnEconomicIndexNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapEconomicIndexDTOMock = new HashMap<>();
        mapEconomicIndexDTOMock.put(EconomicIndexConstantes.ECONOMICINDEX,"x5TiEtCGU3eET2kOh1MWfiadsIAVNPG9jsv9HB2Yz9JQkGVbfX");
        mapEconomicIndexDTOMock.put(EconomicIndexConstantes.BACENSERIECODE,"C6fbW6diSiTz31moFXHuXpE3xsWRAv9mxLMNfCUaB0MjvukbCR");
        mapEconomicIndexDTOMock.put(EconomicIndexConstantes.LASTDATEVALUE,LocalDate.of(2241,5,8));
        mapEconomicIndexDTOMock.put(EconomicIndexConstantes.STATUS,"5fobuG6NYmdQD6oGDtf4bcDY24d5yL603T5VkDYLT0ycE5ibrY");


        Mockito.when(economicindexRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.partialUpdate(1L, mapEconomicIndexDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("EconomicIndex não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnEconomicIndexListWhenFindAllEconomicIndexByIdAndStatus() {
        // scenario
        List<EconomicIndex> economicindexs = Arrays.asList(
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now()
        );

        Mockito.when(economicindexRepositoryMock.findAllByIdAndStatus(52686L, "A")).thenReturn(economicindexs);

        // action
        List<EconomicIndexDTO> result = economicindexService.findAllEconomicIndexByIdAndStatus(52686L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexListWhenFindAllEconomicIndexByEconomicIndexAndStatus() {
        // scenario
        List<EconomicIndex> economicindexs = Arrays.asList(
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now()
        );

        Mockito.when(economicindexRepositoryMock.findAllByEconomicIndexAndStatus("qCnEa6ItrA2hDykAjwS189HG7FIAj5glgEro9sdIaPgc0Pdcm5", "A")).thenReturn(economicindexs);

        // action
        List<EconomicIndexDTO> result = economicindexService.findAllEconomicIndexByEconomicIndexAndStatus("qCnEa6ItrA2hDykAjwS189HG7FIAj5glgEro9sdIaPgc0Pdcm5", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexListWhenFindAllEconomicIndexByBacenSerieCodeAndStatus() {
        // scenario
        List<EconomicIndex> economicindexs = Arrays.asList(
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now()
        );

        Mockito.when(economicindexRepositoryMock.findAllByBacenSerieCodeAndStatus("t0lXwIfwtl0z3r765TB0ClL0VJ8uYO0p9AfP6UvNgrvAxbrt1a", "A")).thenReturn(economicindexs);

        // action
        List<EconomicIndexDTO> result = economicindexService.findAllEconomicIndexByBacenSerieCodeAndStatus("t0lXwIfwtl0z3r765TB0ClL0VJ8uYO0p9AfP6UvNgrvAxbrt1a", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexListWhenFindAllEconomicIndexByLastDateValueAndStatus() {
        // scenario
        List<EconomicIndex> economicindexs = Arrays.asList(
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now()
        );

        Mockito.when(economicindexRepositoryMock.findAllByLastDateValueAndStatus(LocalDate.of(164,9,1), "A")).thenReturn(economicindexs);

        // action
        List<EconomicIndexDTO> result = economicindexService.findAllEconomicIndexByLastDateValueAndStatus(LocalDate.of(164,9,1), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexListWhenFindAllEconomicIndexByDateCreatedAndStatus() {
        // scenario
        List<EconomicIndex> economicindexs = Arrays.asList(
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now()
        );

        Mockito.when(economicindexRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(economicindexs);

        // action
        List<EconomicIndexDTO> result = economicindexService.findAllEconomicIndexByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnEconomicIndexListWhenFindAllEconomicIndexByDateUpdatedAndStatus() {
        // scenario
        List<EconomicIndex> economicindexs = Arrays.asList(
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now(),
            EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now()
        );

        Mockito.when(economicindexRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(economicindexs);

        // action
        List<EconomicIndexDTO> result = economicindexService.findAllEconomicIndexByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentEconomicIndexDTOWhenFindEconomicIndexByIdAndStatus() {
        // scenario
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        Mockito.when(economicindexRepositoryMock.loadMaxIdByIdAndStatus(45410L, "A")).thenReturn(1L);
        Mockito.when(economicindexRepositoryMock.findById(1L)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findEconomicIndexByIdAndStatus(45410L, "A");

        // validate
        Assertions.assertInstanceOf(EconomicIndexDTO.class,result);
    }
    @Test
    public void shouldReturnEconomicIndexNotFoundExceptionWhenNonExistenceEconomicIndexIdAndStatus() {
        // scenario
        Mockito.when(economicindexRepositoryMock.loadMaxIdByIdAndStatus(45410L, "A")).thenReturn(0L);
        Mockito.when(economicindexRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.findEconomicIndexByIdAndStatus(45410L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentEconomicIndexDTOWhenFindEconomicIndexByEconomicIndexAndStatus() {
        // scenario
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        Mockito.when(economicindexRepositoryMock.loadMaxIdByEconomicIndexAndStatus("jRfVW22xsJMmXBHTPj0LH8wnG0heDBXX1NnVPoDKuyRST8qzsk", "A")).thenReturn(1L);
        Mockito.when(economicindexRepositoryMock.findById(1L)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findEconomicIndexByEconomicIndexAndStatus("jRfVW22xsJMmXBHTPj0LH8wnG0heDBXX1NnVPoDKuyRST8qzsk", "A");

        // validate
        Assertions.assertInstanceOf(EconomicIndexDTO.class,result);
    }
    @Test
    public void shouldReturnEconomicIndexNotFoundExceptionWhenNonExistenceEconomicIndexEconomicIndexAndStatus() {
        // scenario
        Mockito.when(economicindexRepositoryMock.loadMaxIdByEconomicIndexAndStatus("jRfVW22xsJMmXBHTPj0LH8wnG0heDBXX1NnVPoDKuyRST8qzsk", "A")).thenReturn(0L);
        Mockito.when(economicindexRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.findEconomicIndexByEconomicIndexAndStatus("jRfVW22xsJMmXBHTPj0LH8wnG0heDBXX1NnVPoDKuyRST8qzsk", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_ECONOMICINDEX));
    }
    @Test
    public void shouldReturnExistentEconomicIndexDTOWhenFindEconomicIndexByBacenSerieCodeAndStatus() {
        // scenario
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        Mockito.when(economicindexRepositoryMock.loadMaxIdByBacenSerieCodeAndStatus("P04pwHUIc9nxAmWfkGn1s7L6IdnybgqIB3RYjSIzJKN42WN0BL", "A")).thenReturn(1L);
        Mockito.when(economicindexRepositoryMock.findById(1L)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findEconomicIndexByBacenSerieCodeAndStatus("P04pwHUIc9nxAmWfkGn1s7L6IdnybgqIB3RYjSIzJKN42WN0BL", "A");

        // validate
        Assertions.assertInstanceOf(EconomicIndexDTO.class,result);
    }
    @Test
    public void shouldReturnEconomicIndexNotFoundExceptionWhenNonExistenceEconomicIndexBacenSerieCodeAndStatus() {
        // scenario
        Mockito.when(economicindexRepositoryMock.loadMaxIdByBacenSerieCodeAndStatus("P04pwHUIc9nxAmWfkGn1s7L6IdnybgqIB3RYjSIzJKN42WN0BL", "A")).thenReturn(0L);
        Mockito.when(economicindexRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.findEconomicIndexByBacenSerieCodeAndStatus("P04pwHUIc9nxAmWfkGn1s7L6IdnybgqIB3RYjSIzJKN42WN0BL", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_BACENSERIECODE));
    }
    @Test
    public void shouldReturnExistentEconomicIndexDTOWhenFindEconomicIndexByLastDateValueAndStatus() {
        // scenario
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder().now());
        Mockito.when(economicindexRepositoryMock.loadMaxIdByLastDateValueAndStatus(LocalDate.of(35,12,3), "A")).thenReturn(1L);
        Mockito.when(economicindexRepositoryMock.findById(1L)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findEconomicIndexByLastDateValueAndStatus(LocalDate.of(35,12,3), "A");

        // validate
        Assertions.assertInstanceOf(EconomicIndexDTO.class,result);
    }
    @Test
    public void shouldReturnEconomicIndexNotFoundExceptionWhenNonExistenceEconomicIndexLastDateValueAndStatus() {
        // scenario
        Mockito.when(economicindexRepositoryMock.loadMaxIdByLastDateValueAndStatus(LocalDate.of(35,12,3), "A")).thenReturn(0L);
        Mockito.when(economicindexRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.findEconomicIndexByLastDateValueAndStatus(LocalDate.of(35,12,3), "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_LASTDATEVALUE));
    }

    @Test
    public void shouldReturnEconomicIndexDTOWhenUpdateExistingEconomicIndexById() {
        // scenario
        String economicIndexUpdateMock = "qR2K4zmWxAWn9ptCrEHKtPb4v8eBzLVwhQE3mNIOTYa8JufbPq";
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(economicindexRepositoryMock.findById(420L)).thenReturn(economicindexModelMock);
        Mockito.doNothing().when(economicindexRepositoryMock).updateEconomicIndexById(420L, economicIndexUpdateMock);

        // action
        economicindexService.updateEconomicIndexById(420L, economicIndexUpdateMock);

        // validate
        Mockito.verify(economicindexRepositoryMock,Mockito.times(1)).updateEconomicIndexById(420L, economicIndexUpdateMock);
    }
    @Test
    public void shouldReturnEconomicIndexDTOWhenUpdateExistingBacenSerieCodeById() {
        // scenario
        String bacenSerieCodeUpdateMock = "JCN40uVSQAGhCJfsK7UrCeGzOSeVqWy8kGOvn2Ht4dVqr3zc3U";
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(economicindexRepositoryMock.findById(420L)).thenReturn(economicindexModelMock);
        Mockito.doNothing().when(economicindexRepositoryMock).updateBacenSerieCodeById(420L, bacenSerieCodeUpdateMock);

        // action
        economicindexService.updateBacenSerieCodeById(420L, bacenSerieCodeUpdateMock);

        // validate
        Mockito.verify(economicindexRepositoryMock,Mockito.times(1)).updateBacenSerieCodeById(420L, bacenSerieCodeUpdateMock);
    }
    @Test
    public void shouldReturnEconomicIndexDTOWhenUpdateExistingLastDateValueById() {
        // scenario
        LocalDate lastDateValueUpdateMock = LocalDate.of(443,2,7);
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(economicindexRepositoryMock.findById(420L)).thenReturn(economicindexModelMock);
        Mockito.doNothing().when(economicindexRepositoryMock).updateLastDateValueById(420L, lastDateValueUpdateMock);

        // action
        economicindexService.updateLastDateValueById(420L, lastDateValueUpdateMock);

        // validate
        Mockito.verify(economicindexRepositoryMock,Mockito.times(1)).updateLastDateValueById(420L, lastDateValueUpdateMock);
    }



    @Test
    public void showReturnExistingEconomicIndexDTOWhenFindEconomicIndexByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 62448L;
        Long maxIdMock = 1972L;
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(economicindexRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(economicindexRepositoryMock.findById(maxIdMock)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findEconomicIndexByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnEconomicIndexNotFoundExceptionWhenNonExistenceFindEconomicIndexByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 62448L;
        Long noMaxIdMock = 0L;
        Optional<EconomicIndex> economicindexModelMock = Optional.empty();
        Mockito.when(economicindexRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(economicindexRepositoryMock.findById(noMaxIdMock)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.findEconomicIndexByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingEconomicIndexDTOWhenFindEconomicIndexByEconomicIndexAndStatusActiveAnonimous() {
        // scenario
        String economicIndexMock = "dTA6xzgQ9SBpW180tz0MB3SpMI3I6dpEOAPuQuCRilBhJt3Frc";
        Long maxIdMock = 1972L;
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .economicIndex(economicIndexMock)
                .now()
        );
        Mockito.when(economicindexRepositoryMock.loadMaxIdByEconomicIndexAndStatus(economicIndexMock, "A")).thenReturn(maxIdMock);
        Mockito.when(economicindexRepositoryMock.findById(maxIdMock)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findEconomicIndexByEconomicIndexAndStatus(economicIndexMock);

        // validate
        Assertions.assertEquals(economicIndexMock, result.getEconomicIndex());

    }
    @Test
    public void showReturnEconomicIndexNotFoundExceptionWhenNonExistenceFindEconomicIndexByEconomicIndexAndStatusActiveAnonimous() {
        // scenario
        String economicIndexMock = "dTA6xzgQ9SBpW180tz0MB3SpMI3I6dpEOAPuQuCRilBhJt3Frc";
        Long noMaxIdMock = 0L;
        Optional<EconomicIndex> economicindexModelMock = Optional.empty();
        Mockito.when(economicindexRepositoryMock.loadMaxIdByEconomicIndexAndStatus(economicIndexMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(economicindexRepositoryMock.findById(noMaxIdMock)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.findEconomicIndexByEconomicIndexAndStatus(economicIndexMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_ECONOMICINDEX));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingEconomicIndexDTOWhenFindEconomicIndexByBacenSerieCodeAndStatusActiveAnonimous() {
        // scenario
        String bacenSerieCodeMock = "b20l5xg8lAwzyADtDxhN6wrG4mlLNxX7jbaaaE2TE53XhqwmY5";
        Long maxIdMock = 1972L;
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .bacenSerieCode(bacenSerieCodeMock)
                .now()
        );
        Mockito.when(economicindexRepositoryMock.loadMaxIdByBacenSerieCodeAndStatus(bacenSerieCodeMock, "A")).thenReturn(maxIdMock);
        Mockito.when(economicindexRepositoryMock.findById(maxIdMock)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findEconomicIndexByBacenSerieCodeAndStatus(bacenSerieCodeMock);

        // validate
        Assertions.assertEquals(bacenSerieCodeMock, result.getBacenSerieCode());

    }
    @Test
    public void showReturnEconomicIndexNotFoundExceptionWhenNonExistenceFindEconomicIndexByBacenSerieCodeAndStatusActiveAnonimous() {
        // scenario
        String bacenSerieCodeMock = "b20l5xg8lAwzyADtDxhN6wrG4mlLNxX7jbaaaE2TE53XhqwmY5";
        Long noMaxIdMock = 0L;
        Optional<EconomicIndex> economicindexModelMock = Optional.empty();
        Mockito.when(economicindexRepositoryMock.loadMaxIdByBacenSerieCodeAndStatus(bacenSerieCodeMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(economicindexRepositoryMock.findById(noMaxIdMock)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.findEconomicIndexByBacenSerieCodeAndStatus(bacenSerieCodeMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_BACENSERIECODE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingEconomicIndexDTOWhenFindEconomicIndexByLastDateValueAndStatusActiveAnonimous() {
        // scenario
        LocalDate lastDateValueMock = LocalDate.of(4010,5,2);
        Long maxIdMock = 1972L;
        Optional<EconomicIndex> economicindexModelMock = Optional.ofNullable(EconomicIndexModelBuilder.newEconomicIndexModelTestBuilder()
                .lastDateValue(lastDateValueMock)
                .now()
        );
        Mockito.when(economicindexRepositoryMock.loadMaxIdByLastDateValueAndStatus(lastDateValueMock, "A")).thenReturn(maxIdMock);
        Mockito.when(economicindexRepositoryMock.findById(maxIdMock)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexDTO result = economicindexService.findEconomicIndexByLastDateValueAndStatus(lastDateValueMock);

        // validate
        Assertions.assertEquals(lastDateValueMock, result.getLastDateValue());

    }
    @Test
    public void showReturnEconomicIndexNotFoundExceptionWhenNonExistenceFindEconomicIndexByLastDateValueAndStatusActiveAnonimous() {
        // scenario
        LocalDate lastDateValueMock = LocalDate.of(4010,5,2);
        Long noMaxIdMock = 0L;
        Optional<EconomicIndex> economicindexModelMock = Optional.empty();
        Mockito.when(economicindexRepositoryMock.loadMaxIdByLastDateValueAndStatus(lastDateValueMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(economicindexRepositoryMock.findById(noMaxIdMock)).thenReturn(economicindexModelMock);

        // action
        EconomicIndexNotFoundException exception = Assertions.assertThrows(EconomicIndexNotFoundException.class,
                ()->economicindexService.findEconomicIndexByLastDateValueAndStatus(lastDateValueMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(ECONOMICINDEX_NOTFOUND_WITH_LASTDATEVALUE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

