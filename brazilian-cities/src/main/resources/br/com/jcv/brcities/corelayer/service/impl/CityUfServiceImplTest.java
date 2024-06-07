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

package br.com.jcv.brcities.corelayer.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.brcities.corelayer.builder.CityUfDTOBuilder;
import br.com.jcv.brcities.corelayer.builder.CityUfModelBuilder;
import br.com.jcv.brcities.corelayer.dto.CityUfDTO;
import br.com.jcv.brcities.corelayer.exception.CityUfNotFoundException;
import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.brcities.corelayer.repository.CityUfRepository;
import br.com.jcv.brcities.corelayer.service.CityUfService;
import br.com.jcv.brcities.corelayer.constantes.CityUfConstantes;
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
public class CityUfServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String CITYUF_NOTFOUND_WITH_ID = "CityUf não encontrada com id = ";
    public static final String CITYUF_NOTFOUND_WITH_CITYID = "CityUf não encontrada com cityId = ";
    public static final String CITYUF_NOTFOUND_WITH_UFID = "CityUf não encontrada com ufId = ";
    public static final String CITYUF_NOTFOUND_WITH_DDD = "CityUf não encontrada com ddd = ";
    public static final String CITYUF_NOTFOUND_WITH_LATITUDE = "CityUf não encontrada com latitude = ";
    public static final String CITYUF_NOTFOUND_WITH_LONGITUDE = "CityUf não encontrada com longitude = ";
    public static final String CITYUF_NOTFOUND_WITH_STATUS = "CityUf não encontrada com status = ";
    public static final String CITYUF_NOTFOUND_WITH_DATECREATED = "CityUf não encontrada com dateCreated = ";
    public static final String CITYUF_NOTFOUND_WITH_DATEUPDATED = "CityUf não encontrada com dateUpdated = ";


    @Mock
    private CityUfRepository cityufRepositoryMock;

    @InjectMocks
    private CityUfService cityufService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        cityufService = new CityUfServiceImpl();
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
    public void shouldReturnListOfCityUfWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 22516L;
        Long cityId = 1624L;
        String ufId = "nDqrVGggzrN7jWH6dCP0ACwsFD0bjTkH9NlF0IxD0AkCxxCfMK";
        String ddd = "fLItt4f07C044yzJGTiYr0kBI2uMek0zGvRA1Is68rNdI1gEIN";
        Double latitude = 8244.0;
        Double longitude = 510.0;
        String status = "EDQdzt3LbwLnYbmA0MMLlDGHH2oQ6CjqW07FAc7RmK0JWDiza6";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("cityId", cityId);
        mapFieldsRequestMock.put("ufId", ufId);
        mapFieldsRequestMock.put("ddd", ddd);
        mapFieldsRequestMock.put("latitude", latitude);
        mapFieldsRequestMock.put("longitude", longitude);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<CityUf> cityufsFromRepository = new ArrayList<>();
        cityufsFromRepository.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        cityufsFromRepository.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        cityufsFromRepository.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        cityufsFromRepository.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());

        Mockito.when(cityufRepositoryMock.findCityUfByFilter(
            id,
            cityId,
            ufId,
            ddd,
            latitude,
            longitude,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(cityufsFromRepository);

        // action
        List<CityUfDTO> result = cityufService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithCityUfListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 35400L;
        Long cityId = 57080L;
        String ufId = "vioKdDzc0dIYgmHeClzG5OwXedUJjVjferuVkWrKUPdqSjtudq";
        String ddd = "BpiGtHaujj9pJ010k6KfeH0WvmtE7OQ40RLVgLhSR2y0HFYX38";
        Double latitude = 48.0;
        Double longitude = 177.0;
        String status = "663frJ5AuH0cft6Asn5hTzW0888xQzSNWbjj8oXvJbNLGQC27l";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("cityId", cityId);
        mapFieldsRequestMock.put("ufId", ufId);
        mapFieldsRequestMock.put("ddd", ddd);
        mapFieldsRequestMock.put("latitude", latitude);
        mapFieldsRequestMock.put("longitude", longitude);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<CityUf> cityufsFromRepository = new ArrayList<>();
        cityufsFromRepository.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        cityufsFromRepository.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        cityufsFromRepository.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        cityufsFromRepository.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());

        List<CityUfDTO> cityufsFiltered = cityufsFromRepository
                .stream()
                .map(m->cityufService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageCityUfItems", cityufsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<CityUf> pagedResponse =
                new PageImpl<>(cityufsFromRepository,
                        pageableMock,
                        cityufsFromRepository.size());

        Mockito.when(cityufRepositoryMock.findCityUfByFilter(pageableMock,
            id,
            cityId,
            ufId,
            ddd,
            latitude,
            longitude,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = cityufService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<CityUfDTO> cityufsResult = (List<CityUfDTO>) result.get("pageCityUfItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, cityufsResult.size());
    }


    @Test
    public void showReturnListOfCityUfWhenAskedForFindAllByStatus() {
        // scenario
        List<CityUf> listOfCityUfModelMock = new ArrayList<>();
        listOfCityUfModelMock.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        listOfCityUfModelMock.add(CityUfModelBuilder.newCityUfModelTestBuilder().now());

        Mockito.when(cityufRepositoryMock.findAllByStatus("A")).thenReturn(listOfCityUfModelMock);

        // action
        List<CityUfDTO> listOfCityUfs = cityufService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfCityUfs.isEmpty());
        Assertions.assertEquals(2, listOfCityUfs.size());
    }
    @Test
    public void shouldReturnCityUfNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 80017L;
        Optional<CityUf> cityufNonExistentMock = Optional.empty();
        Mockito.when(cityufRepositoryMock.findById(idMock)).thenReturn(cityufNonExistentMock);

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowCityUfNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 43010L;
        Mockito.when(cityufRepositoryMock.findById(idMock))
                .thenThrow(new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                CITYUF_NOTFOUND_WITH_ID ));

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnCityUfDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 70401L;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(
                CityUfModelBuilder.newCityUfModelTestBuilder()
                        .id(idMock)
                        .cityId(66171L)
                        .ufId("sJ5xeI0wHfUmawUe3guupVYm35idslckyirCXjICoisrgH1aaq")
                        .ddd("Vnlnhee1CrAyHe3g4MXyFVNKDfgzKwDDzFFKd6CVttB4V1ReAs")
                        .latitude(6827.0)
                        .longitude(608.0)

                        .status("X")
                        .now()
        );
        CityUf cityufToSaveMock = cityufModelMock.orElse(null);
        CityUf cityufSavedMck = CityUfModelBuilder.newCityUfModelTestBuilder()
                        .id(70311L)
                        .cityId(54140L)
                        .ufId("jM04uQIceHwC5jGfMVfHXOByQqDfqeQSIMJyJE7VrmzpdffJhq")
                        .ddd("7k0WMc5XdNEX4KMxBHCaYNXAX0mHK2fs2XzPvvktODssfLrvby")
                        .latitude(4071.0)
                        .longitude(8480.0)

                        .status("A")
                        .now();
        Mockito.when(cityufRepositoryMock.findById(idMock)).thenReturn(cityufModelMock);
        Mockito.when(cityufRepositoryMock.save(cityufToSaveMock)).thenReturn(cityufSavedMck);

        // action
        CityUfDTO result = cityufService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchCityUfByAnyNonExistenceIdAndReturnCityUfNotFoundException() {
        // scenario
        Mockito.when(cityufRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()-> cityufService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchCityUfByIdAndReturnDTO() {
        // scenario
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                .id(60045L)
                .cityId(34740L)
                .ufId("f5VwdyPLAIBiwbO0tx07I9x7Ayud7gKcvHu8AT7VHOg7MCIDW7")
                .ddd("bHYhDIrbMvUoaF1QEGOz1nW9bJrGKgMFDJSOaB9n7WDqhTVFjd")
                .latitude(7818.0)
                .longitude(24.0)

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(cityufRepositoryMock.findById(Mockito.anyLong())).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findById(1L);

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class,result);
    }
    @Test
    public void shouldDeleteCityUfByIdWithSucess() {
        // scenario
        Optional<CityUf> cityuf = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder().id(1L).now());
        Mockito.when(cityufRepositoryMock.findById(Mockito.anyLong())).thenReturn(cityuf);

        // action
        cityufService.delete(1L);

        // validate
        Mockito.verify(cityufRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceCityUfShouldReturnCityUfNotFoundException() {
        // scenario
        Mockito.when(cityufRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(
                CityUfNotFoundException.class, () -> cityufService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingCityUfWithSucess() {
        // scenario
        CityUfDTO cityufDTOMock = CityUfDTOBuilder.newCityUfDTOTestBuilder()
                .id(65353L)
                .cityId(62104L)
                .ufId("Vei3WPuKTIMPsLPJScvfN2TPixihC9XUKRRcX6QiDCvlfhuanJ")
                .ddd("jJHdUyfXTuLGBrRhcidwOSnrE8lfAy8YlrUm31rLdVUFJ2v3jn")
                .latitude(1450.0)
                .longitude(4273.0)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        CityUf cityufMock = CityUfModelBuilder.newCityUfModelTestBuilder()
                .id(cityufDTOMock.getId())
                .cityId(cityufDTOMock.getCityId())
                .ufId(cityufDTOMock.getUfId())
                .ddd(cityufDTOMock.getDdd())
                .latitude(cityufDTOMock.getLatitude())
                .longitude(cityufDTOMock.getLongitude())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        CityUf cityufSavedMock = CityUfModelBuilder.newCityUfModelTestBuilder()
                .id(cityufDTOMock.getId())
                .cityId(cityufDTOMock.getCityId())
                .ufId(cityufDTOMock.getUfId())
                .ddd(cityufDTOMock.getDdd())
                .latitude(cityufDTOMock.getLatitude())
                .longitude(cityufDTOMock.getLongitude())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(cityufRepositoryMock.save(cityufMock)).thenReturn(cityufSavedMock);

        // action
        CityUfDTO cityufSaved = cityufService.salvar(cityufDTOMock);

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class, cityufSaved);
        Assertions.assertNotNull(cityufSaved.getId());
    }

    @Test
    public void ShouldSaveNewCityUfWithSucess() {
        // scenario
        CityUfDTO cityufDTOMock = CityUfDTOBuilder.newCityUfDTOTestBuilder()
                .id(null)
                .cityId(72535L)
                .ufId("SVt97Y0QxxDnI1acII0wl0pFsYywFFhOHLubxrynrQW3mYtlFB")
                .ddd("gBf6gH566CHFmId0VCsEmUmxIYvNo3fqQHvKuBrVCsfuYPo2Kz")
                .latitude(5402.0)
                .longitude(6022.0)

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        CityUf cityufModelMock = CityUfModelBuilder.newCityUfModelTestBuilder()
                .id(null)
                .cityId(cityufDTOMock.getCityId())
                .ufId(cityufDTOMock.getUfId())
                .ddd(cityufDTOMock.getDdd())
                .latitude(cityufDTOMock.getLatitude())
                .longitude(cityufDTOMock.getLongitude())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        CityUf cityufSavedMock = CityUfModelBuilder.newCityUfModelTestBuilder()
                .id(501L)
                .cityId(cityufDTOMock.getCityId())
                .ufId(cityufDTOMock.getUfId())
                .ddd(cityufDTOMock.getDdd())
                .latitude(cityufDTOMock.getLatitude())
                .longitude(cityufDTOMock.getLongitude())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(cityufRepositoryMock.save(cityufModelMock)).thenReturn(cityufSavedMock);

        // action
        CityUfDTO cityufSaved = cityufService.salvar(cityufDTOMock);

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class, cityufSaved);
        Assertions.assertNotNull(cityufSaved.getId());
        Assertions.assertEquals("P",cityufSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapCityUfDTOMock = new HashMap<>();
        mapCityUfDTOMock.put(CityUfConstantes.CITYID,5266L);
        mapCityUfDTOMock.put(CityUfConstantes.UFID,"DVkaW0WElTmujQuS05oL5a75U8RsnQmXYAfkPl190pJvds4xBt");
        mapCityUfDTOMock.put(CityUfConstantes.DDD,"QFhfNkIbAeLbQ9zOjMexLsOIq7fCm1t63ScarWdCTVhus2fd52");
        mapCityUfDTOMock.put(CityUfConstantes.LATITUDE,2123.0);
        mapCityUfDTOMock.put(CityUfConstantes.LONGITUDE,2708.0);
        mapCityUfDTOMock.put(CityUfConstantes.STATUS,"FtcyVH9mieUe09toc1Kxq8qnaFTscMe27E7efCUJQcqriPhO9C");


        Optional<CityUf> cityufModelMock = Optional.ofNullable(
                CityUfModelBuilder.newCityUfModelTestBuilder()
                        .id(17208L)
                        .cityId(86140L)
                        .ufId("kE86eS0PuTtooQbczk1S9TCVdnR61IdAdAdBKAAX2SAvAtvr0s")
                        .ddd("7TeBSKQ2uxUjHquoYqfxtdaVWt40i2rYe6Jf4k5JcO219WMAzw")
                        .latitude(1003.0)
                        .longitude(5572.0)
                        .status("gIkRt0NEs5OSG00xfcY2zRwg0GyH5rSyJl2yYB8W6o9ksoiI0X")

                        .now()
        );

        Mockito.when(cityufRepositoryMock.findById(1L)).thenReturn(cityufModelMock);

        // action
        boolean executed = cityufService.partialUpdate(1L, mapCityUfDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnCityUfNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapCityUfDTOMock = new HashMap<>();
        mapCityUfDTOMock.put(CityUfConstantes.CITYID,64668L);
        mapCityUfDTOMock.put(CityUfConstantes.UFID,"IdbyhYpxQtSRtSjG1LjRAPAxwYYOekemv5NFUN5z2kuyo90sdv");
        mapCityUfDTOMock.put(CityUfConstantes.DDD,"IFzUI3Y5FsC3UiCbxEWzPcYXdCSbu0K5H0D0yON0awMgeoAw1o");
        mapCityUfDTOMock.put(CityUfConstantes.LATITUDE,4083.0);
        mapCityUfDTOMock.put(CityUfConstantes.LONGITUDE,1814.0);
        mapCityUfDTOMock.put(CityUfConstantes.STATUS,"GXEVbRmR91fpFqMfo9CkY88soutgYfXoOH9QIlqIadMJOgiQFc");


        Mockito.when(cityufRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.partialUpdate(1L, mapCityUfDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("CityUf não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnCityUfListWhenFindAllCityUfByIdAndStatus() {
        // scenario
        List<CityUf> cityufs = Arrays.asList(
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now()
        );

        Mockito.when(cityufRepositoryMock.findAllByIdAndStatus(15108L, "A")).thenReturn(cityufs);

        // action
        List<CityUfDTO> result = cityufService.findAllCityUfByIdAndStatus(15108L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityUfListWhenFindAllCityUfByCityIdAndStatus() {
        // scenario
        List<CityUf> cityufs = Arrays.asList(
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now()
        );

        Mockito.when(cityufRepositoryMock.findAllByCityIdAndStatus(85031L, "A")).thenReturn(cityufs);

        // action
        List<CityUfDTO> result = cityufService.findAllCityUfByCityIdAndStatus(85031L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityUfListWhenFindAllCityUfByUfIdAndStatus() {
        // scenario
        List<CityUf> cityufs = Arrays.asList(
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now()
        );

        Mockito.when(cityufRepositoryMock.findAllByUfIdAndStatus("0VA5Ls192HXBtCeeOyDvQAbm3XvGBqAsS5O71W55gxDtbwVee1", "A")).thenReturn(cityufs);

        // action
        List<CityUfDTO> result = cityufService.findAllCityUfByUfIdAndStatus("0VA5Ls192HXBtCeeOyDvQAbm3XvGBqAsS5O71W55gxDtbwVee1", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityUfListWhenFindAllCityUfByDddAndStatus() {
        // scenario
        List<CityUf> cityufs = Arrays.asList(
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now()
        );

        Mockito.when(cityufRepositoryMock.findAllByDddAndStatus("OXMECHjJY7AVJBjGSiKPXwz7WpJJvJ7Gpt8qqT3vwO1XkOWInz", "A")).thenReturn(cityufs);

        // action
        List<CityUfDTO> result = cityufService.findAllCityUfByDddAndStatus("OXMECHjJY7AVJBjGSiKPXwz7WpJJvJ7Gpt8qqT3vwO1XkOWInz", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityUfListWhenFindAllCityUfByLatitudeAndStatus() {
        // scenario
        List<CityUf> cityufs = Arrays.asList(
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now()
        );

        Mockito.when(cityufRepositoryMock.findAllByLatitudeAndStatus(7424.0, "A")).thenReturn(cityufs);

        // action
        List<CityUfDTO> result = cityufService.findAllCityUfByLatitudeAndStatus(7424.0, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityUfListWhenFindAllCityUfByLongitudeAndStatus() {
        // scenario
        List<CityUf> cityufs = Arrays.asList(
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now()
        );

        Mockito.when(cityufRepositoryMock.findAllByLongitudeAndStatus(5856.0, "A")).thenReturn(cityufs);

        // action
        List<CityUfDTO> result = cityufService.findAllCityUfByLongitudeAndStatus(5856.0, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityUfListWhenFindAllCityUfByDateCreatedAndStatus() {
        // scenario
        List<CityUf> cityufs = Arrays.asList(
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now()
        );

        Mockito.when(cityufRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(cityufs);

        // action
        List<CityUfDTO> result = cityufService.findAllCityUfByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityUfListWhenFindAllCityUfByDateUpdatedAndStatus() {
        // scenario
        List<CityUf> cityufs = Arrays.asList(
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now(),
            CityUfModelBuilder.newCityUfModelTestBuilder().now()
        );

        Mockito.when(cityufRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(cityufs);

        // action
        List<CityUfDTO> result = cityufService.findAllCityUfByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentCityUfDTOWhenFindCityUfByIdAndStatus() {
        // scenario
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        Mockito.when(cityufRepositoryMock.loadMaxIdByIdAndStatus(21575L, "A")).thenReturn(1L);
        Mockito.when(cityufRepositoryMock.findById(1L)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByIdAndStatus(21575L, "A");

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class,result);
    }
    @Test
    public void shouldReturnCityUfNotFoundExceptionWhenNonExistenceCityUfIdAndStatus() {
        // scenario
        Mockito.when(cityufRepositoryMock.loadMaxIdByIdAndStatus(21575L, "A")).thenReturn(0L);
        Mockito.when(cityufRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByIdAndStatus(21575L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentCityUfDTOWhenFindCityUfByCityIdAndStatus() {
        // scenario
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        Mockito.when(cityufRepositoryMock.loadMaxIdByCityIdAndStatus(71556L, "A")).thenReturn(1L);
        Mockito.when(cityufRepositoryMock.findById(1L)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByCityIdAndStatus(71556L, "A");

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class,result);
    }
    @Test
    public void shouldReturnCityUfNotFoundExceptionWhenNonExistenceCityUfCityIdAndStatus() {
        // scenario
        Mockito.when(cityufRepositoryMock.loadMaxIdByCityIdAndStatus(71556L, "A")).thenReturn(0L);
        Mockito.when(cityufRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByCityIdAndStatus(71556L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_CITYID));
    }
    @Test
    public void shouldReturnExistentCityUfDTOWhenFindCityUfByUfIdAndStatus() {
        // scenario
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        Mockito.when(cityufRepositoryMock.loadMaxIdByUfIdAndStatus("IFv2OckaJ78LOHUKtICl8cxUAE87fdmGsOUjTonAcGkxubMg0g", "A")).thenReturn(1L);
        Mockito.when(cityufRepositoryMock.findById(1L)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByUfIdAndStatus("IFv2OckaJ78LOHUKtICl8cxUAE87fdmGsOUjTonAcGkxubMg0g", "A");

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class,result);
    }
    @Test
    public void shouldReturnCityUfNotFoundExceptionWhenNonExistenceCityUfUfIdAndStatus() {
        // scenario
        Mockito.when(cityufRepositoryMock.loadMaxIdByUfIdAndStatus("IFv2OckaJ78LOHUKtICl8cxUAE87fdmGsOUjTonAcGkxubMg0g", "A")).thenReturn(0L);
        Mockito.when(cityufRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByUfIdAndStatus("IFv2OckaJ78LOHUKtICl8cxUAE87fdmGsOUjTonAcGkxubMg0g", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_UFID));
    }
    @Test
    public void shouldReturnExistentCityUfDTOWhenFindCityUfByDddAndStatus() {
        // scenario
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        Mockito.when(cityufRepositoryMock.loadMaxIdByDddAndStatus("HE7mlsK7GA0qg4aMsbQXGG744GG9pjtAcSTHI5HcLVRQGrgFc3", "A")).thenReturn(1L);
        Mockito.when(cityufRepositoryMock.findById(1L)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByDddAndStatus("HE7mlsK7GA0qg4aMsbQXGG744GG9pjtAcSTHI5HcLVRQGrgFc3", "A");

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class,result);
    }
    @Test
    public void shouldReturnCityUfNotFoundExceptionWhenNonExistenceCityUfDddAndStatus() {
        // scenario
        Mockito.when(cityufRepositoryMock.loadMaxIdByDddAndStatus("HE7mlsK7GA0qg4aMsbQXGG744GG9pjtAcSTHI5HcLVRQGrgFc3", "A")).thenReturn(0L);
        Mockito.when(cityufRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByDddAndStatus("HE7mlsK7GA0qg4aMsbQXGG744GG9pjtAcSTHI5HcLVRQGrgFc3", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_DDD));
    }
    @Test
    public void shouldReturnExistentCityUfDTOWhenFindCityUfByLatitudeAndStatus() {
        // scenario
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        Mockito.when(cityufRepositoryMock.loadMaxIdByLatitudeAndStatus(5681.0, "A")).thenReturn(1L);
        Mockito.when(cityufRepositoryMock.findById(1L)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByLatitudeAndStatus(5681.0, "A");

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class,result);
    }
    @Test
    public void shouldReturnCityUfNotFoundExceptionWhenNonExistenceCityUfLatitudeAndStatus() {
        // scenario
        Mockito.when(cityufRepositoryMock.loadMaxIdByLatitudeAndStatus(5681.0, "A")).thenReturn(0L);
        Mockito.when(cityufRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByLatitudeAndStatus(5681.0, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_LATITUDE));
    }
    @Test
    public void shouldReturnExistentCityUfDTOWhenFindCityUfByLongitudeAndStatus() {
        // scenario
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder().now());
        Mockito.when(cityufRepositoryMock.loadMaxIdByLongitudeAndStatus(4306.0, "A")).thenReturn(1L);
        Mockito.when(cityufRepositoryMock.findById(1L)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByLongitudeAndStatus(4306.0, "A");

        // validate
        Assertions.assertInstanceOf(CityUfDTO.class,result);
    }
    @Test
    public void shouldReturnCityUfNotFoundExceptionWhenNonExistenceCityUfLongitudeAndStatus() {
        // scenario
        Mockito.when(cityufRepositoryMock.loadMaxIdByLongitudeAndStatus(4306.0, "A")).thenReturn(0L);
        Mockito.when(cityufRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByLongitudeAndStatus(4306.0, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_LONGITUDE));
    }

    @Test
    public void shouldReturnCityUfDTOWhenUpdateExistingCityIdById() {
        // scenario
        Long cityIdUpdateMock = 53465L;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(cityufRepositoryMock.findById(420L)).thenReturn(cityufModelMock);
        Mockito.doNothing().when(cityufRepositoryMock).updateCityIdById(420L, cityIdUpdateMock);

        // action
        cityufService.updateCityIdById(420L, cityIdUpdateMock);

        // validate
        Mockito.verify(cityufRepositoryMock,Mockito.times(1)).updateCityIdById(420L, cityIdUpdateMock);
    }
    @Test
    public void shouldReturnCityUfDTOWhenUpdateExistingUfIdById() {
        // scenario
        String ufIdUpdateMock = "3oYlEO6oP2C3gBd3LNYLWYSGV1HafBIDPB0UNEJlbfmU1EIoko";
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(cityufRepositoryMock.findById(420L)).thenReturn(cityufModelMock);
        Mockito.doNothing().when(cityufRepositoryMock).updateUfIdById(420L, ufIdUpdateMock);

        // action
        cityufService.updateUfIdById(420L, ufIdUpdateMock);

        // validate
        Mockito.verify(cityufRepositoryMock,Mockito.times(1)).updateUfIdById(420L, ufIdUpdateMock);
    }
    @Test
    public void shouldReturnCityUfDTOWhenUpdateExistingDddById() {
        // scenario
        String dddUpdateMock = "6y6RxDU7eVF3z22Yj05TVXofGM1LLIO3GlgxBIdEeT6cQAqNct";
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(cityufRepositoryMock.findById(420L)).thenReturn(cityufModelMock);
        Mockito.doNothing().when(cityufRepositoryMock).updateDddById(420L, dddUpdateMock);

        // action
        cityufService.updateDddById(420L, dddUpdateMock);

        // validate
        Mockito.verify(cityufRepositoryMock,Mockito.times(1)).updateDddById(420L, dddUpdateMock);
    }
    @Test
    public void shouldReturnCityUfDTOWhenUpdateExistingLatitudeById() {
        // scenario
        Double latitudeUpdateMock = 2052.0;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(cityufRepositoryMock.findById(420L)).thenReturn(cityufModelMock);
        Mockito.doNothing().when(cityufRepositoryMock).updateLatitudeById(420L, latitudeUpdateMock);

        // action
        cityufService.updateLatitudeById(420L, latitudeUpdateMock);

        // validate
        Mockito.verify(cityufRepositoryMock,Mockito.times(1)).updateLatitudeById(420L, latitudeUpdateMock);
    }
    @Test
    public void shouldReturnCityUfDTOWhenUpdateExistingLongitudeById() {
        // scenario
        Double longitudeUpdateMock = 2.0;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(cityufRepositoryMock.findById(420L)).thenReturn(cityufModelMock);
        Mockito.doNothing().when(cityufRepositoryMock).updateLongitudeById(420L, longitudeUpdateMock);

        // action
        cityufService.updateLongitudeById(420L, longitudeUpdateMock);

        // validate
        Mockito.verify(cityufRepositoryMock,Mockito.times(1)).updateLongitudeById(420L, longitudeUpdateMock);
    }



    @Test
    public void showReturnExistingCityUfDTOWhenFindCityUfByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 38663L;
        Long maxIdMock = 1972L;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(cityufRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(cityufRepositoryMock.findById(maxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnCityUfNotFoundExceptionWhenNonExistenceFindCityUfByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 38663L;
        Long noMaxIdMock = 0L;
        Optional<CityUf> cityufModelMock = Optional.empty();
        Mockito.when(cityufRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(cityufRepositoryMock.findById(noMaxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingCityUfDTOWhenFindCityUfByCityIdAndStatusActiveAnonimous() {
        // scenario
        Long cityIdMock = 58202L;
        Long maxIdMock = 1972L;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                .cityId(cityIdMock)
                .now()
        );
        Mockito.when(cityufRepositoryMock.loadMaxIdByCityIdAndStatus(cityIdMock, "A")).thenReturn(maxIdMock);
        Mockito.when(cityufRepositoryMock.findById(maxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByCityIdAndStatus(cityIdMock);

        // validate
        Assertions.assertEquals(cityIdMock, result.getCityId());

    }
    @Test
    public void showReturnCityUfNotFoundExceptionWhenNonExistenceFindCityUfByCityIdAndStatusActiveAnonimous() {
        // scenario
        Long cityIdMock = 58202L;
        Long noMaxIdMock = 0L;
        Optional<CityUf> cityufModelMock = Optional.empty();
        Mockito.when(cityufRepositoryMock.loadMaxIdByCityIdAndStatus(cityIdMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(cityufRepositoryMock.findById(noMaxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByCityIdAndStatus(cityIdMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_CITYID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingCityUfDTOWhenFindCityUfByUfIdAndStatusActiveAnonimous() {
        // scenario
        String ufIdMock = "IPKXMCfO0NsyBtdavO9F78W4P6i2XRfhyB0jBsD9n4xk6bYQBC";
        Long maxIdMock = 1972L;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                .ufId(ufIdMock)
                .now()
        );
        Mockito.when(cityufRepositoryMock.loadMaxIdByUfIdAndStatus(ufIdMock, "A")).thenReturn(maxIdMock);
        Mockito.when(cityufRepositoryMock.findById(maxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByUfIdAndStatus(ufIdMock);

        // validate
        Assertions.assertEquals(ufIdMock, result.getUfId());

    }
    @Test
    public void showReturnCityUfNotFoundExceptionWhenNonExistenceFindCityUfByUfIdAndStatusActiveAnonimous() {
        // scenario
        String ufIdMock = "IPKXMCfO0NsyBtdavO9F78W4P6i2XRfhyB0jBsD9n4xk6bYQBC";
        Long noMaxIdMock = 0L;
        Optional<CityUf> cityufModelMock = Optional.empty();
        Mockito.when(cityufRepositoryMock.loadMaxIdByUfIdAndStatus(ufIdMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(cityufRepositoryMock.findById(noMaxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByUfIdAndStatus(ufIdMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_UFID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingCityUfDTOWhenFindCityUfByDddAndStatusActiveAnonimous() {
        // scenario
        String dddMock = "vmP1pMVshku39iILS00M5ObOhbkEIzIEFJkCWdLxFI7CSHEfAD";
        Long maxIdMock = 1972L;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                .ddd(dddMock)
                .now()
        );
        Mockito.when(cityufRepositoryMock.loadMaxIdByDddAndStatus(dddMock, "A")).thenReturn(maxIdMock);
        Mockito.when(cityufRepositoryMock.findById(maxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByDddAndStatus(dddMock);

        // validate
        Assertions.assertEquals(dddMock, result.getDdd());

    }
    @Test
    public void showReturnCityUfNotFoundExceptionWhenNonExistenceFindCityUfByDddAndStatusActiveAnonimous() {
        // scenario
        String dddMock = "vmP1pMVshku39iILS00M5ObOhbkEIzIEFJkCWdLxFI7CSHEfAD";
        Long noMaxIdMock = 0L;
        Optional<CityUf> cityufModelMock = Optional.empty();
        Mockito.when(cityufRepositoryMock.loadMaxIdByDddAndStatus(dddMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(cityufRepositoryMock.findById(noMaxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByDddAndStatus(dddMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_DDD));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingCityUfDTOWhenFindCityUfByLatitudeAndStatusActiveAnonimous() {
        // scenario
        Double latitudeMock = 2863.0;
        Long maxIdMock = 1972L;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                .latitude(latitudeMock)
                .now()
        );
        Mockito.when(cityufRepositoryMock.loadMaxIdByLatitudeAndStatus(latitudeMock, "A")).thenReturn(maxIdMock);
        Mockito.when(cityufRepositoryMock.findById(maxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByLatitudeAndStatus(latitudeMock);

        // validate
        Assertions.assertEquals(latitudeMock, result.getLatitude());

    }
    @Test
    public void showReturnCityUfNotFoundExceptionWhenNonExistenceFindCityUfByLatitudeAndStatusActiveAnonimous() {
        // scenario
        Double latitudeMock = 2863.0;
        Long noMaxIdMock = 0L;
        Optional<CityUf> cityufModelMock = Optional.empty();
        Mockito.when(cityufRepositoryMock.loadMaxIdByLatitudeAndStatus(latitudeMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(cityufRepositoryMock.findById(noMaxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByLatitudeAndStatus(latitudeMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_LATITUDE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingCityUfDTOWhenFindCityUfByLongitudeAndStatusActiveAnonimous() {
        // scenario
        Double longitudeMock = 2670.0;
        Long maxIdMock = 1972L;
        Optional<CityUf> cityufModelMock = Optional.ofNullable(CityUfModelBuilder.newCityUfModelTestBuilder()
                .longitude(longitudeMock)
                .now()
        );
        Mockito.when(cityufRepositoryMock.loadMaxIdByLongitudeAndStatus(longitudeMock, "A")).thenReturn(maxIdMock);
        Mockito.when(cityufRepositoryMock.findById(maxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfDTO result = cityufService.findCityUfByLongitudeAndStatus(longitudeMock);

        // validate
        Assertions.assertEquals(longitudeMock, result.getLongitude());

    }
    @Test
    public void showReturnCityUfNotFoundExceptionWhenNonExistenceFindCityUfByLongitudeAndStatusActiveAnonimous() {
        // scenario
        Double longitudeMock = 2670.0;
        Long noMaxIdMock = 0L;
        Optional<CityUf> cityufModelMock = Optional.empty();
        Mockito.when(cityufRepositoryMock.loadMaxIdByLongitudeAndStatus(longitudeMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(cityufRepositoryMock.findById(noMaxIdMock)).thenReturn(cityufModelMock);

        // action
        CityUfNotFoundException exception = Assertions.assertThrows(CityUfNotFoundException.class,
                ()->cityufService.findCityUfByLongitudeAndStatus(longitudeMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITYUF_NOTFOUND_WITH_LONGITUDE));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

