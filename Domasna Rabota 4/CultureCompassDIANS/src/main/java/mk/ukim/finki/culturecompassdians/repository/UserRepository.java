package mk.ukim.finki.culturecompassdians.repository;

import mk.ukim.finki.culturecompassdians.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}

