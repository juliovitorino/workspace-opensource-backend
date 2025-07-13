package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.enums.SituationEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByExternalId(UUID externalId);

    // distinct student Users based on TrainingPacks created by a specific personal trainer:
    @Query("SELECT DISTINCT upt.studentUser FROM Contract upt WHERE upt.trainingPack.personalUser.id = :personalUserId")
    List<User> findDistinctStudentsByPersonalTrainerTrainingPacks(@Param("personalUserId") Long personalUserId);

    @Query("SELECT C from Contract C WHERE C.trainingPack.personalUser.id = :personalUserId and C.status = :status")
    List<Contract> findAllContracts(@Param("personalUserId") Long personalUserId,
                                    @Param("status") StatusEnum status);


    @Query("SELECT COUNT(c) FROM Contract c " +
            "WHERE c.situation = :situation " +
            "and c.status = :status " +
            "and c.trainingPack.personalUser.id = :personalId")
    long countOpenAndActiveContracts(@Param("situation") SituationEnum situation,
                                     @Param("status") StatusEnum status,
                                     @Param("personalId") Long personalId);

}
