package com.ga.java.project01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVHandler {
    public static void main(String[] args) {
        List<String> csvLine = null;
        List<List<String>> csvData = null;
        csvLine.add("Test Line, test");
        csvData.add(csvLine);
    }

    String separator = ",";

    public boolean doesFileExist(String fileName){
        File file = new File(fileName);
        return file.exists();
    }
    public boolean createFile(String fileName){
        File file = new File(fileName);
        try {
            return file.createNewFile();
        } catch (Exception e){
            System.out.println("Something went wrong while creating the file");
            return false;
        }
    }
    public List<List<String>> readCSV(String fileName, boolean hasHeader){

        List<List<String>> csvData = null;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            while ( br.readLine() != null ){
                line  = br.readLine();
                String[] data = line.split(separator);

                csvData.add(Arrays.asList(data));
            }
        } catch (IOException e){
            System.out.println("The file is not accessible");
        }
        return csvData;
    }

    public boolean writeToCSVFile(String fileName, List<List<String>> csvData){
        if(!doesFileExist(fileName)){
            createFile(fileName);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for (List<String> row : csvData){
                writer.append(String.join(separator, row));
                writer.append("\n");
            }
            return true;
        } catch (IOException e){
            return false;
        }
    }

    public void appendToCSVFile(String fileName, List<List<String>> csvData){
        csvData.forEach(row -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                writer.println(String.join(separator, row));
            } catch (IOException e){
                System.out.println("Something went wrong while appending to CSV File");
            }
        });
    }
}
