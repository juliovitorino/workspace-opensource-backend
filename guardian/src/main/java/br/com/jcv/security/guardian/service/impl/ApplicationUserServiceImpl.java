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


package br.com.jcv.security.guardian.service.impl;

import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateTime;
import br.com.jcv.security.guardian.constantes.ApplicationUserConstantes;
import br.com.jcv.security.guardian.dto.ApplicationUserDTO;
import br.com.jcv.security.guardian.exception.ApplicationUserNotFoundException;
import br.com.jcv.security.guardian.infrastructure.CacheProvider;
import br.com.jcv.security.guardian.model.ApplicationUser;
import br.com.jcv.security.guardian.repository.ApplicationUserRepository;
import br.com.jcv.security.guardian.service.ApplicationUserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/**
* ApplicationUserServiceImpl - Implementation for ApplicationUser interface
*
* @author ApplicationUser
* @since Thu Nov 16 09:03:29 BRT 2023
*/


@Slf4j
@Service
public class ApplicationUserServiceImpl implements ApplicationUserService
{
    private static final String APPLICATIONUSER_NOTFOUND_WITH_ID = "ApplicationUser não encontrada com id = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_IDUSER = "ApplicationUser não encontrada com idUser = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_EMAIL = "ApplicationUser não encontrada com email = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_ENCODEDPASSPHRASE = "ApplicationUser não encontrada com encodedPassPhrase = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_EXTERNALAPPUSERUUID = "ApplicationUser não encontrada com externalAppUserUUID = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_EXTERNALUSERUUID = "ApplicationUser não encontrada com externalUserUUID = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_URLTOKENACTIVATION = "ApplicationUser não encontrada com urlTokenActivation = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_ACTIVATIONCODE = "ApplicationUser não encontrada com activationCode = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_DUEDATEACTIVATION = "ApplicationUser não encontrada com dueDateActivation = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_STATUS = "ApplicationUser não encontrada com status = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_DATECREATED = "ApplicationUser não encontrada com dateCreated = ";
    private static final String APPLICATIONUSER_NOTFOUND_WITH_DATEUPDATED = "ApplicationUser não encontrada com dateUpdated = ";


    @Autowired private ApplicationUserRepository applicationuserRepository;
    @Autowired private DateTime dateTime;
    @Autowired private @Qualifier("redisService") CacheProvider redisProvider;
    @Autowired private Gson gson;


    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ApplicationUserNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando ApplicationUser com id = {}", id);
        applicationuserRepository.findById(id)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_ID  + id));
        applicationuserRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO salvar(ApplicationUserDTO applicationuserDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(applicationuserDTO.getId()) && applicationuserDTO.getId() != 0) {
            applicationuserDTO.setDateUpdated(now);
        } else {
            applicationuserDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            applicationuserDTO.setDateCreated(now);
            applicationuserDTO.setDateUpdated(now);
        }
        return this.toDTO(applicationuserRepository.save(this.toEntity(applicationuserDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findById(Long id) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-findById-" + id,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable(applicationuserRepository.findById(id)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    APPLICATIONUSER_NOTFOUND_WITH_ID  + id ))
                );

        ApplicationUserDTO roleResponse = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(roleResponse)) {
            redisProvider.setValue("applicationUser-findById-" + id, gson.toJson(roleResponse),120);
        }
        return roleResponse;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ApplicationUserNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable(applicationuserRepository.findById(id)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_ID  + id))
                    );
        if (applicationuserData.isPresent()) {
            ApplicationUser applicationuser = applicationuserData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.EMAIL)) applicationuser.setEmail((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.ENCODEDPASSPHRASE)) applicationuser.setEncodedPassPhrase((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.EXTERNALAPPUSERUUID)) applicationuser.setExternalAppUserUUID((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.URLTOKENACTIVATION)) applicationuser.setUrlTokenActivation((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.ACTIVATIONCODE)) applicationuser.setActivationCode((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.DUEDATEACTIVATION)) applicationuser.setDueDateActivation((Date)entry.getValue());

        }
        if(updates.get(ApplicationUserConstantes.DATEUPDATED) == null) applicationuser.setDateUpdated(new Date());
        applicationuserRepository.save(applicationuser);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO updateStatusById(Long id, String status) {
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository.findById(id)
                .orElseThrow(() -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    APPLICATIONUSER_NOTFOUND_WITH_ID + id))
                );
        ApplicationUser applicationuser = applicationuserData.orElseGet(ApplicationUser::new);
        applicationuser.setStatus(status);
        applicationuser.setDateUpdated(new Date());
        return toDTO(applicationuserRepository.save(applicationuser));

    }

    @Override
    public List<ApplicationUserDTO> findAllByStatus(String status) {
        return applicationuserRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<ApplicationUser> lstApplicationUser;
    Long id = null;
    Long idUser = null;
    String email = null;
    String encodedPassPhrase = null;
    UUID externalAppUserUUID = null;
    String urlTokenActivation = null;
    String activationCode = null;
    String dueDateActivation = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.IDUSER)) idUser = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.EMAIL)) email = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.ENCODEDPASSPHRASE)) encodedPassPhrase = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.EXTERNALAPPUSERUUID)) externalAppUserUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.URLTOKENACTIVATION)) urlTokenActivation = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.ACTIVATIONCODE)) activationCode = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.DUEDATEACTIVATION)) dueDateActivation = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<ApplicationUser> paginaApplicationUser = applicationuserRepository.findApplicationUserByFilter(paging,
        id
        ,idUser
        ,email
        ,encodedPassPhrase
        ,externalAppUserUUID
        ,urlTokenActivation
        ,activationCode
        ,dueDateActivation
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstApplicationUser = paginaApplicationUser.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaApplicationUser.getNumber());
    response.put("totalItems", paginaApplicationUser.getTotalElements());
    response.put("totalPages", paginaApplicationUser.getTotalPages());
    response.put("pageApplicationUserItems", lstApplicationUser.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
)
    public List<ApplicationUserDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    Long idUser = null;
    String email = null;
    String encodedPassPhrase = null;
    UUID externalAppUserUUID = null;
    String urlTokenActivation = null;
    String activationCode = null;
    String dueDateActivation = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.IDUSER)) idUser = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.EMAIL)) email = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.ENCODEDPASSPHRASE)) encodedPassPhrase = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.EXTERNALAPPUSERUUID)) externalAppUserUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.URLTOKENACTIVATION)) urlTokenActivation = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.ACTIVATIONCODE)) activationCode = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.DUEDATEACTIVATION)) dueDateActivation = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(ApplicationUserConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<ApplicationUser> lstApplicationUser = applicationuserRepository.findApplicationUserByFilter(
            id
            ,idUser
            ,email
            ,encodedPassPhrase
            ,externalAppUserUUID
            ,urlTokenActivation
            ,activationCode
            ,dueDateActivation
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstApplicationUser.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByIdAndStatus(Long id, String status) {
        return applicationuserRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByIdUserAndStatus(Long idUser, String status) {
        return applicationuserRepository.findAllByIdAndStatus(idUser, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByEmailAndStatus(String email, String status) {
        return applicationuserRepository.findAllByEmailAndStatus(email, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status) {
        return applicationuserRepository.findAllByEncodedPassPhraseAndStatus(encodedPassPhrase, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID, String status) {
        return applicationuserRepository.findAllByExternalAppUserUUIDAndStatus(externalAppUserUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByUrlTokenActivationAndStatus(String urlTokenActivation, String status) {
        return applicationuserRepository.findAllByUrlTokenActivationAndStatus(urlTokenActivation, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByActivationCodeAndStatus(String activationCode, String status) {
        return applicationuserRepository.findAllByActivationCodeAndStatus(activationCode, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByDueDateActivationAndStatus(Date dueDateActivation, String status) {
        return applicationuserRepository.findAllByDueDateActivationAndStatus(dueDateActivation, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByDateCreatedAndStatus(Date dateCreated, String status) {
        return applicationuserRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public List<ApplicationUserDTO> findAllApplicationUserByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return applicationuserRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByIdUserAndStatus(Long id, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-IdUser-" + id + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = applicationuserRepository.loadMaxIdByIdUserAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_ID + id))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-IdUser-" + id + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByIdUserAndStatus(Long id) {
        return this.findApplicationUserByIdUserAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByIdAndStatus(Long id, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + id + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = applicationuserRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_ID + id))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + id + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByIdAndStatus(Long id) {
        return this.findApplicationUserByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByEmailAndStatus(String email, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + email + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;


        Long maxId = applicationuserRepository.loadMaxIdByEmailAndStatus(email, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_EMAIL + email,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_EMAIL + email))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + email + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByEmailAndStatus(String email) {
        return this.findApplicationUserByEmailAndStatus(email, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + encodedPassPhrase + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;

        Long maxId = applicationuserRepository.loadMaxIdByEncodedPassPhraseAndStatus(encodedPassPhrase, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ENCODEDPASSPHRASE + encodedPassPhrase,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_ENCODEDPASSPHRASE + encodedPassPhrase))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + encodedPassPhrase + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByEncodedPassPhraseAndStatus(String encodedPassPhrase) {
        return this.findApplicationUserByEncodedPassPhraseAndStatus(encodedPassPhrase, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + externalAppUserUUID + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;


        Long maxId = applicationuserRepository.loadMaxIdByExternalAppUserUUIDAndStatus(externalAppUserUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_EXTERNALAPPUSERUUID + externalAppUserUUID,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_EXTERNALAPPUSERUUID + externalAppUserUUID))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + externalAppUserUUID + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByExternalUserUUIDAndStatus(UUID externalUserUUID, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + externalUserUUID + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;


        Long maxId = applicationuserRepository.loadMaxIdByExternalUserUUIDAndStatus(externalUserUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_EXTERNALUSERUUID + externalUserUUID,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_EXTERNALUSERUUID + externalUserUUID))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + externalUserUUID + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByExternalAppUserUUIDAndEmailAndStatus(UUID externalAppUserUUID, String email, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + externalAppUserUUID + email + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;


        Long maxId = applicationuserRepository.loadMaxIdByExternalAppUserUUIDAndEmailAndStatus(externalAppUserUUID, email, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_EXTERNALAPPUSERUUID + externalAppUserUUID,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_EXTERNALAPPUSERUUID + externalAppUserUUID))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + externalAppUserUUID + email + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByExternalAppUserUUIDAndStatus(UUID externalAppUserUUID) {
        return this.findApplicationUserByExternalAppUserUUIDAndStatus(externalAppUserUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByUrlTokenActivationAndStatus(String urlTokenActivation, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + urlTokenActivation + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;


        Long maxId = applicationuserRepository.loadMaxIdByUrlTokenActivationAndStatus(urlTokenActivation, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_URLTOKENACTIVATION + urlTokenActivation,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_URLTOKENACTIVATION + urlTokenActivation))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + urlTokenActivation + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByUrlTokenActivationAndStatus(String urlTokenActivation) {
        return this.findApplicationUserByUrlTokenActivationAndStatus(urlTokenActivation, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByActivationCodeAndStatus(String activationCode, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + activationCode + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;


        Long maxId = applicationuserRepository.loadMaxIdByActivationCodeAndStatus(activationCode, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_ACTIVATIONCODE + activationCode,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_ACTIVATIONCODE + activationCode))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + activationCode + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByActivationCodeAndStatus(String activationCode) {
        return this.findApplicationUserByActivationCodeAndStatus(activationCode, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByDueDateActivationAndStatus(Date dueDateActivation, String status) {

        ApplicationUserDTO cache = redisProvider.getValue("applicationUser-" + dueDateActivation + status,ApplicationUserDTO.class);
        if(Objects.nonNull(cache)) return cache;


        Long maxId = applicationuserRepository.loadMaxIdByDueDateActivationAndStatus(dueDateActivation, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_DUEDATEACTIVATION + dueDateActivation,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_DUEDATEACTIVATION + dueDateActivation))
                );

        ApplicationUserDTO response = applicationuserData.map(this::toDTO).orElse(null);
        if(Objects.nonNull(response)) {
            redisProvider.setValue("applicationUser-" + dueDateActivation + status, gson.toJson(response),120);
        }
        return response;
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByDueDateActivationAndStatus(Date dueDateActivation) {
        return this.findApplicationUserByDueDateActivationAndStatus(dueDateActivation, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = applicationuserRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return applicationuserData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByDateCreatedAndStatus(Date dateCreated) {
        return this.findApplicationUserByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = applicationuserRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<ApplicationUser> applicationuserData =
            Optional.ofNullable( applicationuserRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new ApplicationUserNotFoundException(APPLICATIONUSER_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        APPLICATIONUSER_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return applicationuserData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = ApplicationUserNotFoundException.class
    )
    public ApplicationUserDTO findApplicationUserByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findApplicationUserByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ApplicationUserDTO updateEmailById(Long id, String email) {
        findById(id);
        applicationuserRepository.updateEmailById(id, email);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ApplicationUserDTO updateEncodedPassPhraseById(Long id, String encodedPassPhrase) {
        findById(id);
        applicationuserRepository.updateEncodedPassPhraseById(id, encodedPassPhrase);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ApplicationUserDTO updateExternalAppUserUUIDById(Long id, UUID externalAppUserUUID) {
        findById(id);
        applicationuserRepository.updateExternalAppUserUUIDById(id, externalAppUserUUID);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ApplicationUserDTO updateUrlTokenActivationById(Long id, String urlTokenActivation) {
        findById(id);
        applicationuserRepository.updateUrlTokenActivationById(id, urlTokenActivation);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ApplicationUserDTO updateActivationCodeById(Long id, String activationCode) {
        findById(id);
        applicationuserRepository.updateActivationCodeById(id, activationCode);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public ApplicationUserDTO updateDueDateActivationById(Long id, Date dueDateActivation) {
        findById(id);
        applicationuserRepository.updateDueDateActivationById(id, dueDateActivation);
        return findById(id);
    }


    public ApplicationUserDTO toDTO(ApplicationUser applicationuser) {
        ApplicationUserDTO applicationuserDTO = new ApplicationUserDTO();
        applicationuserDTO.setId(applicationuser.getId());
        applicationuserDTO.setIdApplication(applicationuser.getIdApplication());
        applicationuserDTO.setIdUser(applicationuser.getIdUser());
        applicationuserDTO.setEmail(applicationuser.getEmail());
        applicationuserDTO.setEncodedPassPhrase(applicationuser.getEncodedPassPhrase());
        applicationuserDTO.setExternalAppUserUUID(applicationuser.getExternalAppUserUUID());
        applicationuserDTO.setExternalUserUUID(applicationuser.getExternalUserUUID());
        applicationuserDTO.setUrlTokenActivation(applicationuser.getUrlTokenActivation());
        applicationuserDTO.setActivationCode(applicationuser.getActivationCode());
        applicationuserDTO.setDueDateActivation(applicationuser.getDueDateActivation());
        applicationuserDTO.setStatus(applicationuser.getStatus());
        applicationuserDTO.setDateCreated(applicationuser.getDateCreated());
        applicationuserDTO.setDateUpdated(applicationuser.getDateUpdated());

        return applicationuserDTO;
    }

    public ApplicationUser toEntity(ApplicationUserDTO applicationuserDTO) {
        ApplicationUser applicationuser = new ApplicationUser();
        applicationuser.setId(applicationuserDTO.getId());
        applicationuser.setIdApplication(applicationuserDTO.getIdApplication());
        applicationuser.setIdUser(applicationuserDTO.getIdUser());
        applicationuser.setEmail(applicationuserDTO.getEmail());
        applicationuser.setEncodedPassPhrase(applicationuserDTO.getEncodedPassPhrase());
        applicationuser.setExternalAppUserUUID(applicationuserDTO.getExternalAppUserUUID());
        applicationuser.setExternalUserUUID(applicationuserDTO.getExternalUserUUID());
        applicationuser.setUrlTokenActivation(applicationuserDTO.getUrlTokenActivation());
        applicationuser.setActivationCode(applicationuserDTO.getActivationCode());
        applicationuser.setDueDateActivation(applicationuserDTO.getDueDateActivation());
        applicationuser.setStatus(applicationuserDTO.getStatus());
        applicationuser.setDateCreated(applicationuserDTO.getDateCreated());
        applicationuser.setDateUpdated(applicationuserDTO.getDateUpdated());

        return applicationuser;
    }
}
