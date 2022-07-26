package DAO;

import Database.JDBC;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Java class that provides the logic for the Division DAO
 * @author Adam Rutland-Ruiz
 */
public class DivisionDAO {

    /**
     * Retrieves the division data from the Division Table in the database
     * @param country
     * @return
     */
    public ObservableList<Division> pullCountry(String country) {
        try {
            String sql = "SELECT first_level_divisions.Division_ID, countries.Country_ID, first_level_divisions.Division  FROM countries, first_level_divisions WHERE countries.Country = ? and countries.Country_ID = first_level_divisions.Country_ID;";
            Connection connect = JDBC.getConnection();
            ObservableList<Division> divisions = FXCollections.observableArrayList();
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, country);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                divisions.add(createDivision(rs));
            }
            return divisions;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a division object from the database to be called upon
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private Division createDivision(ResultSet resultSet) throws SQLException {
        return new Division(
                resultSet.getInt("Division_ID"),
                resultSet.getInt("Country_ID"),
                resultSet.getString("Division"));
    }

}
