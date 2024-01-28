package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.User;
import mk.ukim.finki.culturecompassdians.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(UserDto userDto);

    User findUserByUsername(String username);

//    List<UserDto> findAllUsers();
    boolean isLoggedIn(User user);
    boolean isAdmin(User user);
    boolean userExists(User user);
}
