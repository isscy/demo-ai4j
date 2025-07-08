package cn.ff.demoai4j.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class XiaozhiAgentConfig {

    /*@Autowired
    private MongoChatMemoryStore mongoChatMemoryStore*/




    @Bean
    ChatMemoryProvider chatMemoryProviderXiaozhi() {
        log.info("初始化chatMemoryProviderXiaozhi");
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                //.chatMemoryStore(mongoChatMemoryStore)
                .build();
    }


    @PostConstruct
    public void init() {
        log.info("chatMemoryProviderXiaozhi 初始化完成");
    }

}
