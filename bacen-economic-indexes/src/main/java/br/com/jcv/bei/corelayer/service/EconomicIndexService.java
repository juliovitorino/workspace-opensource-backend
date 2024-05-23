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


package br.com.jcv.bei.corelayer.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import br.com.jcv.bei.corelayer.model.EconomicIndex;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDTO;
import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;

/**
* EconomicIndexService - Interface for EconomicIndex
*
* @author EconomicIndex
* @since Thu May 23 16:41:19 BRT 2024
*/

public interface EconomicIndexService extends CommoditieBaseService<EconomicIndexDTO,EconomicIndex>
{
    EconomicIndexDTO findEconomicIndexByIdAndStatus(Long id);
    EconomicIndexDTO findEconomicIndexByIdAndStatus(Long id, String status);
    EconomicIndexDTO findEconomicIndexByEconomicIndexAndStatus(String economicIndex);
    EconomicIndexDTO findEconomicIndexByEconomicIndexAndStatus(String economicIndex, String status);
    EconomicIndexDTO findEconomicIndexByBacenSerieCodeAndStatus(String bacenSerieCode);
    EconomicIndexDTO findEconomicIndexByBacenSerieCodeAndStatus(String bacenSerieCode, String status);
    EconomicIndexDTO findEconomicIndexByLastDateValueAndStatus(LocalDate lastDateValue);
    EconomicIndexDTO findEconomicIndexByLastDateValueAndStatus(LocalDate lastDateValue, String status);
    EconomicIndexDTO findEconomicIndexByDateCreatedAndStatus(Date dateCreated);
    EconomicIndexDTO findEconomicIndexByDateCreatedAndStatus(Date dateCreated, String status);
    EconomicIndexDTO findEconomicIndexByDateUpdatedAndStatus(Date dateUpdated);
    EconomicIndexDTO findEconomicIndexByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<EconomicIndexDTO> findAllEconomicIndexByIdAndStatus(Long id, String status);
    List<EconomicIndexDTO> findAllEconomicIndexByEconomicIndexAndStatus(String economicIndex, String status);
    List<EconomicIndexDTO> findAllEconomicIndexByBacenSerieCodeAndStatus(String bacenSerieCode, String status);
    List<EconomicIndexDTO> findAllEconomicIndexByLastDateValueAndStatus(LocalDate lastDateValue, String status);
    List<EconomicIndexDTO> findAllEconomicIndexByDateCreatedAndStatus(Date dateCreated, String status);
    List<EconomicIndexDTO> findAllEconomicIndexByDateUpdatedAndStatus(Date dateUpdated, String status);

    EconomicIndexDTO updateEconomicIndexById(Long id, String economicIndex);
    EconomicIndexDTO updateBacenSerieCodeById(Long id, String bacenSerieCode);
    EconomicIndexDTO updateLastDateValueById(Long id, LocalDate lastDateValue);
    EconomicIndexDTO updateStatusById(Long id, String status);


}
