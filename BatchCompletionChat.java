import com.azure.ai.projects.AIProjectClient;
import com.azure.ai.projects.AIProjectClientBuilder;
import com.azure.ai.inference.ChatCompletionsClient; 
import com.azure.identity.DefaultAzureCredentialBuilder;
import java.util.List;
String endpoint= System.getenv("PROJECT_ENDPOINT");
String deployment = System.getenv("MODEL_DEPLOYMENT_NAME");   
AIProjectClient project = new AIProjectClientBuilder().endpoint(endpoint).credential(new DefaultAzureCredentialBuilder().build()).buildClient();
ChatCompletionsClient client = project.getInference().getAzureOpenAIClient();
var response = client.create( deployment,List.of(new com.azure.ai.inference.models.ChatMessage("system","You are a concise summarizer."),
                                                 new com.azure.ai.inference.models.ChatMessage("user","Summarize the key benefits of solar energy.")   
                                                ) );   
System.out.println(response.getChoices().get(0).getMessage().getContent()); 
