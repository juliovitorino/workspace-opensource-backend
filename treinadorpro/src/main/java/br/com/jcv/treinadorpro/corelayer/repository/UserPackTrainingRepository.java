package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.UserPackTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPackTrainingRepository extends JpaRepository<UserPackTraining, Long> {
    Optional<UserPackTraining> findByExternalId(UUID externalId);

    // distinct student Users based on TrainingPacks created by a specific personal trainer:
    @Query("SELECT DISTINCT upt.studentUser FROM UserPackTraining upt WHERE upt.trainingPack.personalUser.id = :personalUserId")
    List<User> findDistinctStudentsByPersonalTrainerTrainingPacks(@Param("personalUserId") Long personalUserId);
}
