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


package br.com.jcv.bei.corelayer.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.jcv.bei.corelayer.model.EconomicIndexData;
import br.com.jcv.bei.corelayer.repository.EconomicIndexDataRepository;
import br.com.jcv.bei.corelayer.service.EconomicIndexDataService;
import br.com.jcv.bei.infrastructure.constantes.EconomicIndexDataConstantes;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDataDTO;
import br.com.jcv.bei.infrastructure.exception.EconomicIndexDataNotFoundException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateTime;
import lombok.extern.slf4j.Slf4j;


/**
* EconomicIndexDataServiceImpl - Implementation for EconomicIndexData interface
*
* @author EconomicIndexData
* @since Thu May 23 17:29:29 BRT 2024
*/


@Slf4j
@Service
public class EconomicIndexDataServiceImpl implements EconomicIndexDataService
{
    private static final String ECONOMICINDEXDATA_NOTFOUND_WITH_ID = "EconomicIndexData não encontrada com id = ";
    private static final String ECONOMICINDEXDATA_NOTFOUND_WITH_ECONOMICINDEXID = "EconomicIndexData não encontrada com economicIndexId = ";
    private static final String ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXDATE = "EconomicIndexData não encontrada com indexDate = ";
    private static final String ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXVALUE = "EconomicIndexData não encontrada com indexValue = ";
    private static final String ECONOMICINDEXDATA_NOTFOUND_WITH_STATUS = "EconomicIndexData não encontrada com status = ";
    private static final String ECONOMICINDEXDATA_NOTFOUND_WITH_DATECREATED = "EconomicIndexData não encontrada com dateCreated = ";
    private static final String ECONOMICINDEXDATA_NOTFOUND_WITH_DATEUPDATED = "EconomicIndexData não encontrada com dateUpdated = ";


    @Autowired private EconomicIndexDataRepository economicindexdataRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando EconomicIndexData com id = {}", id);
        economicindexdataRepository.findById(id)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEXDATA_NOTFOUND_WITH_ID  + id));
        economicindexdataRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO salvar(EconomicIndexDataDTO economicindexdataDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(economicindexdataDTO.getId()) && economicindexdataDTO.getId() != 0) {
            economicindexdataDTO.setDateUpdated(now);
        } else {
            economicindexdataDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            economicindexdataDTO.setDateCreated(now);
            economicindexdataDTO.setDateUpdated(now);
        }
        return this.toDTO(economicindexdataRepository.save(this.toEntity(economicindexdataDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findById(Long id) {
        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable(economicindexdataRepository.findById(id)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    ECONOMICINDEXDATA_NOTFOUND_WITH_ID  + id ))
                );

        return economicindexdataData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable(economicindexdataRepository.findById(id)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEXDATA_NOTFOUND_WITH_ID  + id))
                    );
        if (economicindexdataData.isPresent()) {
            EconomicIndexData economicindexdata = economicindexdataData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.ECONOMICINDEXID)) economicindexdata.setEconomicIndexId((Long)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.INDEXDATE)) economicindexdata.setIndexDate((LocalDate)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.INDEXVALUE)) economicindexdata.setIndexValue((Double)entry.getValue());

        }
        if(updates.get(EconomicIndexDataConstantes.DATEUPDATED) == null) economicindexdata.setDateUpdated(new Date());
        economicindexdataRepository.save(economicindexdata);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO updateStatusById(Long id, String status) {
        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable( economicindexdataRepository.findById(id)
                .orElseThrow(() -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    ECONOMICINDEXDATA_NOTFOUND_WITH_ID + id))
                );
        EconomicIndexData economicindexdata = economicindexdataData.orElseGet(EconomicIndexData::new);
        economicindexdata.setStatus(status);
        economicindexdata.setDateUpdated(new Date());
        return toDTO(economicindexdataRepository.save(economicindexdata));

    }

    @Override
    public List<EconomicIndexDataDTO> findAllByStatus(String status) {
        return economicindexdataRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<EconomicIndexData> lstEconomicIndexData;
    Long id = null;
    Long economicIndexId = null;
    String indexDate = null;
    Double indexValue = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.ECONOMICINDEXID)) economicIndexId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.INDEXDATE)) indexDate = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.INDEXVALUE)) indexValue = Objects.isNull(entry.getValue()) ? null : Double.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<EconomicIndexData> paginaEconomicIndexData = economicindexdataRepository.findEconomicIndexDataByFilter(paging,
        id
        ,economicIndexId
        ,indexDate
        ,indexValue
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstEconomicIndexData = paginaEconomicIndexData.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaEconomicIndexData.getNumber());
    response.put("totalItems", paginaEconomicIndexData.getTotalElements());
    response.put("totalPages", paginaEconomicIndexData.getTotalPages());
    response.put("pageEconomicIndexDataItems", lstEconomicIndexData.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
)
    public List<EconomicIndexDataDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long economicIndexId = null;
    String indexDate = null;
    Double indexValue = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.ECONOMICINDEXID)) economicIndexId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.INDEXDATE)) indexDate = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.INDEXVALUE)) indexValue = Objects.isNull(entry.getValue()) ? null : Double.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexDataConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<EconomicIndexData> lstEconomicIndexData = economicindexdataRepository.findEconomicIndexDataByFilter(
            id
            ,economicIndexId
            ,indexDate
            ,indexValue
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstEconomicIndexData.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public List<EconomicIndexDataDTO> findAllEconomicIndexDataByIdAndStatus(Long id, String status) {
        return economicindexdataRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public List<EconomicIndexDataDTO> findAllEconomicIndexDataByEconomicIndexIdAndStatus(Long economicIndexId, String status) {
        return economicindexdataRepository.findAllByEconomicIndexIdAndStatus(economicIndexId, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public List<EconomicIndexDataDTO> findAllEconomicIndexDataByIndexDateAndStatus(LocalDate indexDate, String status) {
        return economicindexdataRepository.findAllByIndexDateAndStatus(indexDate, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public List<EconomicIndexDataDTO> findAllEconomicIndexDataByIndexValueAndStatus(Double indexValue, String status) {
        return economicindexdataRepository.findAllByIndexValueAndStatus(indexValue, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public List<EconomicIndexDataDTO> findAllEconomicIndexDataByDateCreatedAndStatus(Date dateCreated, String status) {
        return economicindexdataRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public List<EconomicIndexDataDTO> findAllEconomicIndexDataByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return economicindexdataRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByIdAndStatus(Long id, String status) {
        Long maxId = economicindexdataRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable( economicindexdataRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEXDATA_NOTFOUND_WITH_ID + id))
                );
        return economicindexdataData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByIdAndStatus(Long id) {
        return this.findEconomicIndexDataByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByEconomicIndexIdAndStatus(Long economicIndexId, String status) {
        Long maxId = economicindexdataRepository.loadMaxIdByEconomicIndexIdAndStatus(economicIndexId, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable( economicindexdataRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_ECONOMICINDEXID + economicIndexId,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEXDATA_NOTFOUND_WITH_ECONOMICINDEXID + economicIndexId))
                );
        return economicindexdataData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByEconomicIndexIdAndStatus(Long economicIndexId) {
        return this.findEconomicIndexDataByEconomicIndexIdAndStatus(economicIndexId, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByIndexDateAndStatus(LocalDate indexDate, String status) {
        Long maxId = economicindexdataRepository.loadMaxIdByIndexDateAndStatus(indexDate, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable( economicindexdataRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXDATE + indexDate,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXDATE + indexDate))
                );
        return economicindexdataData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByIndexDateAndStatus(LocalDate indexDate) {
        return this.findEconomicIndexDataByIndexDateAndStatus(indexDate, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByIndexValueAndStatus(Double indexValue, String status) {
        Long maxId = economicindexdataRepository.loadMaxIdByIndexValueAndStatus(indexValue, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable( economicindexdataRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXVALUE + indexValue,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEXDATA_NOTFOUND_WITH_INDEXVALUE + indexValue))
                );
        return economicindexdataData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByIndexValueAndStatus(Double indexValue) {
        return this.findEconomicIndexDataByIndexValueAndStatus(indexValue, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = economicindexdataRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable( economicindexdataRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEXDATA_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return economicindexdataData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByDateCreatedAndStatus(Date dateCreated) {
        return this.findEconomicIndexDataByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = economicindexdataRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndexData> economicindexdataData =
            Optional.ofNullable( economicindexdataRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexDataNotFoundException(ECONOMICINDEXDATA_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEXDATA_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return economicindexdataData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexDataNotFoundException.class
    )
    public EconomicIndexDataDTO findEconomicIndexDataByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findEconomicIndexDataByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public EconomicIndexDataDTO updateEconomicIndexIdById(Long id, Long economicIndexId) {
        findById(id);
        economicindexdataRepository.updateEconomicIndexIdById(id, economicIndexId);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public EconomicIndexDataDTO updateIndexDateById(Long id, LocalDate indexDate) {
        findById(id);
        economicindexdataRepository.updateIndexDateById(id, indexDate);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public EconomicIndexDataDTO updateIndexValueById(Long id, Double indexValue) {
        findById(id);
        economicindexdataRepository.updateIndexValueById(id, indexValue);
        return findById(id);
    }


    public EconomicIndexDataDTO toDTO(EconomicIndexData economicindexdata) {
        EconomicIndexDataDTO economicindexdataDTO = new EconomicIndexDataDTO();
                economicindexdataDTO.setId(economicindexdata.getId());
                economicindexdataDTO.setEconomicIndexId(economicindexdata.getEconomicIndexId());
                economicindexdataDTO.setIndexDate(economicindexdata.getIndexDate());
                economicindexdataDTO.setIndexValue(economicindexdata.getIndexValue());
                economicindexdataDTO.setStatus(economicindexdata.getStatus());
                economicindexdataDTO.setDateCreated(economicindexdata.getDateCreated());
                economicindexdataDTO.setDateUpdated(economicindexdata.getDateUpdated());

        return economicindexdataDTO;
    }

    public EconomicIndexData toEntity(EconomicIndexDataDTO economicindexdataDTO) {
        EconomicIndexData economicindexdata = null;
        economicindexdata = new EconomicIndexData();
                    economicindexdata.setId(economicindexdataDTO.getId());
                    economicindexdata.setEconomicIndexId(economicindexdataDTO.getEconomicIndexId());
                    economicindexdata.setIndexDate(economicindexdataDTO.getIndexDate());
                    economicindexdata.setIndexValue(economicindexdataDTO.getIndexValue());
                    economicindexdata.setStatus(economicindexdataDTO.getStatus());
                    economicindexdata.setDateCreated(economicindexdataDTO.getDateCreated());
                    economicindexdata.setDateUpdated(economicindexdataDTO.getDateUpdated());

        return economicindexdata;
    }
}
