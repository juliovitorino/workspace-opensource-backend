package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.enums.PaymentFrequencyEnum;
import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import br.com.jcv.treinadorpro.corelayer.model.PlanTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanTemplateRepository extends JpaRepository<PlanTemplate, Long> {
    Optional<PlanTemplate> findByDescriptionAndPaymentFrequencyAndStatus(String description, PaymentFrequencyEnum paymentFrequency, String status);

    @Query(value = "select PT from PlanTemplate PT where PT.status = :status")
    List<PlanTemplate> findAllByStatus(@Param("status") String status);

}
