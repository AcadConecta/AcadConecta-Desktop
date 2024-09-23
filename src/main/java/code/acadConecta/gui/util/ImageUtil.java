package code.acadConecta.gui.util;

import code.acadConecta.gui.AcadConectaJavaFxApplication;
import code.acadConecta.model.entites.User;
import code.acadConecta.model.util.TokenUtil;
import code.acadConecta.services.UserService;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.*;

@Controller
public class ImageUtil {

    private static final String DEFAULT_NAME = "icon_perfil_default_6212.png";

    public String createDialogAndGetPath() {
        FileChooser fileChooser = new FileChooser();

        //filtrando apenas imagens nos arquivos a serem exibidos
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = AcadConectaJavaFxApplication.getMainStageApplication();

        //exibindo pane com os arquivos para ser escolhido
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            return file.getAbsolutePath();
        }
        else {
            Notification.showNotification("Erro", null, "Erro ao carregar a imagem, tente novamente mais tarde", Alert.AlertType.ERROR);
        }
        return " ";
    }

    //geração de bytes da imagem caso o caminho seja relativo, ouy seja, se a imagem for a padrão da plataforma
    public static byte[] generateBytesFromRelative(String resourcePath) {

        byte[] bytesImage = null;
        try (InputStream inputStream = ImageUtil.class.getResourceAsStream("/static/media/" + resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Image not found: " + resourcePath);
            }
            bytesImage = inputStream.readAllBytes();
        } catch (IOException error) {
            System.out.println("Error to convert image in bytes: " + error.getMessage());
        }
        return bytesImage;
    }

    //lógica que define se o caminho da imagem é relativo ou absoluto, com base nisso, op tratamento da imagem será diferente
    private static boolean verifyIsRelativeOrAbsolute(String path) {
        String indexStart = "/static/media/";

        String relativePath = null;

        if (path.length() > indexStart.length()) {
            relativePath = path.substring(indexStart.length());
        }

        if (relativePath != null) {
            return relativePath.equals(DEFAULT_NAME);
        }
        return false;
    }

    //recebe o caminho da imagem selecionada, e, a partir desse caminho, os bytes são gerados
    public byte[] generateBytesImage(String path) {
        byte[] bytesImage = null;

        //caso o caminho seja relativo (imagem padrão) a geração do arquivo segue outra lógica
        if (verifyIsRelativeOrAbsolute(path)) {
            bytesImage = generateBytesFromRelative(DEFAULT_NAME);
        }
        else {
            File file = new File(path);

            try (FileInputStream fileInputStream = new FileInputStream(new File(path))) {
                bytesImage = new byte[(int) file.length()];

                fileInputStream.read(bytesImage);
            } catch (IOException error) {
                System.out.println("Error in load image to bytes: " + error.getMessage());
            }
        }

        return bytesImage;
    }


}
