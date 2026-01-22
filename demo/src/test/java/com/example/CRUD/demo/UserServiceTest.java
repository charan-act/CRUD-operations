package com.example.CRUD.demo;

import com.example.CRUD.demo.Entity.User;
import com.example.CRUD.demo.Repository.UserRepository;
import com.example.CRUD.demo.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private User user;

    private User existingUser;
    private User updatedDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("charan");
        user.setEmail("charan@gmail.com");

        existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@gmail.com");
        existingUser.setPhone("1111111111");

        updatedDetails = new User();
        updatedDetails.setName("New Name");
        updatedDetails.setEmail("new@gmail.com");
        updatedDetails.setPhone("9999999999");

    }

    @Test
    @DisplayName("Should create user successfully")
    void testCreateUser_Success() {
        //Arrange
        when(userRepository.save(user)).thenReturn(user);

        //ACT
        User result = userService.createUser(user);

        //Assert
        assertNotNull(result);
        assertEquals("charan", result.getName());
        assertEquals("charan@gmail.com", result.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should get  all user")
    void testGetAllUsers_Success() {


        List<User> users = Arrays.asList(
                user,
                new User(2L, "Tej", "tej@gmail.com", "843343")
        );
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(userRepository, times(1)).findAll();

    }

    // -------------------- SUCCESS CASE --------------------

    @Test
    @DisplayName("Should update user when user exists")
    void testUpdateUser_Success() {
        // Arrange
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));

        when(userRepository.save(any(User.class)))
                .thenReturn(existingUser);

        // Act
        User result = userService.updateUser(1L, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("new@gmail.com", result.getEmail());
        assertEquals("9999999999", result.getPhone());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    // -------------------- USER NOT FOUND CASE --------------------

    @Test
    @DisplayName("Should return null when user not found")
    void testUpdateUser_UserNotFound() {
        // Arrange
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act
        User result = userService.updateUser(1L, updatedDetails);

        // Assert
        assertNull(result);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("charan@gmail.com", result.get().getEmail());
    }
    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}


