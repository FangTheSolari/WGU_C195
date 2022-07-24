package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.ZoneId;
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

    private ResourceBundle setLanguage = ResourceBundle.getBundle("Resource/Language", Locale.getDefault());

    public void initialize() {
        locationLabel.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
        loginButton.setText(setLanguage.getString("login"));
        usernameLabel.setText(setLanguage.getString("username"));
        passwordLabel.setText(setLanguage.getString("password"));
    }

    public void loginButClick(ActionEvent actionEvent) {
    }
}
