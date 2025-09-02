package br.com.jcv.security.guardian.controller.v1.business.validateapplicationcode;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jcv.commons.library.commodities.enums.GenericStatusEnums;
import br.com.jcv.security.guardian.service.GApplicationService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Deprecated
public class ValidateApplicationCodeBusinessServiceImpl implements ValidateApplicationCodeBusinessService {
    @Autowired
    private GApplicationService gApplicationService;

    @Override
    @Deprecated
    public Boolean execute(UUID processId, UUID uuidExternalApp) {
        return Objects.nonNull(gApplicationService.findGApplicationByExternalCodeUUIDAndStatus(
                uuidExternalApp,
                GenericStatusEnums.ATIVO.getShortValue()));
    }
}
