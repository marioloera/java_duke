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
        /**  Part 4: Finding Web Links
            Write a program that reads the lines from the file
            at this URL location:
            http://www.dukelearntoprogram.com/course2/data/manylinks.html
            and prints each URL on the page that is a link to youtube.com.
            Assume that a link to youtube.com has no spaces in it and would be
            in the format (where [stuff] represents characters that are not
            verbatim): “http:[stuff]youtube.com[stuff]”

         */
        int pos = url.toLowerCase().indexOf(base);
        if (pos == -1){
            return "";
        }
        int start = 1 + url.lastIndexOf("\"", pos);
        int end = url.indexOf("\"", pos + base.length());
        String r = url.substring(start, end); 
        return r;
    }

    public ArrayList<String> GetLines(String url){
        ArrayList<String> lines = new ArrayList<String>();
        URLResource file = new  URLResource(url);
        for (String item : file.words()) {
            lines.add(item);
        }
        return lines;
    }

    public void testGetUrlLikeBase(){
        System.out.println("\ntestGetUrlLikeBase:");
        String url = "https://www.dukelearntoprogram.com/course2/data/manylinks.html";
        String base = "youtube.com";
        ArrayList<String> lines = GetLines(url);
        for (String l : lines) {
            String r = getUrlLikeBase(l, base);
            if (!(r.equals(""))){
                System.out.println(r);
            }
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
