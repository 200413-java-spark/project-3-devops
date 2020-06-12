package devops.data;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

// Our parser class.
public class Parser {
    // Reader, to keep open.
    CSVReaderHeaderAware reader;
    ObjectMapper jsonMapper = new ObjectMapper();

    public Parser(String filePath) throws IOException, CsvValidationException {
        reader = new CSVReaderHeaderAware(new FileReader(filePath));
    }

    // maps from csv to json
    public ArrayList<String> read(int n) throws IOException, CsvValidationException, JsonProcessingException {
        ArrayList<String> linesJson = new ArrayList<>();
        int i = 0;
        Map<String, String> nextLine;
        while (i < n && (nextLine = reader.readMap()) != null) {
            linesJson.add(jsonMapper.writeValueAsString(nextLine));
            i++;
        }
        return linesJson;
    }

    public void close() throws IOException {
        reader.close();
    }
}
