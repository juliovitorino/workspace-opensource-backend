package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import br.com.jcv.treinadorpro.corelayer.model.StudentPaymentsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StudentPaymentsTransactionRepository extends JpaRepository<StudentPaymentsTransaction, Long> {

    @Query("select sum(c.receivedAmount) from StudentPaymentsTransaction c " +
            "where c.paymentDate IS NOT NULL " +
            "AND FUNCTION('EXTRACT', YEAR FROM c.paymentDate) = FUNCTION('EXTRACT', YEAR FROM CURRENT_TIMESTAMP) " +
            "AND FUNCTION('EXTRACT', MONTH FROM c.paymentDate) = FUNCTION('EXTRACT', MONTH FROM CURRENT_TIMESTAMP) " +
            "and c.studentPayment.contract.trainingPack.personalUser.id = :personalId")
    BigDecimal sumReceivedPaymentsCurrentMonth(@Param("personalId") Long personalId);

    @Query("select c from StudentPaymentsTransaction c " +
            "where c.paymentDate IS NOT NULL " +
            "AND FUNCTION('EXTRACT', YEAR FROM c.paymentDate) = FUNCTION('EXTRACT', YEAR FROM CURRENT_TIMESTAMP) " +
            "AND FUNCTION('EXTRACT', MONTH FROM c.paymentDate) = FUNCTION('EXTRACT', MONTH FROM CURRENT_TIMESTAMP) " +
            "and c.studentPayment.contract.trainingPack.personalUser.id = :personalId")
    List<StudentPayment> findAllReceivedPaymentsCurrentMonth(@Param("personalId") Long personalId);
}
