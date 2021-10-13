import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Restaurant {

    Display window = new Display();

    static Order order = null;
    MainWindow mainWindow = new MainWindow();
    GuiObserver observer = new GuiObserver(mainWindow);

    Waiter waiter = new Waiter();
    Chef chef = new Chef();

    static final Object lock = new Object();

    public static Order_State  order_state = new Order_State();
    // order states used throughout program
    public static AtomicInteger NO_ORDER = new AtomicInteger(-2);
    public static AtomicInteger NEW_ORDER = new AtomicInteger(-1);
    public static AtomicInteger COOKING = new AtomicInteger(0);
    public static AtomicInteger FINISHED = new AtomicInteger(1);
    public static AtomicInteger DELIVERED = new AtomicInteger(2);
    public static AtomicInteger EXIT = new AtomicInteger(10);

    public Restaurant() {

        order_state.addObserver(observer);
        order_state.setState(NO_ORDER); // start with NO Order

        mainWindow.buildGUI();

        if(!waiter.isAlive()) waiter.start();
        if(!chef.isAlive()) chef.start();

        while(!order_state.equals(EXIT)) { // this state comes from the gui.

            // order is set to null in Waiter after the order has been complete
            while (order != null) {
                if(order_state.equals(NO_ORDER)) {
                    order_state.setState(NEW_ORDER);
                }
            }
            //When order has been delivered, set the order back to null so that a new one can be input
            if (order_state.equals(DELIVERED)) {
                System.out.print("");
                order = null;
                order_state.setState(NO_ORDER);
            }
        }
        // when user exits, threads are interrupted and program exits
        chef.interrupt();
        waiter.interrupt();

        mainWindow.setVisible(false);
        window.showMessage("Thank you for eating with us");
        System.exit(0);
    }

    public static void main(String[] args){
        new Restaurant();
    }

    // Used for adding delay to GUI so that the changes to order state are more visible
    public static void sleep(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            System.out.print(""); // expected
        }
    }
}
