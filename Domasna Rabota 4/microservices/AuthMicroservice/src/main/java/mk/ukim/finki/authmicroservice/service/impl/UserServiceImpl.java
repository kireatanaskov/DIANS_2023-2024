package mk.ukim.finki.authmicroservice.service.impl;

import lombok.NonNull;
import mk.ukim.finki.authmicroservice.model.Role;
import mk.ukim.finki.authmicroservice.model.User;
import mk.ukim.finki.authmicroservice.model.UserDto;
import mk.ukim.finki.authmicroservice.repository.UserRepository;
import mk.ukim.finki.authmicroservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           @NonNull PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(String userInfo) {
        User user = new User();
        String[] allInfoForUser = userInfo.split("\\s+");
        user.setName(allInfoForUser[0] + " " + allInfoForUser[1]);
        user.setUsername(allInfoForUser[2]);
        user.setPassword(passwordEncoder.encode(allInfoForUser[3]));
        user.setRole(Role.valueOf("ROLE_USER"));
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        if (userRepository.findByUsername(username) == null) {
            return Optional.empty();
        }
        return Optional.of(userRepository.findByUsername(username));
    }

    @Override
    public boolean isLoggedIn(User user) {
        return user != null;
    }

    @Override
    public boolean isAdmin(User user) {
        return isLoggedIn(user) && user.getRole() == Role.ROLE_ADMIN;
    }

    @Override
    public boolean userExists(User user) {
        return user != null && user.getUsername() != null && !user.getUsername().isEmpty();
    }

}
