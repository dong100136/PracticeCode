import java.util.ArrayList;
import java.util.List;

public class MediatorTest {
    public static void main(String[] args) {
        Mediator mediator = new Mediator();
        Component addBtn = new Component(mediator) {
            @Override
            void display() {
                System.out.println("点击添加按钮，添加了选项");
            }

            @Override
            void update() {
                System.out.println("输入框更新");
            }
        };

        Component list = new Component(mediator) {
            @Override
            void display() {
                System.out.println("点击列表，添加了选项");
            }

            @Override
            void update() {
                System.out.println("列表更新");
            }
        };

        Component downList = new Component(mediator) {
            @Override
            void display() {
                System.out.println("点击下拉列表，添加了选项");
            }

            @Override
            void update() {
                System.out.println("下拉列表更新");
            }
        };

        System.out.println("=================");
        addBtn.change();
        System.out.println("=================");
        list.change();
        System.out.println("=================");
        downList.change();
    }
}

abstract class Component{
    Mediator mediator = null;

    abstract void display();

    public Component(Mediator mediator) {
        this.mediator = mediator;
        mediator.addComponent(this);
    }

    public void change(){
        notifyMediator();
    }

    abstract void update();

    private void notifyMediator(){
        mediator.update(this);
    }
}

class Mediator{
    List<Component> componentList = new ArrayList<>();

    public void addComponent(Component component){
        componentList.add(component);
    }

    public void update(Component component){
        component.display();
        for (Component c : componentList){
            c.update();
        }
    }

}