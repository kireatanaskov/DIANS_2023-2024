package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.User;

import java.util.List;

public interface AuthService {
    User login(String username, String password);
    List<User> findAll();
}
