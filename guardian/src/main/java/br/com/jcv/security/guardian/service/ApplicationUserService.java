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


package br.com.jcv.security.guardian.service;

import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;

import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.model.ApplicationUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* ApplicationUserService - Interface for ApplicationUser
*
* @author ApplicationUser
* @since Thu Nov 16 09:03:29 BRT 2023
*/

public interface ApplicationUserService extends CommoditieBaseService<ApplicationUserDTO,ApplicationUser>
{
    ApplicationUserDTO findApplicationUserByIdAndStatus(Long id);
    ApplicationUserDTO findApplicationUserByIdAndStatus(Long id, String status);
    ApplicationUserDTO findApplicationUserByIdUserAndStatus(Long idUser);
    ApplicationUserDTO findApplicationUserByIdUserAndStatus(Long idUser, String status);
    ApplicationUserDTO findApplicationUserByEmailAndStatus(String email);
    ApplicationUserDTO findApplicationUserByEmailAndStatus(String email, String status);
    ApplicationUserDTO findApplicationUserByEncodedPassPhraseAndStatus(String encodedPassPhrase);
    ApplicationUserDTO findApplicationUserByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
    ApplicationUserDTO findApplicationUserByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID);
    ApplicationUserDTO findApplicationUserByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID, String status);
    ApplicationUserDTO findApplicationUserByUrlTokenActivationAndStatus(String urlTokenActivation);
    ApplicationUserDTO findApplicationUserByUrlTokenActivationAndStatus(String urlTokenActivation, String status);
    ApplicationUserDTO findApplicationUserByActivationCodeAndStatus(String activationCode);
    ApplicationUserDTO findApplicationUserByActivationCodeAndStatus(String activationCode, String status);
    ApplicationUserDTO findApplicationUserByDueDateActivationAndStatus(Date dueDateActivation);
    ApplicationUserDTO findApplicationUserByDueDateActivationAndStatus(Date dueDateActivation, String status);
    ApplicationUserDTO findApplicationUserByDateCreatedAndStatus(Date dateCreated);
    ApplicationUserDTO findApplicationUserByDateCreatedAndStatus(Date dateCreated, String status);
    ApplicationUserDTO findApplicationUserByDateUpdatedAndStatus(Date dateUpdated);
    ApplicationUserDTO findApplicationUserByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<ApplicationUserDTO> findAllApplicationUserByIdAndStatus(Long id, String status);
    List<ApplicationUserDTO> findAllApplicationUserByIdUserAndStatus(Long idUser, String status);
    List<ApplicationUserDTO> findAllApplicationUserByEmailAndStatus(String email, String status);
    List<ApplicationUserDTO> findAllApplicationUserByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
    List<ApplicationUserDTO> findAllApplicationUserByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID, String status);
    List<ApplicationUserDTO> findAllApplicationUserByUrlTokenActivationAndStatus(String urlTokenActivation, String status);
    List<ApplicationUserDTO> findAllApplicationUserByActivationCodeAndStatus(String activationCode, String status);
    List<ApplicationUserDTO> findAllApplicationUserByDueDateActivationAndStatus(Date dueDateActivation, String status);
    List<ApplicationUserDTO> findAllApplicationUserByDateCreatedAndStatus(Date dateCreated, String status);
    List<ApplicationUserDTO> findAllApplicationUserByDateUpdatedAndStatus(Date dateUpdated, String status);

    ApplicationUserDTO updateEmailById(Long id, String email);
    ApplicationUserDTO updateEncodedPassPhraseById(Long id, String encodedPassPhrase);
    ApplicationUserDTO updateExternalAppUserUUIDById(Long id, UUID externalAppUserUUID);
    ApplicationUserDTO updateUrlTokenActivationById(Long id, String urlTokenActivation);
    ApplicationUserDTO updateActivationCodeById(Long id, String activationCode);
    ApplicationUserDTO updateDueDateActivationById(Long id, Date dueDateActivation);
    ApplicationUserDTO updateStatusById(Long id, String status);


}
