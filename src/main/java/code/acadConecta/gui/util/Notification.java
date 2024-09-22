package code.acadConecta.gui.util;

import javafx.scene.control.Alert;

public class Notification {
    public static void showNotification(String title, String header, String contentText, Alert.AlertType alertType) {
        Alert notification = new Alert(alertType);

        //definindo conteúdo da notificação
        notification.setTitle(title);
        notification.setHeaderText(header);
        notification.setContentText(contentText);

        notification.show();
    }
}