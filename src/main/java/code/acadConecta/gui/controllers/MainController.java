package code.acadConecta.gui.controllers;

import code.acadConecta.gui.AcadConectaJavaFxApplication;
import code.acadConecta.model.entites.User;
import code.acadConecta.model.util.TokenUtil;
import code.acadConecta.services.ChanelService;
import code.acadConecta.services.UserService;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
@FxmlView("/gui/views/MainView.fxml")
@Controller
public class MainController {

    @Autowired
    private ChanelService chanelService;

    @Autowired
    private UserService userService;

    protected static final Long EDITAIS = 1L;
    protected static final Long VAGAS = 2L;
    protected static final Long CHAT = 3L;
    protected static final int CANAIS = 1;
    protected static final int PROFILE = 2;
    private boolean orientation = true;

    @FXML
    private ImageView imageView;

    @FXML
    private Label titlePanel;

    @FXML
    private ScrollPane scrollPaneMain;

    @FXML
    private VBox contentScrollPane;

    @FXML
    private HBox chatBar;

    @FXML
    private TextField contentChatBar;

    @FXML
    private VBox expandedMenu;

    @FXML
    private HBox rootElement;

    @FXML VBox profileVbox;

    @FXML
    private VBox vboxDefault;

    private static int actualPane;

    private VBox paneViewSuport;

    private boolean menuIsactive = true;


    @FXML
    private void toggleMenu() {
        //setando animação do menu lateral
        FadeTransition ft = new FadeTransition(Duration.millis(300), expandedMenu);

        RotateTransition rotateTransition = new RotateTransition();

        rotateTransition.setNode(imageView);
        rotateTransition.setDuration(Duration.seconds(.3));
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);

        //verifica se o atributo "menuIsactive" true, caso não seja, o menu não será expandido
        if (menuIsactive) {
            if (orientation) {
                rotateTransition.setByAngle(90);
                orientation = !orientation;
            }
            else {
                rotateTransition.setByAngle(-90);
                orientation = !orientation;
            }

            // Iniciar a rotação
            rotateTransition.play();

            if (expandedMenu.isVisible()) {
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.setOnFinished(e -> expandedMenu.setVisible(false));
            } else {
                expandedMenu.setVisible(true);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
            }

            ft.play();
            rotateTransition.play();
        }

        onAnyChanelClicked();
    }

    //define o título do canal
    private void setTitleChanel(Long id) {
        if (titlePanel.isVisible()) {
            String chanelTitle = chanelService.findById(id).getName();
            if (chanelTitle != null) {
                titlePanel.setText(chanelTitle);
            }
            else {
                System.out.println("Error to load title page");
            }
        }
    }

    private String dateFormater(String dateIn) {
        String dateDefault = "yyyy-MM-dd";
        String dateOutput = "dd/MM/yyyy";

        String dateFormatted = null;

        //ajustando o formato da data vinda do banco para o nosso padrão
        DateTimeFormatter dtm = DateTimeFormatter.ofPattern(dateDefault);
        DateTimeFormatter dtmOutput = DateTimeFormatter.ofPattern(dateOutput);

        try {
            // Analisar a string de entrada usando o padrão de entrada
            LocalDate date = LocalDate.parse(dateIn, dtm);

            // Formatando para o novo padrão
            dateFormatted = date.format(dtmOutput);


        } catch (DateTimeParseException e) {
            System.out.println("Erro ao converter a data: " + e.getMessage());
        }
        return dateFormatted;
    }

//    private void loadInView(List<Message> messages) {
//        messages.forEach(message -> {
//            try {
//                // Pegando usuário associado à mensagem
//                Users users = DataService.findByHashEmail(message.getId_users().getEmail());
//
//                String idHashUser = "#" + (users.getEmail().substring(0, 4));;
//
//                FXMLLoader fxmlLoader = null;
//                VBox messageElement = null;
//
//                // Usado para definir o tamanho da margem esquerda da mensagem
//                boolean user_self = true;
//
//                // Adicionando estilo diferente se a mensagem foi enviada pelo usuário logado
//                if (users.getEmail().equals(TokenUserUtil.getUserToken())) {
//                    fxmlLoader = new FXMLLoader(getClass().getResource("/gui/views/components/MessageViewSelf.fxml"));
//                } else {
//                    fxmlLoader = new FXMLLoader(getClass().getResource("/gui/views/components/MessageViewOther.fxml"));
//                    user_self = false;
//                }
//
//                messageElement = fxmlLoader.load();
//
//                if (user_self) {
//                    VBox.setMargin(messageElement, new Insets(10, 0, 0, 700));
//                } else {
//                    VBox.setMargin(messageElement, new Insets(10, 0, 0, 100));
//                }
//
//                // Adicionando listener para ajustar o clip após o layout ser definido
//                VBox finalMessageElement = messageElement;
//
//                messageElement.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
//
//                    VBox contentMessage = (VBox) finalMessageElement.getChildren().get(1);
//
//                    //pegando o conteudo textual e adicionando evento
//                    contentMessage.layoutBoundsProperty().addListener((observableInter, oldValueInter, newValueInter) -> {
//                        Rectangle clip = new Rectangle();
//                        clip.setWidth(newValueInter.getWidth());
//                        clip.setHeight(newValueInter.getHeight());
//                        clip.setArcWidth(10);
//                        clip.setArcHeight(10);
//                        contentMessage.setClip(clip);
//                    });
//                });
//
//                MessageControl messageControl = fxmlLoader.getController();
//
//                Image image = ImageUtil.getImageWithEmailUser(users.getEmail());
//
//                String hour = message.getHour().toString();
//                String hourFormated = hour.substring(0, 8);
//
//                // Adicionando o conteúdo do banco na mensagem
//                messageControl.setContent(users.getName(), dateFormater(message.getDate().toString()), hourFormated, message.getContent(), image, idHashUser);
//
//                // Adicionando a mensagem no scrollpane
//                if (messageElement != null) {
//                    contentScrollPane.getChildren().add(messageElement);
//                }
//            } catch (IOException error) {
//                System.out.println("Error in load message: " + error.getMessage());
//            }
//        });
//    }

    private void clearScrollPane() {
        if (contentScrollPane != null) {
            contentScrollPane.getChildren().clear();
        }
    }

    private void checkAndSetChatBar() {
        if (titlePanel != null) {
            try{
                User currentUser = userService.findById(TokenUtil.getCurrentUser());
                System.out.println(currentUser.getCodEspecial());
                //verificação que define se o usuário tem, ou não acesso ao canal
                //usuário acessa se estiver no canal "CHAT" ou, se seu código de usuário especial não for nulo (nesse caso, terá acesso a todos os canais)
                if (titlePanel.getText().equals("CHAT") || currentUser.getCodEspecial() != null) {
                    System.out.println("Entrou no negocio");
                    chatBar.setVisible(true);
                }
                else {
                    chatBar.setVisible(false);
                }
            } catch (Exception error) {
                System.out.println("Erro ao carregar chat bar: " + error.getMessage());
            }
        }
    }

//    private void loadMessagesInChanel(Long id) {
//        Chanel chanel = DataService.findByChanelId(id);
//
//        List<Message> messages = DataService.findMessageById(chanel);
//
//        clearScrollPane();
//        loadInView(messages);
//    }

    private void preparateView(Long chanelId) {
        //define o titulo da página
        setTitleChanel(chanelId);

        //define o canal atual
        TokenUtil.setCurrentChanel(chanelId);

        //limpa a tela
        clearScrollPane();

        //verificar se o canal é o chat, permite acessoa a barra de digitação
        checkAndSetChatBar();

        //carrega as mensagens do canal clicado
//        loadMessagesInChanel(chanelId);
    }

    @FXML
    public void onEnditaisButtonClicked() {
        preparateView(EDITAIS);
    }

    @FXML
    public void onChatButtonClicked() {
        preparateView(CHAT);
    }

    @FXML
    public void onEmployeeButtonClicked() {
        preparateView(VAGAS);
    }

//    @FXML
//    public void onUserSubmitMessage() {
//        String input = contentChatBar.getText();
//
//        if (input.isEmpty()) {
//            Alerts.showAlert("Falha", null, "Erro ao enviar mensagem, campo está vazio", Alert.AlertType.WARNING);
//        }
//        else {
//            //buscando o usuario que enviou
//            Users users = DataService.findByHashEmail(TokenUserUtil.getUserToken());
//            Chanel chanel = DataService.findByChanelId(TokenChanelUtil.getToken());
//
//            if (users != null && chanel != null) {
//                Message message = new Message(null, LocalTime.now(), LocalDate.now(), input, users, chanel);
//
//                DataService.saveItem(message);
//
//                loadMessagesInChanel(TokenChanelUtil.getToken());
//                contentChatBar.setText("");
//
//            }
//            else {
//                Alerts.showAlert("Erro", null, "Erro ao enviar a mensagem, tente novamente", Alert.AlertType.WARNING);
//            }
//        }
//    }

    @FXML
    public void onProfileButtonClicked() {
        if (actualPane != PROFILE) {
            alternateChanelInProfile(PROFILE);
        }
    }

    @FXML
    public void onAnyChanelClicked() {
        if (actualPane != CANAIS) {
            alternateChanelInProfile(CANAIS);
        }
    }

    private double calculateWdtht() {
        return (rootElement.getWidth()) - 200;
    }

    private void alternateChanelInProfile(int idPane) {
        //manipulando tela para transição entre canis e perfil
        if (idPane == CANAIS) {

            rootElement.getChildren().remove(1);

            VBox chanelDefaultView = paneViewSuport;

            rootElement.getChildren().add(chanelDefaultView);

            actualPane = CANAIS;

            menuIsactive = true;

            if (!expandedMenu.isVisible()) {
                toggleMenu();
            }

            onEnditaisButtonClicked();

        } else if (idPane == PROFILE) {
            paneViewSuport = vboxDefault;

            rootElement.getChildren().remove(1);

            HBox profileView = null;

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/views/ProfileView.fxml"));
                profileView = fxmlLoader.load();

            } catch (IOException error) {
                System.out.println("Error ao carregar vbox do profile: " + error.getMessage());
            }

            profileView.setPrefWidth(calculateWdtht());

            Scene scene = AcadConectaJavaFxApplication.getMainStageApplication().getScene();

            scene.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    HBox root = (HBox) rootElement.getChildren().get(1);
                    root.setPrefWidth(calculateWdtht());
                }
            });

            rootElement.getChildren().add(profileView);

            if (expandedMenu.isVisible()) {
                toggleMenu();
            }
            menuIsactive = false;
            actualPane = PROFILE;
        }
    }


//    private void onEnterPressed(KeyEvent keyEvent) {
//        if (keyEvent.getCode() == KeyCode.ENTER) {
//            onUserSubmitMessage();
//        }
//    }

    @FXML
    private void initialize() {
        setTitleChanel(EDITAIS);
        TokenUtil.setCurrentChanel(EDITAIS);

        checkAndSetChatBar();

        //carregar mensagens do banco com base no ID do canal (Editais)
        actualPane = CANAIS;
        toggleMenu();

//        loadMessagesInChanel(EDITAIS);

        //evento que monitora o VBox dentro do scroll pane, serve para deslizar a tela para baixo de form autometica
        contentScrollPane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            scrollPaneMain.setVvalue(1.0);
        });

//        contentChatBar.setOnKeyPressed(event -> onEnterPressed(event));
    }
}
