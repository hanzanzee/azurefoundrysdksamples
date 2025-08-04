import com.azure.identity.DefaultAzureCredentialBuilder; 
import com.azure.ai.projects.AIProjectClient; 
import com.azure.ai.projects.AIProjectClientBuilder;
import com.azure.core.util.paging.PagedIterable;   
AIProjectClient client = new AIProjectClientBuilder().endpoint(System.getenv("PROJECT_ENDPOINT")).credential(new DefaultAzureCredentialBuilder().build()).buildClient();
PagedIterable<Deployment> deployments = client.getDeployments().list(); 
for (Deployment d : deployments) 
{ 
  System.out.printf("%s → model: %s, status: %s%n",d.getName(), d.getModelName(), d.getStatus());
}
