package mk.ukim.finki.culturecompassdians.web.controller;

import mk.ukim.finki.culturecompassdians.model.Role;
import mk.ukim.finki.culturecompassdians.model.exception.InvalidArgumentsException;
import mk.ukim.finki.culturecompassdians.model.exception.PasswordsDoNotMatchException;
import mk.ukim.finki.culturecompassdians.model.exception.UserAlreadyExistsException;
import mk.ukim.finki.culturecompassdians.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;


    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname) {
        try{
            Role role1 = Role.valueOf("ROLE_USER");
            this.userService.register(username, password, repeatedPassword, name, surname, role1);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException | UserAlreadyExistsException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }


}
