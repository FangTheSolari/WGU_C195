package DAO;

import Database.JDBC;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Java class that provides logic for the Contact DAO.
 * @author Adam Rutland-Ruiz
 */
public class ContactDAO {
    /**
     * Retreives contact data from the Contacts table in the database
     * @return
     */
    public static ObservableList<Contacts> pullContacts() {
        Connection connect;
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String sql = "SELECT * FROM contacts;";
            ResultSet resultSet = connect.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                Contacts newContact = new Contacts(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email"));
                contacts.add(newContact);
            }
            return contacts;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
