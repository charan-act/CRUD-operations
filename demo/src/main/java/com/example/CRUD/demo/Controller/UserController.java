package com.example.CRUD.demo.Controller;

import com.example.CRUD.demo.Entity.User;
import com.example.CRUD.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
   public ResponseEntity<User> CreateUser(@RequestBody User user){
        User createUser=userService.createUser(user);
        return new ResponseEntity<>( createUser, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user=userService.getUserById(id);
        return user.map(u->new ResponseEntity<>(u, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetailes){
        User updateUser=userService.updateUser(id, userDetailes);
        if(updateUser!=null){
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
    // DELETE - DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // DELETE - DELETE /api/users
    @DeleteMapping
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
