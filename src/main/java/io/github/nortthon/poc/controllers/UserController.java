package io.github.nortthon.poc.controllers;

import io.github.nortthon.poc.domains.User;
import io.github.nortthon.poc.gateways.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserGateway userGateway;

    @PostMapping
    @ResponseStatus(CREATED)
    public User save(@RequestBody final User user) {
        return userGateway.save(user);
    }

    @GetMapping
    public List<User> findAll() {
        return userGateway.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") final String id) {
        return userGateway.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, null));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("id") final String id) {
        userGateway.delete(id);
    }
}
