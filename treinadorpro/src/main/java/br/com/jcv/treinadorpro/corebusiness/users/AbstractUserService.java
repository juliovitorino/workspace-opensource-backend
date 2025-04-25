package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public abstract class AbstractUserService {

    private final UserRepository userRepository;

    protected AbstractUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected void checkExistingEmail(RegisterRequest registerRequest){
        Optional<User> userByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (userByEmail.isPresent()) {
            throw new CommoditieBaseException("Email has already exist!", HttpStatus.BAD_REQUEST,"MSG-1922");
        }

    }
}
