package com.example.lib;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class ServerSocketThread extends Thread{
    private BufferedReader in;
    private PrintWriter mywrite;
    private PrintWriter ywrite;
    private Socket mysocket;
    private Socket ysocket;
    int playernum = 2;
    public ServerSocketThread(Socket mysocket,Socket ysocket){
        this.mysocket = mysocket;
        this.ysocket = ysocket;
    }

    @Override
    public void run(){
        try {
            in = new BufferedReader(new InputStreamReader(mysocket.getInputStream(),"UTF-8"));
            mywrite = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(mysocket.getOutputStream(), "UTF-8")), true);
            ywrite = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(ysocket.getOutputStream(), "UTF-8")), true);

            //如果已经达到两个人，开始游戏
            if(MyClass.sockets.size() == 2){
                System.out.println("start now");
                mywrite.println("start");
            }

            String content="";
            while ((content = in.readLine()) != null) {
                //4.和客户端通信
                if(content.equals("end")){
                    //说明有一个人死了
                    System.out.println("someone has died");
                    MyClass.sockets.remove(mysocket);
                    if(MyClass.sockets.size() == 0){
                        break;
                    }
                }else{
                    System.out.println("trans now");
                    System.out.println(content);
                    //进行二者之间数据的传输
                    ywrite.println(content);

                }
            }

            if(MyClass.sockets.size() == 0){
                //说明两个人都死了
                mywrite.println("gameover");
                ywrite.println("gameover");
                System.out.println("gameover");
                mysocket.shutdownInput();
                mysocket.shutdownOutput();
                mysocket.close();
                ysocket.shutdownInput();
                ysocket.shutdownOutput();
                ysocket.close();
//                MyClass.socket1.shutdownInput();
//                MyClass.socket1.shutdownOutput();
//                MyClass.socket1.close();
//                MyClass.socket2.shutdownInput();
//                MyClass.socket2.shutdownOutput();
//                MyClass.socket2.close();
//                MyClass.socket2 = null;
//                MyClass.socket1 = null;
                MyClass.socket1 = null;
                MyClass.socket2=null;
                MyClass.sockets.clear();
            }


        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
