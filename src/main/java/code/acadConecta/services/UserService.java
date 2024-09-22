package code.acadConecta.services;

import code.acadConecta.gui.util.LoadView;
import code.acadConecta.gui.util.Notification;
import code.acadConecta.model.entites.User;
import code.acadConecta.model.util.HashItem;
import code.acadConecta.model.util.TokenUtil;
import code.acadConecta.repositories.UserRepository;
import code.acadConecta.security.desktopSecurity.SecurityDesktopConfig;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.Utilities;
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

    //verifica se o usuário que está sendo criado já está cadastrado no banco de dados
    public boolean checkUserInDatabase(String userEmail) {
        return findById(userEmail) != null;
    }

    //valida o usuário e depois o salva no banco
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

        User user = new User(paramsEncrypted[0], params[USER_NAME], paramsEncrypted[1], null, null);

        //adicionando relação entre usuário e os canais
        securityDesktop.createRelationWithChanel(user);

        userRepository.save(user);

        return true;
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

        System.out.println(findById(encryptedParams[0]).getPassword());
        System.out.println(encryptedParams[1]);

        return findById(encryptedParams[0]).getPassword().equals(encryptedParams[1]);
    }
}
