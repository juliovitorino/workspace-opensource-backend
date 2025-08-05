package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.UserTrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTrainingSessionRepository extends JpaRepository<UserTrainingSession, Long> {

    Optional<UserTrainingSession> findTopByContractExternalIdOrderByIdDesc(UUID contractExternalId);

}
