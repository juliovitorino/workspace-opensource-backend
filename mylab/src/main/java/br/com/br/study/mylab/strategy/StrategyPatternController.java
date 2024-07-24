package br.com.br.study.mylab.strategy;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StrategyPatternController {

    @GetMapping("/strategy")
    public ResponseEntity<Boolean> executeStrategy() {
        SortingContext context = new SortingContext();

        List<Integer> numbers = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        
        context.setStrategy(new BubbleSortStrategy());
        context.sortNumbers(numbers);

        context.setStrategy(new QuickSortStrategy());
        context.sortNumbers(numbers);

        //or
        context.setStrategyAndExecute(new QuickSortStrategy(), numbers);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}