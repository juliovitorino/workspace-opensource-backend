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
import br.com.jcv.reminder.corelayer.model.ListReminder;
import br.com.jcv.reminder.infrastructure.dto.ListReminderDTO;

/**
* ListReminderService - Interface for ListReminder
*
* @author ListReminder
* @since Sat May 11 14:30:00 BRT 2024
*/

public interface ListReminderService extends CommoditieBaseService<ListReminderDTO,ListReminder>
{
    ListReminderDTO findListReminderByIdAndStatus(Long id);
    ListReminderDTO findListReminderByIdAndStatus(Long id, String status);
    ListReminderDTO findListReminderByUuidExternalAppAndStatus(UUID uuidExternalApp);
    ListReminderDTO findListReminderByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    ListReminderDTO findListReminderByUuidExternalUserAndStatus(UUID uuidExternalUser);
    ListReminderDTO findListReminderByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    ListReminderDTO findListReminderByUuidExternalListAndStatus(UUID uuidExternalList);
    ListReminderDTO findListReminderByUuidExternalListAndStatus(UUID uuidExternalList, String status);
    ListReminderDTO findListReminderByTitleAndStatus(String title);
    ListReminderDTO findListReminderByTitleAndStatus(String title, String status);
    ListReminderDTO findListReminderByDateCreatedAndStatus(Date dateCreated);
    ListReminderDTO findListReminderByDateCreatedAndStatus(Date dateCreated, String status);
    ListReminderDTO findListReminderByDateUpdatedAndStatus(Date dateUpdated);
    ListReminderDTO findListReminderByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<ListReminderDTO> findAllListReminderByIdAndStatus(Long id, String status);
    List<ListReminderDTO> findAllListReminderByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    List<ListReminderDTO> findAllListReminderByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    List<ListReminderDTO> findAllListReminderByUuidExternalListAndStatus(UUID uuidExternalList, String status);
    List<ListReminderDTO> findAllListReminderByTitleAndStatus(String title, String status);
    List<ListReminderDTO> findAllListReminderByDateCreatedAndStatus(Date dateCreated, String status);
    List<ListReminderDTO> findAllListReminderByDateUpdatedAndStatus(Date dateUpdated, String status);

    ListReminderDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp);
    ListReminderDTO updateUuidExternalUserById(Long id, UUID uuidExternalUser);
    ListReminderDTO updateUuidExternalListById(Long id, UUID uuidExternalList);
    ListReminderDTO updateTitleById(Long id, String title);
    ListReminderDTO updateStatusById(Long id, String status);


}
