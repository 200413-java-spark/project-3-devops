package devops.s3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.commons.io.FileUtils;

public class getS3File {
    private String fileName;
    private String id;
    private String secretKey;
    private String bucketName;
    private String folderName;
    private AmazonS3 s3client;

    public getS3File(String folderName) {
        this.folderName = folderName;
    }

    public void run() {
        readProperties();
        setupCredentials();
        downloadCSV(folderName);
    }

    private void readProperties() {
        Properties properties = new Properties();
        InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("creds.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        id = properties.getProperty("id");
        secretKey = properties.getProperty("secretKey");
        bucketName = properties.getProperty("bucketName");
    }

    private void setupCredentials() {
        AWSCredentials credentials = new BasicAWSCredentials(id, secretKey);

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    private void downloadCSV(String folderName) {
        //Listing Objects
        ObjectListing objListing = s3client.listObjects(bucketName, folderName);
        for (S3ObjectSummary objSummary : objListing.getObjectSummaries()) {
            if (objSummary.getKey().endsWith("csv") && objSummary.getKey().startsWith(folderName + "/part")) {
                fileName = objSummary.getKey();
            }
            System.out.println(objListing);
        }

        // Downloading an Object
        S3Object s3object = s3client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();

        try {
            FileUtils.copyInputStreamToFile(inputStream, new File("C:\\Storage\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File already exists");
        }
    }

    public String getFileName() {
        return fileName;
    }
}