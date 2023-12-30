package algorithms;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class that allows for reading a CSV file.
 * @author Angelo G. Gaillet
 */
public class CsvReader {
    /**
     * Read the csv file at the given path
     * @param fileName the realtive file path from the resource folder
     * @return a mapping of header values to a list containing the column's values
     */
    public static Map<String, List<String>> readCsvFromResources(String fileName) {
        Map<String, List<String>> resultMap = new HashMap<>();

        // Use ClassLoader to load the resource from the resources folder
        ClassLoader classLoader = CsvReader.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);

        if (resource == null) {
            throw new IllegalArgumentException("File not found! " + fileName);
        }

        try (CSVReader csvReader = new CSVReader(new FileReader(resource.getFile()))) {
            List<String[]> allRows = csvReader.readAll();

            // Assuming the first row contains headers
            String[] headers = allRows.get(0);

            // Initialize lists for each header
            for (String header : headers) {
                resultMap.put(header, new ArrayList<>());
            }

            // Process the data (start from line with index 1 to skip headers)
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                // Process each row and add values to the corresponding list
                for (int j = 0; j < headers.length; j++) {
                    resultMap.get(headers[j]).add(row[j]);
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return resultMap;
    }
}
