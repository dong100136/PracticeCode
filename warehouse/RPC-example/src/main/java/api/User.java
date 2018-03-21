package api;

import java.io.Serializable;

/**
 * Created by stone on 24/12/2016.
 */
public class User implements Serializable{
    public String name;
    public int age;
    public String intro;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", intro='" + intro + '\'' +
                '}';
    }
}
