package br.com.jcv.treinadorpro.corelayer.repository;

import br.com.jcv.treinadorpro.corelayer.enums.WeekdaysEnum;
import br.com.jcv.treinadorpro.corelayer.model.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query("select AT from AvailableTime AT "
            + "where AT.personalUser.id = :personalTrainerId "
            + "and  AT.daysOfWeek = :weekday "
            + "and AT.dayTime = :dayTime "
            + "and AT.available = :available")
    Optional<AvailableTime> findByPersonalIdAndDayofweekAndDaytimeAndAvailable(
            @Param("personalTrainerId") Long personalTrainerId,
            @Param("weekday") WeekdaysEnum weekday,
            @Param("dayTime") String dayTime,
            @Param("available") Boolean available
    );

    @Query("select AT.dayTime from AvailableTime AT "
            + "where AT.personalUser.id = :personalTrainerId "
            + "and  AT.daysOfWeek = :weekday "
            + "and AT.available = true ")
    List<String> findAvailableTimeByTrainerId(
            @Param("personalTrainerId") Long personalTrainerId,
            @Param("weekday") WeekdaysEnum weekday
    );


}
