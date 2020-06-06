package devops.kafka.producer;


import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.SparkSession;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class InputFile{

    private static Dataset <Row> ds;
    private static InputFile instance;
  
  
    private InputFile(SparkSession spark) {
        //create dataset
      if (ds == null) {
        ds = spark.read().option("inferSchema", true).option("header", true)
            .csv("s3a://jays-testing-bucket-devops/Oil_and_Gas_Annual_Production__Beginning_2001.csv").cache();
        try {
          ds.createTempView("tempset");
        } catch (AnalysisException e) {
          System.err.println(e.getMessage());
        }
      }
    }

  
  
    public static InputFile getInstance(SparkSession spark) {
      if (instance == null) {
        instance = new InputFile(spark);
      }
      return instance;
    }
  
    public Dataset<Row> getDataset() {
      return ds;
    }
  
  
}