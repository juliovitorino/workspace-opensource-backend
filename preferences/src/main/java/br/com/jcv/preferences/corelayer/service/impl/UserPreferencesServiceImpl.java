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


package br.com.jcv.preferences.corelayer.service.impl;

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
import br.com.jcv.preferences.corelayer.model.UserPreferences;
import br.com.jcv.preferences.corelayer.repository.UserPreferencesRepository;
import br.com.jcv.preferences.corelayer.service.UserPreferencesService;
import br.com.jcv.preferences.infrastructure.constantes.UserPreferencesConstantes;
import br.com.jcv.preferences.infrastructure.dto.UserPreferencesDTO;
import br.com.jcv.preferences.infrastructure.exception.UserPreferencesNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* UserPreferencesServiceImpl - Implementation for UserPreferences interface
*
* @author UserPreferences
* @since Mon Apr 29 16:40:18 BRT 2024
*/


@Slf4j
@Service
public class UserPreferencesServiceImpl implements UserPreferencesService
{
    private static final String USERPREFERENCES_NOTFOUND_WITH_ID = "UserPreferences não encontrada com id = ";
    private static final String USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP = "UserPreferences não encontrada com uuidExternalApp = ";
    private static final String USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALUSER = "UserPreferences não encontrada com uuidExternalUser = ";
    private static final String USERPREFERENCES_NOTFOUND_WITH_KEY = "UserPreferences não encontrada com key = ";
    private static final String USERPREFERENCES_NOTFOUND_WITH_PREFERENCE = "UserPreferences não encontrada com preference = ";
    private static final String USERPREFERENCES_NOTFOUND_WITH_STATUS = "UserPreferences não encontrada com status = ";
    private static final String USERPREFERENCES_NOTFOUND_WITH_DATECREATED = "UserPreferences não encontrada com dateCreated = ";
    private static final String USERPREFERENCES_NOTFOUND_WITH_DATEUPDATED = "UserPreferences não encontrada com dateUpdated = ";


    @Autowired private UserPreferencesRepository userpreferencesRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserPreferencesNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando UserPreferences com id = {}", id);
        userpreferencesRepository.findById(id)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_ID  + id));
        userpreferencesRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO salvar(UserPreferencesDTO userpreferencesDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(userpreferencesDTO.getId()) && userpreferencesDTO.getId() != 0) {
            userpreferencesDTO.setDateUpdated(now);
        } else {
            userpreferencesDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            userpreferencesDTO.setDateCreated(now);
            userpreferencesDTO.setDateUpdated(now);
        }
        return this.toDTO(userpreferencesRepository.save(this.toEntity(userpreferencesDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findById(Long id) {
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable(userpreferencesRepository.findById(id)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    USERPREFERENCES_NOTFOUND_WITH_ID  + id ))
                );

        return userpreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserPreferencesNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable(userpreferencesRepository.findById(id)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_ID  + id))
                    );
        if (userpreferencesData.isPresent()) {
            UserPreferences userpreferences = userpreferencesData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.UUIDEXTERNALAPP)) userpreferences.setUuidExternalApp((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.UUIDEXTERNALUSER)) userpreferences.setUuidExternalUser((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.KEY)) userpreferences.setKey((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.PREFERENCE)) userpreferences.setPreference((String)entry.getValue());

        }
        if(updates.get(UserPreferencesConstantes.DATEUPDATED) == null) userpreferences.setDateUpdated(new Date());
        userpreferencesRepository.save(userpreferences);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO updateStatusById(Long id, String status) {
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable( userpreferencesRepository.findById(id)
                .orElseThrow(() -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    USERPREFERENCES_NOTFOUND_WITH_ID + id))
                );
        UserPreferences userpreferences = userpreferencesData.orElseGet(UserPreferences::new);
        userpreferences.setStatus(status);
        userpreferences.setDateUpdated(new Date());
        return toDTO(userpreferencesRepository.save(userpreferences));

    }

    @Override
    public List<UserPreferencesDTO> findAllByStatus(String status) {
        return userpreferencesRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<UserPreferences> lstUserPreferences;
    Long id = null;
    UUID uuidExternalApp = null;
    UUID uuidExternalUser = null;
    String key = null;
    String preference = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.UUIDEXTERNALUSER)) uuidExternalUser = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.KEY)) key = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.PREFERENCE)) preference = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<UserPreferences> paginaUserPreferences = userpreferencesRepository.findUserPreferencesByFilter(paging,
        id
        ,uuidExternalApp
        ,uuidExternalUser
        ,key
        ,preference
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstUserPreferences = paginaUserPreferences.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaUserPreferences.getNumber());
    response.put("totalItems", paginaUserPreferences.getTotalElements());
    response.put("totalPages", paginaUserPreferences.getTotalPages());
    response.put("pageUserPreferencesItems", lstUserPreferences.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
)
    public List<UserPreferencesDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    UUID uuidExternalApp = null;
    UUID uuidExternalUser = null;
    String key = null;
    String preference = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.UUIDEXTERNALUSER)) uuidExternalUser = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.KEY)) key = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.PREFERENCE)) preference = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UserPreferencesConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<UserPreferences> lstUserPreferences = userpreferencesRepository.findUserPreferencesByFilter(
            id
            ,uuidExternalApp
            ,uuidExternalUser
            ,key
            ,preference
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstUserPreferences.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public List<UserPreferencesDTO> findAllUserPreferencesByIdAndStatus(Long id, String status) {
        return userpreferencesRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public List<UserPreferencesDTO> findAllUserPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        return userpreferencesRepository.findAllByUuidExternalAppAndStatus(uuidExternalApp, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public List<UserPreferencesDTO> findAllUserPreferencesByUuidExternalUserAndStatus(UUID uuidExternalUser, String status) {
        return userpreferencesRepository.findAllByUuidExternalUserAndStatus(uuidExternalUser, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public List<UserPreferencesDTO> findAllUserPreferencesByKeyAndStatus(String key, String status) {
        return userpreferencesRepository.findAllByKeyAndStatus(key, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public List<UserPreferencesDTO> findAllUserPreferencesByPreferenceAndStatus(String preference, String status) {
        return userpreferencesRepository.findAllByPreferenceAndStatus(preference, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public List<UserPreferencesDTO> findAllUserPreferencesByDateCreatedAndStatus(Date dateCreated, String status) {
        return userpreferencesRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public List<UserPreferencesDTO> findAllUserPreferencesByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return userpreferencesRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByIdAndStatus(Long id, String status) {
        Long maxId = userpreferencesRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable( userpreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_ID + id))
                );
        return userpreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByIdAndStatus(Long id) {
        return this.findUserPreferencesByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        Long maxId = userpreferencesRepository.loadMaxIdByUuidExternalAppAndStatus(uuidExternalApp, status);
        if(maxId == null) maxId = 0L;
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable( userpreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp))
                );
        return userpreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp) {
        return this.findUserPreferencesByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByUuidExternalUserAndStatus(UUID uuidExternalUser, String status) {
        Long maxId = userpreferencesRepository.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUser, status);
        if(maxId == null) maxId = 0L;
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable( userpreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALUSER + uuidExternalUser,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALUSER + uuidExternalUser))
                );
        return userpreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByUuidExternalUserAndStatus(UUID uuidExternalUser) {
        return this.findUserPreferencesByUuidExternalUserAndStatus(uuidExternalUser, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByKeyAndStatus(String key, String status) {
        Long maxId = userpreferencesRepository.loadMaxIdByKeyAndStatus(key, status);
        if(maxId == null) maxId = 0L;
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable( userpreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_KEY + key,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_KEY + key))
                );
        return userpreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByKeyAndStatus(String key) {
        return this.findUserPreferencesByKeyAndStatus(key, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByPreferenceAndStatus(String preference, String status) {
        Long maxId = userpreferencesRepository.loadMaxIdByPreferenceAndStatus(preference, status);
        if(maxId == null) maxId = 0L;
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable( userpreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_PREFERENCE + preference,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_PREFERENCE + preference))
                );
        return userpreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByPreferenceAndStatus(String preference) {
        return this.findUserPreferencesByPreferenceAndStatus(preference, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = userpreferencesRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable( userpreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return userpreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByDateCreatedAndStatus(Date dateCreated) {
        return this.findUserPreferencesByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = userpreferencesRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<UserPreferences> userpreferencesData =
            Optional.ofNullable( userpreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UserPreferencesNotFoundException(USERPREFERENCES_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        USERPREFERENCES_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return userpreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UserPreferencesNotFoundException.class
    )
    public UserPreferencesDTO findUserPreferencesByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findUserPreferencesByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UserPreferencesDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp) {
        findById(id);
        userpreferencesRepository.updateUuidExternalAppById(id, uuidExternalApp);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UserPreferencesDTO updateUuidExternalUserById(Long id, UUID uuidExternalUser) {
        findById(id);
        userpreferencesRepository.updateUuidExternalUserById(id, uuidExternalUser);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UserPreferencesDTO updateKeyById(Long id, String key) {
        findById(id);
        userpreferencesRepository.updateKeyById(id, key);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UserPreferencesDTO updatePreferenceById(Long id, String preference) {
        findById(id);
        userpreferencesRepository.updatePreferenceById(id, preference);
        return findById(id);
    }


    public UserPreferencesDTO toDTO(UserPreferences userpreferences) {
        UserPreferencesDTO userpreferencesDTO = new UserPreferencesDTO();
                userpreferencesDTO.setId(userpreferences.getId());
                userpreferencesDTO.setUuidExternalApp(userpreferences.getUuidExternalApp());
                userpreferencesDTO.setUuidExternalUser(userpreferences.getUuidExternalUser());
                userpreferencesDTO.setKey(userpreferences.getKey());
                userpreferencesDTO.setPreference(userpreferences.getPreference());
                userpreferencesDTO.setStatus(userpreferences.getStatus());
                userpreferencesDTO.setDateCreated(userpreferences.getDateCreated());
                userpreferencesDTO.setDateUpdated(userpreferences.getDateUpdated());

        return userpreferencesDTO;
    }

    public UserPreferences toEntity(UserPreferencesDTO userpreferencesDTO) {
        UserPreferences userpreferences = null;
        userpreferences = new UserPreferences();
                    userpreferences.setId(userpreferencesDTO.getId());
                    userpreferences.setUuidExternalApp(userpreferencesDTO.getUuidExternalApp());
                    userpreferences.setUuidExternalUser(userpreferencesDTO.getUuidExternalUser());
                    userpreferences.setKey(userpreferencesDTO.getKey());
                    userpreferences.setPreference(userpreferencesDTO.getPreference());
                    userpreferences.setStatus(userpreferencesDTO.getStatus());
                    userpreferences.setDateCreated(userpreferencesDTO.getDateCreated());
                    userpreferences.setDateUpdated(userpreferencesDTO.getDateUpdated());

        return userpreferences;
    }
}
