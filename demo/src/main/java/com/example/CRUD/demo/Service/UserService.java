package com.example.CRUD.demo.Service;

import com.example.CRUD.demo.Entity.User;
import com.example.CRUD.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User createUser(User user){
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User updateUser(Long id,User userDetailes){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User existingUser = user.get();
            existingUser.setName(userDetailes.getName());
            existingUser.setEmail(userDetailes.getEmail());
            existingUser.setPhone(userDetailes.getPhone());
            return userRepository.save(existingUser);
        }
        return null;
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
}
