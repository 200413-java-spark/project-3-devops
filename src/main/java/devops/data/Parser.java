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
    ObjectMapper mapper = new ObjectMapper();

    public Parser(String filePath) throws IOException, CsvValidationException {
        reader = new CSVReaderHeaderAware(new FileReader(filePath));
    }

    public ArrayList<String> read(int n) throws IOException, CsvValidationException, JsonProcessingException {
        ArrayList<String> lines = new ArrayList<>();
        int i = 0;
        Map<String, String> nextLine;
        while (i < n && (nextLine = reader.readMap()) != null) {
            lines.add(mapper.writeValueAsString(nextLine));
            i++;
        }
        return lines;
    }

    public void close() throws IOException {
        reader.close();
    }
}
