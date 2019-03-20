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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import service.AlertService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class RegisterController {


    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_lastname;

    @FXML
    private TextField tf_login;

    @FXML
    private PasswordField pf_password1;

    @FXML
    private PasswordField pf_password2;


    private void clear() {
        tf_name.clear();
        tf_lastname.clear();
        tf_login.clear();
        pf_password1.clear();
        pf_password2.clear();
    }

    @FXML
    void clearAction(ActionEvent event) {
        clear();
    }

    private void insertData() throws IOException {
        try {
            if (tf_login.getText().equals("") || pf_password1.getText().equals("") || tf_name.getText().equals("") || tf_lastname.getText().equals("")) {
                throw new NullPointerException();
            }
            if (!pf_password1.getText().equals(pf_password2.getText())) {
                throw new InputMismatchException();
            }
            ps = connection.prepareStatement("insert into users values (default ,?,?,?,?, default , default ,default)");
            ps.setString(1, tf_name.getText());
            ps.setString(2, tf_lastname.getText());
            ps.setString(3, tf_login.getText());
            ps.setString(4, pf_password1.getText());
            ps.executeUpdate();

            AlertService.showAlert(Alert.AlertType.INFORMATION, "Rejestracja", "Zarejestrowano uzytkownika");
            clear();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
            stage.setTitle("Panel rejestracji");
            stage.setScene(new Scene(root));
            stage.show();

            Stage stageClosed = (Stage) tf_login.getScene().getWindow();
            stageClosed.close();

        } catch (NullPointerException e) {
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Brak wartosci", "Uzupelnij brakujace pola");
//            Alert a = new Alert(Alert.AlertType.ERROR);
//            a.setTitle("Błąd");
//            a.setHeaderText("Musisz uzupelnic wszystkie pola");
//            a.setContentText("Uzupełnij wszystkie pola");
//            a.show();
//            clear();

        } catch (SQLException e) {
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Błedny login", "Login istnieje w bazie danych");
//            Alert a = new Alert(Alert.AlertType.ERROR);
//            a.setTitle("Błąd");
//            a.setHeaderText("Podany login juz istnieje w bazie danych");
//            a.setContentText("Musisz podac inny login");
//            a.show();
//            clear();
        } catch (InputMismatchException e) {
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Różne hasła", "Hasla musza byc jednakowe");
//            Alert a = new Alert(Alert.AlertType.ERROR);
//            a.setTitle("Błąd");
//            a.setHeaderText("Podane hasla nie sa jednakowe");
//            a.setContentText("Musisz podac jednakowe hasla");
//            a.show();
        }
    }


    @FXML
    void keyRegisterAction(KeyEvent event) throws IOException {
//        if(event.getCode().equals(KeyCode.ENTER)) {
//            insertData();
//        } else if(event.getCode().equals(KeyCode.ESCAPE)){
//            clear();
//        }

        Map<KeyCode, Integer> keyCodeToInteger = new HashMap<>();
        keyCodeToInteger.put(KeyCode.ENTER, 1);
        keyCodeToInteger.put(KeyCode.ESCAPE, 2);

        switch (keyCodeToInteger.get(event.getCode())) {
            case 1:
                insertData();
                break;
            case 2:
                clear();
                break;
        }
    }

    @FXML
    void registerAction(ActionEvent event) throws IOException {
        insertData();

    }

    DBConnector dbConnector;
    Connection connection;
    PreparedStatement ps;

    public void initialize() throws SQLException {
        dbConnector = new DBConnector();
        connection = dbConnector.initConnection();
    }

}

