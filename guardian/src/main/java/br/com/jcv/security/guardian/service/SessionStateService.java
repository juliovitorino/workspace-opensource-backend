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

import br.com.jcv.security.guardian.dto.SessionStateDTO;
import br.com.jcv.security.guardian.model.SessionState;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* SessionStateService - Interface for SessionState
*
* @author SessionState
* @since Sun Oct 29 15:32:37 BRT 2023
*/

public interface SessionStateService extends CommoditieBaseService<SessionStateDTO,SessionState>
{
    SessionStateDTO findSessionStateByIdAndStatus(Long id);
    SessionStateDTO findSessionStateByIdAndStatus(Long id, String status);
    SessionStateDTO findSessionStateByIdTokenAndStatus(UUID idToken);
    SessionStateDTO findSessionStateByIdTokenAndStatus(UUID idToken, String status);
    SessionStateDTO findSessionStateByIdUserUUIDAndStatus(UUID idUserUUID);
    SessionStateDTO findSessionStateByIdUserUUIDAndStatus(UUID idUserUUID, String status);
    SessionStateDTO findSessionStateByDateCreatedAndStatus(Date dateCreated);
    SessionStateDTO findSessionStateByDateCreatedAndStatus(Date dateCreated, String status);
    SessionStateDTO findSessionStateByDateUpdatedAndStatus(Date dateUpdated);
    SessionStateDTO findSessionStateByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<SessionStateDTO> findAllSessionStateByIdAndStatus(Long id, String status);
    List<SessionStateDTO> findAllSessionStateByIdTokenAndStatus(UUID idToken, String status);
    List<SessionStateDTO> findAllSessionStateByIdUserUUIDAndStatus(UUID idUserUUID, String status);
    List<SessionStateDTO> findAllSessionStateByDateCreatedAndStatus(Date dateCreated, String status);
    List<SessionStateDTO> findAllSessionStateByDateUpdatedAndStatus(Date dateUpdated, String status);

    SessionStateDTO updateIdTokenById(Long id, UUID idToken);
    SessionStateDTO updateIdUserUUIDById(Long id, UUID idUserUUID);
    SessionStateDTO updateStatusById(Long id, String status);


}
