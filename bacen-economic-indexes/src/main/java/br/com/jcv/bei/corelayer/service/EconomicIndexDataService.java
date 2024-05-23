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

import br.com.jcv.bei.corelayer.model.EconomicIndexData;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDataDTO;
import br.com.jcv.commons.library.commodities.service.CommoditieBaseService;

/**
* EconomicIndexDataService - Interface for EconomicIndexData
*
* @author EconomicIndexData
* @since Thu May 23 17:29:29 BRT 2024
*/

public interface EconomicIndexDataService extends CommoditieBaseService<EconomicIndexDataDTO,EconomicIndexData>
{
    EconomicIndexDataDTO findEconomicIndexDataByIdAndStatus(Long id);
    EconomicIndexDataDTO findEconomicIndexDataByIdAndStatus(Long id, String status);
    EconomicIndexDataDTO findEconomicIndexDataByEconomicIndexIdAndStatus(Long economicIndexId);
    EconomicIndexDataDTO findEconomicIndexDataByEconomicIndexIdAndStatus(Long economicIndexId, String status);
    EconomicIndexDataDTO findEconomicIndexDataByIndexDateAndStatus(LocalDate indexDate);
    EconomicIndexDataDTO findEconomicIndexDataByIndexDateAndStatus(LocalDate indexDate, String status);
    EconomicIndexDataDTO findEconomicIndexDataByIndexValueAndStatus(Double indexValue);
    EconomicIndexDataDTO findEconomicIndexDataByIndexValueAndStatus(Double indexValue, String status);
    EconomicIndexDataDTO findEconomicIndexDataByDateCreatedAndStatus(Date dateCreated);
    EconomicIndexDataDTO findEconomicIndexDataByDateCreatedAndStatus(Date dateCreated, String status);
    EconomicIndexDataDTO findEconomicIndexDataByDateUpdatedAndStatus(Date dateUpdated);
    EconomicIndexDataDTO findEconomicIndexDataByDateUpdatedAndStatus(Date dateUpdated, String status);

    List<EconomicIndexDataDTO> findAllEconomicIndexDataByIdAndStatus(Long id, String status);
    List<EconomicIndexDataDTO> findAllEconomicIndexDataByEconomicIndexIdAndStatus(Long economicIndexId, String status);
    List<EconomicIndexDataDTO> findAllEconomicIndexDataByIndexDateAndStatus(LocalDate indexDate, String status);
    List<EconomicIndexDataDTO> findAllEconomicIndexDataByIndexValueAndStatus(Double indexValue, String status);
    List<EconomicIndexDataDTO> findAllEconomicIndexDataByDateCreatedAndStatus(Date dateCreated, String status);
    List<EconomicIndexDataDTO> findAllEconomicIndexDataByDateUpdatedAndStatus(Date dateUpdated, String status);

    EconomicIndexDataDTO updateEconomicIndexIdById(Long id, Long economicIndexId);
    EconomicIndexDataDTO updateIndexDateById(Long id, LocalDate indexDate);
    EconomicIndexDataDTO updateIndexValueById(Long id, Double indexValue);
    EconomicIndexDataDTO updateStatusById(Long id, String status);


}
