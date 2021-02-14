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
                || maxOrMin.equals("min") && currentField < resultField) {
                resultRow = currentRow;
            }
        }
        return resultRow;
    }

    public CSVRecord getMaxOrMinHourInFile(CSVParser parser, String maxOrMin, 
                                           String field) {
        //start with resultRow as nothing
        CSVRecord resultRow = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            resultRow = getMaxOrMinOfTwo(currentRow, resultRow, maxOrMin, field);
        }
        //The resultRow is the answer
        return resultRow;
    }

    public double getAvgInFile(CSVParser parser, String field) {
        //start with resultRow as nothing
        double sum = 0.0;
        int count = 0;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            String currFieldStr = currentRow.get(field);
            // value for read error
            if (currFieldStr.equals("-9999") || currFieldStr.equals("N/A")){
                continue;
            }
            count++;
            sum += Double.parseDouble(currFieldStr);
        }
        //The resultRow is the answer

        return sum/count;
    }

    public String getAvgTempWithHighHumidity(CSVParser parser, double humidityLimit) {
        //start with resultRow as nothing
        double sum = 0.0;
        int count = 0;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            String currFieldStr = currentRow.get("TemperatureF");
            String currHumidityStr = currentRow.get("Humidity");
            // value for read error
            if (currFieldStr.equals("-9999") || currFieldStr.equals("N/A")
               || currHumidityStr.equals("-9999") || currHumidityStr.equals("N/A")){
                continue;
            }
            if (Double.parseDouble(currHumidityStr) < humidityLimit){
                continue;
            }
            count++;
            sum += Double.parseDouble(currFieldStr);
        }
        if (count == 0) {
            return "No temperatures with that humidity";
        }
        //The resultRow is the answer
        return Double.toString(sum/count);
    }
 
    public CSVRecord getMaxOrMinInManyDays(String maxOrMin, String field) {
        CSVRecord resultRow = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            
            // use method to get largest in file.
            CSVRecord currentRow = getMaxOrMinHourInFile(fr.getCSVParser(), maxOrMin, field);
            // use method to compare two records
            resultRow = getMaxOrMinOfTwo(currentRow, resultRow, maxOrMin, field);
        }
        //The resultRow is the answer
        return resultRow;
    }

    public void testInManyDays (String field) {

        System.out.println("\n testInManyDays:");

        CSVRecord largest = getMaxOrMinInManyDays("max", field);
        System.out.println(field + " max: " + largest.get(field) +
                   " at " + largest.get("DateUTC"));

        CSVRecord coldest = getMaxOrMinInManyDays("min", field);
        System.out.println(field + " min " + coldest.get(field) +
                    " at "  + coldest.get("DateUTC"));

    }

    public void testMaxMinTempInDay () {

        System.out.println("\n testMaxMinTempInDay:");
        String path = "../daily_nc_weather_data/2015/weather-2015-01-01.csv";
        FileResource fr = new FileResource(path);
        String field = "TemperatureF";

        CSVRecord largest = getMaxOrMinHourInFile(fr.getCSVParser(), "max", field);
        System.out.println("hottest temperature was " + largest.get(field) +
                   " at " + largest.get("DateUTC"));

        CSVRecord coldest = getMaxOrMinHourInFile(fr.getCSVParser(), "min", field);
        System.out.println("coldes temperature was " + coldest.get(field) +
                    " at "  + coldest.get("DateUTC"));

        String file = "2014/weather-2014-01-20.csv";
        path = "../daily_nc_weather_data/" + file;
        fr = new FileResource(path);

        System.out.println("\n test Avg Temperature: "
            + getAvgInFile(fr.getCSVParser(), field)
            + " in InFile: " + file);

        double humidityLimit;
        file = "2014/weather-2014-01-20.csv";
        path = "../daily_nc_weather_data/" + file;
        fr = new FileResource(path);
        humidityLimit = 80;
        System.out.println("\n test getAvgTempWithHighHumidity: "
            + getAvgTempWithHighHumidity(fr.getCSVParser(), humidityLimit)
            + "\n humidityLimit: " + humidityLimit
            + "\n in InFile: " + file);

        file = "2014/weather-2014-03-20.csv";
        path = "../daily_nc_weather_data/" + file;
        fr = new FileResource(path);
        humidityLimit = 80;
        System.out.println("\n test getAvgTempWithHighHumidity: "
            + getAvgTempWithHighHumidity(fr.getCSVParser(), humidityLimit)
            + "\n humidityLimit: " + humidityLimit
            + "\n in InFile: " + file);
    }

    public void testAvgInDay (String field, double humidityLimit) {

        System.out.println("\n testAvgInDay:");
        FileResource fr = new FileResource();

        // print the date of the first row
        for (CSVRecord currentRow : fr.getCSVParser()) {
            // use method to compare two records
            System.out.println(currentRow.get("DateUTC"));
            break;
        }

        System.out.println(
            "\n test Avg " + field + ": " +
            + getAvgInFile(fr.getCSVParser(), field)
        );
            
        System.out.println(
            "\n test getAvgTempWith Higher Humidity than:  " + humidityLimit
            + "\n : " + getAvgTempWithHighHumidity(fr.getCSVParser(), humidityLimit)
        );
    }

    public void test() {
        testMaxMinTempInDay();
    }
    
    public static void tesInManyDays (String field) {
        WeatherTemp2 c = new WeatherTemp2();
        c.testInManyDays(field);
    }

    public static void main (String[] args) {
        WeatherTemp2 c = new WeatherTemp2();
        c.test();
    }
}
