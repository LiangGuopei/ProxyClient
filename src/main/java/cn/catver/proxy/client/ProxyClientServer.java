package cn.catver.proxy.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyClientServer {
    public ProxyClientServer(){}
    public ProxyClientServer(Socket s){
        client = new Client();
        server = new Server();
        client.client = s;
    }
    public void start(){
        try{
            server.server = new Socket(ProxyClient.serverip,ProxyClient.serverport);

            PrintStream ps = new PrintStream(server.server.getOutputStream());
            ps.println(ProxyClient.PPORT);

            if(server.server.isClosed()){
                return;
            }
            client.start();
            server.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    class Client extends Thread{
        public Socket client;

        @Override
        public void run() {
            try {
                int data = -1;
                while ((data = client.getInputStream().read()) != -1){
                    server.server.getOutputStream().write(data);
                }
                server.server.close();
            } catch (IOException e) {
                return;
            }
        }
    }
    class Server extends Thread{
        public Socket server;

        @Override
        public void run() {
            try {
                int data = -1;
                while ((data = server.getInputStream().read()) != -1){
                    client.client.getOutputStream().write(data);
                }
                client.client.close();
            } catch (IOException e) {
                return;
            }
        }
    }
    Client client;
    Server server;

    // --------------------------------

    static ServerSocket serverSocket;
    public void startserver(){
        try{
            serverSocket = new ServerSocket(ProxyClient.PORT);
            System.out.println(String.format("启动成功！端口：%d退出请强制", ProxyClient.PORT));
            while (true){
                new ProxyClientServer(serverSocket.accept()).start();
            }
        }catch (Exception e){

        }
    }
}
