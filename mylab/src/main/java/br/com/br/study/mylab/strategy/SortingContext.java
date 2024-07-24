package br.com.br.study.mylab.strategy;

import java.util.List;

// Context
class SortingContext {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sortNumbers(List<Integer> numbers) {
        strategy.execute(numbers);
    }

    public void setStrategyAndExecute(SortStrategy strategy, List<Integer> numbers) {
        this.setStrategy(strategy);
        strategy.execute(numbers);
    }
}