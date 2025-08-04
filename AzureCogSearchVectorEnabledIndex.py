from azure.core.credentials import AzureKeyCredential
from azure.search.documents.indexes import SearchIndexClient
from azure.search.documents.indexes.models import (
    SearchIndex,
    SimpleField,
    SearchableField,
    SearchFieldDataType,
    VectorField,
    VectorSearchAlgorithmConfiguration,
    VectorSearch
)

# Azure Cognitive Search service configuration
endpoint = "https://<your-search-service>.search.windows.net"
admin_key = "<your-admin-key>"
credential = AzureKeyCredential(admin_key)

# Initialize the index client
index_client = SearchIndexClient(endpoint=endpoint, credential=credential)

# 1. Define the vector field for storing 1536-dimensional embeddings
vector_field = VectorField(
    name="contentVector",
    dimensions=1536,
    vector_search_configuration="vector_config"
)

# 2. Define the text fields and the primary key
fields = [
    SimpleField(name="id", type=SearchFieldDataType.String, key=True, filterable=True),
    SearchableField(name="content", type=SearchFieldDataType.String),
    vector_field
]

# 3. Define vector search configuration using HNSW (Hierarchical Navigable Small World)
vector_search = VectorSearch(
    algorithm_configurations=[
        VectorSearchAlgorithmConfiguration(
            name="vector_config",
            kind="hnsw",
            hnsw_parameters={
                "m": 4,
                "efConstruction": 400,
                "metric": "cosine"
            }
        )
    ]
)

# 4. Create or update the index with the fields and vector configuration
index = SearchIndex(
    name="my-vec-index",
    fields=fields,
    vector_search=vector_search
)

index_client.create_or_update_index(index)
print("✅ Vector index 'my-vec-index' created or updated successfully.")
