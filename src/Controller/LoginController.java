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

public class LoginController {
    public Label locationTitleLabel;
    public Label locationLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public TextField usernameText;
    public TextField passwordText;
    public Button loginButton;
    public Label errorLabel;

    private final ResourceBundle setLanguage = ResourceBundle.getBundle("Resource/Language", Locale.getDefault());


    public void initialize() {
        locationLabel.setText(String.valueOf(TimeZone.getDefault().getID()));
        loginButton.setText(setLanguage.getString("login"));
        usernameLabel.setText(setLanguage.getString("username"));
        passwordLabel.setText(setLanguage.getString("password"));
    }


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