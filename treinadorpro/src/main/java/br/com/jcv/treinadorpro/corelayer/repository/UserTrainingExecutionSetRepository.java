package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.UserTrainingExecutionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTrainingExecutionSetRepository extends JpaRepository<UserTrainingExecutionSet, Long> {

    @Query(nativeQuery = true,
    value = "select utes.set_number as \"setNumber\", utes.reps as \"reps\", utes.weight as \"weight\"  from user_training_execution_set utes " +
            "inner join user_training_session_exercises utse on utse.id = utes.user_training_session_exercises_id " +
            "inner join user_workout_plan uwp on uwp.id = utse.user_workout_plan_id " +
            "inner join exercise e on e.id = uwp.exercise_id " +
            "where e.external_id = :externalId " +
            "order by utes.id desc limit 1")
    Optional<LastLoadProjection> findLastLoadByExerciseExternalId(@Param("externalId") UUID exerciseExternalId);

    @Query(nativeQuery = true,
    value = "select utes.set_number as \"setNumber\", utes.reps as \"reps\", utes.weight as \"weight\" from user_training_execution_set utes " +
            "inner join user_training_session_exercises utse on utse.id = utes.user_training_session_exercises_id " +
            "inner join user_training_session uts on uts.id = utse.user_training_session_id " +
            "inner join contract c on c.id = uts.contract_id " +
            "inner join user_workout_plan uwp on uwp.id = utse.user_workout_plan_id " +
            "where uwp.custom_exercise like :customExercise || '%' " +
            "and c.external_id = :contractExternalId " +
            "order by utes.id desc limit 1;")
    Optional<LastLoadProjection> findLastLoadByCustomExercise(@Param("contractExternalId") UUID contractExternalId,
                                                              @Param("customExercise") String customExercise);
}
