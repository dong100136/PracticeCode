import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

interface DBQuery2 {
    abstract String doQuery(String name);
}

public class DynaticProxy {
    public static void main(String[] args) {
        DBQuery2 dbQuery = DBQueryDynamicFactory.create();
        dbQuery.doQuery("stone");
        dbQuery.doQuery("stone");
    }
}

class RealDBQuery2 implements DBQuery2 {
    @Override
    public String doQuery(String name) {
        System.out.println("调用数据库查询" + name);
        return "result";
    }
}

class DBQueryDynamicFactory {
    public static DBQuery2 create() {
        Class<DBQuery2> targetClass = DBQuery2.class;
        return (DBQuery2) Proxy.newProxyInstance(targetClass.getClassLoader(),
                new Class[]{targetClass},
                new CacheProviderHandler(new RealDBQuery2()));
    }
}

class CacheProviderHandler implements InvocationHandler {
    private Map<String, Object> cache = new HashMap();
    private Object target;

    public CacheProviderHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Type[] types = method.getParameterTypes();
        if (types.length == 1 && types[0] == String.class) {
            String name = (String) args[0];
            Object value = cache.get(name);
            if (value == null) {
                value = method.invoke(target, args);
                cache.put(name, value);
            }else{
                System.out.println("从缓存得到"+name);
            }
            return value;
        }
        return method.invoke(target, args);
    }
}
