import com.google.api.gax.paging.Page;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQuery.DatasetListOption;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;

public class ListDatasets {

    public static void main(String[] args) {
        // TODO(developer): Replace these variables before running the sample.
        String projectId = "fir-db-for-spring-boot-6d380";
        listDatasets(projectId);
    }

    public static void listDatasets(String projectId) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

            Page<Dataset> datasets = bigquery.listDatasets(projectId, DatasetListOption.pageSize(100));
            if (datasets == null) {
                System.out.println("Dataset does not contain any models");
                return;
            }
            datasets
                    .iterateAll()
                    .forEach(
                            dataset -> System.out.printf("Success! Dataset ID: %s ", dataset.getDatasetId()));
        } catch (BigQueryException e) {
            System.out.println("Project does not contain any datasets \n" + e.toString());
        }
    }
}