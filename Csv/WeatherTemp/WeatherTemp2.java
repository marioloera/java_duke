/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class WeatherTemp2 {

    public CSVRecord getMaxOrMinOfTwo (CSVRecord currentRow, CSVRecord resultRow,
                                     String maxOrMin, String field) {
        String currFieldStr = currentRow.get(field);
        // value for read error
        if (currFieldStr.equals("-9999") || currFieldStr.equals("N/A")){
            return resultRow;
        }

        //If resultRow is nothing
        if (resultRow == null) {
            resultRow = currentRow;
        }
        //Otherwise
        else {
            double currentField = Double.parseDouble(currFieldStr);
            double resultField = Double.parseDouble(resultRow.get(field));
            //getting max Check if currentRow’s temperature > resultRow’s
            if (maxOrMin.equals("max") && currentField > resultField
                || axOrMin.equals("min") && currentField < resultField) {
                resultRow = currentRow;
            }
        }
        return resultRow;
    }

    public CSVRecord hottestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord largestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            largestSoFar = getMaxOrMinOfTwo(currentRow, largestSoFar, "max", "TemperatureF");
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }

    public CSVRecord coldestHourInFile(CSVParser parser) {
        //start with smallestSoFar as nothing
        CSVRecord smallestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            // smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
            smallestSoFar = getMaxOrMinOfTwo(currentRow, smallestSoFar, "min", "TemperatureF");
        }
        //The largestSoFar is the answer
        return smallestSoFar;
    }

    public CSVRecord hottestInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
            // use method to compare two records
            //largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
            largestSoFar = getMaxOrMinOfTwo(currentRow, largestSoFar, "max", "TemperatureF");
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }

    public CSVRecord coldestInManyDays() {
        CSVRecord smallestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            // use method to compare two records
            //smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
            smallestSoFar = getMaxOrMinOfTwo(currentRow, smallestSoFar, "min", "TemperatureF");
        }
        //The largestSoFar is the answer
        return smallestSoFar;
    }

    public void testInManyDays () {
        System.out.println("\ntestInManyDays:");                   
        CSVRecord largest = hottestInManyDays();
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("TimeEST") + " " + largest.get("DateUTC"));
        CSVRecord coldest = coldestInManyDays();
        System.out.println("coldes temperature was " + coldest.get("TemperatureF") +
                    " at " + coldest.get("TimeEST") + " "  + coldest.get("DateUTC"));
    }

    public void testHottesColdesttInDay () {
        System.out.println("\ntestHottesColdesttInDay:");
        String path = "../daily_nc_weather_data/2015/weather-2015-01-01.csv";
        FileResource fr = new FileResource(path);
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("TimeEST") + " " + largest.get("DateUTC"));
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldes temperature was " + coldest.get("TemperatureF") +
                    " at " + coldest.get("TimeEST") + " "  + coldest.get("DateUTC"));
    }


    public void test() {
        testHottesColdesttInDay();
    }
    
    public static void tesInManyDays () {
        WeatherTemp2 c = new WeatherTemp2();
        c.testInManyDays();
    }

    public static void main (String[] args) {
        WeatherTemp2 c = new WeatherTemp2();
        c.test();
    }
}
