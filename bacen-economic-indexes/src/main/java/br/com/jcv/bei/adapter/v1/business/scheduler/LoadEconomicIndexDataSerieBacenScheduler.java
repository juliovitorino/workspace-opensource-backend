package br.com.jcv.bei.adapter.v1.business.scheduler;

import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.jcv.bei.corebusiness.loadeconomicindexdata.LoadEconomicIndexDataSerieBacenBusinessService;
import br.com.jcv.bei.infrastructure.interfaces.SchedulerBusinessService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoadEconomicIndexDataSerieBacenScheduler implements SchedulerBusinessService {

    private final LoadEconomicIndexDataSerieBacenBusinessService loadEconomicIndexDataSerieBacenBusinessService;

    @Scheduled(cron="0 */2 * * * ?")
    @Override
    public void execute() {
        loadEconomicIndexDataSerieBacenBusinessService.execute(UUID.randomUUID(), Boolean.TRUE);
    }

}
