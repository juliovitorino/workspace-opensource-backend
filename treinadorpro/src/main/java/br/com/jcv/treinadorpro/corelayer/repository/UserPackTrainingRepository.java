package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.UserPackTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPackTrainingRepository extends JpaRepository<UserPackTraining, Long> {
}
