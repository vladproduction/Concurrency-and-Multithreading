package vladproduction.com;

class Attender extends Thread {
    private int wichqueue;
    private boolean threadalive = true;

    public Attender(int wichqueue) {
        this.wichqueue = wichqueue;
    }

    public void run() {
        int timetodecide = 0;
        Customer customer;

        while (this.threadalive) {
            customer = CustomerQueues.attendCustomer(this.wichqueue);

            if (customer != null) {
                try {
                    this.sleep(customer.timetodecide);
                    this.attend(customer);
                } catch (InterruptedException e) {
                    System.out.println("Attender Interrupted");
                }
            } else {
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Attender Interrupted");
                }
            }
        }
    }

    public void attend(Customer customer) {
        boolean sold = true;
        int wait = 0;
        while (sold) {
            switch (customer.desiredfood) {
                case "burger":
                    if (FoodStock.cooksellburger("sell"))
                        sold = false;
                    break;
                case "sandwich":
                    if (FoodStock.cooksellsandwich("sell"))
                        sold = false;
                    break;
                case "salad":
                    if (FoodStock.cooksellsalad("sell"))
                        sold = false;
                    break;
            }
            if (sold) {
                try {
                    this.sleep(1000);
                    wait++;
                } catch (InterruptedException e) {
                    System.out.println("Attender Interrupted wait food");
                }
            }
        }
        System.out.println("Customer #" + (customer.id + 1) + " buy a " + customer.desiredfood + " and in " + (customer.timetodecide / 1000) + "seconds and wait for food " + wait + "seconds");
    }

    public void end() {
        this.threadalive = false;
    }
}
