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
import br.com.jcv.reminder.corelayer.model.ReminderList;
import br.com.jcv.reminder.corelayer.repository.ReminderListRepository;
import br.com.jcv.reminder.corelayer.service.ReminderListService;
import br.com.jcv.reminder.infrastructure.constantes.ReminderListConstantes;
import br.com.jcv.reminder.infrastructure.dto.ReminderListDTO;
import br.com.jcv.reminder.infrastructure.exception.ReminderListNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* ReminderListServiceImpl - Implementation for ReminderList interface
*
* @author ReminderList
* @since Sat May 11 17:44:44 BRT 2024
*/


@Slf4j
@Service
public class ReminderListServiceImpl implements ReminderListService
{
    private static final String REMINDERLIST_NOTFOUND_WITH_ID = "ReminderList não encontrada com id = ";
    private static final String REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALAPP = "ReminderList não encontrada com uuidExternalApp = ";
    private static final String REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALUSER = "ReminderList não encontrada com uuidExternalUser = ";
    private static final String REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALLIST = "ReminderList não encontrada com uuidExternalList = ";
    private static final String REMINDERLIST_NOTFOUND_WITH_TITLE = "ReminderList não encontrada com title = ";
    private static final String REMINDERLIST_NOTFOUND_WITH_STATUS = "ReminderList não encontrada com status = ";
    private static final String REMINDERLIST_NOTFOUND_WITH_DATECREATED = "ReminderList não encontrada com dateCreated = ";
    private static final String REMINDERLIST_NOTFOUND_WITH_DATEUPDATED = "ReminderList não encontrada com dateUpdated = ";


    @Autowired private ReminderListRepository reminderlistRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderListNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando ReminderList com id = {}", id);
        reminderlistRepository.findById(id)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_ID  + id));
        reminderlistRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO salvar(ReminderListDTO reminderlistDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(reminderlistDTO.getId()) && reminderlistDTO.getId() != 0) {
            reminderlistDTO.setDateUpdated(now);
        } else {
            reminderlistDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            reminderlistDTO.setDateCreated(now);
            reminderlistDTO.setDateUpdated(now);
        }
        return this.toDTO(reminderlistRepository.save(this.toEntity(reminderlistDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findById(Long id) {
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable(reminderlistRepository.findById(id)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    REMINDERLIST_NOTFOUND_WITH_ID  + id ))
                );

        return reminderlistData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderListNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<ReminderList> reminderlistData =
            Optional.ofNullable(reminderlistRepository.findById(id)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_ID  + id))
                    );
        if (reminderlistData.isPresent()) {
            ReminderList reminderlist = reminderlistData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALAPP)) reminderlist.setUuidExternalApp((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALUSER)) reminderlist.setUuidExternalUser((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALLIST)) reminderlist.setUuidExternalList((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.TITLE)) reminderlist.setTitle((String)entry.getValue());

        }
        if(updates.get(ReminderListConstantes.DATEUPDATED) == null) reminderlist.setDateUpdated(new Date());
        reminderlistRepository.save(reminderlist);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO updateStatusById(Long id, String status) {
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable( reminderlistRepository.findById(id)
                .orElseThrow(() -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    REMINDERLIST_NOTFOUND_WITH_ID + id))
                );
        ReminderList reminderlist = reminderlistData.orElseGet(ReminderList::new);
        reminderlist.setStatus(status);
        reminderlist.setDateUpdated(new Date());
        return toDTO(reminderlistRepository.save(reminderlist));

    }

    @Override
    public List<ReminderListDTO> findAllByStatus(String status) {
        return reminderlistRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<ReminderList> lstReminderList;
    Long id = null;
    UUID uuidExternalApp = null;
    UUID uuidExternalUser = null;
    UUID uuidExternalList = null;
    String title = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALUSER)) uuidExternalUser = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALLIST)) uuidExternalList = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<ReminderList> paginaReminderList = reminderlistRepository.findReminderListByFilter(paging,
        id
        ,uuidExternalApp
        ,uuidExternalUser
        ,uuidExternalList
        ,title
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstReminderList = paginaReminderList.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaReminderList.getNumber());
    response.put("totalItems", paginaReminderList.getTotalElements());
    response.put("totalPages", paginaReminderList.getTotalPages());
    response.put("pageReminderListItems", lstReminderList.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
)
    public List<ReminderListDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    UUID uuidExternalApp = null;
    UUID uuidExternalUser = null;
    UUID uuidExternalList = null;
    String title = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALAPP)) uuidExternalApp = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALUSER)) uuidExternalUser = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.UUIDEXTERNALLIST)) uuidExternalList = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderListConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<ReminderList> lstReminderList = reminderlistRepository.findReminderListByFilter(
            id
            ,uuidExternalApp
            ,uuidExternalUser
            ,uuidExternalList
            ,title
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstReminderList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public List<ReminderListDTO> findAllReminderListByIdAndStatus(Long id, String status) {
        return reminderlistRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public List<ReminderListDTO> findAllReminderListByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        return reminderlistRepository.findAllByUuidExternalAppAndStatus(uuidExternalApp, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public List<ReminderListDTO> findAllReminderListByUuidExternalUserAndStatus(UUID uuidExternalUser, String status) {
        return reminderlistRepository.findAllByUuidExternalUserAndStatus(uuidExternalUser, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public List<ReminderListDTO> findAllReminderListByUuidExternalListAndStatus(UUID uuidExternalList, String status) {
        return reminderlistRepository.findAllByUuidExternalListAndStatus(uuidExternalList, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public List<ReminderListDTO> findAllReminderListByTitleAndStatus(String title, String status) {
        return reminderlistRepository.findAllByTitleAndStatus(title, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public List<ReminderListDTO> findAllReminderListByDateCreatedAndStatus(Date dateCreated, String status) {
        return reminderlistRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public List<ReminderListDTO> findAllReminderListByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return reminderlistRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByIdAndStatus(Long id, String status) {
        Long maxId = reminderlistRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable( reminderlistRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_ID + id))
                );
        return reminderlistData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByIdAndStatus(Long id) {
        return this.findReminderListByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByUuidExternalAppAndStatus(UUID uuidExternalApp, String status) {
        Long maxId = reminderlistRepository.loadMaxIdByUuidExternalAppAndStatus(uuidExternalApp, status);
        if(maxId == null) maxId = 0L;
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable( reminderlistRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALAPP + uuidExternalApp))
                );
        return reminderlistData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByUuidExternalAppAndStatus(UUID uuidExternalApp) {
        return this.findReminderListByUuidExternalAppAndStatus(uuidExternalApp, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByUuidExternalUserAndStatus(UUID uuidExternalUser, String status) {
        Long maxId = reminderlistRepository.loadMaxIdByUuidExternalUserAndStatus(uuidExternalUser, status);
        if(maxId == null) maxId = 0L;
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable( reminderlistRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALUSER + uuidExternalUser,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALUSER + uuidExternalUser))
                );
        return reminderlistData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByUuidExternalUserAndStatus(UUID uuidExternalUser) {
        return this.findReminderListByUuidExternalUserAndStatus(uuidExternalUser, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByUuidExternalListAndStatus(UUID uuidExternalList, String status) {
        Long maxId = reminderlistRepository.loadMaxIdByUuidExternalListAndStatus(uuidExternalList, status);
        if(maxId == null) maxId = 0L;
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable( reminderlistRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALLIST + uuidExternalList,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_UUIDEXTERNALLIST + uuidExternalList))
                );
        return reminderlistData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByUuidExternalListAndStatus(UUID uuidExternalList) {
        return this.findReminderListByUuidExternalListAndStatus(uuidExternalList, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByTitleAndStatus(String title, String status) {
        Long maxId = reminderlistRepository.loadMaxIdByTitleAndStatus(title, status);
        if(maxId == null) maxId = 0L;
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable( reminderlistRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_TITLE + title,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_TITLE + title))
                );
        return reminderlistData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByTitleAndStatus(String title) {
        return this.findReminderListByTitleAndStatus(title, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = reminderlistRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable( reminderlistRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return reminderlistData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByDateCreatedAndStatus(Date dateCreated) {
        return this.findReminderListByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = reminderlistRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<ReminderList> reminderlistData =
            Optional.ofNullable( reminderlistRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderListNotFoundException(REMINDERLIST_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        REMINDERLIST_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return reminderlistData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderListNotFoundException.class
    )
    public ReminderListDTO findReminderListByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findReminderListByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderListDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp) {
        findById(id);
        reminderlistRepository.updateUuidExternalAppById(id, uuidExternalApp);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderListDTO updateUuidExternalUserById(Long id, UUID uuidExternalUser) {
        findById(id);
        reminderlistRepository.updateUuidExternalUserById(id, uuidExternalUser);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderListDTO updateUuidExternalListById(Long id, UUID uuidExternalList) {
        findById(id);
        reminderlistRepository.updateUuidExternalListById(id, uuidExternalList);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderListDTO updateTitleById(Long id, String title) {
        findById(id);
        reminderlistRepository.updateTitleById(id, title);
        return findById(id);
    }


    public ReminderListDTO toDTO(ReminderList reminderlist) {
        ReminderListDTO reminderlistDTO = new ReminderListDTO();
                reminderlistDTO.setId(reminderlist.getId());
                reminderlistDTO.setUuidExternalApp(reminderlist.getUuidExternalApp());
                reminderlistDTO.setUuidExternalUser(reminderlist.getUuidExternalUser());
                reminderlistDTO.setUuidExternalList(reminderlist.getUuidExternalList());
                reminderlistDTO.setTitle(reminderlist.getTitle());
                reminderlistDTO.setStatus(reminderlist.getStatus());
                reminderlistDTO.setDateCreated(reminderlist.getDateCreated());
                reminderlistDTO.setDateUpdated(reminderlist.getDateUpdated());

        return reminderlistDTO;
    }

    public ReminderList toEntity(ReminderListDTO reminderlistDTO) {
        ReminderList reminderlist = null;
        reminderlist = new ReminderList();
                    reminderlist.setId(reminderlistDTO.getId());
                    reminderlist.setUuidExternalApp(reminderlistDTO.getUuidExternalApp());
                    reminderlist.setUuidExternalUser(reminderlistDTO.getUuidExternalUser());
                    reminderlist.setUuidExternalList(reminderlistDTO.getUuidExternalList());
                    reminderlist.setTitle(reminderlistDTO.getTitle());
                    reminderlist.setStatus(reminderlistDTO.getStatus());
                    reminderlist.setDateCreated(reminderlistDTO.getDateCreated());
                    reminderlist.setDateUpdated(reminderlistDTO.getDateUpdated());

        return reminderlist;
    }
}
