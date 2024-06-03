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
import br.com.jcv.brcities.corelayer.builder.CityDTOBuilder;
import br.com.jcv.brcities.corelayer.builder.CityModelBuilder;
import br.com.jcv.brcities.corelayer.dto.CityDTO;
import br.com.jcv.brcities.corelayer.exception.CityNotFoundException;
import br.com.jcv.brcities.corelayer.model.City;
import br.com.jcv.brcities.corelayer.repository.CityRepository;
import br.com.jcv.brcities.corelayer.service.CityService;
import br.com.jcv.brcities.corelayer.constantes.CityConstantes;
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
public class CityServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String CITY_NOTFOUND_WITH_ID = "City não encontrada com id = ";
    public static final String CITY_NOTFOUND_WITH_NAME = "City não encontrada com name = ";
    public static final String CITY_NOTFOUND_WITH_STATUS = "City não encontrada com status = ";
    public static final String CITY_NOTFOUND_WITH_DATECREATED = "City não encontrada com dateCreated = ";
    public static final String CITY_NOTFOUND_WITH_DATEUPDATED = "City não encontrada com dateUpdated = ";


    @Mock
    private CityRepository cityRepositoryMock;

    @InjectMocks
    private CityService cityService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        cityService = new CityServiceImpl();
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
    public void shouldReturnListOfCityWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 83878L;
        String name = "IhrFe2vXU7x0Qz0PH7N315bARTRXG8Y0RqKVUvuUMdp070HVYr";
        String status = "zQe8d3ucjeP6hOHFk4zBGbkv1RnFK9EhF5O6QIsvUKMDhO713T";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<City> citysFromRepository = new ArrayList<>();
        citysFromRepository.add(CityModelBuilder.newCityModelTestBuilder().now());
        citysFromRepository.add(CityModelBuilder.newCityModelTestBuilder().now());
        citysFromRepository.add(CityModelBuilder.newCityModelTestBuilder().now());
        citysFromRepository.add(CityModelBuilder.newCityModelTestBuilder().now());

        Mockito.when(cityRepositoryMock.findCityByFilter(
            id,
            name,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(citysFromRepository);

        // action
        List<CityDTO> result = cityService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithCityListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 30386L;
        String name = "fQ35bP7Ih8GyqXsL2JN6JcpqebKgmOq0b6LDC4qJmELAwk8t6U";
        String status = "uT3mG95vG4BU8wPVxWECjwDNsEPgv1z84Lr1C7XQ4qjyGEvpn2";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<City> citysFromRepository = new ArrayList<>();
        citysFromRepository.add(CityModelBuilder.newCityModelTestBuilder().now());
        citysFromRepository.add(CityModelBuilder.newCityModelTestBuilder().now());
        citysFromRepository.add(CityModelBuilder.newCityModelTestBuilder().now());
        citysFromRepository.add(CityModelBuilder.newCityModelTestBuilder().now());

        List<CityDTO> citysFiltered = citysFromRepository
                .stream()
                .map(m->cityService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageCityItems", citysFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<City> pagedResponse =
                new PageImpl<>(citysFromRepository,
                        pageableMock,
                        citysFromRepository.size());

        Mockito.when(cityRepositoryMock.findCityByFilter(pageableMock,
            id,
            name,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = cityService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<CityDTO> citysResult = (List<CityDTO>) result.get("pageCityItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, citysResult.size());
    }


    @Test
    public void showReturnListOfCityWhenAskedForFindAllByStatus() {
        // scenario
        List<City> listOfCityModelMock = new ArrayList<>();
        listOfCityModelMock.add(CityModelBuilder.newCityModelTestBuilder().now());
        listOfCityModelMock.add(CityModelBuilder.newCityModelTestBuilder().now());

        Mockito.when(cityRepositoryMock.findAllByStatus("A")).thenReturn(listOfCityModelMock);

        // action
        List<CityDTO> listOfCitys = cityService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfCitys.isEmpty());
        Assertions.assertEquals(2, listOfCitys.size());
    }
    @Test
    public void shouldReturnCityNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 67605L;
        Optional<City> cityNonExistentMock = Optional.empty();
        Mockito.when(cityRepositoryMock.findById(idMock)).thenReturn(cityNonExistentMock);

        // action
        CityNotFoundException exception = Assertions.assertThrows(CityNotFoundException.class,
                ()->cityService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITY_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowCityNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 33835L;
        Mockito.when(cityRepositoryMock.findById(idMock))
                .thenThrow(new CityNotFoundException(CITY_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                CITY_NOTFOUND_WITH_ID ));

        // action
        CityNotFoundException exception = Assertions.assertThrows(CityNotFoundException.class,
                ()->cityService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITY_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnCityDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 24400L;
        Optional<City> cityModelMock = Optional.ofNullable(
                CityModelBuilder.newCityModelTestBuilder()
                        .id(idMock)
                        .name("AcJPOlFIOu304oMXH07WxdcvT1NCizWOjsE2tT1YPrKkPlDkYj")

                        .status("X")
                        .now()
        );
        City cityToSaveMock = cityModelMock.orElse(null);
        City citySavedMck = CityModelBuilder.newCityModelTestBuilder()
                        .id(85877L)
                        .name("idSoKSpk603NK8JnXNj9wnEu7RfqIBiudWpj149pYdgoOprcob")

                        .status("A")
                        .now();
        Mockito.when(cityRepositoryMock.findById(idMock)).thenReturn(cityModelMock);
        Mockito.when(cityRepositoryMock.save(cityToSaveMock)).thenReturn(citySavedMck);

        // action
        CityDTO result = cityService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchCityByAnyNonExistenceIdAndReturnCityNotFoundException() {
        // scenario
        Mockito.when(cityRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        CityNotFoundException exception = Assertions.assertThrows(CityNotFoundException.class,
                ()-> cityService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITY_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchCityByIdAndReturnDTO() {
        // scenario
        Optional<City> cityModelMock = Optional.ofNullable(CityModelBuilder.newCityModelTestBuilder()
                .id(1015L)
                .name("H7EjVvKgeLdtJifhWtLTP0OTbPnhsU3HKoERNrtNoFcbS02EYE")

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(cityRepositoryMock.findById(Mockito.anyLong())).thenReturn(cityModelMock);

        // action
        CityDTO result = cityService.findById(1L);

        // validate
        Assertions.assertInstanceOf(CityDTO.class,result);
    }
    @Test
    public void shouldDeleteCityByIdWithSucess() {
        // scenario
        Optional<City> city = Optional.ofNullable(CityModelBuilder.newCityModelTestBuilder().id(1L).now());
        Mockito.when(cityRepositoryMock.findById(Mockito.anyLong())).thenReturn(city);

        // action
        cityService.delete(1L);

        // validate
        Mockito.verify(cityRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceCityShouldReturnCityNotFoundException() {
        // scenario
        Mockito.when(cityRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        CityNotFoundException exception = Assertions.assertThrows(
                CityNotFoundException.class, () -> cityService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITY_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingCityWithSucess() {
        // scenario
        CityDTO cityDTOMock = CityDTOBuilder.newCityDTOTestBuilder()
                .id(88255L)
                .name("rgTTYc812134tbfnhC0IaX6T6QPxbu0c2A30J8czK05nXD3Jxo")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        City cityMock = CityModelBuilder.newCityModelTestBuilder()
                .id(cityDTOMock.getId())
                .name(cityDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        City citySavedMock = CityModelBuilder.newCityModelTestBuilder()
                .id(cityDTOMock.getId())
                .name(cityDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(cityRepositoryMock.save(cityMock)).thenReturn(citySavedMock);

        // action
        CityDTO citySaved = cityService.salvar(cityDTOMock);

        // validate
        Assertions.assertInstanceOf(CityDTO.class, citySaved);
        Assertions.assertNotNull(citySaved.getId());
    }

    @Test
    public void ShouldSaveNewCityWithSucess() {
        // scenario
        CityDTO cityDTOMock = CityDTOBuilder.newCityDTOTestBuilder()
                .id(null)
                .name("fVBkRms0jCsniEotbI02jSy1eTHePFJopw0I45qcF0B6iWjJRw")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        City cityModelMock = CityModelBuilder.newCityModelTestBuilder()
                .id(null)
                .name(cityDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        City citySavedMock = CityModelBuilder.newCityModelTestBuilder()
                .id(501L)
                .name(cityDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(cityRepositoryMock.save(cityModelMock)).thenReturn(citySavedMock);

        // action
        CityDTO citySaved = cityService.salvar(cityDTOMock);

        // validate
        Assertions.assertInstanceOf(CityDTO.class, citySaved);
        Assertions.assertNotNull(citySaved.getId());
        Assertions.assertEquals("P",citySaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapCityDTOMock = new HashMap<>();
        mapCityDTOMock.put(CityConstantes.NAME,"US7U0pVRT5uLeNRV6GjbhN5sRYyRY3AyRQOdSXCW4oc3NOxpg0");
        mapCityDTOMock.put(CityConstantes.STATUS,"QmXGh771ozCzwpGbwtp94hFPRl3BRSxdemum7ER0V29yun0IIg");


        Optional<City> cityModelMock = Optional.ofNullable(
                CityModelBuilder.newCityModelTestBuilder()
                        .id(26715L)
                        .name("WRMp6ftvflbO5J8xmLSQeUmktCW2YUmmu6h4SXcm8flNW02Wkh")
                        .status("Vov29LVr1sUjEsvdNH4skTtVsf7HEid5f3sWhYb1TH6Uhu30dX")

                        .now()
        );

        Mockito.when(cityRepositoryMock.findById(1L)).thenReturn(cityModelMock);

        // action
        boolean executed = cityService.partialUpdate(1L, mapCityDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnCityNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapCityDTOMock = new HashMap<>();
        mapCityDTOMock.put(CityConstantes.NAME,"H1nC7OyRWBWwa7yejoLaggDQyAuLQGztqOwjuVLRk0qnWLzX72");
        mapCityDTOMock.put(CityConstantes.STATUS,"zQerFFpFeFLpNsX2eiU5SnFX796xo9TwT86hjI1QQEBdJOT44o");


        Mockito.when(cityRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        CityNotFoundException exception = Assertions.assertThrows(CityNotFoundException.class,
                ()->cityService.partialUpdate(1L, mapCityDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("City não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnCityListWhenFindAllCityByIdAndStatus() {
        // scenario
        List<City> citys = Arrays.asList(
            CityModelBuilder.newCityModelTestBuilder().now(),
            CityModelBuilder.newCityModelTestBuilder().now(),
            CityModelBuilder.newCityModelTestBuilder().now()
        );

        Mockito.when(cityRepositoryMock.findAllByIdAndStatus(28600L, "A")).thenReturn(citys);

        // action
        List<CityDTO> result = cityService.findAllCityByIdAndStatus(28600L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityListWhenFindAllCityByNameAndStatus() {
        // scenario
        List<City> citys = Arrays.asList(
            CityModelBuilder.newCityModelTestBuilder().now(),
            CityModelBuilder.newCityModelTestBuilder().now(),
            CityModelBuilder.newCityModelTestBuilder().now()
        );

        Mockito.when(cityRepositoryMock.findAllByNameAndStatus("YuLqtxr8XVKKwS0OSACeQ90887MnXlOahv4BwrVWRKSKDpE5E0", "A")).thenReturn(citys);

        // action
        List<CityDTO> result = cityService.findAllCityByNameAndStatus("YuLqtxr8XVKKwS0OSACeQ90887MnXlOahv4BwrVWRKSKDpE5E0", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityListWhenFindAllCityByDateCreatedAndStatus() {
        // scenario
        List<City> citys = Arrays.asList(
            CityModelBuilder.newCityModelTestBuilder().now(),
            CityModelBuilder.newCityModelTestBuilder().now(),
            CityModelBuilder.newCityModelTestBuilder().now()
        );

        Mockito.when(cityRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(citys);

        // action
        List<CityDTO> result = cityService.findAllCityByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnCityListWhenFindAllCityByDateUpdatedAndStatus() {
        // scenario
        List<City> citys = Arrays.asList(
            CityModelBuilder.newCityModelTestBuilder().now(),
            CityModelBuilder.newCityModelTestBuilder().now(),
            CityModelBuilder.newCityModelTestBuilder().now()
        );

        Mockito.when(cityRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(citys);

        // action
        List<CityDTO> result = cityService.findAllCityByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentCityDTOWhenFindCityByIdAndStatus() {
        // scenario
        Optional<City> cityModelMock = Optional.ofNullable(CityModelBuilder.newCityModelTestBuilder().now());
        Mockito.when(cityRepositoryMock.loadMaxIdByIdAndStatus(15873L, "A")).thenReturn(1L);
        Mockito.when(cityRepositoryMock.findById(1L)).thenReturn(cityModelMock);

        // action
        CityDTO result = cityService.findCityByIdAndStatus(15873L, "A");

        // validate
        Assertions.assertInstanceOf(CityDTO.class,result);
    }
    @Test
    public void shouldReturnCityNotFoundExceptionWhenNonExistenceCityIdAndStatus() {
        // scenario
        Mockito.when(cityRepositoryMock.loadMaxIdByIdAndStatus(15873L, "A")).thenReturn(0L);
        Mockito.when(cityRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        CityNotFoundException exception = Assertions.assertThrows(CityNotFoundException.class,
                ()->cityService.findCityByIdAndStatus(15873L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITY_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentCityDTOWhenFindCityByNameAndStatus() {
        // scenario
        Optional<City> cityModelMock = Optional.ofNullable(CityModelBuilder.newCityModelTestBuilder().now());
        Mockito.when(cityRepositoryMock.loadMaxIdByNameAndStatus("jmYrb7GTFn67lRxXtKbnH7U2D4lXRLxVkb0GQnlNgAUNeQzYA6", "A")).thenReturn(1L);
        Mockito.when(cityRepositoryMock.findById(1L)).thenReturn(cityModelMock);

        // action
        CityDTO result = cityService.findCityByNameAndStatus("jmYrb7GTFn67lRxXtKbnH7U2D4lXRLxVkb0GQnlNgAUNeQzYA6", "A");

        // validate
        Assertions.assertInstanceOf(CityDTO.class,result);
    }
    @Test
    public void shouldReturnCityNotFoundExceptionWhenNonExistenceCityNameAndStatus() {
        // scenario
        Mockito.when(cityRepositoryMock.loadMaxIdByNameAndStatus("jmYrb7GTFn67lRxXtKbnH7U2D4lXRLxVkb0GQnlNgAUNeQzYA6", "A")).thenReturn(0L);
        Mockito.when(cityRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        CityNotFoundException exception = Assertions.assertThrows(CityNotFoundException.class,
                ()->cityService.findCityByNameAndStatus("jmYrb7GTFn67lRxXtKbnH7U2D4lXRLxVkb0GQnlNgAUNeQzYA6", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITY_NOTFOUND_WITH_NAME));
    }

    @Test
    public void shouldReturnCityDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "NqSfJCTrdW7xxK3h4T60pLQeDcOduSdSPs740uwtsLQo00xRTp";
        Optional<City> cityModelMock = Optional.ofNullable(CityModelBuilder.newCityModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(cityRepositoryMock.findById(420L)).thenReturn(cityModelMock);
        Mockito.doNothing().when(cityRepositoryMock).updateNameById(420L, nameUpdateMock);

        // action
        cityService.updateNameById(420L, nameUpdateMock);

        // validate
        Mockito.verify(cityRepositoryMock,Mockito.times(1)).updateNameById(420L, nameUpdateMock);
    }



    @Test
    public void showReturnExistingCityDTOWhenFindCityByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 54464L;
        Long maxIdMock = 1972L;
        Optional<City> cityModelMock = Optional.ofNullable(CityModelBuilder.newCityModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(cityRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(cityRepositoryMock.findById(maxIdMock)).thenReturn(cityModelMock);

        // action
        CityDTO result = cityService.findCityByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnCityNotFoundExceptionWhenNonExistenceFindCityByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 54464L;
        Long noMaxIdMock = 0L;
        Optional<City> cityModelMock = Optional.empty();
        Mockito.when(cityRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(cityRepositoryMock.findById(noMaxIdMock)).thenReturn(cityModelMock);

        // action
        CityNotFoundException exception = Assertions.assertThrows(CityNotFoundException.class,
                ()->cityService.findCityByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITY_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingCityDTOWhenFindCityByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "h5LWT4ExghLpttXNR1Hp62LBPU65TM3KrjvhzXIvf0qFWETMyk";
        Long maxIdMock = 1972L;
        Optional<City> cityModelMock = Optional.ofNullable(CityModelBuilder.newCityModelTestBuilder()
                .name(nameMock)
                .now()
        );
        Mockito.when(cityRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(maxIdMock);
        Mockito.when(cityRepositoryMock.findById(maxIdMock)).thenReturn(cityModelMock);

        // action
        CityDTO result = cityService.findCityByNameAndStatus(nameMock);

        // validate
        Assertions.assertEquals(nameMock, result.getName());

    }
    @Test
    public void showReturnCityNotFoundExceptionWhenNonExistenceFindCityByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "h5LWT4ExghLpttXNR1Hp62LBPU65TM3KrjvhzXIvf0qFWETMyk";
        Long noMaxIdMock = 0L;
        Optional<City> cityModelMock = Optional.empty();
        Mockito.when(cityRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(cityRepositoryMock.findById(noMaxIdMock)).thenReturn(cityModelMock);

        // action
        CityNotFoundException exception = Assertions.assertThrows(CityNotFoundException.class,
                ()->cityService.findCityByNameAndStatus(nameMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(CITY_NOTFOUND_WITH_NAME));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

