package devops.kafka.producer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InputFileReader {

    public BufferedReader reader;
    public BufferedWriter writer;
    public String inputFilePath;
    public String outputFilePath;
    public int currentLine = 0;
    public int lineLimit;
    public int interval;
    
    public void execute(String inputFilePath, String outputFilePath, int lineLimit, int seconds) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.lineLimit = lineLimit;
        this.interval = seconds;
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile() throws IOException {
        
        FileReader fr = new FileReader(inputFilePath);
        reader = new BufferedReader(fr);
        String headers = reader.readLine();
    
        File outputFile = new File(outputFilePath);
        FileWriter fw = new FileWriter(outputFile);
        writer = new BufferedWriter(fw);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                processNextLine();
            }
        }, 0, interval, TimeUnit.SECONDS);

        while (reader.ready()) { }

        executorService.shutdown();

        writer.close();
        reader.close();

    }

    private void processNextLine() {
        try {
            int remainder = currentLine++ % lineLimit;
            if (remainder == 0) {
                writer.close();
                File outputFile = new File(outputFilePath);
                FileWriter fw = new FileWriter(outputFile);
                writer = new BufferedWriter(fw);
            }
            String line = reader.readLine() + reader.readLine();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}