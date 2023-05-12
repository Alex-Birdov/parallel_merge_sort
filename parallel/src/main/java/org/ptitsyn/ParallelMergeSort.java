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
        SortWorker worker = new SortWorker(Arrays.copyOfRange(globalArray, (numberOfWorkers - 1) * globalArray.length / numberOfWorkers, (numberOfWorkers - 2) * globalArray.length / numberOfWorkers));
        sortWorkers.add(worker);
        for (int i = 0; i < numberOfWorkers - 1; i++) {
            worker = new SortWorker(Arrays.copyOfRange(globalArray, i * globalArray.length / numberOfWorkers, globalArray.length));
            sortWorkers.add(worker);
        }
    }

    public int[] sortArray() {
//        Запускаем всех работников
        for (int i = 0; i < numberOfWorkers; i++) {
            sortWorkers.get(i).start();
        }
//        Ждем, пока все работники закончат сортировку
        for (SortWorker worker : sortWorkers) {
            try {
                worker.join();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
//        for (int i = 0; i < numberOfWorkers / 2; i++) {
//            MergeWorker mergeWorker = new MergeWorker(sortWorkers.get(i).getSortedArray(), sortWorkers.get(i+1).getSortedArray());
//            mergeWorkers.add(mergeWorker);
//        }

//      Список групп со степенями 2 для части массива
        ArrayList<Integer> groupOfMergers = new ArrayList<>();
        int tempNumOfWorkers = numberOfWorkers;
        do {
            groupOfMergers.add((int) log2(tempNumOfWorkers));
            tempNumOfWorkers = (int) (tempNumOfWorkers - Math.pow(2, tempNumOfWorkers));
        }
        while (tempNumOfWorkers > 0);

        for (int mergeGroup : groupOfMergers) {

        }
//      Массив индексов отсортированных частей массива
        int arrayIndexOfPart = 0;
        for (int numOfMergers : groupOfMergers) {
            int counter = numOfMergers;
            do {
                counter--;
                for (int i = 0; i < (int)Math.pow(2, numOfMergers); i+=2) {
                    MergeWorker mergeWorker = new MergeWorker(sortWorkers.get(arrayIndexOfPart).getSortedArray(), sortWorkers.get(arrayIndexOfPart + 1).getSortedArray());
                    mergeWorkers.add(mergeWorker);
                    arrayIndexOfPart += 2;
                }
            }
            while (counter >= 0);
        }
//      Ждем завершения слияния всех массивов
        for (MergeWorker worker : mergeWorkers) {
            try {
                worker.join();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
//        int numberOfMergers = (int)log2(numberOfWorkers);
        for (MergeWorker worker : mergeWorkers) {
            try{
                worker.join();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        globalArray = new int[globalArray.length];
        return globalArray;
    }
    public double log2(int x) {
        return(Math.log(x)/Math.log(2));
    }
}
