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

import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.dto.GroupRoleDTO;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.GroupRole;
import br.com.jcv.security.guardian.constantes.GroupRoleConstantes;
import br.com.jcv.security.guardian.repository.GroupRoleRepository;
import br.com.jcv.security.guardian.service.GroupRoleService;
import br.com.jcv.security.guardian.exception.GroupRoleNotFoundException;

import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
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
* GroupRoleServiceImpl - Implementation for GroupRole interface
*
* @author GroupRole
* @since Sat Nov 18 18:21:12 BRT 2023
*/


@Slf4j
@Service
public class GroupRoleServiceImpl implements GroupRoleService
{
    private static final String GROUPROLE_NOTFOUND_WITH_ID = "GroupRole não encontrada com id = ";
    private static final String GROUPROLE_NOTFOUND_WITH_IDROLE = "GroupRole não encontrada com idRole = ";
    private static final String GROUPROLE_NOTFOUND_WITH_IDGROUP = "GroupRole não encontrada com idGroup = ";
    private static final String GROUPROLE_NOTFOUND_WITH_STATUS = "GroupRole não encontrada com status = ";
    private static final String GROUPROLE_NOTFOUND_WITH_DATECREATED = "GroupRole não encontrada com dateCreated = ";
    private static final String GROUPROLE_NOTFOUND_WITH_DATEUPDATED = "GroupRole não encontrada com dateUpdated = ";


    @Autowired private GroupRoleRepository grouproleRepository;
    @Autowired private DateTime dateTime;
    @Autowired private @Qualifier("redisService") CacheProvider redisProvider;
    @Autowired private Gson gson;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupRoleNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando GroupRole com id = {}", id);
        grouproleRepository.findById(id)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GROUPROLE_NOTFOUND_WITH_ID  + id));
        grouproleRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO salvar(GroupRoleDTO grouproleDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(grouproleDTO.getId()) && grouproleDTO.getId() != 0) {
            grouproleDTO.setDateUpdated(now);
        } else {
            grouproleDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            grouproleDTO.setDateCreated(now);
            grouproleDTO.setDateUpdated(now);
        }
        return this.toDTO(grouproleRepository.save(this.toEntity(grouproleDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findById(Long id) {

        GroupRoleDTO cache = redisProvider.getValue("applicationUser-findById-" + id,GroupRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Optional<GroupRole> grouproleData =
            Optional.ofNullable(grouproleRepository.findById(id)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GROUPROLE_NOTFOUND_WITH_ID  + id ))
                );

        GroupRoleDTO response = grouproleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-findById-" + id, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupRoleNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<GroupRole> grouproleData =
            Optional.ofNullable(grouproleRepository.findById(id)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GROUPROLE_NOTFOUND_WITH_ID  + id))
                    );
        if (grouproleData.isPresent()) {
            GroupRole grouprole = grouproleData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.IDROLE)) grouprole.setIdRole((Long)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.IDGROUP)) grouprole.setIdGroup((Long)entry.getValue());

        }
        if(updates.get(GroupRoleConstantes.DATEUPDATED) == null) grouprole.setDateUpdated(new Date());
        grouproleRepository.save(grouprole);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO updateStatusById(Long id, String status) {
        Optional<GroupRole> grouproleData =
            Optional.ofNullable( grouproleRepository.findById(id)
                .orElseThrow(() -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GROUPROLE_NOTFOUND_WITH_ID + id))
                );
        GroupRole grouprole = grouproleData.orElseGet(GroupRole::new);
        grouprole.setStatus(status);
        grouprole.setDateUpdated(new Date());
        return toDTO(grouproleRepository.save(grouprole));

    }

    @Override
    public List<GroupRoleDTO> findAllByStatus(String status) {
        return grouproleRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<GroupRole> lstGroupRole;
    Long id = null;
    Long idRole = null;
    Long idGroup = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.IDROLE)) idRole = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.IDGROUP)) idGroup = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<GroupRole> paginaGroupRole = grouproleRepository.findGroupRoleByFilter(paging,
        id
        ,idRole
        ,idGroup
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstGroupRole = paginaGroupRole.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaGroupRole.getNumber());
    response.put("totalItems", paginaGroupRole.getTotalElements());
    response.put("totalPages", paginaGroupRole.getTotalPages());
    response.put("pageGroupRoleItems", lstGroupRole.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
)
    public List<GroupRoleDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long idRole = null;
    Long idGroup = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.IDROLE)) idRole = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.IDGROUP)) idGroup = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupRoleConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<GroupRole> lstGroupRole = grouproleRepository.findGroupRoleByFilter(
            id
            ,idRole
            ,idGroup
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstGroupRole.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public List<GroupRoleDTO> findAllGroupRoleByIdAndStatus(Long id, String status) {
        return grouproleRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public List<GroupRoleDTO> findAllGroupRoleByIdRoleAndStatus(Long idRole, String status) {
        return grouproleRepository.findAllByIdRoleAndStatus(idRole, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public List<GroupRoleDTO> findAllGroupRoleByIdGroupAndStatus(Long idGroup, String status) {
        return grouproleRepository.findAllByIdGroupAndStatus(idGroup, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public List<GroupRoleDTO> findAllGroupRoleByDateCreatedAndStatus(Date dateCreated, String status) {
        return grouproleRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public List<GroupRoleDTO> findAllGroupRoleByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return grouproleRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByIdAndStatus(Long id, String status) {

        GroupRoleDTO cache = redisProvider.getValue("application-cache-" + id + status,GroupRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = grouproleRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupRole> grouproleData =
            Optional.ofNullable( grouproleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        GROUPROLE_NOTFOUND_WITH_ID + id))
                );

        GroupRoleDTO response = grouproleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("application-cache-" + id + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByIdAndStatus(Long id) {
        return this.findGroupRoleByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByIdRoleAndStatus(Long idRole, String status) {

        GroupRoleDTO cache = redisProvider.getValue("application-cache-" + idRole + status,GroupRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = grouproleRepository.loadMaxIdByIdRoleAndStatus(idRole, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupRole> grouproleData =
            Optional.ofNullable( grouproleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_IDROLE + idRole,
                        HttpStatus.NOT_FOUND,
                        GROUPROLE_NOTFOUND_WITH_IDROLE + idRole))
                );

        GroupRoleDTO response = grouproleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("application-cache-" + idRole + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByIdRoleAndStatus(Long idRole) {
        return this.findGroupRoleByIdRoleAndStatus(idRole, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByIdGroupAndStatus(Long idGroup, String status) {

        GroupRoleDTO cache = redisProvider.getValue("application-cache-" + idGroup + status,GroupRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = grouproleRepository.loadMaxIdByIdGroupAndStatus(idGroup, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupRole> grouproleData =
            Optional.ofNullable( grouproleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_IDGROUP + idGroup,
                        HttpStatus.NOT_FOUND,
                        GROUPROLE_NOTFOUND_WITH_IDGROUP + idGroup))
                );

        GroupRoleDTO response = grouproleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("application-cache-" + idGroup + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByIdGroupAndIdRoleAndStatus(Long idGroup, Long idRole, String status) {

        GroupRoleDTO cache = redisProvider.getValue("application-cache-" + idGroup + idRole + status,GroupRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = grouproleRepository.loadMaxIdByIdGroupAndIdRoleAndStatus(idGroup, idRole, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupRole> grouproleData =
            Optional.ofNullable( grouproleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_IDGROUP + idGroup,
                        HttpStatus.NOT_FOUND,
                        GROUPROLE_NOTFOUND_WITH_IDGROUP + idGroup))
                );

        GroupRoleDTO response = grouproleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("application-cache-" + idGroup + idRole + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByIdGroupAndStatus(Long idGroup) {
        return this.findGroupRoleByIdGroupAndStatus(idGroup, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = grouproleRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupRole> grouproleData =
            Optional.ofNullable( grouproleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        GROUPROLE_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return grouproleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByDateCreatedAndStatus(Date dateCreated) {
        return this.findGroupRoleByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = grouproleRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupRole> grouproleData =
            Optional.ofNullable( grouproleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupRoleNotFoundException(GROUPROLE_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        GROUPROLE_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return grouproleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupRoleNotFoundException.class
    )
    public GroupRoleDTO findGroupRoleByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findGroupRoleByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GroupRoleDTO updateIdRoleById(Long id, Long idRole) {
        findById(id);
        grouproleRepository.updateIdRoleById(id, idRole);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GroupRoleDTO updateIdGroupById(Long id, Long idGroup) {
        findById(id);
        grouproleRepository.updateIdGroupById(id, idGroup);
        return findById(id);
    }


    public GroupRoleDTO toDTO(GroupRole grouprole) {
        GroupRoleDTO grouproleDTO = new GroupRoleDTO();
                grouproleDTO.setId(grouprole.getId());
                grouproleDTO.setIdRole(grouprole.getIdRole());
                grouproleDTO.setIdGroup(grouprole.getIdGroup());
                grouproleDTO.setStatus(grouprole.getStatus());
                grouproleDTO.setDateCreated(grouprole.getDateCreated());
                grouproleDTO.setDateUpdated(grouprole.getDateUpdated());

        return grouproleDTO;
    }

    public GroupRole toEntity(GroupRoleDTO grouproleDTO) {
        GroupRole grouprole = null;
        grouprole = new GroupRole();
                    grouprole.setId(grouproleDTO.getId());
                    grouprole.setIdRole(grouproleDTO.getIdRole());
                    grouprole.setIdGroup(grouproleDTO.getIdGroup());
                    grouprole.setStatus(grouproleDTO.getStatus());
                    grouprole.setDateCreated(grouproleDTO.getDateCreated());
                    grouprole.setDateUpdated(grouproleDTO.getDateUpdated());

        return grouprole;
    }
}
