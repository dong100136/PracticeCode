/**
 * Created by stone on 05/12/2016.
 */
public class StateTest {
    public static void main(String[] args) {
        Lift lift = new Lift(new StopState());

        lift.open();
        lift.run();
        lift.stop();
        lift.run();
        lift.close();
        lift.run();
        lift.open();
    }
}

abstract class LiftState {
    abstract void open(Lift lift);

    abstract void close(Lift lift);

    abstract void run(Lift lift);

    abstract void stop(Lift lift);
}

class OpenState extends LiftState {

    @Override
    void open(Lift lift) {
        System.out.println("门已经打开");
        lift.setCurrentState(Lift.openState);
    }

    @Override
    void close(Lift lift) {
        System.out.println("门正在关闭");
        lift.setCurrentState(Lift.stopState);
    }

    @Override
    void run(Lift lift) {
        System.out.println("门已经打开，请先关门");
        lift.setCurrentState(Lift.openState);
    }

    @Override
    void stop(Lift lift) {
        System.out.println("电梯没有在运行，不能停止");
        lift.setCurrentState(Lift.openState);
    }
}

class RunningState extends LiftState {
    @Override
    void open(Lift lift) {
        System.out.println("电梯正在运行，不能打开门");
        lift.setCurrentState(Lift.runningState);
    }

    @Override
    void close(Lift lift) {
        System.out.println("电梯正在运行，门已经关闭");
        lift.setCurrentState(Lift.runningState);
    }

    @Override
    void run(Lift lift) {
        System.out.println("电梯正在运行");
        lift.setCurrentState(Lift.runningState);
    }

    @Override
    void stop(Lift lift) {
        System.out.println("电梯正在停止");
        lift.setCurrentState(Lift.stopState);
    }
}

class StopState extends LiftState {
    @Override
    void open(Lift lift) {
        System.out.println("电梯门打开");
        lift.setCurrentState(Lift.openState);
    }


    @Override
    void close(Lift lift) {
        System.out.println("电梯门没有打开，不能关闭");
        lift.setCurrentState(Lift.stopState);
    }

    @Override
    void run(Lift lift) {
        System.out.println("电梯开始运行");
        lift.setCurrentState(Lift.runningState);
    }

    @Override
    void stop(Lift lift) {
        System.out.println("电梯已经停止，不能再次停止");
        lift.setCurrentState(Lift.stopState);
    }
}

class Lift {
    private LiftState currentState;
    public static LiftState openState = new OpenState();
    public static LiftState runningState = new RunningState();
    public static LiftState stopState = new StopState();

    public Lift(LiftState currentState) {
        this.currentState = currentState;
    }

    public void open() {
        currentState.open(this);
    }

    public void close() {
        currentState.close(this);
    }

    public void stop() {
        currentState.stop(this);
    }

    public void run() {
        currentState.run(this);
    }

    public LiftState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(LiftState currentState) {
        this.currentState = currentState;
    }
}