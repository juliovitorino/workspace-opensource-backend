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


package br.com.jcv.notifier.service;

import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;

import br.com.jcv.notifier.dto.NotifierDTO;
import br.com.jcv.notifier.model.Notifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* NotifierService - Interface for Notifier
*
* @author Notifier
* @since Sat Dec 16 12:29:21 BRT 2023
*/

public interface NotifierService extends CommoditieBaseService<NotifierDTO,Notifier>
{
    NotifierDTO findNotifierByIdAndStatus(Long id);
    NotifierDTO findNotifierByIdAndStatus(Long id, String status);
    NotifierDTO findNotifierByApplicationUUIDAndStatus(UUID applicationUUID);
    NotifierDTO findNotifierByApplicationUUIDAndStatus(UUID applicationUUID, String status);
    NotifierDTO findNotifierByUserUUIDAndStatus(UUID userUUID);
    NotifierDTO findNotifierByUserUUIDAndStatus(UUID userUUID, String status);
    NotifierDTO findNotifierByTypeAndStatus(String type);
    NotifierDTO findNotifierByTypeAndStatus(String type, String status);
    NotifierDTO findNotifierByTitleAndStatus(String title);
    NotifierDTO findNotifierByTitleAndStatus(String title, String status);
    NotifierDTO findNotifierByDescriptionAndStatus(String description);
    NotifierDTO findNotifierByDescriptionAndStatus(String description, String status);
    NotifierDTO findNotifierByIsReadedAndStatus(String isReaded);
    NotifierDTO findNotifierByIsReadedAndStatus(String isReaded, String status);
    NotifierDTO findNotifierByDateCreatedAndStatus(Date dateCreated);
    NotifierDTO findNotifierByDateCreatedAndStatus(Date dateCreated, String status);
    NotifierDTO findNotifierByDateUpdatedAndStatus(Date dateUpdated);
    NotifierDTO findNotifierByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<NotifierDTO> findAllNotifierByIdAndStatus(Long id, String status);
    List<NotifierDTO> findAllNotifierByApplicationUUIDAndStatus(UUID applicationUUID, String status);
    List<NotifierDTO> findAllNotifierByUserUUIDAndStatus(UUID userUUID, String status);
    List<NotifierDTO> findAllNotifierByTypeAndStatus(String type, String status);
    List<NotifierDTO> findAllNotifierByTitleAndStatus(String title, String status);
    List<NotifierDTO> findAllNotifierByDescriptionAndStatus(String description, String status);
    List<NotifierDTO> findAllNotifierByIsReadedAndStatus(String isReaded, String status);
    List<NotifierDTO> findAllNotifierByDateCreatedAndStatus(Date dateCreated, String status);
    List<NotifierDTO> findAllNotifierByDateUpdatedAndStatus(Date dateUpdated, String status);

    NotifierDTO updateApplicationUUIDById(Long id, UUID applicationUUID);
    NotifierDTO updateUserUUIDById(Long id, UUID userUUID);
    NotifierDTO updateTypeById(Long id, String type);
    NotifierDTO updateTitleById(Long id, String title);
    NotifierDTO updateDescriptionById(Long id, String description);
    NotifierDTO updateIsReadedById(Long id, String isReaded);
    NotifierDTO updateStatusById(Long id, String status);


}
