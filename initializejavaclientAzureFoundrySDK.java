import com.azure.ai.projects.ProjectsClient; 
import com.azure.ai.projects.ProjectsClientBuilder; 
import com.azure.identity.DefaultAzureCredentialBuilder;   
public class Main { 
  public static void main(String[] args) 
  {         
    String endpoint = System.getenv("PROJECT_ENDPOINT");
    ProjectsClient client = new ProjectsClientBuilder().endpoint(endpoint).credential(new DefaultAzureCredentialBuilder().build()).buildClient();
    System.out.println("✔ Java client initialized: " + client.getClass().getSimpleName());    
 }
