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

import br.com.jcv.security.guardian.dto.UsersDTO;
import br.com.jcv.security.guardian.model.Users;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* UsersService - Interface for Users
*
* @author Users
* @since Tue Nov 14 19:09:15 BRT 2023
*/

public interface UsersService extends CommoditieBaseService<UsersDTO,Users>
{
    UsersDTO findUsersByIdAndStatus(Long id);
    UsersDTO findUsersByIdAndStatus(Long id, String status);
    UsersDTO findUsersByNameAndStatus(String name);
    UsersDTO findUsersByNameAndStatus(String name, String status);
    UsersDTO findUsersByEmailAndStatus(String email);
    UsersDTO findUsersByEmailAndStatus(String email, String status);
    UsersDTO findUsersByEncodedPassPhraseAndStatus(String encodedPassPhrase);
    UsersDTO findUsersByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
    UsersDTO findUsersByIdUserUUIDAndStatus(UUID idUserUUID);
    UsersDTO findUsersByIdUserUUIDAndStatus(UUID idUserUUID, String status);
    UsersDTO findUsersByBirthdayAndStatus(LocalDate birthday);
    UsersDTO findUsersByBirthdayAndStatus(LocalDate birthday, String status);
    UsersDTO findUsersByDateCreatedAndStatus(Date dateCreated);
    UsersDTO findUsersByDateCreatedAndStatus(Date dateCreated, String status);
    UsersDTO findUsersByDateUpdatedAndStatus(Date dateUpdated);
    UsersDTO findUsersByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<UsersDTO> findAllUsersByIdAndStatus(Long id, String status);
    List<UsersDTO> findAllUsersByNameAndStatus(String name, String status);
    List<UsersDTO> findAllUsersByEmailAndStatus(String email, String status);
    List<UsersDTO> findAllUsersByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status);
    List<UsersDTO> findAllUsersByIdUserUUIDAndStatus(UUID idUserUUID, String status);
    List<UsersDTO> findAllUsersByBirthdayAndStatus(LocalDate birthday, String status);
    List<UsersDTO> findAllUsersByDateCreatedAndStatus(Date dateCreated, String status);
    List<UsersDTO> findAllUsersByDateUpdatedAndStatus(Date dateUpdated, String status);

    UsersDTO updateNameById(Long id, String name);
    UsersDTO updateEmailById(Long id, String email);
    UsersDTO updateEncodedPassPhraseById(Long id, String encodedPassPhrase);
    UsersDTO updateIdUserUUIDById(Long id, UUID idUserUUID);
    UsersDTO updateBirthdayById(Long id, LocalDate birthday);
    UsersDTO updateStatusById(Long id, String status);


}
