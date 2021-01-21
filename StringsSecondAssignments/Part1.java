import java.io.File;
import java.util.HashMap;
import java.util.*;
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

class TestData {

    public String dna;
    public String stopCodon;
    public int expResult;
    public int startIndex;
    public String msg;

    TestData(int expResult, int startIndex, String stopCodon, String dna){
        this.dna = dna;
        this.stopCodon = stopCodon;
        this.startIndex = startIndex;
        this.expResult = expResult;
        this.msg = String.format(
            "expResult:%4$s, dna:%1$s, stopCodon:%2$s, startIndex:%3$s",
            dna, stopCodon, startIndex, expResult
            );
    }

}

public class Part1 {

    public String findSimpleGene(String dnaI, String startCodonI, String stopCodonI){
        /** doc string in java
            Write the method findSimpleGene that has one String parameter dna,
            representing a string of DNA.

            This method does the following:

            1 - Finds the index position of the start codon “ATG”.
                If there is no “ATG”, return the empty string.

            2 - Finds the index position of the first stop codon “TAA”
                appearing after the “ATG” that was found.
                If there is no such “TAA”, return the empty string.

            3 - If the length of the substring between the “ATG” and “TAA”
                is a multiple of 3, then return the substring that starts with
                that “ATG” and ends with that “TAA”.

            4 - 4. Modify the findSimpleGene method to work with DNA strings
                that are either all uppercase letters such as “ATGGGTTAAGTC” 
                or all lowercase letters such as “gatgctataat”.
                Calling findSimpleGene with “ATGGGTTAAGTC” should return the answer
                with uppercase letters, the gene “ATGGGTTAA”, and calling
                findSimpleGene with  “gatgctataat” should return the answer with
                lowercase letters, the gene “atgctataa”.
                HINT: there are two string methods toUpperCase() and toLowerCase().
                If dna is the string “ATGTAA” then dna.toLowerCase() results in the
                string “atgtaa”.
        */
        String dna = dnaI.toUpperCase();
        String startCodon = startCodonI.toUpperCase();
        String stopCodon = stopCodonI.toUpperCase();

        int startIndex = dna.indexOf(startCodon);
        if (startIndex == -1){
            return "";
        }

        int stopIndex = 3 + dna.indexOf(stopCodon, startIndex + 3);
        if (stopIndex == -1){
            return "";
        }

        if ((stopIndex - startIndex) % 3 != 0){
            return "";
        }

        String gen = dnaI.substring(startIndex, stopIndex);
        return gen;
        }

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

    public void testFindSimpleGene(){
        /**
            doc string in java
        */
        System.out.println("Start testFindSimpleGene  ");
        HashMap<String, String> examples = new HashMap<String, String>();

        examples.put("ATGAAATAA", "ATGAAATAA");
        examples.put("ATGAAATAAz", "ATGAAATAA");
        examples.put("ATGAAAGGGTAA", "ATGAAAGGGTAA");
        examples.put("atgaaataa", "atgaaataa");
        examples.put("atgaaataaz", "atgaaataa");
        examples.put("atgaaagggtaa", "atgaaagggtaa");
        examples.put("ATGAATAA", "");
        examples.put("TTGAAATAA", "");
        examples.put("ATGAAATGA", "");
        examples.put("13432434234", "");
        examples.put("TTATGAAATAATT", "ATGAAATAA");
        examples.put("xxxATGTTTTAAyyy", "ATGTTTTAA");
        String value;
        String gen;
        String msg;
        String okMsg;
        String notOkMsg;
        String startCodon = "ATG";
        String stopCodon = "TAA";
        // Print keys
        for (String key : examples.keySet()) {
            value = examples.get(key);
            gen = findSimpleGene(key, startCodon, stopCodon);
            okMsg = "ok " + key + ": " + gen;
            notOkMsg = "error! for:" + key + " expected:" + value + " got:" + gen;
            msg = (gen.equals(value)) ? okMsg : notOkMsg;
            System.out.println(msg);
            }
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
            int x = findStopCodon(r.dna, r.startIndex, r.stopCodon);
            String check = (x == r.expResult) ? "ok" : "error got: " + x;
            String msg = String.format("%1$s: %2$s: %3$s", key, check, r.msg);
            System.out.println(msg);
            }
        }

    public void test() {
        // testFindSimpleGene();
        testFindStopCodon();
        }

    public static void main (String[] args) {
        Part1 c = new Part1();
        c.test();
        }
}
