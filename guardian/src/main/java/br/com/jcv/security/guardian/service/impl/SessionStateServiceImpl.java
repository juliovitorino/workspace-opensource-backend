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
import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.SessionState;
import br.com.jcv.security.guardian.constantes.SessionStateConstantes;
import br.com.jcv.security.guardian.repository.SessionStateRepository;
import br.com.jcv.security.guardian.service.SessionStateService;
import br.com.jcv.security.guardian.exception.SessionStateNotFoundException;

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
* SessionStateServiceImpl - Implementation for SessionState interface
*
* @author SessionState
* @since Sun Oct 29 15:32:37 BRT 2023
*/


@Slf4j
@Service
public class SessionStateServiceImpl implements SessionStateService
{
    private static final String SESSIONSTATE_NOTFOUND_WITH_ID = "SessionState não encontrada com id = ";
    private static final String SESSIONSTATE_NOTFOUND_WITH_IDTOKEN = "SessionState não encontrada com idToken = ";
    private static final String SESSIONSTATE_NOTFOUND_WITH_IDUSERUUID = "SessionState não encontrada com idUserUUID = ";
    private static final String SESSIONSTATE_NOTFOUND_WITH_STATUS = "SessionState não encontrada com status = ";
    private static final String SESSIONSTATE_NOTFOUND_WITH_DATECREATED = "SessionState não encontrada com dateCreated = ";
    private static final String SESSIONSTATE_NOTFOUND_WITH_DATEUPDATED = "SessionState não encontrada com dateUpdated = ";


    @Autowired private SessionStateRepository sessionstateRepository;
    @Autowired private DateTime dateTime;
    @Autowired private Gson gson;

    @Autowired private @Qualifier("redisService") CacheProvider redisProvider;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SessionStateNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando SessionState com id = {}", id);
        sessionstateRepository.findById(id)
                .orElseThrow(
                    () -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        SESSIONSTATE_NOTFOUND_WITH_ID  + id));
        sessionstateRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO salvar(SessionStateDTO sessionstateDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(sessionstateDTO.getId()) && sessionstateDTO.getId() != 0) {
            sessionstateDTO.setDateUpdated(now);
        } else {
            sessionstateDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            sessionstateDTO.setDateCreated(now);
            sessionstateDTO.setDateUpdated(now);
        }
        return this.toDTO(sessionstateRepository.save(this.toEntity(sessionstateDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findById(Long id) {
        Optional<SessionState> sessionstateData =
            Optional.ofNullable(sessionstateRepository.findById(id)
                .orElseThrow(
                    () -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    SESSIONSTATE_NOTFOUND_WITH_ID  + id ))
                );

        return sessionstateData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SessionStateNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<SessionState> sessionstateData =
            Optional.ofNullable(sessionstateRepository.findById(id)
                .orElseThrow(
                    () -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        SESSIONSTATE_NOTFOUND_WITH_ID  + id))
                    );
        if (sessionstateData.isPresent()) {
            SessionState sessionstate = sessionstateData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.IDTOKEN)) sessionstate.setIdToken((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.IDUSERUUID)) sessionstate.setIdUserUUID((UUID)entry.getValue());

        }
        if(updates.get(SessionStateConstantes.DATEUPDATED) == null) sessionstate.setDateUpdated(new Date());
        sessionstateRepository.save(sessionstate);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO updateStatusById(Long id, String status) {
        Optional<SessionState> sessionstateData =
            Optional.ofNullable( sessionstateRepository.findById(id)
                .orElseThrow(() -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    SESSIONSTATE_NOTFOUND_WITH_ID + id))
                );
        SessionState sessionstate = sessionstateData.orElseGet(SessionState::new);
        sessionstate.setStatus(status);
        sessionstate.setDateUpdated(new Date());
        return toDTO(sessionstateRepository.save(sessionstate));

    }

    @Override
    public List<SessionStateDTO> findAllByStatus(String status) {
        return sessionstateRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<SessionState> lstSessionState;
    Long id = null;
    UUID idToken = null;
    UUID idUserUUID = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.IDTOKEN)) idToken = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.IDUSERUUID)) idUserUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<SessionState> paginaSessionState = sessionstateRepository.findSessionStateByFilter(paging,
        id
        ,idToken
        ,idUserUUID
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstSessionState = paginaSessionState.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaSessionState.getNumber());
    response.put("totalItems", paginaSessionState.getTotalElements());
    response.put("totalPages", paginaSessionState.getTotalPages());
    response.put("pageSessionStateItems", lstSessionState.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
)
    public List<SessionStateDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    UUID idToken = null;
    UUID idUserUUID = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.IDTOKEN)) idToken = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.IDUSERUUID)) idUserUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SessionStateConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<SessionState> lstSessionState = sessionstateRepository.findSessionStateByFilter(
            id
            ,idToken
            ,idUserUUID
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstSessionState.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public List<SessionStateDTO> findAllSessionStateByIdAndStatus(Long id, String status) {
        return sessionstateRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public List<SessionStateDTO> findAllSessionStateByIdTokenAndStatus(UUID idToken, String status) {
        return sessionstateRepository.findAllByIdTokenAndStatus(idToken, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public List<SessionStateDTO> findAllSessionStateByIdUserUUIDAndStatus(UUID idUserUUID, String status) {
        return sessionstateRepository.findAllByIdUserUUIDAndStatus(idUserUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public List<SessionStateDTO> findAllSessionStateByDateCreatedAndStatus(Date dateCreated, String status) {
        return sessionstateRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public List<SessionStateDTO> findAllSessionStateByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return sessionstateRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByIdAndStatus(Long id, String status) {
        SessionStateDTO cache = redisProvider.getValue("sessionState-" + id + status,SessionStateDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = sessionstateRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<SessionState> sessionstateData =
            Optional.ofNullable( sessionstateRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        SESSIONSTATE_NOTFOUND_WITH_ID + id))
                );
        SessionStateDTO roleResponse = sessionstateData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(roleResponse)) {
            redisProvider.setValue("sessionState-" + id + status, gson.toJson(roleResponse),120);
        }
        return roleResponse;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByIdAndStatus(Long id) {
        return this.findSessionStateByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByIdTokenAndStatus(UUID idToken, String status) {
        SessionStateDTO cache = redisProvider.getValue("sessionState-" + idToken + status,SessionStateDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = sessionstateRepository.loadMaxIdByIdTokenAndStatus(idToken, status);
        if(maxId == null) maxId = 0L;
        Optional<SessionState> sessionstateData =
            Optional.ofNullable( sessionstateRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_IDTOKEN + idToken,
                        HttpStatus.NOT_FOUND,
                        SESSIONSTATE_NOTFOUND_WITH_IDTOKEN + idToken))
                );

        SessionStateDTO roleResponse = sessionstateData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(roleResponse)) {
            redisProvider.setValue("sessionState-" + idToken + status, gson.toJson(roleResponse),120);
        }
        return roleResponse;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByIdTokenAndStatus(UUID idToken) {
        return this.findSessionStateByIdTokenAndStatus(idToken, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByIdUserUUIDAndStatus(UUID idUserUUID, String status) {
        SessionStateDTO cache = redisProvider.getValue("sessionState-" + idUserUUID + status,SessionStateDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = sessionstateRepository.loadMaxIdByIdUserUUIDAndStatus(idUserUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<SessionState> sessionstateData =
            Optional.ofNullable( sessionstateRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_IDUSERUUID + idUserUUID,
                        HttpStatus.NOT_FOUND,
                        SESSIONSTATE_NOTFOUND_WITH_IDUSERUUID + idUserUUID))
                );

        SessionStateDTO roleResponse = sessionstateData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(roleResponse)) {
            redisProvider.setValue("sessionState-" + idUserUUID + status, gson.toJson(roleResponse),120);
        }
        return roleResponse;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByIdUserUUIDAndStatus(UUID idUserUUID) {
        return this.findSessionStateByIdUserUUIDAndStatus(idUserUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = sessionstateRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<SessionState> sessionstateData =
            Optional.ofNullable( sessionstateRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        SESSIONSTATE_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return sessionstateData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByDateCreatedAndStatus(Date dateCreated) {
        return this.findSessionStateByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = sessionstateRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<SessionState> sessionstateData =
            Optional.ofNullable( sessionstateRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SessionStateNotFoundException(SESSIONSTATE_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        SESSIONSTATE_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return sessionstateData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SessionStateNotFoundException.class
    )
    public SessionStateDTO findSessionStateByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findSessionStateByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public SessionStateDTO updateIdTokenById(Long id, UUID idToken) {
        findById(id);
        sessionstateRepository.updateIdTokenById(id, idToken);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public SessionStateDTO updateIdUserUUIDById(Long id, UUID idUserUUID) {
        findById(id);
        sessionstateRepository.updateIdUserUUIDById(id, idUserUUID);
        return findById(id);
    }


    public SessionStateDTO toDTO(SessionState sessionstate) {
        SessionStateDTO sessionstateDTO = new SessionStateDTO();
                sessionstateDTO.setId(sessionstate.getId());
                sessionstateDTO.setIdToken(sessionstate.getIdToken());
                sessionstateDTO.setIdUserUUID(sessionstate.getIdUserUUID());
                sessionstateDTO.setStatus(sessionstate.getStatus());
                sessionstateDTO.setDateCreated(sessionstate.getDateCreated());
                sessionstateDTO.setDateUpdated(sessionstate.getDateUpdated());

        return sessionstateDTO;
    }

    public SessionState toEntity(SessionStateDTO sessionstateDTO) {
        SessionState sessionstate = null;
        sessionstate = new SessionState();
                    sessionstate.setId(sessionstateDTO.getId());
                    sessionstate.setIdToken(sessionstateDTO.getIdToken());
                    sessionstate.setIdUserUUID(sessionstateDTO.getIdUserUUID());
                    sessionstate.setStatus(sessionstateDTO.getStatus());
                    sessionstate.setDateCreated(sessionstateDTO.getDateCreated());
                    sessionstate.setDateUpdated(sessionstateDTO.getDateUpdated());

        return sessionstate;
    }
}
