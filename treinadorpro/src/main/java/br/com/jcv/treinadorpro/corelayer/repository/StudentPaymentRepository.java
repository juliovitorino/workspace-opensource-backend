package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Long> {

    @Query("select sum(c.amount) from StudentPayment c " +
    "where c.paymentDate IS NULL " +
    "and c.duedate < CURRENT_TIMESTAMP " +
    "and c.contract.trainingPack.personalUser.id = :personalId")
    BigDecimal sumOverduePayments(@Param("personalId") Long personalId);
}
