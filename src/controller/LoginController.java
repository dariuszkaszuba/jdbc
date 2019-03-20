package controller;

import configuration.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField tf_login;

    @FXML
    private PasswordField tf_password;

    @FXML
    void loginAction(ActionEvent event) throws SQLException {
        ps = connection.prepareStatement("select * from users where email=? and password =?");
        ps.setString(1, tf_login.getText());
        ps.setString(2, tf_password.getText());
        //Select -> executeQuery()
        //insert, update, delete. create, drop -> executeUpdate()
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()){
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
            System.out.println(resultSet.getString(4));
            System.out.println(resultSet.getString(5));
            System.out.println(resultSet.getBoolean(6));
            System.out.println(resultSet.getString(7));
            System.out.println(resultSet.getDate(8));
        } else {
            System.out.println("Błąd logowania");
        }

    }

    @FXML
    void registerAction(ActionEvent event) {
    }

    DBConnector dbConnector;
    Connection connection;
    PreparedStatement ps;

    public void initialize() throws SQLException {
        dbConnector = new DBConnector();
        connection = dbConnector.initConnection();
    }

}
