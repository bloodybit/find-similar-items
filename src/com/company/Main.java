package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Read the file, I get each review as a document
        String csvFile = "/Users/riccardosibani/Documents/University/KTH/Data Mining/input/dataset3";
//        String csvFile = "/Users/riccardosibani/Documents/University/KTH/Data Mining/input/dataset2";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\n";


        // create the shingling object
        // I shingle each document by a factor k = 10 as said in the assignment
        // even thought reviews are quite short, so perhaps k=5 is better
        Shingling shingling = new Shingling(5);

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] documents = line.split(cvsSplitBy);

                for (String document: documents) {
                    System.out.println(document);
                    shingling.shingleADocument(document);
                }

//                System.out.println("Result " + documents[0]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Map<Integer, ArrayList<Integer>> shingles = shingling.getShingles();
        System.out.println(shingles);
        System.out.println(shingling.compareSets(1,2));
        Integer[][] signature = shingling.minHashing(100);

        System.out.println(shingling.compareSignatures(1, 2));
    }
}
