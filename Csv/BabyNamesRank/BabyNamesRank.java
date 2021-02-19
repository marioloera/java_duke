/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.lang.Math.*;

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
        this.year = year;
        this.init();
    }

    NameRecord (Integer year, String gender, String name) {
        this.name = name;
        this.gender = gender;
        this.births = 0;
        this.year = year;
        this.init();
    }

    private void init() {
        this.rank = -1;
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
            if (
                (current.rank < largest.rank && current.rank != -1)
                || largest.rank == -1
                ) {
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
            //"us_babynames_datatest/yob%1$sshort.csv", year
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

    public Integer getRank(Integer year, String gender, String name) {
        NameRecord nr = getRankRecord(year, gender, name);
        return nr.rank;
    }

    private NameRecord getRankRecord (Integer year, String gender, String name) {
        NameRecord result = new NameRecord(year, gender, name);
        FileResource fr = getFile(year);
        int rank = 0;
        for (CSVRecord csvRec : fr.getCSVParser(false)) {
            NameRecord nr = new NameRecord(csvRec, year);
            if (!nr.gender.equals(gender)){
                continue;
            }
            rank++;
            if (nr.name.equals(name)){
                result = nr;
                result.rank = rank;
                break;
            }
        }
        return result;
    }

    public String getName (Integer year, String gender, Integer trgRank) {
        FileResource fr = getFile(year);
        int rank = 1;
        for (CSVRecord csvRec : fr.getCSVParser(false)) {
            NameRecord nr = new NameRecord(csvRec, year);
            if (!nr.gender.equals(gender)){
                continue;
            }
            if (rank == trgRank) {
                return nr.name;
            }
            rank++;
        }
        return "NO NAME";
    }

    public void getNameIf (Integer year, String gender, String name, Integer year2){
        System.out.println("\n getNameIf:");
        int rank = getRank(year, gender, name);
        String name2 = getName(year2, gender, rank);
        String msg = String.format(
            "(%1$s): %2$s -> (%3$s): %4$s",
            year, name, year2, name2
            );
        System.out.println(msg);
    }

    public void getStatistics (Integer year, String gender, String name, Integer year2){
        getStatistics2(year, gender, name, year2);
        //NameRecord highestRecord = new NameRecord(year, gender, name);
        NameRecord highestRecord = null;
        int rankAcc = 0;
        int count = 0;
        while (year <= year2){
            NameRecord nr = getRankRecord(year, gender, name);
            year++;
            if (nr.rank == -1){
                continue;
            }
            rankAcc += nr.rank;
            highestRecord = NameRecord.getHigherRankOfTwo(nr, highestRecord);
            count++;
        }
        System.out.println("\n getStatistics: ");
        System.out.println(highestRecord.GetRecordOnLine());
        Double rankAvg = Double.valueOf(rankAcc) / count;
        System.out.println("\n rankAvg:" + rankAvg);
    }
    
    public void getStatistics2 (Integer year, String gender, String name, Integer year2){
        //NameRecord highestRecord = new NameRecord(year, gender, name);
        int minRank = Integer.MAX_VALUE;
        int yearHigerRank = year;
        int rankAcc = 0;
        int count = 0;
        while (year <= year2){
            int rank = getRank(year, gender, name);
            year++;
            if (rank == -1) {
                continue;
            }
            rankAcc += rank;
            if (rank < yearHigerRank){
                yearHigerRank = year-1;
                minRank = rank;
            }
            count++;
            
        }
        System.out.println("\n getStatistics2: ");
        System.out.println("yearHigerRankr: " + yearHigerRank + " rank: " + minRank );
        Double rankAvg = Double.valueOf(rankAcc) / count;
        System.out.println("\n rankAvg:" + rankAvg);
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
        System.out.println("\n getRank(2014, F, Mia) = " + rank);

        NameRecord nr = getRankRecord(2014, "F", "Mia");
        System.out.println("\n getRankRecord(2014, F, Mia)\n" +
            nr.GetRecordOnLine());

        getNameIf(1972, "F", "Susan", 2014);
        getNameIf(1974, "M", "Owen", 2014);

        // for test files
        //getStatistics(2012, "M", "Mason", 2014);
        //getStatistics(2012, "M", "Jacob", 2014);
        
        getStatistics(1880, "F", "Genevieve", 2014);
        getStatistics(1880, "M", "Mich", 2014);
        
        getStatistics(1880, "F", "Susan", 2014);
        getStatistics(1880, "M", "Robert", 2014);
    }

    public static void main (String[] args) {
        BabyNamesRank c = new BabyNamesRank();
        c.test();
    }
}
