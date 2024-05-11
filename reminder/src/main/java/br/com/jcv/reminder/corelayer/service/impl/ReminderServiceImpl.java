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
import br.com.jcv.reminder.corelayer.model.Reminder;
import br.com.jcv.reminder.corelayer.repository.ReminderRepository;
import br.com.jcv.reminder.corelayer.service.ReminderService;
import br.com.jcv.reminder.infrastructure.constantes.ReminderConstantes;
import br.com.jcv.reminder.infrastructure.dto.ReminderDTO;
import br.com.jcv.reminder.infrastructure.enums.EnumPriority;
import br.com.jcv.reminder.infrastructure.exception.ReminderNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**
* ReminderServiceImpl - Implementation for Reminder interface
*
* @author Reminder
* @since Sat May 11 18:10:51 BRT 2024
*/


@Slf4j
@Service
public class ReminderServiceImpl implements ReminderService
{
    private static final String REMINDER_NOTFOUND_WITH_ID = "Reminder não encontrada com id = ";
    private static final String REMINDER_NOTFOUND_WITH_IDLIST = "Reminder não encontrada com idList = ";
    private static final String REMINDER_NOTFOUND_WITH_TITLE = "Reminder não encontrada com title = ";
    private static final String REMINDER_NOTFOUND_WITH_NOTE = "Reminder não encontrada com note = ";
    private static final String REMINDER_NOTFOUND_WITH_TAGS = "Reminder não encontrada com tags = ";
    private static final String REMINDER_NOTFOUND_WITH_FULLURLIMAGE = "Reminder não encontrada com fullUrlImage = ";
    private static final String REMINDER_NOTFOUND_WITH_URL = "Reminder não encontrada com url = ";
    private static final String REMINDER_NOTFOUND_WITH_PRIORITY = "Reminder não encontrada com priority = ";
    private static final String REMINDER_NOTFOUND_WITH_FLAG = "Reminder não encontrada com flag = ";
    private static final String REMINDER_NOTFOUND_WITH_DUEDATE = "Reminder não encontrada com dueDate = ";
    private static final String REMINDER_NOTFOUND_WITH_STATUS = "Reminder não encontrada com status = ";
    private static final String REMINDER_NOTFOUND_WITH_DATECREATED = "Reminder não encontrada com dateCreated = ";
    private static final String REMINDER_NOTFOUND_WITH_DATEUPDATED = "Reminder não encontrada com dateUpdated = ";


    @Autowired private ReminderRepository reminderRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando Reminder com id = {}", id);
        reminderRepository.findById(id)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_ID  + id));
        reminderRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO salvar(ReminderDTO reminderDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(reminderDTO.getId()) && reminderDTO.getId() != 0) {
            reminderDTO.setDateUpdated(now);
        } else {
            reminderDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            reminderDTO.setDateCreated(now);
            reminderDTO.setDateUpdated(now);
        }
        return this.toDTO(reminderRepository.save(this.toEntity(reminderDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findById(Long id) {
        Optional<Reminder> reminderData =
            Optional.ofNullable(reminderRepository.findById(id)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    REMINDER_NOTFOUND_WITH_ID  + id ))
                );

        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<Reminder> reminderData =
            Optional.ofNullable(reminderRepository.findById(id)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_ID  + id))
                    );
        if (reminderData.isPresent()) {
            Reminder reminder = reminderData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.IDLIST)) reminder.setIdList((Long)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.TITLE)) reminder.setTitle((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.NOTE)) reminder.setNote((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.TAGS)) reminder.setTags((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.FULLURLIMAGE)) reminder.setFullUrlImage((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.URL)) reminder.setUrl((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.PRIORITY)) reminder.setPriority((EnumPriority) entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.FLAG)) reminder.setFlag((Boolean) entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ReminderConstantes.DUEDATE)) reminder.setDueDate((Date)entry.getValue());

        }
        if(updates.get(ReminderConstantes.DATEUPDATED) == null) reminder.setDateUpdated(new Date());
        reminderRepository.save(reminder);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO updateStatusById(Long id, String status) {
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    REMINDER_NOTFOUND_WITH_ID + id))
                );
        Reminder reminder = reminderData.orElseGet(Reminder::new);
        reminder.setStatus(status);
        reminder.setDateUpdated(new Date());
        return toDTO(reminderRepository.save(reminder));

    }

    @Override
    public List<ReminderDTO> findAllByStatus(String status) {
        return reminderRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<Reminder> lstReminder;
    Long id = null;
    Long idList = null;
    String title = null;
    String note = null;
    String tags = null;
    String fullUrlImage = null;
    String url = null;
    String priority = null;
    String flag = null;
    String dueDate = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.IDLIST)) idList = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.NOTE)) note = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.TAGS)) tags = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.FULLURLIMAGE)) fullUrlImage = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.URL)) url = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.PRIORITY)) priority = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.FLAG)) flag = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.DUEDATE)) dueDate = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<Reminder> paginaReminder = reminderRepository.findReminderByFilter(paging,
        id
        ,idList
        ,title
        ,note
        ,tags
        ,fullUrlImage
        ,url
        ,priority
        ,flag
        ,dueDate
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstReminder = paginaReminder.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaReminder.getNumber());
    response.put("totalItems", paginaReminder.getTotalElements());
    response.put("totalPages", paginaReminder.getTotalPages());
    response.put("pageReminderItems", lstReminder.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
)
    public List<ReminderDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long idList = null;
    String title = null;
    String note = null;
    String tags = null;
    String fullUrlImage = null;
    String url = null;
    String priority = null;
    String flag = null;
    String dueDate = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.IDLIST)) idList = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.TITLE)) title = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.NOTE)) note = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.TAGS)) tags = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.FULLURLIMAGE)) fullUrlImage = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.URL)) url = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.PRIORITY)) priority = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.FLAG)) flag = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.DUEDATE)) dueDate = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ReminderConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<Reminder> lstReminder = reminderRepository.findReminderByFilter(
            id
            ,idList
            ,title
            ,note
            ,tags
            ,fullUrlImage
            ,url
            ,priority
            ,flag
            ,dueDate
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstReminder.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByIdAndStatus(Long id, String status) {
        return reminderRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByIdListAndStatus(Long idList, String status) {
        return reminderRepository.findAllByIdListAndStatus(idList, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByTitleAndStatus(String title, String status) {
        return reminderRepository.findAllByTitleAndStatus(title, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByNoteAndStatus(String note, String status) {
        return reminderRepository.findAllByNoteAndStatus(note, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByTagsAndStatus(String tags, String status) {
        return reminderRepository.findAllByTagsAndStatus(tags, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByFullUrlImageAndStatus(String fullUrlImage, String status) {
        return reminderRepository.findAllByFullUrlImageAndStatus(fullUrlImage, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByUrlAndStatus(String url, String status) {
        return reminderRepository.findAllByUrlAndStatus(url, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByPriorityAndStatus(String priority, String status) {
        return reminderRepository.findAllByPriorityAndStatus(priority, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByFlagAndStatus(String flag, String status) {
        return reminderRepository.findAllByFlagAndStatus(flag, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByDueDateAndStatus(Date dueDate, String status) {
        return reminderRepository.findAllByDueDateAndStatus(dueDate, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByDateCreatedAndStatus(Date dateCreated, String status) {
        return reminderRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public List<ReminderDTO> findAllReminderByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return reminderRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByIdAndStatus(Long id, String status) {
        Long maxId = reminderRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_ID + id))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByIdAndStatus(Long id) {
        return this.findReminderByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByIdListAndStatus(Long idList, String status) {
        Long maxId = reminderRepository.loadMaxIdByIdListAndStatus(idList, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_IDLIST + idList,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_IDLIST + idList))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByIdListAndStatus(Long idList) {
        return this.findReminderByIdListAndStatus(idList, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByTitleAndStatus(String title, String status) {
        Long maxId = reminderRepository.loadMaxIdByTitleAndStatus(title, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_TITLE + title,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_TITLE + title))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByTitleAndStatus(String title) {
        return this.findReminderByTitleAndStatus(title, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByNoteAndStatus(String note, String status) {
        Long maxId = reminderRepository.loadMaxIdByNoteAndStatus(note, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_NOTE + note,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_NOTE + note))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByNoteAndStatus(String note) {
        return this.findReminderByNoteAndStatus(note, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByTagsAndStatus(String tags, String status) {
        Long maxId = reminderRepository.loadMaxIdByTagsAndStatus(tags, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_TAGS + tags,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_TAGS + tags))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByTagsAndStatus(String tags) {
        return this.findReminderByTagsAndStatus(tags, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByFullUrlImageAndStatus(String fullUrlImage, String status) {
        Long maxId = reminderRepository.loadMaxIdByFullUrlImageAndStatus(fullUrlImage, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_FULLURLIMAGE + fullUrlImage,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_FULLURLIMAGE + fullUrlImage))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByFullUrlImageAndStatus(String fullUrlImage) {
        return this.findReminderByFullUrlImageAndStatus(fullUrlImage, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByUrlAndStatus(String url, String status) {
        Long maxId = reminderRepository.loadMaxIdByUrlAndStatus(url, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_URL + url,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_URL + url))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByUrlAndStatus(String url) {
        return this.findReminderByUrlAndStatus(url, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByPriorityAndStatus(String priority, String status) {
        Long maxId = reminderRepository.loadMaxIdByPriorityAndStatus(priority, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_PRIORITY + priority,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_PRIORITY + priority))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByPriorityAndStatus(String priority) {
        return this.findReminderByPriorityAndStatus(priority, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByFlagAndStatus(String flag, String status) {
        Long maxId = reminderRepository.loadMaxIdByFlagAndStatus(flag, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_FLAG + flag,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_FLAG + flag))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByFlagAndStatus(String flag) {
        return this.findReminderByFlagAndStatus(flag, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByDueDateAndStatus(Date dueDate, String status) {
        Long maxId = reminderRepository.loadMaxIdByDueDateAndStatus(dueDate, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_DUEDATE + dueDate,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_DUEDATE + dueDate))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByDueDateAndStatus(Date dueDate) {
        return this.findReminderByDueDateAndStatus(dueDate, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = reminderRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByDateCreatedAndStatus(Date dateCreated) {
        return this.findReminderByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = reminderRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<Reminder> reminderData =
            Optional.ofNullable( reminderRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ReminderNotFoundException(REMINDER_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        REMINDER_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return reminderData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ReminderNotFoundException.class
    )
    public ReminderDTO findReminderByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findReminderByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updateIdListById(Long id, Long idList) {
        findById(id);
        reminderRepository.updateIdListById(id, idList);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updateTitleById(Long id, String title) {
        findById(id);
        reminderRepository.updateTitleById(id, title);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updateNoteById(Long id, String note) {
        findById(id);
        reminderRepository.updateNoteById(id, note);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updateTagsById(Long id, String tags) {
        findById(id);
        reminderRepository.updateTagsById(id, tags);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updateFullUrlImageById(Long id, String fullUrlImage) {
        findById(id);
        reminderRepository.updateFullUrlImageById(id, fullUrlImage);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updateUrlById(Long id, String url) {
        findById(id);
        reminderRepository.updateUrlById(id, url);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updatePriorityById(Long id, String priority) {
        findById(id);
        reminderRepository.updatePriorityById(id, priority);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updateFlagById(Long id, String flag) {
        findById(id);
        reminderRepository.updateFlagById(id, flag);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ReminderDTO updateDueDateById(Long id, Date dueDate) {
        findById(id);
        reminderRepository.updateDueDateById(id, dueDate);
        return findById(id);
    }


    public ReminderDTO toDTO(Reminder reminder) {
        ReminderDTO reminderDTO = new ReminderDTO();
                reminderDTO.setId(reminder.getId());
                reminderDTO.setIdList(reminder.getIdList());
                reminderDTO.setTitle(reminder.getTitle());
                reminderDTO.setNote(reminder.getNote());
                reminderDTO.setTags(reminder.getTags());
                reminderDTO.setFullUrlImage(reminder.getFullUrlImage());
                reminderDTO.setUrl(reminder.getUrl());
                reminderDTO.setPriority(reminder.getPriority());
                reminderDTO.setFlag(reminder.getFlag());
                reminderDTO.setDueDate(reminder.getDueDate());
                reminderDTO.setStatus(reminder.getStatus());
                reminderDTO.setDateCreated(reminder.getDateCreated());
                reminderDTO.setDateUpdated(reminder.getDateUpdated());

        return reminderDTO;
    }

    public Reminder toEntity(ReminderDTO reminderDTO) {
        Reminder reminder = null;
        reminder = new Reminder();
                    reminder.setId(reminderDTO.getId());
                    reminder.setIdList(reminderDTO.getIdList());
                    reminder.setTitle(reminderDTO.getTitle());
                    reminder.setNote(reminderDTO.getNote());
                    reminder.setTags(reminderDTO.getTags());
                    reminder.setFullUrlImage(reminderDTO.getFullUrlImage());
                    reminder.setUrl(reminderDTO.getUrl());
                    reminder.setPriority(reminderDTO.getPriority());
                    reminder.setFlag(reminderDTO.getFlag());
                    reminder.setDueDate(reminderDTO.getDueDate());
                    reminder.setStatus(reminderDTO.getStatus());
                    reminder.setDateCreated(reminderDTO.getDateCreated());
                    reminder.setDateUpdated(reminderDTO.getDateUpdated());

        return reminder;
    }
}
