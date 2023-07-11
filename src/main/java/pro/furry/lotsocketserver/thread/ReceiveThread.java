package pro.furry.lotsocketserver.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author NuoTian
 * @date 2023/7/8
 */
@Slf4j
public class ReceiveThread implements Runnable {
    private final Socket socket;

    public ReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.info("{} 建立连接", socket.getRemoteSocketAddress());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream output = socket.getOutputStream()) {
            while (true) {
                StringBuilder builder = new StringBuilder();
                int value;
                while ((value = reader.read()) != -1) {
                    builder.append((char) value);
                    if (builder.length() > 2 &&
                            "\\n".equals(builder.substring(builder.length() - 2))) // \n作为终止符
                        break;
                }
                if (value == -1) // 连接关闭
                    break;

                // 处理接收到的数据
                if (builder.length() > 2) {
                    String receiveString = builder.substring(0, builder.length() - 2);
                    log.info("接收数据：{}", receiveString);

                    output.write("你好".getBytes(StandardCharsets.UTF_8));
                }
            }

            log.info("{} 关闭连接", socket.getRemoteSocketAddress());
            socket.close();
        } catch (IOException e) {
            log.error("创建IO流时出现错误", e);
        }
    }
}
