package main.java.api;


import main.java.client.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * Created by stone on 24/12/2016.
 */
public class DynamicProxy implements InvocationHandler {
    Class obj = null;

    public DynamicProxy(Class obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ObjectInputStream objIS = null;
        ObjectOutputStream objOS = null;

        objOS = Client.getObjectOutputStream(0);
        objOS.writeObject(obj.getName());
        objOS.writeObject(method.getName());
        objOS.writeObject(args);

        objIS = Client.getObjectInputStream(0);
        Object rs = objIS.readObject();

        return rs;
    }
}
