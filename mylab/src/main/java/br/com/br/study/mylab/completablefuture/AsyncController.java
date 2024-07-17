package br.com.br.study.mylab.completablefuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async-tasks")
    public CompletableFuture<List<String>> executeAsyncTasks() {
        List<CompletableFuture<String>> futures = IntStream.range(0, 20)
                .mapToObj(asyncService::performAsyncTask)
                .collect(Collectors.toList());

        log.info("executeAsyncTasks :: all task ids have been sent. Tasks will be executed unordered.");
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allOf.thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }
}
