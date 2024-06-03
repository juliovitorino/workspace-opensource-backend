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


package br.com.jcv.brcities.corelayer.service;

import java.util.Date;
import java.util.List;

import br.com.jcv.brcities.corelayer.model.Uf;
import br.com.jcv.brcities.infrastructure.dto.UfDTO;
import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;

/**
* UfService - Interface for Uf
*
* @author Uf
* @since Mon Jun 03 16:53:27 BRT 2024
*/

public interface UfService extends CommoditieBaseService<UfDTO,Uf>
{
    UfDTO findUfByIdAndStatus(Long id);
    UfDTO findUfByIdAndStatus(Long id, String status);
    UfDTO findUfByNameAndStatus(String name);
    UfDTO findUfByNameAndStatus(String name, String status);
    UfDTO findUfByDateCreatedAndStatus(Date dateCreated);
    UfDTO findUfByDateCreatedAndStatus(Date dateCreated, String status);
    UfDTO findUfByDateUpdatedAndStatus(Date dateUpdated);
    UfDTO findUfByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<UfDTO> findAllUfByIdAndStatus(Long id, String status);
    List<UfDTO> findAllUfByNameAndStatus(String name, String status);
    List<UfDTO> findAllUfByDateCreatedAndStatus(Date dateCreated, String status);
    List<UfDTO> findAllUfByDateUpdatedAndStatus(Date dateUpdated, String status);

    UfDTO updateNameById(Long id, String name);
    UfDTO updateStatusById(Long id, String status);


}
