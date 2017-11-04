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
            Integer currentShingle = fullText.substring(i, i+k).hashCode();

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

    public float compareSets(Integer set1, Integer set2) {

        int shieldsInCommon = 0;
        int shieldsInTotal = 0;

        for (Map.Entry<Integer, ArrayList<Integer>> shingle: this.shingles.entrySet()) {
            if (shingle.getValue().contains(set1) && shingle.getValue().contains(set2)) {
                shieldsInCommon++;
            }
            if (shingle.getValue().contains(set1) || shingle.getValue().contains(set2)) {
                shieldsInTotal++;
            }
        }

        System.out.println(shieldsInCommon);
        System.out.println(shieldsInTotal);
        return (float) shieldsInCommon/ (float) shieldsInTotal;
    }

    public Map<Integer, ArrayList<Integer>> getShingles() {
        return shingles;
    }

    public int getNumberOfDocuments() {
        return numberOfDocuments;
    }
}
