public class Chef extends Thread {

    public Chef() {
    }

    public void run() {

        Restaurant.sleep(2);

        synchronized (Restaurant.lock) {

            while (true) {
                // wait until waiter has picked up a new order
                while (Restaurant.order == null) {

                    try {
                        Restaurant.lock.wait();
                    } catch (IllegalMonitorStateException e) {
                        e.printStackTrace();
                    } catch(InterruptedException ex){
                        System.out.print(""); // expected
                    }

                }
                Restaurant.sleep(1);// artificial wait to show progress between states

                if(Restaurant.order_state.equals(Restaurant.NEW_ORDER)) {

                    try {
                        // Cooking
                        Restaurant.order_state.setState(Restaurant.COOKING);
                        Restaurant.sleep(3); // to allow cooking to complete

                        // Finished Cooking, giving time for waiter to pick up order
                        Restaurant.order_state.setState(Restaurant.FINISHED);
                        Restaurant.sleep(1); // artificial wait to show progress between states
                        Restaurant.lock.notify();
                        Restaurant.lock.wait();

                    } catch (IllegalMonitorStateException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        System.out.print("");
                        System.exit(0);
                    }
                }
            }
        }
    }

}
