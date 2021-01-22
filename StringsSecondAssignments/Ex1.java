import java.io.File;
import java.util.HashMap;
import java.util.*;
import java.lang.Math.*;
/**
 * Write a description of Ex1 here.
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

public class Ex1 {

    public void findAbc(String input) {
        int index = input.indexOf("abc");
        String all_results = "";
        while (true) {
            if (index == -1 || index >= input.length() - 3) {
                break;
            }
            //System.out.println((index+1)+ ", " +(index+4));
            System.out.println("index " + index);
            String found = input.substring(index+1, index+4);
            System.out.println(found);
            index = input.indexOf("abc", index+4-1);
            all_results += (found + ",");
            //System.out.println("index after updating  " + index);

        }
        System.out.println(all_results);
    }

    public void test() {
        //findAbc("abcd");
        //findAbc("abcdabc");
        //findAbc("abcdkfjsksioehgjfhsdjfhksdfhuwabcabcajfieowj");
        findAbc("abcabcabcabca");
        }

    public static void main (String[] args) {
        Ex1 c = new Ex1();
        c.test();
        }
}
