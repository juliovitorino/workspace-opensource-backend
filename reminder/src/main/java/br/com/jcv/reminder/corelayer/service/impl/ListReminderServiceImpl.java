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


package br.com.jcv.reminder.corelayer.service.impl;

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
import br.com.jcv.reminder.corelayer.model.ListReminder;
import br.com.jcv.reminder.corelayer.repository.ListReminderRepository;
import br.com.jcv.reminder.corelayer.service.ListReminderService;
import br.com.jcv.reminder.infrastructure.constantes.ListReminderConstantes;
import br.com.jcv.reminder.infrastructure.dto.ListReminderDTO;
import br.com.jcv.reminder.infrastructure.exception.ListReminderNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* ListReminderServiceImpl - Implementation for ListReminder interface
*
* @author ListReminder
* @since Sat May 11 14:30:00 BRT 2024
*/


@Slf4j
@Service
public class ListReminderServiceImpl implements ListReminderService
{
    private static final String LISTREMINDER_NOTFOUND_WITH_ID = "ListReminder não encontrada com id = ";
    private static final String LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALAPP = "ListReminder não encontrada com uuidExternalApp = ";
    private static final String LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALUSER = "ListReminder não encontrada com uuidExternalUser = ";
    private static final String LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALLIST = "ListReminder não encontrada com uuidExternalList = ";
    private static final String LISTREMINDER_NOTFOUND_WITH_TITLE = "ListReminder não encontrada com title = ";
    private static final String LISTREMINDER_NOTFOUND_WITH_STATUS = "ListReminder não encontrada com status = ";
    private static final String LISTREMINDER_NOTFOUND_WITH_DATECREATED = "ListReminder não encontrada com dateCreated = ";
    private static final String LISTREMINDER_NOTFOUND_WITH_DATEUPDATED = "ListReminder não encontrada com dateUpdated = ";


    @Autowired private ListReminderRepository listreminderRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ListReminderNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando ListReminder com id = {}", id);
        listreminderRepository.findById(id)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_ID  + id));
        listreminderRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO salvar(ListReminderDTO listreminderDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(listreminderDTO.getId()) && listreminderDTO.getId() != 0) {
            listreminderDTO.setDateUpdated(now);
        } else {
            listreminderDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            listreminderDTO.setDateCreated(now);
            listreminderDTO.setDateUpdated(now);
        }
        return this.toDTO(listreminderRepository.save(this.toEntity(listreminderDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findById(Long id) {
        Optional<ListReminder> listreminderData =
            Optional.ofNullable(listreminderRepository.findById(id)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    LISTREMINDER_NOTFOUND_WITH_ID  + id ))
                );

        return listreminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ListReminderNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<ListReminder> listreminderData =
            Optional.ofNullable(listreminderRepository.findById(id)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_ID  + id))
                    );
        if (listreminderData.isPresent()) {
            ListReminder listreminder = listreminderData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALAPP)) listreminder.setUuidExternalApp((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALUSER)) listreminder.setUuidExternalUser((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALLIST)) listreminder.setUuidExternalList((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.TITLE)) listreminder.setTitle((String)entry.getValue());

        }
        if(updates.get(ListReminderConstantes.DATEUPDATED) == null) listreminder.setDateUpdated(new Date());
        listreminderRepository.save(listreminder);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO updateStatusById(Long id, String status) {
        Optional<ListReminder> listreminderData =
            Optional.ofNullable( listreminderRepository.findById(id)
                .orElseThrow(() -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    LISTREMINDER_NOTFOUND_WITH_ID + id))
                );
        ListReminder listreminder = listreminderData.orElseGet(ListReminder::new);
        listreminder.setStatus(status);
        listreminder.setDateUpdated(new Date());
        return toDTO(listreminderRepository.save(listreminder));

    }

    @Override
    public List<ListReminderDTO> findAllByStatus(String status) {
        return listreminderRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<ListReminder> lstListReminder;
    Long id = null;
    UUID uuidExternalApp = null;
    UUID uuidExternalUser = null;
    UUID uuidExternalList = null;
    String title = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALUSER)) uuidExternalUser = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALLIST)) uuidExternalList = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<ListReminder> paginaListReminder = listreminderRepository.findListReminderByFilter(paging,
        id
        ,uuidExternalApp
        ,uuidExternalUser
        ,uuidExternalList
        ,title
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstListReminder = paginaListReminder.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaListReminder.getNumber());
    response.put("totalItems", paginaListReminder.getTotalElements());
    response.put("totalPages", paginaListReminder.getTotalPages());
    response.put("pageListReminderItems", lstListReminder.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
)
    public List<ListReminderDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    UUID uuidExternalApp = null;
    UUID uuidExternalUser = null;
    UUID uuidExternalList = null;
    String title = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALUSER)) uuidExternalUser = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.UUIDEXTERNALLIST)) uuidExternalList = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ListReminderConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<ListReminder> lstListReminder = listreminderRepository.findListReminderByFilter(
            id
            ,uuidExternalApp
            ,uuidExternalUser
            ,uuidExternalList
            ,title
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstListReminder.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public List<ListReminderDTO> findAllListReminderByIdAndStatus(Long id, String status) {
        return listreminderRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public List<ListReminderDTO> findAllListReminderByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        return listreminderRepository.findAllByUuidExternalAppAndStatus(uuidExternalApp, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public List<ListReminderDTO> findAllListReminderByUuidExternalUserAndStatus(UUID uuidExternalUser, String status) {
        return listreminderRepository.findAllByUuidExternalUserAndStatus(uuidExternalUser, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public List<ListReminderDTO> findAllListReminderByUuidExternalListAndStatus(UUID uuidExternalList, String status) {
        return listreminderRepository.findAllByUuidExternalListAndStatus(uuidExternalList, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public List<ListReminderDTO> findAllListReminderByTitleAndStatus(String title, String status) {
        return listreminderRepository.findAllByTitleAndStatus(title, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public List<ListReminderDTO> findAllListReminderByDateCreatedAndStatus(Date dateCreated, String status) {
        return listreminderRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public List<ListReminderDTO> findAllListReminderByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return listreminderRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByIdAndStatus(Long id, String status) {
        Long maxId = listreminderRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<ListReminder> listreminderData =
            Optional.ofNullable( listreminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_ID + id))
                );
        return listreminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByIdAndStatus(Long id) {
        return this.findListReminderByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        Long maxId = listreminderRepository.loadMaxIdByUuidExternalAppAndStatus(uuidExternalApp, status);
        if(maxId == null) maxId = 0L;
        Optional<ListReminder> listreminderData =
            Optional.ofNullable( listreminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp))
                );
        return listreminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByUuidExternalAppAndStatus(UUID uuidExternalApp) {
        return this.findListReminderByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByUuidExternalUserAndStatus(UUID uuidExternalUser, String status) {
        Long maxId = listreminderRepository.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUser, status);
        if(maxId == null) maxId = 0L;
        Optional<ListReminder> listreminderData =
            Optional.ofNullable( listreminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALUSER + uuidExternalUser,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALUSER + uuidExternalUser))
                );
        return listreminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByUuidExternalUserAndStatus(UUID uuidExternalUser) {
        return this.findListReminderByUuidExternalUserAndStatus(uuidExternalUser, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByUuidExternalListAndStatus(UUID uuidExternalList, String status) {
        Long maxId = listreminderRepository.loadMaxIdByUuidExternalListAndStatus(uuidExternalList, status);
        if(maxId == null) maxId = 0L;
        Optional<ListReminder> listreminderData =
            Optional.ofNullable( listreminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALLIST + uuidExternalList,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_UUIDEXTERNALLIST + uuidExternalList))
                );
        return listreminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByUuidExternalListAndStatus(UUID uuidExternalList) {
        return this.findListReminderByUuidExternalListAndStatus(uuidExternalList, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByTitleAndStatus(String title, String status) {
        Long maxId = listreminderRepository.loadMaxIdByTitleAndStatus(title, status);
        if(maxId == null) maxId = 0L;
        Optional<ListReminder> listreminderData =
            Optional.ofNullable( listreminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_TITLE + title,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_TITLE + title))
                );
        return listreminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByTitleAndStatus(String title) {
        return this.findListReminderByTitleAndStatus(title, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = listreminderRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<ListReminder> listreminderData =
            Optional.ofNullable( listreminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return listreminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByDateCreatedAndStatus(Date dateCreated) {
        return this.findListReminderByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = listreminderRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<ListReminder> listreminderData =
            Optional.ofNullable( listreminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ListReminderNotFoundException(LISTREMINDER_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        LISTREMINDER_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return listreminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ListReminderNotFoundException.class
    )
    public ListReminderDTO findListReminderByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findListReminderByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ListReminderDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp) {
        findById(id);
        listreminderRepository.updateUuidExternalAppById(id, uuidExternalApp);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ListReminderDTO updateUuidExternalUserById(Long id, UUID uuidExternalUser) {
        findById(id);
        listreminderRepository.updateUuidExternalUserById(id, uuidExternalUser);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ListReminderDTO updateUuidExternalListById(Long id, UUID uuidExternalList) {
        findById(id);
        listreminderRepository.updateUuidExternalListById(id, uuidExternalList);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ListReminderDTO updateTitleById(Long id, String title) {
        findById(id);
        listreminderRepository.updateTitleById(id, title);
        return findById(id);
    }


    public ListReminderDTO toDTO(ListReminder listreminder) {
        ListReminderDTO listreminderDTO = new ListReminderDTO();
                listreminderDTO.setId(listreminder.getId());
                listreminderDTO.setUuidExternalApp(listreminder.getUuidExternalApp());
                listreminderDTO.setUuidExternalUser(listreminder.getUuidExternalUser());
                listreminderDTO.setUuidExternalList(listreminder.getUuidExternalList());
                listreminderDTO.setTitle(listreminder.getTitle());
                listreminderDTO.setStatus(listreminder.getStatus());
                listreminderDTO.setDateCreated(listreminder.getDateCreated());
                listreminderDTO.setDateUpdated(listreminder.getDateUpdated());

        return listreminderDTO;
    }

    public ListReminder toEntity(ListReminderDTO listreminderDTO) {
        ListReminder listreminder = null;
        listreminder = new ListReminder();
                    listreminder.setId(listreminderDTO.getId());
                    listreminder.setUuidExternalApp(listreminderDTO.getUuidExternalApp());
                    listreminder.setUuidExternalUser(listreminderDTO.getUuidExternalUser());
                    listreminder.setUuidExternalList(listreminderDTO.getUuidExternalList());
                    listreminder.setTitle(listreminderDTO.getTitle());
                    listreminder.setStatus(listreminderDTO.getStatus());
                    listreminder.setDateCreated(listreminderDTO.getDateCreated());
                    listreminder.setDateUpdated(listreminderDTO.getDateUpdated());

        return listreminder;
    }
}
