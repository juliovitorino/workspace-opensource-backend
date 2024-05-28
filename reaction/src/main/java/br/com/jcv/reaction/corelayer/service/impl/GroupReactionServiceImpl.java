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
import br.com.jcv.reaction.corelayer.model.GroupReaction;
import br.com.jcv.reaction.corelayer.repository.GroupReactionRepository;
import br.com.jcv.reaction.corelayer.service.GroupReactionService;
import br.com.jcv.reaction.infrastructure.constantes.GroupReactionConstantes;
import br.com.jcv.reaction.infrastructure.dto.GroupReactionDTO;
import br.com.jcv.reaction.infrastructure.exception.GroupReactionNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* GroupReactionServiceImpl - Implementation for GroupReaction interface
*
* @author GroupReaction
* @since Tue May 28 16:22:55 BRT 2024
*/


@Slf4j
@Service
public class GroupReactionServiceImpl implements GroupReactionService
{
    private static final String GROUPREACTION_NOTFOUND_WITH_ID = "GroupReaction não encontrada com id = ";
    private static final String GROUPREACTION_NOTFOUND_WITH_GROUPID = "GroupReaction não encontrada com groupId = ";
    private static final String GROUPREACTION_NOTFOUND_WITH_REACTIONID = "GroupReaction não encontrada com reactionId = ";
    private static final String GROUPREACTION_NOTFOUND_WITH_STATUS = "GroupReaction não encontrada com status = ";
    private static final String GROUPREACTION_NOTFOUND_WITH_DATECREATED = "GroupReaction não encontrada com dateCreated = ";
    private static final String GROUPREACTION_NOTFOUND_WITH_DATEUPDATED = "GroupReaction não encontrada com dateUpdated = ";


    @Autowired private GroupReactionRepository groupreactionRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupReactionNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando GroupReaction com id = {}", id);
        groupreactionRepository.findById(id)
                .orElseThrow(
                    () -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GROUPREACTION_NOTFOUND_WITH_ID  + id));
        groupreactionRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO salvar(GroupReactionDTO groupreactionDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(groupreactionDTO.getId()) && groupreactionDTO.getId() != 0) {
            groupreactionDTO.setDateUpdated(now);
        } else {
            groupreactionDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            groupreactionDTO.setDateCreated(now);
            groupreactionDTO.setDateUpdated(now);
        }
        return this.toDTO(groupreactionRepository.save(this.toEntity(groupreactionDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findById(Long id) {
        Optional<GroupReaction> groupreactionData =
            Optional.ofNullable(groupreactionRepository.findById(id)
                .orElseThrow(
                    () -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GROUPREACTION_NOTFOUND_WITH_ID  + id ))
                );

        return groupreactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupReactionNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<GroupReaction> groupreactionData =
            Optional.ofNullable(groupreactionRepository.findById(id)
                .orElseThrow(
                    () -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GROUPREACTION_NOTFOUND_WITH_ID  + id))
                    );
        if (groupreactionData.isPresent()) {
            GroupReaction groupreaction = groupreactionData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.GROUPID)) groupreaction.setGroupId((Long)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.REACTIONID)) groupreaction.setReactionId((Long)entry.getValue());

        }
        if(updates.get(GroupReactionConstantes.DATEUPDATED) == null) groupreaction.setDateUpdated(new Date());
        groupreactionRepository.save(groupreaction);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO updateStatusById(Long id, String status) {
        Optional<GroupReaction> groupreactionData =
            Optional.ofNullable( groupreactionRepository.findById(id)
                .orElseThrow(() -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GROUPREACTION_NOTFOUND_WITH_ID + id))
                );
        GroupReaction groupreaction = groupreactionData.orElseGet(GroupReaction::new);
        groupreaction.setStatus(status);
        groupreaction.setDateUpdated(new Date());
        return toDTO(groupreactionRepository.save(groupreaction));

    }

    @Override
    public List<GroupReactionDTO> findAllByStatus(String status) {
        return groupreactionRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<GroupReaction> lstGroupReaction;
    Long id = null;
    Long groupId = null;
    Long reactionId = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.GROUPID)) groupId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.REACTIONID)) reactionId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<GroupReaction> paginaGroupReaction = groupreactionRepository.findGroupReactionByFilter(paging,
        id
        ,groupId
        ,reactionId
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstGroupReaction = paginaGroupReaction.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaGroupReaction.getNumber());
    response.put("totalItems", paginaGroupReaction.getTotalElements());
    response.put("totalPages", paginaGroupReaction.getTotalPages());
    response.put("pageGroupReactionItems", lstGroupReaction.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
)
    public List<GroupReactionDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long groupId = null;
    Long reactionId = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.GROUPID)) groupId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.REACTIONID)) reactionId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupReactionConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<GroupReaction> lstGroupReaction = groupreactionRepository.findGroupReactionByFilter(
            id
            ,groupId
            ,reactionId
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstGroupReaction.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public List<GroupReactionDTO> findAllGroupReactionByIdAndStatus(Long id, String status) {
        return groupreactionRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public List<GroupReactionDTO> findAllGroupReactionByGroupIdAndStatus(Long groupId, String status) {
        return groupreactionRepository.findAllByGroupIdAndStatus(groupId, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public List<GroupReactionDTO> findAllGroupReactionByReactionIdAndStatus(Long reactionId, String status) {
        return groupreactionRepository.findAllByReactionIdAndStatus(reactionId, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public List<GroupReactionDTO> findAllGroupReactionByDateCreatedAndStatus(Date dateCreated, String status) {
        return groupreactionRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public List<GroupReactionDTO> findAllGroupReactionByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return groupreactionRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByIdAndStatus(Long id, String status) {
        Long maxId = groupreactionRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupReaction> groupreactionData =
            Optional.ofNullable( groupreactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        GROUPREACTION_NOTFOUND_WITH_ID + id))
                );
        return groupreactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByIdAndStatus(Long id) {
        return this.findGroupReactionByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByGroupIdAndStatus(Long groupId, String status) {
        Long maxId = groupreactionRepository.loadMaxIdByGroupIdAndStatus(groupId, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupReaction> groupreactionData =
            Optional.ofNullable( groupreactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_GROUPID + groupId,
                        HttpStatus.NOT_FOUND,
                        GROUPREACTION_NOTFOUND_WITH_GROUPID + groupId))
                );
        return groupreactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByGroupIdAndStatus(Long groupId) {
        return this.findGroupReactionByGroupIdAndStatus(groupId, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByReactionIdAndStatus(Long reactionId, String status) {
        Long maxId = groupreactionRepository.loadMaxIdByReactionIdAndStatus(reactionId, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupReaction> groupreactionData =
            Optional.ofNullable( groupreactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_REACTIONID + reactionId,
                        HttpStatus.NOT_FOUND,
                        GROUPREACTION_NOTFOUND_WITH_REACTIONID + reactionId))
                );
        return groupreactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByReactionIdAndStatus(Long reactionId) {
        return this.findGroupReactionByReactionIdAndStatus(reactionId, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = groupreactionRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupReaction> groupreactionData =
            Optional.ofNullable( groupreactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        GROUPREACTION_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return groupreactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByDateCreatedAndStatus(Date dateCreated) {
        return this.findGroupReactionByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = groupreactionRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupReaction> groupreactionData =
            Optional.ofNullable( groupreactionRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupReactionNotFoundException(GROUPREACTION_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        GROUPREACTION_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return groupreactionData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupReactionNotFoundException.class
    )
    public GroupReactionDTO findGroupReactionByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findGroupReactionByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GroupReactionDTO updateGroupIdById(Long id, Long groupId) {
        findById(id);
        groupreactionRepository.updateGroupIdById(id, groupId);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GroupReactionDTO updateReactionIdById(Long id, Long reactionId) {
        findById(id);
        groupreactionRepository.updateReactionIdById(id, reactionId);
        return findById(id);
    }


    public GroupReactionDTO toDTO(GroupReaction groupreaction) {
        GroupReactionDTO groupreactionDTO = new GroupReactionDTO();
                groupreactionDTO.setId(groupreaction.getId());
                groupreactionDTO.setGroupId(groupreaction.getGroupId());
                groupreactionDTO.setReactionId(groupreaction.getReactionId());
                groupreactionDTO.setStatus(groupreaction.getStatus());
                groupreactionDTO.setDateCreated(groupreaction.getDateCreated());
                groupreactionDTO.setDateUpdated(groupreaction.getDateUpdated());

        return groupreactionDTO;
    }

    public GroupReaction toEntity(GroupReactionDTO groupreactionDTO) {
        GroupReaction groupreaction = null;
        groupreaction = new GroupReaction();
                    groupreaction.setId(groupreactionDTO.getId());
                    groupreaction.setGroupId(groupreactionDTO.getGroupId());
                    groupreaction.setReactionId(groupreactionDTO.getReactionId());
                    groupreaction.setStatus(groupreactionDTO.getStatus());
                    groupreaction.setDateCreated(groupreactionDTO.getDateCreated());
                    groupreaction.setDateUpdated(groupreactionDTO.getDateUpdated());

        return groupreaction;
    }
}
