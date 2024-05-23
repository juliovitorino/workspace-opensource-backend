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

import br.com.jcv.bei.corelayer.model.EconomicIndex;
import br.com.jcv.bei.corelayer.repository.EconomicIndexRepository;
import br.com.jcv.bei.corelayer.service.EconomicIndexService;
import br.com.jcv.bei.infrastructure.constantes.EconomicIndexConstantes;
import br.com.jcv.bei.infrastructure.dto.EconomicIndexDTO;
import br.com.jcv.bei.infrastructure.exception.EconomicIndexNotFoundException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateTime;
import lombok.extern.slf4j.Slf4j;


/**
* EconomicIndexServiceImpl - Implementation for EconomicIndex interface
*
* @author EconomicIndex
* @since Thu May 23 16:41:19 BRT 2024
*/


@Slf4j
@Service
public class EconomicIndexServiceImpl implements EconomicIndexService
{
    private static final String ECONOMICINDEX_NOTFOUND_WITH_ID = "EconomicIndex não encontrada com id = ";
    private static final String ECONOMICINDEX_NOTFOUND_WITH_ECONOMICINDEX = "EconomicIndex não encontrada com economicIndex = ";
    private static final String ECONOMICINDEX_NOTFOUND_WITH_BACENSERIECODE = "EconomicIndex não encontrada com bacenSerieCode = ";
    private static final String ECONOMICINDEX_NOTFOUND_WITH_LASTDATEVALUE = "EconomicIndex não encontrada com lastDateValue = ";
    private static final String ECONOMICINDEX_NOTFOUND_WITH_STATUS = "EconomicIndex não encontrada com status = ";
    private static final String ECONOMICINDEX_NOTFOUND_WITH_DATECREATED = "EconomicIndex não encontrada com dateCreated = ";
    private static final String ECONOMICINDEX_NOTFOUND_WITH_DATEUPDATED = "EconomicIndex não encontrada com dateUpdated = ";


    @Autowired private EconomicIndexRepository economicindexRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando EconomicIndex com id = {}", id);
        economicindexRepository.findById(id)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEX_NOTFOUND_WITH_ID  + id));
        economicindexRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO salvar(EconomicIndexDTO economicindexDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(economicindexDTO.getId()) && economicindexDTO.getId() != 0) {
            economicindexDTO.setDateUpdated(now);
        } else {
            economicindexDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            economicindexDTO.setDateCreated(now);
            economicindexDTO.setDateUpdated(now);
        }
        return this.toDTO(economicindexRepository.save(this.toEntity(economicindexDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findById(Long id) {
        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable(economicindexRepository.findById(id)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    ECONOMICINDEX_NOTFOUND_WITH_ID  + id ))
                );

        return economicindexData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable(economicindexRepository.findById(id)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEX_NOTFOUND_WITH_ID  + id))
                    );
        if (economicindexData.isPresent()) {
            EconomicIndex economicindex = economicindexData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.ECONOMICINDEX)) economicindex.setEconomicIndex((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.BACENSERIECODE)) economicindex.setBacenSerieCode((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.LASTDATEVALUE)) economicindex.setLastDateValue((LocalDate)entry.getValue());

        }
        if(updates.get(EconomicIndexConstantes.DATEUPDATED) == null) economicindex.setDateUpdated(new Date());
        economicindexRepository.save(economicindex);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO updateStatusById(Long id, String status) {
        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable( economicindexRepository.findById(id)
                .orElseThrow(() -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    ECONOMICINDEX_NOTFOUND_WITH_ID + id))
                );
        EconomicIndex economicindex = economicindexData.orElseGet(EconomicIndex::new);
        economicindex.setStatus(status);
        economicindex.setDateUpdated(new Date());
        return toDTO(economicindexRepository.save(economicindex));

    }

    @Override
    public List<EconomicIndexDTO> findAllByStatus(String status) {
        return economicindexRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<EconomicIndex> lstEconomicIndex;
    Long id = null;
    String economicIndex = null;
    String bacenSerieCode = null;
    String lastDateValue = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.ECONOMICINDEX)) economicIndex = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.BACENSERIECODE)) bacenSerieCode = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.LASTDATEVALUE)) lastDateValue = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<EconomicIndex> paginaEconomicIndex = economicindexRepository.findEconomicIndexByFilter(paging,
        id
        ,economicIndex
        ,bacenSerieCode
        ,lastDateValue
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstEconomicIndex = paginaEconomicIndex.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaEconomicIndex.getNumber());
    response.put("totalItems", paginaEconomicIndex.getTotalElements());
    response.put("totalPages", paginaEconomicIndex.getTotalPages());
    response.put("pageEconomicIndexItems", lstEconomicIndex.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
)
    public List<EconomicIndexDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    String economicIndex = null;
    String bacenSerieCode = null;
    String lastDateValue = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.ECONOMICINDEX)) economicIndex = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.BACENSERIECODE)) bacenSerieCode = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.LASTDATEVALUE)) lastDateValue = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(EconomicIndexConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<EconomicIndex> lstEconomicIndex = economicindexRepository.findEconomicIndexByFilter(
            id
            ,economicIndex
            ,bacenSerieCode
            ,lastDateValue
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstEconomicIndex.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public List<EconomicIndexDTO> findAllEconomicIndexByIdAndStatus(Long id, String status) {
        return economicindexRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public List<EconomicIndexDTO> findAllEconomicIndexByEconomicIndexAndStatus(String economicIndex, String status) {
        return economicindexRepository.findAllByEconomicIndexAndStatus(economicIndex, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public List<EconomicIndexDTO> findAllEconomicIndexByBacenSerieCodeAndStatus(String bacenSerieCode, String status) {
        return economicindexRepository.findAllByBacenSerieCodeAndStatus(bacenSerieCode, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public List<EconomicIndexDTO> findAllEconomicIndexByLastDateValueAndStatus(LocalDate lastDateValue, String status) {
        return economicindexRepository.findAllByLastDateValueAndStatus(lastDateValue, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public List<EconomicIndexDTO> findAllEconomicIndexByDateCreatedAndStatus(Date dateCreated, String status) {
        return economicindexRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public List<EconomicIndexDTO> findAllEconomicIndexByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return economicindexRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByIdAndStatus(Long id, String status) {
        Long maxId = economicindexRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable( economicindexRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEX_NOTFOUND_WITH_ID + id))
                );
        return economicindexData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByIdAndStatus(Long id) {
        return this.findEconomicIndexByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByEconomicIndexAndStatus(String economicIndex, String status) {
        Long maxId = economicindexRepository.loadMaxIdByEconomicIndexAndStatus(economicIndex, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable( economicindexRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_ECONOMICINDEX + economicIndex,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEX_NOTFOUND_WITH_ECONOMICINDEX + economicIndex))
                );
        return economicindexData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByEconomicIndexAndStatus(String economicIndex) {
        return this.findEconomicIndexByEconomicIndexAndStatus(economicIndex, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByBacenSerieCodeAndStatus(String bacenSerieCode, String status) {
        Long maxId = economicindexRepository.loadMaxIdByBacenSerieCodeAndStatus(bacenSerieCode, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable( economicindexRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_BACENSERIECODE + bacenSerieCode,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEX_NOTFOUND_WITH_BACENSERIECODE + bacenSerieCode))
                );
        return economicindexData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByBacenSerieCodeAndStatus(String bacenSerieCode) {
        return this.findEconomicIndexByBacenSerieCodeAndStatus(bacenSerieCode, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByLastDateValueAndStatus(LocalDate lastDateValue, String status) {
        Long maxId = economicindexRepository.loadMaxIdByLastDateValueAndStatus(lastDateValue, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable( economicindexRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_LASTDATEVALUE + lastDateValue,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEX_NOTFOUND_WITH_LASTDATEVALUE + lastDateValue))
                );
        return economicindexData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByLastDateValueAndStatus(LocalDate lastDateValue) {
        return this.findEconomicIndexByLastDateValueAndStatus(lastDateValue, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = economicindexRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable( economicindexRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEX_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return economicindexData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByDateCreatedAndStatus(Date dateCreated) {
        return this.findEconomicIndexByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = economicindexRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<EconomicIndex> economicindexData =
            Optional.ofNullable( economicindexRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new EconomicIndexNotFoundException(ECONOMICINDEX_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        ECONOMICINDEX_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return economicindexData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = EconomicIndexNotFoundException.class
    )
    public EconomicIndexDTO findEconomicIndexByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findEconomicIndexByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public EconomicIndexDTO updateEconomicIndexById(Long id, String economicIndex) {
        findById(id);
        economicindexRepository.updateEconomicIndexById(id, economicIndex);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public EconomicIndexDTO updateBacenSerieCodeById(Long id, String bacenSerieCode) {
        findById(id);
        economicindexRepository.updateBacenSerieCodeById(id, bacenSerieCode);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public EconomicIndexDTO updateLastDateValueById(Long id, LocalDate lastDateValue) {
        findById(id);
        economicindexRepository.updateLastDateValueById(id, lastDateValue);
        return findById(id);
    }


    public EconomicIndexDTO toDTO(EconomicIndex economicindex) {
        EconomicIndexDTO economicindexDTO = new EconomicIndexDTO();
                economicindexDTO.setId(economicindex.getId());
                economicindexDTO.setEconomicIndex(economicindex.getEconomicIndex());
                economicindexDTO.setBacenSerieCode(economicindex.getBacenSerieCode());
                economicindexDTO.setLastDateValue(economicindex.getLastDateValue());
                economicindexDTO.setStatus(economicindex.getStatus());
                economicindexDTO.setDateCreated(economicindex.getDateCreated());
                economicindexDTO.setDateUpdated(economicindex.getDateUpdated());

        return economicindexDTO;
    }

    public EconomicIndex toEntity(EconomicIndexDTO economicindexDTO) {
        EconomicIndex economicindex = null;
        economicindex = new EconomicIndex();
                    economicindex.setId(economicindexDTO.getId());
                    economicindex.setEconomicIndex(economicindexDTO.getEconomicIndex());
                    economicindex.setBacenSerieCode(economicindexDTO.getBacenSerieCode());
                    economicindex.setLastDateValue(economicindexDTO.getLastDateValue());
                    economicindex.setStatus(economicindexDTO.getStatus());
                    economicindex.setDateCreated(economicindexDTO.getDateCreated());
                    economicindex.setDateUpdated(economicindexDTO.getDateUpdated());

        return economicindex;
    }
}
