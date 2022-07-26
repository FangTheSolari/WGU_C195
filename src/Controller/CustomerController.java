package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import Model.Country;
import Model.Customers;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller class that provides logic for the customer screen.
 * @author Adam Rutland-Ruiz
 */
public class CustomerController {
    /**
     * Customer ID Label
     */
    public Label custIdLabel;
    /**
     * Customer Name Label
     */
    public Label custNameLabel;
    /**
     * Customer Address Label
     */
    public Label custAddressLabel;
    /**
     * Customer Postal Code Label
     */
    public Label custZipLabel;
    /**
     * Customer Phone Label
     */
    public Label custPhoneLabel;
    /**
     * Customer Country Label
     */
    public Label custCountryLabel;
    /**
     * Customer Division Label
     */
    public Label custDivisionLabel;
    /**
     * Customer ID text box
     */
    public TextField custIdText;
    /**
     * Customer Name text box
     */
    public TextField custNameText;
    /**
     * Customer Address text box
     */
    public TextField custAddressText;
    /**
     * Customer Postal Code text box
     */
    public TextField custZipText;
    /**
     * Customer Phone text box
     */
    public TextField custPhoneText;
    /**
     * Customer Country combo box
     */
    public ComboBox custCountryCB;
    /**
     * Customer Division combo box
     */
    public ComboBox custDivisionCB;
    /**
     * observable list of countries from database
     */
    private ObservableList countriesList = FXCollections.observableArrayList();

    /**
     * Validates if any fields in the Customer screen are empty
     * @return boolean checker to see if any fields are empty
     */
    public boolean emptyField() {
        if (custNameText.getText().isEmpty()) {
            return true;
        }
        else if (custAddressText.getText().isEmpty()) {
            return true;
        }
        else if (custZipText.getText().isEmpty()) {
            return true;
        }
        else if (custPhoneText.getText().isEmpty()) {
            return true;
        }
        else if (custCountryCB.getValue().toString().isEmpty()) {
            return true;
        }
        else if (divisionInt().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Removes non-integer values from the division box
     * @return
     */
    public String divisionInt() {
        String div = (String) custDivisionCB.getValue();
        return div.replaceAll("\\D+","");
    }

    /**
     * Pulls the data for list of countries to populate the Combo Box
     * @param actionEvent selecting a country from the list
     * @throws SQLException
     */
    public void countrySelect(ActionEvent actionEvent) throws SQLException {
        ObservableList<String> divs = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisions = new DivisionDAO().pullCountry((String) custCountryCB.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (Division division : divisions) {
                    divs.add(division.getDivisionID() + " - " + division.getDivision());
                }
            }
            custDivisionCB.setItems(divs);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Pulls appointment data from the database and places it in the correct fields.
     * @param customer
     * @throws SQLException
     */
    public void sendData(Customers customer) throws SQLException {
        try {
            ObservableList<Country> countries = CountryDAO.getCountries();
            if (countries != null) {
                for (Country country : countries) {
                    countriesList.add(country.getCountry());
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        custCountryCB.setItems(countriesList);
        if (customer != null) try {
            custIdText.setText(Integer.toString(customer.getCustomerID()));
            custAddressText.setText((customer.getCustomerAddress()));
            custNameText.setText(customer.getCustomerName());
            custZipText.setText((customer.getCustomerPostalCode()));
            custPhoneText.setText((customer.getCustomerPhone()));
            custCountryCB.setValue(customer.getCountryID() + " - " + customer.getCountry());
            custDivisionCB.setValue(customer.getCustomerDivisionID() + " - " + customer.getDivision());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Checks for save button being clicked, if it is and any fields are empty they will comeback with an error
     * if no empty fields will save the customer as a new customer if the customerID field is empty
     * if the customerID field is not empty it will update the existing customer in the database
     * after saving to the database it will throw back to the main menu screen
     * @param actionEvent save button click
     * @throws Exception
     */
    public void saveButClick(ActionEvent actionEvent) throws Exception {
        if (emptyField()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
            return;
        }
        else if (custIdText.getText().isEmpty()) {
            CustomerDAO.createCustomer(
                    custNameText.getText(),
                    custAddressText.getText(),
                    custZipText.getText(),
                    custPhoneText.getText(),
                    divisionInt());
        }
        else try {
            CustomerDAO.updateCustomer(
                    Integer.parseInt(custIdText.getText()),
                    custNameText.getText(),
                    custAddressText.getText(),
                    custZipText.getText(),
                    custPhoneText.getText(),
                    divisionInt());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1050, 700);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cancel button will check if the user wants to cancel adding/updating the appointment
     * if the answer is no then it will allow the user to go back to appointment screen
     * if the answer is yes then the appointment screen will not send any data to the database and throw back to the main menu
     * @param actionEvent
     * @throws Exception
     */
    public void cancelButClick(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text fields and return you to the main menu. All information entered will not be saved. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1050, 700);
            stage.setTitle("Scheduling Application");
            stage.setScene(scene);
            stage.show();
        }
    }
}
