# bigquery_practice
# bigquery_practice get data from bigquery to storage bucket
# Set google application configuration by using below command
  SET GOOGLE_APPLICATION_CREDENTIALS=*path of service JSON key*

To get data from table and copy it to compressed zip file which contains csv formted table data
  run ExtractTableCompressed.java
  Modify below parameters 
        String projectName = "fir-db-for-spring-boot-6d380";  
        String datasetName = "babynames";
        String tableName = "names_2014";
        String bucketName = "fir-db-for-spring-boot-6d380.appspot.com";
        String destinationUri = "gs://" + bucketName + "/code/names_2014";

Check zip file will be copied in storage bucket
