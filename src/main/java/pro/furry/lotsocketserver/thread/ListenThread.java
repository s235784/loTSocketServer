package pro.furry.lotsocketserver.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import pro.furry.lotsocketserver.util.CommandUtil;
import pro.furry.lotsocketserver.util.SpringContextUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author NuoTian
 * @date 2023/7/10
 */
@Slf4j
public class ListenThread implements Runnable{
    private final int port;
    private final ThreadPoolTaskExecutor executor;

    public ListenThread(int port, ThreadPoolTaskExecutor executor) {
        this.port = port;
        this.executor = executor;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        CommandUtil commandUtil = SpringContextUtil.getBean(CommandUtil.class);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("socket服务端开启，监听端口：{}", port);
            while (true) {
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(true);
                executor.execute(new ReceiveThread(socket, commandUtil));
            }
        } catch (IOException e) {
            log.info("socket服务启动异常", e);
        }
    }
}
