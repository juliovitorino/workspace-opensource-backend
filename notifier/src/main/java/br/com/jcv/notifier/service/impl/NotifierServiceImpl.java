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


package br.com.jcv.notifier.service.impl;

import br.com.jcv.commons.library.commodities.constantes.GenericConstantes;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;

import br.com.jcv.notifier.dto.NotifierDTO;
import br.com.jcv.notifier.model.Notifier;
import br.com.jcv.notifier.constantes.NotifierConstantes;
import br.com.jcv.notifier.repository.NotifierRepository;
import br.com.jcv.notifier.service.NotifierService;
import br.com.jcv.notifier.exception.NotifierNotFoundException;

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
* NotifierServiceImpl - Implementation for Notifier interface
*
* @author Notifier
* @since Sat Dec 16 12:29:21 BRT 2023
*/


@Slf4j
@Service
public class NotifierServiceImpl implements NotifierService
{
    private static final String NOTIFIER_NOTFOUND_WITH_ID = "Notifier não encontrada com id = ";
    private static final String NOTIFIER_NOTFOUND_WITH_APPLICATIONUUID = "Notifier não encontrada com applicationUUID = ";
    private static final String NOTIFIER_NOTFOUND_WITH_USERUUID = "Notifier não encontrada com userUUID = ";
    private static final String NOTIFIER_NOTFOUND_WITH_TYPE = "Notifier não encontrada com type = ";
    private static final String NOTIFIER_NOTFOUND_WITH_TITLE = "Notifier não encontrada com title = ";
    private static final String NOTIFIER_NOTFOUND_WITH_DESCRIPTION = "Notifier não encontrada com description = ";
    private static final String NOTIFIER_NOTFOUND_WITH_ISREADED = "Notifier não encontrada com isReaded = ";
    private static final String NOTIFIER_NOTFOUND_WITH_STATUS = "Notifier não encontrada com status = ";
    private static final String NOTIFIER_NOTFOUND_WITH_DATECREATED = "Notifier não encontrada com dateCreated = ";
    private static final String NOTIFIER_NOTFOUND_WITH_DATEUPDATED = "Notifier não encontrada com dateUpdated = ";


    @Autowired private NotifierRepository notifierRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = NotifierNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando Notifier com id = {}", id);
        notifierRepository.findById(id)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_ID  + id));
        notifierRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO salvar(NotifierDTO notifierDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(notifierDTO.getId()) && notifierDTO.getId() != 0) {
            notifierDTO.setDateUpdated(now);
        } else {
            notifierDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            notifierDTO.setDateCreated(now);
            notifierDTO.setDateUpdated(now);
        }
        return this.toDTO(notifierRepository.save(this.toEntity(notifierDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findById(Long id) {
        Optional<Notifier> notifierData =
            Optional.ofNullable(notifierRepository.findById(id)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    NOTIFIER_NOTFOUND_WITH_ID  + id ))
                );

        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = NotifierNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<Notifier> notifierData =
            Optional.ofNullable(notifierRepository.findById(id)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_ID  + id))
                    );
        if (notifierData.isPresent()) {
            Notifier notifier = notifierData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.APPLICATIONUUID)) notifier.setApplicationUUID((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.USERUUID)) notifier.setUserUUID((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TYPE)) notifier.setType((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TITLE)) notifier.setTitle((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DESCRIPTION)) notifier.setDescription((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ISREADED)) notifier.setIsReaded((String)entry.getValue());

        }
        if(updates.get(NotifierConstantes.DATEUPDATED) == null) notifier.setDateUpdated(new Date());
        notifierRepository.save(notifier);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO updateStatusById(Long id, String status) {
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository.findById(id)
                .orElseThrow(() -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    NOTIFIER_NOTFOUND_WITH_ID + id))
                );
        Notifier notifier = notifierData.orElseGet(Notifier::new);
        notifier.setStatus(status);
        notifier.setDateUpdated(new Date());
        return toDTO(notifierRepository.save(notifier));

    }

    @Override
    public List<NotifierDTO> findAllByStatus(String status) {
        return notifierRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<Notifier> lstNotifier;
    Long id = null;
    UUID applicationUUID = null;
    UUID userUUID = null;
    String type = null;
    String title = null;
    String description = null;
    String isReaded = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.APPLICATIONUUID)) applicationUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.USERUUID)) userUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TYPE)) type = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DESCRIPTION)) description = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ISREADED)) isReaded = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<Notifier> paginaNotifier = notifierRepository.findNotifierByFilter(paging,
        id
        ,applicationUUID
        ,userUUID
        ,type
        ,title
        ,description
        ,isReaded
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstNotifier = paginaNotifier.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaNotifier.getNumber());
    response.put("totalItems", paginaNotifier.getTotalElements());
    response.put("totalPages", paginaNotifier.getTotalPages());
    response.put("pageNotifierItems", lstNotifier.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
)
    public List<NotifierDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    UUID applicationUUID = null;
    UUID userUUID = null;
    String type = null;
    String title = null;
    String description = null;
    String isReaded = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.APPLICATIONUUID)) applicationUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.USERUUID)) userUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TYPE)) type = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DESCRIPTION)) description = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ISREADED)) isReaded = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<Notifier> lstNotifier = notifierRepository.findNotifierByFilter(
            id
            ,applicationUUID
            ,userUUID
            ,type
            ,title
            ,description
            ,isReaded
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstNotifier.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByIdAndStatus(Long id, String status) {
        return notifierRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByApplicationUUIDAndStatus(UUID applicationUUID, String status) {
        return notifierRepository.findAllByApplicationUUIDAndStatus(applicationUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByUserUUIDAndStatus(UUID userUUID, String status) {
        return notifierRepository.findAllByUserUUIDAndStatus(userUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByTypeAndStatus(String type, String status) {
        return notifierRepository.findAllByTypeAndStatus(type, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByTitleAndStatus(String title, String status) {
        return notifierRepository.findAllByTitleAndStatus(title, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByDescriptionAndStatus(String description, String status) {
        return notifierRepository.findAllByDescriptionAndStatus(description, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByIsReadedAndStatus(String isReaded, String status) {
        return notifierRepository.findAllByIsReadedAndStatus(isReaded, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByDateCreatedAndStatus(Date dateCreated, String status) {
        return notifierRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return notifierRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByIdAndStatus(Long id, String status) {
        Long maxId = notifierRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_ID + id))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByIdAndStatus(Long id) {
        return this.findNotifierByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByApplicationUUIDAndStatus(UUID applicationUUID, String status) {
        Long maxId = notifierRepository.loadMaxIdByApplicationUUIDAndStatus(applicationUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_APPLICATIONUUID + applicationUUID,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_APPLICATIONUUID + applicationUUID))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByApplicationUUIDAndStatus(UUID applicationUUID) {
        return this.findNotifierByApplicationUUIDAndStatus(applicationUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByUserUUIDAndStatus(UUID userUUID, String status) {
        Long maxId = notifierRepository.loadMaxIdByUserUUIDAndStatus(userUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_USERUUID + userUUID,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_USERUUID + userUUID))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByUserUUIDAndStatus(UUID userUUID) {
        return this.findNotifierByUserUUIDAndStatus(userUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByTypeAndStatus(String type, String status) {
        Long maxId = notifierRepository.loadMaxIdByTypeAndStatus(type, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_TYPE + type,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_TYPE + type))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByTypeAndStatus(String type) {
        return this.findNotifierByTypeAndStatus(type, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByTitleAndStatus(String title, String status) {
        Long maxId = notifierRepository.loadMaxIdByTitleAndStatus(title, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_TITLE + title,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_TITLE + title))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByTitleAndStatus(String title) {
        return this.findNotifierByTitleAndStatus(title, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByDescriptionAndStatus(String description, String status) {
        Long maxId = notifierRepository.loadMaxIdByDescriptionAndStatus(description, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_DESCRIPTION + description,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_DESCRIPTION + description))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByDescriptionAndStatus(String description) {
        return this.findNotifierByDescriptionAndStatus(description, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByIsReadedAndStatus(String isReaded, String status) {
        Long maxId = notifierRepository.loadMaxIdByIsReadedAndStatus(isReaded, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_ISREADED + isReaded,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_ISREADED + isReaded))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByIsReadedAndStatus(String isReaded) {
        return this.findNotifierByIsReadedAndStatus(isReaded, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = notifierRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByDateCreatedAndStatus(Date dateCreated) {
        return this.findNotifierByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = notifierRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findNotifierByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateApplicationUUIDById(Long id, UUID applicationUUID) {
        findById(id);
        notifierRepository.updateApplicationUUIDById(id, applicationUUID);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateUserUUIDById(Long id, UUID userUUID) {
        findById(id);
        notifierRepository.updateUserUUIDById(id, userUUID);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateTypeById(Long id, String type) {
        findById(id);
        notifierRepository.updateTypeById(id, type);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateTitleById(Long id, String title) {
        findById(id);
        notifierRepository.updateTitleById(id, title);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateDescriptionById(Long id, String description) {
        findById(id);
        notifierRepository.updateDescriptionById(id, description);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateIsReadedById(Long id, String isReaded) {
        findById(id);
        notifierRepository.updateIsReadedById(id, isReaded);
        return findById(id);
    }


    public NotifierDTO toDTO(Notifier notifier) {
        NotifierDTO notifierDTO = new NotifierDTO();
                notifierDTO.setId(notifier.getId());
                notifierDTO.setApplicationUUID(notifier.getApplicationUUID());
                notifierDTO.setUserUUID(notifier.getUserUUID());
                notifierDTO.setType(notifier.getType());
                notifierDTO.setTitle(notifier.getTitle());
                notifierDTO.setDescription(notifier.getDescription());
                notifierDTO.setIsReaded(notifier.getIsReaded());
                notifierDTO.setStatus(notifier.getStatus());
                notifierDTO.setDateCreated(notifier.getDateCreated());
                notifierDTO.setDateUpdated(notifier.getDateUpdated());

        return notifierDTO;
    }

    public Notifier toEntity(NotifierDTO notifierDTO) {
        Notifier notifier = null;
        notifier = new Notifier();
                    notifier.setId(notifierDTO.getId());
                    notifier.setApplicationUUID(notifierDTO.getApplicationUUID());
                    notifier.setUserUUID(notifierDTO.getUserUUID());
                    notifier.setType(notifierDTO.getType());
                    notifier.setTitle(notifierDTO.getTitle());
                    notifier.setDescription(notifierDTO.getDescription());
                    notifier.setIsReaded(notifierDTO.getIsReaded());
                    notifier.setStatus(notifierDTO.getStatus());
                    notifier.setDateCreated(notifierDTO.getDateCreated());
                    notifier.setDateUpdated(notifierDTO.getDateUpdated());

        return notifier;
    }
}
