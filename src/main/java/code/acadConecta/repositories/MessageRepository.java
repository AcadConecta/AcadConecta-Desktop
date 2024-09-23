package code.acadConecta.repositories;

import code.acadConecta.model.entites.Chanel;
import code.acadConecta.model.entites.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.id_chanel = :id")
    List<Message> findByIdChanel(Chanel id);
}
