package br.com.projectpicpay.controllers;

import br.com.projectpicpay.dtos.UserDTO;
import br.com.projectpicpay.model.entities.user.User;
import br.com.projectpicpay.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> putUser(@PathVariable(name = "id") Long id,
                                        @RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.actualizarUser(id, user), HttpStatus.OK);
    }
}
