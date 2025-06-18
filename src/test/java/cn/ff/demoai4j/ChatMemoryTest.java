package cn.ff.demoai4j;

import cn.ff.demoai4j.assistant.Assistant;
import cn.ff.demoai4j.assistant.MemoryChatAssistant;
import cn.ff.demoai4j.assistant.SeparateChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ChatMemoryTest {

    @Autowired
    private QwenChatModel qwenChatModel;
    @Autowired
    private MemoryChatAssistant memoryChatAssistant;
    @Autowired
    private SeparateChatAssistant separateChatAssistant;


    @Test
    public void testChatMemory1() {
        //第一轮对话
        UserMessage um = UserMessage.userMessage("我是张三");
        ChatResponse chatResponse = qwenChatModel.chat(um);
        AiMessage aiMessage = chatResponse.aiMessage();
        System.out.println(aiMessage.text());
        //第二轮对话
        UserMessage um2 = UserMessage.userMessage("你知道我是谁吗");
        ChatResponse chatResponse2 = qwenChatModel.chat(Arrays.asList(um, aiMessage, um2));

        AiMessage aiMessage2 = chatResponse2.aiMessage();
        System.out.println(aiMessage2.text());
    }


    @Test
    public void testChatMemory2() {
        //创建chatMemory
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        //创建AIService
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(qwenChatModel)
                .chatMemory(chatMemory)
                .build();
        //调用service的接口
        String answer1 = assistant.chat("我是张三");
        System.out.println(answer1);
        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }


    @Test
    public void testChatMemory4() {
        String answer1 = memoryChatAssistant.chat("我是张三");
        System.out.println(answer1);
        String answer2 = memoryChatAssistant.chat("我是谁");
        System.out.println(answer2);
    }
    @Test
    public void testChatMemory5() {
        String a1 = separateChatAssistant.chat(1, "我是张三");
        System.out.println(a1);
        String a2 = separateChatAssistant.chat(1, "我是谁");
        System.out.println(a2);
        String a3 = separateChatAssistant.chat(2, "我是谁");
        System.out.println(a3);

    }
}
