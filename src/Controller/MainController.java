package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import Model.Appointments;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

import static javafx.fxml.FXMLLoader.load;
/**
 * Controller class that provides logic for the Main screen.
 * @author Adam Rutland-Ruiz
 */
public class MainController {
    /**
     * Customer ID Column
     */
    public TableColumn custIdCol;
    /**
     * Customer Name Column
     */
    public TableColumn custNameCol;
    /**
     * Customer Address Column
     */
    public TableColumn custAddressCol;
    /**
     * Customer Postal Code Column
     */
    public TableColumn custZipCol;
    /**
     * Customer Phone Column
     */
    public TableColumn custPhoneCol;
    /**
     * Customer Country Column
     */
    public TableColumn custCountryCol;
    /**
     * Customer Division Column
     */
    public TableColumn custDivisionCol;
    /**
     * Appointment ID Column
     */
    public TableColumn aptIdCol;
    /**
     * Appointment Title Column
     */
    public TableColumn aptTitleCol;
    /**
     * Appointment Description Column
     */
    public TableColumn aptDescriptionCol;
    /**
     * Appointment Location Column
     */
    public TableColumn aptLocationCol;
    /**
     * Appointment Contact Column
     */
    public TableColumn aptContactCol;
    /**
     * Appointment Type Column
     */
    public TableColumn aptTypeCol;
    /**
     * Appointment Start Time Column
     */
    public TableColumn aptStartCol;
    /**
     * Appointment End Time Column
     */
    public TableColumn aptEndCol;
    /**
     * Appointment Customer ID Column
     */
    public TableColumn aptCustomerCol;
    /**
     * Appointment User ID Column
     */
    public TableColumn aptUserCol;
    /**
     * Toggle list views in appointment table
     */
    public ObservableList selectedToggle;
    /**
     * Appointment Alert Label
     */
    @FXML
    private Label appointmentAlertLabel;
    /**
     * All radio Button
     */
    @FXML
    private RadioButton allRadButton;
    /**
     * Week Radio Button
     */
    @FXML
    private RadioButton weekRadButton;
    /**
     * Month Radio Button
     */
    @FXML
    private RadioButton monthRadButton;
    /**
     * Customer Table View
     */
    @FXML
    private TableView custTableView;
    /**
     * Appointment Table View
     */
    @FXML
    private TableView aptTableView;

    /**
     * Initializes the table views and populates the columns with values
     * Lambda expression creates an alert if there is an appointment within 15 minutes of the users current time.
     */
    public void initialize() {
        custTableView.setItems(CustomerDAO.pullCustomerTable());
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        custDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        aptTableView.setItems(AppointmentDAO.appointmentData());
        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("aptID"));
        aptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        aptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        aptEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        aptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        aptUserCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        ObservableList<Appointments> upcomingAppointments = FXCollections.observableArrayList();
        ObservableList<Appointments> appointmentAlert = AppointmentDAO.appointmentAlerts();
        if (appointmentAlert != null) {
            //Lambda expression
            appointmentAlert.forEach(appointment -> {
                upcomingAppointments.add(appointment);
                String alert = "There are appointments starting within the next 15 minutes:\n" + "Appointment ID: " + appointment.getAptID() + "\n" +
                        "Date: " + appointment.getStartDate() + "\n" + "Time: " + appointment.getStartTime().toLocalTime();
                appointmentAlertLabel.setText(alert);
            });
        }
    }

    /**
     * Expression that changes the table view based on what the user would like to see
     */
    public void aptViewToggle() {

        if (allRadButton.isSelected()) {
            selectedToggle = AppointmentDAO.appointmentData();
        } else if (weekRadButton.isSelected()) {
            selectedToggle = AppointmentDAO.weekList();
        } else if (monthRadButton.isSelected()) {
            selectedToggle = AppointmentDAO.monthList();
        }
        aptTableView.setItems(selectedToggle);


    }

    /**
     * Switches screens to the Customer FXML to add customer
     * @param actionEvent add customer button click
     * @throws IOException
     * @throws SQLException
     */
    public void addCustomerButClick(ActionEvent actionEvent) throws IOException, SQLException {
        Customers customer = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/CustomerForm.fxml"));
        Parent scene = loader.load();
        CustomerController controller = loader.getController();
        controller.sendData(customer);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Gets the selected customer from the Customer Table and populates the Customer FXML to be edited
     * If no customer selected error pops up
     * @param actionEvent update customer button click
     * @throws IOException
     * @throws SQLException
     */
    public void updateCustomerButClick(ActionEvent actionEvent) throws IOException, SQLException {
        Customers selectedCustomer = (Customers) custTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/CustomerForm.fxml"));
            Parent scene = loader.load();
            CustomerController controller = loader.getController();
            controller.sendData(selectedCustomer);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please select a customer.");
            alert.showAndWait();
        }
    }

    /**
     * Deletes customer from database, thus removing them from Customer Table and all associated Appointments from
     * database and Appointment Table view
     * If no customer is selected error pops up
     * @param actionEvent delete customer button click
     * @throws SQLException
     */
    public void deleteCustomerButClick(ActionEvent actionEvent) throws SQLException{
        Customers selectedCustomer = (Customers) custTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to delete this customer and all associated appointments?");
            alert.setHeaderText("Delete " + selectedCustomer.getCustomerName() + " and any associated appointments.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                CustomerDAO.deleteCustomer(selectedCustomer.getCustomerID());
                AppointmentDAO.deleteAppointment(selectedCustomer.getCustomerID());
                custTableView.setItems(CustomerDAO.createCustomerList());
                aptTableView.setItems(AppointmentDAO.appointmentData());
                custTableView.setItems(CustomerDAO.pullCustomerTable());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        }
    }

    /**
     * Switches screens to the Appointment FXML to add appointment
     * @param actionEvent add appointment button click
     * @throws Exception
     */
    public void addAppointmentButClick(ActionEvent actionEvent) throws Exception{
        Appointments appointments = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AppointmentForm.fxml"));
        Parent scene = loader.load();
        AppointmentController controller = loader.getController();
        controller.pullData(appointments);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Switches screens to Appointment FXML and populates the fields with the selected appointments data to be edited
     * If no appointment is selected then error pops up
     * @param actionEvent update appointment button click
     * @throws Exception
     */
    public void updateAppointmentButClick(ActionEvent actionEvent) throws Exception {
        Appointments selectedAppointment = (Appointments) aptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/AppointmentForm.fxml"));
            Parent scene = loader.load();
            AppointmentController controller = loader.getController();
            controller.pullData(selectedAppointment);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please select an appointment.");
            alert.showAndWait();
        }
    }

    /**
     * Deletes appointment data from database and appointment table view
     * If no appointment selected then error pops up
     * @param actionEvent
     * @throws SQLException
     */
    public void deleteAppointmentButClick(ActionEvent actionEvent) throws SQLException {
        Appointments selectedAppointment = (Appointments) aptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to delete this appointment?");
            alert.setHeaderText("Delete " + selectedAppointment.getTitle());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                AppointmentDAO.deleteAppointment(selectedAppointment.getAptID());
                aptTableView.setItems(AppointmentDAO.appointmentData());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please select an appointment.");
            alert.showAndWait();
        }

    }

    /**
     * Switches screens to the Reports FXML
     * @param actionEvent reports button click
     * @throws Exception
     */
    public void reportsButClick(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/ReportsForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 550);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}
