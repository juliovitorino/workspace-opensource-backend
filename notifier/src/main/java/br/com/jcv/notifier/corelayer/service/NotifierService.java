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


package br.com.jcv.notifier.corelayer.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;
import br.com.jcv.notifier.corelayer.model.Notifier;
import br.com.jcv.notifier.infrastructure.dto.NotifierDTO;

/**
* NotifierService - Interface for Notifier
*
* @author Notifier
* @since Mon May 06 08:02:37 BRT 2024
*/

public interface NotifierService extends CommoditieBaseService<NotifierDTO,Notifier>
{
    NotifierDTO findNotifierByIdAndStatus(Long id);
    NotifierDTO findNotifierByIdAndStatus(Long id, String status);
    NotifierDTO findNotifierByUuidExternalAppAndStatus(UUID uuidExternalApp);
    NotifierDTO findNotifierByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    NotifierDTO findNotifierByUuidExternalUserAndStatus(UUID uuidExternalUser);
    NotifierDTO findNotifierByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    NotifierDTO findNotifierByTypeAndStatus(String type);
    NotifierDTO findNotifierByTypeAndStatus(String type, String status);
    NotifierDTO findNotifierByKeyAndStatus(String key);
    NotifierDTO findNotifierByKeyAndStatus(String key, String status);
    NotifierDTO findNotifierByTitleAndStatus(String title);
    NotifierDTO findNotifierByTitleAndStatus(String title, String status);
    NotifierDTO findNotifierByDescriptionAndStatus(String description);
    NotifierDTO findNotifierByDescriptionAndStatus(String description, String status);
    NotifierDTO findNotifierByUrlImageAndStatus(String urlImage);
    NotifierDTO findNotifierByUrlImageAndStatus(String urlImage, String status);
    NotifierDTO findNotifierByIconClassAndStatus(String iconClass);
    NotifierDTO findNotifierByIconClassAndStatus(String iconClass, String status);
    NotifierDTO findNotifierByUrlFollowAndStatus(String urlFollow);
    NotifierDTO findNotifierByUrlFollowAndStatus(String urlFollow, String status);
    NotifierDTO findNotifierByObjectFreeAndStatus(String objectFree);
    NotifierDTO findNotifierByObjectFreeAndStatus(String objectFree, String status);
    NotifierDTO findNotifierBySeenIndicatorAndStatus(String seenIndicator);
    NotifierDTO findNotifierBySeenIndicatorAndStatus(String seenIndicator, String status);
    NotifierDTO findNotifierByDateCreatedAndStatus(Date dateCreated);
    NotifierDTO findNotifierByDateCreatedAndStatus(Date dateCreated, String status);
    NotifierDTO findNotifierByDateUpdatedAndStatus(Date dateUpdated);
    NotifierDTO findNotifierByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<NotifierDTO> findAllNotifierByIdAndStatus(Long id, String status);
    List<NotifierDTO> findAllNotifierByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    List<NotifierDTO> findAllNotifierByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    List<NotifierDTO> findAllNotifierByTypeAndStatus(String type, String status);
    List<NotifierDTO> findAllNotifierByKeyAndStatus(String key, String status);
    List<NotifierDTO> findAllNotifierByTitleAndStatus(String title, String status);
    List<NotifierDTO> findAllNotifierByDescriptionAndStatus(String description, String status);
    List<NotifierDTO> findAllNotifierByUrlImageAndStatus(String urlImage, String status);
    List<NotifierDTO> findAllNotifierByIconClassAndStatus(String iconClass, String status);
    List<NotifierDTO> findAllNotifierByUrlFollowAndStatus(String urlFollow, String status);
    List<NotifierDTO> findAllNotifierByObjectFreeAndStatus(String objectFree, String status);
    List<NotifierDTO> findAllNotifierBySeenIndicatorAndStatus(String seenIndicator, String status);
    List<NotifierDTO> findAllNotifierByDateCreatedAndStatus(Date dateCreated, String status);
    List<NotifierDTO> findAllNotifierByDateUpdatedAndStatus(Date dateUpdated, String status);

    NotifierDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp);
    NotifierDTO updateUuidExternalUserById(Long id, UUID uuidExternalUser);
    NotifierDTO updateTypeById(Long id, String type);
    NotifierDTO updateKeyById(Long id, String key);
    NotifierDTO updateTitleById(Long id, String title);
    NotifierDTO updateDescriptionById(Long id, String description);
    NotifierDTO updateUrlImageById(Long id, String urlImage);
    NotifierDTO updateIconClassById(Long id, String iconClass);
    NotifierDTO updateUrlFollowById(Long id, String urlFollow);
    NotifierDTO updateObjectFreeById(Long id, String objectFree);
    NotifierDTO updateSeenIndicatorById(Long id, String seenIndicator);
    NotifierDTO updateStatusById(Long id, String status);


}
