package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class AppointmentController {
    public TextField appointIdText;
    public TextField titleText;
    public TextField descriptionText;
    public TextField locationText;
    public TextField typeText;
    public ComboBox contactCB;
    public ComboBox startTimeCB;
    public ComboBox endTimeCB;
    public ComboBox customerIdCB;
    public ComboBox userIdCB;
    public DatePicker startDate;
    public DatePicker endDate;

    public void pullData(Appointments appointments) {
        ObservableList<String> contactList = FXCollections.observableArrayList();
        ObservableList<String> customerList = FXCollections.observableArrayList();
        ObservableList<String> userList = FXCollections.observableArrayList();
        ObservableList<String> timeList = FXCollections.observableArrayList();

        try {
            ObservableList<Contacts> contacts = ContactDAO.pullContacts();
            if (contacts != null) {
                for (Contacts contact : contacts) {
                    contactList.add(contact.getID() + " - " + contact.getName());
                }
            }

            ObservableList<Customers> customers = CustomerDAO.pullCustomerTable();
            if (customers != null) {
                for (Customers customer : customers) {
                    customerList.add(customer.getCustomerID() + " - " + customer.getCustomerName());
                }
            }

            ObservableList<User> users = UserDAO.buildUsersList();
            if (users != null) {
                for (User user : users) {
                    userList.add(user.getUserID() + " - " + user.getUserName());
                }
            }

            LocalTime start = LocalTime.of(8, 0);
            LocalTime end = LocalTime.of(22, 0);
            timeList.add(start.toString());
            while (start.isBefore(end)) {
                start = start.plusMinutes(5);
                timeList.add(start.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        contactCB.setItems(contactList);
        customerIdCB.setItems(customerList);
        userIdCB.setItems(userList);
        startTimeCB.setItems(timeList);
        endTimeCB.setItems(timeList);

        if (appointments != null) try {
            appointIdText.setText(Integer.toString(appointments.getAppointmentID()));
            titleText.setText(appointments.getAptTitle());
            descriptionText.setText(appointments.getDescription());
            locationText.setText(appointments.getLocation());
            contactCB.setValue(appointments.getContactID() + " - " + appointments.getContactName());
            typeText.setText(appointments.getType());
            startDate.setValue(appointments.getStartDate());
            startTimeCB.setValue(appointments.getStartTime().toLocalTime());
            endDate.setValue(appointments.getEndDate());
            endTimeCB.setValue(appointments.getEndTime().toLocalTime());
            customerIdCB.setValue(appointments.getCustomerID());
            userIdCB.setValue(appointments.getUserID());

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public int contactInt() {
        String str = String.valueOf(contactCB.getValue());
        String contactCBInt = str.replaceAll("\\D+","");
        return Integer.parseInt(contactCBInt);
    }

    public int customerInt() {
        String str = String.valueOf(customerIdCB.getValue());
        String customerCBInt = str.replaceAll("\\D+","");
        return Integer.parseInt(customerCBInt);
    }

    public int userInt() {
        String str = String.valueOf(userIdCB.getValue());
        String userCBInt = str.replaceAll("\\D+","");
        return Integer.parseInt(userCBInt);
    }

    public int setAppointID(){
        int setAppointID;
        if(appointIdText.getText().isEmpty()){
            setAppointID = 0;
        } else{
            setAppointID = Integer.parseInt(appointIdText.getText());
        }
        return setAppointID;
    }

    public boolean timeValidator (){
        LocalDateTime startTime = LocalDateTime.of(startDate.getValue(),
                LocalTime.parse(startTimeCB.getSelectionModel().getSelectedItem().toString()));
        LocalDateTime endTime = LocalDateTime.of(endDate.getValue(),
                LocalTime.parse(endTimeCB.getSelectionModel().getSelectedItem().toString()));

        return endTime.isBefore(startTime);
    }

    public boolean custOverlap() {
        LocalDateTime startTime = LocalDateTime.of(startDate.getValue(),
                LocalTime.parse(startTimeCB.getSelectionModel().getSelectedItem().toString()));
        LocalDateTime endTime = LocalDateTime.of(endDate.getValue(),
                LocalTime.parse(endTimeCB.getSelectionModel().getSelectedItem().toString()));

        int setAppointID = setAppointID();
        ObservableList<Appointments> appointmentCustID = AppointmentDAO.getCustomerID(customerInt());
        if (appointmentCustID != null) {
            for (Appointments appointments: appointmentCustID){
                if (setAppointID == appointments.getAppointmentID()) {
                    return false;
                }
                if (appointments.getStartTime().isBefore(endTime) && (startTime.isBefore(appointments.getEndTime()))){
                    return true;
                }
            }

        }
        return false;
    }
    public boolean emptyField() {
        if (titleText.getText().isEmpty()) {
            return true;
        } else if (descriptionText.getText().isEmpty()) {
            return true;
        } else if (locationText.getText().isEmpty()) {
            return true;
        } else if (contactCB.getSelectionModel().isEmpty()) {
            return true;
        } else if (typeText.getText().isEmpty()) {
            return true;
        } else if (startDate.getValue() == null) {
            return true;
        } else if (startTimeCB.getValue() == null) {
            return true;
        } else if (endDate.getValue().toString().isEmpty()) {
            return true;
        } else if (endTimeCB.getValue().toString().isEmpty()) {
            return true;
        } else if (customerIdCB.getValue().toString().isEmpty()) {
            return true;
        } else if (userIdCB.getValue().toString().isEmpty()) {
            return true;
        }
        return false;
    }



    public void saveButClick(ActionEvent actionEvent) throws Exception{
        if (emptyField()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please fill out all text fields.");
            alert.showAndWait();
            return;
        }
        else if (custOverlap()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make sure appointments do not overlap.");
            alert.showAndWait();
            return;
        }
        else if (timeValidator()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Appointment end time is before start time.");
            alert.showAndWait();
            return;
        } else if (appointIdText.getText().isEmpty()) {
            AppointmentDAO.createAppointment(
                    titleText.getText(),
                    descriptionText.getText(),
                    locationText.getText(),
                    contactInt(),
                    typeText.getText(),
                    LocalDateTime.of(startDate.getValue(),
                            LocalTime.parse(startTimeCB.getSelectionModel().getSelectedItem().toString())),
                    LocalDateTime.of(endDate.getValue(),
                            LocalTime.parse(endTimeCB.getSelectionModel().getSelectedItem().toString())),
                    customerInt(),
                    (userInt()));
        } else try {
            AppointmentDAO.updateAppointments(
                    Integer.parseInt(appointIdText.getText()),
                    titleText.getText(),
                    descriptionText.getText(),
                    locationText.getText(),
                    contactInt(),
                    typeText.getText(),
                    LocalDateTime.of(startDate.getValue(),
                            LocalTime.parse(startTimeCB.getSelectionModel().getSelectedItem().toString())),
                    LocalDateTime.of(endDate.getValue(),
                            LocalTime.parse(endTimeCB.getSelectionModel().getSelectedItem().toString())),
                    customerInt(),
                    (userInt()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1050, 700);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    public void cancelButClick(ActionEvent actionEvent) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text fields and return you to the main menu. All information entered will not be saved. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1050, 700);
            stage.setTitle("Scheduling Application");
            stage.setScene(scene);
            stage.show();
        }
    }
}
