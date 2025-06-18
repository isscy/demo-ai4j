package cn.ff.demoai4j;

import cn.ff.demoai4j.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class AIServiceTest {

   /* @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testChat() {
        //创建AIService
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        //调用service的接口
        String answer = assistant.chat("Hello");
        System.out.println(answer);
    }*/


    //直接注入Assistant对象
    @Autowired
    private Assistant assistant;
    @Test
    public void testChat() {
        String answer = assistant.chat("Hello");
        System.out.println(answer);
    }

}
