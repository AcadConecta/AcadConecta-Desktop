package code.acadConecta.gui.util;

import code.acadConecta.gui.AcadConectaJavaFxApplication;
import code.acadConecta.gui.controllers.MessageController;
import code.acadConecta.gui.controllers.RegisterController;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoadView {

    @Autowired
    private FxWeaver fxWeaver;

    public void load(String controllerViewName, String titleView, String cssFileName)  {
        try {
            //referencia do stage principal
            Stage mainStage = AcadConectaJavaFxApplication.getMainStageApplication();

            Parent root = fxWeaver.loadView(Class.forName("code.acadConecta.gui.controllers." + controllerViewName));

            Scene scene = new Scene(root);

            mainStage.setTitle(titleView);
            mainStage.setScene(scene);
            mainStage.show();

            fullScreen(mainStage);

        } catch (ClassNotFoundException error) {
            System.out.println("Error to load view, class of controller not found: " + error.getMessage());
        }

    }

    //perspective se refere a perspectiva do usuário, ou seja, se a mensagem pertence a ele ou a outro user
    //true = proprio usuário, false = outro usuário
    public VBox generateMessageElement(boolean perspective) {
        VBox message = null;

        message = fxWeaver.loadView(MessageController.class, "/gui/views/components/MessageViewOther.fxml");

        if (perspective) {
            message = fxWeaver.loadView(MessageController.class, "/gui/views/components/MessageViewSelf.fxml");
        }

        return message;
    }

    //método que define a largura máxima do stage com base no tamanho da tela do usuário
    private static void fullScreen(Stage stage) {
        Screen screen = Screen.getPrimary();
        double width = screen.getBounds().getWidth();
        double height = screen.getBounds().getHeight();

        stage.setWidth(width);
        stage.setHeight(height);
    }
}
