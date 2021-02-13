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

    public ArrayList<String> bigExporters (CSVParser parser, String minValue) {
        ArrayList<String> list = new ArrayList<String>();
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String value = record.get("Value (dollars)");
            //Check if it contains exportOfInterest
            if (value.length() > minValue.length()) {
                //If so, write down the "Country" from that row
                list.add(record.get("Country"));
            }
        }
        return list;
    }

    public ArrayList<String> listExportersX(CSVParser parser, String exportOfInterestCsv) {
        ArrayList<String> list = new ArrayList<String>();
        String[] exportOfInterest = exportOfInterestCsv.split(",");
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String export = record.get("Exports");

            //Check if it contains exportOfInterest
            boolean hasAllInterest = true;
            for (String i : exportOfInterest) {
                if (!export.contains(i.trim())){
                    hasAllInterest = false;
                    break;
                }
            }

            if (hasAllInterest){
                list.add(record.get("Country"));
            }

        }
        return list;
    }

    public int numberOfExporters(CSVParser parser, String exportItem1) {
        return listExporters(parser, exportItem1, exportItem1).size();
    }

    public void PrintExporters(CSVParser parser, String exportItem1, String exportItem2){
        ArrayList<String> list = listExporters(parser, exportItem1, exportItem2);
        for (String record : list) {
            System.out.println(record);
        }
    }
    
    public void PrintExportersX(CSVParser parser, String exportInterestCsv){
        ArrayList<String> list = listExportersX(parser, exportInterestCsv);
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

    public void whoExportsCoffee(FileResource fr) {
        System.out.println("\ntest whoExportsCoffee");
        CSVParser parser = fr.getCSVParser();
        PrintExporters(parser, "coffee");
    }

    public void testListExporters(FileResource fr) {
        System.out.println("\ntest listExporters two itemes");
        CSVParser parser;
        parser = fr.getCSVParser();
        System.out.println("whoExports gold & diamonds");
        PrintExporters(parser, "gold", "diamonds");
        parser = fr.getCSVParser();
        System.out.println("whoExports coffee & vanilla");
        PrintExporters(parser, "coffee", "vanilla");
        
    }
    
    public void testListExportersX(FileResource fr) {
        System.out.println("\ntest testListExportersX x itemes");
        CSVParser parser;
        parser = fr.getCSVParser();
        System.out.println("whoExports footwear,textiles,asphalt");
        PrintExporters(parser, "footwear,textiles, asphalt");
        parser = fr.getCSVParser();
        System.out.println("whoExports coffee & vanilla");
        PrintExportersX(parser, "coffee,vanilla");
        parser = fr.getCSVParser();
        System.out.println("whoExports diamonds");
        PrintExportersX(parser, "diamonds");
    }

    public void testCountryInfo(FileResource fr) {
        System.out.println("\ntest testCountryInfo");
        CSVParser parser;
        parser = fr.getCSVParser();
        countryInfo(parser, "Germany");
        parser = fr.getCSVParser();
        countryInfo(parser, "Malawi");
    }

    public void testBigExporters(FileResource fr) {
        System.out.println("\ntest testBigExporters");
        CSVParser parser;
        parser = fr.getCSVParser();
        String minValue = "$123,999,999,999";
        System.out.println("bigger than " + minValue + ":");
        for (String record : bigExporters(parser, minValue)) {
            System.out.println(record);
        }
    }

    public void testNumberOfExporters(FileResource fr){
        System.out.println("\ntest testNumberOfExporters");
        
        CSVParser parser;
        int n;
        String item;

        item = "coffee";
        parser = fr.getCSVParser();
        n = numberOfExporters(parser, item);
        System.out.println(n + " exporters of " + item);

        item = "tea";
        parser = fr.getCSVParser();
        n = numberOfExporters(parser, item);
        System.out.println(n + " exporters of " + item);

        item = "drugs";
        parser = fr.getCSVParser();
        n = numberOfExporters(parser, item);
        System.out.println(n + " exporters of " + item);
    }

    public void test() {
        FileResource fr = new FileResource();
        whoExportsCoffee(fr);
        testCountryInfo(fr);
        testListExporters(fr);
        testNumberOfExporters(fr);
        testListExportersX(fr);
        testBigExporters(fr);
    }

    public static void main(String[] args) {
        WhichCountriesExport c = new WhichCountriesExport();
        c.test();
    }

}
