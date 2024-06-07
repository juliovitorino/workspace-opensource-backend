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

import br.com.jcv.brcities.corelayer.model.City;
import br.com.jcv.brcities.corelayer.repository.CityRepository;
import br.com.jcv.brcities.corelayer.service.CityService;
import br.com.jcv.brcities.infrastructure.constantes.CityConstantes;
import br.com.jcv.brcities.infrastructure.dto.CityDTO;
import br.com.jcv.brcities.infrastructure.exception.CityNotFoundException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateTime;
import lombok.extern.slf4j.Slf4j;


/**
* CityServiceImpl - Implementation for City interface
*
* @author City
* @since Mon Jun 03 17:37:14 BRT 2024
*/


@Slf4j
@Service
public class CityServiceImpl implements CityService
{
    private static final String CITY_NOTFOUND_WITH_ID = "City não encontrada com id = ";
    private static final String CITY_NOTFOUND_WITH_NAME = "City não encontrada com name = ";
    private static final String CITY_NOTFOUND_WITH_STATUS = "City não encontrada com status = ";
    private static final String CITY_NOTFOUND_WITH_DATECREATED = "City não encontrada com dateCreated = ";
    private static final String CITY_NOTFOUND_WITH_DATEUPDATED = "City não encontrada com dateUpdated = ";


    @Autowired private CityRepository cityRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando City com id = {}", id);
        cityRepository.findById(id)
                .orElseThrow(
                    () -> new CityNotFoundException(CITY_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        CITY_NOTFOUND_WITH_ID  + id));
        cityRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityNotFoundException.class
    )
    public CityDTO salvar(CityDTO cityDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(cityDTO.getId()) && cityDTO.getId() != 0) {
            cityDTO.setDateUpdated(now);
        } else {
            cityDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            cityDTO.setDateCreated(now);
            cityDTO.setDateUpdated(now);
        }
        return this.toDTO(cityRepository.save(this.toEntity(cityDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findById(Long id) {
        Optional<City> cityData =
            Optional.ofNullable(cityRepository.findById(id)
                .orElseThrow(
                    () -> new CityNotFoundException(CITY_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    CITY_NOTFOUND_WITH_ID  + id ))
                );

        return cityData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<City> cityData =
            Optional.ofNullable(cityRepository.findById(id)
                .orElseThrow(
                    () -> new CityNotFoundException(CITY_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        CITY_NOTFOUND_WITH_ID  + id))
                    );
        if (cityData.isPresent()) {
            City city = cityData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(CityConstantes.NAME)) city.setName((String)entry.getValue());

        }
        if(updates.get(CityConstantes.DATEUPDATED) == null) city.setDateUpdated(new Date());
        cityRepository.save(city);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = CityNotFoundException.class
    )
    public CityDTO updateStatusById(Long id, String status) {
        Optional<City> cityData =
            Optional.ofNullable( cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(CITY_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    CITY_NOTFOUND_WITH_ID + id))
                );
        City city = cityData.orElseGet(City::new);
        city.setStatus(status);
        city.setDateUpdated(new Date());
        return toDTO(cityRepository.save(city));

    }

    @Override
    public List<CityDTO> findAllByStatus(String status) {
        return cityRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<City> lstCity;
    Long id = null;
    String name = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(CityConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<City> paginaCity = cityRepository.findCityByFilter(paging,
        id
        ,name
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstCity = paginaCity.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaCity.getNumber());
    response.put("totalItems", paginaCity.getTotalElements());
    response.put("totalPages", paginaCity.getTotalPages());
    response.put("pageCityItems", lstCity.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
)
    public List<CityDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    String name = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(CityConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(CityConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(CityConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<City> lstCity = cityRepository.findCityByFilter(
            id
            ,name
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstCity.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public List<CityDTO> findAllCityByIdAndStatus(Long id, String status) {
        return cityRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public List<CityDTO> findAllCityByNameAndStatus(String name, String status) {
        return cityRepository.findAllByNameAndStatus(name, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public List<CityDTO> findAllCityByDateCreatedAndStatus(Date dateCreated, String status) {
        return cityRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public List<CityDTO> findAllCityByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return cityRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findCityByIdAndStatus(Long id, String status) {
        Long maxId = cityRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<City> cityData =
            Optional.ofNullable( cityRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityNotFoundException(CITY_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        CITY_NOTFOUND_WITH_ID + id))
                );
        return cityData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findCityByIdAndStatus(Long id) {
        return this.findCityByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findCityByNameAndStatus(String name, String status) {
        Long maxId = cityRepository.loadMaxIdByNameAndStatus(name, status);
        if(maxId == null) maxId = 0L;
        Optional<City> cityData =
            Optional.ofNullable( cityRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityNotFoundException(CITY_NOTFOUND_WITH_NAME + name,
                        HttpStatus.NOT_FOUND,
                        CITY_NOTFOUND_WITH_NAME + name))
                );
        return cityData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findCityByNameAndStatus(String name) {
        return this.findCityByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findCityByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = cityRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<City> cityData =
            Optional.ofNullable( cityRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityNotFoundException(CITY_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        CITY_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return cityData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findCityByDateCreatedAndStatus(Date dateCreated) {
        return this.findCityByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findCityByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = cityRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<City> cityData =
            Optional.ofNullable( cityRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new CityNotFoundException(CITY_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        CITY_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return cityData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = CityNotFoundException.class
    )
    public CityDTO findCityByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findCityByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public CityDTO updateNameById(Long id, String name) {
        findById(id);
        cityRepository.updateNameById(id, name);
        return findById(id);
    }


    public CityDTO toDTO(City city) {
        CityDTO cityDTO = new CityDTO();
                cityDTO.setId(city.getId());
                cityDTO.setName(city.getName());
                cityDTO.setStatus(city.getStatus());
                cityDTO.setDateCreated(city.getDateCreated());
                cityDTO.setDateUpdated(city.getDateUpdated());

        return cityDTO;
    }

    public City toEntity(CityDTO cityDTO) {
        City city = null;
        city = new City();
                    city.setId(cityDTO.getId());
                    city.setName(cityDTO.getName());
                    city.setStatus(cityDTO.getStatus());
                    city.setDateCreated(cityDTO.getDateCreated());
                    city.setDateUpdated(cityDTO.getDateUpdated());

        return city;
    }
}
