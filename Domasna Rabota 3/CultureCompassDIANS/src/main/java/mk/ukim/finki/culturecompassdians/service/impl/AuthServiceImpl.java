package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.User;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidArgumentsException;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidUserCredentialsException;
import mk.ukim.finki.culturecompassdians.repository.UserRepository;
import mk.ukim.finki.culturecompassdians.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean credentialsInvalid(String username, String password) {
        return username == null || password == null || username.isEmpty() || password.isEmpty();
    }

    @Override
    public User login(String username, String password) {
        if (credentialsInvalid(username, password)) {
            throw new InvalidArgumentsException();
        }

        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
