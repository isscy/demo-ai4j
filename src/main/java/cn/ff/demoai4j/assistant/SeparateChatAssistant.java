package cn.ff.demoai4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * 隔离聊天记忆
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface SeparateChatAssistant {


    /**
     * 分离聊天记录
     *
     * @param memoryId    聊天id
     * @param userMessage 用户消息
     */
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
