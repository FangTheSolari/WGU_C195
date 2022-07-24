package DAO;

import Database.JDBC;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static ObservableList<Customers> pullCustomerTable() {
        Connection connect;
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String SQL = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, countries.Country, first_level_divisions.Division, customers.Division_ID, countries.Country_ID FROM customers INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID;";
            ResultSet resultSet = connect.createStatement().executeQuery(SQL);
            while (resultSet.next()) {
                Customers newCustomer = new Customers(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Country"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Division_ID"),
                        resultSet.getInt("Country_ID"));

                customers.add(newCustomer);
            }
            return customers;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public static ObservableList<Customers> createCustomerList() {
        Connection connect;
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String SQL = "SELECT Customer_ID, Customer_Name FROM customers;";
            ResultSet resultSet = connect.createStatement().executeQuery(SQL);
            while (resultSet.next()) {
                Customers newCustomer = new Customers(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"));
                customers.add(newCustomer);
            }
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean updateCustomer(int CustomerID, String CustomerName, String CustomerAddress, String CustomerPostalCode, String CustomerPhone, String DivisionID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, CustomerName);
            statement.setString(2, CustomerAddress);
            statement.setString(3, CustomerPostalCode);
            statement.setString(4, CustomerPhone);
            statement.setInt(5, Integer.parseInt(DivisionID));
            statement.setInt(6, CustomerID);
            statement.execute();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }


    public static boolean createCustomer(String CustomerName, String CustomerAddress, String CustomerPostalCode, String CustomerPhone, String DivisionID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, CustomerName);
            statement.setString(2, CustomerAddress);
            statement.setString(3, CustomerPostalCode);
            statement.setString(4, CustomerPhone);
            statement.setInt(5, Integer.parseInt(DivisionID));
            statement.execute();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCustomer(int CustomerID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            String SQL = "DELETE FROM customers WHERE Customer_ID = ?;";

            PreparedStatement statement = connect.prepareStatement(SQL);
            statement.setInt(1, CustomerID);
            statement.execute();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }


}
