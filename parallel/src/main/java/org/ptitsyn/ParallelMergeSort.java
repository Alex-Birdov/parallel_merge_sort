package org.ptitsyn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelMergeSort {
    int numberOfWorkers;
    int[] globalArray;
    List<SortWorker> sortWorkers = new ArrayList<>();
    List<MergeWorker> mergeWorkers = new ArrayList<>();

    ParallelMergeSort(int[] globalArray, int numberOfWorkers) {
        this.globalArray = globalArray;
        this.numberOfWorkers = numberOfWorkers;
//        Записываем в последнего работника последнюю часть массива, в случае если длина массива делится на число всех работников с остатком
//       Передаем работнику часть его массива
        SortWorker worker = new SortWorker(Arrays.copyOfRange(globalArray, globalArray.length / numberOfWorkers * (numberOfWorkers - 1), globalArray.length));
        sortWorkers.add(worker);
        for (int i = 0; i < numberOfWorkers - 1; i++) {
            worker = new SortWorker(Arrays.copyOfRange(globalArray, globalArray.length / numberOfWorkers * i, globalArray.length / numberOfWorkers * (i + 1)));
            sortWorkers.add(worker);
        }
    }
    public int[] getGlobalArray() {
        return globalArray;
    }

    public int[] sortArray() {
        List<int[]> sortedArrays = new ArrayList<>();
//        Запускаем всех работников
        for (int i = 0; i < numberOfWorkers; i++) {
            sortWorkers.get(i).start();
        }
//        Ждем, пока все работники закончат сортировку, записываем отсортированные массивы в список
//        Освобождаем работников
        for (SortWorker worker : sortWorkers) {
            try {
                worker.join();
                sortedArrays.add(worker.getSortedArray());
                worker = null;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

//      Глубина погружения
        int depth = (int)log2(numberOfWorkers);
        do {
            depth--;
            int takenPart = 0;
//            Выделяем потоки слияния
            for (int i = 0; i < (int)Math.pow(2, depth); i++) {
                MergeWorker mergeWorker = new MergeWorker(sortedArrays.get(takenPart), sortedArrays.get(takenPart + 1));
                takenPart += 2;
                mergeWorker.start();
                mergeWorkers.add(mergeWorker);
            }
//            Ждем, пока все потоки слияния завершат работу, перезаписываем объедененные массивы в первые элементы списка
//            Освобождаем работников
            int counter = 0;
            for (MergeWorker worker : mergeWorkers) {
                try {
                    worker.join();
                    sortedArrays.set(counter, worker.getMergedArray());
                    counter++;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Очищаем список с потоками сливателей
            mergeWorkers.clear();
        }
        while (depth >= 0);
        globalArray = sortedArrays.get(0);
        return globalArray;
    }
    public double log2(int x) {
        return(Math.log(x)/Math.log(2));
    }
}
