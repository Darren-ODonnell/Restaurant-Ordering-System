import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class OrderPanel extends JPanel {

    final private JPanel orderProcessingPanel = new JPanel();

    public OrderPanel(MainWindow mainWindow){

        // Build Order Processing panel
        buildOrderProcessingPanel(mainWindow);

        //Adds the order processing panel to Order Panel
        this.add(orderProcessingPanel);
    }

    private void buildOrderProcessingPanel(MainWindow mainWindow) {

        // Set dimensions for the panel for each individual computer in cart
        orderProcessingPanel.setLayout(new MigLayout("","[grow]", "[][center]"));
        orderProcessingPanel.setSize(1000, 200);

        // Show Order in a Label
        Order order = mainWindow.getOrder();
        if(order != null) {
            JLabel orderDetailsHeading = new JLabel("Order Details");
            JLabel orderDetails = new JLabel("Order: " + order.toDisplay());

            // Create Deliver to Chef Label
            JLabel deliverToChefHeading = new JLabel("To Chef");

            // Create Cooking/Cooked Label
            JLabel cookingHeading = new JLabel("State");

            // Create Delivered to Customer Label
            JLabel deliveredToCustomerHeading = new JLabel("Customer Status");

            orderProcessingPanel.add(orderDetailsHeading);
            orderProcessingPanel.add(deliverToChefHeading);
            orderProcessingPanel.add(cookingHeading);
            orderProcessingPanel.add(deliveredToCustomerHeading,"Wrap");

            orderProcessingPanel.add(orderDetails);
            orderProcessingPanel.add(mainWindow.getDeliverToChef());
            orderProcessingPanel.add(mainWindow.getCooking());
            orderProcessingPanel.add(mainWindow.getDeliveredToCustomer());
        }
    }

}
