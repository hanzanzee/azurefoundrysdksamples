import com.azure.ai.projects.AIProjectClient; 
import com.azure.ai.projects.AIProjectClientBuilder; 
import com.azure.ai.inference.ChatCompletionsClient; 
import com.azure.identity.DefaultAzureCredentialBuilder;   
public class SwitchModelExample 
{     
  public static void main(String[] args) {
    String endpoint   = System.getenv("PROJECT_ENDPOINT");         
    String deployment = System.getenv().getOrDefault("MODEL_DEPLOYMENT_NAME", "ChatGPT4");           
    AIProjectClient projectClient = new AIProjectClientBuilder().endpoint(endpoint).credential(new DefaultAzureCredentialBuilder().build()).buildClient();
    ChatCompletionsClient client = projectClient.getInference().getAzureOpenAIClient();
    var messages = List.of(new ChatMessage("system", "You are an expert assistant."),new ChatMessage("user",   "Summarize the latest AI news for me."));
    var response = client.create(deployment, messages);
    System.out.println(response.getChoices().get(0).getMessage().getContent());    
  }
