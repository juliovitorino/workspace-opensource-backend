package br.com.jcv.treinadorpro.corelayer.service;

import br.com.jcv.treinadorpro.corebusiness.plantemplate.PlanTemplateResponse;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import br.com.jcv.treinadorpro.corelayer.model.Exercise;
import br.com.jcv.treinadorpro.corelayer.model.Goal;
import br.com.jcv.treinadorpro.corelayer.model.Modality;
import br.com.jcv.treinadorpro.corelayer.model.PersonalFeature;
import br.com.jcv.treinadorpro.corelayer.model.PlanTemplate;
import br.com.jcv.treinadorpro.corelayer.model.Program;
import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import br.com.jcv.treinadorpro.corelayer.model.StudentPaymentsTransaction;
import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingExecutionSet;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSession;
import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSessionExercise;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutPlan;
import br.com.jcv.treinadorpro.corelayer.model.WorkGroup;
import br.com.jcv.treinadorpro.corelayer.request.CreateTrainingPackRequest;
import br.com.jcv.treinadorpro.corelayer.request.TrainingSessionRequest;
import br.com.jcv.treinadorpro.corelayer.request.UserWorkoutPlanRequest;
import br.com.jcv.treinadorpro.corelayer.response.ContractResponse;
import br.com.jcv.treinadorpro.corelayer.response.ExerciseResponse;
import br.com.jcv.treinadorpro.corelayer.response.GoalResponse;
import br.com.jcv.treinadorpro.corelayer.response.ModalityResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalFeatureResponse;
import br.com.jcv.treinadorpro.corelayer.response.PersonalTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.response.ProgramResponse;
import br.com.jcv.treinadorpro.corelayer.response.StudentPaymentResponse;
import br.com.jcv.treinadorpro.corelayer.response.StudentPaymentsTransactionResponse;
import br.com.jcv.treinadorpro.corelayer.response.StudentResponse;
import br.com.jcv.treinadorpro.corelayer.response.StudentsFromTrainerResponse;
import br.com.jcv.treinadorpro.corelayer.response.TrainingPackResponse;
import br.com.jcv.treinadorpro.corelayer.response.UserTrainingExecutionSetResponse;
import br.com.jcv.treinadorpro.corelayer.response.WorkGroupResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public static StudentResponse toStudentResponse(User user) {
        return new StudentResponse(
                user.getId(),
                user.getUuidId(),
                user.getName(),
                user.getEmail(),
                user.getCellphone(),
                user.getBirthday(),
                user.getGender(),
                user.getUrlPhotoProfile(),
                user.getUserProfile(),
                user.getMasterLanguage(),
                user.getStatus(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


    public static PersonalTrainerResponse toResponse(User user) {
        return new PersonalTrainerResponse(
                user.getId(),
                user.getUuidId(),
                user.getName(),
                user.getEmail(),
                user.getCellphone(),
                user.getBirthday(),
                user.getGender(),
                user.getUrlPhotoProfile(),
                user.getUserProfile(),
                user.getMasterLanguage(),
                user.getStatus(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                toResponse(user.getPersonalFeature())
        );
    }


    public static PersonalFeatureResponse toResponse(PersonalFeature personalFeature) {
        if (personalFeature == null) return null;
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
                .personalUser(toResponse(trainingPack.getPersonalUser()))
                .modality(toResponse(trainingPack.getModality()))
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
        if (exercise == null) return null;
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

    public static PlanTemplateResponse toResponse(PlanTemplate planTemplate) {
        return PlanTemplateResponse.builder()
                .id(planTemplate.getId())
                .externalId(planTemplate.getExternalId())
                .amountDiscount(planTemplate.getAmountDiscount())
                .description(planTemplate.getDescription())
                .paymentFrequency(planTemplate.getPaymentFrequency())
                .price(planTemplate.getPrice())
                .qtyUserStudentAllowed(planTemplate.getQtyUserStudentAllowed())
                .qtyContractAllowed(planTemplate.getQtyContractAllowed())
                .status(planTemplate.getStatus())
                .createdAt(planTemplate.getCreatedAt())
                .updatedAt(planTemplate.getUpdatedAt())
                .build();
    }

    public static StudentsFromTrainerResponse toResponseStudents(User user) {
        return StudentsFromTrainerResponse.builder()
                .externalId(user.getUuidId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getCellphone())
                .build();
    }
    public static StudentPaymentResponse toResponse(StudentPayment studentPayment) {
        return toResponse(studentPayment, true);
    }

    public static StudentPaymentResponse toResponse(StudentPayment studentPayment, Boolean isDeep) {
        StudentPaymentResponse studentPaymentResponse = StudentPaymentResponse.builder()
                .externalId(studentPayment.getExternalId())
                .contract(toResponse(studentPayment.getContract()))
                .amount(studentPayment.getAmount())
                .dueDate(studentPayment.getDuedate())
                .status(studentPayment.getStatus())
                .createdAt(studentPayment.getCreatedAt())
                .updatedAt(studentPayment.getUpdatedAt())
                .build();
        if(isDeep){
            studentPaymentResponse.setStudentPaymentsTransactions(
                    studentPayment.getStudentPaymentsTransactions()
                    .stream()
                    .map(MapperServiceHelper::toResponse)
                    .collect(Collectors.toList()));
        }
        return studentPaymentResponse;
    }

    public static StudentPaymentsTransactionResponse toResponse(StudentPaymentsTransaction studentPaymentsTransaction) {
        return StudentPaymentsTransactionResponse.builder()
                .id(studentPaymentsTransaction.getId())
                .externalId(studentPaymentsTransaction.getExternalId())
                .studentPayment(toResponse(studentPaymentsTransaction.getStudentPayment(), false))
                .paymentDate(studentPaymentsTransaction.getPaymentDate())
                .paymentMethod(studentPaymentsTransaction.getPaymentMethod())
                .comment(studentPaymentsTransaction.getComment())
                .receivedAmount(studentPaymentsTransaction.getReceivedAmount())
                .status(studentPaymentsTransaction.getStatus())
                .createdAt(studentPaymentsTransaction.getCreatedAt())
                .updatedAt(studentPaymentsTransaction.getUpdatedAt())
                .build();
    }

    public static ContractResponse toResponse(Contract contract) {
        return ContractResponse.builder()
                .externalId(contract.getExternalId())
                .trainingPack(toResponse(contract.getTrainingPack()))
                .studentUser(toStudentResponse(contract.getStudentUser()))
                .description(contract.getDescription())
                .workoutSite(contract.getWorkoutSite())
                .price(contract.getPrice())
                .currency(contract.getCurrency())
                .monday(contract.getMonday())
                .tuesday(contract.getTuesday())
                .wednesday(contract.getWednesday())
                .thursday(contract.getThursday())
                .friday(contract.getFriday())
                .saturday(contract.getSaturday())
                .sunday(contract.getSunday())
                .duration(contract.getDuration())
                .status(contract.getStatus())
                .createdAt(contract.getCreatedAt())
                .updatedAt(contract.getUpdatedAt())
                .build();
    }

    public static UserWorkoutPlanRequest toResponse(UserWorkoutPlan item) {
        return UserWorkoutPlanRequest.builder()
                .externalId(item.getExternalId())
                .contract(MapperServiceHelper.toResponse(item.getContract()))
                .modality(MapperServiceHelper.toResponse(item.getModality()))
                .goal(MapperServiceHelper.toResponse(item.getGoal()))
                .program(MapperServiceHelper.toResponse(item.getProgram()))
                .workGroup(MapperServiceHelper.toResponse(item.getWorkGroup()))
                .exercise(MapperServiceHelper.toResponse(item.getExercise()))
                .customExercise(item.getCustomExercise())
                .customProgram(item.getCustomProgram())
                .executionMethod(item.getExecutionMethod())
                .qtySeries(item.getQtySeries())
                .qtyReps(item.getQtyReps())
                .execution(item.getExecution())
                .executionTime(item.getExecutionTime())
                .restTime(item.getRestTime())
                .comments(item.getComments())
                .control(UUID.randomUUID().toString())
                .obs(item.getObs())
                .status(item.getStatus())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public static TrainingSessionRequest toResponse(UserTrainingSession userTrainingSession) {
        if (userTrainingSession == null) return null;
        return TrainingSessionRequest.builder()
                .externalId(userTrainingSession.getExternalId())
                .contract(MapperServiceHelper.toResponse(userTrainingSession.getContract()))
                .booking(userTrainingSession.getBooking())
                .startAt(userTrainingSession.getStartAt())
                .finishedAt(userTrainingSession.getFinishedAt())
                .elapsedTime(userTrainingSession.getElapsedTime())
                .progressStatus(userTrainingSession.getProgressStatus())
                .syncStatus(userTrainingSession.getSyncStatus())
                .comments((userTrainingSession.getComments()))
                .status(userTrainingSession.getStatus())
                .userWorkoutPlanList(MapperServiceHelper.toResponseExercise(userTrainingSession.getExercises()))
                .build();
    }

    private static List<UserWorkoutPlanRequest> toResponseExercise(List<UserTrainingSessionExercise> exercises) {
        return exercises.stream()
                .map((e) -> UserWorkoutPlanRequest.builder()
                        .externalId(e.getUserWorkoutPlan().getExternalId())
                        .contract(MapperServiceHelper.toResponse(e.getUserWorkoutPlan().getContract()))
                        .modality(MapperServiceHelper.toResponse(e.getUserWorkoutPlan().getModality()))
                        .goal(MapperServiceHelper.toResponse(e.getUserWorkoutPlan().getGoal()))
                        .program(MapperServiceHelper.toResponse(e.getUserWorkoutPlan().getProgram()))
                        .workGroup(MapperServiceHelper.toResponse(e.getUserWorkoutPlan().getWorkGroup()))
                        .exercise(MapperServiceHelper.toResponse(e.getUserWorkoutPlan().getExercise()))
                        .customExercise(e.getUserWorkoutPlan().getCustomExercise())
                        .customProgram(e.getUserWorkoutPlan().getCustomProgram())
                        .executionMethod(e.getUserWorkoutPlan().getExecutionMethod())
                        .qtySeries(e.getUserWorkoutPlan().getQtySeries())
                        .qtyReps(e.getUserWorkoutPlan().getQtyReps())
                        .execution(e.getUserWorkoutPlan().getExecution())
                        .executionTime(e.getUserWorkoutPlan().getExecutionTime())
                        .restTime(e.getUserWorkoutPlan().getRestTime())
                        .comments(e.getUserWorkoutPlan().getComments())
                        .obs(e.getUserWorkoutPlan().getObs())
                        .control(UUID.randomUUID().toString())
                        .status(e.getUserWorkoutPlan().getStatus())
                        .createdAt(e.getUserWorkoutPlan().getCreatedAt())
                        .updatedAt(e.getUserWorkoutPlan().getUpdatedAt())
                        .userExecutionSetList(MapperServiceHelper.toResponse(e.getExecutionSets()))
                        .build()).collect(Collectors.toList());
    }

    private static List<UserTrainingExecutionSetResponse> toResponse(List<UserTrainingExecutionSet> trainingExecutionSets) {
        return trainingExecutionSets.stream()
                .map(set -> UserTrainingExecutionSetResponse.builder()
                        .externalId(set.getExternalId())
                        .userWorkPlan(MapperServiceHelper.toResponse(set.getUserTrainingSessionExercise().getUserWorkoutPlan()))
                        .startedAt(set.getStartedAt())
                        .finishedAt(set.getFinishedAt())
                        .setNumber(set.getSetNumber())
                        .reps(set.getReps())
                        .weight(set.getWeight())
                        .weightUnit(set.getWeightUnit())
                        .elapsedTime(set.getElapsedTime())
                        .status(set.getStatus())
                        .createdAt(set.getCreatedAt())
                        .updatedAt(set.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
