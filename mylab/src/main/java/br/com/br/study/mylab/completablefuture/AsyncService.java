package br.com.br.study.mylab.completablefuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncService {

    @Async
    public CompletableFuture<String> performAsyncTask(int taskId) {
        try {
            // Simular um processamento demorado
            log.info("performAsyncTask :: executing taskId = {}", taskId);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Tarefa " + taskId + " concluída!");
    }
}
