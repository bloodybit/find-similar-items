package com.company;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class Shingling that
 *  - constructs k–shingles of a given length k (e.g., 10) from a given document,
 *  - computes a hash value for each unique shingle,
 *  - and represents the document in the form of an ordered set of its hashed k-shingles.
 */
public class Shingling {

    private Map<Integer, ArrayList<Integer>> shingles = new HashMap();
    private int k;
    private int numberOfDocuments = 0;

    public Shingling(Integer k) {
        // Inizialize the shingle with a map
        // The map is shared by all the documents
        // Set the k that will be in common among all the documents
        this.k = k;
    }

    public void shingleADocument(String fullText) {

        // Increase the number of documents
        this.numberOfDocuments++;

        for (int i = 0; i <fullText.length()-k; i++) {

            // get the current shingle and hash it
            Integer currentShingle = fullText.substring(i, i+k).toLowerCase().hashCode();

            if(this.shingles.containsKey(currentShingle)) {
                // if the shingle is already in the map, get the arrayList with all the documents that have that shingle
                ArrayList<Integer> documentsWithThisShingle = (ArrayList<Integer>) this.shingles.get(currentShingle);

                // if the current document that I am analysing does already have this single previously, don't do anything (is already here)
                // Otherwise add it to the list
                if(!documentsWithThisShingle.contains(currentShingle)){
                    documentsWithThisShingle.add(this.numberOfDocuments);
                    this.shingles.replace(currentShingle, documentsWithThisShingle);
                }
            } else {
                // if this shingle is not yet in the map, add it and map it with the current document
                ArrayList<Integer> documentsWithThisShingle = new ArrayList<>();
                documentsWithThisShingle.add(this.numberOfDocuments);
                this.shingles.put(currentShingle, documentsWithThisShingle);
            }
        }
    }

    /**
     * This function allows to compare two documents according to Jaccard Similarity
     * @param set1
     * @param set2
     * @return
     */
    public float compareSets(Integer set1, Integer set2) {

        int shieldsInCommon = 0; // Shields that are in both the documents
        int shieldsInTotal = 0; // distinct shields that the two documents have

        for (Map.Entry<Integer, ArrayList<Integer>> shingle: this.shingles.entrySet()) {

            // check each shingle if it is present in both the documents, if yes I add it in the numerator
            if (shingle.getValue().contains(set1) && shingle.getValue().contains(set2)) {
                shieldsInCommon++;
            }

            // check each shingle, if it is present one of the two documents, add it to the denominator
            if (shingle.getValue().contains(set1) || shingle.getValue().contains(set2)) {
                shieldsInTotal++;
            }
        }

        System.out.println("shileds in common " + shieldsInCommon);
        System.out.println("shields in total " + shieldsInTotal);
        return (float) shieldsInCommon/ (float) shieldsInTotal;
    }

    public void minHashing(int numberOfHashFunction){

        // Signature matrix
        int[][] signatureMatrix = new int[numberOfHashFunction][this.shingles.size()];


        // creating a matrix that allows me to create random hash functions and to reuse the during my process
        // values can be something in between 1 and 100 [range is my arbitrary choice]
        int[][] hashFunctionParameters = new int[numberOfHashFunction][3];

        for (int i = 0; i < hashFunctionParameters.length; i++) {

            for (int j = 0; j < hashFunctionParameters[i].length; j++) {
                hashFunctionParameters[i][j] = 1 + (int)(Math.random() * ((100 - 1) + 1));
            }
        }
        printMatrix(hashFunctionParameters); // print the matrix for debugging

        // implement the minHashing algorithm according to slides
        for (Map.Entry<Integer, ArrayList<Integer>> shingle: this.shingles.entrySet()) {
            // compute the hashfunctions and save the result
            int[] hashFunctionsRowResults = new int[hashFunctionParameters.length];

            for (int i = 0; i < hashFunctionParameters.length; i++) {
                hashFunctionsRowResults[i] = randomHashFunction(
                        shingle.getKey(),
                        hashFunctionParameters[i][0],
                        hashFunctionParameters[i][1],
                        hashFunctionParameters[i][2]);
                System.out.println(hashFunctionsRowResults[i]);
            }
        }
    }

    private int randomHashFunction(int shingle, int a, int b, int mod) {
        System.out.println(shingle);
        System.out.println(a);
        System.out.println(b);
        System.out.println(mod);

        long r = (long) shingle * (long) a;
        int r2 = shingle * a;
        System.out.println(r);
        System.out.println(r2);
        System.out.println();
        System.out.println();
        return ((a * shingle) + b) % mod;
    }


    public Map<Integer, ArrayList<Integer>> getShingles() {
        return shingles;
    }

    public int getNumberOfDocuments() {
        return numberOfDocuments;
    }

    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
