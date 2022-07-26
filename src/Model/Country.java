package Model;

/**
 * Model for Country class
 * @author Adam Rutland-Ruiz
 */
public class Country {
    /**
     * Country ID
     */
    private final int country_ID;
    /**
     * Country Name
     */
    private String country;

    /**
     * Constructor for the countries
     * @param country_ID
     * @param country
     */
    public Country(int country_ID, String country) {
        this.country_ID = country_ID;
        this.country = country;
    }

    /**
     * Getter for Country Name
     * @return
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Getter for Country ID
     * @return
     */
    public int getCountryID() {
        return this.country_ID;
    }
}
