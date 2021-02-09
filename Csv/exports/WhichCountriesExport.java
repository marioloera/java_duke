/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.*;

public class WhichCountriesExport {
    public void listExporters(CSVParser parser, String exportOfInterest) {
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportOfInterest)) {
                //If so, write down the "Country" from that row
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }

    public void countryInfo (CSVParser parser, String country) {
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            if (country.equals(record.get("Country"))) {
                String export = record.get("Exports");
                String value_dols = record.get("Value (dollars)");
                ArrayList<String> list = new ArrayList<String>();
                list.add(country);
                list.add(export);
                list.add(value_dols);
                String listString = String.join(": ", list);
                System.out.println(listString);
                return;
            }
        }
        System.out.println(country + ": NOT FOUND");
    }

    public void whoExportsCoffee() {
        System.out.println("test whoExportsCoffee");
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExporters(parser, "coffee");
    }

    public void testCountryInfo() {
        System.out.println("test testCountryInfo");
        FileResource fr = new FileResource();
        CSVParser parser;
        parser = fr.getCSVParser();
        countryInfo(parser, "Germany");
        parser = fr.getCSVParser();
        countryInfo(parser, "Malawi");
    }

    public void test() {
        whoExportsCoffee();
        testCountryInfo();
    }

    public static void main(String[] args) {
        WhichCountriesExport c = new WhichCountriesExport();
        c.test();
    }

}
