
// [START bigquery_extract_table_compressed]
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.ExtractJobConfiguration;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.TableId;

// Sample to extract a compressed table
public class ExtractTableCompressed {

    public static void main(String[] args) {
        // TODO(developer): Replace these variables before running the sample.
        String projectName = "fir-db-for-spring-boot-6d380";//"fir-db-for-spring-boot-6d380";
        String datasetName = "babynames";
        String tableName = "names_2014";
        String bucketName = "fir-db-for-spring-boot-6d380.appspot.com";
//        String bucketName = "names_bucket_fir-db-for-spring-boot";
        String destinationUri = "gs://" + bucketName + "/code/names_2014";
        // For more information on export formats available see:
        // https://cloud.google.com/bigquery/docs/exporting-data#export_formats_and_compression_types
        String compressed = "gzip";
        // For more information on Job see:
        // https://googleapis.dev/java/google-cloud-clients/latest/index.html?com/google/cloud/bigquery/package-summary.html
        String dataFormat = "CSV";

        extractTableCompressed(
                projectName, datasetName, tableName, destinationUri, dataFormat, compressed);
    }

    public static void extractTableCompressed(
            String projectName,
            String datasetName,
            String tableName,
            String destinationUri,
            String dataFormat,
            String compressed) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

            TableId tableId = TableId.of(projectName, datasetName, tableName);

            ExtractJobConfiguration extractConfig =
                    ExtractJobConfiguration.newBuilder(tableId, destinationUri)
                            .setCompression(compressed)
                            .setFormat(dataFormat)
                            .build();

            Job job = bigquery.create(JobInfo.of(extractConfig));

            // Blocks until this job completes its execution, either failing or succeeding.
            Job completedJob = job.waitFor();
            if (completedJob == null) {
                System.out.println("Job not executed since it no longer exists.");
                return;
            } else if (completedJob.getStatus().getError() != null) {
                System.out.println(
                        "BigQuery was unable to extract due to an error: \n" + job.getStatus().getError());
                return;
            }
            System.out.println("Table extract compressed successful");
        } catch (BigQueryException | InterruptedException e) {
            System.out.println("Table extraction job was interrupted. \n" + e.toString());
        }
    }
}
// [END bigquery_extract_table_compressed]
