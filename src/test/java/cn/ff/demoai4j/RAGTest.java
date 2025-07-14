package cn.ff.demoai4j;

import dev.langchain4j.community.model.dashscope.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;

@Slf4j
@SpringBootTest
public class RAGTest {


    @Test
    public void testReadDocument() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器TextDocumentParser对文档进行解析
        // 加载单个文档
        //Document document = FileSystemDocumentLoader.loadDocument("E:/logs/eda-crawler/zy_install.log", new TextDocumentParser());
        //System.out.println(document.text());
        // 从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.txt");
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("E:/logs/eda-crawler/", pathMatcher, new TextDocumentParser());
        System.out.println(documents.size());
        documents.forEach(e -> System.out.println(e.text()));

        Document documentPdf = FileSystemDocumentLoader.loadDocument("E:/logs/eda-crawler/Introduction to Linear Algebra 4th edition.PDF", new ApachePdfBoxDocumentParser());
        System.out.println(documentPdf);

    }


    /**
     * 加载文档并存入向量数据库
     */
    @Test
    public void testReadDocumentAndStore() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析(TextDocumentParser)
        /*Path filePath = getPathFromResources("knowledge", "人工智能.md");
        Document document = FileSystemDocumentLoader.loadDocument(filePath);*/
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/人工智能.md", new TextDocumentParser());
        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //1、分割文档：默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
        //DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
        //2、文本向量化：使用一个LangChain4j内置的轻量化向量模型对每个文本片段进行向量化
        //3、将原始文本和向量存储到向量数据库中(InMemoryEmbeddingStore)
        EmbeddingStoreIngestor.ingest(document, embeddingStore);
        //查看向量数据库内容
        log.info("查看向量数据库内容 {}", embeddingStore.serializeToJson());
    }


    /**
     * 文档分割
     */
    @Test
    public void testDocumentSplitter() {
        Document document = ClassPathDocumentLoader.loadDocument("knowledge/人工智能.md", new TextDocumentParser());
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //自定义文档分割器
        //按段落分割文档：每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
        //注意：当段落长度总和小于设定的最大长度时，就不会有重叠的必要。
        //token分词器：按token计算
        DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30, new HuggingFaceTokenizer());
        //按字符计算
        //DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30);
        EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter)
                .build()
                .ingest(document);
    }


    /**
     * token计算
     */
    @Test
    public void testTokenCount() {
        String text = "这是一个示例文本，用于测试 token 长度的计算。";
        UserMessage userMessage = UserMessage.userMessage(text);
        //计算 token 长度
        QwenTokenizer tokenizer1 = new QwenTokenizer(System.getenv("DASH_SCOPE_API_KEY"),"qwen-max");
        HuggingFaceTokenizer tokenizer2 = new HuggingFaceTokenizer();
        int count1 = tokenizer1.estimateTokenCountInMessage(userMessage);
        int count2 = tokenizer2.estimateTokenCountInMessage(userMessage);
        log.info("token 长度- qwen：{}", count1);
        log.info("token 长度- HuggingFaceTokenizer：{}", count2);
    }


    public Path getPathFromResources(String dirName, String resourceFileName) {
        URL resource = getClass().getClassLoader().getResource(dirName + "/" + resourceFileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        }
        File file = new File(resource.getFile());
        Path path = file.toPath();
        log.info("file path:{}", path);
        return path;
    }


}
