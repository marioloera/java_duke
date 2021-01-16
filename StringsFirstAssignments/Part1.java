import edu.duke.*;
import java.io.File;
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {

    public String findSimpleGene(String dna){
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
        String startCodon = "ATG";
        String stopCodon = "TAA";

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

    public void test() {

    }

    public static void main (String[] args) {
        Part1 c = new Part1();
        c.test();
    }
}
