import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomisePanel extends JPanel {

    final private JLabel orderSummary = new JLabel();
    final private JPanel topPanel  = new JPanel();
    final private JPanel customisePanel = new JPanel();
    private Order order;

    final int mainCourse = 0;
    final int side = 1;
    final int drink = 2;

    JPanel[] customisePanels = new JPanel[3];
    Font font = new Font(Font.SERIF, Font.PLAIN,30);
    Font fontBold = new Font(Font.SERIF, Font.BOLD,30);

    public CustomisePanel(MainWindow mainWindow){
        // Sets font for all instances of JLabel and JRadioButton
        UIManager.put("Label.font",font);
        UIManager.put("RadioButton.font", font);

        Dimension dim = new Dimension(1000,1000);
        this.setSize(dim);
        this.setLayout(new MigLayout("","[100%]", "[30%][18%][18%][18%][18%]"));
        order = new Order();

        buildTopPanel(mainWindow);
        buildCustomisePanel();

        add(topPanel, "wrap");
        add(customisePanel);
    }

    // Adds all necessary components to fill top Panel
    public void buildTopPanel(MainWindow mainWindow) {

        // Create 4 columns here for Image, Headers, Details and Price
        topPanel.setLayout(new MigLayout("","[30%, left][30%, left][40%, left]"));
        topPanel.setSize(1150,200);

        // create component for headers and bold this text
        JLabel label = new JLabel();
        label.setFont(fontBold);
        label.setText(order.headers());

        // create button
        JButton orderToChef = new JButton("Order to Chef");
        orderToChef.setFont(fontBold);
        orderToChef.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mainWindow.saveOrder(order);

                // Sets the order inside restaurant which causes the waiter Thread to kick off
                Restaurant.order = order;
                mainWindow.buildOrderPanel();

                mainWindow.getContentPane().removeAll();
                mainWindow.getContentPane().add(mainWindow.orderPanel);
                mainWindow.orderPanel.setVisible(true);
                mainWindow.repaint();

                order = new Order();
            }
        });

        // create component for the details of the computer
        orderSummary.setFont(font);
        orderSummary.setText(order.toHtml());

        // add these four elements to the top panel
        topPanel.add(label);
        topPanel.add(orderSummary);
        topPanel.add(orderToChef);
    }

    public void buildCustomisePanel() {
        customisePanel.setLayout(new MigLayout("","[100%]","[30%][70%]"));
        customisePanel.setSize(1150,800);

        // configuration options
        String[] mainCourseOptions   = new String[]{"Burger", "Pizza"       , "Pasta"       , "Kebab"       };
        String[] sideOptions         = new String[]{"Chips" , "Curly Fries" , "Goujons"     , "Salad"       };
        String[] drinkOptions        = new String[]{"Coke"  , "7up"         , "Club Orange" , "Club Lemon"  };

        // build each config panel
        buildCustomisePanel("Main Course", mainCourse   , mainCourseOptions );
        buildCustomisePanel("Side"       , side         , sideOptions       );
        buildCustomisePanel("Drink"      , drink        , drinkOptions      );

        // add panels to customisePanel
        for (JPanel p : customisePanels) {
            customisePanel.add(p,"wrap");
        }
    }

    // Generic creation of radioButtons and a listener which is used for the 4 sets of radio buttons needed in the customise panel
    public JPanel buildRadioButtonPanel(final String[] options,int type) {

        JPanel panel = new JPanel();
        ButtonGroup radioButtonGroup = new ButtonGroup(); // used for exclusion
        JRadioButton[] radioButtons = new JRadioButton[4]; // the array of radioButtons

        for (int i = 0; i < options.length; i++) {

            radioButtons[i] = new JRadioButton(options[i]);
            radioButtons[i].addActionListener(new ActionListener() {
                // int type is used to allow the 4 panels to use the same actionListeners, when a listener is called it is updated accordingly
                int newVar = type;

                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = e.getActionCommand();

                    switch (newVar) {
                        case 0:
                            order.setMainCourseOption(str);
                            break;
                        case 1:
                            order.setSideOption(str);
                            break;
                        case 2:
                            order.setDrinkOption(str);
                            break;
                    }
                    //Updates text in top panel when a radio button is pressed
                    orderSummary.setText(order.toHtml());
                }
            });

            radioButtonGroup.add(radioButtons[i]);
            panel.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);

        return panel;
    }

    // Adds components to the customise Panel
    public void buildCustomisePanel(String header, int type, String[] options  ) {

        JPanel panel = initCustomisePanel(header);
        JPanel rbPanel = buildRadioButtonPanel(options,type);

        panel.add(rbPanel);

        customisePanel.setLayout(new MigLayout("","[100%]","[30%][70%]"));
        customisePanels[type] = panel;
    }

    // Creates the initial customise Panel
    public JPanel initCustomisePanel(String headerStr) {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("","[280, left][grow, left]"));
        panel.setSize(1100,180);

        // add Header label in first column
        JLabel headerPnl = new JLabel();
        headerPnl.setFont(fontBold);
        headerPnl.setText(headerStr);
        panel.add(headerPnl);

        return panel;
    }
}
