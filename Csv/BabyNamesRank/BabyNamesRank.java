/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

class NameRecord {

    public String name;
    public String gender;
    public int births;
    public int year;
    public int rank;
    public int Mbirths;
    public int Fbirths;

    NameRecord (CSVRecord nameRecord, int year) {
        this.name = nameRecord.get(0);
        this.gender = nameRecord.get(1);
        this.births = Integer.parseInt(nameRecord.get(2));
        this.rank = -1;
        this.year = year;
        this.Mbirths = this.gender.equals("M") ? this.births : 0;
        this.Fbirths = this.gender.equals("F") ? this.births : 0;
    }

    public String GetRecordOnLine () {
        String msg = String.format(
            "name:%1$s, gender:%2$s, births:%3$s, year:%4$s, rank:%4$s",
            name, gender, births, year, rank
            );
        return msg;
    }

    public static NameRecord getHigherRankOfTwo (NameRecord current, NameRecord largest) {
        //If largest is nothing
        if (largest == null) {
            largest = current;
        }
        //Otherwise
        else {
            //Check if currentRow’s temperature > largestSoFar’s
            if (current.rank < largest.rank && current.rank != -1) {
                //If so update largestSoFar to currentRow
                largest = current;
            }
        }
        return largest;
    }
}

public class BabyNamesRank {

    public void totalBirthsBasic (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
            }
            else {
                totalGirls += numBorn;
            }
        }
        System.out.println(" total births = " + totalBirths);
        System.out.println("female births = " + totalGirls);
        System.out.println("  male births = " + totalBoys);
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord csvRec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(csvRec.get(2));
            NameRecord nr = new NameRecord(csvRec, 0);
            totalBirths += nr.births;
            totalBoys += nr.Mbirths;
            totalGirls += nr.Fbirths;
        }
        System.out.println(" total births = " + totalBirths);
        System.out.println("female births = " + totalGirls);
        System.out.println(  "male births = " + totalBoys);
    }

    public void testTotalBirths(FileResource fr) {
        System.out.println("\n totalBirthsBasic");
        totalBirthsBasic(fr);
        System.out.println("\n totalBirths");
        totalBirths(fr);
    }

    public void test() {
        FileResource fr = new FileResource("us_babynames_datatest/yob2014short.csv");
        testTotalBirths(fr);
    }
    
    public static void tesInManyDays () {
        BabyNamesRank c = new BabyNamesRank();
        //c.testInManyDays();
    }

    public static void main (String[] args) {
        BabyNamesRank c = new BabyNamesRank();
        c.test();
    }
}
