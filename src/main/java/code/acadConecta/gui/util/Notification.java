package code.acadConecta.gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class Notification {
    public static void showNotification(String title, String header, String contentText, Alert.AlertType alertType) {
        Alert notification = new Alert(alertType);

        //definindo conteúdo da notificação
        notification.setTitle(title);
        notification.setHeaderText(header);
        notification.setContentText(contentText);

        notification.show();
    }

    public static String showInputDialog() {
        String inputText = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alterar");
        alert.setHeaderText("Digite o novo nome para seu perfil: ");

        // Cria um campo de texto
        TextField textField = new TextField();
        textField.setPromptText("Digite aqui");

        // Adiciona o campo de texto ao diálogo
        VBox vbox = new VBox(textField);
        vbox.setSpacing(10);
        alert.getDialogPane().setContent(vbox);

        // Mostra o diálogo e aguarda a resposta
        Optional<ButtonType> result = alert.showAndWait();

        // Processa a resposta e o texto digitado
        if (result.isPresent() && result.get() == ButtonType.OK) {
            inputText = textField.getText();
        }
        return inputText;
    }

    // Método que exibe o diálogo de confirmação
    public static boolean showConfirmationDialog() {
        // Criar o diálogo do tipo CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Tem certeza?");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que deseja deletar sua conta (essa ação é irreversível)?");

        //botões de confirmação ou cancelar
        ButtonType confirmButton = new ButtonType("Confirmar");
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> response = alert.showAndWait();

        return response.isPresent() && response.get() == confirmButton;
    }
}