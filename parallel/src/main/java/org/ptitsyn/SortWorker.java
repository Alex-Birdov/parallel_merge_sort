package org.ptitsyn;

public class SortWorker extends Thread{
    private int[] arr;
    public SortWorker() {
    }
    public SortWorker(int[] arr) {
        this.arr = arr;
    }
    public int[] getSortedArray() {
        return this.arr;
    }
    public void run() {
        SingleMergeSort singleMergeSort = new SingleMergeSort();
        arr = singleMergeSort.mergeSort(arr);
    }
}
