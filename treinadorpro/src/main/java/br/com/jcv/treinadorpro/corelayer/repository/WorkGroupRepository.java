package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.Goal;
import br.com.jcv.treinadorpro.corelayer.model.WorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkGroupRepository extends JpaRepository<WorkGroup, Long> {
}
