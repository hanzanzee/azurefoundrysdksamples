import com.azure.core.credential.AzureKeyCredential;
import com.azure.search.documents.indexes.SearchIndexClient;
import com.azure.search.documents.indexes.SearchIndexClientBuilder;
import com.azure.search.documents.indexes.models.*;

import java.util.Arrays;

public class CreateVectorIndex {

    public static void main(String[] args) {
        // 1. Define your Azure Cognitive Search endpoint and admin key
        String endpoint = "https://<your-search-service>.search.windows.net";
        String adminKey = "<your-admin-key>";

        // 2. Initialize SearchIndexClient to interact with the search service
        SearchIndexClient indexClient = new SearchIndexClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(adminKey))
                .buildClient();

        // 3. Define the vector field that will hold 1536-dimensional embeddings
        SearchField vectorField = new SearchField("contentVector", SearchFieldDataType.collection(SearchFieldDataType.SINGLE))
                .setVectorSearchDimensions(1536)
                .setVectorSearchConfiguration("vector_config");

        // 4. Define regular text fields
        SearchField idField = new SearchField("id", SearchFieldDataType.STRING)
                .setKey(true)
                .setFilterable(true);

        SearchField contentField = new SearchField("content", SearchFieldDataType.STRING)
                .setSearchable(true);

        // 5. Combine all fields
        java.util.List<SearchField> fields = Arrays.asList(idField, contentField, vectorField);

        // 6. Configure vector search using HNSW algorithm
        VectorSearchAlgorithmConfiguration hnswConfig = new VectorSearchAlgorithmConfiguration()
                .setName("vector_config")
                .setKind(VectorSearchAlgorithmKind.HNSW)
                .setHnswParameters(
                        new HnswParameters()
                                .setM(4)
                                .setEfConstruction(400)
                                .setMetric(VectorSearchAlgorithmMetric.COSINE)
                );

        VectorSearch vectorSearch = new VectorSearch()
                .setAlgorithmConfigurations(Arrays.asList(hnswConfig));

        // 7. Create the index object
        SearchIndex index = new SearchIndex("my-vec-index")
                .setFields(fields)
                .setVectorSearch(vectorSearch);

        // 8. Push index to Azure Cognitive Search
        indexClient.createOrUpdateIndex(index);
        System.out.println("✅ Vector index 'my-vec-index' created or updated successfully.");
    }
}
