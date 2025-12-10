package com.ga.java.project01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVHandler {
    public static void main(String[] args) {
        List<String> csvLine = new ArrayList<>();
        List<List<String>> csvData = new ArrayList<>();
        csvLine.add("Test Line, test");
        csvData.add(csvLine);
        CSVHandler testHandler = new CSVHandler();
        boolean ret = testHandler.writeToCSVFile("test.csv", csvData);
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
    public static List<List<String>> readCSV(String fileName){
        CSVHandler csvHandler = new CSVHandler();
        List<List<String>> csvData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while ( (line  = br.readLine()) != null ){
                String[] data = line.split(csvHandler.separator);
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

    public static void appendToCSVFile(String fileName, List<List<String>> csvData){
        CSVHandler csvHandler = new CSVHandler();
        csvData.forEach(row -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                writer.println(String.join(csvHandler.separator, row));
            } catch (IOException e){
                System.out.println("Something went wrong while appending to CSV File");
            }
        });
    }
}
