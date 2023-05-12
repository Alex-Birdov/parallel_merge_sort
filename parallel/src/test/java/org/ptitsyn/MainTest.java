package org.ptitsyn;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MainTest {
    ReadData readData;

    @Before
    public void setUp() {
        readData = new ReadData();
    }

    @Test
    public void testMergeSort() throws Exception{
        SingleMergeSort singleMergeSort = new SingleMergeSort();
        String path = "C:\\Users\\user\\Documents\\Учеба\\Семестр 2\\Параллельные вычисления\\parallel\\src\\main\\resources";
        List <int[]> listOfArrays = readData.readData(path, "test_input.txt");
        List <int[]> sortedListOfArrays  = readData.readData(path, "test_output.txt");

        for (int i = 0; i < sortedListOfArrays.size(); i++){
            assertEquals(Arrays.toString(sortedListOfArrays.get(i)), Arrays.toString(singleMergeSort.mergeSort(listOfArrays.get(i))));
        }
    }
}