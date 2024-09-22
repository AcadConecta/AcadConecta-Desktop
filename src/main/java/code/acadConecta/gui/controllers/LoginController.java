package code.acadConecta.gui.controllers;

import code.acadConecta.gui.util.LoadView;
import code.acadConecta.gui.util.Notification;
import code.acadConecta.model.util.HashItem;
import code.acadConecta.model.util.TokenUtil;
import code.acadConecta.security.desktopSecurity.SecurityDesktopConfig;
import code.acadConecta.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@FxmlView("/gui/views/LoginView.fxml")
@Controller
public class LoginController {

    //controlador das transições entre as telas
    @Autowired
    private LoadView loadView;

    @Autowired
    private UserService userService;

    @FXML
    private TextField userEmail;

    @FXML
    private PasswordField userPassword;

    //redirecionar para tela de registro do usuiário
    @FXML
    public void onCreateAccountButtonClicked() {
        //redireciona para a tela de registro de usuário
        loadView.load("RegisterController", "Cadastro", null);
    }

    //verificar se: o email existe no banco e, se existir, verificar se a senha está correta; levando isso em conta, emitir alertas na tela
    @FXML
    public void onLoginButtonClicked() {
        if (userService.validateLogin(new String[] {userEmail.getText(), userPassword.getText()})) {
            //definindo o usuario atual
            TokenUtil.setCurrentUser(HashItem.defineHash(userEmail.getText()));

            loadView.load("MainController", null, "AcadConecta");
            Notification.showNotification("Bem-vindo(a)", null, "Aproveite o AcadConecta!", Alert.AlertType.CONFIRMATION);
        }
        else {
            Notification.showNotification("Erro", null, "A senha informada está incorreta", Alert.AlertType.ERROR);
        }
    }
}
