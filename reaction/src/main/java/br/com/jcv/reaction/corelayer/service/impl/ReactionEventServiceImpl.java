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
import java.util.UUID;
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
import br.com.jcv.reaction.corelayer.model.ReactionEvent;
import br.com.jcv.reaction.corelayer.repository.ReactionEventRepository;
import br.com.jcv.reaction.corelayer.service.ReactionEventService;
import br.com.jcv.reaction.infrastructure.constantes.ReactionEventConstantes;
import br.com.jcv.reaction.infrastructure.dto.ReactionEventDTO;
import br.com.jcv.reaction.infrastructure.exception.ReactionEventNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* ReactionEventServiceImpl - Implementation for ReactionEvent interface
*
* @author ReactionEvent
* @since Tue May 28 16:48:27 BRT 2024
*/


@Slf4j
@Service
public class ReactionEventServiceImpl implements ReactionEventService
{
    private static final String REACTIONEVENT_NOTFOUND_WITH_ID = "ReactionEvent não encontrada com id = ";
    private static final String REACTIONEVENT_NOTFOUND_WITH_REACTIONID = "ReactionEvent não encontrada com reactionId = ";
    private static final String REACTIONEVENT_NOTFOUND_WITH_EXTERNALITEMUUID = "ReactionEvent não encontrada com externalItemUUID = ";
    private static final String REACTIONEVENT_NOTFOUND_WITH_EXTERNALAPPUUID = "ReactionEvent não encontrada com externalAppUUID = ";
    private static final String REACTIONEVENT_NOTFOUND_WITH_EXTERNALUSERUUID = "ReactionEvent não encontrada com externalUserUUID = ";
    private static final String REACTIONEVENT_NOTFOUND_WITH_STATUS = "ReactionEvent não encontrada com status = ";
    private static final String REACTIONEVENT_NOTFOUND_WITH_DATECREATED = "ReactionEvent não encontrada com dateCreated = ";
    private static final String REACTIONEVENT_NOTFOUND_WITH_DATEUPDATED = "ReactionEvent não encontrada com dateUpdated = ";


    @Autowired private ReactionEventRepository reactioneventRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionEventNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando ReactionEvent com id = {}", id);
        reactioneventRepository.findById(id)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_ID  + id));
        reactioneventRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO salvar(ReactionEventDTO reactioneventDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(reactioneventDTO.getId()) && reactioneventDTO.getId() != 0) {
            reactioneventDTO.setDateUpdated(now);
        } else {
            reactioneventDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            reactioneventDTO.setDateCreated(now);
            reactioneventDTO.setDateUpdated(now);
        }
        return this.toDTO(reactioneventRepository.save(this.toEntity(reactioneventDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findById(Long id) {
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable(reactioneventRepository.findById(id)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    REACTIONEVENT_NOTFOUND_WITH_ID  + id ))
                );

        return reactioneventData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionEventNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable(reactioneventRepository.findById(id)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_ID  + id))
                    );
        if (reactioneventData.isPresent()) {
            ReactionEvent reactionevent = reactioneventData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.REACTIONID)) reactionevent.setReactionId((Long)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALITEMUUID)) reactionevent.setExternalItemUUID((UUID) entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALAPPUUID)) reactionevent.setExternalAppUUID((UUID) entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALUSERUUID)) reactionevent.setExternalUserUUID((UUID)entry.getValue());

        }
        if(updates.get(ReactionEventConstantes.DATEUPDATED) == null) reactionevent.setDateUpdated(new Date());
        reactioneventRepository.save(reactionevent);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO updateStatusById(Long id, String status) {
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository.findById(id)
                .orElseThrow(() -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    REACTIONEVENT_NOTFOUND_WITH_ID + id))
                );
        ReactionEvent reactionevent = reactioneventData.orElseGet(ReactionEvent::new);
        reactionevent.setStatus(status);
        reactionevent.setDateUpdated(new Date());
        return toDTO(reactioneventRepository.save(reactionevent));

    }

    @Override
    public List<ReactionEventDTO> findAllByStatus(String status) {
        return reactioneventRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<ReactionEvent> lstReactionEvent;
    Long id = null;
    Long reactionId = null;
    Long externalItemUUID = null;
    Long externalAppUUID = null;
    Long externalUserUUID = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.REACTIONID)) reactionId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALITEMUUID)) externalItemUUID = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALAPPUUID)) externalAppUUID = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALUSERUUID)) externalUserUUID = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<ReactionEvent> paginaReactionEvent = reactioneventRepository.findReactionEventByFilter(paging,
        id
        ,reactionId
        ,externalItemUUID
        ,externalAppUUID
        ,externalUserUUID
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstReactionEvent = paginaReactionEvent.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaReactionEvent.getNumber());
    response.put("totalItems", paginaReactionEvent.getTotalElements());
    response.put("totalPages", paginaReactionEvent.getTotalPages());
    response.put("pageReactionEventItems", lstReactionEvent.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
)
    public List<ReactionEventDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long reactionId = null;
    Long externalItemUUID = null;
    Long externalAppUUID = null;
    Long externalUserUUID = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.REACTIONID)) reactionId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALITEMUUID)) externalItemUUID = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALAPPUUID)) externalAppUUID = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.EXTERNALUSERUUID)) externalUserUUID = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReactionEventConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<ReactionEvent> lstReactionEvent = reactioneventRepository.findReactionEventByFilter(
            id
            ,reactionId
            ,externalItemUUID
            ,externalAppUUID
            ,externalUserUUID
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstReactionEvent.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public List<ReactionEventDTO> findAllReactionEventByIdAndStatus(Long id, String status) {
        return reactioneventRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public List<ReactionEventDTO> findAllReactionEventByReactionIdAndStatus(Long reactionId, String status) {
        return reactioneventRepository.findAllByReactionIdAndStatus(reactionId, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public List<ReactionEventDTO> findAllReactionEventByExternalItemUUIDAndStatus(Long externalItemUUID, String status) {
        return reactioneventRepository.findAllByExternalItemUUIDAndStatus(externalItemUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public List<ReactionEventDTO> findAllReactionEventByExternalAppUUIDAndStatus(Long externalAppUUID, String status) {
        return reactioneventRepository.findAllByExternalAppUUIDAndStatus(externalAppUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public List<ReactionEventDTO> findAllReactionEventByExternalUserUUIDAndStatus(Long externalUserUUID, String status) {
        return reactioneventRepository.findAllByExternalUserUUIDAndStatus(externalUserUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public List<ReactionEventDTO> findAllReactionEventByDateCreatedAndStatus(Date dateCreated, String status) {
        return reactioneventRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public List<ReactionEventDTO> findAllReactionEventByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return reactioneventRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByIdAndStatus(Long id, String status) {
        Long maxId = reactioneventRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_ID + id))
                );
        return reactioneventData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByIdAndStatus(Long id) {
        return this.findReactionEventByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByReactionIdAndStatus(Long reactionId, String status) {
        Long maxId = reactioneventRepository.loadMaxIdByReactionIdAndStatus(reactionId, status);
        if(maxId == null) maxId = 0L;
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_REACTIONID + reactionId,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_REACTIONID + reactionId))
                );
        return reactioneventData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByReactionIdAndStatus(Long reactionId) {
        return this.findReactionEventByReactionIdAndStatus(reactionId, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByExternalItemUUIDAndStatus(Long externalItemUUID, String status) {
        Long maxId = reactioneventRepository.loadMaxIdByExternalItemUUIDAndStatus(externalItemUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_EXTERNALITEMUUID + externalItemUUID,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_EXTERNALITEMUUID + externalItemUUID))
                );
        return reactioneventData.map(this::toDTO).orElse(null);
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByHashMD5AndStatus(String hashMD5, String status) {
        Long maxId = reactioneventRepository.loadMaxIdByHashMD5AndStatus(hashMD5, status);
        if(maxId == null) maxId = 0L;
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_EXTERNALITEMUUID + hashMD5,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_EXTERNALITEMUUID + hashMD5))
                );
        return reactioneventData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByExternalItemUUIDAndStatus(Long externalItemUUID) {
        return this.findReactionEventByExternalItemUUIDAndStatus(externalItemUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByExternalAppUUIDAndStatus(Long externalAppUUID, String status) {
        Long maxId = reactioneventRepository.loadMaxIdByExternalAppUUIDAndStatus(externalAppUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_EXTERNALAPPUUID + externalAppUUID,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_EXTERNALAPPUUID + externalAppUUID))
                );
        return reactioneventData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByExternalAppUUIDAndStatus(Long externalAppUUID) {
        return this.findReactionEventByExternalAppUUIDAndStatus(externalAppUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByExternalUserUUIDAndStatus(Long externalUserUUID, String status) {
        Long maxId = reactioneventRepository.loadMaxIdByExternalUserUUIDAndStatus(externalUserUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_EXTERNALUSERUUID + externalUserUUID,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_EXTERNALUSERUUID + externalUserUUID))
                );
        return reactioneventData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByExternalUserUUIDAndStatus(Long externalUserUUID) {
        return this.findReactionEventByExternalUserUUIDAndStatus(externalUserUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = reactioneventRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return reactioneventData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByDateCreatedAndStatus(Date dateCreated) {
        return this.findReactionEventByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = reactioneventRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<ReactionEvent> reactioneventData =
            Optional.ofNullable( reactioneventRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReactionEventNotFoundException(REACTIONEVENT_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        REACTIONEVENT_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return reactioneventData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReactionEventNotFoundException.class
    )
    public ReactionEventDTO findReactionEventByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findReactionEventByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReactionEventDTO updateReactionIdById(Long id, Long reactionId) {
        findById(id);
        reactioneventRepository.updateReactionIdById(id, reactionId);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReactionEventDTO updateExternalItemUUIDById(Long id, Long externalItemUUID) {
        findById(id);
        reactioneventRepository.updateExternalItemUUIDById(id, externalItemUUID);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReactionEventDTO updateExternalAppUUIDById(Long id, Long externalAppUUID) {
        findById(id);
        reactioneventRepository.updateExternalAppUUIDById(id, externalAppUUID);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReactionEventDTO updateExternalUserUUIDById(Long id, Long externalUserUUID) {
        findById(id);
        reactioneventRepository.updateExternalUserUUIDById(id, externalUserUUID);
        return findById(id);
    }


    public ReactionEventDTO toDTO(ReactionEvent reactionevent) {
        ReactionEventDTO reactioneventDTO = new ReactionEventDTO();
                reactioneventDTO.setId(reactionevent.getId());
                reactioneventDTO.setReactionId(reactionevent.getReactionId());
                reactioneventDTO.setExternalItemUUID(reactionevent.getExternalItemUUID());
                reactioneventDTO.setExternalAppUUID(reactionevent.getExternalAppUUID());
                reactioneventDTO.setExternalUserUUID(reactionevent.getExternalUserUUID());
                reactioneventDTO.setHashMD5(reactionevent.getHashMD5());
                reactioneventDTO.setStatus(reactionevent.getStatus());
                reactioneventDTO.setDateCreated(reactionevent.getDateCreated());
                reactioneventDTO.setDateUpdated(reactionevent.getDateUpdated());

        return reactioneventDTO;
    }

    public ReactionEvent toEntity(ReactionEventDTO reactioneventDTO) {
        ReactionEvent reactionevent = null;
        reactionevent = new ReactionEvent();
        reactionevent.setId(reactioneventDTO.getId());
        reactionevent.setReactionId(reactioneventDTO.getReactionId());
        reactionevent.setExternalItemUUID(reactioneventDTO.getExternalItemUUID());
        reactionevent.setExternalAppUUID(reactioneventDTO.getExternalAppUUID());
        reactionevent.setExternalUserUUID(reactioneventDTO.getExternalUserUUID());
        reactionevent.setHashMD5(reactioneventDTO.getHashMD5());
        reactionevent.setStatus(reactioneventDTO.getStatus());
        reactionevent.setDateCreated(reactioneventDTO.getDateCreated());
        reactionevent.setDateUpdated(reactioneventDTO.getDateUpdated());

        return reactionevent;
    }
}
