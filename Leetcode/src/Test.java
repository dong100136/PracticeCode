import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by stone on 04/12/2016.
 */
public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        new Thread(() -> {
            for (int i=0;i<10;i++){
                try {
                    list.add(i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i=10;i<20;i++){
                try {
                    list.add(i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
