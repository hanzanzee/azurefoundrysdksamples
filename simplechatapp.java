import com.azure.ai.projects.AIProjectClient; 
import com.azure.ai.projects.AIProjectClientBuilder; 
import com.azure.ai.inference.ChatCompletionsClient; 
import com.azure.identity.DefaultAzureCredentialBuilder;   
public class ChatExample {     
  public static void main(String[] args) {
    String endpoint   = System.getenv("PROJECT_ENDPOINT");
    String deployment = System.getenv("MODEL_DEPLOYMENT_NAME");
    // Build the project client         
    AIProjectClient project = new AIProjectClientBuilder().endpoint(endpoint).credential(new DefaultAzureCredentialBuilder().build()).buildClient();
    // Acquire the OpenAI inference client         
    ChatCompletionsClient client =project.getInference().getAzureOpenAIClient();
    // Send chat messages         
    var response = client.create(
      deployment,
      Arrays.asList(
        new ChatMessage("system", "You are a Java expert assistant."),
        new ChatMessage("user",   "How do I read a file in Java?")
      )
    );           
    // Print the model’s answer         
    System.out.println(response.getChoices().get(0).getMessage().getContent());
  } 
} 
