package devops.kafka.producer;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.AnalysisException;





public class DataShower {
    private SparkSession spark;
    private Dataset<Row> ds;

    public DataShower() {
        spark = Session.getInstance().getSession();
        ds = InputFile.getInstance(spark).getDataset();
    }


    void printdata() {
        ds.printSchema();
    }


}