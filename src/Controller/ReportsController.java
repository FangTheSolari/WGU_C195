package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;

public class ReportsController {
    public Label sortByLabel;
    @FXML
    private TableView reportsTable;
    @FXML
    private RadioButton typeAndMonthRadBut;
    @FXML
    private RadioButton contactsRadBut;
    @FXML
    private RadioButton usersRadBut;
    public Button backButton;

    public void sortByToggle(ActionEvent actionEvent) {
    }


    public void backButClick(ActionEvent actionEvent) {
    }
}
