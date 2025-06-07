package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.model.PersonalFeature;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.response.PersonalFeatureResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.infrastructure.utils.ControllerGenericResponseHelper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindPersonalTrainerServiceImpl implements FindPersonalTrainerService {

    private final UserRepository userRepository;

    public FindPersonalTrainerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ControllerGenericResponse<PersonalTrainerResponse> execute(UUID processId, UUID uuid) {
        User user = userRepository.findByUuidId(uuid)
                .orElseThrow(
                        () -> new CommoditieBaseException("Invalid external id.", HttpStatus.BAD_REQUEST, "MSG-1247")
                );

        return ControllerGenericResponseHelper.getInstance(
                "MSG-1254",
                "Personal Trainer Data retrieved successfully",
                toResponse(user)
        );

    }

    private PersonalTrainerResponse toResponse(User user) {
        return PersonalTrainerResponse.builder()
                .id(user.getId())
                .uuidId(user.getUuidId())
                .name(user.getName())
                .email(user.getEmail())
                .cellphone(user.getCellphone())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .userProfile(user.getUserProfile())
                .urlPhotoProfile(user.getUrlPhotoProfile())
                .masterLanguage(user.getMasterLanguage())
                .lastLogin(user.getLastLogin())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .personalFeature(toResponse(user.getPersonalFeature()))
                .build();
    }

    private PersonalFeatureResponse toResponse(PersonalFeature personalFeature) {
        return PersonalFeatureResponse.builder()
                .id(personalFeature.getId())
                .register(personalFeature.getRegister())
                .place(personalFeature.getPlace())
                .experience(personalFeature.getExperience())
                .specialty(personalFeature.getSpecialty())
                .monPeriod(personalFeature.getMonPeriod())
                .tuePeriod(personalFeature.getTuePeriod())
                .wedPeriod(personalFeature.getWedPeriod())
                .thuPeriod(personalFeature.getThuPeriod())
                .friPeriod(personalFeature.getFriPeriod())
                .satPeriod(personalFeature.getSatPeriod())
                .sunPeriod(personalFeature.getSunPeriod())
                .status(personalFeature.getStatus())
                .createdAt(personalFeature.getCreatedAt())
                .updatedAt(personalFeature.getUpdatedAt())
                .build();
    }
}
