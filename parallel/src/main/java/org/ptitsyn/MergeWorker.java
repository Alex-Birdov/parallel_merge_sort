package org.ptitsyn;

public class MergeWorker extends Thread{
    private int[] array1;
    private int[] array2;
    private int[] mergedArray;
    MergeWorker(int[] array1, int[] array2) {
        this.array1 = array1;
        this.array2 = array2;
    }
    public int[] getMergedArray() {
        return mergedArray;
    }
    public void run() {
        mergedArray = new int[array1.length + array2.length];
        int index1 = 0;
        int index2 = 0;
        int tempIndex = 0;
        while (index1 < array1.length && index2 < array2.length) {
            mergedArray[tempIndex++] = array1[index1] < array2[index2]
                    ? array1[index1++] : array2[index2++];
        }
        while (index1 < array1.length) {
            mergedArray[tempIndex++] = array1[index1++];
        }
        while (index2 < array2.length) {
            mergedArray[tempIndex++] = array2[index2++];
        }
    }
}
