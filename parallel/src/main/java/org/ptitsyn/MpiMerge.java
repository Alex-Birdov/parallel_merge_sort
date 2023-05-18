package org.ptitsyn;

import mpi.MPI;

import java.util.Arrays;

import static mpi.MPI.INT;

public class MpiMerge {
    static int[] globalArray;

    public void setGlobalArray(int[] array) {
        globalArray = array;
    }
    MpiMerge(String[] args, int[] array) {
        globalArray = array;
        mpiMergeSort(args);

    }
    public int[] getGlobalArray() {
        return globalArray;
    }

    public static void main(String[] args) {
//        Задаем массив
        int globalArraySize = 100;
        globalArray = new int[globalArraySize];
        for (int i = 0; i < globalArray.length; i++) {
            globalArray[i] = (int)(Math.random() * 1000);
        }
//        Вызываем сортировку слиянием MPI
        mpiMergeSort(args);
    }
    public static void mpiMergeSort(String[] args) {
        int globalArraySize = globalArray.length;
//        Запускаем таймер и процессы
        double startTime = System.currentTimeMillis();
        MPI.Init(args);
        int id = MPI.COMM_WORLD.Rank();
        int numberOfProcesses = MPI.COMM_WORLD.Size();
        int depth = (int) log2(numberOfProcesses);

        if (id == 0) {
//            System.out.println("Amount of processes: " + numberOfProcesses);
        }
//        Заполняем подмассивы
        int localArraySize = globalArraySize / numberOfProcesses;
        int[] localArray = new int[localArraySize];
        //разбиваем массив на подмассивы
        MPI.COMM_WORLD.Scatter(globalArray, 0, localArraySize, INT,
                localArray, 0, localArraySize, INT, 0);
//        Объединяем подмассивы
        if (id == 0) {
            globalArray = mergeSort(depth, id, localArray, localArraySize, globalArray);
        } else {
            mergeSort(depth, id, localArray, localArraySize, null);
        }

        if (id == 0) {
            double endTime =  System.currentTimeMillis();
//            System.out.println(Arrays.toString(globalArray));
//            MPJ VM не знает кириллицу, поэтому сообщение на английском
            System.out.println("Time passed for MPI merge sort: " + (endTime - startTime));
        }
//        Завершаем процессы MPI
        MPI.Finalize();
    }
    private static int[] mergeSort(int depth, int id, int[] localArray, int localArraySize, int[] globalArray) {
        int currentDepth = 0;
        int parent;
        int rightChild;
        int[] leftHalf = localArray;
        int[] rightHalf;
        int[] mergeResult;

//        Сортируем подмассивы
        SingleMergeSort singleMergeSort = new SingleMergeSort(localArray);
        localArray = singleMergeSort.getGlobalArray();

        while (currentDepth < depth) {
//            Устанавливаем иерархию процессов
            if (id % ((int)Math.pow(2, currentDepth + 1)) == 0) {
                parent = id;
            }
            else {
                parent = id - (int)Math.pow(2, currentDepth);
            }
//            Для родительского процесса
            if (parent == id) {
                rightChild = id + (int)Math.pow(2, currentDepth);
                rightHalf = new int[localArraySize];
//                Принимаем правый массив для объединения
                MPI.COMM_WORLD.Recv(rightHalf, 0, localArraySize, INT,
                        rightChild, 0);
//                Записываем результат слияния вверх по иерархии процессов
                mergeResult = new int[localArraySize * 2];
                merge(leftHalf, rightHalf, mergeResult);
                leftHalf = mergeResult;
                localArraySize = localArraySize * 2;
                currentDepth++;
            } else {
//                Отправляем правый подмассив для слияния
                MPI.COMM_WORLD.Send(leftHalf, 0, localArraySize, INT, parent, 0);
                currentDepth = depth;
            }
        }
        if(id == 0) {
            globalArray = leftHalf;
        }
        return globalArray;
    }
    private static void merge(int[] leftHalf, int[] rightHalf, int[] mergeResult) {
        int i = 0, j = 0, k = 0;
        while (i < leftHalf.length && j < rightHalf.length) {
            if (leftHalf[i] <= rightHalf[j]) {
                mergeResult[k++] = leftHalf[i++];
            }
            else {
                mergeResult[k++] = rightHalf[j++];
            }
        }
        while (i < leftHalf.length) {
            mergeResult[k++] = leftHalf[i++];
        }
        while (j < rightHalf.length) {
            mergeResult[k++] = rightHalf[j++];
        }
    }
    private static double log2(double x) {
        return Math.log(x) / Math.log(2.0);
    }
}
