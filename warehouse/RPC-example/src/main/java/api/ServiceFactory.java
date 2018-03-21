package api;

import java.lang.reflect.Proxy;

/**
 * Created by stone on 24/12/2016.
 */
public class ServiceFactory {
    public static UserService getUserService(){
        UserService userService = new UserService() {
            public User getUser() {
                return null;
            }
        };

        ClassLoader serviceCL = UserService.class.getClassLoader();
        Class<?>[] serviceInterface = userService.getClass().getInterfaces();
        DynamicProxy proxy = new DynamicProxy(UserService.class);

        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(serviceCL,serviceInterface,proxy);
        return userServiceProxy;
    }
}
