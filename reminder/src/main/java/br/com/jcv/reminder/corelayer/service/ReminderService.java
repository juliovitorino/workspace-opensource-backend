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


package br.com.jcv.reminder.corelayer.service;

import java.util.Date;
import java.util.List;

import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;
import br.com.jcv.reminder.corelayer.model.Reminder;
import br.com.jcv.reminder.infrastructure.dto.ReminderDTO;

/**
* ReminderService - Interface for Reminder
*
* @author Reminder
* @since Sat May 11 18:10:51 BRT 2024
*/

public interface ReminderService extends CommoditieBaseService<ReminderDTO,Reminder>
{
    ReminderDTO findReminderByIdAndStatus(Long id);
    ReminderDTO findReminderByIdAndStatus(Long id, String status);
    ReminderDTO findReminderByIdListAndStatus(Long idList);
    ReminderDTO findReminderByIdListAndStatus(Long idList, String status);
    ReminderDTO findReminderByTitleAndStatus(String title);
    ReminderDTO findReminderByTitleAndStatus(String title, String status);
    ReminderDTO findReminderByNoteAndStatus(String note);
    ReminderDTO findReminderByNoteAndStatus(String note, String status);
    ReminderDTO findReminderByTagsAndStatus(String tags);
    ReminderDTO findReminderByTagsAndStatus(String tags, String status);
    ReminderDTO findReminderByFullUrlImageAndStatus(String fullUrlImage);
    ReminderDTO findReminderByFullUrlImageAndStatus(String fullUrlImage, String status);
    ReminderDTO findReminderByUrlAndStatus(String url);
    ReminderDTO findReminderByUrlAndStatus(String url, String status);
    ReminderDTO findReminderByPriorityAndStatus(String priority);
    ReminderDTO findReminderByPriorityAndStatus(String priority, String status);
    ReminderDTO findReminderByFlagAndStatus(String flag);
    ReminderDTO findReminderByFlagAndStatus(String flag, String status);
    ReminderDTO findReminderByDueDateAndStatus(Date dueDate);
    ReminderDTO findReminderByDueDateAndStatus(Date dueDate, String status);
    ReminderDTO findReminderByDateCreatedAndStatus(Date dateCreated);
    ReminderDTO findReminderByDateCreatedAndStatus(Date dateCreated, String status);
    ReminderDTO findReminderByDateUpdatedAndStatus(Date dateUpdated);
    ReminderDTO findReminderByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<ReminderDTO> findAllReminderByIdAndStatus(Long id, String status);
    List<ReminderDTO> findAllReminderByIdListAndStatus(Long idList, String status);
    List<ReminderDTO> findAllReminderByTitleAndStatus(String title, String status);
    List<ReminderDTO> findAllReminderByNoteAndStatus(String note, String status);
    List<ReminderDTO> findAllReminderByTagsAndStatus(String tags, String status);
    List<ReminderDTO> findAllReminderByFullUrlImageAndStatus(String fullUrlImage, String status);
    List<ReminderDTO> findAllReminderByUrlAndStatus(String url, String status);
    List<ReminderDTO> findAllReminderByPriorityAndStatus(String priority, String status);
    List<ReminderDTO> findAllReminderByFlagAndStatus(String flag, String status);
    List<ReminderDTO> findAllReminderByDueDateAndStatus(Date dueDate, String status);
    List<ReminderDTO> findAllReminderByDateCreatedAndStatus(Date dateCreated, String status);
    List<ReminderDTO> findAllReminderByDateUpdatedAndStatus(Date dateUpdated, String status);

    ReminderDTO updateIdListById(Long id, Long idList);
    ReminderDTO updateTitleById(Long id, String title);
    ReminderDTO updateNoteById(Long id, String note);
    ReminderDTO updateTagsById(Long id, String tags);
    ReminderDTO updateFullUrlImageById(Long id, String fullUrlImage);
    ReminderDTO updateUrlById(Long id, String url);
    ReminderDTO updatePriorityById(Long id, String priority);
    ReminderDTO updateFlagById(Long id, String flag);
    ReminderDTO updateDueDateById(Long id, Date dueDate);
    ReminderDTO updateStatusById(Long id, String status);


}
