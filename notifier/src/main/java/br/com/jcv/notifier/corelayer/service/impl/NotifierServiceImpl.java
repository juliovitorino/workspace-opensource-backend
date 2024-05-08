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


package br.com.jcv.notifier.corelayer.service.impl;

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
import br.com.jcv.notifier.corebusiness.addnotification.NotificationRequest;
import br.com.jcv.notifier.corelayer.model.Notifier;
import br.com.jcv.notifier.corelayer.repository.NotifierRepository;
import br.com.jcv.notifier.corelayer.service.NotifierService;
import br.com.jcv.notifier.infrastructure.constantes.NotifierConstantes;
import br.com.jcv.notifier.infrastructure.dto.NotifierDTO;
import br.com.jcv.notifier.infrastructure.exception.NotifierNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* NotifierServiceImpl - Implementation for Notifier interface
*
* @author Notifier
* @since Mon May 06 08:02:37 BRT 2024
*/


@Slf4j
@Service
public class NotifierServiceImpl implements NotifierService
{
    private static final String NOTIFIER_NOTFOUND_WITH_ID = "Notifier não encontrada com id = ";
    private static final String NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALAPP = "Notifier não encontrada com uuidExternalApp = ";
    private static final String NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALUSER = "Notifier não encontrada com uuidExternalUser = ";
    private static final String NOTIFIER_NOTFOUND_WITH_TYPE = "Notifier não encontrada com type = ";
    private static final String NOTIFIER_NOTFOUND_WITH_KEY = "Notifier não encontrada com key = ";
    private static final String NOTIFIER_NOTFOUND_WITH_TITLE = "Notifier não encontrada com title = ";
    private static final String NOTIFIER_NOTFOUND_WITH_DESCRIPTION = "Notifier não encontrada com description = ";
    private static final String NOTIFIER_NOTFOUND_WITH_URLIMAGE = "Notifier não encontrada com urlImage = ";
    private static final String NOTIFIER_NOTFOUND_WITH_ICONCLASS = "Notifier não encontrada com iconClass = ";
    private static final String NOTIFIER_NOTFOUND_WITH_URLFOLLOW = "Notifier não encontrada com urlFollow = ";
    private static final String NOTIFIER_NOTFOUND_WITH_OBJECTFREE = "Notifier não encontrada com objectFree = ";
    private static final String NOTIFIER_NOTFOUND_WITH_SEENINDICATOR = "Notifier não encontrada com seenIndicator = ";
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
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.UUIDEXTERNALAPP)) notifier.setUuidExternalApp((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.UUIDEXTERNALUSER)) notifier.setUuidExternalUser((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TYPE)) notifier.setType((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.KEY)) notifier.setKey((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TITLE)) notifier.setTitle((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DESCRIPTION)) notifier.setDescription((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.URLIMAGE)) notifier.setUrlImage((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ICONCLASS)) notifier.setIconClass((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.URLFOLLOW)) notifier.setUrlFollow((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.OBJECTFREE)) notifier.setObjectFree((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(NotifierConstantes.SEENINDICATOR)) notifier.setSeenIndicator((String)entry.getValue());

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
    UUID uuidExternalApp = null;
    UUID uuidExternalUser = null;
    String type = null;
    String key = null;
    String title = null;
    String description = null;
    String urlImage = null;
    String iconClass = null;
    String urlFollow = null;
    String objectFree = null;
    String seenIndicator = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.UUIDEXTERNALUSER)) uuidExternalUser = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TYPE)) type = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.KEY)) key = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DESCRIPTION)) description = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.URLIMAGE)) urlImage = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ICONCLASS)) iconClass = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.URLFOLLOW)) urlFollow = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.OBJECTFREE)) objectFree = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.SEENINDICATOR)) seenIndicator = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<Notifier> paginaNotifier = notifierRepository.findNotifierByFilter(paging,
        id
        ,uuidExternalApp
        ,uuidExternalUser
        ,type
        ,key
        ,title
        ,description
        ,urlImage
        ,iconClass
        ,urlFollow
        ,objectFree
        ,seenIndicator
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
    UUID uuidExternalApp = null;
    UUID uuidExternalUser = null;
    String type = null;
    String key = null;
    String title = null;
    String description = null;
    String urlImage = null;
    String iconClass = null;
    String urlFollow = null;
    String objectFree = null;
    String seenIndicator = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.UUIDEXTERNALUSER)) uuidExternalUser = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TYPE)) type = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.KEY)) key = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DESCRIPTION)) description = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.URLIMAGE)) urlImage = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.ICONCLASS)) iconClass = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.URLFOLLOW)) urlFollow = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.OBJECTFREE)) objectFree = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.SEENINDICATOR)) seenIndicator = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(NotifierConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<Notifier> lstNotifier = notifierRepository.findNotifierByFilter(
            id
            ,uuidExternalApp
            ,uuidExternalUser
            ,type
            ,key
            ,title
            ,description
            ,urlImage
            ,iconClass
            ,urlFollow
            ,objectFree
            ,seenIndicator
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
    public List<NotifierDTO> findAllNotifierByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        return notifierRepository.findAllByUuidExternalAppAndStatus(uuidExternalApp, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByUuidExternalUserAndStatus(UUID uuidExternalUser, String status) {
        return notifierRepository.findAllByUuidExternalUserAndStatus(uuidExternalUser, status).stream().map(this::toDTO).collect(Collectors.toList());
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
    public List<NotifierDTO> findAllNotifierByKeyAndStatus(String key, String status) {
        return notifierRepository.findAllByKeyAndStatus(key, status).stream().map(this::toDTO).collect(Collectors.toList());
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
    public List<NotifierDTO> findAllNotifierByUrlImageAndStatus(String urlImage, String status) {
        return notifierRepository.findAllByUrlImageAndStatus(urlImage, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByIconClassAndStatus(String iconClass, String status) {
        return notifierRepository.findAllByIconClassAndStatus(iconClass, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByUrlFollowAndStatus(String urlFollow, String status) {
        return notifierRepository.findAllByUrlFollowAndStatus(urlFollow, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierByObjectFreeAndStatus(String objectFree, String status) {
        return notifierRepository.findAllByObjectFreeAndStatus(objectFree, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public List<NotifierDTO> findAllNotifierBySeenIndicatorAndStatus(String seenIndicator, String status) {
        return notifierRepository.findAllBySeenIndicatorAndStatus(seenIndicator, status).stream().map(this::toDTO).collect(Collectors.toList());
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
    public NotifierDTO findNotifierByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        Long maxId = notifierRepository.loadMaxIdByUuidExternalAppAndStatus(uuidExternalApp, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByUuidExternalAppAndStatus(UUID uuidExternalApp) {
        return this.findNotifierByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByUuidExternalUserAndStatus(UUID uuidExternalUser, String status) {
        Long maxId = notifierRepository.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUser, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALUSER + uuidExternalUser,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_UUIDEXTERNALUSER + uuidExternalUser))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByUuidExternalUserAndStatus(UUID uuidExternalUser) {
        return this.findNotifierByUuidExternalUserAndStatus(uuidExternalUser, GenericStatusEnums.ATIVO.getShortValue());
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
    public NotifierDTO findNotifierByKeyAndStatus(String key, String status) {
        Long maxId = notifierRepository.loadMaxIdByKeyAndStatus(key, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_KEY + key,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_KEY + key))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByKeyAndStatus(String key) {
        return this.findNotifierByKeyAndStatus(key, GenericStatusEnums.ATIVO.getShortValue());
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
    public NotifierDTO findNotifierByUrlImageAndStatus(String urlImage, String status) {
        Long maxId = notifierRepository.loadMaxIdByUrlImageAndStatus(urlImage, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_URLIMAGE + urlImage,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_URLIMAGE + urlImage))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByUrlImageAndStatus(String urlImage) {
        return this.findNotifierByUrlImageAndStatus(urlImage, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByIconClassAndStatus(String iconClass, String status) {
        Long maxId = notifierRepository.loadMaxIdByIconClassAndStatus(iconClass, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_ICONCLASS + iconClass,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_ICONCLASS + iconClass))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByIconClassAndStatus(String iconClass) {
        return this.findNotifierByIconClassAndStatus(iconClass, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByUrlFollowAndStatus(String urlFollow, String status) {
        Long maxId = notifierRepository.loadMaxIdByUrlFollowAndStatus(urlFollow, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_URLFOLLOW + urlFollow,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_URLFOLLOW + urlFollow))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByUrlFollowAndStatus(String urlFollow) {
        return this.findNotifierByUrlFollowAndStatus(urlFollow, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByObjectFreeAndStatus(String objectFree, String status) {
        Long maxId = notifierRepository.loadMaxIdByObjectFreeAndStatus(objectFree, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_OBJECTFREE + objectFree,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_OBJECTFREE + objectFree))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierByObjectFreeAndStatus(String objectFree) {
        return this.findNotifierByObjectFreeAndStatus(objectFree, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierBySeenIndicatorAndStatus(String seenIndicator, String status) {
        Long maxId = notifierRepository.loadMaxIdBySeenIndicatorAndStatus(seenIndicator, status);
        if(maxId == null) maxId = 0L;
        Optional<Notifier> notifierData =
            Optional.ofNullable( notifierRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new NotifierNotFoundException(NOTIFIER_NOTFOUND_WITH_SEENINDICATOR + seenIndicator,
                        HttpStatus.NOT_FOUND,
                        NOTIFIER_NOTFOUND_WITH_SEENINDICATOR + seenIndicator))
                );
        return notifierData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = NotifierNotFoundException.class
    )
    public NotifierDTO findNotifierBySeenIndicatorAndStatus(String seenIndicator) {
        return this.findNotifierBySeenIndicatorAndStatus(seenIndicator, GenericStatusEnums.ATIVO.getShortValue());
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
    public NotifierDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp) {
        findById(id);
        notifierRepository.updateUuidExternalAppById(id, uuidExternalApp);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateUuidExternalUserById(Long id, UUID uuidExternalUser) {
        findById(id);
        notifierRepository.updateUuidExternalUserById(id, uuidExternalUser);
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
    public NotifierDTO updateKeyById(Long id, String key) {
        findById(id);
        notifierRepository.updateKeyById(id, key);
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
    public NotifierDTO updateUrlImageById(Long id, String urlImage) {
        findById(id);
        notifierRepository.updateUrlImageById(id, urlImage);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateIconClassById(Long id, String iconClass) {
        findById(id);
        notifierRepository.updateIconClassById(id, iconClass);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateUrlFollowById(Long id, String urlFollow) {
        findById(id);
        notifierRepository.updateUrlFollowById(id, urlFollow);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateObjectFreeById(Long id, String objectFree) {
        findById(id);
        notifierRepository.updateObjectFreeById(id, objectFree);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public NotifierDTO updateSeenIndicatorById(Long id, String seenIndicator) {
        findById(id);
        notifierRepository.updateSeenIndicatorById(id, seenIndicator);
        return findById(id);
    }


    public NotifierDTO toDTO(Notifier notifier) {
        NotifierDTO notifierDTO = new NotifierDTO();
        notifierDTO.setId(notifier.getId());
        notifierDTO.setUuidExternalApp(notifier.getUuidExternalApp());
        notifierDTO.setUuidExternalUser(notifier.getUuidExternalUser());
        notifierDTO.setType(notifier.getType());
        notifierDTO.setKey(notifier.getKey());
        notifierDTO.setTitle(notifier.getTitle());
        notifierDTO.setDescription(notifier.getDescription());
        notifierDTO.setUrlImage(notifier.getUrlImage());
        notifierDTO.setIconClass(notifier.getIconClass());
        notifierDTO.setUrlFollow(notifier.getUrlFollow());
        notifierDTO.setObjectFree(notifier.getObjectFree());
        notifierDTO.setSeenIndicator(notifier.getSeenIndicator());
        notifierDTO.setStatus(notifier.getStatus());
        notifierDTO.setDateCreated(notifier.getDateCreated());
        notifierDTO.setDateUpdated(notifier.getDateUpdated());

        return notifierDTO;
    }
    public NotifierDTO toDTO(NotificationRequest notifier) {
        NotifierDTO notifierDTO = new NotifierDTO();
        notifierDTO.setUuidExternalApp(notifier.getUuidExternalApp());
        notifierDTO.setUuidExternalUser(notifier.getUuidExternalUser());
        notifierDTO.setType(notifier.getType());
        notifierDTO.setKey(notifier.getKey());
        notifierDTO.setTitle(notifier.getTitle());
        notifierDTO.setDescription(notifier.getDescription());
        notifierDTO.setUrlImage(notifier.getUrlImage());
        notifierDTO.setIconClass(notifier.getIconClass());
        notifierDTO.setUrlFollow(notifier.getUrlFollow());
        notifierDTO.setObjectFree(notifier.getObjectFree());
        notifierDTO.setSeenIndicator(notifier.getSeenIndicator());

        return notifierDTO;
    }

    public Notifier toEntity(NotifierDTO notifierDTO) {
        Notifier notifier = null;
        notifier = new Notifier();
        notifier.setId(notifierDTO.getId());
        notifier.setUuidExternalApp(notifierDTO.getUuidExternalApp());
        notifier.setUuidExternalUser(notifierDTO.getUuidExternalUser());
        notifier.setType(notifierDTO.getType());
        notifier.setKey(notifierDTO.getKey());
        notifier.setTitle(notifierDTO.getTitle());
        notifier.setDescription(notifierDTO.getDescription());
        notifier.setUrlImage(notifierDTO.getUrlImage());
        notifier.setIconClass(notifierDTO.getIconClass());
        notifier.setUrlFollow(notifierDTO.getUrlFollow());
        notifier.setObjectFree(notifierDTO.getObjectFree());
        notifier.setSeenIndicator(notifierDTO.getSeenIndicator());
        notifier.setStatus(notifierDTO.getStatus());
        notifier.setDateCreated(notifierDTO.getDateCreated());
        notifier.setDateUpdated(notifierDTO.getDateUpdated());

        return notifier;
    }

}
