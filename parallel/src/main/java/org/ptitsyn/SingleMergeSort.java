package org.ptitsyn;

public class SingleMergeSort {
    private int[] globalArray;

    SingleMergeSort(int[] globalArray) {
        this.globalArray = globalArray;
        mergeSort(globalArray, globalArray.length);
    }
    public int[] getGlobalArray() {
        return  globalArray;
    }

    public static void mergeSort(int[] array, int arrayLength) {
        if (arrayLength < 2) {
            return;
        }
        int mid = arrayLength / 2;
        int[] leftArray = new int[mid];
        int[] rightArray = new int[arrayLength - mid];

        for (int i = 0; i < mid; i++) {
            leftArray[i] = array[i];
        }
        for (int i = mid; i < arrayLength; i++) {
            rightArray[i - mid] = array[i];
        }
        mergeSort(leftArray, mid);
        mergeSort(rightArray, arrayLength - mid);

        merge(array, leftArray, rightArray, mid, arrayLength - mid);
    }

    public static void merge(int[] array, int[] leftArray, int[] rightArray, int leftLength, int rightLength) {

        int i = 0, j = 0, k = 0;
        while (i < leftLength && j < rightLength) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            }
            else {
                array[k++] = rightArray[j++];
            }
        }
        while (i < leftLength) {
            array[k++] = leftArray[i++];
        }
        while (j < rightLength) {
            array[k++] = rightArray[j++];
        }
    }
}

