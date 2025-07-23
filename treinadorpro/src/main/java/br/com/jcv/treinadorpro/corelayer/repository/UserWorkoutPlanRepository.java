package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.DistinctUserPlanProjection;
import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserWorkoutPlanRepository extends JpaRepository<UserWorkoutPlan, Long> {

    Optional<UserWorkoutPlan> findByExternalId(UUID externalId);

    @Query(value = "select uwp from UserWorkoutPlan uwp " +
            "where uwp.contract.externalId = :contractExternalId " +
            "and uwp.contract.trainingPack.personalUser.id = :trainerId " +
            "and uwp.status = 'A' " +
            "order by uwp.modality.id, uwp.goal.id, uwp.program.id, uwp.workGroup.id")
    List<UserWorkoutPlan> findAllUserDataSheetPlanByContractExternalIdAndTrainerId(@Param("contractExternalId") UUID contractExternalId,
                                                                                   @Param("trainerId") Long trainerId);

    @Modifying
    @Transactional
    @Query(value = "update user_workout_plan set status = 'I' where contract_id = :contractId "
            , nativeQuery = true
    )
    void disableExercises(@Param("contractId") Long contractId);

    @Query("SELECT DISTINCT " +
            "uwp.modality.id AS modalityId, " +
            "uwp.goal.id AS goalId, " +
            "uwp.program.id AS programId, " +
            "uwp.workGroup.id AS workGroupId " +
            "FROM UserWorkoutPlan uwp " +
            "WHERE uwp.contract.externalId = :contractExternalId " +
            "AND uwp.contract.trainingPack.personalUser.id = :trainerId " +
            "AND uwp.status = 'A' " +
            "ORDER BY uwp.modality.id, uwp.goal.id, uwp.program.id, uwp.workGroup.id")
    List<DistinctUserPlanProjection> findDistinctCombinations(@Param("contractExternalId") UUID contractExternalId,
                                                              @Param("trainerId") Long trainerId);

}
