package Model;

/** * Model for Customers class
 * @author Adam Rutland-Ruiz
 */
public class Customers {
    /**
     * Customer ID
     */
    private int custID;
    /**
     * Customer Name
     */
    private String custName;
    /**
     * Customer Address
     */
    private String custAddress;
    /**
     * Customer Postal Code
     */
    private String custPostalCode;
    /**
     * Customer Phone
     */
    private String custPhone;
    /**
     * Customer Country
     */
    private String country;
    /**
     * Customer Division
     */
    private String division;
    /**
     * Customer DivisionID
     */
    private int divisionID;
    /**
     * Customer Country ID
     */
    private int countryID;

    /**
     * Constructor for Customers
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param country
     * @param division
     * @param divisionID
     * @param countryID
     */
    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode,
                    String customerPhone, String country, String division, int divisionID, int countryID) {
        this.custID = customerID;
        this.custName = customerName;
        this.custAddress = customerAddress;
        this.custPostalCode = customerPostalCode;
        this.custPhone = customerPhone;
        this.country = country;
        this.division = division;
        this.divisionID = divisionID;
        this.countryID = countryID;
    }

    /**
     * Constructor for Customers List
     * @param customerID
     * @param customerName
     */
    public Customers(int customerID, String customerName) {
        this.custID = customerID;
        this.custName = customerName;
    }

    /**
     * Getter for Customer ID
     * @return
     */
    public int getCustomerID() {
        return custID;
    }

    /**
     * Getter for Customer Name
     * @return
     */
    public String getCustomerName() {
        return custName;
    }

    /**
     * Getter for Customer Address
     * @return
     */
    public String getCustomerAddress() {
        return custAddress;
    }

    /**
     * Getter for Customer Postal Code
     * @return
     */
    public String getCustomerPostalCode() {
        return custPostalCode;
    }

    /**
     * Getter for Customer Phone
     * @return
     */
    public String getCustomerPhone() {
        return custPhone;
    }

    /**
     * Getter for the customer Country
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Getter for the customer Division
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Getter for the customer Division ID
     * @return
     */
    public int getCustomerDivisionID() {
        return divisionID;
    }

    /**
     * Getter for Country ID
     * @return
     */
    public int getCountryID() {
        return countryID;
    }
}
