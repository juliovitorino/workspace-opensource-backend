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

    @Query("SELECT c FROM Contract c " +
            "WHERE c.externalId  = :externalId " +
            "and c.trainingPack.personalUser.id = :personalId")
    Optional<Contract> findByExternalIdAndPersonalId(@Param("externalId") UUID externalId,
                                                     @Param("personalId") Long personalId);

    @Query(value = "select count(*) " +
            "from contract c  " +
            "inner join training_pack tp on tp.id = c.pack_training_id  " +
            "where ( " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 0 AND sunday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 1 AND monday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 2 AND tuesday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 3 AND wednesday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 4 AND thursday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 5 AND friday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 6 AND saturday IS NOT NULL) " +
            ") " +
            "and c.situation = :situation  " +
            "and c.status = :status  " +
            "and tp.personal_user_id = :personalId"
            , nativeQuery = true)
    long countTodayWorkout(@Param("situation") String situation,
                           @Param("status") String status,
                           @Param("personalId") Long personalId);


    @Query(value = "select * " +
            "from contract c  " +
            "inner join training_pack tp on tp.id = c.pack_training_id  " +
            "where ( " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 0 AND sunday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 1 AND monday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 2 AND tuesday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 3 AND wednesday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 4 AND thursday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 5 AND friday IS NOT NULL) OR " +
            "    (EXTRACT(DOW FROM CURRENT_DATE) = 6 AND saturday IS NOT NULL) " +
            ") " +
            "and c.situation = :situation  " +
            "and c.status = :status  " +
            "and tp.personal_user_id = :personalId " +
            "order by case extract(dow from current_date) " +
            "    when 0 then sunday " +
            "    when 1 then monday " +
            "    when 2 then tuesday " +
            "    when 3 then wednesday " +
            "    when 4 then thursday " +
            "    when 5 then friday " +
            "    when 6 then saturday " +
            "end", nativeQuery = true)
    List<Contract> findAllContractTodayWorkout(@Param("situation") String situation,
                                               @Param("status") String status,
                                               @Param("personalId") Long personalId);

}
