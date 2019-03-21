package controller;

import configuration.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.AlertService;
import service.WindowService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField tf_login;

    @FXML
    private PasswordField tf_password;

    public static int id_logged;

    @FXML
    void loginAction(ActionEvent event) throws SQLException, IOException {
        ps = connection.prepareStatement("select * from users where email=? and password =?");
        ps.setString(1, tf_login.getText());
        ps.setString(2, tf_password.getText());
        //Select -> executeQuery()
        //insert, update, delete. create, drop -> executeUpdate()
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()){
            id_logged=resultSet.getInt(1);
            WindowService.showWindow("/view/courseView.fxml","Formularz zapisu na kurs");
            WindowService.closeWindow(tf_login);

//            System.out.println(resultSet.getInt(1));
//            System.out.println(resultSet.getString(2));
//            System.out.println(resultSet.getString(3));
//            System.out.println(resultSet.getString(4));
//            System.out.println(resultSet.getString(5));
//            System.out.println(resultSet.getBoolean(6));
//            System.out.println(resultSet.getString(7));
//            System.out.println(resultSet.getDate(8));
        } else {
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Blad logowania", "Zarejstruj sie");
        }
    }

    @FXML
    void registerAction(ActionEvent event) throws IOException {
        // utworzenie stage okna nowego widoku
//        Stage stage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/view/registerView.fxml"));
//        stage.setTitle("Panel rejestracji");
//        stage.setScene(new Scene(root));
//        stage.show();
        WindowService.showWindow("/view/registerView.fxml", "Panel rejestracji");
        WindowService.closeWindow(tf_login);

//        Stage stageClosed = (Stage) tf_login.getScene().getWindow();
//        stageClosed.close();
    }

    DBConnector dbConnector;
    Connection connection;
    PreparedStatement ps;

    public void initialize() throws SQLException {
        dbConnector = new DBConnector();
        connection = dbConnector.initConnection();
    }

}
