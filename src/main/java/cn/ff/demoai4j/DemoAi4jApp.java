package cn.ff.demoai4j;

import cn.ff.demoai4j.config.XiaozhiAgentConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(XiaozhiAgentConfig.class) // 显式导入配置类
public class DemoAi4jApp {


    public static void main(String[] args) {
        SpringApplication.run(DemoAi4jApp.class, args);
    }

}


