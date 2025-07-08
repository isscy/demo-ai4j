package cn.ff.demoai4j;

import cn.ff.demoai4j.assistant.SeparateChatAssistant;
import cn.ff.demoai4j.assistant.UserMessageChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;
    @Autowired
    private UserMessageChatAssistant userMessageChatAssistant;


    @Test
    public void testSystemMessage() {
        String answer = separateChatAssistant.chat(3, "今天几号");
        System.out.println(answer);
    }


    @Test
    public void testUserMessage() {
        String answer = userMessageChatAssistant.chat2(1, "我是环环");
        System.out.println(answer);
        String answer2 = userMessageChatAssistant.chat2(1, "我是谁");
        System.out.println(answer2);
        String answer3 = userMessageChatAssistant.chat3(2, "我是谁，我多大了", "翠花", 18);
        System.out.println(answer3);
    }


}


