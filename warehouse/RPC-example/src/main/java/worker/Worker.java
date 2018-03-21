package main.java.worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by stone on 24/12/2016.
 */
public class Worker {
    private static boolean isRunning = false;

    public static void run(String host,int port) {
        Socket socket=null;
        try {
            socket = new Socket(host, port);
            System.out.println("working连接上了client"+socket.getRemoteSocketAddress());

            ObjectInputStream objIS = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objOS = new ObjectOutputStream(socket.getOutputStream());

            isRunning = true;
            while (isRunning) {
                try {
                    String className = (String) objIS.readObject();
                    String methodName = (String) objIS.readObject();
                    Object[] arg = (Object[]) objIS.readObject();

                    System.out.println("接受到指令"+className+":"+methodName+":");

                    Object rs = handleRequest(className,methodName,arg);

                    objOS.writeObject(rs);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    isRunning=false;
                }
            }
        } catch (IOException e) {
            System.out.println("连接失败");
            e.printStackTrace();
        }finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object handleRequest(String className,String methodName,Object[] args){
        try {
            Class service = Class.forName(className + "Impl");
            Constructor constructor = service.getDeclaredConstructor();
            Object obj = constructor.newInstance();

            Method method = service.getDeclaredMethod(methodName);
            Object rs = method.invoke(obj, args);

            return rs;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}

