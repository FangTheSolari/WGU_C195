package Model;

import Database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {


    public static void main(String[] args){
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        ResourceBundle language = ResourceBundle.getBundle("Resource/Language", Locale.getDefault());
        primaryStage.setTitle(language.getString("application"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

}
