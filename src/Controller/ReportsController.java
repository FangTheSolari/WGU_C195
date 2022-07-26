package Controller;

import Database.JDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
/**
 * Controller class that provides logic for the Login screen.
 * @author Adam Rutland-Ruiz
 */
public class ReportsController {
    /**
     * Sort by Label
     */
    public Label sortByLabel;
    /**
     * Reports Table view
     */
    @FXML
    private TableView reportsTable;
    /**
     * Type and Month Radio Button
     */
    @FXML
    private RadioButton typeAndMonthRadBut;
    /**
     * Contacts Radio Button
     */
    @FXML
    private RadioButton contactsRadBut;
    /**
     * User Radio Button
     */
    @FXML
    private RadioButton usersRadBut;
    /**
     * Back Button
     */
    public Button backButton;
    /**
     * Observable list of data from database
     */
    public ObservableList reportsData;
    /**
     * SQL Query object
     */
    public String sqlQuery;

    /**
     * Starts the screen with the appropriate data in the Table view
     */
    public void initialize(){
        sortByToggle();
    }

    /**
     * Generates a list for the reports Table view by getting table data
     * lambda expression is used to dynamically create the table list.
     */
    public void reportsList(){
        Connection connect;
        reportsData = FXCollections.observableArrayList();
        reportsTable.getColumns().clear();
        try {
            connect = JDBC.getConnection();
            String sql = sqlQuery;
            ResultSet rs = connect.createStatement().executeQuery(sql);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int l = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                // Lambda expression
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
                        new SimpleStringProperty(param.getValue().get(l).toString()));

                reportsTable.getColumns().addAll(col);
            }
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                reportsData.add(row);
            }
            reportsTable.setItems(reportsData);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Sets the Table view to the correct data based off the user's chosen radio button
     */
    public void sortByToggle() {
        if (typeAndMonthRadBut.isSelected()) {
            sqlQuery = ("SELECT Type, MONTHNAME(Start) as Month, count(*) AS Count FROM appointments GROUP BY Type, MONTHNAME(Start) ORDER BY Type;");
        } else if (contactsRadBut.isSelected()) {
            sqlQuery = ("SELECT contacts.Contact_Name, appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, CONVERT_TZ(start, '+00:00', 'system') AS start, CONVERT_TZ(end, '+00:00', 'system') AS end, appointments.Customer_ID FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID order by Contact_Name;");
        } else if (usersRadBut.isSelected()) {
            sqlQuery = ("SELECT users.User_Name, contacts.Contact_Name, appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, CONVERT_TZ(start, '+00:00', 'system') AS start, CONVERT_TZ(end, '+00:00', 'system') AS end, appointments.Customer_ID FROM appointments, contacts, users WHERE appointments.Contact_ID = contacts.Contact_ID order by User_Name;");
        }
        reportsList();
    }

    /**
     * Changes the screen to the main menu
     * @param actionEvent back button click
     * @throws Exception
     */
    public void backButClick(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1050, 700);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }
}
