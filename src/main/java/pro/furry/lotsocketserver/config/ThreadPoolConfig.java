package pro.furry.lotsocketserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author NuoTian
 * @date 2023/7/8
 */
@Configuration
public class ThreadPoolConfig {
    @Bean("socketTaskExecutor")
    public ThreadPoolTaskExecutor redisTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setKeepAliveSeconds(0);
        executor.setAwaitTerminationSeconds(10);
        executor.setThreadNamePrefix("Socket-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
