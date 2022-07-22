package Model;

public class Customers {
    private int custID;
    private String custName;
    private String custAddress;
    private String custPostalCode;
    private String custPhone;
    private String country;
    private String division;
    private int divisionID;
    private int countryID;
    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode,
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
    public Customer(int customerID, String customerName) {
        this.custID = customerID;
        this.custName = customerName;
    }
    public int getCustomerID() {
        return custID;
    }
    public String getCustomerName() {
        return custName;
    }
    public String getCustomerAddress() {
        return custAddress;
    }
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public String getCountry() {
        return country;
    }
    public String getDivision() {
        return division;
    }
    public int getCustomerDivisionID() {
        return divisionID;
    }
    public int getCountryID() {
        return countryID;
    }
}
