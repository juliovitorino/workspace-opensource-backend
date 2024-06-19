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

import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.brcities.infrastructure.dto.CityUfDTO;
import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;

/**
* CityUfService - Interface for CityUf
*
* @author CityUf
* @since Wed Jun 05 13:47:11 BRT 2024
*/

public interface CityUfService extends CommoditieBaseService<CityUfDTO,CityUf>
{
    CityUfDTO findCityUfByIdAndStatus(Long id);
    CityUfDTO findCityUfByIdAndStatus(Long id, String status);
    CityUfDTO findCityUfByCityIdAndStatus(Long cityId);
    CityUfDTO findCityUfByCityIdAndStatus(Long cityId, String status);
    CityUfDTO findCityUfByUfIdAndStatus(String ufId);
    CityUfDTO findCityUfByUfIdAndStatus(String ufId, String status);
    CityUfDTO findCityUfByDddAndStatus(String ddd);
    CityUfDTO findCityUfByDddAndStatus(String ddd, String status);
    CityUfDTO findCityUfByLatitudeAndStatus(Double latitude);
    CityUfDTO findCityUfByLatitudeAndStatus(Double latitude, String status);
    CityUfDTO findCityUfByLongitudeAndStatus(Double longitude);
    CityUfDTO findCityUfByLongitudeAndStatus(Double longitude, String status);
    CityUfDTO findCityUfByDateCreatedAndStatus(Date dateCreated);
    CityUfDTO findCityUfByDateCreatedAndStatus(Date dateCreated, String status);
    CityUfDTO findCityUfByDateUpdatedAndStatus(Date dateUpdated);
    CityUfDTO findCityUfByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<CityUfDTO> findAllCityUfByIdAndStatus(Long id, String status);
    List<CityUfDTO> findAllCityUfByCityIdAndStatus(Long cityId, String status);
    List<CityUfDTO> findAllCityUfByUfIdAndStatus(String ufId, String status);
    List<CityUfDTO> findAllCityUfByDddAndStatus(String ddd, String status);
    List<CityUfDTO> findAllCityUfByLatitudeAndStatus(Double latitude, String status);
    List<CityUfDTO> findAllCityUfByLongitudeAndStatus(Double longitude, String status);
    List<CityUfDTO> findAllCityUfByDateCreatedAndStatus(Date dateCreated, String status);
    List<CityUfDTO> findAllCityUfByDateUpdatedAndStatus(Date dateUpdated, String status);

    CityUfDTO updateCityIdById(Long id, Long cityId);
    CityUfDTO updateUfIdById(Long id, String ufId);
    CityUfDTO updateDddById(Long id, String ddd);
    CityUfDTO updateLatitudeById(Long id, Double latitude);
    CityUfDTO updateLongitudeById(Long id, Double longitude);
    CityUfDTO updateStatusById(Long id, String status);


}
