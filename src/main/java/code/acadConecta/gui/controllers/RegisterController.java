package code.acadConecta.gui.controllers;

import code.acadConecta.gui.util.LoadView;
import code.acadConecta.gui.util.Notification;
import code.acadConecta.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@FxmlView("/gui/views/RegisterView.fxml")
@Controller
public class RegisterController {

    //controlador das transições entre as telas
    @Autowired
    private LoadView loadView;

    //seriviço para persistência
    @Autowired
    private UserService userService;

    @FXML
    private TextField userName;

    @FXML
    private TextField userEmail;

    @FXML
    private PasswordField userPassword;

    //quando a operação for cancelada o usuário e redirecionado para tela de login e um alerta é emitido
    @FXML
    public void onCancelButtonClicked() {
        loadView.load("LoginController", "Login", null);
        //exibindo mensagem no caso do usuário cancelar a operação
        Notification.showNotification("Operação cancelada", null, "Criação de conta cancelada pelo usuário", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void onCreateAccountButtonClicked() {
        //passando as informações de entrada para serem tratradas e adicionadas no banco
       if (userService.save(new String[] {userEmail.getText(), userName.getText(), userPassword.getText()})) {
           Notification.showNotification("Sucesso", null, "Usuário cadastrado com sucesso, realize o login para aproveitar a plataforma", Alert.AlertType.CONFIRMATION);
           loadView.load("LoginController", "Login", null);
       }
    }
}
