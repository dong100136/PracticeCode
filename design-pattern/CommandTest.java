import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class CommandTest {
    public static void main(String[] args) throws SAXException, IllegalAccessException, IOException, InstantiationException, ParserConfigurationException, ClassNotFoundException {
        Invoker invoker1 = new Invoker();
        invoker1.setCommand((Command) getCommandFromXML(0));
        invoker1.run();
        Invoker invoker2 = new Invoker();
        invoker2.setCommand((Command) getCommandFromXML(1));
        invoker2.run();
    }

    public static Object getCommandFromXML(int i) throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        DocumentBuilderFactory dFactory =DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dFactory.newDocumentBuilder();
        Document doc = builder.parse(new File("./CommandCfg.xml"));

        NodeList nl = doc.getElementsByTagName("className");
        if (nl.getLength()<i) {
            System.out.println("超出范围");
            return null;
        }else{
            String className = nl.item(i).getFirstChild().getNodeValue();
            Class cls = Class.forName(className);
            Object obj = cls.newInstance();
            return obj;
        }
    }
}

abstract class Command {
    abstract void execute();
}

class AddCommand extends Command {
    @Override
    public void execute() {
        System.out.println("执行添加命令");
    }
}

class DeleteCommand extends Command {
    @Override
    public void execute() {
        System.out.println("执行删除命令");
    }
}

class Invoker {
    private Command command = null;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void run() {
        if (command != null) {
            this.command.execute();
        } else {
            System.out.println("no command was set");
        }
    }
}
