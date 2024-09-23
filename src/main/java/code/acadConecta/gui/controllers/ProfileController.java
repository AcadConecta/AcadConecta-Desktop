package code.acadConecta.gui.controllers;

import code.acadConecta.gui.util.ImageUtil;
import code.acadConecta.gui.util.LoadView;
import code.acadConecta.gui.util.Notification;
import code.acadConecta.mail.MailService;
import code.acadConecta.model.entites.User;
import code.acadConecta.model.util.TokenUtil;
import code.acadConecta.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@FxmlView("/gui/views/ProfileView.fxml")
@Controller
public class ProfileController implements Initializable {

    @Autowired
    private UserService userService;

    @Autowired
    private LoadView loadView;

    @Autowired
    private ImageUtil imageUtil;

    @FXML
    private HBox rootElement;

    @FXML
    private ImageView icor_perfil;

    @FXML
    private Label userName;

    @FXML
    private Label userEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (rootElement.isVisible()) {

            User user = userService.findById(TokenUtil.getCurrentUser());
            //carregando informações do usuário na tela
            if (user != null) {
               icor_perfil.setImage(userService.getImageOfCurrentUser());
               userName.setText(user.getName());
               userEmail.setText(TokenUtil.getCurrentuserEmail());
            }
        }
    }

    //atualiza a view do usuário
    private void updateNameInView(String name) {
        userName.setText(name);
    }

    //evento para atualização do nome de perfil do usuário
    @FXML
    public void onChangeNameButtonClicked() {
        String newName = Notification.showInputDialog();

        if (!(newName.isEmpty())) {
            userService.updateUserName(newName);
            updateNameInView(newName);
        }
        else {
            Notification.showNotification("Erro", null, "Erro ao atualizar o nome do usuário, tente novamente mais tarde", Alert.AlertType.ERROR);
        }
    }

    //atualiza a imagem na exibição do perfil
    private void updateImageInView() {
        icor_perfil.setImage(userService.getImageOfCurrentUser());
    }

    //inicia o processo para que a imagem seja carregada e salva no banco de dados
    @FXML
    public void onUpdateImageButtonClicked() {
        userService.updateImageInDataBase();
        updateImageInView();
    }

    //redirecionando para a tela de login
    @FXML
    public void onLogOutButtonClicked() {
        loadView.load("LoginController", "Login", null);
        Notification.showNotification("Obrigado", null, "Em nome da equipe AcadConecta: obrigado pelo acesso!", Alert.AlertType.CONFIRMATION);
    }

    //inicia o processo de deleção de conta
    @FXML
    public void onDeleteAccountButtonClicked() {
        if (Notification.showConfirmationDialog()) {
            userService.deleteByCurrentUser();
            loadView.load("LoginController", "Login", null);
            Notification.showNotification("Deletado", null, "Sua conta foi deletada da plataforma!", Alert.AlertType.INFORMATION);

        }
        else {
            Notification.showNotification("Cancelado", null, "Deleção da conta cancelada pelo usuário", Alert.AlertType.INFORMATION);
        }
    }

    //executando a lógica para enviar email solicitando acesso especial
    public void onRequestSpecialAccessButtonClicked() {
        String requestBody = "O usuário com email " + TokenUtil.getCurrentuserEmail() + ", hash n° " + TokenUtil.getCurrentUser() + " deseja ter acesso especial na plataforma AcadConecta";
        if (MailService.sendEmail("Solicitação para acesso especial do AcadConecta", requestBody)) {
            Notification.showNotification("Enviado", null, "Sua solicitação foi enviada para a análise, um email de confirmação será enviado para " + TokenUtil.getCurrentuserEmail() + " após a validção", Alert.AlertType.INFORMATION);
        }
        else {
            Notification.showNotification("Erro", null, "Não foi possível finalizar o pedido de acesso especial, tente novamente mais tarde", Alert.AlertType.ERROR);
        }
    }
}
