package pro.furry.lotsocketserver.socket;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import pro.furry.lotsocketserver.thread.ListenThread;

/**
 * @author NuoTian
 * @date 2023/7/8
 */
@Slf4j
@Component
public class LoTSocket {
    @Value("${app.port}")
    private int port;

    @Resource(name = "socketTaskExecutor")
    private ThreadPoolTaskExecutor executor;

    @Bean
    public void socketCreate() {
        // 创建新线程监听Socket连接
        executor.execute(new ListenThread(port, executor));
    }
}
