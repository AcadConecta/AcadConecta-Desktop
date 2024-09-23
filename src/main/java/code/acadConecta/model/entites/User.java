package code.acadConecta.model.entites;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Lob
    @Column(nullable = true, columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @Column(nullable = true)
    private Long codEspecial;

    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role_id")
    private Set<String> roles = new HashSet<>();

    @OneToMany(mappedBy = "id_user")
    private Set<Message> messageSet = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_in_chanel",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chanel_id")
    )
    private List<Chanel> chanelList = new ArrayList<>();

    public User() {}

    public User(String email, String name, String password, byte[] image, Long codEspecial) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
        this.codEspecial = codEspecial;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", codEspecial=" + codEspecial +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getCodEspecial() {
        return codEspecial;
    }

    public Set<Message> getMessageSet() {
        return messageSet;
    }

    public void setMessageSet(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    public List<Chanel> getChanelList() {
        return chanelList;
    }

    public void setChanelList(List<Chanel> chanelList) {
        this.chanelList = chanelList;
    }
}
