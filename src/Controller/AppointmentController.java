package Controller;

import DAO.AppointmentDAO;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

                }
            }

        }
    }

    public void saveButClick(ActionEvent actionEvent) {
    }

    public void cancelButClick(ActionEvent actionEvent) {
    }
}
