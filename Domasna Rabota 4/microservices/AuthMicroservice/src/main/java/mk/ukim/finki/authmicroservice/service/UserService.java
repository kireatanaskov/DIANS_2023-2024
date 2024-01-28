package mk.ukim.finki.authmicroservice.service;

import mk.ukim.finki.authmicroservice.model.User;
import mk.ukim.finki.authmicroservice.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(String userInfo);

    Optional<User> findUserByUsername(String username);

    //    List<UserDto> findAllUsers();
    boolean isLoggedIn(User user);

    boolean isAdmin(User user);
    boolean userExists(User user);
}
