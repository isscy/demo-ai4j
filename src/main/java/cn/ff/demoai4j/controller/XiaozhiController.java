package cn.ff.demoai4j.controller;

import cn.ff.demoai4j.assistant.XiaozhiAgent;
import cn.ff.demoai4j.assistant.XiaozhiAgent2;
import cn.ff.demoai4j.entity.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "硅谷小智")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {

    @Autowired
    private XiaozhiAgent xiaozhiAgent;

    @Autowired
    private XiaozhiAgent2 xiaozhiAgent2;

    @Operation(summary = "对话")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatForm chatForm) {
        return xiaozhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }

    @Operation(summary = "对话2")
    @PostMapping("/chat2")
    public Flux<String> chat2(@RequestBody ChatForm chatForm) {
        return xiaozhiAgent2.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}
