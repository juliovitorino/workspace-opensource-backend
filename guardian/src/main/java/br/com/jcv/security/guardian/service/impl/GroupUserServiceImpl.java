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

import br.com.jcv.security.guardian.dto.GroupUserDTO;
import br.com.jcv.security.guardian.model.GroupUser;
import br.com.jcv.security.guardian.constantes.GroupUserConstantes;
import br.com.jcv.security.guardian.repository.GroupUserRepository;
import br.com.jcv.security.guardian.service.GroupUserService;
import br.com.jcv.security.guardian.exception.GroupUserNotFoundException;

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
* GroupUserServiceImpl - Implementation for GroupUser interface
*
* @author GroupUser
* @since Sat Nov 18 18:21:13 BRT 2023
*/


@Slf4j
@Service
public class GroupUserServiceImpl implements GroupUserService
{
    private static final String GROUPUSER_NOTFOUND_WITH_ID = "GroupUser não encontrada com id = ";
    private static final String GROUPUSER_NOTFOUND_WITH_IDUSER = "GroupUser não encontrada com idUser = ";
    private static final String GROUPUSER_NOTFOUND_WITH_IDGROUP = "GroupUser não encontrada com idGroup = ";
    private static final String GROUPUSER_NOTFOUND_WITH_STATUS = "GroupUser não encontrada com status = ";
    private static final String GROUPUSER_NOTFOUND_WITH_DATECREATED = "GroupUser não encontrada com dateCreated = ";
    private static final String GROUPUSER_NOTFOUND_WITH_DATEUPDATED = "GroupUser não encontrada com dateUpdated = ";


    @Autowired private GroupUserRepository groupuserRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupUserNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando GroupUser com id = {}", id);
        groupuserRepository.findById(id)
                .orElseThrow(
                    () -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GROUPUSER_NOTFOUND_WITH_ID  + id));
        groupuserRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO salvar(GroupUserDTO groupuserDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(groupuserDTO.getId()) && groupuserDTO.getId() != 0) {
            groupuserDTO.setDateUpdated(now);
        } else {
            groupuserDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            groupuserDTO.setDateCreated(now);
            groupuserDTO.setDateUpdated(now);
        }
        return this.toDTO(groupuserRepository.save(this.toEntity(groupuserDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findById(Long id) {
        Optional<GroupUser> groupuserData =
            Optional.ofNullable(groupuserRepository.findById(id)
                .orElseThrow(
                    () -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GROUPUSER_NOTFOUND_WITH_ID  + id ))
                );

        return groupuserData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupUserNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<GroupUser> groupuserData =
            Optional.ofNullable(groupuserRepository.findById(id)
                .orElseThrow(
                    () -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GROUPUSER_NOTFOUND_WITH_ID  + id))
                    );
        if (groupuserData.isPresent()) {
            GroupUser groupuser = groupuserData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.IDUSER)) groupuser.setIdUser((Long)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.IDGROUP)) groupuser.setIdGroup((Long)entry.getValue());

        }
        if(updates.get(GroupUserConstantes.DATEUPDATED) == null) groupuser.setDateUpdated(new Date());
        groupuserRepository.save(groupuser);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO updateStatusById(Long id, String status) {
        Optional<GroupUser> groupuserData =
            Optional.ofNullable( groupuserRepository.findById(id)
                .orElseThrow(() -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GROUPUSER_NOTFOUND_WITH_ID + id))
                );
        GroupUser groupuser = groupuserData.orElseGet(GroupUser::new);
        groupuser.setStatus(status);
        groupuser.setDateUpdated(new Date());
        return toDTO(groupuserRepository.save(groupuser));

    }

    @Override
    public List<GroupUserDTO> findAllByStatus(String status) {
        return groupuserRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<GroupUser> lstGroupUser;
    Long id = null;
    Long idUser = null;
    Long idGroup = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.IDUSER)) idUser = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.IDGROUP)) idGroup = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<GroupUser> paginaGroupUser = groupuserRepository.findGroupUserByFilter(paging,
        id
        ,idUser
        ,idGroup
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstGroupUser = paginaGroupUser.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaGroupUser.getNumber());
    response.put("totalItems", paginaGroupUser.getTotalElements());
    response.put("totalPages", paginaGroupUser.getTotalPages());
    response.put("pageGroupUserItems", lstGroupUser.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
)
    public List<GroupUserDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long idUser = null;
    Long idGroup = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.IDUSER)) idUser = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.IDGROUP)) idGroup = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GroupUserConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<GroupUser> lstGroupUser = groupuserRepository.findGroupUserByFilter(
            id
            ,idUser
            ,idGroup
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstGroupUser.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public List<GroupUserDTO> findAllGroupUserByIdAndStatus(Long id, String status) {
        return groupuserRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public List<GroupUserDTO> findAllGroupUserByIdUserAndStatus(Long idUser, String status) {
        return groupuserRepository.findAllByIdUserAndStatus(idUser, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public List<GroupUserDTO> findAllGroupUserByIdGroupAndStatus(Long idGroup, String status) {
        return groupuserRepository.findAllByIdGroupAndStatus(idGroup, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public List<GroupUserDTO> findAllGroupUserByDateCreatedAndStatus(Date dateCreated, String status) {
        return groupuserRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public List<GroupUserDTO> findAllGroupUserByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return groupuserRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByIdAndStatus(Long id, String status) {
        Long maxId = groupuserRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupUser> groupuserData =
            Optional.ofNullable( groupuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        GROUPUSER_NOTFOUND_WITH_ID + id))
                );
        return groupuserData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByIdAndStatus(Long id) {
        return this.findGroupUserByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByIdUserAndStatus(Long idUser, String status) {
        Long maxId = groupuserRepository.loadMaxIdByIdUserAndStatus(idUser, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupUser> groupuserData =
            Optional.ofNullable( groupuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_IDUSER + idUser,
                        HttpStatus.NOT_FOUND,
                        GROUPUSER_NOTFOUND_WITH_IDUSER + idUser))
                );
        return groupuserData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByIdUserAndStatus(Long idUser) {
        return this.findGroupUserByIdUserAndStatus(idUser, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByIdGroupAndStatus(Long idGroup, String status) {
        Long maxId = groupuserRepository.loadMaxIdByIdGroupAndStatus(idGroup, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupUser> groupuserData =
            Optional.ofNullable( groupuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_IDGROUP + idGroup,
                        HttpStatus.NOT_FOUND,
                        GROUPUSER_NOTFOUND_WITH_IDGROUP + idGroup))
                );
        return groupuserData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByIdGroupAndStatus(Long idGroup) {
        return this.findGroupUserByIdGroupAndStatus(idGroup, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = groupuserRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupUser> groupuserData =
            Optional.ofNullable( groupuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        GROUPUSER_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return groupuserData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByDateCreatedAndStatus(Date dateCreated) {
        return this.findGroupUserByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = groupuserRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<GroupUser> groupuserData =
            Optional.ofNullable( groupuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GroupUserNotFoundException(GROUPUSER_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        GROUPUSER_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return groupuserData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GroupUserNotFoundException.class
    )
    public GroupUserDTO findGroupUserByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findGroupUserByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GroupUserDTO updateIdUserById(Long id, Long idUser) {
        findById(id);
        groupuserRepository.updateIdUserById(id, idUser);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GroupUserDTO updateIdGroupById(Long id, Long idGroup) {
        findById(id);
        groupuserRepository.updateIdGroupById(id, idGroup);
        return findById(id);
    }


    public GroupUserDTO toDTO(GroupUser groupuser) {
        GroupUserDTO groupuserDTO = new GroupUserDTO();
                groupuserDTO.setId(groupuser.getId());
                groupuserDTO.setIdUser(groupuser.getIdUser());
                groupuserDTO.setIdGroup(groupuser.getIdGroup());
                groupuserDTO.setStatus(groupuser.getStatus());
                groupuserDTO.setDateCreated(groupuser.getDateCreated());
                groupuserDTO.setDateUpdated(groupuser.getDateUpdated());

        return groupuserDTO;
    }

    public GroupUser toEntity(GroupUserDTO groupuserDTO) {
        GroupUser groupuser = null;
        groupuser = new GroupUser();
                    groupuser.setId(groupuserDTO.getId());
                    groupuser.setIdUser(groupuserDTO.getIdUser());
                    groupuser.setIdGroup(groupuserDTO.getIdGroup());
                    groupuser.setStatus(groupuserDTO.getStatus());
                    groupuser.setDateCreated(groupuserDTO.getDateCreated());
                    groupuser.setDateUpdated(groupuserDTO.getDateUpdated());

        return groupuser;
    }
}
