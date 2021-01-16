import edu.duke.*;
import java.io.File;
import java.util.ArrayList;
/**
 * Write a description of Part4 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */


public class Part4 {

    public String getUrlLikeBase(String url, String base){
        String r = "";
        int n = 0;
        int indexA = 0;

        // while (indexA < stringB.length()){
        //     indexA = stringB.indexOf(stringA, indexA);
        //     if (indexA == -1){
        //         break;
        //     }
        //     indexA += stringA.length();
        //     n += 1;
        // }
        return r;
    }

    public ArrayList<String> GetLines(String url){
        ArrayList<String> lines = new ArrayList<String>();
        URLResource file = new  URLResource(url);
   	for (String item : file.words()) {
   	    lines.add(item);
   	}
        lines.add("Ford");
        return lines;
    }

    public void testGetUrlLikeBase(){
        System.out.println("\ntestGetUrlLikeBase:");
        String url = "https://www.dukelearntoprogram.com/course2/data/manylinks.html";
        String base = "youtube.com";
        ArrayList<String> lines = GetLines(url);
        for (String l : lines) {
            String r = getUrlLikeBase(l, base);
            System.out.println(l + ' ' + r);
        }
    }

    public void Test() {
        testGetUrlLikeBase();
    }

    public static void main (String[] args) {
        Part4 c = new Part4();
        c.Test();
    }
}
