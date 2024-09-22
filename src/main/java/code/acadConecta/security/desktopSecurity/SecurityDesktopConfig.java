package code.acadConecta.security.desktopSecurity;

import code.acadConecta.gui.util.Notification;
import code.acadConecta.model.entites.User;
import code.acadConecta.model.util.HashItem;
import code.acadConecta.persistence.GenerateChanels;
import code.acadConecta.services.ChanelService;
import code.acadConecta.services.UserService;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class SecurityDesktopConfig {

    @Autowired
    private ChanelService chanelService;

    //verifica se algum parametro enviado é nulo
    public boolean checkHaveNull(String[] params) {
        for (String param: params) {
            if (param.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    //gera o hash do email e senha do usuário que será cadastrado
    public String[] generateEncrypted(String userEmail, String userPassword) {
        //retornando senha e email criptografados
        return new String[] {HashItem.defineHash(userEmail), HashItem.defineHash(userPassword)};
    }

    //relaciona o usuário com todos os canais da plataforma
    public void createRelationWithChanel(User user) {
        user.setChanelList(chanelService.findAll());
    }

}
