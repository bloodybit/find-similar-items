package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Read the file, I get each review as a document
        String csvFile = "/Users/riccardosibani/Documents/University/KTH/Data Mining/input/dataset3";
        String line = "";

        // create the shingling object
        // I shingle each document by a factor k = 10 as said in the assignment
        // even thought reviews are quite short, so perhaps k=5 is better
        Shingling shingling = new Shingling(9);

        File folder = new File("input");
        File[] listOfFiles = folder.listFiles();

//        String[] listOfFiles = {"input/keepItSimple.txt", "input/keepItSimple2.txt"};
//        String[] listOfFiles = {"input/divinaCommedia1.txt", "input/divinaCommedia2.txt"};

        for (int i = 0; i < listOfFiles.length; i++) {
            System.out.println(listOfFiles[i]);
            String document = "";
            BufferedReader br = null;

            try {
                br = new BufferedReader(new FileReader(listOfFiles[i]));
                while((line = br.readLine()) != null) {
                    document += line.replaceAll("\\s","").replace("’", "").toLowerCase();
//                    document += line;
                }
                System.out.println(document);
                shingling.shingleADocument(document);
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

        }


        Map<Integer, ArrayList<Integer>> shingles = shingling.getShingles();
        System.out.println(shingles);

        // want to compare romeoJuliet.txt && romeoJuliet2.txt
        int set1 = 0;
        int set2 = 0;

        for (int i = 0; i <listOfFiles.length; i++) {
            System.out.println(i+1 + " " + listOfFiles[i].getName());
            if (listOfFiles[i].getName().equals("divinaCommedia1.txt")) {
                set1 = i + 1;
            }
            if (listOfFiles[i].getName().equals("divinaCommedia2.txt")){
                set2 = i + 1;
            }
        }

        System.out.println(shingling.compareSets(set1,set2));
        Integer[][] signature = shingling.minHashing(100);

        System.out.println(shingling.compareSignatures(set1, set2));

        LSH lsh = new LSH(shingling.getSignatureMatrix());
        System.out.println();
        System.out.println();
        System.out.println("LSH COMPARES");
        System.out.println(lsh.findCandidates(0.50));

    }
}
