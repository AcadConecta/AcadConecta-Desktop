package code.acadConecta.services;

import code.acadConecta.model.entites.Chanel;
import code.acadConecta.model.entites.Message;
import code.acadConecta.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void save(Message message) {
        messageRepository.save(message);
    }

    //carrega todas as mesagens que possuem o id x, do canal
    public List<Message> getMessagesByIdChanel(Chanel id) {

        try {
            List<Message> messages = messageRepository.findByIdChanel(id);

            if (messages == null) {
                throw new NullPointerException();
            }

            return messages;

        } catch (NullPointerException error) {
            System.out.println("Error in load message by id chanel: " + error.getMessage());
        }

        return null;
    }
}
