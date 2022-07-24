package DAO;

import Database.JDBC;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentDAO {

    private static Appointments newAppointments(ResultSet resultSet) throws SQLException {
        return new Appointments(
                resultSet.getInt("Appointment_ID"),
                resultSet.getString("Title"),
                resultSet.getString("Description"),
                resultSet.getString("Location"),
                resultSet.getString("Contact_Name"),
                resultSet.getString("Type"),
                resultSet.getDate("Start").toLocalDate(),
                resultSet.getTimestamp("Start").toLocalDateTime(),
                resultSet.getDate("End").toLocalDate(),
                resultSet.getTimestamp("End").toLocalDateTime(),
                resultSet.getInt("Customer_ID"),
                resultSet.getInt("User_ID"),
                resultSet.getInt("Contact_ID"));
    }

    public static ObservableList<Appointments> appointmentData() {
        Connection connect;
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try{
            connect = JDBC.getConnection();
            String sql = "Select appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID;";
            ResultSet results = connect.createStatement().executeQuery(sql);
            while (results.next()) {
                Appointments newAppointments = new Appointments(
                        results.getInt("Appointment_ID"),
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Location"),
                        results.getString("Contact_Name"),
                        results.getString("Type"),
                        results.getDate("Start").toLocalDate(),
                        results.getTimestamp("Start").toLocalDateTime(),
                        results.getDate("End").toLocalDate(),
                        results.getTimestamp("End").toLocalDateTime(),
                        results.getInt("Customer_ID"),
                        results.getInt("User_ID"),
                        results.getInt("Contact_ID"));
                appointments.add(newAppointments);
            }
            return appointments;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Appointments> weekList() {
        Connection connect;
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            //SQL Query
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE week(Start)=week(now());";
            //ResultSet
            ResultSet resultSet = connect.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                Appointments newAppointments = new Appointments(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"));
                appointments.add(newAppointments);
            }
            return appointments;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Appointments> monthList() {
        Connection connect;
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            //SQL Query
            String sql = " SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE month(Start)=month(now());";
            //ResultSet
            ResultSet resultSet = connect.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                Appointments newAppointments = new Appointments(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"));

                appointments.add(newAppointments);
            }
            return appointments;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Appointments> appointmentAlerts() {
        Connection connect;
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            //SQL Query
            String sql = "SELECT Appointment_ID, Start FROM appointments a WHERE a.start >= now() and a.start <= date_add(now(), interval 15 minute);";
            //
            //ResultSet
            ResultSet resultSet= connect.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                Appointments newAppointments = new Appointments(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime());

                appointments.add(newAppointments);
            }
            return appointments;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Appointments> getCustomerID(int customerID) {
        Connection connect;
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            //SQL Query
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID FROM appointments, contacts WHERE appointments.Customer_ID = ?;";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                appointments.add(newAppointments(resultSet));
            }
            return appointments;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static boolean updateAppointments(int aptID, String title, String description, String location, int contactName, String type,
                                             LocalDateTime start, LocalDateTime end, int customerID, int userID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            //SQL Query
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?;";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setInt(4, contactName);
            statement.setString(5, type);
            statement.setTimestamp(6, Timestamp.valueOf(start));
            statement.setTimestamp(7, Timestamp.valueOf(end));
            statement.setInt(8, customerID);
            statement.setInt(9, userID);
            statement.setInt(10, aptID);
            statement.execute();

        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean createAppointment(String title, String description, String location, int contactName, String type,
                                            LocalDateTime start, LocalDateTime end, int customerID, int userID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            //SQL Query
            String sql = "INSERT INTO appointments(Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setInt(4, contactName);
            statement.setString(5, type);
            statement.setTimestamp(6, Timestamp.valueOf(start));
            statement.setTimestamp(7, Timestamp.valueOf(end));
            statement.setInt(8, customerID);
            statement.setInt(9, userID);
            statement.execute();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAppointment(int AptID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            //SQL Query
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, AptID);
            statement.execute();

        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
