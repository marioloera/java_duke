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
            "name:%1$s, gender:%2$s, births:%3$s, year:%4$s, rank:%5$s",
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

    private FileResource getFile(Integer year) {
        String file = String.format(
            "us_babynames_by_year/yob%1$s.csv", year
            );
        FileResource fr = new FileResource(file);
        return fr;
    }

    private void totalBirthsBasic (FileResource fr) {
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

    private void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int boysBirths = 0;
        int girlsBirths = 0;
        int totalNames = 0;
        int boysNames = 0;
        int girlsNames = 0;
        for (CSVRecord csvRec : fr.getCSVParser(false)) {
            NameRecord nr = new NameRecord(csvRec, 0);
            totalBirths += nr.births;
            boysBirths += nr.Mbirths;
            girlsBirths += nr.Fbirths;
            totalNames++;
            boysNames+= nr.gender.equals("M") ? 1 : 0;
            girlsNames+= nr.gender.equals("F") ? 1 : 0;
        }
        System.out.println("total births = " + totalBirths);
        System.out.println(" boys births = " + boysBirths);
        System.out.println("girls births = " + girlsBirths);
        System.out.println("");
        System.out.println("total names = " + totalNames);
        System.out.println(" boys names = " + boysNames);
        System.out.println("girls names = " + girlsNames);
        
    }

    public void totalBirths(Integer year){
        System.out.println("\n totalBirths " + year);
        totalBirths(getFile(year));
    }

    public Integer getRank (Integer year, String gender, String name) {
        FileResource fr = getFile(year);
        int rank = 0;
        for (CSVRecord csvRec : fr.getCSVParser(false)) {
            NameRecord nr = new NameRecord(csvRec, year);
            if (!nr.gender.equals(gender)){
                continue;
            }
            rank++;
            if (nr.name.equals(name)){
                break;
            }
        }
        return rank;
    }

    private Integer getTotalBirthsRankedHigher (Integer year, String gender, String name, FileResource fr) {
        int birthsRankedHigher = 0;
        NameRecord nr = null;
        int rank = 0;
        for (CSVRecord csvRec : fr.getCSVParser(false)) {
            nr = new NameRecord(csvRec, year);
            if (!nr.gender.equals(gender)){
                continue;
            }
            rank++;
            if (nr.name.equals(name)){
                nr.rank = rank;
                break;
            }
            birthsRankedHigher += nr.births;
        }
        System.out.println("\n getTotalBirthsRankedHigher: " + birthsRankedHigher);
        System.out.println(nr.GetRecordOnLine());
        return birthsRankedHigher;
    }

    public Integer getTotalBirthsRankedHigher (Integer year, String gender, String name) {
        FileResource fr = getFile(year);
        return getTotalBirthsRankedHigher( year, gender, name, fr);
    }

    private void test() {

        FileResource fr;

        fr = new FileResource("us_babynames_datatest/yob2014short.csv");

        System.out.println("\n test totalBirthsBasic");
        totalBirthsBasic(fr);
    
        System.out.println("\n test totalBirths");
        totalBirths(fr);

        fr = new FileResource("us_babynames_datatest/yob2012short.csv");
        getTotalBirthsRankedHigher(2012, "M", "Ethan", fr);

        int rank;
        rank = getRank(2014, "F", "Mia");
        System.out.println("getRank(2014, F, Mia) = " + rank);
    }
    
    private static void tesInManyDays () {
        BabyNamesRank c = new BabyNamesRank();
        //c.testInManyDays();
    }

    public static void main (String[] args) {
        BabyNamesRank c = new BabyNamesRank();
        c.test();
    }
}
