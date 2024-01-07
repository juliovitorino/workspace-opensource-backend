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

import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.GApplication;
import br.com.jcv.security.guardian.constantes.GApplicationConstantes;
import br.com.jcv.security.guardian.repository.GApplicationRepository;
import br.com.jcv.security.guardian.service.GApplicationService;
import br.com.jcv.security.guardian.exception.GApplicationNotFoundException;

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
* GApplicationServiceImpl - Implementation for GApplication interface
*
* @author GApplication
* @since Wed Nov 15 11:12:12 BRT 2023
*/


@Slf4j
@Service
public class GApplicationServiceImpl implements GApplicationService
{
    private static final String GAPPLICATION_NOTFOUND_WITH_ID = "GApplication não encontrada com id = ";
    private static final String GAPPLICATION_NOTFOUND_WITH_NAME = "GApplication não encontrada com name = ";
    private static final String GAPPLICATION_NOTFOUND_WITH_EXTERNALCODEUUID = "GApplication não encontrada com externalCodeUUID = ";
    private static final String GAPPLICATION_NOTFOUND_WITH_STATUS = "GApplication não encontrada com status = ";
    private static final String GAPPLICATION_NOTFOUND_WITH_DATECREATED = "GApplication não encontrada com dateCreated = ";
    private static final String GAPPLICATION_NOTFOUND_WITH_DATEUPDATED = "GApplication não encontrada com dateUpdated = ";


    @Autowired private GApplicationRepository gapplicationRepository;
    @Autowired private DateTime dateTime;
    @Autowired private @Qualifier("redisService") CacheProvider redisProvider;
    @Autowired private Gson gson;
    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GApplicationNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando GApplication com id = {}", id);
        gapplicationRepository.findById(id)
                .orElseThrow(
                    () -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GAPPLICATION_NOTFOUND_WITH_ID  + id));
        gapplicationRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO salvar(GApplicationDTO gapplicationDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(gapplicationDTO.getId()) && gapplicationDTO.getId() != 0) {
            gapplicationDTO.setDateUpdated(now);
        } else {
            gapplicationDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            gapplicationDTO.setDateCreated(now);
            gapplicationDTO.setDateUpdated(now);
        }
        return this.toDTO(gapplicationRepository.save(this.toEntity(gapplicationDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findById(Long id) {

        GApplicationDTO cache = redisProvider.getValue("applicationUser-findById-" + id,GApplicationDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Optional<GApplication> gapplicationData =
            Optional.ofNullable(gapplicationRepository.findById(id)
                .orElseThrow(
                    () -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GAPPLICATION_NOTFOUND_WITH_ID  + id ))
                );

        GApplicationDTO roleResponse = gapplicationData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(roleResponse)) {
            redisProvider.setValue("applicationUser-findById-" + id, gson.toJson(roleResponse),120);
        }
        return roleResponse;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GApplicationNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<GApplication> gapplicationData =
            Optional.ofNullable(gapplicationRepository.findById(id)
                .orElseThrow(
                    () -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        GAPPLICATION_NOTFOUND_WITH_ID  + id))
                    );
        if (gapplicationData.isPresent()) {
            GApplication gapplication = gapplicationData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.NAME)) gapplication.setName((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.EXTERNALCODEUUID)) gapplication.setExternalCodeUUID((UUID)entry.getValue());

        }
        if(updates.get(GApplicationConstantes.DATEUPDATED) == null) gapplication.setDateUpdated(new Date());
        gapplicationRepository.save(gapplication);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO updateStatusById(Long id, String status) {
        Optional<GApplication> gapplicationData =
            Optional.ofNullable( gapplicationRepository.findById(id)
                .orElseThrow(() -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    GAPPLICATION_NOTFOUND_WITH_ID + id))
                );
        GApplication gapplication = gapplicationData.orElseGet(GApplication::new);
        gapplication.setStatus(status);
        gapplication.setDateUpdated(new Date());
        return toDTO(gapplicationRepository.save(gapplication));

    }

    @Override
    public List<GApplicationDTO> findAllByStatus(String status) {
        return gapplicationRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<GApplication> lstGApplication;
    Long id = null;
    String name = null;
    UUID externalCodeUUID = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.EXTERNALCODEUUID)) externalCodeUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<GApplication> paginaGApplication = gapplicationRepository.findGApplicationByFilter(paging,
        id
        ,name
        ,externalCodeUUID
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstGApplication = paginaGApplication.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaGApplication.getNumber());
    response.put("totalItems", paginaGApplication.getTotalElements());
    response.put("totalPages", paginaGApplication.getTotalPages());
    response.put("pageGApplicationItems", lstGApplication.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
)
    public List<GApplicationDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    String name = null;
    UUID externalCodeUUID = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.EXTERNALCODEUUID)) externalCodeUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(GApplicationConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<GApplication> lstGApplication = gapplicationRepository.findGApplicationByFilter(
            id
            ,name
            ,externalCodeUUID
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstGApplication.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public List<GApplicationDTO> findAllGApplicationByIdAndStatus(Long id, String status) {
        return gapplicationRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public List<GApplicationDTO> findAllGApplicationByNameAndStatus(String name, String status) {
        return gapplicationRepository.findAllByNameAndStatus(name, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public List<GApplicationDTO> findAllGApplicationByExternalCodeUUIDAndStatus(UUID externalCodeUUID, String status) {
        return gapplicationRepository.findAllByExternalCodeUUIDAndStatus(externalCodeUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public List<GApplicationDTO> findAllGApplicationByDateCreatedAndStatus(Date dateCreated, String status) {
        return gapplicationRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public List<GApplicationDTO> findAllGApplicationByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return gapplicationRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByIdAndStatus(Long id, String status) {

        GApplicationDTO cache = redisProvider.getValue("application-cache-" + id + status,GApplicationDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = gapplicationRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<GApplication> gapplicationData =
            Optional.ofNullable( gapplicationRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        GAPPLICATION_NOTFOUND_WITH_ID + id))
                );

        GApplicationDTO response = gapplicationData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("application-cache-" + id + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByIdAndStatus(Long id) {
        return this.findGApplicationByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByNameAndStatus(String name, String status) {

        GApplicationDTO cache = redisProvider.getValue("application-cache-" + name + status,GApplicationDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = gapplicationRepository.loadMaxIdByNameAndStatus(name, status);
        if(maxId == null) maxId = 0L;
        Optional<GApplication> gapplicationData =
            Optional.ofNullable( gapplicationRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_NAME + name,
                        HttpStatus.NOT_FOUND,
                        GAPPLICATION_NOTFOUND_WITH_NAME + name))
                );

        GApplicationDTO response = gapplicationData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("application-cache-" + name + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByNameAndStatus(String name) {
        return this.findGApplicationByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByExternalCodeUUIDAndStatus(UUID externalCodeUUID, String status) {

        GApplicationDTO cache = redisProvider.getValue("application-cache-" + externalCodeUUID + status,GApplicationDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = gapplicationRepository.loadMaxIdByExternalCodeUUIDAndStatus(externalCodeUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<GApplication> gapplicationData =
            Optional.ofNullable( gapplicationRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_EXTERNALCODEUUID + externalCodeUUID,
                        HttpStatus.NOT_FOUND,
                        GAPPLICATION_NOTFOUND_WITH_EXTERNALCODEUUID + externalCodeUUID))
                );

        GApplicationDTO response = gapplicationData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("application-cache-" + externalCodeUUID + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByExternalCodeUUIDAndStatus(UUID externalCodeUUID) {
        return this.findGApplicationByExternalCodeUUIDAndStatus(externalCodeUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = gapplicationRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<GApplication> gapplicationData =
            Optional.ofNullable( gapplicationRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        GAPPLICATION_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return gapplicationData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByDateCreatedAndStatus(Date dateCreated) {
        return this.findGApplicationByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = gapplicationRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<GApplication> gapplicationData =
            Optional.ofNullable( gapplicationRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new GApplicationNotFoundException(GAPPLICATION_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        GAPPLICATION_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return gapplicationData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = GApplicationNotFoundException.class
    )
    public GApplicationDTO findGApplicationByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findGApplicationByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GApplicationDTO updateNameById(Long id, String name) {
        findById(id);
        gapplicationRepository.updateNameById(id, name);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public GApplicationDTO updateExternalCodeUUIDById(Long id, UUID externalCodeUUID) {
        findById(id);
        gapplicationRepository.updateExternalCodeUUIDById(id, externalCodeUUID);
        return findById(id);
    }


    public GApplicationDTO toDTO(GApplication gapplication) {
        GApplicationDTO gapplicationDTO = new GApplicationDTO();
                gapplicationDTO.setId(gapplication.getId());
                gapplicationDTO.setName(gapplication.getName());
                gapplicationDTO.setExternalCodeUUID(gapplication.getExternalCodeUUID());
                gapplicationDTO.setJwtTimeToLive(gapplication.getJwtTimeToLive());
                gapplicationDTO.setStatus(gapplication.getStatus());
                gapplicationDTO.setDateCreated(gapplication.getDateCreated());
                gapplicationDTO.setDateUpdated(gapplication.getDateUpdated());

        return gapplicationDTO;
    }

    public GApplication toEntity(GApplicationDTO gapplicationDTO) {
        GApplication gapplication = null;
        gapplication = new GApplication();
                    gapplication.setId(gapplicationDTO.getId());
                    gapplication.setName(gapplicationDTO.getName());
                    gapplication.setExternalCodeUUID(gapplicationDTO.getExternalCodeUUID());
                    gapplication.setJwtTimeToLive(gapplicationDTO.getJwtTimeToLive());
                    gapplication.setStatus(gapplicationDTO.getStatus());
                    gapplication.setDateCreated(gapplicationDTO.getDateCreated());
                    gapplication.setDateUpdated(gapplicationDTO.getDateUpdated());

        return gapplication;
    }
}
