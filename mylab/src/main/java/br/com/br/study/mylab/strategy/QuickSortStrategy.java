package br.com.br.study.mylab.strategy;

import java.util.Collections;
import java.util.List;

// Concrete Strategy B
class QuickSortStrategy implements SortStrategy {
    @Override
    public void execute(List<Integer> numbers) {
        quickSort(numbers, 0, numbers.size() - 1);
        System.out.println("Sorted using Quick Sort");
    }

    private void quickSort(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pi = partition(numbers, low, high);
            quickSort(numbers, low, pi-1);
            quickSort(numbers, pi+1, high);
        }
    }

    private int partition(List<Integer> numbers, int low, int high) {
        int pivot = numbers.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (numbers.get(j) < pivot) {
                i++;
                Collections.swap(numbers, i, j);
            }
        }
        Collections.swap(numbers, i+1, high);
        return i+1;
    }
}