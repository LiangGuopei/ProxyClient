package cn.catver.proxy.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ProxyClient {
    public static int PORT = 44444;
    public static int PPORT = 30000; // 请求的端口
    public static String serverip = "localhost";
    public static int serverport = 25500;

    public static Properties properties = new Properties();
    public static void main(String[] args) throws IOException {
        { //读取配置
            Path path = Paths.get("config.properties");
            if(!new File("conifg.properties").exists()){ //判断配置文件是否存在
                properties.load(ProxyClient.class.getClassLoader().getResourceAsStream("config.properties"));
                properties.store(Files.newOutputStream(path),"?");
            }else{
                properties.load(Files.newInputStream(path));
            }

            PORT = (int) properties.getOrDefault("port",44444);
            PPORT = (int) properties.getOrDefault("pport",30000);
            serverip = (String) properties.getOrDefault("sip","localhost");
            serverport = (int) properties.getOrDefault("sport",25500);
        }

        ProxyClientServer pcs = new ProxyClientServer();
        pcs.startserver();
    }
}
