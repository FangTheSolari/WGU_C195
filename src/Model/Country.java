package Model;

public class Country {
    private final int country_ID;
    private String country;

    public Country(int country_ID, String country) {
        this.country_ID = country_ID;
        this.country = country;
    }
    public String getCountry() {
        return this.country;
    }
    public int getCountryID() {
        return this.country_ID;
    }
}
