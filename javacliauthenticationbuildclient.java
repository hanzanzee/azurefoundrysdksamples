import com.azure.ai.projects.ProjectsClient;
import com.azure.ai.projects.ProjectsClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;   
ProjectsClient client = new ProjectsClientBuilder().endpoint("https://<your-project-endpoint>.cognitiveservices.azure.com/").credential(new DefaultAzureCredentialBuilder().build()).buildClient();
