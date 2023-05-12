package org.ptitsyn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadData {

    ReadData() {
    }

    public List<int[]> readData(String path, String fileName) throws Exception{
        List<int[]> listOfArrays = new ArrayList<>();
        File file = new File(path, fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();

        while (line != null) {
            line = line.replace(" ", "");
            line = line.replace("[", "");
            line = line.replace("]", "");
            listOfArrays.add(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray());
            line = bufferedReader.readLine();
        }
        return listOfArrays;
    }
}
