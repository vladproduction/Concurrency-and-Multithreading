package vladproduction.com;

class Customer {
    public int id, timetodecide;
    public String desiredfood;

    public Customer(int id, int timetodecide, String desiredfood){
        this.id = id;
        this.timetodecide = timetodecide;
        this.desiredfood = desiredfood;
    }

    public String toString(){
        return "Customer #" + this.id;
    }
}
