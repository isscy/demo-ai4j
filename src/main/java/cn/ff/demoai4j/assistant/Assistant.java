package cn.ff.demoai4j.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * 因为配置文件中同时配置了多个模型，所以需要明确EXPLICIT模型的beanName=qwenChatModel
 */
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel")
public interface Assistant {
    String chat(String userMessage);
}
