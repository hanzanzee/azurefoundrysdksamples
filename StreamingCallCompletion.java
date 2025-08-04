import com.azure.ai.projects.AIProjectClient;
import com.azure.ai.projects.AIProjectClientBuilder; 
import com.azure.ai.inference.ChatCompletionsClient; 
import com.azure.core.util.paging.StreamingResponse;
import com.azure.identity.DefaultAzureCredentialBuilder; 
import com.azure.ai.inference.models.ChatCompletionsOptions; 
import com.azure.ai.inference.models.ChatMessage;
import com.azure.ai.inference.models.StreamingChatCompletionsUpdate;   
List<ChatMessage> history = List.of(   
  new ChatMessage("system","You are an engaging storyteller."),   
  new ChatMessage("user","Tell me a short fable about teamwork.")                                                                                                              );   
ChatCompletionsOptions options = new ChatCompletionsOptions().setDeploymentName(System.getenv("MODEL_DEPLOYMENT_NAME")).setMessages(history);
AIProjectClient project = new AIProjectClientBuilder().endpoint(System.getenv("PROJECT_ENDPOINT")).credential(new DefaultAzureCredentialBuilder().build()).buildClient();
ChatCompletionsClient client = project.getInference().getAzureOpenAIClient();   
StreamingResponse<StreamingChatCompletionsUpdate> stream =client.completeStreaming(options);   
for (StreamingChatCompletionsUpdate update : stream) { 
  update.getChoices().forEach(choice -> { 
    String delta = choice.getDelta().getContent();
    if (delta != null) System.out.print(delta);   });
} 
