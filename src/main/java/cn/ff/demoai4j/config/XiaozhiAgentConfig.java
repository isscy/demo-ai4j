package cn.ff.demoai4j.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


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


    @Bean
    ContentRetriever contentRetrieverXiaozhi() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析
        Document document1 = ClassPathDocumentLoader.loadDocument("knowledge/医院信息.md", new TextDocumentParser());
        Document document2 = ClassPathDocumentLoader.loadDocument("knowledge/科室信息.md", new TextDocumentParser());
        Document document3 = ClassPathDocumentLoader.loadDocument("knowledge/神经内科.md", new TextDocumentParser());
        List<Document> documents = Arrays.asList(document1, document2, document3);
        //使用内存向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //使用默认的文档分割器
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        //从嵌入存储（EmbeddingStore）里检索和查询内容相关的信息
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }


}
