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

import br.com.jcv.brcities.corelayer.model.Uf;
import br.com.jcv.brcities.corelayer.repository.UfRepository;
import br.com.jcv.brcities.corelayer.service.UfService;
import br.com.jcv.brcities.infrastructure.constantes.UfConstantes;
import br.com.jcv.brcities.infrastructure.dto.UfDTO;
import br.com.jcv.brcities.infrastructure.exception.UfNotFoundException;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateTime;
import lombok.extern.slf4j.Slf4j;


/**
* UfServiceImpl - Implementation for Uf interface
*
* @author Uf
* @since Mon Jun 03 16:53:27 BRT 2024
*/


@Slf4j
@Service
public class UfServiceImpl implements UfService
{
    private static final String UF_NOTFOUND_WITH_ID = "Uf não encontrada com id = ";
    private static final String UF_NOTFOUND_WITH_NAME = "Uf não encontrada com name = ";
    private static final String UF_NOTFOUND_WITH_STATUS = "Uf não encontrada com status = ";
    private static final String UF_NOTFOUND_WITH_DATECREATED = "Uf não encontrada com dateCreated = ";
    private static final String UF_NOTFOUND_WITH_DATEUPDATED = "Uf não encontrada com dateUpdated = ";


    @Autowired private UfRepository ufRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UfNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando Uf com id = {}", id);
        ufRepository.findById(id)
                .orElseThrow(
                    () -> new UfNotFoundException(UF_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        UF_NOTFOUND_WITH_ID  + id));
        ufRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UfNotFoundException.class
    )
    public UfDTO salvar(UfDTO ufDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(ufDTO.getId()) && ufDTO.getId() != 0) {
            ufDTO.setDateUpdated(now);
        } else {
            ufDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            ufDTO.setDateCreated(now);
            ufDTO.setDateUpdated(now);
        }
        return this.toDTO(ufRepository.save(this.toEntity(ufDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findById(Long id) {
        Optional<Uf> ufData =
            Optional.ofNullable(ufRepository.findById(id)
                .orElseThrow(
                    () -> new UfNotFoundException(UF_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    UF_NOTFOUND_WITH_ID  + id ))
                );

        return ufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UfNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<Uf> ufData =
            Optional.ofNullable(ufRepository.findById(id)
                .orElseThrow(
                    () -> new UfNotFoundException(UF_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        UF_NOTFOUND_WITH_ID  + id))
                    );
        if (ufData.isPresent()) {
            Uf uf = ufData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(UfConstantes.NAME)) uf.setName((String)entry.getValue());

        }
        if(updates.get(UfConstantes.DATEUPDATED) == null) uf.setDateUpdated(new Date());
        ufRepository.save(uf);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UfNotFoundException.class
    )
    public UfDTO updateStatusById(Long id, String status) {
        Optional<Uf> ufData =
            Optional.ofNullable( ufRepository.findById(id)
                .orElseThrow(() -> new UfNotFoundException(UF_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    UF_NOTFOUND_WITH_ID + id))
                );
        Uf uf = ufData.orElseGet(Uf::new);
        uf.setStatus(status);
        uf.setDateUpdated(new Date());
        return toDTO(ufRepository.save(uf));

    }

    @Override
    public List<UfDTO> findAllByStatus(String status) {
        return ufRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<Uf> lstUf;
    Long id = null;
    String name = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(UfConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UfConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UfConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UfConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UfConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<Uf> paginaUf = ufRepository.findUfByFilter(paging,
        id
        ,name
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstUf = paginaUf.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaUf.getNumber());
    response.put("totalItems", paginaUf.getTotalElements());
    response.put("totalPages", paginaUf.getTotalPages());
    response.put("pageUfItems", lstUf.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
)
    public List<UfDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    String name = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(UfConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UfConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UfConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UfConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UfConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<Uf> lstUf = ufRepository.findUfByFilter(
            id
            ,name
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstUf.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public List<UfDTO> findAllUfByIdAndStatus(Long id, String status) {
        return ufRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public List<UfDTO> findAllUfByNameAndStatus(String name, String status) {
        return ufRepository.findAllByNameAndStatus(name, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public List<UfDTO> findAllUfByDateCreatedAndStatus(Date dateCreated, String status) {
        return ufRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public List<UfDTO> findAllUfByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return ufRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findUfByIdAndStatus(Long id, String status) {
        Long maxId = ufRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<Uf> ufData =
            Optional.ofNullable( ufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UfNotFoundException(UF_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        UF_NOTFOUND_WITH_ID + id))
                );
        return ufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findUfByIdAndStatus(Long id) {
        return this.findUfByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findUfByNameAndStatus(String name, String status) {
        Long maxId = ufRepository.loadMaxIdByNameAndStatus(name, status);
        if(maxId == null) maxId = 0L;
        Optional<Uf> ufData =
            Optional.ofNullable( ufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UfNotFoundException(UF_NOTFOUND_WITH_NAME + name,
                        HttpStatus.NOT_FOUND,
                        UF_NOTFOUND_WITH_NAME + name))
                );
        return ufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findUfByNameAndStatus(String name) {
        return this.findUfByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findUfByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = ufRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<Uf> ufData =
            Optional.ofNullable( ufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UfNotFoundException(UF_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        UF_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return ufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findUfByDateCreatedAndStatus(Date dateCreated) {
        return this.findUfByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findUfByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = ufRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<Uf> ufData =
            Optional.ofNullable( ufRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UfNotFoundException(UF_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        UF_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return ufData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UfNotFoundException.class
    )
    public UfDTO findUfByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findUfByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UfDTO updateNameById(Long id, String name) {
        findById(id);
        ufRepository.updateNameById(id, name);
        return findById(id);
    }


    public UfDTO toDTO(Uf uf) {
        UfDTO ufDTO = new UfDTO();
        ufDTO.setId(uf.getId());
        ufDTO.setNickname(uf.getNickname());
        ufDTO.setName(uf.getName());
        ufDTO.setStatus(uf.getStatus());
        ufDTO.setDateCreated(uf.getDateCreated());
        ufDTO.setDateUpdated(uf.getDateUpdated());

        return ufDTO;
    }

    public Uf toEntity(UfDTO ufDTO) {
        Uf uf = null;
        uf = new Uf();
        uf.setId(ufDTO.getId());
        uf.setNickname(ufDTO.getNickname());
        uf.setName(ufDTO.getName());
        uf.setStatus(ufDTO.getStatus());
        uf.setDateCreated(ufDTO.getDateCreated());
        uf.setDateUpdated(ufDTO.getDateUpdated());

        return uf;
    }
}
