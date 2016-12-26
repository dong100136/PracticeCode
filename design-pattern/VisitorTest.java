import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 14/12/2016.
 */
public class VisitorTest {
    public static void main(String[] args) {
        BillBook billBook = new BillBook();
        billBook.addBill(new InBill(10.0));
        billBook.addBill(new InBill(20.0));
        billBook.addBill(new OutBill(40.0));
        billBook.addBill(new OutBill(20.0));
        billBook.addBill(new InBill(60.0));
        billBook.addBill(new OutBill(40.0));
        billBook.addBill(new OutBill(20.0));
        billBook.addBill(new InBill(60.0));
        billBook.addBill(new OutBill(40.0));
        billBook.addBill(new OutBill(20.0));
        billBook.addBill(new InBill(60.0));

        Visitor boss = new Boss();
        boss.viewBills(billBook);

        Visitor account = new Account();
        account.viewBills(billBook);
    }
}

abstract class Bill {
    private Double money;

    public Bill(Double money) {
        this.money = money;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public abstract void view(Visitor visitor);
}

abstract class Visitor {
    protected abstract void visit(InBill element);
    protected abstract void visit(OutBill element);

    public abstract void viewBills(BillBook billBook);
}

class InBill extends Bill {
    public InBill(Double money) {
        super(money);
    }

    @Override
    public void view(Visitor visitor) {
        visitor.visit(this);
    }
}

class OutBill extends Bill {

    public OutBill(Double money) {
        super(money);
    }

    @Override
    public void view(Visitor visitor) {
        visitor.visit(this);
    }
}

class Boss extends Visitor {

    private Double totalInMoney = 0.0;
    private Double totalOutMoney = 0.0;

    @Override
    protected void visit(InBill element) {
        totalInMoney += element.getMoney();
    }

    @Override
    protected void visit(OutBill element) {
        totalOutMoney += element.getMoney();
    }

    @Override
    public void viewBills(BillBook billBook) {
        billBook.viewAll(this);
        System.out.println("========老板查看账本=========");
        System.out.printf("共收入%.1f元\n",totalInMoney);
        System.out.printf("共支出%.1f元\n",totalOutMoney);
        System.out.printf("结余%.1f元\n",totalInMoney-totalOutMoney);
        System.out.println("========老板查看账本=========");
    }
}

class Account extends Visitor{

    @Override
    protected void visit(InBill element) {
        System.out.printf("+ %.1f\n",element.getMoney());
    }

    @Override
    protected void visit(OutBill element){
        System.out.printf("- %.1f\n",element.getMoney());
    }

    @Override
    public void viewBills(BillBook billBook) {
        System.out.println("========会计查看账本=========");
        billBook.viewAll(this);
        System.out.println("========会计查看账本=========");
    }
}

class BillBook {
    private List<Bill> billList = new ArrayList<>();

    public void addBill(Bill bill) {
        billList.add(bill);
    }

    public void deleteBill(Bill bill){
        billList.remove(bill);
    }

    public void viewAll(Visitor visitor){
        for (Bill bill:billList){
            bill.view(visitor);
        }
    }
}

