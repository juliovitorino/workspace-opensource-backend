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

import br.com.jcv.commons.library.commodities.constantes.GenericConstantes;
import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.commodities.dto.RequestFilter;
import br.com.jcv.commons.library.utility.DateTime;

import br.com.jcv.security.guardian.dto.UsersDTO;
import br.com.jcv.security.guardian.model.Users;
import br.com.jcv.security.guardian.constantes.UsersConstantes;
import br.com.jcv.security.guardian.repository.UsersRepository;
import br.com.jcv.security.guardian.service.UsersService;
import br.com.jcv.security.guardian.exception.UsersNotFoundException;

import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.extern.slf4j.Slf4j;


/**
* UsersServiceImpl - Implementation for Users interface
*
* @author Users
* @since Tue Nov 14 19:09:15 BRT 2023
*/


@Slf4j
@Service
public class UsersServiceImpl implements UsersService
{
    private static final String USERS_NOTFOUND_WITH_ID = "Users não encontrada com id = ";
    private static final String USERS_NOTFOUND_WITH_NAME = "Users não encontrada com name = ";
    private static final String USERS_NOTFOUND_WITH_EMAIL = "Users não encontrada com email = ";
    private static final String USERS_NOTFOUND_WITH_ENCODEDPASSPHRASE = "Users não encontrada com encodedPassPhrase = ";
    private static final String USERS_NOTFOUND_WITH_IDUSERUUID = "Users não encontrada com idUserUUID = ";
    private static final String USERS_NOTFOUND_WITH_BIRTHDAY = "Users não encontrada com birthday = ";
    private static final String USERS_NOTFOUND_WITH_STATUS = "Users não encontrada com status = ";
    private static final String USERS_NOTFOUND_WITH_DATECREATED = "Users não encontrada com dateCreated = ";
    private static final String USERS_NOTFOUND_WITH_DATEUPDATED = "Users não encontrada com dateUpdated = ";


    @Autowired private UsersRepository usersRepository;
    @Autowired private DateTime dateTime;

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UsersNotFoundException.class
    )
    public void delete(Long id) {
        log.info("Deletando Users com id = {}", id);
        usersRepository.findById(id)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_ID  + id));
        usersRepository.deleteById(id);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO salvar(UsersDTO usersDTO) {
        Date now = dateTime.getToday();
        if(Objects.nonNull(usersDTO.getId()) && usersDTO.getId() != 0) {
            usersDTO.setDateUpdated(now);
        } else {
            usersDTO.setStatus(GenericStatusEnums.PENDENTE.getShortValue());
            usersDTO.setDateCreated(now);
            usersDTO.setDateUpdated(now);
        }
        return this.toDTO(usersRepository.save(this.toEntity(usersDTO)));
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findById(Long id) {
        Optional<Users> usersData =
            Optional.ofNullable(usersRepository.findById(id)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    USERS_NOTFOUND_WITH_ID  + id ))
                );

        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UsersNotFoundException.class
    )
    public boolean partialUpdate(Long id, Map<String, Object> updates) {

        Optional<Users> usersData =
            Optional.ofNullable(usersRepository.findById(id)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_ID  + id,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_ID  + id))
                    );
        if (usersData.isPresent()) {
            Users users = usersData.get();

            for (Map.Entry<String,Object> entry : updates.entrySet()) {
                if(entry.getKey().equalsIgnoreCase(UsersConstantes.NAME)) users.setName((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(UsersConstantes.EMAIL)) users.setEmail((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(UsersConstantes.ENCODEDPASSPHRASE)) users.setEncodedPassPhrase((String)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(UsersConstantes.IDUSERUUID)) users.setIdUserUUID((UUID)entry.getValue());
                if(entry.getKey().equalsIgnoreCase(UsersConstantes.BIRTHDAY)) users.setBirthday((LocalDate)entry.getValue());

        }
        if(updates.get(UsersConstantes.DATEUPDATED) == null) users.setDateUpdated(new Date());
        usersRepository.save(users);
        return true;
    }
        return false;
    }




    @Override
    @Transactional(transactionManager="transactionManager",
        propagation = Propagation.REQUIRED,
        rollbackFor = Throwable.class,
        noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO updateStatusById(Long id, String status) {
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository.findById(id)
                .orElseThrow(() -> new UsersNotFoundException(USERS_NOTFOUND_WITH_ID  + id,
                    HttpStatus.NOT_FOUND,
                    USERS_NOTFOUND_WITH_ID + id))
                );
        Users users = usersData.orElseGet(Users::new);
        users.setStatus(status);
        users.setDateUpdated(new Date());
        return toDTO(usersRepository.save(users));

    }

    @Override
    public List<UsersDTO> findAllByStatus(String status) {
        return usersRepository.findAllByStatus(status)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

@Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
)
public Map<String, Object> findPageByFilter(RequestFilter filtro) {
    List<Users> lstUsers;
    Long id = null;
    String name = null;
    String email = null;
    String encodedPassPhrase = null;
    UUID idUserUUID = null;
    String birthday = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;


    for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.EMAIL)) email = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.ENCODEDPASSPHRASE)) encodedPassPhrase = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.IDUSERUUID)) idUserUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.BIRTHDAY)) birthday = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

    }

    Pageable paging = PageRequest.of(filtro.getPagina(), filtro.getQtdeRegistrosPorPagina());
    Page<Users> paginaUsers = usersRepository.findUsersByFilter(paging,
        id
        ,name
        ,email
        ,encodedPassPhrase
        ,idUserUUID
        ,birthday
        ,status
        ,dateCreated
        ,dateUpdated

    );

    lstUsers = paginaUsers.getContent();
    Map<String,Object> response = new HashMap<>();
    response.put("currentPage", paginaUsers.getNumber());
    response.put("totalItems", paginaUsers.getTotalElements());
    response.put("totalPages", paginaUsers.getTotalPages());
    response.put("pageUsersItems", lstUsers.stream().map(this::toDTO).collect(Collectors.toList()));
    return response;
}


    @Override
@Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
)
    public List<UsersDTO> findAllByFilter(RequestFilter filtro) {
    Long id = null;
    String name = null;
    String email = null;
    String encodedPassPhrase = null;
    UUID idUserUUID = null;
    String birthday = null;
    String status = null;
    String dateCreated = null;
    String dateUpdated = null;

        for (Map.Entry<String,Object> entry : filtro.getCamposFiltro().entrySet()) {
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.ID)) id = Objects.isNull(entry.getValue()) ? null : Long.valueOf(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.NAME)) name = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.EMAIL)) email = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.ENCODEDPASSPHRASE)) encodedPassPhrase = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.IDUSERUUID)) idUserUUID = Objects.isNull(entry.getValue()) ? null : UUID.fromString(entry.getValue().toString());
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.BIRTHDAY)) birthday = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.STATUS)) status = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.DATECREATED)) dateCreated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();
        if(entry.getKey().equalsIgnoreCase(UsersConstantes.DATEUPDATED)) dateUpdated = Objects.isNull(entry.getValue()) ? null : entry.getValue().toString();

        }

        List<Users> lstUsers = usersRepository.findUsersByFilter(
            id
            ,name
            ,email
            ,encodedPassPhrase
            ,idUserUUID
            ,birthday
            ,status
            ,dateCreated
            ,dateUpdated

        );

        return lstUsers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public List<UsersDTO> findAllUsersByIdAndStatus(Long id, String status) {
        return usersRepository.findAllByIdAndStatus(id, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public List<UsersDTO> findAllUsersByNameAndStatus(String name, String status) {
        return usersRepository.findAllByNameAndStatus(name, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public List<UsersDTO> findAllUsersByEmailAndStatus(String email, String status) {
        return usersRepository.findAllByEmailAndStatus(email, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public List<UsersDTO> findAllUsersByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status) {
        return usersRepository.findAllByEncodedPassPhraseAndStatus(encodedPassPhrase, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public List<UsersDTO> findAllUsersByIdUserUUIDAndStatus(UUID idUserUUID, String status) {
        return usersRepository.findAllByIdUserUUIDAndStatus(idUserUUID, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public List<UsersDTO> findAllUsersByBirthdayAndStatus(LocalDate birthday, String status) {
        return usersRepository.findAllByBirthdayAndStatus(birthday, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public List<UsersDTO> findAllUsersByDateCreatedAndStatus(Date dateCreated, String status) {
        return usersRepository.findAllByDateCreatedAndStatus(dateCreated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public List<UsersDTO> findAllUsersByDateUpdatedAndStatus(Date dateUpdated, String status) {
        return usersRepository.findAllByDateUpdatedAndStatus(dateUpdated, status).stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByIdAndStatus(Long id, String status) {
        Long maxId = usersRepository.loadMaxIdByIdAndStatus(id, status);
        if(maxId == null) maxId = 0L;
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_ID + id,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_ID + id))
                );
        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByIdAndStatus(Long id) {
        return this.findUsersByIdAndStatus(id, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByNameAndStatus(String name, String status) {
        Long maxId = usersRepository.loadMaxIdByNameAndStatus(name, status);
        if(maxId == null) maxId = 0L;
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_NAME + name,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_NAME + name))
                );
        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByNameAndStatus(String name) {
        return this.findUsersByNameAndStatus(name, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByEmailAndStatus(String email, String status) {
        Long maxId = usersRepository.loadMaxIdByEmailAndStatus(email, status);
        if(maxId == null) maxId = 0L;
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_EMAIL + email,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_EMAIL + email))
                );
        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByEmailAndStatus(String email) {
        return this.findUsersByEmailAndStatus(email, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByEncodedPassPhraseAndStatus(String encodedPassPhrase, String status) {
        Long maxId = usersRepository.loadMaxIdByEncodedPassPhraseAndStatus(encodedPassPhrase, status);
        if(maxId == null) maxId = 0L;
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_ENCODEDPASSPHRASE + encodedPassPhrase,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_ENCODEDPASSPHRASE + encodedPassPhrase))
                );
        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByEncodedPassPhraseAndStatus(String encodedPassPhrase) {
        return this.findUsersByEncodedPassPhraseAndStatus(encodedPassPhrase, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByIdUserUUIDAndStatus(UUID idUserUUID, String status) {
        Long maxId = usersRepository.loadMaxIdByIdUserUUIDAndStatus(idUserUUID, status);
        if(maxId == null) maxId = 0L;
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_IDUSERUUID + idUserUUID,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_IDUSERUUID + idUserUUID))
                );
        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByIdUserUUIDAndStatus(UUID idUserUUID) {
        return this.findUsersByIdUserUUIDAndStatus(idUserUUID, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByBirthdayAndStatus(LocalDate birthday, String status) {
        Long maxId = usersRepository.loadMaxIdByBirthdayAndStatus(birthday, status);
        if(maxId == null) maxId = 0L;
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_BIRTHDAY + birthday,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_BIRTHDAY + birthday))
                );
        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByBirthdayAndStatus(LocalDate birthday) {
        return this.findUsersByBirthdayAndStatus(birthday, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByDateCreatedAndStatus(Date dateCreated, String status) {
        Long maxId = usersRepository.loadMaxIdByDateCreatedAndStatus(dateCreated, status);
        if(maxId == null) maxId = 0L;
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_DATECREATED + dateCreated,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_DATECREATED + dateCreated))
                );
        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByDateCreatedAndStatus(Date dateCreated) {
        return this.findUsersByDateCreatedAndStatus(dateCreated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByDateUpdatedAndStatus(Date dateUpdated, String status) {
        Long maxId = usersRepository.loadMaxIdByDateUpdatedAndStatus(dateUpdated, status);
        if(maxId == null) maxId = 0L;
        Optional<Users> usersData =
            Optional.ofNullable( usersRepository
                .findById(maxId)
                .orElseThrow(
                    () -> new UsersNotFoundException(USERS_NOTFOUND_WITH_DATEUPDATED + dateUpdated,
                        HttpStatus.NOT_FOUND,
                        USERS_NOTFOUND_WITH_DATEUPDATED + dateUpdated))
                );
        return usersData.map(this::toDTO).orElse(null);
    }

    @Override
    @Transactional(transactionManager="transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class,
    noRollbackFor = UsersNotFoundException.class
    )
    public UsersDTO findUsersByDateUpdatedAndStatus(Date dateUpdated) {
        return this.findUsersByDateUpdatedAndStatus(dateUpdated, GenericStatusEnums.ATIVO.getShortValue());
    }

    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UsersDTO updateNameById(Long id, String name) {
        findById(id);
        usersRepository.updateNameById(id, name);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UsersDTO updateEmailById(Long id, String email) {
        findById(id);
        usersRepository.updateEmailById(id, email);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UsersDTO updateEncodedPassPhraseById(Long id, String encodedPassPhrase) {
        findById(id);
        usersRepository.updateEncodedPassPhraseById(id, encodedPassPhrase);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UsersDTO updateIdUserUUIDById(Long id, UUID idUserUUID) {
        findById(id);
        usersRepository.updateIdUserUUIDById(id, idUserUUID);
        return findById(id);
    }
    @Override
    @Transactional(
    transactionManager = "transactionManager",
    propagation = Propagation.REQUIRED,
    rollbackFor = Throwable.class)
    public UsersDTO updateBirthdayById(Long id, LocalDate birthday) {
        findById(id);
        usersRepository.updateBirthdayById(id, birthday);
        return findById(id);
    }


    public UsersDTO toDTO(Users users) {
        UsersDTO usersDTO = new UsersDTO();
                usersDTO.setId(users.getId());
                usersDTO.setName(users.getName());
                usersDTO.setEmail(users.getEmail());
                usersDTO.setEncodedPassPhrase(users.getEncodedPassPhrase());
                usersDTO.setIdUserUUID(users.getIdUserUUID());
                usersDTO.setBirthday(users.getBirthday());
                usersDTO.setActivationCode(users.getActivationCode());
                usersDTO.setDueDateActivation(users.getDueDateActivation());
                usersDTO.setUrlTokenActivation(users.getUrlTokenActivation());
                usersDTO.setStatus(users.getStatus());
                usersDTO.setDateCreated(users.getDateCreated());
                usersDTO.setDateUpdated(users.getDateUpdated());
        return usersDTO;
    }

    public Users toEntity(UsersDTO usersDTO) {
        Users users = new Users();
                    users.setId(usersDTO.getId());
                    users.setName(usersDTO.getName());
                    users.setEmail(usersDTO.getEmail());
                    users.setEncodedPassPhrase(usersDTO.getEncodedPassPhrase());
                    users.setIdUserUUID(usersDTO.getIdUserUUID());
                    users.setBirthday(usersDTO.getBirthday());
                    users.setActivationCode(usersDTO.getActivationCode());
                    users.setDueDateActivation(usersDTO.getDueDateActivation());
                    users.setUrlTokenActivation(usersDTO.getUrlTokenActivation());
                    users.setStatus(usersDTO.getStatus());
                    users.setDateCreated(usersDTO.getDateCreated());
                    users.setDateUpdated(usersDTO.getDateUpdated());
        return users;
    }
}
