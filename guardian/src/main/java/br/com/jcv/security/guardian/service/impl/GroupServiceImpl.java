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


package br.com.jcv.security.guardian.service.impl;

import br.com.jcv.commons.library.commodities.constantes.GenericConstantes;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;

import br.com.jcv.security.guardian.dto.GroupDTO;
import br.com.jcv.security.guardian.model.Group;
import br.com.jcv.security.guardian.constantes.GroupConstantes;
import br.com.jcv.security.guardian.repository.GroupRepository;
import br.com.jcv.security.guardian.service.GroupService;
import br.com.jcv.security.guardian.exception.GroupNotFoundException;

import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.text.ParseException;
import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.extern.slf4j.Slf4j;


/**
* GroupServiceImpl - Implementation for Group interface
*
* @author Group
* @since Sat Nov 18 18:21:11 BRT 2023
*/


@Slf4j
@Service
public class GroupServiceImpl implements GroupService
{
    private static final String GROUP_NOTFOUND_WITH_ID = "Group não encontrada com id = ";
    private static final String GROUP_NOTFOUND_WITH_NAME = "Group não encontrada com name = ";
    private static final String GROUP_NOTFOUND_WITH_STATUS = "Group não encontrada com status = ";
    private static final String GROUP_NOTFOUND_WITH_DATECREATED = "Group não encontrada com dateCreated = ";
    private static final String GROUP_NOTFOUND_WITH_DATEUPDATED = "Group não encontrada com dateUpdated = ";


    @Autowired private GroupRepository groupRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando Group com id = {}", id);
        groupRepository.findById(id)
                .orElseThrow(
                    () -> new GroupNotFoundException(GROUP_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GROUP_NOTFOUND_WITH_ID  + id));
        groupRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO salvar(GroupDTO groupDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(groupDTO.getId()) && groupDTO.getId() != 0) {
            groupDTO.setDateUpdated(now);
        } else {
            groupDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            groupDTO.setDateCreated(now);
            groupDTO.setDateUpdated(now);
        }
        return this.toDTO(groupRepository.save(this.toEntity(groupDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findById(Long id) {
        Optional<Group> groupData =
            Optional.ofNullable(groupRepository.findById(id)
                .orElseThrow(
                    () -> new GroupNotFoundException(GROUP_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GROUP_NOTFOUND_WITH_ID  + id ))
                );

        return groupData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<Group> groupData =
            Optional.ofNullable(groupRepository.findById(id)
                .orElseThrow(
                    () -> new GroupNotFoundException(GROUP_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GROUP_NOTFOUND_WITH_ID  + id))
                    );
        if (groupData.isPresent()) {
            Group group = groupData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(GroupConstantes.NAME)) group.setName(entry.getValue().toString());

        }
        if(updates.get(GroupConstantes.DATEUPDATED) == null) group.setDateUpdated(new Date());
        groupRepository.save(group);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO updateStatusById(Long id, String status) {
        Optional<Group> groupData =
            Optional.ofNullable( groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(GROUP_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GROUP_NOTFOUND_WITH_ID + id))
                );
        Group group = groupData.orElseGet(Group::new);
        group.setStatus(status);
        group.setDateUpdated(new Date());
        return toDTO(groupRepository.save(group));

    }

    @Override
    public List<GroupDTO> findAllByStatus(String status) {
        return groupRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<Group> lstGroup;
    Long id = null;
    String name = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<Group> paginaGroup = groupRepository.findGroupByFilter(paging,
        id
        ,name
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstGroup = paginaGroup.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaGroup.getNumber());
    response.put("totalItems", paginaGroup.getTotalElements());
    response.put("totalPages", paginaGroup.getTotalPages());
    response.put("pageGroupItems", lstGroup.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
)
    public List<GroupDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    String name = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<Group> lstGroup = groupRepository.findGroupByFilter(
            id
            ,name
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstGroup.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public List<GroupDTO> findAllGroupByIdAndStatus(Long id, String status) {
        return groupRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public List<GroupDTO> findAllGroupByNameAndStatus(String name, String status) {
        return groupRepository.findAllByNameAndStatus(name, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public List<GroupDTO> findAllGroupByDateCreatedAndStatus(Date dateCreated, String status) {
        return groupRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public List<GroupDTO> findAllGroupByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return groupRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findGroupByIdAndStatus(Long id, String status) {
        Long maxId = groupRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<Group> groupData =
            Optional.ofNullable( groupRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupNotFoundException(GROUP_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        GROUP_NOTFOUND_WITH_ID + id))
                );
        return groupData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findGroupByIdAndStatus(Long id) {
        return this.findGroupByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findGroupByNameAndStatus(String name, String status) {
        Long maxId = groupRepository.loadMaxIdByNameAndStatus(name, status);
        if(maxId == null) maxId = 0L;
        Optional<Group> groupData =
            Optional.ofNullable( groupRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupNotFoundException(GROUP_NOTFOUND_WITH_NAME + name,
                        HttpStatus.NOT_FOUND,
                        GROUP_NOTFOUND_WITH_NAME + name))
                );
        return groupData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findGroupByNameAndStatus(String name) {
        return this.findGroupByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findGroupByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = groupRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<Group> groupData =
            Optional.ofNullable( groupRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupNotFoundException(GROUP_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        GROUP_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return groupData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findGroupByDateCreatedAndStatus(Date dateCreated) {
        return this.findGroupByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findGroupByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = groupRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<Group> groupData =
            Optional.ofNullable( groupRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupNotFoundException(GROUP_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        GROUP_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return groupData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupNotFoundException.class
    )
    public GroupDTO findGroupByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findGroupByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GroupDTO updateNameById(Long id, String name) {
        findById(id);
        groupRepository.updateNameById(id, name);
        return findById(id);
    }


    public GroupDTO toDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO();
                groupDTO.setId(group.getId());
                groupDTO.setName(group.getName());
                groupDTO.setStatus(group.getStatus());
                groupDTO.setDateCreated(group.getDateCreated());
                groupDTO.setDateUpdated(group.getDateUpdated());

        return groupDTO;
    }

    public Group toEntity(GroupDTO groupDTO) {
        Group group = null;
        group = new Group();
                    group.setId(groupDTO.getId());
                    group.setName(groupDTO.getName());
                    group.setStatus(groupDTO.getStatus());
                    group.setDateCreated(groupDTO.getDateCreated());
                    group.setDateUpdated(groupDTO.getDateUpdated());

        return group;
    }
}
