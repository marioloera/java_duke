/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.*;

public class WhichCountriesExport {
    public ArrayList<String> listExporters(CSVParser parser, String exportOfInterest) {
        return listExporters(parser, exportOfInterest, exportOfInterest);
    }

    public ArrayList<String> listExporters(CSVParser parser, String exportItem1, String exportItem2) {
        ArrayList<String> list = new ArrayList<String>();
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportItem1) && export.contains(exportItem2)) {
                //If so, write down the "Country" from that row
                list.add(record.get("Country"));
            }
        }
        return list;
    }

    public void PrintExporters(CSVParser parser, String exportItem1, String exportItem2){
        ArrayList<String> list = listExporters(parser, exportItem1, exportItem2);
        for (String record : list) {
            System.out.println(record);
        }
    }
    
    public void PrintExporters(CSVParser parser, String exportItem1){
        /*ArrayList<String> list = listExporters(parser, exportItem1);
        for (String record : list) {
            System.out.println(record);
        }
        */
        PrintExporters(parser, exportItem1, exportItem1);
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
        PrintExporters(parser, "coffee");
    }

    public void testListExporters() {
        System.out.println("test listExporters");
        FileResource fr = new FileResource();
        CSVParser parser;
        parser = fr.getCSVParser();
        System.out.println("whoExports gold & diamonds");
        PrintExporters(parser, "gold", "diamonds");
        parser = fr.getCSVParser();
        System.out.println("whoExports coffee & vanilla");
        PrintExporters(parser, "coffee", "vanilla");
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
        testListExporters();
    }

    public static void main(String[] args) {
        WhichCountriesExport c = new WhichCountriesExport();
        c.test();
    }

}
