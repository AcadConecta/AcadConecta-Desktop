package code.acadConecta;

import code.acadConecta.gui.AcadConectaJavaFxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"code.acadConecta", "net.rgielen.fxweaver"})
@SpringBootApplication
public class AcadConectaApplication{

	public static void main(String[] args) {
		/*alternando o fluxo da aplicação para a classe AcadConectaApplicationJavaFx,
		nessa classe, o contexto do spring é inicializado e os controladores JavaFx também*/
		Application.launch(AcadConectaJavaFxApplication.class);
	}

}