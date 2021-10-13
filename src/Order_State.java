import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Using the Observer Pattern to get the GUI to respond to the change in the variable
 * order_state
 *
 * The implementation is in 3 parts
 * 1st create a class encapsulating the state variable (this is the observable)
 * 2nd create a class which becomes the observer
 * 3rd in the main - create the state variable (which is now observable)
 *                 - create an observer
 *                 - finally add this observer to the observable.
 */

public class Order_State extends Observable {
    AtomicInteger prevState = new AtomicInteger(-2);
    AtomicInteger state = new AtomicInteger(-2);

    public void setState(AtomicInteger state) {
        synchronized (this) {
            this.prevState = this.state;
            this.state = state;
        }
        setChanged();
        // useful comment to show the progress between states - verifying that none are skipped
        System.out.println("Changing State from : " + toString(getPrevState()) + " to " + toString(getState()) );
        notifyObservers();
    }

    public boolean equals(AtomicInteger newState) {
        return(state.get() == newState.get());
    }

    public synchronized AtomicInteger getState() {
        return state;
    }

    public synchronized AtomicInteger getPrevState() {
        return prevState;
    }

    public String toString(AtomicInteger state) {
        String str = "";
        if (state.get() == Restaurant.NO_ORDER.get())  str = "NO_ORDER";
        if (state.get() == Restaurant.NEW_ORDER.get()) str = "NEW_ORDER";
        if (state.get() == Restaurant.COOKING.get())   str = "COOKING";
        if (state.get() == Restaurant.FINISHED.get())  str = "FINISHED";
        if (state.get() == Restaurant.DELIVERED.get()) str = "DELIVERED";
        if (state.get() == Restaurant.EXIT.get())      str = "EXIT";
        return str;
    }
}

class GuiObserver implements Observer {
    MainWindow mainWindow;

    public GuiObserver(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    public void observe(Observable o) {
        o.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {
        mainWindow.changeMainWindowState();
        mainWindow.repaint();
    }
}

