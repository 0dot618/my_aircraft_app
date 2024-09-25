package com.example.lib;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClass {
    public static void main(String[] args){
        new MyClass();
    }
    static List<Socket> sockets = new ArrayList<>();
    static List<Socket> mList = new ArrayList<>();
     static  Socket socket1 = null;
     static  Socket socket2 = null;
    ServerSocket serverSocket ;
    boolean flag = false;
    public MyClass(){

        try {
            // 1. 创建ServerSocket
            serverSocket = new ServerSocket(9999);
            System.out.println("--Listener Port: 9999--");
            while (true) {
                if(sockets.size() == 2){
                    System.out.println("");
                }
                //2.等待接收请求，这里接收客户端的请求
                    if(sockets.size() < 2) {
                        System.out.println("waiting for connect");
                        if(socket1 == null){
                            socket1 = serverSocket.accept();
                            if(sockets.size() == 1) flag = true;
                            sockets.add(socket1);
                        }else{
                            socket2 = serverSocket.accept();
                            if(sockets.size() == 1) flag = true;
                            sockets.add(socket2);
                        }
                        System.out.println("has one player");
                    }
                    else if(sockets.size() == 2 && flag){
                        System.out.println("has two players");
                        new ServerSocketThread(socket1,socket2).start();
                        new ServerSocketThread(socket2,socket1).start();
//                        socket1 = null;
//                        socket2 = null;
                        flag = false;
//                        sockets.clear();
                    }
                }
                //3.开启子线程线程处理和客户端的消息传输


        }catch (Exception ex){
            ex.printStackTrace();
        }

//        Thread gameThread = new Thread(() -> {
//            try {
//                acceptConnections2(serverSocket);
//            } catch (IOException | ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
////        userThread.start();
//        gameThread.start();

    }
//    private static void acceptConnections2(ServerSocket serverSocket) throws IOException, ClassNotFoundException {
//        while (true) {
//            System.out.println("Waiting client connect");
//            // 进行连接请求的接受
//            Socket socket1 = serverSocket.accept();
//            Socket socket2 = serverSocket.accept();
//            mList.add(socket1);
//            mList.add(socket2);
//            System.out.println(mList.size());
//
////             处理Socket连接
//              new ServerSocketThread(socket1,socket2).start();
//              new ServerSocketThread(socket2,socket1).start();
//        }
//    }
}