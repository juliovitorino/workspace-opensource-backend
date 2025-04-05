package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // Adicione métodos personalizados aqui se necessário
}
