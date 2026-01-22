package com.example.CRUD.demo.Repository;

import com.example.CRUD.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
