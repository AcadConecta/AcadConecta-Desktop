package code.acadConecta.gui.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MessageController implements Initializable {
    private static final String URL_CONTENT = "https";

    @FXML
    private ImageView user_icon;
    @FXML
    private Label hashUser;
    @FXML
    private Label user_name;
    @FXML
    private Label date_send;
    @FXML
    private Label hour_send;
    @FXML
    private Text message_content;
    @FXML
    private VBox rootMessage;
    @FXML
    private Region backgroundRegion;
    @FXML
    private StackPane stackPaneMessage;

    private boolean checkHaveLink (String text) {
        return text.toLowerCase().contains(URL_CONTENT);
    }

    private String getUrlInText(String text) {
        int indexStart = text.toLowerCase().indexOf(URL_CONTENT);
        String urlStart = text.substring(indexStart);

        int indexFinal = urlStart.indexOf(" ");

        if (indexFinal == -1) {
            indexFinal = urlStart.length();
        }

        return urlStart.substring(0, indexFinal);
    }


    private void onTextWithUrlClicked(Event event, String text) {
        String url = getUrlInText(text);

        //App.getHostSerciveInApp().showDocument(url);
    }

    public void setContent(String name, String date, String hour, String text, Image icon, String hash_user) {
        //montando a mensagem com o conteúdo do banco
        if (icon != null){
            user_icon.setImage(icon);
            Rectangle rectangle = new Rectangle(0, 0, 38, 38);
            rectangle.setArcHeight(35);
            rectangle.setArcWidth(35);
            user_icon.setClip(rectangle);
        }
        user_name.setText(name);
        date_send.setText(date);
        hour_send.setText(hour);
        hashUser.setText(hash_user);
        if (checkHaveLink(text)) {
            message_content.setCursor(Cursor.HAND);
            message_content.setOnMouseClicked(event -> onTextWithUrlClicked(event, text));

            String replacedText = text.replace(getUrlInText(text), "Link conteúdo");

            message_content.setText(replacedText);
        }
        else {
            message_content.setText(text);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (backgroundRegion.isVisible()) {
            //adaptando o tamanho do corpo da mesnagem com base nas dimensões do texto
            backgroundRegion.prefWidthProperty().bind(stackPaneMessage.widthProperty());
            backgroundRegion.prefWidthProperty().bind(stackPaneMessage.heightProperty());
        }
    }
}