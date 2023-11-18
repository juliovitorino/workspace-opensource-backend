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

import br.com.jcv.security.guardian.dto.RoleDTO;
import br.com.jcv.security.guardian.model.Role;
import br.com.jcv.security.guardian.constantes.RoleConstantes;
import br.com.jcv.security.guardian.repository.RoleRepository;
import br.com.jcv.security.guardian.service.RoleService;
import br.com.jcv.security.guardian.exception.RoleNotFoundException;

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
* RoleServiceImpl - Implementation for Role interface
*
* @author Role
* @since Sat Nov 18 18:21:11 BRT 2023
*/


@Slf4j
@Service
public class RoleServiceImpl implements RoleService
{
    private static final String ROLE_NOTFOUND_WITH_ID = "Role não encontrada com id = ";
    private static final String ROLE_NOTFOUND_WITH_NAME = "Role não encontrada com name = ";
    private static final String ROLE_NOTFOUND_WITH_STATUS = "Role não encontrada com status = ";
    private static final String ROLE_NOTFOUND_WITH_DATECREATED = "Role não encontrada com dateCreated = ";
    private static final String ROLE_NOTFOUND_WITH_DATEUPDATED = "Role não encontrada com dateUpdated = ";


    @Autowired private RoleRepository roleRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = RoleNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando Role com id = {}", id);
        roleRepository.findById(id)
                .orElseThrow(
                    () -> new RoleNotFoundException(ROLE_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        ROLE_NOTFOUND_WITH_ID  + id));
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO salvar(RoleDTO roleDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(roleDTO.getId()) && roleDTO.getId() != 0) {
            roleDTO.setDateUpdated(now);
        } else {
            roleDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            roleDTO.setDateCreated(now);
            roleDTO.setDateUpdated(now);
        }
        return this.toDTO(roleRepository.save(this.toEntity(roleDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findById(Long id) {
        Optional<Role> roleData =
            Optional.ofNullable(roleRepository.findById(id)
                .orElseThrow(
                    () -> new RoleNotFoundException(ROLE_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    ROLE_NOTFOUND_WITH_ID  + id ))
                );

        return roleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = RoleNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<Role> roleData =
            Optional.ofNullable(roleRepository.findById(id)
                .orElseThrow(
                    () -> new RoleNotFoundException(ROLE_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        ROLE_NOTFOUND_WITH_ID  + id))
                    );
        if (roleData.isPresent()) {
            Role role = roleData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(RoleConstantes.NAME)) role.setName((UUID)entry.getValue());

        }
        if(updates.get(RoleConstantes.DATEUPDATED) == null) role.setDateUpdated(new Date());
        roleRepository.save(role);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO updateStatusById(Long id, String status) {
        Optional<Role> roleData =
            Optional.ofNullable( roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(ROLE_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    ROLE_NOTFOUND_WITH_ID + id))
                );
        Role role = roleData.orElseGet(Role::new);
        role.setStatus(status);
        role.setDateUpdated(new Date());
        return toDTO(roleRepository.save(role));

    }

    @Override
    public List<RoleDTO> findAllByStatus(String status) {
        return roleRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<Role> lstRole;
    Long id = null;
    UUID name = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<Role> paginaRole = roleRepository.findRoleByFilter(paging,
        id
        ,name
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstRole = paginaRole.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaRole.getNumber());
    response.put("totalItems", paginaRole.getTotalElements());
    response.put("totalPages", paginaRole.getTotalPages());
    response.put("pageRoleItems", lstRole.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
)
    public List<RoleDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    UUID name = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(RoleConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<Role> lstRole = roleRepository.findRoleByFilter(
            id
            ,name
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstRole.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public List<RoleDTO> findAllRoleByIdAndStatus(Long id, String status) {
        return roleRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public List<RoleDTO> findAllRoleByNameAndStatus(UUID name, String status) {
        return roleRepository.findAllByNameAndStatus(name, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public List<RoleDTO> findAllRoleByDateCreatedAndStatus(Date dateCreated, String status) {
        return roleRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public List<RoleDTO> findAllRoleByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return roleRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findRoleByIdAndStatus(Long id, String status) {
        Long maxId = roleRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<Role> roleData =
            Optional.ofNullable( roleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new RoleNotFoundException(ROLE_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        ROLE_NOTFOUND_WITH_ID + id))
                );
        return roleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findRoleByIdAndStatus(Long id) {
        return this.findRoleByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findRoleByNameAndStatus(UUID name, String status) {
        Long maxId = roleRepository.loadMaxIdByNameAndStatus(name, status);
        if(maxId == null) maxId = 0L;
        Optional<Role> roleData =
            Optional.ofNullable( roleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new RoleNotFoundException(ROLE_NOTFOUND_WITH_NAME + name,
                        HttpStatus.NOT_FOUND,
                        ROLE_NOTFOUND_WITH_NAME + name))
                );
        return roleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findRoleByNameAndStatus(UUID name) {
        return this.findRoleByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findRoleByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = roleRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<Role> roleData =
            Optional.ofNullable( roleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new RoleNotFoundException(ROLE_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        ROLE_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return roleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findRoleByDateCreatedAndStatus(Date dateCreated) {
        return this.findRoleByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findRoleByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = roleRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<Role> roleData =
            Optional.ofNullable( roleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new RoleNotFoundException(ROLE_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        ROLE_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return roleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = RoleNotFoundException.class
    )
    public RoleDTO findRoleByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findRoleByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public RoleDTO updateNameById(Long id, UUID name) {
        findById(id);
        roleRepository.updateNameById(id, name);
        return findById(id);
    }


    public RoleDTO toDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(role.getId());
                roleDTO.setName(role.getName());
                roleDTO.setStatus(role.getStatus());
                roleDTO.setDateCreated(role.getDateCreated());
                roleDTO.setDateUpdated(role.getDateUpdated());

        return roleDTO;
    }

    public Role toEntity(RoleDTO roleDTO) {
        Role role = null;
        role = new Role();
                    role.setId(roleDTO.getId());
                    role.setName(roleDTO.getName());
                    role.setStatus(roleDTO.getStatus());
                    role.setDateCreated(roleDTO.getDateCreated());
                    role.setDateUpdated(roleDTO.getDateUpdated());

        return role;
    }
}
