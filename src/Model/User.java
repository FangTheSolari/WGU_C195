package Model;

/**
 * Model for the User class
 */
public class User {
    private final int userID;
    private final String userName;

    /**
     * Constructor for the User List
     * @param userID
     * @param userName
     */
    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    /**
     * Getter for the User ID
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter for the User Name
     * @return
     */
    public String getUserName() {
        return userName;
    }
}
