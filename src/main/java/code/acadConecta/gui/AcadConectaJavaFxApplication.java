package code.acadConecta.gui;

import code.acadConecta.AcadConectaApplication;
import code.acadConecta.gui.controllers.LoginController;
import code.acadConecta.gui.controllers.RegisterController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AcadConectaJavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    //Stage principal é definido aqui para que sua referência possa ser compartilhada
    private static Stage mainStageApplication;

    //método executado antes do start, inicia o contexto do spring
    @Override
    public void init() {
        //pegando paramentros caso necessario
        String[] args = getParameters().getRaw().toArray(new String[0]);

        //inicializando o contexto do spring com base na classe principal (AcadConectaApplication)
        this.applicationContext = new SpringApplicationBuilder().sources(AcadConectaApplication.class).run(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //atribuindo stage gerado pelo JavaFx a minha variável
        mainStageApplication = stage;
        //pega uma instância do FxWeaver a partir do contexto do spring
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);

        //as telas são carregadas a partir dos controladores
        //o FxWeaver permite carregar as telas enquanto utilizamos as injeções de dependências fornecidas pelo spring
        Parent root = fxWeaver.loadView(LoginController.class);

        Scene scene = new Scene(root);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() {
        //encerra o contexto do spring
        this.applicationContext.close();
        //encerra o JavaFx
        Platform.exit();
    }

    //compartilhando a referência do stage (tela) com outras classes
    public static Stage getMainStageApplication() {
        return mainStageApplication;
    }

}
