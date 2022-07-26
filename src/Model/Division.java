package Model;

/** Model for Division class
 * @author Adam Rutland-Ruiz
 */
public class Division {
    /**
     * Division ID
     */
    private final int divisionID;
    /**
     * Division Country ID
     */
    private final int countryID;
    /**
     * Division
     */
    private String division;

    /**
     * Constructor for Division list
     * @param divisionID
     * @param countryID
     * @param division
     */
    public Division(int divisionID, int countryID, String division) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.division = division;
    }

    /**
     * Getter for Divion name
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Getter for Division ID
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Getter for Division Country ID
     * @return
     */
    public int getCountryID() {
        return countryID;
    }
}
