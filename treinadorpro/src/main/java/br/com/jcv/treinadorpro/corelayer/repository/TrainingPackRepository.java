package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.TrainingPack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingPackRepository extends JpaRepository<TrainingPack, Long> {

    @Query(value = "Select TP from TrainingPack TP where personalUser.id = :id")
    Page<TrainingPack> findAllByPersonalUserId(@Param("id") Long Id, Pageable page);
}
