package code.acadConecta.repositories;

import code.acadConecta.model.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> { }
