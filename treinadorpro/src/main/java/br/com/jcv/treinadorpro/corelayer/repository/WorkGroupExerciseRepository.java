package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.WorkGroupExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkGroupExerciseRepository extends JpaRepository<WorkGroupExercise, Long> {


    @Query(value = "select wge from WorkGroupExercise wge where wge.workGroup.id = :workgroupId and wge.exercise.id = :exerciseId ")
    Optional<WorkGroupExercise> findByWorkGroupIdAndExerciseId(@Param("workgroupId") Long workgroupId,
                                                              @Param("exerciseId") Long exerciseId);
}
