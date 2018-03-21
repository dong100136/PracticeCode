import client.Client;

/**
 * Created by stone on 24/12/2016.
 */
public class Main{
    public static void main(final String[] args) throws InterruptedException {
        int port = 8080;
        String host = "0.0.0.0";

        //获取参数
        if (args.length==2){
            host = args[1];
        }else if (args.length==3){
            host = args[1];
            port = Integer.parseInt(args[2]);
        }else if (args.length==0){
            System.out.println("输入参数出错");
            System.exit(0);
        }

        new Main().run(args[0],host,port);
    }

    public void run(String type, final String host, final int port) throws InterruptedException {
        boolean worker=false;
        boolean client=false;

        if (type.equals("client")){
            client = true;
        }else if (type.equals("worker")){
            worker = true;
        }else{
            client = true;
            worker = true;
        }

        if (client) {
            new Thread(new Runnable() {
                public void run() {
                    Client.run(port);
                }
            }).start();
            System.out.println("client启动...");
        }

        Thread.sleep(300);

        if (worker) {
            new Thread(new Runnable() {
                public void run() {
//                    main.java.worker.Worker.run(host,port);
                }
            }).start();
            System.out.println("worker启动...");
        }
    }
}
