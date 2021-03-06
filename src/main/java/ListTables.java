/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



// [START bigquery_list_tables]
import com.google.api.gax.paging.Page;
import com.google.cloud.bigquery.*;
import com.google.cloud.bigquery.BigQuery.TableListOption;

public class ListTables {

    public static void main(String[] args) {
        // TODO(developer): Replace these variables before running the sample.
        String projectId = "fir-db-for-spring-boot-6d380";
        String datasetName = "babynames";
        listTables(projectId, datasetName);
        listModels(datasetName);
    }

    public static void listTables(String projectId, String datasetName) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

            DatasetId datasetId = DatasetId.of(projectId, datasetName);
            Page<Table> tables = bigquery.listTables(datasetId, TableListOption.pageSize(100));
            tables.iterateAll().forEach(table -> System.out.print(table.getTableId().getTable() + "\n"));

            System.out.println("Tables listed successfully.");
        } catch (BigQueryException e) {
            System.out.println("Tables were not listed. Error occurred: " + e.toString());
        }
    }

    public static void listModels(String datasetName) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

            Page<Model> models = bigquery.listModels(datasetName, BigQuery.ModelListOption.pageSize(100));
            if (models == null) {
                System.out.println("Dataset does not contain any models.");
                return;
            }
            models
                    .iterateAll()
                    .forEach(model -> System.out.printf("Success! Model ID: %s", model.getModelId()));
        } catch (BigQueryException e) {
            System.out.println("Models not listed in dataset due to error: \n" + e.toString());
        }
    }
}

// [END bigquery_list_tables]