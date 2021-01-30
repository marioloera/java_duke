import java.io.File;
import java.util.HashMap;
import java.util.*;
import java.lang.Math.*;
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

class TestData {

    public String dna;
    public String stringX;
    public int expResult;
    public int startIndex;
    public String msg;
    public String genData;

    TestData(int expResult, int startIndex, String stringX, String dna){
        this.dna = dna;
        this.stringX = stringX;
        this.startIndex = startIndex;
        this.expResult = expResult;
        this.msg = String.format(
            "expResult:%4$s, dna:%1$s, stopCodon:%2$s, startIndex:%3$s",
            dna, stringX, startIndex, expResult
            );
        this.genData = String.format(
                "expResult:%1$s, dna:%2$s",
                stringX, dna
                );
    }
}

public class Part1 {

    public int findStopCodon(String dna, int startIndex, String stopCodon){
        /**  findStopCodon that has three parameters,
            * a String parameter named dna,
            an integer parameter named startIndex that represents
                where the first occurrence of ATG occurs in dna,
            a String parameter named stopCodon.
            This method returns the index of the first occurrence of stopCodon
                that appears past startIndex
                and is a multiple of 3 away from startIndex.
        
            If there is no such stopCodon,
            this method returns the length of the dna strand
         */
        int notFound = dna.length();
        int currIndex = startIndex;
        while (currIndex < dna.length()){
            currIndex = dna.indexOf(stopCodon, currIndex);
            if (currIndex == -1){
                break;
            }
            if ((currIndex - startIndex) % 3 == 0){
                return currIndex;
            }
            currIndex += 1;
        }
        return notFound;
        }

    public String findGene(String dnaI){
        /** 5. Write the method findGene that has one String parameter dna,
            representing a string of DNA. 
            In this method you should do the following:

            Find the index of the first occurrence of the start codon “ATG”.
            If there is no “ATG”, return the empty string.

            Find the index of the first occurrence of the stop codon “TAA” 
            after the first occurrence of “ATG”
            that is a multiple of three away from the “ATG”.

            Find the index of the first occurrence of the stop codon “TAG”
            after the first occurrence of “ATG”
            that is a multiple of three away from the “ATG”.
            
            Find the index of the first occurrence of the stop codon “TGA”
            after the first occurrence of “ATG”
            that is a multiple of three away from the “ATG”.

            Return the gene formed from the “ATG” and the closest stop codon 
            that is a multiple of three away.
            If there is no valid stop codon and therefore no gene,
            return the empty string.
            */
        String dna = dnaI.toUpperCase();
        String notGen = "";

        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1){
            return notGen;
        }

        int TAA = findStopCodon(dna, startIndex ,"TAA");
        int TAG = findStopCodon(dna, startIndex ,"TAG");
        int TGA = findStopCodon(dna, startIndex ,"TGA");
        
        //int stopIndex = Math.min(Math.min(TAA, TAG), TGA);
        int stopIndex = Math.min(Math.min(TAA, TAG), TGA);
        if (stopIndex == dna.length()){
            return notGen;
            }
        return dnaI.substring(startIndex, stopIndex + 3);
        }

    public void testFindStopCodon(){
        /**
            doc string in java
        */
        System.out.println("Start testFindStopCodon  ");

        HashMap<Integer, TestData> rows = new HashMap<Integer, TestData>();
        int i = 1;
        // TestData(int expResult, int startIndex, String stopCodon, String dna)
        rows.put(i++, new TestData(3, 0, "xxx", "012xxx"));
        rows.put(i++, new TestData(3, 0, "xxx", "012xxxxxx"));
        rows.put(i++, new TestData(5, 0, "a", "12345"));
        rows.put(i++, new TestData(6, 0, "x", "_234x6"));
        rows.put(i++, new TestData(6, 0, "x", "0x2345x55885"));
        rows.put(i++, new TestData(9, 0, "x", "0123456x8x0"));
        rows.put(i++, new TestData(0, 0, "x", "xxxx"));
        rows.put(i++, new TestData(3, 0, "x", "_xxx"));
        rows.put(i++, new TestData(3, 0, "x", "_xxx567"));
        rows.put(i++, new TestData(3, 0, "x", "_xxxx67"));
        rows.put(i++, new TestData(9, 3, "xxx", "012345648xxx")); //
        rows.put(i++, new TestData(9, 3, "xxx", "0123xxx01xxx"));

        for (int key : rows.keySet()) {
            TestData r = rows.get(key);
            // findStopCodon( dna, startIndex, stopCodon)
            int x = findStopCodon(r.dna, r.startIndex, r.stringX);
            String check = (x == r.expResult) ? "ok" : "error got: " + x;
            String msg = String.format("%1$s: %2$s: %3$s", key, check, r.msg);
            System.out.println(msg);
            }
        }

    public void testFindGen(){
        /**
         * TestData(int x, int x, String stringX~gen, String dna)
         * start codon "ATG"
         * stop codons: "TAA", "TGA", "TAG"
        */
        System.out.println("Start testFindGen  ");

        HashMap<Integer, TestData> rows = new HashMap<Integer, TestData>();
        int i = 1;
        rows.put(i++, new TestData(0, 0, "atg123taa", "atg123taa"));
        rows.put(i++, new TestData(0, 0, "atg123tga", "atg123tga"));
        rows.put(i++, new TestData(0, 0, "atg123tag", "atg123tag"));
        rows.put(i++, new TestData(0, 0, "ATG123TAA", "ATG123TAA"));
        rows.put(i++, new TestData(0, 0, "ATG123TGA", "ATG123TGA"));
        rows.put(i++, new TestData(0, 0, "ATG123TAG", "ATG123TAG"));
        rows.put(i++, new TestData(0, 0, "ATG1TAGTGA12xxxTAG", "12asdATG1TAGTGA12xxxTAGccc456TAA"));
        rows.put(i++, new TestData(0, 0, "", "12asdAXG1TAGTGA12xxxTAGccc456TAA"));
        rows.put(i++, new TestData(0, 0, "", "ATG123xAA"));
        rows.put(i++, new TestData(0, 0, "", "454"));
        rows.put(i++, new TestData(0, 0, "", ""));

        for (int key : rows.keySet()) {
            TestData r = rows.get(key);
            // findGene(String dnaI)
            String gen = findGene(r.dna);
            String check = (gen.equals(r.stringX)) ? "ok" : "error got: " + gen;
            String msg = String.format("%1$s: %2$s: %3$s", key, check, r.genData);
            System.out.println(msg);
            }
        }

    public ArrayList<String> getAllGenes(String dna) {
        ArrayList<String> gens = new ArrayList<String>();
        while (true){
            String gen = findGene(dna);
            if (gen.equals("")){
                break;
            }
            gens.add(gen);
            int startIndex = dna.indexOf(gen) + gen.length();
            dna = dna.substring(startIndex);
        }
        return gens;
    }

    public void printAllGenes() {
        System.out.println("Print all gens");
        HashMap<Integer, String> expResults = new HashMap<Integer, String>();
        HashMap<Integer, String> gens = new HashMap<Integer, String>();
        int i = 1;
        String dna = "11f551atg123taa_ATG123TAGx12asdATG1TAGTGA12xxxTAGccc456TAA";
        expResults.put(i++, "atg123taa");
        expResults.put(i++, "ATG123TAG");
        expResults.put(i++, "ATG1TAGTGA12xxxTAG");
        // extrat method
        ArrayList<String> gensList = getAllGenes(dna);
        // save data in HashMap, easier to test
        i=1;
        for (String gen : gensList) {
            gens.put(i++, gen);
        }

        System.out.println("Test");
        for (int key : expResults.keySet()) {
            String expResult = expResults.get(key);
            String gen = gens.getOrDefault(key, " no gens");
            String msg = key + " ";
            if (expResult.equals(gen)){
                msg = "gen ok: " + gen;
            }
            else {
                msg = "gen not ok:" + gen + " expected:" + expResult;
            }
            System.out.println(msg);
        }
    }

    public int nOccurrences(String stringA, String stringB){

        int n = 0;
        int indexA = 0;

        while (indexA < stringB.length()){
            indexA = stringB.indexOf(stringA, indexA);
            if (indexA == -1){
                break;
            }
            indexA += stringA.length();
            n += 1;
        }
        return n;
    }

    public float cgRatio (String dna){
        dna = dna.toLowerCase();
        return (float)(nOccurrences("c", dna) + nOccurrences("g", dna)) / dna.length();
    }

    public void testCgRatio (){
        float result = cgRatio("ATGCCATAG");
        String msg = ((float)4/9 == result)? "cgRatio ok" : "error";
        System.out.println(msg);
    }

    public int countCTG (String dna){
        dna = dna.toUpperCase();
        return nOccurrences("CTG", dna);
    }

    public void testCountCTG(){
        int result = countCTG("CTGATGCCATAGCTG");
        String msg = (2 == result)? "countCTG ok" : "error";
        System.out.println(msg);
    }

    public void test() {
        //testFindStopCodon();
        //testFindGen();
        printAllGenes();
        testCgRatio();
        testCountCTG();
    }

    public static void main (String[] args) {
        Part1 c = new Part1();
        c.test();
        }
}
