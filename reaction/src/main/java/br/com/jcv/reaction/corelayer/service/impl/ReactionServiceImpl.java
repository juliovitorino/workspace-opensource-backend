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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.reaction.corelayer.model.Reaction;
import br.com.jcv.reaction.corelayer.repository.ReactionRepository;
import br.com.jcv.reaction.corelayer.service.ReactionService;
import br.com.jcv.reaction.infrastructure.constantes.ReactionConstantes;
import br.com.jcv.reaction.infrastructure.dto.ReactionDTO;
import br.com.jcv.reaction.infrastructure.exception.ReactionNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* ReactionServiceImpl - Implementation for Reaction interface
*
* @author Reaction
* @since Tue May 28 16:08:23 BRT 2024
*/


@Slf4j
@Service
public class ReactionServiceImpl implements ReactionService
{
    private static final String REACTION_NOTFOUND_WITH_ID = "Reaction não encontrada com id = ";
    private static final String REACTION_NOTFOUND_WITH_NAME = "Reaction não encontrada com name = ";
    private static final String REACTION_NOTFOUND_WITH_ICON = "Reaction não encontrada com icon = ";
    private static final String REACTION_NOTFOUND_WITH_TAG = "Reaction não encontrada com tag = ";
    private static final String REACTION_NOTFOUND_WITH_STATUS = "Reaction não encontrada com status = ";
    private static final String REACTION_NOTFOUND_WITH_DATECREATED = "Reaction não encontrada com dateCreated = ";
    private static final String REACTION_NOTFOUND_WITH_DATEUPDATED = "Reaction não encontrada com dateUpdated = ";


    @Autowired private ReactionRepository reactionRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando Reaction com id = {}", id);
        reactionRepository.findById(id)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        REACTION_NOTFOUND_WITH_ID  + id));
        reactionRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO salvar(ReactionDTO reactionDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(reactionDTO.getId()) && reactionDTO.getId() != 0) {
            reactionDTO.setDateUpdated(now);
        } else {
            reactionDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            reactionDTO.setDateCreated(now);
            reactionDTO.setDateUpdated(now);
        }
        return this.toDTO(reactionRepository.save(this.toEntity(reactionDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findById(Long id) {
        Optional<Reaction> reactionData =
            Optional.ofNullable(reactionRepository.findById(id)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    REACTION_NOTFOUND_WITH_ID  + id ))
                );

        return reactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<Reaction> reactionData =
            Optional.ofNullable(reactionRepository.findById(id)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        REACTION_NOTFOUND_WITH_ID  + id))
                    );
        if (reactionData.isPresent()) {
            Reaction reaction = reactionData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(ReactionConstantes.NAME)) reaction.setName((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReactionConstantes.ICON)) reaction.setIcon((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReactionConstantes.TAG)) reaction.setTag((String)entry.getValue());

        }
        if(updates.get(ReactionConstantes.DATEUPDATED) == null) reaction.setDateUpdated(new Date());
        reactionRepository.save(reaction);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO updateStatusById(Long id, String status) {
        Optional<Reaction> reactionData =
            Optional.ofNullable( reactionRepository.findById(id)
                .orElseThrow(() -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    REACTION_NOTFOUND_WITH_ID + id))
                );
        Reaction reaction = reactionData.orElseGet(Reaction::new);
        reaction.setStatus(status);
        reaction.setDateUpdated(new Date());
        return toDTO(reactionRepository.save(reaction));

    }

    @Override
    public List<ReactionDTO> findAllByStatus(String status) {
        return reactionRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<Reaction> lstReaction;
    Long id = null;
    String name = null;
    String icon = null;
    String tag = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.ICON)) icon = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.TAG)) tag = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<Reaction> paginaReaction = reactionRepository.findReactionByFilter(paging,
        id
        ,name
        ,icon
        ,tag
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstReaction = paginaReaction.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaReaction.getNumber());
    response.put("totalItems", paginaReaction.getTotalElements());
    response.put("totalPages", paginaReaction.getTotalPages());
    response.put("pageReactionItems", lstReaction.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
)
    public List<ReactionDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    String name = null;
    String icon = null;
    String tag = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.ICON)) icon = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.TAG)) tag = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<Reaction> lstReaction = reactionRepository.findReactionByFilter(
            id
            ,name
            ,icon
            ,tag
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstReaction.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public List<ReactionDTO> findAllReactionByIdAndStatus(Long id, String status) {
        return reactionRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public List<ReactionDTO> findAllReactionByNameAndStatus(String name, String status) {
        return reactionRepository.findAllByNameAndStatus(name, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public List<ReactionDTO> findAllReactionByIconAndStatus(String icon, String status) {
        return reactionRepository.findAllByIconAndStatus(icon, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public List<ReactionDTO> findAllReactionByTagAndStatus(String tag, String status) {
        return reactionRepository.findAllByTagAndStatus(tag, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public List<ReactionDTO> findAllReactionByDateCreatedAndStatus(Date dateCreated, String status) {
        return reactionRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public List<ReactionDTO> findAllReactionByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return reactionRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByIdAndStatus(Long id, String status) {
        Long maxId = reactionRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<Reaction> reactionData =
            Optional.ofNullable( reactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        REACTION_NOTFOUND_WITH_ID + id))
                );
        return reactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByIdAndStatus(Long id) {
        return this.findReactionByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByNameAndStatus(String name, String status) {
        Long maxId = reactionRepository.loadMaxIdByNameAndStatus(name, status);
        if(maxId == null) maxId = 0L;
        Optional<Reaction> reactionData =
            Optional.ofNullable( reactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_NAME + name,
                        HttpStatus.NOT_FOUND,
                        REACTION_NOTFOUND_WITH_NAME + name))
                );
        return reactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByNameAndStatus(String name) {
        return this.findReactionByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByIconAndStatus(String icon, String status) {
        Long maxId = reactionRepository.loadMaxIdByIconAndStatus(icon, status);
        if(maxId == null) maxId = 0L;
        Optional<Reaction> reactionData =
            Optional.ofNullable( reactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_ICON + icon,
                        HttpStatus.NOT_FOUND,
                        REACTION_NOTFOUND_WITH_ICON + icon))
                );
        return reactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByIconAndStatus(String icon) {
        return this.findReactionByIconAndStatus(icon, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByTagAndStatus(String tag, String status) {
        Long maxId = reactionRepository.loadMaxIdByTagAndStatus(tag, status);
        if(maxId == null) maxId = 0L;
        Optional<Reaction> reactionData =
            Optional.ofNullable( reactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_TAG + tag,
                        HttpStatus.NOT_FOUND,
                        REACTION_NOTFOUND_WITH_TAG + tag))
                );
        return reactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByTagAndStatus(String tag) {
        return this.findReactionByTagAndStatus(tag, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = reactionRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<Reaction> reactionData =
            Optional.ofNullable( reactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        REACTION_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return reactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByDateCreatedAndStatus(Date dateCreated) {
        return this.findReactionByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = reactionRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<Reaction> reactionData =
            Optional.ofNullable( reactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionNotFoundException(REACTION_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        REACTION_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return reactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionNotFoundException.class
    )
    public ReactionDTO findReactionByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findReactionByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReactionDTO updateNameById(Long id, String name) {
        findById(id);
        reactionRepository.updateNameById(id, name);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReactionDTO updateIconById(Long id, String icon) {
        findById(id);
        reactionRepository.updateIconById(id, icon);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReactionDTO updateTagById(Long id, String tag) {
        findById(id);
        reactionRepository.updateTagById(id, tag);
        return findById(id);
    }


    public ReactionDTO toDTO(Reaction reaction) {
        ReactionDTO reactionDTO = new ReactionDTO();
                reactionDTO.setId(reaction.getId());
                reactionDTO.setName(reaction.getName());
                reactionDTO.setIcon(reaction.getIcon());
                reactionDTO.setTag(reaction.getTag());
                reactionDTO.setStatus(reaction.getStatus());
                reactionDTO.setDateCreated(reaction.getDateCreated());
                reactionDTO.setDateUpdated(reaction.getDateUpdated());

        return reactionDTO;
    }

    public Reaction toEntity(ReactionDTO reactionDTO) {
        Reaction reaction = null;
        reaction = new Reaction();
                    reaction.setId(reactionDTO.getId());
                    reaction.setName(reactionDTO.getName());
                    reaction.setIcon(reactionDTO.getIcon());
                    reaction.setTag(reactionDTO.getTag());
                    reaction.setStatus(reactionDTO.getStatus());
                    reaction.setDateCreated(reactionDTO.getDateCreated());
                    reaction.setDateUpdated(reactionDTO.getDateUpdated());

        return reaction;
    }
}
