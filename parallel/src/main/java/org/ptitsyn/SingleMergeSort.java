package org.ptitsyn;

import java.util.Arrays;

public class SingleMergeSort {

    SingleMergeSort() {
    }

    public static int[] mergeSort(int[] sortArr) {
        int[] array1 = Arrays.copyOf(sortArr, sortArr.length);
        int[] array2 = new int[sortArr.length];
        int[] result = mergeSortInner(array1, array2, 0, sortArr.length);
        return result;
    }

    public static int[] mergeSortInner(int[] array1, int[] array2, int startIndex, int endIndex) {
        if (startIndex >= endIndex - 1) {
            return array1;
        }

        //уже отсортирован
        int middle = (startIndex + endIndex) / 2;
        int[] sorted1 = mergeSortInner(array1, array2, startIndex, middle);
        int[] sorted2 = mergeSortInner(array1, array2, middle, endIndex);

        //слияние
        int index1 = startIndex;
        int index2 = middle;
        int tempIndex = startIndex;
        array2 = new int[array1.length];
        while (index1 < middle && index2 < endIndex) {
            array2[tempIndex++] = sorted1[index1] < sorted2[index2]
                    ? sorted1[index1++] : sorted2[index2++];
        }
        while (index1 < middle) {
            array2[tempIndex++] = sorted1[index1++];
        }
        while (index2 < endIndex) {
            array2[tempIndex++] = sorted2[index2++];
        }
        return array2;
    }
}
