/**
 * Created by stone on 13/12/2016.
 */
public class AbstrateMethod {
    public static void main(String[] args) {
        D d = new D();
        d.test(new A());
        d.test(new B());
        d.test(new C());

        D e = new E();
        e.test(new C());
        e.test(new A());

        E e2 = new E();
        e2.test(new C());
        e2.test(new A());
    }
}

class A{}

class B extends A{}

class C extends A{}

class D{
    public void test(A a){
        System.out.println('a');
    }

    public void test(B b){
        System.out.println('b');
    }
}

class E extends D{
    public void test(C c){
        System.out.println('c');
    }
}

