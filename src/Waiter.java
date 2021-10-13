public class Waiter extends Thread{

    Display window = new Display();

    public Waiter()  {
    }

    public void run(){
         while (true) {

             synchronized(Restaurant.lock) {

                 // waiter acts on two states
                 // first - New order received - NEW_ORDER
                 // second - when order is FINISHED
                 while(Restaurant.order_state.equals(Restaurant.NEW_ORDER)) {
                     try {
                         // NEW_ORDER goes to chef and Waiter waits for chef to pass it back
                         Restaurant.lock.notify();
                         Restaurant.lock.wait();
                     } catch (IllegalMonitorStateException e) {
                         e.printStackTrace();
                     } catch (InterruptedException e){
                         System.out.print(""); // this is expected
                     }
                 }
                 if (Restaurant.order_state.equals(Restaurant.FINISHED)) {
                     Restaurant.order_state.setState(Restaurant.DELIVERED);
                     window.showMessage("Waiter has returned with order: \n" + Restaurant.order.toString());
                     // nullify order just finished to allow for new order to be made
                     Restaurant.order = null;
                 }
             }
         }
    }
}
