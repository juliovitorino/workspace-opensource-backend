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


package br.com.jcv.preferences.corelayer.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;
import br.com.jcv.preferences.corelayer.model.UserPreferences;
import br.com.jcv.preferences.infrastructure.dto.UserPreferencesDTO;

/**
* UserPreferencesService - Interface for UserPreferences
*
* @author UserPreferences
* @since Mon Apr 29 16:40:18 BRT 2024
*/

public interface UserPreferencesService extends CommoditieBaseService<UserPreferencesDTO,UserPreferences>
{
    UserPreferencesDTO findUserPreferencesByIdAndStatus(Long id);
    UserPreferencesDTO findUserPreferencesByIdAndStatus(Long id, String status);
    UserPreferencesDTO findUserPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp);
    UserPreferencesDTO findUserPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    UserPreferencesDTO findUserPreferencesByUuidExternalUserAndStatus(UUID uuidExternalUser);
    UserPreferencesDTO findUserPreferencesByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    UserPreferencesDTO findUserPreferencesByKeyAndStatus(String key);
    UserPreferencesDTO findUserPreferencesByKeyAndStatus(String key, String status);
    UserPreferencesDTO findUserPreferencesByPreferenceAndStatus(String preference);
    UserPreferencesDTO findUserPreferencesByPreferenceAndStatus(String preference, String status);
    UserPreferencesDTO findUserPreferencesByDateCreatedAndStatus(Date dateCreated);
    UserPreferencesDTO findUserPreferencesByDateCreatedAndStatus(Date dateCreated, String status);
    UserPreferencesDTO findUserPreferencesByDateUpdatedAndStatus(Date dateUpdated);
    UserPreferencesDTO findUserPreferencesByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<UserPreferencesDTO> findAllUserPreferencesByIdAndStatus(Long id, String status);
    List<UserPreferencesDTO> findAllUserPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    List<UserPreferencesDTO> findAllUserPreferencesByUuidExternalUserAndStatus(UUID uuidExternalUser, String status);
    List<UserPreferencesDTO> findAllUserPreferencesByKeyAndStatus(String key, String status);
    List<UserPreferencesDTO> findAllUserPreferencesByPreferenceAndStatus(String preference, String status);
    List<UserPreferencesDTO> findAllUserPreferencesByDateCreatedAndStatus(Date dateCreated, String status);
    List<UserPreferencesDTO> findAllUserPreferencesByDateUpdatedAndStatus(Date dateUpdated, String status);

    UserPreferencesDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp);
    UserPreferencesDTO updateUuidExternalUserById(Long id, UUID uuidExternalUser);
    UserPreferencesDTO updateKeyById(Long id, String key);
    UserPreferencesDTO updatePreferenceById(Long id, String preference);
    UserPreferencesDTO updateStatusById(Long id, String status);


}
