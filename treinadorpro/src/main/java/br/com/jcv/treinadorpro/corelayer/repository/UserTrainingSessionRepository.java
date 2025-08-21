package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTrainingSessionRepository extends JpaRepository<UserTrainingSession, Long> {

    Optional<UserTrainingSession> findTopByContractExternalIdAndProgressStatusOrderByIdDesc(UUID contractExternalId, String progressStatus);
    Optional<UserTrainingSession> findTopByContractExternalIdAndProgressStatusAndBookingOrderByIdDesc(
            UUID contractExternalId,
            String progressStatus,
            LocalDateTime booking);

    @Query(value = "select uts from UserTrainingSession uts " +
            "where uts.externalId = :externalId and uts.contract.id = :contractId")
    Optional<UserTrainingSession> findByExternalIdAndContractId(@Param("externalId") UUID externalId,
                                                                @Param("contractId") Long contractId);

    @Query(
            value = "select uts from UserTrainingSession uts " +
                    "where uts.contract.externalId = :contractExternalId " +
                    "and (" +
                    "      (  uts.progressStatus = 'BOOKING' and uts.booking between :startDate and :endDate) " +
                    "      OR " +
                    "      ( uts.progressStatus = 'FINISHED' and  uts.startAt between :startDate and :endDate) " +
                    ")"
    )
    List<UserTrainingSession> findTrainingSessionByContractExternalIdAndStartDateAndEndDate(
            @Param("contractExternalId") UUID externalId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Modifying
    @Query("delete from UserTrainingSession uts where uts.externalId = :externalId")
    void deleteUserTrainingSessionByExternalId(@Param("externalId") UUID externalId);

}
