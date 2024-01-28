package mk.ukim.finki.culturecompassdians.service;

import mk.ukim.finki.culturecompassdians.model.Node;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import mk.ukim.finki.culturecompassdians.model.User;
import mk.ukim.finki.culturecompassdians.model.UserDto;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "user-service",
        url = "http://localhost:9002/user/register"
)
public interface UserFeignClient {

    @GetMapping(value = "/findByUsername/{username}", consumes = "application/json")
    ResponseEntity<User> findByUsername(@PathVariable String username);

    @PostMapping(value = "/save/{userInfo}", consumes = "application/json")
    User registration(@PathVariable String userInfo);
}
