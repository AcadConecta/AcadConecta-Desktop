package code.acadConecta.services;

import code.acadConecta.gui.util.ImageUtil;
import code.acadConecta.gui.util.LoadView;
import code.acadConecta.gui.util.Notification;
import code.acadConecta.model.entites.User;
import code.acadConecta.model.util.HashItem;
import code.acadConecta.model.util.TokenUtil;
import code.acadConecta.repositories.UserRepository;
import code.acadConecta.security.desktopSecurity.SecurityDesktopConfig;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.NumberUp;
import javax.swing.text.Utilities;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final int USER_EMAIL = 0;
    private static final int USER_NAME = 1;
    private static final int USER_PASSWORD = 2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityDesktopConfig securityDesktop;

    @Autowired
    private LoadView loadView;

    @Autowired
    private ImageUtil imageUtil;

    //retorna a imagem do usuário logado
    public Image getImageOfCurrentUser() {
        User user = findById(TokenUtil.getCurrentUser());

        ByteArrayInputStream bytes = new ByteArrayInputStream(user.getImage());
        return new Image(bytes);
    }

    //atualiza apaenas o valor do campo imagem do usuário
    public void updateImageInDataBase() {
        User user = findById(TokenUtil.getCurrentUser());

        try {
            if (user == null) {
                throw new Exception();
            }

            user.setImage(imageUtil.generateBytesImage(imageUtil.createDialogAndGetPath()));

            saveByInstance(user);

        } catch (Exception error) {
            System.out.println("Error in get user on database (process: update image): " + error.getMessage());
        }

    }

    //verifica se o usuário que está sendo criado já está cadastrado no banco de dados
    public boolean checkUserInDatabase(String userEmail) {
        return findById(userEmail) != null;
    }

    //valida o usuário e depois o salva no banco (se estiver sendo criado pela página de registreo)
    public boolean save(String[] params) {

        //verificando se os campos não estão vazios
        if (securityDesktop.checkHaveNull(params)) {
            Notification.showNotification("Erro", null, "Parâmetros vazios não são permitidos, verifique os campos e tente novamente", Alert.AlertType.ERROR);
            return false;
        }

        //verifica se o email informado já foi cadastrado no banco anteriormente
        if (checkUserInDatabase(HashItem.defineHash(params[USER_EMAIL]))) {
            Notification.showNotification("Erro", null, "Usuário já cadastrado na AcadConecta", Alert.AlertType.ERROR);
            return false;
        }

        if (!(params[USER_EMAIL].contains("@"))) {
            Notification.showNotification("Erro", null, "O formato do email informado não é válido", Alert.AlertType.ERROR);
            return false;
        }

        String[] paramsEncrypted = securityDesktop.generateEncrypted(params[USER_EMAIL], params[USER_PASSWORD]);

        byte[] imageBytes = imageUtil.generateBytesImage("/static/media/icon_perfil_default_6212.png");

        User user = new User(paramsEncrypted[0], params[USER_NAME], paramsEncrypted[1], imageBytes, null);

        //adicionando relação entre usuário e os canais
        securityDesktop.createRelationWithChanel(user);

        userRepository.save(user);

        return true;
    }

    //adiciona uma instância direta do usuário
    public void saveByInstance(User user) {
        userRepository.save(user);
    }

    public User findById(String userEmail) {
        Optional<User> userOptional = userRepository.findById(userEmail);

        return userOptional.orElse(null);
    }

    //valida as informações do usuário que está tentando fazer login
    public boolean validateLogin(String[] params) {

        String[] encryptedParams = securityDesktop.generateEncrypted(params[0], params[1]);

        if (securityDesktop.checkHaveNull(params)) {
            Notification.showNotification("Erro", null, "Parâmetros vazios não são permitidos, verifique os campos e tente novamente", Alert.AlertType.ERROR);
            return false;
        }

        if (!checkUserInDatabase(encryptedParams[0])) {
            Notification.showNotification("Erro", null, "Usuário não cadastrado", Alert.AlertType.ERROR);
            return false;
        }

        if (findById(encryptedParams[0]).getPassword().equals(encryptedParams[1])) {
            return true;
        }

        Notification.showNotification("Erro", null, "A senha informada está incorreta", Alert.AlertType.ERROR);
        return false;
    }

    //atualiza o nome do usuário no banco de dados
    public void updateUserName(String name) {
        User user = findById(TokenUtil.getCurrentUser());

        try {
            if (user == null) {
                throw new NullPointerException();
            }

            user.setName(name);

            saveByInstance(user);
        } catch (NullPointerException error) {
            System.out.println("Error in load user of database: " + error.getMessage());
        }

    }

    //deleta o usuário logado
    public void deleteByCurrentUser() {
        userRepository.deleteById(TokenUtil.getCurrentUser());
    }
}
