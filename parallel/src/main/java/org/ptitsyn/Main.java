package org.ptitsyn;

import java.util.Arrays;

public class Main {
    public static void main(String args[]) throws Exception{
        int[] arraySingle = new int[100000];
        int[] arrayMulti = new int[100000];

        SingleMergeSort singleMergeSort = new SingleMergeSort();

        for (int i = 0; i < arraySingle.length; i++) {
            arraySingle[i] = arrayMulti[i] = (int)(Math.random() * 1000);
        }

        long startTimeSingle = System.nanoTime();
        int[] sortedArraySingle = singleMergeSort.mergeSort(arraySingle);
        long stopTimeSingle = System.nanoTime();

        long startTimeMulti = System.nanoTime();
        SortWorker firstWorker = new SortWorker(arrayMulti);
        firstWorker.start();
        try {
            firstWorker.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        long stopTimeMulti = System.nanoTime();

        long timeDifference = (stopTimeSingle - startTimeSingle) - (stopTimeMulti - startTimeMulti);
        System.out.println("Время, потраченное на выполнение однопоточной сортировки, нс: " + (stopTimeSingle - startTimeSingle));
        System.out.println("Время, потраченное на выполнение многопоточной сортировки, нс: " + (stopTimeMulti - startTimeMulti));
        System.out.println("Разница времени исполнения: " + timeDifference);
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