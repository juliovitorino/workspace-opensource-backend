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


package br.com.jcv.brcities.corelayer.service.impl;

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

import br.com.jcv.brcities.corelayer.model.CityUf;
import br.com.jcv.brcities.corelayer.repository.CityUfRepository;
import br.com.jcv.brcities.corelayer.service.CityUfService;
import br.com.jcv.brcities.infrastructure.constantes.CityUfConstantes;
import br.com.jcv.brcities.infrastructure.dto.CityUfDTO;
import br.com.jcv.brcities.infrastructure.exception.CityUfNotFoundException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateTime;
import lombok.extern.slf4j.Slf4j;


/**
* CityUfServiceImpl - Implementation for CityUf interface
*
* @author CityUf
* @since Wed Jun 05 13:47:11 BRT 2024
*/


@Slf4j
@Service
public class CityUfServiceImpl implements CityUfService
{
    private static final String CITYUF_NOTFOUND_WITH_ID = "CityUf não encontrada com id = ";
    private static final String CITYUF_NOTFOUND_WITH_CITYID = "CityUf não encontrada com cityId = ";
    private static final String CITYUF_NOTFOUND_WITH_UFID = "CityUf não encontrada com ufId = ";
    private static final String CITYUF_NOTFOUND_WITH_DDD = "CityUf não encontrada com ddd = ";
    private static final String CITYUF_NOTFOUND_WITH_LATITUDE = "CityUf não encontrada com latitude = ";
    private static final String CITYUF_NOTFOUND_WITH_LONGITUDE = "CityUf não encontrada com longitude = ";
    private static final String CITYUF_NOTFOUND_WITH_STATUS = "CityUf não encontrada com status = ";
    private static final String CITYUF_NOTFOUND_WITH_DATECREATED = "CityUf não encontrada com dateCreated = ";
    private static final String CITYUF_NOTFOUND_WITH_DATEUPDATED = "CityUf não encontrada com dateUpdated = ";


    @Autowired private CityUfRepository cityufRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityUfNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando CityUf com id = {}", id);
        cityufRepository.findById(id)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_ID  + id));
        cityufRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO salvar(CityUfDTO cityufDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(cityufDTO.getId()) && cityufDTO.getId() != 0) {
            cityufDTO.setDateUpdated(now);
        } else {
            cityufDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            cityufDTO.setDateCreated(now);
            cityufDTO.setDateUpdated(now);
        }
        return this.toDTO(cityufRepository.save(this.toEntity(cityufDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findById(Long id) {
        Optional<CityUf> cityufData =
            Optional.ofNullable(cityufRepository.findById(id)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    CITYUF_NOTFOUND_WITH_ID  + id ))
                );

        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityUfNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<CityUf> cityufData =
            Optional.ofNullable(cityufRepository.findById(id)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_ID  + id))
                    );
        if (cityufData.isPresent()) {
            CityUf cityuf = cityufData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(CityUfConstantes.CITYID)) cityuf.setCityId((Long)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(CityUfConstantes.UFID)) cityuf.setUfId((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(CityUfConstantes.DDD)) cityuf.setDdd((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(CityUfConstantes.LATITUDE)) cityuf.setLatitude((Double)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(CityUfConstantes.LONGITUDE)) cityuf.setLongitude((Double)entry.getValue());

        }
        if(updates.get(CityUfConstantes.DATEUPDATED) == null) cityuf.setDateUpdated(new Date());
        cityufRepository.save(cityuf);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO updateStatusById(Long id, String status) {
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository.findById(id)
                .orElseThrow(() -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    CITYUF_NOTFOUND_WITH_ID + id))
                );
        CityUf cityuf = cityufData.orElseGet(CityUf::new);
        cityuf.setStatus(status);
        cityuf.setDateUpdated(new Date());
        return toDTO(cityufRepository.save(cityuf));

    }

    @Override
    public List<CityUfDTO> findAllByStatus(String status) {
        return cityufRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<CityUf> lstCityUf;
    Long id = null;
    Long cityId = null;
    String ufId = null;
    String ddd = null;
    Double latitude = null;
    Double longitude = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.CITYID)) cityId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.UFID)) ufId = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.DDD)) ddd = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.LATITUDE)) latitude = Objects.isNull(entry.getValue()) ? null : Double.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.LONGITUDE)) longitude = Objects.isNull(entry.getValue()) ? null : Double.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<CityUf> paginaCityUf = cityufRepository.findCityUfByFilter(paging,
        id
        ,cityId
        ,ufId
        ,ddd
        ,latitude
        ,longitude
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstCityUf = paginaCityUf.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaCityUf.getNumber());
    response.put("totalItems", paginaCityUf.getTotalElements());
    response.put("totalPages", paginaCityUf.getTotalPages());
    response.put("pageCityUfItems", lstCityUf.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
)
    public List<CityUfDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long cityId = null;
    String ufId = null;
    String ddd = null;
    Double latitude = null;
    Double longitude = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.CITYID)) cityId = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.UFID)) ufId = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.DDD)) ddd = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.LATITUDE)) latitude = Objects.isNull(entry.getValue()) ? null : Double.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.LONGITUDE)) longitude = Objects.isNull(entry.getValue()) ? null : Double.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityUfConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<CityUf> lstCityUf = cityufRepository.findCityUfByFilter(
            id
            ,cityId
            ,ufId
            ,ddd
            ,latitude
            ,longitude
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstCityUf.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public List<CityUfDTO> findAllCityUfByIdAndStatus(Long id, String status) {
        return cityufRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public List<CityUfDTO> findAllCityUfByCityIdAndStatus(Long cityId, String status) {
        return cityufRepository.findAllByCityIdAndStatus(cityId, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public List<CityUfDTO> findAllCityUfByUfIdAndStatus(String ufId, String status) {
        return cityufRepository.findAllByUfIdAndStatus(ufId, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public List<CityUfDTO> findAllCityUfByDddAndStatus(String ddd, String status) {
        return cityufRepository.findAllByDddAndStatus(ddd, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public List<CityUfDTO> findAllCityUfByLatitudeAndStatus(Double latitude, String status) {
        return cityufRepository.findAllByLatitudeAndStatus(latitude, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public List<CityUfDTO> findAllCityUfByLongitudeAndStatus(Double longitude, String status) {
        return cityufRepository.findAllByLongitudeAndStatus(longitude, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public List<CityUfDTO> findAllCityUfByDateCreatedAndStatus(Date dateCreated, String status) {
        return cityufRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public List<CityUfDTO> findAllCityUfByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return cityufRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByIdAndStatus(Long id, String status) {
        Long maxId = cityufRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_ID + id))
                );
        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByIdAndStatus(Long id) {
        return this.findCityUfByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByCityIdAndStatus(Long cityId, String status) {
        Long maxId = cityufRepository.loadMaxIdByCityIdAndStatus(cityId, status);
        if(maxId == null) maxId = 0L;
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_CITYID + cityId,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_CITYID + cityId))
                );
        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByCityIdAndStatus(Long cityId) {
        return this.findCityUfByCityIdAndStatus(cityId, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByUfIdAndStatus(String ufId, String status) {
        Long maxId = cityufRepository.loadMaxIdByUfIdAndStatus(ufId, status);
        if(maxId == null) maxId = 0L;
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_UFID + ufId,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_UFID + ufId))
                );
        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByUfIdAndStatus(String ufId) {
        return this.findCityUfByUfIdAndStatus(ufId, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByDddAndStatus(String ddd, String status) {
        Long maxId = cityufRepository.loadMaxIdByDddAndStatus(ddd, status);
        if(maxId == null) maxId = 0L;
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_DDD + ddd,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_DDD + ddd))
                );
        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByDddAndStatus(String ddd) {
        return this.findCityUfByDddAndStatus(ddd, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByLatitudeAndStatus(Double latitude, String status) {
        Long maxId = cityufRepository.loadMaxIdByLatitudeAndStatus(latitude, status);
        if(maxId == null) maxId = 0L;
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_LATITUDE + latitude,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_LATITUDE + latitude))
                );
        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByLatitudeAndStatus(Double latitude) {
        return this.findCityUfByLatitudeAndStatus(latitude, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByLongitudeAndStatus(Double longitude, String status) {
        Long maxId = cityufRepository.loadMaxIdByLongitudeAndStatus(longitude, status);
        if(maxId == null) maxId = 0L;
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_LONGITUDE + longitude,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_LONGITUDE + longitude))
                );
        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByLongitudeAndStatus(Double longitude) {
        return this.findCityUfByLongitudeAndStatus(longitude, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = cityufRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByDateCreatedAndStatus(Date dateCreated) {
        return this.findCityUfByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = cityufRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<CityUf> cityufData =
            Optional.ofNullable( cityufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityUfNotFoundException(CITYUF_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        CITYUF_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return cityufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityUfNotFoundException.class
    )
    public CityUfDTO findCityUfByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findCityUfByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public CityUfDTO updateCityIdById(Long id, Long cityId) {
        findById(id);
        cityufRepository.updateCityIdById(id, cityId);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public CityUfDTO updateUfIdById(Long id, String ufId) {
        findById(id);
        cityufRepository.updateUfIdById(id, ufId);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public CityUfDTO updateDddById(Long id, String ddd) {
        findById(id);
        cityufRepository.updateDddById(id, ddd);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public CityUfDTO updateLatitudeById(Long id, Double latitude) {
        findById(id);
        cityufRepository.updateLatitudeById(id, latitude);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public CityUfDTO updateLongitudeById(Long id, Double longitude) {
        findById(id);
        cityufRepository.updateLongitudeById(id, longitude);
        return findById(id);
    }


    public CityUfDTO toDTO(CityUf cityuf) {
        CityUfDTO cityufDTO = new CityUfDTO();
                cityufDTO.setId(cityuf.getId());
                cityufDTO.setCityId(cityuf.getCityId());
                cityufDTO.setUfId(cityuf.getUfId());
                cityufDTO.setDdd(cityuf.getDdd());
                cityufDTO.setLatitude(cityuf.getLatitude());
                cityufDTO.setLongitude(cityuf.getLongitude());
                cityufDTO.setStatus(cityuf.getStatus());
                cityufDTO.setDateCreated(cityuf.getDateCreated());
                cityufDTO.setDateUpdated(cityuf.getDateUpdated());

        return cityufDTO;
    }

    public CityUf toEntity(CityUfDTO cityufDTO) {
        CityUf cityuf = null;
        cityuf = new CityUf();
                    cityuf.setId(cityufDTO.getId());
                    cityuf.setCityId(cityufDTO.getCityId());
                    cityuf.setUfId(cityufDTO.getUfId());
                    cityuf.setDdd(cityufDTO.getDdd());
                    cityuf.setLatitude(cityufDTO.getLatitude());
                    cityuf.setLongitude(cityufDTO.getLongitude());
                    cityuf.setStatus(cityufDTO.getStatus());
                    cityuf.setDateCreated(cityufDTO.getDateCreated());
                    cityuf.setDateUpdated(cityufDTO.getDateUpdated());

        return cityuf;
    }
}
