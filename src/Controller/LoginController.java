package Controller;

import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller class that provides logic for the Login screen.
 * @author Adam Rutland-Ruiz
 */
public class LoginController {
    /**
     * Location Title Label
     */
    public Label locationTitleLabel;
    /**
     * Location Label
     */
    public Label locationLabel;
    /**
     * Username Label
     */
    public Label usernameLabel;
    /**
     * Password Label
     */
    public Label passwordLabel;
    /**
     * Username Text box
     */
    public TextField usernameText;
    /**
     * Password Text Box
     */
    public TextField passwordText;
    /**
     * Login button
     */
    public Button loginButton;
    /**
     * Error label
     */
    public Label errorLabel;
    /**
     * Sets the language of the login screen based on the users computer laanguage (only English and French Currently)
     */
    private final ResourceBundle setLanguage = ResourceBundle.getBundle("Resource/Language", Locale.getDefault());

/**
 * Initializes login and sets the paage up in the language based off the Resource bundle chosen.
 */
    public void initialize() {
        locationLabel.setText(String.valueOf(TimeZone.getDefault().getID()));
        loginButton.setText(setLanguage.getString("login"));
        usernameLabel.setText(setLanguage.getString("username"));
        passwordLabel.setText(setLanguage.getString("password"));
    }

    /**
     * Validates if the username and password match the ones in the database
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void loginButClick(ActionEvent actionEvent) throws SQLException, IOException {
        if (!inputValidation()) return;
        boolean validLogin = UserDAO.validateLogin(usernameText.getText(), passwordText.getText());
        if (validLogin) {
            UserDAO.validateLogin(usernameText.getText(), passwordText.getText());
            loginActivity(usernameText.getText() + " has logged in at ");

            Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1050, 700);
            stage.setTitle("Scheduling Application");
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText(setLanguage.getString("void"));
            loginActivity(usernameText.getText() + " has unsuccessfully attempted to log in at ");
        }
    }

    /**
     * Checks if the username or password text boxes are empty
     * @return boolean checker to see if any fields are empty
     */
    private boolean inputValidation() {
        if (usernameText.getText().isEmpty()) {
            errorLabel.setText(setLanguage.getString("username_req"));
            return false;
        } else if (passwordText.getText().isEmpty()) {
            errorLabel.setText(setLanguage.getString("password_req"));
            return false;
        }
        return true;
    }


    /**
     * Creates a log if login attempts and annotates if they were failed or successful
     * @param loginText
     */
    private void loginActivity(String loginText) {
        try (FileWriter fileWriter = new FileWriter("login_activity.txt", true)) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            fileWriter.write(loginText + timeFormat.format(date) + "\n");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}