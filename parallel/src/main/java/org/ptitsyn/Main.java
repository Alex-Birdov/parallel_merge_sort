package org.ptitsyn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String args[]) throws Exception{
        int[] arraySingle = new int[30000000];

        for (int i = 0; i < arraySingle.length; i++) {
            arraySingle[i] = (int)(Math.random() * 1000);
        }

        List<int[]> groupOfMultiArr = new ArrayList<>();
        int[] arrayMulti;
        int[] groupOfWorkers = new int[] {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};

        for (int i = 0; i < groupOfWorkers.length; i++) {
            arrayMulti = Arrays.copyOf(arraySingle, arraySingle.length);
            groupOfMultiArr.add(arrayMulti);
        }

        long startTimeSingle = System.currentTimeMillis();
        SingleMergeSort singleMergeSort = new SingleMergeSort(arraySingle);
        int[] sortedArraySingle = singleMergeSort.getGlobalArray();
        long stopTimeSingle = System.currentTimeMillis();

        long timeOfSingle = stopTimeSingle - startTimeSingle;
        System.out.println("Время, потраченное на выполнение однопоточной сортировки, мс: " + timeOfSingle);
        ParallelMergeSort parallelMergeSort;
        System.out.println("Время, потраченное на выполнение многопоточной сортировки:");
        for (int i = 0; i < groupOfWorkers.length; i++) {
            long startTimeMulti = System.currentTimeMillis();
            parallelMergeSort = new ParallelMergeSort(groupOfMultiArr.get(i), groupOfWorkers[i]);
            parallelMergeSort.sortArray();
            long stopTimeMulti = System.currentTimeMillis();
            groupOfMultiArr.set(i, parallelMergeSort.getGlobalArray());

            long timeDifference = timeOfSingle - (stopTimeMulti - startTimeMulti);
            System.out.println(Arrays.equals(groupOfMultiArr.get(i), arraySingle));
            System.out.println("Количество потоков: " + groupOfWorkers[i] + "\nВремя исполнения, мс: " + (stopTimeMulti - startTimeMulti));
            System.out.println("Разница времени исполнения, мс: " + timeDifference);
        }




//        long startTimeMulti = System.nanoTime();
//        SortWorker firstWorker = new SortWorker(arrayMulti);
//        firstWorker.start();
//        try {
//            firstWorker.join();
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//        long stopTimeMulti = System.nanoTime();

//        System.out.println(Arrays.toString(sortedArraySingle));
//        System.out.println(Arrays.toString(firstWorker.getSortedArray()));

//        Проверка слияния двух отсортированных массивов
//        SortWorker worker = new SortWorker();
//        MergeWorker mw = new MergeWorker(arrayMulti, arraySingle);
//        mw.start();
//        mw.join();
//
//        System.out.println(Arrays.toString(mw.getMergedArray()));

//      Считываение данных
//        ReadData readData = new ReadData();

//        Запись данных
//        WriteData writeArray = new WriteData(5, 20, 200);
//        writeArray.writeArrays();

    }
}