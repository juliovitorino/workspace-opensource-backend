package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.AccountStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatementRepository extends JpaRepository<AccountStatement, Long> {
}
