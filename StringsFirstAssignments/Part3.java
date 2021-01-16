import edu.duke.*;
import java.io.File;
import java.util.HashMap;
/**
 * Write a description of Part1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */

class TestData {

    public String stringA;
    public String stringB;
    public boolean result;
    public int n;

    TestData(String stringB, String stringA, boolean result){
        this.stringA = stringA;
        this.stringB = stringB;
        this.result = result;
    }
    
    TestData(String stringB, String stringA, int n){
        this.stringA = stringA;
        this.stringB = stringB;
        this.n = n;
    }
}

public class Part3 {

    public boolean twoOccurrences(String stringA, String stringB){
        /** 2. Write the method named twoOccurrences 
            that has two String parameters named stringa and stringb.
            This method returns true if stringa appears at least twice in stringb,
            otherwise it returns false.
            For example,
                twoOccurrences(“by”, “A story by Abby Long”) returns true
                    as there are two occurrences of “by”,
                twoOccurrences(“a”, “banana”) returns true
                    as there are three occurrences of “a” so “a” occurs at least twice,
                twoOccurrences(“atg”, “ctgtatgta”) returns false
                    as there is only one occurence of “atg”.

        */
        int lengthA = stringA.length();
        int n = 0;
        int indexA_prev = 0;
        int indexA = 0;
        

        indexA = stringB.indexOf(stringA, indexA_prev);
        if (indexA == -1){
            return false;
        }
        indexA_prev = indexA + lengthA;

        indexA = stringB.indexOf(stringA, indexA_prev);
        indexA_prev = indexA + lengthA;
        if (indexA == -1){
            return false;
        }
        return true;
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

    public void testTwoOccurrences(){
        /**
            doc string in java
        */
        System.out.println("Start testTwoOccurrences!");
        TestData[] arr = new TestData[4];
        arr[0] = new TestData("a1a2a3a4", "a", true);
        arr[1] = new TestData("A story by Abby Long", "by", true);
        arr[2] = new TestData("banana", "a", true);
        arr[3] = new TestData("ctgtatgta", "atg", false);
        for (int i = 0; i < arr.length; i++){
            String a = arr[i].stringA;
            String b = arr[i].stringB;
            boolean exp = arr[i].result;
            boolean r = twoOccurrences(a, b);
            String msgOk = "ok a:" + a + " b:" + b + " r:" +  r;
            String error = "error a:" + a + " b:" + b + " exp:" + exp + " r:" +  r;
            String msg = (exp == r) ? msgOk : error;
            System.out.println(msg);
        }
    }

    public void testN_Occurrences(){
        /**
            doc string in java
        */
        System.out.println("Start testnOccurrences!");
        int n = 8;
        TestData[] arr = new TestData[n];
        arr[--n] = new TestData("a1a2a3a4", "a", 4);
        arr[--n] = new TestData("A story by Abby Long", "by", 2);
        arr[--n] = new TestData("banana", "a", 3);
        arr[--n] = new TestData("ctgtatgta", "atgx", 0);
        arr[--n] = new TestData("abcxx", "abc", 1);
        arr[--n] = new TestData("aaaaaa", "a", 6);
        arr[--n] = new TestData("aaaaaa", "aa", 3);
        arr[--n] = new TestData("aaaaaa", "aaa", 2);
        for (int i = 0; i < arr.length; i++){
            String a = arr[i].stringA;
            String b = arr[i].stringB;
            int exp = arr[i].n;
            int r = nOccurrences(a, b);
            String msgOk = "ok a:" + a + " b:" + b + " r:" +  r;
            String error = "error a:" + a + " b:" + b + " exp:" + exp + " r:" +  r;
            String msg = (exp == r) ? msgOk : error;
            System.out.println(msg);
        }
    }

    public void Test() {
        //testTwoOccurrences();
        testN_Occurrences();
    }

    public static void main (String[] args) {
        Part3 c = new Part3();
        c.Test();
    }
}
