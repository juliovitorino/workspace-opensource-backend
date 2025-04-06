package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.ModalityExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalityExerciseRepository extends JpaRepository<ModalityExercise, Long> {
    // Aqui você pode adicionar métodos personalizados se desejar
}
