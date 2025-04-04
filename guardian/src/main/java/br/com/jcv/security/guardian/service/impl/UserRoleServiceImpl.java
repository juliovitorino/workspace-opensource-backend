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
import br.com.jcv.security.guardian.dto.UserRoleDTO;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.UserRole;
import br.com.jcv.security.guardian.constantes.UserRoleConstantes;
import br.com.jcv.security.guardian.repository.UserRoleRepository;
import br.com.jcv.security.guardian.service.UserRoleService;
import br.com.jcv.security.guardian.exception.UserRoleNotFoundException;

import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.extern.slf4j.Slf4j;


/**
* UserRoleServiceImpl - Implementation for UserRole interface
*
* @author UserRole
* @since Sat Nov 18 18:21:14 BRT 2023
*/


@Slf4j
@Service
public class UserRoleServiceImpl implements UserRoleService
{
    private static final String USERROLE_NOTFOUND_WITH_ID = "UserRole não encontrada com id = ";
    private static final String USERROLE_NOTFOUND_WITH_IDROLE = "UserRole não encontrada com idRole = ";
    private static final String USERROLE_NOTFOUND_WITH_IDUSER = "UserRole não encontrada com idUser = ";
    private static final String USERROLE_NOTFOUND_WITH_STATUS = "UserRole não encontrada com status = ";
    private static final String USERROLE_NOTFOUND_WITH_DATECREATED = "UserRole não encontrada com dateCreated = ";
    private static final String USERROLE_NOTFOUND_WITH_DATEUPDATED = "UserRole não encontrada com dateUpdated = ";


    @Autowired private UserRoleRepository userroleRepository;
    @Autowired private DateTime dateTime;
    @Autowired private @Qualifier("redisService") CacheProvider redisProvider;
    @Autowired private Gson gson;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserRoleNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando UserRole com id = {}", id);
        userroleRepository.findById(id)
                .orElseThrow(
                    () -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        USERROLE_NOTFOUND_WITH_ID  + id));
        userroleRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO salvar(UserRoleDTO userroleDTO) {
        LocalDateTime now = LocalDateTime.now();
        if(Objects.nonNull(userroleDTO.getId()) && userroleDTO.getId() != 0) {
            userroleDTO.setDateUpdated(now);
        } else {
            userroleDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            userroleDTO.setDateCreated(now);
            userroleDTO.setDateUpdated(now);
        }
        return this.toDTO(userroleRepository.save(this.toEntity(userroleDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findById(Long id) {

        UserRoleDTO cache = redisProvider.getValue("userRole-findById-" + id,UserRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Optional<UserRole> userroleData =
            Optional.ofNullable(userroleRepository.findById(id)
                .orElseThrow(
                    () -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    USERROLE_NOTFOUND_WITH_ID  + id ))
                );

        UserRoleDTO response = userroleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("userRole-findById-" + id, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserRoleNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<UserRole> userroleData =
            Optional.ofNullable(userroleRepository.findById(id)
                .orElseThrow(
                    () -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        USERROLE_NOTFOUND_WITH_ID  + id))
                    );
        if (userroleData.isPresent()) {
            UserRole userrole = userroleData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.IDROLE)) userrole.setIdRole((Long)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.IDUSER)) userrole.setIdUser((Long)entry.getValue());

        }
        if(updates.get(UserRoleConstantes.DATEUPDATED) == null) userrole.setDateUpdated(LocalDateTime.now());
        userroleRepository.save(userrole);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO updateStatusById(Long id, String status) {
        Optional<UserRole> userroleData =
            Optional.ofNullable( userroleRepository.findById(id)
                .orElseThrow(() -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    USERROLE_NOTFOUND_WITH_ID + id))
                );
        UserRole userrole = userroleData.orElseGet(UserRole::new);
        userrole.setStatus(status);
        userrole.setDateUpdated(LocalDateTime.now());
        return toDTO(userroleRepository.save(userrole));

    }

    @Override
    public List<UserRoleDTO> findAllByStatus(String status) {
        return userroleRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<UserRole> lstUserRole;
    Long id = null;
    Long idRole = null;
    Long idUser = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.IDROLE)) idRole = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.IDUSER)) idUser = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<UserRole> paginaUserRole = userroleRepository.findUserRoleByFilter(paging,
        id
        ,idRole
        ,idUser
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstUserRole = paginaUserRole.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaUserRole.getNumber());
    response.put("totalItems", paginaUserRole.getTotalElements());
    response.put("totalPages", paginaUserRole.getTotalPages());
    response.put("pageUserRoleItems", lstUserRole.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
)
    public List<UserRoleDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long idRole = null;
    Long idUser = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.IDROLE)) idRole = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.IDUSER)) idUser = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserRoleConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<UserRole> lstUserRole = userroleRepository.findUserRoleByFilter(
            id
            ,idRole
            ,idUser
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstUserRole.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public List<UserRoleDTO> findAllUserRoleByIdAndStatus(Long id, String status) {
        return userroleRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public List<UserRoleDTO> findAllUserRoleByIdRoleAndStatus(Long idRole, String status) {
        return userroleRepository.findAllByIdRoleAndStatus(idRole, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public List<UserRoleDTO> findAllUserRoleByIdUserAndStatus(Long idUser, String status) {
        return userroleRepository.findAllByIdUserAndStatus(idUser, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public List<UserRoleDTO> findAllUserRoleByIdUserAndIdRoleAndStatus(Long idUser, Long idRole, String status) {
        return userroleRepository.findAllByIdUserAndIdRoleAndStatus(idUser, idRole, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public List<UserRoleDTO> findAllUserRoleByDateCreatedAndStatus(Date dateCreated, String status) {
        return userroleRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public List<UserRoleDTO> findAllUserRoleByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return userroleRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByIdAndStatus(Long id, String status) {

        UserRoleDTO cache = redisProvider.getValue("userRole-cache-by-Id-status-" + id + status,UserRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = userroleRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<UserRole> userroleData =
            Optional.ofNullable( userroleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        USERROLE_NOTFOUND_WITH_ID + id))
                );

        UserRoleDTO response = userroleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("userRole-cache-by-Id-status-" + id + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByIdAndStatus(Long id) {
        return this.findUserRoleByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByIdRoleAndStatus(Long idRole, String status) {

        UserRoleDTO cache = redisProvider.getValue("userRole-cache-by-IdRole-status-" + idRole + status,UserRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = userroleRepository.loadMaxIdByIdRoleAndStatus(idRole, status);
        if(maxId == null) maxId = 0L;
        Optional<UserRole> userroleData =
            Optional.ofNullable( userroleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_IDROLE + idRole,
                        HttpStatus.NOT_FOUND,
                        USERROLE_NOTFOUND_WITH_IDROLE + idRole))
                );

        UserRoleDTO response = userroleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("userRole-cache-by-IdRole-status-" + idRole + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByIdRoleAndStatus(Long idRole) {
        return this.findUserRoleByIdRoleAndStatus(idRole, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByIdUserAndStatus(Long idUser, String status) {

        UserRoleDTO cache = redisProvider.getValue("userRole-cache-by-IdUser-status-" + idUser + status,UserRoleDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = userroleRepository.loadMaxIdByIdUserAndStatus(idUser, status);
        if(maxId == null) maxId = 0L;
        Optional<UserRole> userroleData =
            Optional.ofNullable( userroleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_IDUSER + idUser,
                        HttpStatus.NOT_FOUND,
                        USERROLE_NOTFOUND_WITH_IDUSER + idUser))
                );

        UserRoleDTO response = userroleData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("userRole-cache-by-IdUser-status-" + idUser + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByIdUserAndStatus(Long idUser) {
        return this.findUserRoleByIdUserAndStatus(idUser, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = userroleRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<UserRole> userroleData =
            Optional.ofNullable( userroleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        USERROLE_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return userroleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByDateCreatedAndStatus(Date dateCreated) {
        return this.findUserRoleByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = userroleRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<UserRole> userroleData =
            Optional.ofNullable( userroleRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserRoleNotFoundException(USERROLE_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        USERROLE_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return userroleData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserRoleNotFoundException.class
    )
    public UserRoleDTO findUserRoleByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findUserRoleByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UserRoleDTO updateIdRoleById(Long id, Long idRole) {
        findById(id);
        userroleRepository.updateIdRoleById(id, idRole);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UserRoleDTO updateIdUserById(Long id, Long idUser) {
        findById(id);
        userroleRepository.updateIdUserById(id, idUser);
        return findById(id);
    }


    public UserRoleDTO toDTO(UserRole userrole) {
        UserRoleDTO userroleDTO = new UserRoleDTO();
                userroleDTO.setId(userrole.getId());
                userroleDTO.setIdRole(userrole.getIdRole());
                userroleDTO.setIdUser(userrole.getIdUser());
                userroleDTO.setStatus(userrole.getStatus());
                userroleDTO.setDateCreated(userrole.getDateCreated());
                userroleDTO.setDateUpdated(userrole.getDateUpdated());

        return userroleDTO;
    }

    public UserRole toEntity(UserRoleDTO userroleDTO) {
        UserRole userrole = null;
        userrole = new UserRole();
                    userrole.setId(userroleDTO.getId());
                    userrole.setIdRole(userroleDTO.getIdRole());
                    userrole.setIdUser(userroleDTO.getIdUser());
                    userrole.setStatus(userroleDTO.getStatus());
                    userrole.setDateCreated(userroleDTO.getDateCreated());
                    userrole.setDateUpdated(userroleDTO.getDateUpdated());

        return userrole;
    }
}
