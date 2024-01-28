package mk.ukim.finki.culturecompassdians.service.impl;

import feign.Response;
import lombok.NonNull;
import mk.ukim.finki.culturecompassdians.model.Role;
import mk.ukim.finki.culturecompassdians.model.User;
import mk.ukim.finki.culturecompassdians.model.UserDto;
import mk.ukim.finki.culturecompassdians.repository.UserRepository;
import mk.ukim.finki.culturecompassdians.service.UserFeignClient;
import mk.ukim.finki.culturecompassdians.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private UserFeignClient userFeignClient;
    final private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserFeignClient userFeignClient,
                           @NonNull PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userFeignClient = userFeignClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(UserDto userDto) {
        String userInfo = createStringForUser(userDto);
        return userFeignClient.registration(userInfo);
    }

    @Override
    public User findUserByUsername(String username) {
        if (userFeignClient.findByUsername(username).getStatusCode() == HttpStatus.OK &&
        userFeignClient.findByUsername(username).getBody() != null) {
            return userFeignClient.findByUsername(username).getBody();
        } else {
            return null;
        }
    }
    @Override
    public boolean userExists(User user) {
        return user != null && user.getUsername() != null && !user.getUsername().isEmpty();
    }

    @Override
    public boolean isLoggedIn(User user) {
        return user != null;
    }

    @Override
    public boolean isAdmin(User user) {
        return isLoggedIn(user) && user.getRole() == Role.ROLE_ADMIN;
    }

    private String createStringForUser(UserDto userDto) {
        StringBuilder sb = new StringBuilder();

        sb.append(userDto.getFirstName()).
                append(" ").
                append(userDto.getLastName()).
                append(" ").
                append(userDto.getUsername()).
                append(" ").
                append(userDto.getPassword());

        return sb.toString();
    }
}
