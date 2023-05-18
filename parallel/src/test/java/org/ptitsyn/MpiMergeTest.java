package org.ptitsyn;


import java.util.Arrays;
import java.util.List;

public class MpiMergeTest {
    public static void main(String[] args) throws Exception{
        ReadData readData = new ReadData();
        String path = "./src\\main\\resources";
        List<int[]> listOfArrays = readData.readData(path, "test_input.txt");
        List <int[]> sortedListOfArrays  = readData.readData(path, "test_output.txt");
        MpiMerge mpiMerge;

        for (int i = 0; i < sortedListOfArrays.size(); i++){
            mpiMerge = new MpiMerge(args, listOfArrays.get(i));
            if (!Arrays.equals(sortedListOfArrays.get(i), mpiMerge.getGlobalArray())) {
                System.out.println("Test failed!");
            }
        }
    }
}