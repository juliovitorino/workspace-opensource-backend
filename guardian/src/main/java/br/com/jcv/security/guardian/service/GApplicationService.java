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

import br.com.jcv.security.guardian.dto.GApplicationDTO;
import br.com.jcv.security.guardian.model.GApplication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
* GApplicationService - Interface for GApplication
*
* @author GApplication
* @since Wed Nov 15 11:12:12 BRT 2023
*/

public interface GApplicationService extends CommoditieBaseService<GApplicationDTO,GApplication>
{
    GApplicationDTO findGApplicationByIdAndStatus(Long id);
    GApplicationDTO findGApplicationByIdAndStatus(Long id, String status);
    GApplicationDTO findGApplicationByNameAndStatus(String name);
    GApplicationDTO findGApplicationByNameAndStatus(String name, String status);
    GApplicationDTO findGApplicationByExternalCodeUUIDAndStatus(UUID externalCodeUUID);
    GApplicationDTO findGApplicationByExternalCodeUUIDAndStatus(UUID externalCodeUUID, String status);
    GApplicationDTO findGApplicationByDateCreatedAndStatus(Date dateCreated);
    GApplicationDTO findGApplicationByDateCreatedAndStatus(Date dateCreated, String status);
    GApplicationDTO findGApplicationByDateUpdatedAndStatus(Date dateUpdated);
    GApplicationDTO findGApplicationByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<GApplicationDTO> findAllGApplicationByIdAndStatus(Long id, String status);
    List<GApplicationDTO> findAllGApplicationByNameAndStatus(String name, String status);
    List<GApplicationDTO> findAllGApplicationByExternalCodeUUIDAndStatus(UUID externalCodeUUID, String status);
    List<GApplicationDTO> findAllGApplicationByDateCreatedAndStatus(Date dateCreated, String status);
    List<GApplicationDTO> findAllGApplicationByDateUpdatedAndStatus(Date dateUpdated, String status);

    GApplicationDTO updateNameById(Long id, String name);
    GApplicationDTO updateExternalCodeUUIDById(Long id, UUID externalCodeUUID);
    GApplicationDTO updateStatusById(Long id, String status);


}
