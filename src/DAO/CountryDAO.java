package DAO;

import Database.JDBC;
import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Java class that provides logic to the Country DAO
 * @author Adam Rutland-Ruiz
 */
public class CountryDAO {
    /**
     * Retrieves country data from the Country table in the database
     * @return
     */
    public static ObservableList<Country> getCountries() {
        Connection connect;
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String SQL = "SELECT * FROM countries";
            ResultSet resultSet = connect.createStatement().executeQuery(SQL);
            while (resultSet.next()) {
                Country newCountry = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country"));
                countries.add(newCountry);
            }
            return countries;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
