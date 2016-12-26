public class Template {
    public static void main(String[] args) {
        DataHandler reader = new FileReader();
        reader.execute();

        DataHandler reader2 = new URLReader();
        reader2.execute();
    }
}

abstract class DataHandler {
    public void execute(){
        System.out.println("----------------------------");
        readData();
        Handle();
        if (isShow()){
            show();
        }
        System.out.println("----------------------------");
    }

    protected abstract void readData();

    protected void Handle(){
        System.out.println("处理数据");
    }

    protected boolean isShow(){
        return true;
    }

    protected void show(){
        System.out.println("将数据用树状图的方式进行显示");
    }
}

class FileReader extends DataHandler {

    @Override
    protected void readData() {
        System.out.println("从文件读入数据");
    }
}


class URLReader extends DataHandler {

    @Override
    protected void readData() {
        System.out.println("从网站读入数据");
    }

    @Override
    protected boolean isShow(){
        return false;
    }
}

