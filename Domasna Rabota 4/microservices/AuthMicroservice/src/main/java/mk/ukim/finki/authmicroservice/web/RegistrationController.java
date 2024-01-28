package mk.ukim.finki.authmicroservice.web;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.authmicroservice.model.User;
import mk.ukim.finki.authmicroservice.model.UserDto;
import mk.ukim.finki.authmicroservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/user/register")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/findByUsername/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        if (userService.findUserByUsername(username).isEmpty()) {
            return ResponseEntity.ok().body(null);
        } else {
            return new ResponseEntity<>(userService.findUserByUsername(username).get(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/save/{userInfo}")
    public User registration(@PathVariable String userInfo){
        return userService.saveUser(userInfo);
    }
}
