package br.com.jcv.treinadorpro.corebusiness.users;

import br.com.jcv.commons.library.commodities.dto.MensagemResponse;
import br.com.jcv.commons.library.commodities.exception.CommoditieBaseException;
import br.com.jcv.commons.library.commodities.response.ControllerGenericResponse;
import br.com.jcv.treinadorpro.corelayer.enums.MasterLanguageEnum;
import br.com.jcv.treinadorpro.corelayer.mapper.UserMapper;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.repository.UserRepository;
import br.com.jcv.treinadorpro.corelayer.request.StudentProfileRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EditStudentProfileServiceImpl implements EditStudentProfileService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public EditStudentProfileServiceImpl(UserRepository userRepository,
                                         ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ControllerGenericResponse<Boolean> execute(UUID processId, StudentProfileRequest studentProfileRequest) {

        User userProfile = userRepository.findByUuidId(studentProfileRequest.getUuidId())
                .orElseThrow(() -> new CommoditieBaseException("Invalid profile", HttpStatus.BAD_REQUEST, "MSG-1442"));

        getData(userProfile, studentProfileRequest);
        userRepository.save(userProfile);


        ControllerGenericResponse<Boolean> response = new ControllerGenericResponse<>();
        response.setResponse(MensagemResponse.builder()
                .msgcode("MSG-0001")
                .mensagem("Profile has been updated")
                .build());

        return response;
    }

    public void getData(User userEntity, StudentProfileRequest request) {
        userEntity.setName(request.getName());
        userEntity.setCellphone(request.getCellphone());
        userEntity.setBirthday(request.getBirthday());
        userEntity.setGender(request.getGender());
        userEntity.setUrlPhotoProfile(request.getUrlPhotoProfile());

        MasterLanguageEnum languageByString = MasterLanguageEnum.findByString(request.getMasterLanguage());
        if(languageByString != null)
            userEntity.setMasterLanguage(languageByString.getLanguage());

        userEntity.getStudentFeature().setHeight(request.getHeight());
        userEntity.getStudentFeature().setWeight(request.getWeight());
        userEntity.getStudentFeature().setWeightUnit(request.getWeightUnit());
    }
}
