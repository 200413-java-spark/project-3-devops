package devops.kafka.producer;


import org.apache.spark.sql.SparkSession;


public class Session {
    private static SparkSession spark;
    private static Session instance;
    
  
    private Session() {
      // create SparkSession
      if (spark == null) {
        System.out.println("hello");										   
        spark = new SparkSession.Builder().appName("testapp").master("local").getOrCreate();
        spark.sparkContext().setLogLevel("WARN");
        spark.sparkContext().hadoopConfiguration().addResource("conf.xml");
        spark.sparkContext().hadoopConfiguration().set("fs.s3.canned.acl", "PublicReadWrite");
        System.out.println("hello2");
      }
    }
  
    public static Session getInstance() {
      if (instance == null) {
        instance = new Session();
      }
      return instance;
    }
  
    public SparkSession getSession() {
      return spark;
    }
  
    
}