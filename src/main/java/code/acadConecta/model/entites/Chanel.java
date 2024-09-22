package code.acadConecta.model.entites;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Chanel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

//    @OneToMany(mappedBy = "id_chanel")
//    private Set<Message> messageSet = new LinkedHashSet<>();

    //relacionamento com os usuários presentes no respectivo canal
    @ManyToMany(mappedBy = "chanelList")
    private Set<User> usersSet = new LinkedHashSet<>();

    public Chanel() {}

    public Chanel(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Chanel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Set<Message> getMessageSet() {
//        return messageSet;
//    }
//
//    public void setMessageSet(Set<Message> messageSet) {
//        this.messageSet = messageSet;
//    }

    public Set<User> getUserSet() {
        return usersSet;
    }

    public void setUsersSet(Set<User> usersSet) {
        this.usersSet = usersSet;
    }

    public void setOneUser(User user) {
        this.usersSet.add(user);
    }
}
