package br.com.jcv.treinadorpro.corelayer.service;

import br.com.jcv.treinadorpro.corelayer.model.Exercise;
import br.com.jcv.treinadorpro.corelayer.model.Goal;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.PersonalFeature;
import br.com.jcv.treinadorpro.corelayer.model.Program;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.WorkGroup;
import br.com.jcv.treinadorpro.corelayer.request.CreateTrainingPackRequest;
import br.com.jcv.treinadorpro.corelayer.response.ExerciseResponse;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalFeatureResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;
import br.com.jcv.treinadorpro.corelayer.response.WorkGroupResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MapperServiceHelper {


    public static TrainingPack toEntity(CreateTrainingPackRequest request,
                                  User user,
                                  Modality modality) {
        return TrainingPack.builder()
                .externalId(UUID.randomUUID())
                .personalUser(user)
                .modality(modality)
                .description(request.getDescription())
                .durationDays(request.getDurationDays())
                .weeklyFrequency(request.getWeeklyFrequency())
                .notes(request.getNotes())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .status("A")
                .build();
    }


    public static PersonalTrainerResponse toResponse(User user) {
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

    public static PersonalFeatureResponse toResponse(PersonalFeature personalFeature) {
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

    public static ModalityResponse toResponse(Modality modality) {
        return ModalityResponse.builder()
                .id(modality.getId())
                .externalId(modality.getExternalId())
                .nameEn(modality.getNameEn())
                .nameEs(modality.getNameEs())
                .namePt(modality.getNamePt())
                .createdAt(modality.getCreatedAt())
                .updatedAt(modality.getUpdatedAt())
                .status(modality.getStatus())
                .build();
    }

    public static TrainingPackResponse toResponse(TrainingPack trainingPack) {
        return TrainingPackResponse.builder()
                .externalId(trainingPack.getExternalId())
                .personalUser(MapperServiceHelper.toResponse(trainingPack.getPersonalUser()))
                .modality(MapperServiceHelper.toResponse(trainingPack.getModality()))
                .description(trainingPack.getDescription())
                .durationDays(trainingPack.getDurationDays())
                .weeklyFrequency(trainingPack.getWeeklyFrequency())
                .notes(trainingPack.getNotes())
                .price(trainingPack.getPrice())
                .currency(trainingPack.getCurrency())
                .totalStudentsPack(0L)
                .status(trainingPack.getStatus())
                .createdAt(trainingPack.getCreatedAt())
                .updatedAt(trainingPack.getUpdatedAt())
                .build();
    }

    public static ProgramResponse toResponse(Program program) {
        return ProgramResponse.builder()
                .id(program.getId())
                .externalId(program.getExternalId())
                .namePt(program.getNamePt())
                .nameEn(program.getNameEn())
                .nameEs(program.getNameEs())
                .status(program.getStatus())
                .createdAt(program.getCreatedAt())
                .updatedAt(program.getUpdatedAt())
                .build();
    }

    public static GoalResponse toResponse(Goal goal) {
        return GoalResponse.builder()
                .id(goal.getId())
                .externalId(goal.getExternalId())
                .namePt(goal.getNamePt())
                .nameEn(goal.getNameEn())
                .nameEs(goal.getNameEs())
                .status(goal.getStatus())
                .createdAt(goal.getCreatedAt())
                .updatedAt(goal.getUpdatedAt())
                .build();
    }

    public static WorkGroupResponse toResponse(WorkGroup workGroup) {
        return WorkGroupResponse.builder()
                .id(workGroup.getId())
                .externalId(workGroup.getExternalId())
                .namePt(workGroup.getNamePt())
                .nameEn(workGroup.getNameEn())
                .nameEs(workGroup.getNameEs())
                .status(workGroup.getStatus())
                .createdAt(workGroup.getCreatedAt())
                .updatedAt(workGroup.getUpdatedAt())
                .build();
    }
    
    public static ExerciseResponse toResponse(Exercise exercise) {
        return ExerciseResponse.builder()
                .id(exercise.getId())
                .externalId(exercise.getExternalId())
                .namePt(exercise.getNamePt())
                .nameEn(exercise.getNameEn())
                .nameEs(exercise.getNameEs())
                .videoUrlEs(exercise.getVideoUrlEs())
                .videoUrlPt(exercise.getVideoUrlPt())
                .videoUrlEn(exercise.getVideoUrlEn())
                .imageUUID(exercise.getImageUUID())
                .status(exercise.getStatus())
                .createdAt(exercise.getCreatedAt())
                .updatedAt(exercise.getUpdatedAt())
                .build();
    }
}
