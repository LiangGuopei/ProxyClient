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

    public static String version = "ver1";

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

            PORT = progetint("port",44444);
            PPORT = progetint("pport",30000);
            serverip = properties.getOrDefault("sip","localhost").toString();
            serverport = progetint("sport",25500);
        }

        ProxyClientServer pcs = new ProxyClientServer();
        pcs.startserver();
    }

    public static int progetint(String key,int p){
        return Integer.getInteger(properties.getOrDefault(key,p).toString(),p);
    }
}
