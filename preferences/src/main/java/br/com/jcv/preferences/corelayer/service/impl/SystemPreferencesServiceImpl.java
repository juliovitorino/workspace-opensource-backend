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
import br.com.jcv.preferences.corelayer.model.SystemPreferences;
import br.com.jcv.preferences.corelayer.repository.SystemPreferencesRepository;
import br.com.jcv.preferences.corelayer.service.SystemPreferencesService;
import br.com.jcv.preferences.infrastructure.constantes.SystemPreferencesConstantes;
import br.com.jcv.preferences.infrastructure.dto.SystemPreferencesDTO;
import br.com.jcv.preferences.infrastructure.exception.SystemPreferencesNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* SystemPreferencesServiceImpl - Implementation for SystemPreferences interface
*
* @author SystemPreferences
* @since Mon Apr 29 15:32:51 BRT 2024
*/


@Slf4j
@Service
public class SystemPreferencesServiceImpl implements SystemPreferencesService
{
    private static final String SYSTEMPREFERENCES_NOTFOUND_WITH_ID = "SystemPreferences não encontrada com id = ";
    private static final String SYSTEMPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP = "SystemPreferences não encontrada com uuidExternalApp = ";
    private static final String SYSTEMPREFERENCES_NOTFOUND_WITH_KEY = "SystemPreferences não encontrada com key = ";
    private static final String SYSTEMPREFERENCES_NOTFOUND_WITH_PREFERENCE = "SystemPreferences não encontrada com preference = ";
    private static final String SYSTEMPREFERENCES_NOTFOUND_WITH_STATUS = "SystemPreferences não encontrada com status = ";
    private static final String SYSTEMPREFERENCES_NOTFOUND_WITH_DATECREATED = "SystemPreferences não encontrada com dateCreated = ";
    private static final String SYSTEMPREFERENCES_NOTFOUND_WITH_DATEUPDATED = "SystemPreferences não encontrada com dateUpdated = ";


    @Autowired private SystemPreferencesRepository systempreferencesRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando SystemPreferences com id = {}", id);
        systempreferencesRepository.findById(id)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        SYSTEMPREFERENCES_NOTFOUND_WITH_ID  + id));
        systempreferencesRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO salvar(SystemPreferencesDTO systempreferencesDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(systempreferencesDTO.getId()) && systempreferencesDTO.getId() != 0) {
            systempreferencesDTO.setDateUpdated(now);
        } else {
            systempreferencesDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            systempreferencesDTO.setDateCreated(now);
            systempreferencesDTO.setDateUpdated(now);
        }
        return this.toDTO(systempreferencesRepository.save(this.toEntity(systempreferencesDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findById(Long id) {
        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable(systempreferencesRepository.findById(id)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    SYSTEMPREFERENCES_NOTFOUND_WITH_ID  + id ))
                );

        return systempreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable(systempreferencesRepository.findById(id)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        SYSTEMPREFERENCES_NOTFOUND_WITH_ID  + id))
                    );
        if (systempreferencesData.isPresent()) {
            SystemPreferences systempreferences = systempreferencesData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.UUIDEXTERNALAPP)) systempreferences.setUuidExternalApp((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.KEY)) systempreferences.setKey((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.PREFERENCE)) systempreferences.setPreference((String)entry.getValue());

        }
        if(updates.get(SystemPreferencesConstantes.DATEUPDATED) == null) systempreferences.setDateUpdated(new Date());
        systempreferencesRepository.save(systempreferences);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO updateStatusById(Long id, String status) {
        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable( systempreferencesRepository.findById(id)
                .orElseThrow(() -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    SYSTEMPREFERENCES_NOTFOUND_WITH_ID + id))
                );
        SystemPreferences systempreferences = systempreferencesData.orElseGet(SystemPreferences::new);
        systempreferences.setStatus(status);
        systempreferences.setDateUpdated(new Date());
        return toDTO(systempreferencesRepository.save(systempreferences));

    }

    @Override
    public List<SystemPreferencesDTO> findAllByStatus(String status) {
        return systempreferencesRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<SystemPreferences> lstSystemPreferences;
    Long id = null;
    UUID uuidExternalApp = null;
    String key = null;
    String preference = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.KEY)) key = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.PREFERENCE)) preference = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<SystemPreferences> paginaSystemPreferences = systempreferencesRepository.findSystemPreferencesByFilter(paging,
        id
        ,uuidExternalApp
        ,key
        ,preference
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstSystemPreferences = paginaSystemPreferences.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaSystemPreferences.getNumber());
    response.put("totalItems", paginaSystemPreferences.getTotalElements());
    response.put("totalPages", paginaSystemPreferences.getTotalPages());
    response.put("pageSystemPreferencesItems", lstSystemPreferences.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
)
    public List<SystemPreferencesDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    UUID uuidExternalApp = null;
    String key = null;
    String preference = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.KEY)) key = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.PREFERENCE)) preference = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(SystemPreferencesConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<SystemPreferences> lstSystemPreferences = systempreferencesRepository.findSystemPreferencesByFilter(
            id
            ,uuidExternalApp
            ,key
            ,preference
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstSystemPreferences.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public List<SystemPreferencesDTO> findAllSystemPreferencesByIdAndStatus(Long id, String status) {
        return systempreferencesRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public List<SystemPreferencesDTO> findAllSystemPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        return systempreferencesRepository.findAllByUuidExternalAppAndStatus(uuidExternalApp, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public List<SystemPreferencesDTO> findAllSystemPreferencesByKeyAndStatus(String key, String status) {
        return systempreferencesRepository.findAllByKeyAndStatus(key, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public List<SystemPreferencesDTO> findAllSystemPreferencesByPreferenceAndStatus(String preference, String status) {
        return systempreferencesRepository.findAllByPreferenceAndStatus(preference, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public List<SystemPreferencesDTO> findAllSystemPreferencesByDateCreatedAndStatus(Date dateCreated, String status) {
        return systempreferencesRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public List<SystemPreferencesDTO> findAllSystemPreferencesByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return systempreferencesRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByIdAndStatus(Long id, String status) {
        Long maxId = systempreferencesRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable( systempreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        SYSTEMPREFERENCES_NOTFOUND_WITH_ID + id))
                );
        return systempreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByIdAndStatus(Long id) {
        return this.findSystemPreferencesByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        Long maxId = systempreferencesRepository.loadMaxIdByUuidExternalAppAndStatus(uuidExternalApp, status);
        if(maxId == null) maxId = 0L;
        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable( systempreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp,
                        HttpStatus.NOT_FOUND,
                        SYSTEMPREFERENCES_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp))
                );
        return systempreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp) {
        return this.findSystemPreferencesByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByKeyAndStatus(String key, String status) {
        Long maxId = systempreferencesRepository.loadMaxIdByKeyAndStatus(key, status);
        if(maxId == null) maxId = 0L;
        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable( systempreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_KEY + key,
                        HttpStatus.NOT_FOUND,
                        SYSTEMPREFERENCES_NOTFOUND_WITH_KEY + key))
                );
        return systempreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByKeyAndStatus(String key) {
        return this.findSystemPreferencesByKeyAndStatus(key, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByPreferenceAndStatus(String preference, String status) {
        Long maxId = systempreferencesRepository.loadMaxIdByPreferenceAndStatus(preference, status);
        if(maxId == null) maxId = 0L;
        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable( systempreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_PREFERENCE + preference,
                        HttpStatus.NOT_FOUND,
                        SYSTEMPREFERENCES_NOTFOUND_WITH_PREFERENCE + preference))
                );
        return systempreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByPreferenceAndStatus(String preference) {
        return this.findSystemPreferencesByPreferenceAndStatus(preference, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = systempreferencesRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable( systempreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        SYSTEMPREFERENCES_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return systempreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByDateCreatedAndStatus(Date dateCreated) {
        return this.findSystemPreferencesByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = systempreferencesRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<SystemPreferences> systempreferencesData =
            Optional.ofNullable( systempreferencesRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new SystemPreferencesNotFoundException(SYSTEMPREFERENCES_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        SYSTEMPREFERENCES_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return systempreferencesData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = SystemPreferencesNotFoundException.class
    )
    public SystemPreferencesDTO findSystemPreferencesByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findSystemPreferencesByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public SystemPreferencesDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp) {
        findById(id);
        systempreferencesRepository.updateUuidExternalAppById(id, uuidExternalApp);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public SystemPreferencesDTO updateKeyById(Long id, String key) {
        findById(id);
        systempreferencesRepository.updateKeyById(id, key);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public SystemPreferencesDTO updatePreferenceById(Long id, String preference) {
        findById(id);
        systempreferencesRepository.updatePreferenceById(id, preference);
        return findById(id);
    }


    public SystemPreferencesDTO toDTO(SystemPreferences systempreferences) {
        SystemPreferencesDTO systempreferencesDTO = new SystemPreferencesDTO();
                systempreferencesDTO.setId(systempreferences.getId());
                systempreferencesDTO.setUuidExternalApp(systempreferences.getUuidExternalApp());
                systempreferencesDTO.setKey(systempreferences.getKey());
                systempreferencesDTO.setPreference(systempreferences.getPreference());
                systempreferencesDTO.setStatus(systempreferences.getStatus());
                systempreferencesDTO.setDateCreated(systempreferences.getDateCreated());
                systempreferencesDTO.setDateUpdated(systempreferences.getDateUpdated());

        return systempreferencesDTO;
    }

    public SystemPreferences toEntity(SystemPreferencesDTO systempreferencesDTO) {
        SystemPreferences systempreferences = null;
        systempreferences = new SystemPreferences();
                    systempreferences.setId(systempreferencesDTO.getId());
                    systempreferences.setUuidExternalApp(systempreferencesDTO.getUuidExternalApp());
                    systempreferences.setKey(systempreferencesDTO.getKey());
                    systempreferences.setPreference(systempreferencesDTO.getPreference());
                    systempreferences.setStatus(systempreferencesDTO.getStatus());
                    systempreferences.setDateCreated(systempreferencesDTO.getDateCreated());
                    systempreferences.setDateUpdated(systempreferencesDTO.getDateUpdated());

        return systempreferences;
    }
}
