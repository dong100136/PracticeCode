import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by stone on 07/06/2017.
 */
public class Test {



	public static void main(String[] args) {
		PersonInterface p= (PersonInterface) Proxy.newProxyInstance(Person.class.getClassLoader(),Person.class.getInterfaces(),
				new MyIncationHandler(new Person("stone")));

		p.setName("stone123");
		System.out.println(p.getName());
	}
}

class MyIncationHandler implements InvocationHandler {

	private final Person person;

	public MyIncationHandler(Person person) {
		this.person = person;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before");
		System.out.println(method.toString());
		Object object = method.invoke(person,args);
		System.out.println("after");
		return object;
	}
}

interface PersonInterface{
	String getName();
	void setName(String name);
}

class Person implements PersonInterface{
	private String name;

	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
