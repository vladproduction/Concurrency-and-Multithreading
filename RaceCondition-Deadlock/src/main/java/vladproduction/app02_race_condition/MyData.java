package vladproduction.app02_race_condition;

public class MyData {
    private int value;

    public synchronized void increment(){
        value++;
//        synchronized (this){
//            value++;
//        }
    }

    public synchronized void decrement(){
        value--;
//        synchronized (this){
//            value--;
//        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "value=" + value;
    }

}
