package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.User;
import br.com.jcv.treinadorpro.corelayer.model.UserPackTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPackTrainingRepository extends JpaRepository<UserPackTraining, Long> {
    List<UserPackTraining> findByPersonalUser(User personalTrainer);
    Optional<UserPackTraining> findByExternalId(UUID externalId);
}
