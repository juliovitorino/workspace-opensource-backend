package br.com.jcv.security.guardian.controller.v1.business.createaccount;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.commons.library.utility.DateUtility;
import br.com.jcv.commons.library.utility.StringUtility;
import br.com.jcv.security.guardian.dto.UsersDTO;
import br.com.jcv.security.guardian.service.AbstractGuardianBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CreateNewAccountBusinessService extends AbstractGuardianBusinessService implements CreateNewAccount{
    @Override
    public CreateNewAccountResponse execute(UUID processId, CreateNewAccountRequest request) {
        log.info("execute :: processId = {} :: has been started", processId);
        String md5Hex = DigestUtils.md5Hex(request.getPasswd()).toUpperCase();

        UsersDTO usersDTO = mapperToDto(request);
        usersDTO.setIdUserUUID(UUID.randomUUID());
        usersDTO.setEncodedPassPhrase(md5Hex);
        usersDTO.setActivationCode(StringUtility.getRandomCodeNumber(6));
        usersDTO.setDueDateActivation(DateUtility.addDays(dateTime.getToday(),1));
        usersDTO.setUrlTokenActivation(StringUtility.getRandomCodeStringUpperLower(32));
        UsersDTO savedAccount = usersService.salvar(usersDTO);

        log.info("execute :: processId = {} :: has been sent an email for {}", processId, request.getEmail());
        // TODO send email for the new account and it wait for user confirmation

        log.info("execute :: processId = {} :: has been finished successfully", processId);
        MensagemResponse response = MensagemResponse.builder()
                .msgcode("GRDN-1933")
                .mensagem("Your account has been created successfully, but it's depending on confirmation. A 6-digit code for activation has been sent to " + request.getEmail() + ". Check it out!")
                .build();
        return CreateNewAccountResponse.builder()
                .response(response)
                .build();
    }

    private UsersDTO mapperToDto(CreateNewAccountRequest request) {
        UsersDTO dto = new UsersDTO();
        dto.setBirthday(request.getBirthday());
        dto.setName(request.getName());
        dto.setEmail(request.getEmail());
        return dto;
    }
}
