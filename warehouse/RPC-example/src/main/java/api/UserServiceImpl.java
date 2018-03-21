package api;

/**
 * Created by stone on 24/12/2016.
 */
public class UserServiceImpl implements UserService {
    public User getUser() {
        User user = new User();
        user.name = "stone";
        user.age = 10;
        user.intro = "This is a test";

        return user;
    }
}
