package RESTApiExample.controller;

import RESTApiExample.Service.UserService;
import RESTApiExample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.lisAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public void addNewUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") Integer id) {
        try {
            User existUser = userService.getUser(id);
            if (user.getFirstName() != null) {
                existUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                existUser.setLastName(user.getLastName());
            }
            userService.saveUser(user);
            return new ResponseEntity<User>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable("id") Integer id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<User>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
