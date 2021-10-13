import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    // References to each panel
    CustomisePanel customisePanel;
    OrderPanel orderPanel;
    Order order = null; // single order object used throughout program
    JPanel mainPanel = new JPanel();
    Display display = new Display();

    Font font = new Font(Font.SERIF, Font.PLAIN, 20);
    Font font30 = new Font(Font.SERIF, Font.BOLD, 30);

    final private JMenuBar menuBar = new JMenuBar();// Menu Bar contains Home, orders and Cart Menus
    final private JMenu homeMenu = new JMenu("Home");// Home Menu contains an Exit option
    final private JMenuItem exitMenuItem = new JMenuItem("Exit");
    final private JMenu shoppingMenu = new JMenu("Orders");// orders contains preset orders option and customise Computer options
    final private JMenuItem customiseMenuItem = new JMenuItem("Make Order");


    // Create 3 Labels Deliver to Chef, Cooking and Deliver to Customer,  Label
    public static JLabel deliverToChef  = new JLabel("   No Order   ");
    public static JLabel cooking        = new JLabel("   No Order   ");
    public static JLabel deliveredToCustomer = new JLabel("   No Order   ");

    public MainWindow() {
        Display.setUIFont("BiggerJoptionPaneButtons");
        this.setLayout(new MigLayout(""));
        this.setFont(font);

        buildCustomisationPanel();
        buildOrderPanel();

        // order processing labels
        buildOrderProcessingLabels();
        addLogoToHomeSCreen();
        this.add(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        buildMenu(this);
        this.setJMenuBar(menuBar);
    }

    private void buildOrderProcessingLabels() {

        Border border = BorderFactory.createLineBorder(Color.BLUE, 5);

        // Default State at start.
        deliverToChef.setBackground(Color.lightGray);
        deliverToChef.setFont(font30);
        deliverToChef.setOpaque(true);
        deliverToChef.setBorder(border);

        cooking.setBackground(Color.lightGray);
        cooking.setBorder(border);
        cooking.setFont(font30);
        cooking.setOpaque(true);

        deliveredToCustomer.setBackground(Color.lightGray);
        deliveredToCustomer.setBorder(border);
        deliveredToCustomer.setFont(font30);
        deliveredToCustomer.setOpaque(true);

    }

    private void buildCustomisationPanel() {
        customisePanel = new CustomisePanel(this);
        customisePanel.setVisible(false);

    }

    // Builds the landing page of the project
    public void addLogoToHomeSCreen(){
        ImageIcon icon = new ImageIcon("Images/Meal_Image.png");
        JLabel picLabel = new JLabel(icon);
        mainPanel.add(picLabel);
    }

    // save order
    public void saveOrder(Order order) {
        this.order = order;
    }

    public void buildOrderPanel() {
        orderPanel = new OrderPanel(this);
        orderPanel.setVisible(false);

    }

    // Builds the menu and provides functionality to them through ActionListeners
    public void buildMenu(MainWindow mainWindow) {

        menuBar.add(homeMenu);
        menuBar.add(shoppingMenu);
        homeMenu.add(exitMenuItem);
        shoppingMenu.add(customiseMenuItem);

        // Exit menu item selected
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( display.confirmYesNo("Please Confirm Exit operation","Exit Dialog")==0) {
                    synchronized (Restaurant.lock) {
                        Restaurant.order_state.setState(Restaurant.EXIT);
                    }
                }
            }
        });

        // Customise orders menu item selected
        customiseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.this.getContentPane().removeAll();
                MainWindow.this.getContentPane().add(customisePanel);
                customisePanel.setVisible(true);
                mainWindow.repaint();
            }
        });
    }

    public void buildGUI() {

        Font f = new Font(Font.SERIF, Font.PLAIN, 30);
        // Sets font for all instances of menu, menuBar and Menuitem
        UIManager.put("Menu.font", f);
        UIManager.put("MenuBar.font", f);
        UIManager.put("MenuItem.font", f);

//        // Creates and assigns default values to the main Window
        MainWindow window = new MainWindow();
        Display.setUIFont("BiggerJoptionPaneButtons");
        window.setTitle("Cosy Cafe");
        window.setSize(1150, 1000);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Displays greeting message on initial opening of project

        display.showMessage( "Welcome! To make an order, please click the Orders menu");

    }

    // The threads work based on this atomic variable which is change to represent where the order is along the production line
    // The if statements are used to modify the GUI view of the order as it is processing
    public void changeMainWindowState() {

        final String NoOrder       = "   No Order   ";
        final String Cooking       = "    Cooking   ";
        final String Cooked        = "    Cooked    ";
        final String Delivered     = "   Delivered  ";
        final String Waiting       = "    Waiting   ";
        final String OrderReceived = "Order Received";

        if(Restaurant.order_state.equals(Restaurant.NO_ORDER)) {
            setOrderStatusBlocks(
                    Color.lightGray, Color.lightGray, Color.lightGray,
                    NoOrder, NoOrder, NoOrder);
        } else
        if(Restaurant.order_state.equals(Restaurant.NEW_ORDER)) {
            setOrderStatusBlocks(
                    Color.green, Color.lightGray, Color.lightGray,
                    OrderReceived, Waiting, Waiting);
        } else
        if(Restaurant.order_state.equals(Restaurant.COOKING)) {
            setOrderStatusBlocks(
                    Color.green, Color.orange, Color.lightGray,
                    OrderReceived, Cooking, Waiting);
        } else
        if(Restaurant.order_state.equals(Restaurant.FINISHED)) {
            setOrderStatusBlocks(
                    Color.green, Color.green, Color.orange,
                    OrderReceived, Cooked, Waiting);
        } else
        if(Restaurant.order_state.equals(Restaurant.DELIVERED)) {
            setOrderStatusBlocks(
                    Color.green, Color.green, Color.green,
                    OrderReceived, Cooked, Delivered);
        }
    }

    // Updates the GUI Text and Colour to accurately represent the order state
    private void setOrderStatusBlocks(Color deliverCol,  Color cookingCol, Color customerCol,
                                     String deliverTxt, String cookTxt,   String customerTxt) {
        deliverToChef.setBackground(deliverCol);
        cooking.setBackground(cookingCol);
        deliveredToCustomer.setBackground(customerCol);

        deliverToChef.setText(deliverTxt);
        cooking.setText(cookTxt);
        deliveredToCustomer.setText(customerTxt);
    }

    public JLabel getDeliverToChef() {
        return deliverToChef;
    }
    public JLabel getDeliveredToCustomer() {
        return deliveredToCustomer;
    }
    public JLabel getCooking() {
        return cooking;
    }
    // returns full cart
    public Order getOrder() {
        return order;
    }

}


