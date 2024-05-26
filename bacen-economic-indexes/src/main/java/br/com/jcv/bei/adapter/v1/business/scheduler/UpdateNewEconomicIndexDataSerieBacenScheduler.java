package br.com.jcv.bei.adapter.v1.business.scheduler;

import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.jcv.bei.corebusiness.updateneweconomicindexdata.UpdateNewEconomicIndexDataSerieBacenBusinessService;
import br.com.jcv.bei.infrastructure.interfaces.SchedulerBusinessService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateNewEconomicIndexDataSerieBacenScheduler implements SchedulerBusinessService {

    private final UpdateNewEconomicIndexDataSerieBacenBusinessService updateNewEconomicIndexDataSerieBacenBusinessService;

    @Scheduled(cron="0 */2 * * * ?")
    @Override
    public void execute() {
        updateNewEconomicIndexDataSerieBacenBusinessService.execute(UUID.randomUUID(), Boolean.TRUE);
    }

}
