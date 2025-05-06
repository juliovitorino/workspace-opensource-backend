package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.model.UserWorkoutCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserWorkoutCalendarRepository extends JpaRepository<UserWorkoutCalendar, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM UserWorkoutCalendar uw WHERE uw.externalId IN :uuids")
    void deleteByExternalId(List<UUID> uuids);

    Optional<UserWorkoutCalendar> findByExternalId(UUID externalId);
}
