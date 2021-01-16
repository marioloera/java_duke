import edu.duke.*;
import java.io.File;
import java.util.HashMap;
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {

    public String findSimpleGene(String dna, String startCodon, String stopCodon){
        /*
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
        */

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

        String gen = dna.substring(startIndex, stopIndex);
        return gen;
    }

    public void testFindSimpleGene(){
        System.out.println("Start testFindSimpleGene  ");
        HashMap<String, String> examples = new HashMap<String, String>();

        examples.put("ATGAAATAA", "ATGAAATAA");
        examples.put("ATGAAATAAz", "ATGAAATAA");
        examples.put("ATGAAAGGGTAA", "ATGAAAGGGTAA");
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
        Part2 c = new Part2();
        c.test();
    }
}
