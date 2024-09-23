package code.acadConecta.mail;

import code.acadConecta.gui.util.Notification;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {

    public static boolean sendEmail(String subject, String body) {
        Dotenv dotenv = Dotenv.load();

        //definindo as propriedades so SMTP
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        //efetivando autenticação
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(dotenv.get("EMAIL_USER"), dotenv.get("EMAIL_PASSWORD"));
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(dotenv.get("EMAIL_USER")));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dotenv.get("EMAIL_ACAD")));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            return true;
        } catch (MessagingException error) {
            throw new RuntimeException("Failed in send email: " + error.getMessage());
        }
    }
}
