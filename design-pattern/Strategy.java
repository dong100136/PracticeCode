/**
 * Created by stone on 13/12/2016.
 */
public class Strategy {
    public static void main(String[] args) {
        Ticket  ticket = new Ticket(60,new StudentDiscount());
        ticket.getTicket();
    }
}

abstract class Discount {
    public abstract void getDiscount(double price);
}

class StudentDiscount extends Discount {

    @Override
    public void getDiscount(double price) {
        System.out.println("========学生票=========");
        System.out.printf("原价:%2.1f\n", price);
        System.out.printf("价格%2.1f\n", price * 0.8);
        System.out.println("======================");
    }
}

class ChildrenDiscount extends Discount {

    @Override
    public void getDiscount(double price) {
        System.out.println("========儿童票=========");
        System.out.printf("原价:%2.1f\n", price);
        System.out.printf("价格%2.1f\n", 10);
        System.out.println("======================");
    }
}

class VIPDiscount extends Discount {

    @Override
    public void getDiscount(double price) {
        System.out.println("========VIP票=========");
        System.out.printf("原价:%2.1f\n", price);
        System.out.printf("价格%2.1f\n", price * 0.5);
        System.out.println("======================");
    }
}


class Ticket {
    private double price;

    private Discount discount;

    public Ticket(double price,Discount discount) {
        this.price = price;
        this.discount= discount;
    }

    public void getTicket() {
        if (discount!=null){
            discount.getDiscount(price);
        }
    }

    public void setDiscount(Discount discount){
        this.discount = discount;
    }
}
