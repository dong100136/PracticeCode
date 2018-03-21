package client;

import api.ServiceFactory;
import api.User;
import api.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by stone on 24/12/2016.
 */
public class Client {
    private static boolean isRunning = false;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static List<Socket> workerList = new ArrayList<Socket>();

    private static List<ObjectInputStream> objectInputStreamList = new ArrayList<ObjectInputStream>();

    private static List<ObjectOutputStream> objectOutputStreamList = new ArrayList<ObjectOutputStream>();

    //随机获取一个socket
    public static ObjectInputStream getObjectInputStream(int index){
        return objectInputStreamList.get(index);
    }

    public static ObjectOutputStream getObjectOutputStream(int index){
        return objectOutputStreamList.get(index);
    }

    public static void run(final int port) {
        isRunning = true;
        Thread t = new Thread(new Runnable() {
            public void run() {
                ServerSocket serverSocket=null;
                try {
                    serverSocket = new ServerSocket(port);
                    System.out.println("listening in:"+serverSocket.getLocalSocketAddress());

                    while (isRunning){
                        Socket socket = serverSocket.accept();
                        workerList.add(socket);

                        ObjectOutputStream objOS = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream objIS = new ObjectInputStream(socket.getInputStream());
                        objectInputStreamList.add(objIS);
                        objectOutputStreamList.add(objOS);
                        System.out.printf("连接成功，现在有%d台worker,地址%s\n",workerList.size(),socket.getRemoteSocketAddress());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (serverSocket != null) {
                        try {
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t.start();

        //根据用户指令执行函数
        while (isRunning){
            Scanner scanner = new Scanner(System.in);
            System.out.println("输入指令:(1.执行getUser，2.退出)");

            Integer i = scanner.nextInt();
            if (i.equals(1)){
                UserService userService = ServiceFactory.getUserService();
                User user = userService.getUser();
                System.out.println(user);
            }else{
                for (Socket socket:workerList){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                isRunning=false;
            }
        }
    }
}

