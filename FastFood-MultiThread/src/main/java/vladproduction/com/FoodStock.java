package vladproduction.com;

final class FoodStock {
    private static int burger, sandwich, salad;

    private FoodStock() {
        ;
    }
    public static synchronized boolean cooksellburger(String action) {
        if(action.equals("cook")) {
            burger++;
            return true;
        } else if (action.equals("sell")) {
            if(burger > 0){
                burger--;
                return true;
            }
        }
        return false;
    }
    public static synchronized boolean cooksellsandwich(String action) {
        if(action.equals("cook")) {
            sandwich++;
            return true;
        } else if (action.equals("sell")) {
            if(sandwich > 0){
                sandwich--;
                return true;
            }
        }
        return false;
    }
    public static synchronized boolean cooksellsalad(String action) {
        if(action.equals("cook")) {
            salad++;
            return true;
        } else if (action.equals("sell")) {
            if(salad > 0){
                salad--;
                return true;
            }
        }
        return false;
    }

    public String toString(){
        return  "\tFood Stock  burger: " + burger + " sandwich: " + sandwich + " salad: " + salad;
    }
}
