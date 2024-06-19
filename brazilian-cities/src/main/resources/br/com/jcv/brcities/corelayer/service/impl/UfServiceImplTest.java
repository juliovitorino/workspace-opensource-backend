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
import br.com.jcv.brcities.corelayer.builder.UfDTOBuilder;
import br.com.jcv.brcities.corelayer.builder.UfModelBuilder;
import br.com.jcv.brcities.corelayer.dto.UfDTO;
import br.com.jcv.brcities.corelayer.exception.UfNotFoundException;
import br.com.jcv.brcities.corelayer.model.Uf;
import br.com.jcv.brcities.corelayer.repository.UfRepository;
import br.com.jcv.brcities.corelayer.service.UfService;
import br.com.jcv.brcities.corelayer.constantes.UfConstantes;
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
public class UfServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String UF_NOTFOUND_WITH_ID = "Uf não encontrada com id = ";
    public static final String UF_NOTFOUND_WITH_NAME = "Uf não encontrada com name = ";
    public static final String UF_NOTFOUND_WITH_STATUS = "Uf não encontrada com status = ";
    public static final String UF_NOTFOUND_WITH_DATECREATED = "Uf não encontrada com dateCreated = ";
    public static final String UF_NOTFOUND_WITH_DATEUPDATED = "Uf não encontrada com dateUpdated = ";


    @Mock
    private UfRepository ufRepositoryMock;

    @InjectMocks
    private UfService ufService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        ufService = new UfServiceImpl();
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
    public void shouldReturnListOfUfWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 77482L;
        String name = "rJNWFoAfTlM3podX5kKzE8AnAP8a819c70QqoX5VhzpjHs8hwH";
        String status = "xTCh0BwsP3QUdfOFLanNjWOrbvFUD0J4lABfA234cN0naEOCoe";
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

        List<Uf> ufsFromRepository = new ArrayList<>();
        ufsFromRepository.add(UfModelBuilder.newUfModelTestBuilder().now());
        ufsFromRepository.add(UfModelBuilder.newUfModelTestBuilder().now());
        ufsFromRepository.add(UfModelBuilder.newUfModelTestBuilder().now());
        ufsFromRepository.add(UfModelBuilder.newUfModelTestBuilder().now());

        Mockito.when(ufRepositoryMock.findUfByFilter(
            id,
            name,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(ufsFromRepository);

        // action
        List<UfDTO> result = ufService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithUfListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 80004L;
        String name = "iuJH03PJAIOU4FvE1pORATaIjHMWcXcpY1Ao0QIDD8tNWdqOlS";
        String status = "NSGiLterd6bdk0VNT0qn80CHkOtQRu07e0H5t501bkRFaIux70";
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

        List<Uf> ufsFromRepository = new ArrayList<>();
        ufsFromRepository.add(UfModelBuilder.newUfModelTestBuilder().now());
        ufsFromRepository.add(UfModelBuilder.newUfModelTestBuilder().now());
        ufsFromRepository.add(UfModelBuilder.newUfModelTestBuilder().now());
        ufsFromRepository.add(UfModelBuilder.newUfModelTestBuilder().now());

        List<UfDTO> ufsFiltered = ufsFromRepository
                .stream()
                .map(m->ufService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageUfItems", ufsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<Uf> pagedResponse =
                new PageImpl<>(ufsFromRepository,
                        pageableMock,
                        ufsFromRepository.size());

        Mockito.when(ufRepositoryMock.findUfByFilter(pageableMock,
            id,
            name,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = ufService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<UfDTO> ufsResult = (List<UfDTO>) result.get("pageUfItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, ufsResult.size());
    }


    @Test
    public void showReturnListOfUfWhenAskedForFindAllByStatus() {
        // scenario
        List<Uf> listOfUfModelMock = new ArrayList<>();
        listOfUfModelMock.add(UfModelBuilder.newUfModelTestBuilder().now());
        listOfUfModelMock.add(UfModelBuilder.newUfModelTestBuilder().now());

        Mockito.when(ufRepositoryMock.findAllByStatus("A")).thenReturn(listOfUfModelMock);

        // action
        List<UfDTO> listOfUfs = ufService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfUfs.isEmpty());
        Assertions.assertEquals(2, listOfUfs.size());
    }
    @Test
    public void shouldReturnUfNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 43406L;
        Optional<Uf> ufNonExistentMock = Optional.empty();
        Mockito.when(ufRepositoryMock.findById(idMock)).thenReturn(ufNonExistentMock);

        // action
        UfNotFoundException exception = Assertions.assertThrows(UfNotFoundException.class,
                ()->ufService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(UF_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowUfNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 7247L;
        Mockito.when(ufRepositoryMock.findById(idMock))
                .thenThrow(new UfNotFoundException(UF_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                UF_NOTFOUND_WITH_ID ));

        // action
        UfNotFoundException exception = Assertions.assertThrows(UfNotFoundException.class,
                ()->ufService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(UF_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnUfDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 26762L;
        Optional<Uf> ufModelMock = Optional.ofNullable(
                UfModelBuilder.newUfModelTestBuilder()
                        .id(idMock)
                        .name("Xt9WbIk8XTLnpzUXHpNYMXS6BSKjK42y7P99FyRS6oNEYfwWkA")

                        .status("X")
                        .now()
        );
        Uf ufToSaveMock = ufModelMock.orElse(null);
        Uf ufSavedMck = UfModelBuilder.newUfModelTestBuilder()
                        .id(18515L)
                        .name("rja0135BGibnrnoDSRQqrFpWKKLbwcf2w2vrS7b31bWz34NTMc")

                        .status("A")
                        .now();
        Mockito.when(ufRepositoryMock.findById(idMock)).thenReturn(ufModelMock);
        Mockito.when(ufRepositoryMock.save(ufToSaveMock)).thenReturn(ufSavedMck);

        // action
        UfDTO result = ufService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchUfByAnyNonExistenceIdAndReturnUfNotFoundException() {
        // scenario
        Mockito.when(ufRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        UfNotFoundException exception = Assertions.assertThrows(UfNotFoundException.class,
                ()-> ufService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(UF_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchUfByIdAndReturnDTO() {
        // scenario
        Optional<Uf> ufModelMock = Optional.ofNullable(UfModelBuilder.newUfModelTestBuilder()
                .id(40467L)
                .name("53XJ6n2z1fYDSYLCgJFEppGFQCvOSn09CuvNt1nt0K13ifCf2I")

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(ufRepositoryMock.findById(Mockito.anyLong())).thenReturn(ufModelMock);

        // action
        UfDTO result = ufService.findById(1L);

        // validate
        Assertions.assertInstanceOf(UfDTO.class,result);
    }
    @Test
    public void shouldDeleteUfByIdWithSucess() {
        // scenario
        Optional<Uf> uf = Optional.ofNullable(UfModelBuilder.newUfModelTestBuilder().id(1L).now());
        Mockito.when(ufRepositoryMock.findById(Mockito.anyLong())).thenReturn(uf);

        // action
        ufService.delete(1L);

        // validate
        Mockito.verify(ufRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceUfShouldReturnUfNotFoundException() {
        // scenario
        Mockito.when(ufRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        UfNotFoundException exception = Assertions.assertThrows(
                UfNotFoundException.class, () -> ufService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(UF_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingUfWithSucess() {
        // scenario
        UfDTO ufDTOMock = UfDTOBuilder.newUfDTOTestBuilder()
                .id(8885L)
                .name("83veFta1hRibWDrX6LQeUE7Sb7wecVBGYM0ACFqcYtsWbwnf9N")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Uf ufMock = UfModelBuilder.newUfModelTestBuilder()
                .id(ufDTOMock.getId())
                .name(ufDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Uf ufSavedMock = UfModelBuilder.newUfModelTestBuilder()
                .id(ufDTOMock.getId())
                .name(ufDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(ufRepositoryMock.save(ufMock)).thenReturn(ufSavedMock);

        // action
        UfDTO ufSaved = ufService.salvar(ufDTOMock);

        // validate
        Assertions.assertInstanceOf(UfDTO.class, ufSaved);
        Assertions.assertNotNull(ufSaved.getId());
    }

    @Test
    public void ShouldSaveNewUfWithSucess() {
        // scenario
        UfDTO ufDTOMock = UfDTOBuilder.newUfDTOTestBuilder()
                .id(null)
                .name("8cTtxbXyQtnwj515toi8j1CBqS928biTbLi3GSh0stm8FXQtW3")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Uf ufModelMock = UfModelBuilder.newUfModelTestBuilder()
                .id(null)
                .name(ufDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Uf ufSavedMock = UfModelBuilder.newUfModelTestBuilder()
                .id(501L)
                .name(ufDTOMock.getName())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(ufRepositoryMock.save(ufModelMock)).thenReturn(ufSavedMock);

        // action
        UfDTO ufSaved = ufService.salvar(ufDTOMock);

        // validate
        Assertions.assertInstanceOf(UfDTO.class, ufSaved);
        Assertions.assertNotNull(ufSaved.getId());
        Assertions.assertEquals("P",ufSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapUfDTOMock = new HashMap<>();
        mapUfDTOMock.put(UfConstantes.NAME,"NJ6DIasBm7RQ1t8i5kH1j9tUaUQJ4g90RBlk5EDV5jCld7Tu2l");
        mapUfDTOMock.put(UfConstantes.STATUS,"oFJ1fzidDJhnfEVex9bEQh228P1CKrzh4XIdHag2wBFuQEvoX2");


        Optional<Uf> ufModelMock = Optional.ofNullable(
                UfModelBuilder.newUfModelTestBuilder()
                        .id(22633L)
                        .name("3xGNJ0mfPubmm6erVE6Cwgrr3WBUIzj2rv9IkHMvJcjD9XCR3O")
                        .status("QtIjKzRIr3bc0L0L2RBzFKX0T0eEph2VFECAxrSmna3KVL4wg2")

                        .now()
        );

        Mockito.when(ufRepositoryMock.findById(1L)).thenReturn(ufModelMock);

        // action
        boolean executed = ufService.partialUpdate(1L, mapUfDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnUfNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapUfDTOMock = new HashMap<>();
        mapUfDTOMock.put(UfConstantes.NAME,"cY7y7lOLgn7RM3ysgDHeQ1oxSPVMpfOUdfE2jt9zIxuEHpABLK");
        mapUfDTOMock.put(UfConstantes.STATUS,"FQbAovNhji3C18jAnMGcJHhfeyDm5wcBbazRrPX2I1e2R5Dop0");


        Mockito.when(ufRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        UfNotFoundException exception = Assertions.assertThrows(UfNotFoundException.class,
                ()->ufService.partialUpdate(1L, mapUfDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("Uf não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnUfListWhenFindAllUfByIdAndStatus() {
        // scenario
        List<Uf> ufs = Arrays.asList(
            UfModelBuilder.newUfModelTestBuilder().now(),
            UfModelBuilder.newUfModelTestBuilder().now(),
            UfModelBuilder.newUfModelTestBuilder().now()
        );

        Mockito.when(ufRepositoryMock.findAllByIdAndStatus(605L, "A")).thenReturn(ufs);

        // action
        List<UfDTO> result = ufService.findAllUfByIdAndStatus(605L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUfListWhenFindAllUfByNameAndStatus() {
        // scenario
        List<Uf> ufs = Arrays.asList(
            UfModelBuilder.newUfModelTestBuilder().now(),
            UfModelBuilder.newUfModelTestBuilder().now(),
            UfModelBuilder.newUfModelTestBuilder().now()
        );

        Mockito.when(ufRepositoryMock.findAllByNameAndStatus("COSCjp79vJHSJ2WoW3to6ERkjxL9PKEJHB0WcXpt9008jSPVke", "A")).thenReturn(ufs);

        // action
        List<UfDTO> result = ufService.findAllUfByNameAndStatus("COSCjp79vJHSJ2WoW3to6ERkjxL9PKEJHB0WcXpt9008jSPVke", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUfListWhenFindAllUfByDateCreatedAndStatus() {
        // scenario
        List<Uf> ufs = Arrays.asList(
            UfModelBuilder.newUfModelTestBuilder().now(),
            UfModelBuilder.newUfModelTestBuilder().now(),
            UfModelBuilder.newUfModelTestBuilder().now()
        );

        Mockito.when(ufRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(ufs);

        // action
        List<UfDTO> result = ufService.findAllUfByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnUfListWhenFindAllUfByDateUpdatedAndStatus() {
        // scenario
        List<Uf> ufs = Arrays.asList(
            UfModelBuilder.newUfModelTestBuilder().now(),
            UfModelBuilder.newUfModelTestBuilder().now(),
            UfModelBuilder.newUfModelTestBuilder().now()
        );

        Mockito.when(ufRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(ufs);

        // action
        List<UfDTO> result = ufService.findAllUfByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentUfDTOWhenFindUfByIdAndStatus() {
        // scenario
        Optional<Uf> ufModelMock = Optional.ofNullable(UfModelBuilder.newUfModelTestBuilder().now());
        Mockito.when(ufRepositoryMock.loadMaxIdByIdAndStatus(45480L, "A")).thenReturn(1L);
        Mockito.when(ufRepositoryMock.findById(1L)).thenReturn(ufModelMock);

        // action
        UfDTO result = ufService.findUfByIdAndStatus(45480L, "A");

        // validate
        Assertions.assertInstanceOf(UfDTO.class,result);
    }
    @Test
    public void shouldReturnUfNotFoundExceptionWhenNonExistenceUfIdAndStatus() {
        // scenario
        Mockito.when(ufRepositoryMock.loadMaxIdByIdAndStatus(45480L, "A")).thenReturn(0L);
        Mockito.when(ufRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UfNotFoundException exception = Assertions.assertThrows(UfNotFoundException.class,
                ()->ufService.findUfByIdAndStatus(45480L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(UF_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentUfDTOWhenFindUfByNameAndStatus() {
        // scenario
        Optional<Uf> ufModelMock = Optional.ofNullable(UfModelBuilder.newUfModelTestBuilder().now());
        Mockito.when(ufRepositoryMock.loadMaxIdByNameAndStatus("WRprY1Nm16K5xMp242PCI2Ufi2GjIyW0tqpLLiQJEghrWGNCXe", "A")).thenReturn(1L);
        Mockito.when(ufRepositoryMock.findById(1L)).thenReturn(ufModelMock);

        // action
        UfDTO result = ufService.findUfByNameAndStatus("WRprY1Nm16K5xMp242PCI2Ufi2GjIyW0tqpLLiQJEghrWGNCXe", "A");

        // validate
        Assertions.assertInstanceOf(UfDTO.class,result);
    }
    @Test
    public void shouldReturnUfNotFoundExceptionWhenNonExistenceUfNameAndStatus() {
        // scenario
        Mockito.when(ufRepositoryMock.loadMaxIdByNameAndStatus("WRprY1Nm16K5xMp242PCI2Ufi2GjIyW0tqpLLiQJEghrWGNCXe", "A")).thenReturn(0L);
        Mockito.when(ufRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        UfNotFoundException exception = Assertions.assertThrows(UfNotFoundException.class,
                ()->ufService.findUfByNameAndStatus("WRprY1Nm16K5xMp242PCI2Ufi2GjIyW0tqpLLiQJEghrWGNCXe", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(UF_NOTFOUND_WITH_NAME));
    }

    @Test
    public void shouldReturnUfDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "eSvFyA870VpaAmqz8c00ChokJVy27cSOqFXetvtLj2wUYnGD8N";
        Optional<Uf> ufModelMock = Optional.ofNullable(UfModelBuilder.newUfModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(ufRepositoryMock.findById(420L)).thenReturn(ufModelMock);
        Mockito.doNothing().when(ufRepositoryMock).updateNameById(420L, nameUpdateMock);

        // action
        ufService.updateNameById(420L, nameUpdateMock);

        // validate
        Mockito.verify(ufRepositoryMock,Mockito.times(1)).updateNameById(420L, nameUpdateMock);
    }



    @Test
    public void showReturnExistingUfDTOWhenFindUfByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 48585L;
        Long maxIdMock = 1972L;
        Optional<Uf> ufModelMock = Optional.ofNullable(UfModelBuilder.newUfModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(ufRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(ufRepositoryMock.findById(maxIdMock)).thenReturn(ufModelMock);

        // action
        UfDTO result = ufService.findUfByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnUfNotFoundExceptionWhenNonExistenceFindUfByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 48585L;
        Long noMaxIdMock = 0L;
        Optional<Uf> ufModelMock = Optional.empty();
        Mockito.when(ufRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(ufRepositoryMock.findById(noMaxIdMock)).thenReturn(ufModelMock);

        // action
        UfNotFoundException exception = Assertions.assertThrows(UfNotFoundException.class,
                ()->ufService.findUfByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(UF_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingUfDTOWhenFindUfByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "r09wMHcgxUl18XC3Tr898sL8fU1xTQQkypyhXrnavgXsLYUurF";
        Long maxIdMock = 1972L;
        Optional<Uf> ufModelMock = Optional.ofNullable(UfModelBuilder.newUfModelTestBuilder()
                .name(nameMock)
                .now()
        );
        Mockito.when(ufRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(maxIdMock);
        Mockito.when(ufRepositoryMock.findById(maxIdMock)).thenReturn(ufModelMock);

        // action
        UfDTO result = ufService.findUfByNameAndStatus(nameMock);

        // validate
        Assertions.assertEquals(nameMock, result.getName());

    }
    @Test
    public void showReturnUfNotFoundExceptionWhenNonExistenceFindUfByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "r09wMHcgxUl18XC3Tr898sL8fU1xTQQkypyhXrnavgXsLYUurF";
        Long noMaxIdMock = 0L;
        Optional<Uf> ufModelMock = Optional.empty();
        Mockito.when(ufRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(ufRepositoryMock.findById(noMaxIdMock)).thenReturn(ufModelMock);

        // action
        UfNotFoundException exception = Assertions.assertThrows(UfNotFoundException.class,
                ()->ufService.findUfByNameAndStatus(nameMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(UF_NOTFOUND_WITH_NAME));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

