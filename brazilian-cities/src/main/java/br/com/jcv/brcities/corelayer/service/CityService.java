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

import br.com.jcv.brcities.corelayer.model.City;
import br.com.jcv.brcities.infrastructure.dto.CityDTO;
import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;

/**
* CityService - Interface for City
*
* @author City
* @since Mon Jun 03 17:37:14 BRT 2024
*/

public interface CityService extends CommoditieBaseService<CityDTO,City>
{
    CityDTO findCityByIdAndStatus(Long id);
    CityDTO findCityByIdAndStatus(Long id, String status);
    CityDTO findCityByNameAndStatus(String name);
    CityDTO findCityByNameAndStatus(String name, String status);
    CityDTO findCityByDateCreatedAndStatus(Date dateCreated);
    CityDTO findCityByDateCreatedAndStatus(Date dateCreated, String status);
    CityDTO findCityByDateUpdatedAndStatus(Date dateUpdated);
    CityDTO findCityByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<CityDTO> findAllCityByIdAndStatus(Long id, String status);
    List<CityDTO> findAllCityByNameAndStatus(String name, String status);
    List<CityDTO> findAllCityByDateCreatedAndStatus(Date dateCreated, String status);
    List<CityDTO> findAllCityByDateUpdatedAndStatus(Date dateUpdated, String status);

    CityDTO updateNameById(Long id, String name);
    CityDTO updateStatusById(Long id, String status);


}
