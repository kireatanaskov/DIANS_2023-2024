package mk.ukim.finki.culturecompassdians.service.impl;

import mk.ukim.finki.culturecompassdians.model.Role;
import mk.ukim.finki.culturecompassdians.model.User;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidArgumentsException;
import mk.ukim.finki.culturecompassdians.model.exception.PasswordsDoNotMatchException;
import mk.ukim.finki.culturecompassdians.model.exception.UserAlreadyExistsException;
import mk.ukim.finki.culturecompassdians.repository.UserRepository;
import mk.ukim.finki.culturecompassdians.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean credentialsInvalid(String username, String password) {
        return username == null || password == null || username.isEmpty() || password.isEmpty() ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        if (repeatPassword == null || repeatPassword.isEmpty() ||
                name == null || name.isEmpty() ||
                surname == null || surname.isEmpty() ||
                role == null  ){
            throw new InvalidArgumentsException();
        }
        if (userRepository.findByUsername(username).isPresent() || !userRepository.findByUsername(username).isEmpty()){
            throw new UserAlreadyExistsException(username);
        }
        if (credentialsInvalid(username, password)) {
            throw new InvalidArgumentsException();
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        User user = new User(username, passwordEncoder.encode(password), name, surname, role);
        return userRepository.save(user);

    }
}
