package vladproduction.com;

import java.util.LinkedList;

final class CustomerQueues {
    private static LinkedList<Customer> customerQueue1 = new LinkedList<Customer>();
    private static LinkedList<Customer> customerQueue2 = new LinkedList<Customer>();

    private CustomerQueues() {
        ;
    }

    public static void enterCustomer(int queue, Customer customer) {
        if(queue == 0)
            customerQueue1.addLast(customer);
        else
            customerQueue2.addLast(customer);
    }

    public static Customer attendCustomer(int queue) {
        if(queue == 0){
            if(customerQueue1.size() > 0)
                return customerQueue1.removeFirst();
            return null;
        }
        if(customerQueue2.size() > 0)
            return customerQueue2.removeFirst();
        return null;
    }

    public String toString(){
        return  "\tQueue 1: " + customerQueue1 +
                "\n\tQueue 2: " + customerQueue2;
    }
}
