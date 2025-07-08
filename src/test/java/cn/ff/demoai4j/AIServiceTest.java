package cn.ff.demoai4j;

import cn.ff.demoai4j.assistant.Assistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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


    @Test
    public void de() {
        Set<String> pathSet = new HashSet<>();
        pathSet.add("$CALIBRE_HOME/bin:$PATH");

        pathSet = pathSet.stream().filter(StringUtils::hasText).map(e -> e.replaceAll("\\$", "\\\\\\$")).collect(Collectors.toSet());
        System.out.println(pathSet.toArray()[0]);
    }

}
