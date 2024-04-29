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
import br.com.jcv.preferences.corelayer.model.SystemPreferences;
import br.com.jcv.preferences.infrastructure.dto.SystemPreferencesDTO;

/**
* SystemPreferencesService - Interface for SystemPreferences
*
* @author SystemPreferences
* @since Mon Apr 29 15:32:50 BRT 2024
*/

public interface SystemPreferencesService extends CommoditieBaseService<SystemPreferencesDTO,SystemPreferences>
{
    SystemPreferencesDTO findSystemPreferencesByIdAndStatus(Long id);
    SystemPreferencesDTO findSystemPreferencesByIdAndStatus(Long id, String status);
    SystemPreferencesDTO findSystemPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp);
    SystemPreferencesDTO findSystemPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    SystemPreferencesDTO findSystemPreferencesByKeyAndStatus(String key);
    SystemPreferencesDTO findSystemPreferencesByKeyAndStatus(String key, String status);
    SystemPreferencesDTO findSystemPreferencesByPreferenceAndStatus(String preference);
    SystemPreferencesDTO findSystemPreferencesByPreferenceAndStatus(String preference, String status);
    SystemPreferencesDTO findSystemPreferencesByDateCreatedAndStatus(Date dateCreated);
    SystemPreferencesDTO findSystemPreferencesByDateCreatedAndStatus(Date dateCreated, String status);
    SystemPreferencesDTO findSystemPreferencesByDateUpdatedAndStatus(Date dateUpdated);
    SystemPreferencesDTO findSystemPreferencesByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<SystemPreferencesDTO> findAllSystemPreferencesByIdAndStatus(Long id, String status);
    List<SystemPreferencesDTO> findAllSystemPreferencesByUuidExternalAppAndStatus(UUID uuidExternalApp, String status);
    List<SystemPreferencesDTO> findAllSystemPreferencesByKeyAndStatus(String key, String status);
    List<SystemPreferencesDTO> findAllSystemPreferencesByPreferenceAndStatus(String preference, String status);
    List<SystemPreferencesDTO> findAllSystemPreferencesByDateCreatedAndStatus(Date dateCreated, String status);
    List<SystemPreferencesDTO> findAllSystemPreferencesByDateUpdatedAndStatus(Date dateUpdated, String status);

    SystemPreferencesDTO updateUuidExternalAppById(Long id, UUID uuidExternalApp);
    SystemPreferencesDTO updateKeyById(Long id, String key);
    SystemPreferencesDTO updatePreferenceById(Long id, String preference);
    SystemPreferencesDTO updateStatusById(Long id, String status);


}
