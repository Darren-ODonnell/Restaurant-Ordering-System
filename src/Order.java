public class Order {

    private boolean isCooked = false;

    String[] mainCourseOptions = new String[]{"Burger", "Pizza", "Pasta", "Kebab"};
    int[] mainCoursePrices = new int[]{10, 12, 8, 6};

    String[] sideOptions = new String[]{"Chips", "Curly Fries", "Goujons", "Salad"};
    int[] sidePrices = new int[]{2, 3, 6, 4};

    String[] drinkOptions = new String[]{"Coke", "7up", "Club Orange", "Club Lemon"};
    int[] drinkPrices = new int[]{ 2, 2, 2, 2};

    int mainCourseOption;
    int sideOption;
    int drinkOption;
    int quantity;

    public int basePrice = 0;

    public Order() {
        initTotalPrice();
    }

    private void initTotalPrice() {
        basePrice = 0;
        basePrice += mainCoursePrices[this.mainCourseOption];
        basePrice += sidePrices[this.sideOption];
        basePrice += drinkPrices[this.drinkOption];
    }

    public Order(int mainCourseOption, int sideOption, int drinkOption) {

        this.mainCourseOption = mainCourseOption;
        this.sideOption = sideOption;
        this.drinkOption = drinkOption;
        this.quantity += 1;
        initTotalPrice();
    }

    public String getMainCourseStr() {
        return mainCourseOptions[this.mainCourseOption];
    }

    public void setMainCourseOption(int selection) {
        this.basePrice -= mainCoursePrices[this.mainCourseOption];

        this.mainCourseOption = selection;

        this.basePrice += mainCoursePrices[selection];
    }

    public void setMainCourseOption(String selection) {
        int index = 0;
        while(!mainCourseOptions[index].equals(selection)){
            index++;
        }
        setMainCourseOption(index);
    }

    public String getSideOption() {
        return this.sideOptions[sideOption];
    }

    public void setSideOption(int selection) {
        //Selection Values
        //0 = 8gb, 1 = 16gb, 2 = 32gb, 3 = 32gb
        this.basePrice -= sidePrices[this.sideOption];

        this.sideOption = selection;

        this.basePrice += sidePrices[selection];

    }

    public void setSideOption(String option) {
        int index = 0;
        while(!sideOptions[index].equals(option)){
            index++;
        }
        setSideOption(index);
    }

    public String getDrinkOption() {
        return this.drinkOptions[this.drinkOption];
    }

    public void setDrinkOption(int selection) {
        this.basePrice -= drinkPrices[this.drinkOption];

        this.drinkOption = selection;

        this.basePrice += drinkPrices[selection];

    }

    public void setDrinkOption(String diskSpace) {
        int index = 0;
        while(!drinkOptions[index].equals(diskSpace)){
            index++;
        }
        setDrinkOption(index);
    }

    @Override
    public String toString() {
        return "Order{" +
                "Main Course=" + getMainCourseStr() +
                ", Side=" + getSideOption() +
                ", Drink=" + getDrinkOption() +
                ", totalPrice= €" + basePrice +
                '}';
    }

    public String toDisplay() {
        return getMainCourseStr() +
                ", " + getSideOption() +
                ", " + getDrinkOption();
    }

    public String headers() {

        String START_HTML = "<HTML>";
        String END_HTML = "</HTML>";
        String LINE_BREAK = "<BR>";


        return START_HTML +
                "Main Course:" + LINE_BREAK +
                "Side:" + LINE_BREAK +
                "Drink:" + END_HTML;
    }

    public String toHtml() {

        String START_HTML = "<HTML>";
        String END_HTML = "</HTML>";
        String LINE_BREAK = "<BR>";


        return START_HTML +
                this.getMainCourseStr() + LINE_BREAK +
                this.getSideOption() + LINE_BREAK +
                this.getDrinkOption() + END_HTML;

    }
}

