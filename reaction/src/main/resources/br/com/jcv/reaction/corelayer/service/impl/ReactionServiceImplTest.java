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
        Long id = 40133L;
        String name = "LBQdH10C4Pr98azOpb3MriTPnRuR1WaCTgHO5qf14fgAvYTAsY";
        String icon = "EpLYXakUgXaVL4eg6wOm4G0W94DEQfM1AdM79hsbmxGLmawxvt";
        String tag = "DDEfBXlfFJcsIgapAJxI4OnUmz0EBTi9p0SiXMhh5bRCiXqIJC";
        String status = "wEBPbF1D4cqGDctfvHPstVpi7aH8RPpxhTfIjgkJCmBoWW4JV0";
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
        Long id = 50076L;
        String name = "pcHhnAOK6xp6ivA1FXd09zk7X00wHzD1xdo0rxi0n1e1hm4iKB";
        String icon = "HRGDhxRpIo7ezLF1id4RxftMm8Dk6aW3cBNeEpmNv1kCQt90OF";
        String tag = "L1NGzJaaB1lBx07I8rfoR1qkqgpOMiWpNkwWxc8igCXYumsofX";
        String status = "9qmU9KwL2BIsWKpEK8M5zB1i4uJxbIaOJPs9TVDJsF6HgKjET9";
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
        Long idMock = 21308L;
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
        Long idMock = 61048L;
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
        Long idMock = 68148L;
        Optional<Reaction> reactionModelMock = Optional.ofNullable(
                ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(idMock)
                        .name("Jm1R1Gl6WJplvJro4wIMRxP8qbuo49uDfc6RVqa3kpzvmc4mg4")
                        .icon("OeVmb4redDFqOR4Dd87OMbbiiKXaSbsgHRmuAdSSyRVFGmNg20")
                        .tag("47uFhXEuCAbAmBxUO3vse0vq0us3tNpeEeNP4vMU0DBIzyrvUn")

                        .status("X")
                        .now()
        );
        Reaction reactionToSaveMock = reactionModelMock.orElse(null);
        Reaction reactionSavedMck = ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(33578L)
                        .name("mv64GfnLO1wrpmesu2L3gnzxEhkGyI6zKIa9Nag4ohaBQ5yPN4")
                        .icon("37KNHwhd0l1bsS0zqkwm6y8iYm4ksgqsD8fyfBVo6K7qOGlV6w")
                        .tag("K4Vwmbz4dUk96Q7IT6ghU0KkBjBMKK6LEsV59eRp7dfrrcY7c5")

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
                .id(5148L)
                .name("RQtaxNntDwWTRJacYDXA8wMl6EL5ApMyj56B80jnDlx4T63vPG")
                .icon("7NxlaA2lkLk7iLAVXBSjLtqerjep34h5jwnU9GnrNwFx2mEcH6")
                .tag("CqlWB19DiIBxqVtEC0FdnOwIJkAVN5DkrBC2CLpfAxlgQkbz6y")

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
                .id(30605L)
                .name("IlY4z1Hw87JfuyjVVcPwc5JQll0DBauS2Q914WNeHtYOH1kgsB")
                .icon("VWrqYMoLImXyG9VMjSNu7Yve9mMSMn7l0xyz6cXJo8WWHyKa2w")
                .tag("GpKU0HPVAWexI2hUchQAl4cGbWHEWbysMgmQ718Js9Eq5hCK0d")

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
                .name("T0zaT0MPJak4eVQ58OSarmctuz3r5CGeCMHn7pGWtWo9DQowAw")
                .icon("sdY39EFgvQhKYqIYNVKtD6e0P5n3HLq0hsV1Bu4tovQKlG6jc2")
                .tag("L93LdIc5B0s2bxLqVadY00F6b1zGvb2hkn0lHNrMWrf4H4RCfe")

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
        mapReactionDTOMock.put(ReactionConstantes.NAME,"lY7gGG6eIThO2uKqdfP5bAEUEvtE7cl0FXgq0I9b4SFgVhVV6v");
        mapReactionDTOMock.put(ReactionConstantes.ICON,"FG0BF9kMB2B5eIDbF3SMcTznxAsmqUf04kMF0IB7MPMlXdursp");
        mapReactionDTOMock.put(ReactionConstantes.TAG,"7UUICp8cYNSYspy4oqPV3V7QNEvfqWMYgcjL1ElQTA5AmFuHBz");
        mapReactionDTOMock.put(ReactionConstantes.STATUS,"rG0T4WwQ1QbD98OGwEzryPiBP3WBPywJSatJn0hE9WmaPBliAy");


        Optional<Reaction> reactionModelMock = Optional.ofNullable(
                ReactionModelBuilder.newReactionModelTestBuilder()
                        .id(11888L)
                        .name("in0oEUiI80Rl4GlMghbGpIIHahWOlRfEQ9i9ECNdSEfShEfRGh")
                        .icon("cCj0FmAmGrMbxYk1qCjRF0lLYbPNyavG4f59O8zVDYdcy4W54U")
                        .tag("D77s56llarX1FmvKyzojJVRSymIsrOpiSoWwtidB8SAfb8KQvt")
                        .status("J67C8jKmJ90rvRAjxC0RuLo36Wg0WILbeVtu3zo0YhlFf8BMd4")

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
        mapReactionDTOMock.put(ReactionConstantes.NAME,"jRT2LNEtAhCW4y0oFcJHCzqFKjli0DAT1yVMCN5O9SwB3gHGqH");
        mapReactionDTOMock.put(ReactionConstantes.ICON,"woBYH3OqcJEOajhQ30P03xRaorTr2dwLKWkrXgXRsVaB8eWqFq");
        mapReactionDTOMock.put(ReactionConstantes.TAG,"mKP3SXQ404YRQAvztBV2dMHUq8L1uPwv50Lix1ApcYaKdxP6bU");
        mapReactionDTOMock.put(ReactionConstantes.STATUS,"BRhXy82vbEg9RkOzUWUSEVR03zSuDy1uWUgxkFry2sPl7JSxkr");


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

        Mockito.when(reactionRepositoryMock.findAllByIdAndStatus(12810L, "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByIdAndStatus(12810L, "A");

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

        Mockito.when(reactionRepositoryMock.findAllByNameAndStatus("QYpdLHqt5fsrvtRkiCLRpJxJUnaypJyyKBClPFIHPqSRewPycI", "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByNameAndStatus("QYpdLHqt5fsrvtRkiCLRpJxJUnaypJyyKBClPFIHPqSRewPycI", "A");

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

        Mockito.when(reactionRepositoryMock.findAllByIconAndStatus("Y1mpI6vyUbDQW9SOzrbtsWtw4ujDPsCqfV1hbCVTwAkuUEWPzv", "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByIconAndStatus("Y1mpI6vyUbDQW9SOzrbtsWtw4ujDPsCqfV1hbCVTwAkuUEWPzv", "A");

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

        Mockito.when(reactionRepositoryMock.findAllByTagAndStatus("gH6thpMGxSD79ylo1bGoJDyLNlc8zj0rClFGJCfhUUM7YA3UeV", "A")).thenReturn(reactions);

        // action
        List<ReactionDTO> result = reactionService.findAllReactionByTagAndStatus("gH6thpMGxSD79ylo1bGoJDyLNlc8zj0rClFGJCfhUUM7YA3UeV", "A");

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
        Mockito.when(reactionRepositoryMock.loadMaxIdByIdAndStatus(3040L, "A")).thenReturn(1L);
        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByIdAndStatus(3040L, "A");

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenNonExistenceReactionIdAndStatus() {
        // scenario
        Mockito.when(reactionRepositoryMock.loadMaxIdByIdAndStatus(3040L, "A")).thenReturn(0L);
        Mockito.when(reactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByIdAndStatus(3040L, "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ID));
    }
    @Test
    public void shouldReturnExistentReactionDTOWhenFindReactionByNameAndStatus() {
        // scenario
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder().now());
        Mockito.when(reactionRepositoryMock.loadMaxIdByNameAndStatus("9c8Q0e8HHYczw2Rrv42rxN8gqSm7cVu3Xoy00CjgbDRnAlb8Fr", "A")).thenReturn(1L);
        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByNameAndStatus("9c8Q0e8HHYczw2Rrv42rxN8gqSm7cVu3Xoy00CjgbDRnAlb8Fr", "A");

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenNonExistenceReactionNameAndStatus() {
        // scenario
        Mockito.when(reactionRepositoryMock.loadMaxIdByNameAndStatus("9c8Q0e8HHYczw2Rrv42rxN8gqSm7cVu3Xoy00CjgbDRnAlb8Fr", "A")).thenReturn(0L);
        Mockito.when(reactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByNameAndStatus("9c8Q0e8HHYczw2Rrv42rxN8gqSm7cVu3Xoy00CjgbDRnAlb8Fr", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_NAME));
    }
    @Test
    public void shouldReturnExistentReactionDTOWhenFindReactionByIconAndStatus() {
        // scenario
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder().now());
        Mockito.when(reactionRepositoryMock.loadMaxIdByIconAndStatus("HGotN0JKrJeYSbeBbP3HXw3zXFW7IK0A0SSxpn5sTCbz1lo6B5", "A")).thenReturn(1L);
        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByIconAndStatus("HGotN0JKrJeYSbeBbP3HXw3zXFW7IK0A0SSxpn5sTCbz1lo6B5", "A");

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenNonExistenceReactionIconAndStatus() {
        // scenario
        Mockito.when(reactionRepositoryMock.loadMaxIdByIconAndStatus("HGotN0JKrJeYSbeBbP3HXw3zXFW7IK0A0SSxpn5sTCbz1lo6B5", "A")).thenReturn(0L);
        Mockito.when(reactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByIconAndStatus("HGotN0JKrJeYSbeBbP3HXw3zXFW7IK0A0SSxpn5sTCbz1lo6B5", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_ICON));
    }
    @Test
    public void shouldReturnExistentReactionDTOWhenFindReactionByTagAndStatus() {
        // scenario
        Optional<Reaction> reactionModelMock = Optional.ofNullable(ReactionModelBuilder.newReactionModelTestBuilder().now());
        Mockito.when(reactionRepositoryMock.loadMaxIdByTagAndStatus("1jRPRMIEmVaHOOeS3unNbrv6A20X61R1VHVtuTmb2nve90e27h", "A")).thenReturn(1L);
        Mockito.when(reactionRepositoryMock.findById(1L)).thenReturn(reactionModelMock);

        // action
        ReactionDTO result = reactionService.findReactionByTagAndStatus("1jRPRMIEmVaHOOeS3unNbrv6A20X61R1VHVtuTmb2nve90e27h", "A");

        // validate
        Assertions.assertInstanceOf(ReactionDTO.class,result);
    }
    @Test
    public void shouldReturnReactionNotFoundExceptionWhenNonExistenceReactionTagAndStatus() {
        // scenario
        Mockito.when(reactionRepositoryMock.loadMaxIdByTagAndStatus("1jRPRMIEmVaHOOeS3unNbrv6A20X61R1VHVtuTmb2nve90e27h", "A")).thenReturn(0L);
        Mockito.when(reactionRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        // action
        ReactionNotFoundException exception = Assertions.assertThrows(ReactionNotFoundException.class,
                ()->reactionService.findReactionByTagAndStatus("1jRPRMIEmVaHOOeS3unNbrv6A20X61R1VHVtuTmb2nve90e27h", "A"));

        // validate
        Assertions.assertTrue(exception.getMessage().contains(REACTION_NOTFOUND_WITH_TAG));
    }

    @Test
    public void shouldReturnReactionDTOWhenUpdateExistingNameById() {
        // scenario
        String nameUpdateMock = "xtddL7dOEOt0ejsqyRbcseYQYx5ToIisSf2m5qI1WL003NI8A2";
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
        String iconUpdateMock = "0XvRz0YFd7hmp0x6dMzOGek9Hky16Cebq1ti8dIyA4rnAFLk0B";
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
        String tagUpdateMock = "q0DyKNozVB053kzO4OeatnwSJP7gAFW4IJYzRjNtGwnDNkl3hB";
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
        Long idMock = 70061L;
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
        Long idMock = 70061L;
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
        String nameMock = "8oF20Us1QG3LhUSTcKfSv5HSo9AdftVLtLPVw2EcKhLHaughpD";
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
        String nameMock = "8oF20Us1QG3LhUSTcKfSv5HSo9AdftVLtLPVw2EcKhLHaughpD";
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
        String iconMock = "4Tz8F1K5WurSu2Y8NG06PvdVTOfFQv1VFGimuV0vpU0pOiy1Cs";
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
        String iconMock = "4Tz8F1K5WurSu2Y8NG06PvdVTOfFQv1VFGimuV0vpU0pOiy1Cs";
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
        String tagMock = "dfo9ApQqoncseHi39gPKdYSx00J4XEAGjnWfcWhxaGROYriGmQ";
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
        String tagMock = "dfo9ApQqoncseHi39gPKdYSx00J4XEAGjnWfcWhxaGROYriGmQ";
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

