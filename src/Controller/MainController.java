package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    public void aptViewToggle(ActionEvent actionEvent) {
    }

    public void addCustomerButClick(ActionEvent actionEvent) {
    }

    public void updateCustomerButClick(ActionEvent actionEvent) {
    }

    public void deleteCustomerButClick(ActionEvent actionEvent) {
    }

    public void addAppointmentButClick(ActionEvent actionEvent) {
    }

    public void updateAppointmentButClick(ActionEvent actionEvent) {
    }

    public void deleteAppointmentButClick(ActionEvent actionEvent) {
    }

    public void reportsButClick(ActionEvent actionEvent) {
    }
}
