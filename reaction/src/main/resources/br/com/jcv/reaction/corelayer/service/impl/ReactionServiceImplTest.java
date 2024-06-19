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

package br.com.jcv.reaction.corelayer.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.reaction.corelayer.builder.ReactionDTOBuilder;
import br.com.jcv.reaction.corelayer.builder.ReactionModelBuilder;
import br.com.jcv.reaction.corelayer.dto.ReactionDTO;
import br.com.jcv.reaction.corelayer.exception.ReactionNotFoundException;
import br.com.jcv.reaction.corelayer.model.Reaction;
import br.com.jcv.reaction.corelayer.repository.ReactionRepository;
import br.com.jcv.reaction.corelayer.service.ReactionService;
import br.com.jcv.reaction.corelayer.constantes.ReactionConstantes;
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
public class ReactionServiceImplTest {
    private static final UUID uuidMock = UUID.fromString("3dc936e6-478e-4d21-b167-67dee8b730af");
    private static MockedStatic<UUID> uuidMockedStatic;
    private static MockedStatic<DateUtility> dateUtilityMockedStatic;

    public static final String REACTION_NOTFOUND_WITH_ID = "Reaction não encontrada com id = ";
    public static final String REACTION_NOTFOUND_WITH_NAME = "Reaction não encontrada com name = ";
    public static final String REACTION_NOTFOUND_WITH_ICON = "Reaction não encontrada com icon = ";
    public static final String REACTION_NOTFOUND_WITH_TAG = "Reaction não encontrada com tag = ";
    public static final String REACTION_NOTFOUND_WITH_STATUS = "Reaction não encontrada com status = ";
    public static final String REACTION_NOTFOUND_WITH_DATECREATED = "Reaction não encontrada com dateCreated = ";
    public static final String REACTION_NOTFOUND_WITH_DATEUPDATED = "Reaction não encontrada com dateUpdated = ";


    @Mock
    private ReactionRepository reactionRepositoryMock;

    @InjectMocks
    private ReactionService reactionService;
    final DateTime dateTimeMock = Mockito.mock(DateTime.class);

    @BeforeAll
    public void setup() {
        Mockito.when(dateTimeMock.getToday()).thenReturn(DateUtility.getDate(12,10,2023));
        Mockito.when(dateTimeMock.now()).thenReturn(DateUtility.getDate(12,10,2023));

        reactionService = new ReactionServiceImpl();
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
    public void shouldReturnListOfReactionWhenFindAllByFilterIsCalled() {
        // scenario
        Long id = 45023L;
        String name = "vVQaQXYwFjh2fTAIQJoPTldaLBLlDu1jeAjHBYTvmABu0FfyDM";
        String icon = "QOTpGsJNwDCCx5CXyjOhxlUOtsBMLmRtplwe0MAs7kukOEa5qQ";
        String tag = "XpmFM3LVjri9SI0ndD4nqIDlYopoSGrfgwhnkM0OcG5JHe0HbG";
        String status = "bruDgI2RqtIslxlhrUMUogfHPcTRpzr0jpjSOlOvjzTm61XHEq";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("icon", icon);
        mapFieldsRequestMock.put("tag", tag);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(0);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Reaction> reactionsFromRepository = new ArrayList<>();
        reactionsFromRepository.add(ReactionModelBuilder.newReactionModelTestBuilder().now());
        reactionsFromRepository.add(ReactionModelBuilder.newReactionModelTestBuilder().now());
        reactionsFromRepository.add(ReactionModelBuilder.newReactionModelTestBuilder().now());
        reactionsFromRepository.add(ReactionModelBuilder.newReactionModelTestBuilder().now());

        Mockito.when(reactionRepositoryMock.findReactionByFilter(
            id,
            name,
            icon,
            tag,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(reactionsFromRepository);

        // action
        List<ReactionDTO> result = reactionService.findAllByFilter(requestFilterMock);

        // validate
        Assertions.assertEquals(4L, result.size());
    }



    @Test
    public void shouldReturnMapWithReactionListWhenFindPageByFilterIsCalled() {
        // scenario
        Long id = 51803L;
        String name = "W6DM04NhEhS3ylyh6jLqzrc501r7uMz4xTuxOYU2W9saQdPtOI";
        String icon = "jUzbjg1aPprFbvnNotQvVzqVy4dzxKPv8un1tFaADx4eg3sDSk";
        String tag = "Id4JtCSWeQkOwk5EnLmOGeVvFmelWz0G7Ft1yXTMWSqQxbB3Mt";
        String status = "epH8oQ2QHV4F2T0rVmR8Iy9H0C28MI5QN0J0azDrr8ATst9q6m";
        String dateCreated = "2025-10-07";
        String dateUpdated = "2025-10-07";


        Map<String, Object> mapFieldsRequestMock = new HashMap<>();
        mapFieldsRequestMock.put("id", id);
        mapFieldsRequestMock.put("name", name);
        mapFieldsRequestMock.put("icon", icon);
        mapFieldsRequestMock.put("tag", tag);
        mapFieldsRequestMock.put("status", status);
        mapFieldsRequestMock.put("dateCreated", dateCreated);
        mapFieldsRequestMock.put("dateUpdated", dateUpdated);


        RequestFilter requestFilterMock = new RequestFilter();
        requestFilterMock.setQtdeRegistrosPorPagina(25);
        requestFilterMock.setOrdemAsc(true);
        requestFilterMock.setPagina(0);
        requestFilterMock.setCamposFiltro(mapFieldsRequestMock);

        List<Reaction> reactionsFromRepository = new ArrayList<>();
        reactionsFromRepository.add(ReactionModelBuilder.newReactionModelTestBuilder().now());
        reactionsFromRepository.add(ReactionModelBuilder.newReactionModelTestBuilder().now());
        reactionsFromRepository.add(ReactionModelBuilder.newReactionModelTestBuilder().now());
        reactionsFromRepository.add(ReactionModelBuilder.newReactionModelTestBuilder().now());

        List<ReactionDTO> reactionsFiltered = reactionsFromRepository
                .stream()
                .map(m->reactionService.toDTO(m))
                .collect(Collectors.toList());

        Map<String,Object> mapResponseMock = new HashMap<>();
        mapResponseMock.put("currentPage", 0);
        mapResponseMock.put("totalItems", 4);
        mapResponseMock.put("totalPages", 1);
        mapResponseMock.put("pageReactionItems", reactionsFiltered);

        Pageable pageableMock = PageRequest.of(0,25);

        PageImpl<Reaction> pagedResponse =
                new PageImpl<>(reactionsFromRepository,
                        pageableMock,
                        reactionsFromRepository.size());

        Mockito.when(reactionRepositoryMock.findReactionByFilter(pageableMock,
            id,
            name,
            icon,
            tag,
            status,
            dateCreated,
            dateUpdated

        )).thenReturn(pagedResponse);

        // action
        Map<String, Object> result = reactionService.findPageByFilter(requestFilterMock);

        // validate
        Long currentPage = Long.valueOf(result.get("currentPage").toString());
        Long totalItems = Long.valueOf(result.get("totalItems").toString());
        Long totalPages = Long.valueOf(result.get("totalPages").toString());
        List<ReactionDTO> reactionsResult = (List<ReactionDTO>) result.get("pageReactionItems");

        Assertions.assertEquals(0L, currentPage);
        Assertions.assertEquals(4L, totalItems);
        Assertions.assertEquals(1L, totalPages);
        Assertions.assertEquals(4L, reactionsResult.size());
    }


    @Test
    public void showReturnListOfReactionWhenAskedForFindAllByStatus() {
        // scenario
        List<Reaction> listOfReactionModelMock = new ArrayList<>();
        listOfReactionModelMock.add(ReactionModelBuilder.newReactionModelTestBuilder().now());
        listOfReactionModelMock.add(ReactionModelBuilder.newReactionModelTestBuilder().now());

        Mockito.when(reactionRepositoryMock.findAllByStatus("A")).thenReturn(listOfReactionModelMock);

        // action
        List<ReactionDTO> listOfReactions = reactionService.findAllByStatus("A");

        // validate
        Assertions.assertTrue(!listOfReactions.isEmpty());
        Assertions.assertEquals(2, listOfReactions.size());
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 3848L;
        Optional<Reaction> reactionNonExistentMock = Optional.empty();
        Mockito.when(reactionRepositoryMock.findById(idMock)).thenReturn(reactionNonExistentMock);

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldThrowReactionNotFoundExceptionWhenUpdateStatusByIdForInexistentId() {
        // scenario
        Long idMock = 2078L;
        Mockito.when(reactionRepositoryMock.findById(idMock))
                .thenThrow(new ReactionNotFoundException(REACTION_NOTFOUND_WITH_ID,
                HttpStatus.NOT_FOUND,
                REACTION_NOTFOUND_WITH_ID ));

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.updateStatusById(idMock, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404,exception.getHttpStatus().value());
    }
    @Test
    public void shouldReturnReactionDTOAfterUpdateStatusById() {
        // scenario
        Long idMock = 38100L;
        Optional<Reaction> reactionModelMock = Optional.ofNullable(
                ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(idMock)
                        .name("Pkt3cgYtNH7NTDzngwULmruMA9O8vBtA3BpHXPWHnioUDB5LFm")
                        .icon("vj8NOi9AiNQqn1YomAMVUGpcj9lgSxcCvK0Vc7Q45XueDqGrp7")
                        .tag("pp076zEbvdkgQyYYDW0Ir5ENcXsKjYi0kXRBc4Wodif0C0k61i")

                        .status("X")
                        .now()
        );
        Reaction reactionToSaveMock = reactionModelMock.orElse(null);
        Reaction reactionSavedMck = ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(46103L)
                        .name("rYLF6dYJWSMn6xyIgfywxNGv5MwiV97qVI7bFlh0cgB80UbxKL")
                        .icon("uc900qCrmzr0wYORBLuhlqkDA7G70bEmQs1B0t0AWza5NWUe8a")
                        .tag("mWY06h6UbHYRAj3fT0ateYspVAOSqQkKctJlsXQt1knDMOYouN")

                        .status("A")
                        .now();
        Mockito.when(reactionRepositoryMock.findById(idMock)).thenReturn(reactionModelMock);
        Mockito.when(reactionRepositoryMock.save(reactionToSaveMock)).thenReturn(reactionSavedMck);

        // action
        ReactionDTO result = reactionService.updateStatusById(idMock, "A");

        // validate
        Assertions.assertEquals("A",result.getStatus());

    }

    @Test
    public void shouldSearchReactionByAnyNonExistenceIdAndReturnReactionNotFoundException() {
        // scenario
        Mockito.when(reactionRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()-> reactionService.findById(-1000L));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldSearchReactionByIdAndReturnDTO() {
        // scenario
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder()
                .id(55020L)
                .name("5N90EBuWVbIROepuRHQgovX1jVkmNYUloGlOks9kamb0OI60Yz")
                .icon("XusiyX5Yny4bmeqTPDji8QbI1BquDQMUxMOLOBIYyK30a0GEWe")
                .tag("gmPbA1j0xYEj00cxS3NbVwceTky5vco0QdvEgLkoe8WNpKkY4V")

                .status("A")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now());
        Mockito.when(reactionRepositoryMock.findById(Mockito.anyLong())).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findById(1L);

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldDeleteReactionByIdWithSucess() {
        // scenario
        Optional<Reaction> reaction = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder().id(1L).now());
        Mockito.when(reactionRepositoryMock.findById(Mockito.anyLong())).thenReturn(reaction);

        // action
        reactionService.delete(1L);

        // validate
        Mockito.verify(reactionRepositoryMock,Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenTryDeleteNotExistenceReactionShouldReturnReactionNotFoundException() {
        // scenario
        Mockito.when(reactionRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(
                ReactionNotFoundException.class, () -> reactionService.delete(1L)
        );

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ID));
    }

    @Test
    public void ShouldSaveUpdateExistingReactionWithSucess() {
        // scenario
        ReactionDTO reactionDTOMock = ReactionDTOBuilder.newReactionDTOTestBuilder()
                .id(35200L)
                .name("DGSxoKM12AycCpeeO0A5YJQ5XCgsclkqXlDFuD62roxj4SnvvX")
                .icon("b2bKac5arTSFocOklmWto70pcrIR5UPYvQcRbhMl8OEE0UH5Ow")
                .tag("3nwsqhc08z878CI87E0mVK07lJPXR300GxHTJR6Ui4uEsnJxci")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Reaction reactionMock = ReactionModelBuilder.newReactionModelTestBuilder()
                .id(reactionDTOMock.getId())
                .name(reactionDTOMock.getName())
                .icon(reactionDTOMock.getIcon())
                .tag(reactionDTOMock.getTag())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Reaction reactionSavedMock = ReactionModelBuilder.newReactionModelTestBuilder()
                .id(reactionDTOMock.getId())
                .name(reactionDTOMock.getName())
                .icon(reactionDTOMock.getIcon())
                .tag(reactionDTOMock.getTag())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(reactionRepositoryMock.save(reactionMock)).thenReturn(reactionSavedMock);

        // action
        ReactionDTO reactionSaved = reactionService.salvar(reactionDTOMock);

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class, reactionSaved);
        Assertions.assertNotNull(reactionSaved.getId());
    }

    @Test
    public void ShouldSaveNewReactionWithSucess() {
        // scenario
        ReactionDTO reactionDTOMock = ReactionDTOBuilder.newReactionDTOTestBuilder()
                .id(null)
                .name("2z46S064eBKBbEUh4obfJu0NvJrCutorofElW7cf0aOxWKut94")
                .icon("oKYt0qgXIk303owdks1yi0N0XdHGOiRumN57BWA6UNwRNwNbKx")
                .tag("568PL3aIhj7KG0gUs3motVt8g5xEH9XRjfCOeg8FkEga2EJXkI")

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Reaction reactionModelMock = ReactionModelBuilder.newReactionModelTestBuilder()
                .id(null)
                .name(reactionDTOMock.getName())
                .icon(reactionDTOMock.getIcon())
                .tag(reactionDTOMock.getTag())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();
        Reaction reactionSavedMock = ReactionModelBuilder.newReactionModelTestBuilder()
                .id(501L)
                .name(reactionDTOMock.getName())
                .icon(reactionDTOMock.getIcon())
                .tag(reactionDTOMock.getTag())

                .status("P")
                .dateCreated(dateTimeMock.getToday())
                .dateUpdated(dateTimeMock.getToday())
                .now();

        Mockito.when(reactionRepositoryMock.save(reactionModelMock)).thenReturn(reactionSavedMock);

        // action
        ReactionDTO reactionSaved = reactionService.salvar(reactionDTOMock);

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class, reactionSaved);
        Assertions.assertNotNull(reactionSaved.getId());
        Assertions.assertEquals("P",reactionSaved.getStatus());
    }

    @Test
    public void shouldExecutePartialUpdateWithSucess() {
        // scenario
        Map<String, Object> mapReactionDTOMock = new HashMap<>();
        mapReactionDTOMock.put(ReactionConstantes.NAME,"3UEiU2V7qVCkVFxcPVu88r0NstcUWlYNxWj2dg5tiYwaTQeyvd");
        mapReactionDTOMock.put(ReactionConstantes.ICON,"q2JgG5EzEbuFBjlLyWjavdprGmESwFSj2HQu4jxWkOOtKQA3rD");
        mapReactionDTOMock.put(ReactionConstantes.TAG,"OPfmlVIameBKx0SCjQsK5klnYPT58PEg9K5cT8RJTVssv24u2N");
        mapReactionDTOMock.put(ReactionConstantes.STATUS,"IMdWgsI5dctNynB54lgVRmBYN30oiHYEI4exCORkvJaVAeDUot");


        Optional<Reaction> reactionModelMock = Optional.ofNullable(
                ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(71852L)
                        .name("5n8UDLI0dU01g8OHKJmj7QOUnyOteJFek8EQWWPSw1zFeR49kF")
                        .icon("MeU9Uv4Jws6cMl5Re4taAIcD8FbbUBrqkV7fpMUuF4ie7AzJDH")
                        .tag("pmcI4WOdsRb7wv5ux0KLPxXSy7rhSg20FGzXgWA6hcjrxs02G0")
                        .status("cqUdNHHB3z5tWk16oFVu21vgsc2Liv4F8Vqq6BrFjanEj8mKLm")

                        .now()
        );

        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        boolean executed = reactionService.partialUpdate(1L, mapReactionDTOMock);

        // validate
        Assertions.assertTrue(executed);

    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenTrySearchNotExistentId() {
        // scenario
        Map<String, Object> mapReactionDTOMock = new HashMap<>();
        mapReactionDTOMock.put(ReactionConstantes.NAME,"fWPhb2Iccd5TdFTiUQKJ6qvQrltgbLnXfefdb4It0uzajqD67n");
        mapReactionDTOMock.put(ReactionConstantes.ICON,"ws8FWbI6XwwfnLgEtb37FqbgKYeKEuIBNQqYuK6oNOAasCBYA7");
        mapReactionDTOMock.put(ReactionConstantes.TAG,"5gBMJP4mqAaMszwsXi644LX0ybulNKiwOpEUHj1RuIJNLyHiRH");
        mapReactionDTOMock.put(ReactionConstantes.STATUS,"x5xywyG92QHGt3t4wIf2WVkfFI7wY5R5KTCiP2OdlT7rKIoKIw");


        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.partialUpdate(1L, mapReactionDTOMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains("Reaction não encontrada com id = "));
        Assertions.assertEquals(404,exception.getHttpStatus().value());

    }

    @Test
    public void shouldReturnReactionListWhenFindAllReactionByIdAndStatus() {
        // scenario
        List<Reaction> reactions = Arrays.asList(
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now()
        );

        Mockito.when(reactionRepositoryMock.findAllByIdAndStatus(10528L, "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByIdAndStatus(10528L, "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionListWhenFindAllReactionByNameAndStatus() {
        // scenario
        List<Reaction> reactions = Arrays.asList(
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now()
        );

        Mockito.when(reactionRepositoryMock.findAllByNameAndStatus("vRaxXzx0SplMfRE5WpgaxwrbsSAvHjkmW0PXetYwzRACktwUFh", "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByNameAndStatus("vRaxXzx0SplMfRE5WpgaxwrbsSAvHjkmW0PXetYwzRACktwUFh", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionListWhenFindAllReactionByIconAndStatus() {
        // scenario
        List<Reaction> reactions = Arrays.asList(
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now()
        );

        Mockito.when(reactionRepositoryMock.findAllByIconAndStatus("073IP8opJ4yqViV0tChPFucLYXuyxz20T89YQmdfKIjpkehjFw", "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByIconAndStatus("073IP8opJ4yqViV0tChPFucLYXuyxz20T89YQmdfKIjpkehjFw", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionListWhenFindAllReactionByTagAndStatus() {
        // scenario
        List<Reaction> reactions = Arrays.asList(
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now()
        );

        Mockito.when(reactionRepositoryMock.findAllByTagAndStatus("2ryc8lrh1O1m95h1uU6XK80GmtP3T8ceFwzjeuu6sdiEQQGFpU", "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByTagAndStatus("2ryc8lrh1O1m95h1uU6XK80GmtP3T8ceFwzjeuu6sdiEQQGFpU", "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionListWhenFindAllReactionByDateCreatedAndStatus() {
        // scenario
        List<Reaction> reactions = Arrays.asList(
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now()
        );

        Mockito.when(reactionRepositoryMock.findAllByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByDateCreatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }
    @Test
    public void shouldReturnReactionListWhenFindAllReactionByDateUpdatedAndStatus() {
        // scenario
        List<Reaction> reactions = Arrays.asList(
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now(),
            ReactionModelBuilder.newReactionModelTestBuilder().now()
        );

        Mockito.when(reactionRepositoryMock.findAllByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByDateUpdatedAndStatus(Date.from(LocalDate.of(2025,10,7).atStartOfDay(ZoneId.systemDefault()).toInstant()), "A");

        // validate
        Assertions.assertInstanceOf(List.class, result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnExistentReactionDTOWhenFindReactionByIdAndStatus() {
        // scenario
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder().now());
        Mockito.when(reactionRepositoryMock.loadMaxIdByIdAndStatus(48078L, "A")).thenReturn(1L);
        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByIdAndStatus(48078L, "A");

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenNonExistenceReactionIdAndStatus() {
        // scenario
        Mockito.when(reactionRepositoryMock.loadMaxIdByIdAndStatus(48078L, "A")).thenReturn(0L);
        Mockito.when(reactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByIdAndStatus(48078L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentReactionDTOWhenFindReactionByNameAndStatus() {
        // scenario
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder().now());
        Mockito.when(reactionRepositoryMock.loadMaxIdByNameAndStatus("yc9piIUoKaaBB07vSMh3XbLnCxu9BNtPPqwGzPptY2HkfW8Jue", "A")).thenReturn(1L);
        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByNameAndStatus("yc9piIUoKaaBB07vSMh3XbLnCxu9BNtPPqwGzPptY2HkfW8Jue", "A");

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenNonExistenceReactionNameAndStatus() {
        // scenario
        Mockito.when(reactionRepositoryMock.loadMaxIdByNameAndStatus("yc9piIUoKaaBB07vSMh3XbLnCxu9BNtPPqwGzPptY2HkfW8Jue", "A")).thenReturn(0L);
        Mockito.when(reactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByNameAndStatus("yc9piIUoKaaBB07vSMh3XbLnCxu9BNtPPqwGzPptY2HkfW8Jue", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_NAME));
    }
    @Test
    public void shouldReturnExistentReactionDTOWhenFindReactionByIconAndStatus() {
        // scenario
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder().now());
        Mockito.when(reactionRepositoryMock.loadMaxIdByIconAndStatus("G70oMyrRUoDOHNuQ4kX9GdqbjloVXvItQVLkxwoHuTt01y7H3R", "A")).thenReturn(1L);
        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByIconAndStatus("G70oMyrRUoDOHNuQ4kX9GdqbjloVXvItQVLkxwoHuTt01y7H3R", "A");

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenNonExistenceReactionIconAndStatus() {
        // scenario
        Mockito.when(reactionRepositoryMock.loadMaxIdByIconAndStatus("G70oMyrRUoDOHNuQ4kX9GdqbjloVXvItQVLkxwoHuTt01y7H3R", "A")).thenReturn(0L);
        Mockito.when(reactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByIconAndStatus("G70oMyrRUoDOHNuQ4kX9GdqbjloVXvItQVLkxwoHuTt01y7H3R", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ICON));
    }
    @Test
    public void shouldReturnExistentReactionDTOWhenFindReactionByTagAndStatus() {
        // scenario
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder().now());
        Mockito.when(reactionRepositoryMock.loadMaxIdByTagAndStatus("1zcDvRnIWN4n5bRk3VoPfYIyuDBniXJQQJ28ULxL5SDUrvnhL3", "A")).thenReturn(1L);
        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByTagAndStatus("1zcDvRnIWN4n5bRk3VoPfYIyuDBniXJQQJ28ULxL5SDUrvnhL3", "A");

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenNonExistenceReactionTagAndStatus() {
        // scenario
        Mockito.when(reactionRepositoryMock.loadMaxIdByTagAndStatus("1zcDvRnIWN4n5bRk3VoPfYIyuDBniXJQQJ28ULxL5SDUrvnhL3", "A")).thenReturn(0L);
        Mockito.when(reactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByTagAndStatus("1zcDvRnIWN4n5bRk3VoPfYIyuDBniXJQQJ28ULxL5SDUrvnhL3", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_TAG));
    }

    @Test
    public void shouldReturnReactionDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "pVnuGst6ofoAhT5irALb8q1SxjM4UwMFEuyrL33eBNTAvRITwp";
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reactionRepositoryMock.findById(420L)).thenReturn(reactionModelMock);
        Mockito.doNothing().when(reactionRepositoryMock).updateNameById(420L, nameUpdateMock);

        // action
        reactionService.updateNameById(420L, nameUpdateMock);

        // validate
        Mockito.verify(reactionRepositoryMock,Mockito.times(1)).updateNameById(420L, nameUpdateMock);
    }
    @Test
    public void shouldReturnReactionDTOWhenUpdateExistingIconById() {
        // scenario
        String iconUpdateMock = "dh2yeKxVcz15sYpflfgPRBPOKus3QhvmAxAWLz0gQLEcWA2AyW";
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reactionRepositoryMock.findById(420L)).thenReturn(reactionModelMock);
        Mockito.doNothing().when(reactionRepositoryMock).updateIconById(420L, iconUpdateMock);

        // action
        reactionService.updateIconById(420L, iconUpdateMock);

        // validate
        Mockito.verify(reactionRepositoryMock,Mockito.times(1)).updateIconById(420L, iconUpdateMock);
    }
    @Test
    public void shouldReturnReactionDTOWhenUpdateExistingTagById() {
        // scenario
        String tagUpdateMock = "kLQ6QK0rJdX6T5uuGEcTyqCDm1sMaP9PV9twn9UdyLAREYQjHs";
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(420L)
                .now());
        Mockito.when(reactionRepositoryMock.findById(420L)).thenReturn(reactionModelMock);
        Mockito.doNothing().when(reactionRepositoryMock).updateTagById(420L, tagUpdateMock);

        // action
        reactionService.updateTagById(420L, tagUpdateMock);

        // validate
        Mockito.verify(reactionRepositoryMock,Mockito.times(1)).updateTagById(420L, tagUpdateMock);
    }



    @Test
    public void showReturnExistingReactionDTOWhenFindReactionByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 48500L;
        Long maxIdMock = 1972L;
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder()
                .id(idMock)
                .now()
        );
        Mockito.when(reactionRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactionRepositoryMock.findById(maxIdMock)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByIdAndStatus(idMock);

        // validate
        Assertions.assertEquals(idMock, result.getId());

    }
    @Test
    public void showReturnReactionNotFoundExceptionWhenNonExistenceFindReactionByIdAndStatusActiveAnonimous() {
        // scenario
        Long idMock = 48500L;
        Long noMaxIdMock = 0L;
        Optional<Reaction> reactionModelMock = Optional.empty();
        Mockito.when(reactionRepositoryMock.loadMaxIdByIdAndStatus(idMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactionRepositoryMock.findById(noMaxIdMock)).thenReturn(reactionModelMock);

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByIdAndStatus(idMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ID));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReactionDTOWhenFindReactionByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "DjcTS0CiIPM0r1WsRuguV0gSxvsw0gWQQUqmO9CKaNf40HClUM";
        Long maxIdMock = 1972L;
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder()
                .name(nameMock)
                .now()
        );
        Mockito.when(reactionRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactionRepositoryMock.findById(maxIdMock)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByNameAndStatus(nameMock);

        // validate
        Assertions.assertEquals(nameMock, result.getName());

    }
    @Test
    public void showReturnReactionNotFoundExceptionWhenNonExistenceFindReactionByNameAndStatusActiveAnonimous() {
        // scenario
        String nameMock = "DjcTS0CiIPM0r1WsRuguV0gSxvsw0gWQQUqmO9CKaNf40HClUM";
        Long noMaxIdMock = 0L;
        Optional<Reaction> reactionModelMock = Optional.empty();
        Mockito.when(reactionRepositoryMock.loadMaxIdByNameAndStatus(nameMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactionRepositoryMock.findById(noMaxIdMock)).thenReturn(reactionModelMock);

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByNameAndStatus(nameMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_NAME));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReactionDTOWhenFindReactionByIconAndStatusActiveAnonimous() {
        // scenario
        String iconMock = "AVCqReRYgnyoq0VI3sAWTcwNLLEvDkPV5gbsTNpPAjedo7skQk";
        Long maxIdMock = 1972L;
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder()
                .icon(iconMock)
                .now()
        );
        Mockito.when(reactionRepositoryMock.loadMaxIdByIconAndStatus(iconMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactionRepositoryMock.findById(maxIdMock)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByIconAndStatus(iconMock);

        // validate
        Assertions.assertEquals(iconMock, result.getIcon());

    }
    @Test
    public void showReturnReactionNotFoundExceptionWhenNonExistenceFindReactionByIconAndStatusActiveAnonimous() {
        // scenario
        String iconMock = "AVCqReRYgnyoq0VI3sAWTcwNLLEvDkPV5gbsTNpPAjedo7skQk";
        Long noMaxIdMock = 0L;
        Optional<Reaction> reactionModelMock = Optional.empty();
        Mockito.when(reactionRepositoryMock.loadMaxIdByIconAndStatus(iconMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactionRepositoryMock.findById(noMaxIdMock)).thenReturn(reactionModelMock);

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByIconAndStatus(iconMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ICON));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

    @Test
    public void showReturnExistingReactionDTOWhenFindReactionByTagAndStatusActiveAnonimous() {
        // scenario
        String tagMock = "3hlhsfdu1Q5jSEO2Ed1L1KrwiAhRWC13aOpydPDD9lrflSPfHm";
        Long maxIdMock = 1972L;
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder()
                .tag(tagMock)
                .now()
        );
        Mockito.when(reactionRepositoryMock.loadMaxIdByTagAndStatus(tagMock, "A")).thenReturn(maxIdMock);
        Mockito.when(reactionRepositoryMock.findById(maxIdMock)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByTagAndStatus(tagMock);

        // validate
        Assertions.assertEquals(tagMock, result.getTag());

    }
    @Test
    public void showReturnReactionNotFoundExceptionWhenNonExistenceFindReactionByTagAndStatusActiveAnonimous() {
        // scenario
        String tagMock = "3hlhsfdu1Q5jSEO2Ed1L1KrwiAhRWC13aOpydPDD9lrflSPfHm";
        Long noMaxIdMock = 0L;
        Optional<Reaction> reactionModelMock = Optional.empty();
        Mockito.when(reactionRepositoryMock.loadMaxIdByTagAndStatus(tagMock, "A")).thenReturn(noMaxIdMock);
        Mockito.when(reactionRepositoryMock.findById(noMaxIdMock)).thenReturn(reactionModelMock);

        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByTagAndStatus(tagMock));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_TAG));
        Assertions.assertEquals(404, exception.getHttpStatus().value());

    }

}

