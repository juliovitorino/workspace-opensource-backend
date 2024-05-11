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
import java.util.UUID;

import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;
import br.com.jcv.reminder.corelayer.model.ReminderList;
import br.com.jcv.reminder.infrastructure.dto.ReminderListDTO;

/**
* ReminderListService - Interface for ReminderList
*
* @author ReminderList
* @since Sat May 11 17:44:44 BRT 2024
*/

public interface ReminderListService extends CommoditieBaseService<ReminderListDTO,ReminderList>
{
    ReminderListDTO findReminderListByIdAndStatus(Long id);
    ReminderListDTO findReminderListByIdAndStatus(Long id, String status);
    ReminderListDTO findReminderListByUuidExternalAppAndStatus(UUID uuidExternalApp);
    ReminderListDTO findReminderListByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    ReminderListDTO findReminderListByUuidExternalUserAndStatus(UUID uuidExternalUser);
    ReminderListDTO findReminderListByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    ReminderListDTO findReminderListByUuidExternalListAndStatus(UUID uuidExternalList);
    ReminderListDTO findReminderListByUuidExternalListAndStatus(UUID uuidExternalList, String status);
    ReminderListDTO findReminderListByTitleAndStatus(String title);
    ReminderListDTO findReminderListByTitleAndStatus(String title, String status);
    ReminderListDTO findReminderListByDateCreatedAndStatus(Date dateCreated);
    ReminderListDTO findReminderListByDateCreatedAndStatus(Date dateCreated, String status);
    ReminderListDTO findReminderListByDateUpdatedAndStatus(Date dateUpdated);
    ReminderListDTO findReminderListByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<ReminderListDTO> findAllReminderListByIdAndStatus(Long id, String status);
    List<ReminderListDTO> findAllReminderListByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    List<ReminderListDTO> findAllReminderListByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    List<ReminderListDTO> findAllReminderListByUuidExternalListAndStatus(UUID uuidExternalList, String status);
    List<ReminderListDTO> findAllReminderListByTitleAndStatus(String title, String status);
    List<ReminderListDTO> findAllReminderListByDateCreatedAndStatus(Date dateCreated, String status);
    List<ReminderListDTO> findAllReminderListByDateUpdatedAndStatus(Date dateUpdated, String status);

    ReminderListDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp);
    ReminderListDTO updateUuidExternalUserById(Long id, UUID uuidExternalUser);
    ReminderListDTO updateUuidExternalListById(Long id, UUID uuidExternalList);
    ReminderListDTO updateTitleById(Long id, String title);
    ReminderListDTO updateStatusById(Long id, String status);


}
