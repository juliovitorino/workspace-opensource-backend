package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.ProgramTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgramTemplateRepository extends JpaRepository<ProgramTemplate, Long> {

    @Query("select pt from ProgramTemplate pt where pt.version = :version and pt.modality.id = :modalityId and pt.goal.id = :goalId and pt.program.id IN :programList")
    List<ProgramTemplate> findAllProgramTemplateByVersionAndModalityAndGoalAndProgramList(
            Long version,
            Long modalityId,
            Long goalId,
            List<Long> programList);

    @Query(value = "select pt from ProgramTemplate pt where pt.externalId IN (:ids)")
    List<ProgramTemplate> findAllByExternalId(@Param("ids") Iterable<UUID> ids);

}
