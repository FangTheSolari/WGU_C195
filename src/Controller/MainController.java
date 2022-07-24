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

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

import static javafx.fxml.FXMLLoader.load;

public class MainController {
    public TableColumn custIdCol;
    public TableColumn custNameCol;
    public TableColumn custAddressCol;
    public TableColumn custZipCol;
    public TableColumn custPhoneCol;
    public TableColumn custCountryCol;
    public TableColumn custDivisionCol;
    public TableColumn aptIdCol;
    public TableColumn aptTitleCol;
    public TableColumn aptDescriptionCol;
    public TableColumn aptLocationCol;
    public TableColumn aptContactCol;
    public TableColumn aptTypeCol;
    public TableColumn aptStartCol;
    public TableColumn aptEndCol;
    public TableColumn aptCustomerCol;
    public TableColumn aptUserCol;
    public ObservableList selectedToggle;
    @FXML
    private Label appointmentAlertLabel;
    @FXML
    private RadioButton allRadButton;
    @FXML
    private RadioButton weekRadButton;
    @FXML
    private RadioButton monthRadButton;
    @FXML
    private TableView custTableView;
    @FXML
    private TableView aptTableView;

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
                String alert = "There are appointments starting within the next 15 minutes:\n" + "Appointment ID: " + appointment.getAppointmentID() + "\n" +
                        "Date: " + appointment.getStartDate() + "\n" + "Time: " + appointment.getStartTime().toLocalTime();
                appointmentAlertLabel.setText(alert);
            });
        }
    }

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

    public void addCustomerButClick(ActionEvent actionEvent) {



    }


    public void updateCustomerButClick(ActionEvent actionEvent) {



    }

    public void deleteCustomerButClick(ActionEvent actionEvent) throws SQLException{
        Customers selectedCustomer = (Customers) custTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to delete this customer and all associated appointments?");
            alert.setHeaderText("Delete " + selectedCustomer.getCustomerName() + " and any associated appointments.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                AppointmentDAO.deleteAppointment(selectedCustomer.getCustomerID());
                CustomerDAO.deleteCustomer(selectedCustomer.getCustomerID());
                custTableView.setItems(CustomerDAO.createCustomerList());
                aptTableView.setItems(AppointmentDAO.appointmentData());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        }


    }

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

    public void deleteAppointmentButClick(ActionEvent actionEvent) throws SQLException {
        Appointments selectedAppointment = (Appointments) aptTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to delete this appointment?");
            alert.setHeaderText("Delete " + selectedAppointment.getAptTitle());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                AppointmentDAO.deleteAppointment(selectedAppointment.getAppointmentID());
                aptTableView.setItems(AppointmentDAO.appointmentData());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please select an appointment.");
            alert.showAndWait();
        }

    }

    public void reportsButClick(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Report.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 960, 590);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}
