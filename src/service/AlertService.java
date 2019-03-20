package service;

import javafx.scene.control.Alert;

public class AlertService {
    public static void showAlert(Alert.AlertType alert, String header, String content){
        Alert a = new Alert(alert);
        a.setTitle("Ważna informacja");
        a.setHeaderText(header);
        a.setContentText(content);
        a.show();
    }
}
