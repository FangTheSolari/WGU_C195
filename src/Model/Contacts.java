package Model;

/**
 * Model for Contacts class
 * @author Adam Rutland-Ruiz
 */
public class Contacts {
    /**
     * Contacts ID
     */
    private int ID;
    /**
     * Contacts Name
     */
    private String name;
    /**
     * Contacts Email
     */
    private String email;

    /**
     * Constructor for the contacts
     * @param ID
     * @param name
     * @param email
     */
    public Contacts(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    /**
     * getter for the Contacts ID
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter for the Contacts Name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the Contacts Email
     * @return
     */
    public String getEmail() {
        return email;
    }
}
