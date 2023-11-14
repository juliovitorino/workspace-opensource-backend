package br.com.jcv.security.guardian.service;

import br.com.jcv.commons.library.utility.DateTime;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractGuardianBusinessService {
    @Autowired protected Gson gson;
    @Autowired protected SessionStateService sessionStateService;
    @Autowired protected UsersService usersService;
    @Autowired protected DateTime dateTime;
}
