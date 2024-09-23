package code.acadConecta.model.entites;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime hour;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String content;

    //usuário que enviou a mensagem
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "email")
    private User id_user;

    //relacionamento com o canal que a mensagem pertence
    @ManyToOne
    @JoinColumn(name = "id_chanel", referencedColumnName = "id")
    private Chanel id_chanel;

    public Message() {
    }

    public Message(Long id, LocalTime hour, LocalDate date, String content, User id_user, Chanel id_chanel) {
        this.id = id;
        this.hour = hour;
        this.date = date;
        this.content = content;
        this.id_user = id_user;
        this.id_chanel = id_chanel;
    }

    @Override
    public String toString() {
        return "Message{" +
                "hour=" + hour +
                ", date=" + date +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_users) {
        this.id_user = id_users;
    }
}

