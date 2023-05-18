package org.ptitsyn;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WriteData {
    List<int[]> listOfArrays = new ArrayList<>();
    List<int[]> sortedListOfArrays = new ArrayList<>();
    int[] arr;
    public static void main(String args[]) throws Exception{
        WriteData writeData = new WriteData(10, 1000, 2000);
        writeData.writeArrays();
    }
    WriteData(int amountOfArr, int rangeOfLength, int rangeOfValue) {
        SingleMergeSort singleMergeSort;
        for (int i = 0; i <= amountOfArr; i++) {
//            int length = 1000 + (int) (Math.random() * (rangeOfLength - 1) + 1);
            int length = 32 * (int) (Math.random() * 1000);
            arr = new int[length];

            for (int j = 0; j < length; j++) {
                arr[j] = (int) (Math.random() * rangeOfValue - rangeOfValue / 2);
            }
            singleMergeSort = new SingleMergeSort(arr);
            sortedListOfArrays.add(singleMergeSort.getGlobalArray());
            listOfArrays.add(arr);
        }
    }

    public void writeArrays() throws Exception {
        File file_input = new File("./src\\main\\resources", "test_input.txt");
        file_input.createNewFile();
        FileWriter fileWriter_input = new FileWriter(file_input);
        for (int[] array : listOfArrays) {
            fileWriter_input.write(Arrays.toString(array) + "\n");
        }
        fileWriter_input.flush();
        fileWriter_input.close();

        File file_output = new File("./src\\main\\resources", "test_output.txt");
        file_output.createNewFile();
        FileWriter fileWriter_output = new FileWriter(file_output);
        for (int[] array : sortedListOfArrays) {
            fileWriter_output.write(Arrays.toString(array) + "\n");
        }
        fileWriter_output.flush();
        fileWriter_output.close();
    }
}
