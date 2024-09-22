package code.acadConecta.gui.util;

import code.acadConecta.gui.AcadConectaJavaFxApplication;
import code.acadConecta.gui.controllers.RegisterController;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        } catch (ClassNotFoundException error) {
            System.out.println("Error to load view, class of controller not found: " + error.getMessage());
        }

    }
}
