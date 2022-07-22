package Model;

public class Division {
    private final int divisionID;
    private final int countryID;
    private String division;
    public Division(int divisionID, int countryID, String division) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.division = division;
    }
    public String getDivision() {
        return division;
    }
    public int getDivisionID() {
        return divisionID;
    }
    public int getCountryID() {
        return countryID;
    }
}
