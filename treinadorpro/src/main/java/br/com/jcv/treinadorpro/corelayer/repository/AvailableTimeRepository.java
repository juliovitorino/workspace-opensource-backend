package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.enums.WeekdaysEnum;
import br.com.jcv.treinadorpro.corelayer.model.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {

    @Modifying
    @Query("update AvailableTime AT "
            + "set AT.available = :available "
            + "where AT.personalUser.id = :personalTrainerId "
            + "and  AT.daysOfWeek = :weekday "
            + "and AT.dayTime = :dayTime ")
    void updateAvailableTime(@Param("available") Boolean available,
                             @Param("personalTrainerId") Long personalTrainerId,
                             @Param("weekday") WeekdaysEnum weekday,
                             @Param("dayTime") String dayTime);
}
