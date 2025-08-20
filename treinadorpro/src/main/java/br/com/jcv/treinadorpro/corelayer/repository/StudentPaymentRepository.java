package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.StudentPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Long> {

    @Query("select sum(c.amount) from StudentPayment c " +
            "where c.duedate < CURRENT_TIMESTAMP " +
            "and c.contract.trainingPack.personalUser.id = :personalId")
    BigDecimal sumOverduePayments(@Param("personalId") Long personalId);

    @Query(nativeQuery = true,
            value = "WITH bills AS (" +
                    "  SELECT sp.id" +
                    "  FROM student_payments sp" +
                    "  LEFT JOIN student_payments_transaction spt ON spt.student_payments_id = sp.id" +
                    "  INNER JOIN contract c ON c.id = sp.contract_id " +
                    "  INNER JOIN training_pack tp ON tp.id = c.pack_training_id " +
                    "  WHERE tp.personal_user_id = :personalId " +
                    "    AND sp.expected_date < current_timestamp " +
                    "  GROUP BY sp.id, sp.amount" +
                    "  HAVING sp.amount - COALESCE(SUM(spt.received_amount), 0) > 0" +
                    ")" +
                    "SELECT * " +
                    "FROM student_payments sp " +
                    "WHERE sp.id IN (SELECT id FROM bills)")
    List<StudentPayment> findAllOverduePayments(@Param("personalId") Long personalId);

    Optional<StudentPayment> findByExternalId(UUID externalId);
}
