import java.io.File;
import java.util.HashMap;
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
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

    public void test() {
        testFindSimpleGene();
    }

    public static void main (String[] args) {
        Part1 c = new Part1();
        c.test();
    }
}
