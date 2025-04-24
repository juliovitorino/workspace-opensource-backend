package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    Optional<Parameter> findByKeytag(String keytag);
}
