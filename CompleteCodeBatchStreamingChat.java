import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.ai.projects.AIProjectClient;
import com.azure.ai.projects.AIProjectClientBuilder;
import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatMessage;
import com.azure.core.util.paging.StreamingResponse;

import java.util.List;

public class PracticalPreview {

    public static void main(String[] args) {
        // 1. Initialize project and chat client
        AIProjectClient project = new AIProjectClientBuilder()
                .endpoint(System.getenv("PROJECT_ENDPOINT"))
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();

        ChatCompletionsClient client = project.getInference().getAzureOpenAIClient();

        // 2. Discover a successful deployment
        String deployment = project.getDeployments().list().stream()
                .filter(d -> d.getStatus().equalsIgnoreCase("succeeded"))
                .findFirst()
                .map(d -> d.getName())
                .orElseThrow();

        System.out.println("Using deployment: " + deployment);

        // 3. Batch chat
        var batchResp = client.create(
                deployment,
                List.of(
                        new ChatMessage("system", "You are a clear assistant."),
                        new ChatMessage("user", "Explain how rainbows form.")
                )
        );

        System.out.println("\nBatch response:");
        System.out.println(batchResp.getChoices().get(0).getMessage().getContent());

        // 4. Streaming chat
        System.out.println("\nStreaming response:");

        ChatCompletionsOptions opts = new ChatCompletionsOptions()
                .setDeploymentName(deployment)
                .setMessages(List.of(
                        new ChatMessage("system", "You are a storyteller."),
                        new ChatMessage("user", "Tell a brief fable about teamwork.")
                ))
                .setStream(true);

        StreamingResponse<com.azure.ai.inference.models.StreamingChatCompletionsUpdate> stream =
                client.completeStreaming(opts);

        stream.forEach(update ->
                update.getChoices().forEach(choice -> {
                    String delta = choice.getDelta().getContent();
                    if (delta != null) System.out.print(delta);
                })
        );

        System.out.println();
    }
}
