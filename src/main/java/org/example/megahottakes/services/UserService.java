package org.example.megahottakes.services;

import jakarta.transaction.Transactional;
import org.example.megahottakes.dto.CommentDTO;
import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.dto.UserDTO;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.repositories.CommentRepository;
import org.example.megahottakes.repositories.HotTakeRepository;
import org.example.megahottakes.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final HotTakeRepository hotTakeRepository;
    private final CommentRepository commentRepository;
    private final HotTakeService hotTakeService;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,  HotTakeRepository hotTakeRepository,  CommentRepository commentRepository, HotTakeService hotTakeService,  CommentService commentService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.hotTakeRepository = hotTakeRepository;
        this.commentRepository = commentRepository;
        this.hotTakeService = hotTakeService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
    }
    // Convert to DTO
    private UserDTO convertDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setBio(user.getBio());
        return userDTO;
    }
    // Create Section
    @Transactional
    public UserDTO createUser(String name, String bioContent, String password){
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Username cannot be empty");
        User user = new User();
        user.setUserName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setBio(bioContent);
        return convertDTO(userRepository.save(user));
    }
    // Read Section
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertDTO)
                .toList();
    }
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("The User was not found"));
        return convertDTO(user);
    }
    public String getBio(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("The User was not found"));
        return user.getBio();
    }
    public UserDTO getUserByName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("The User was not found"));
        return convertDTO(user);
    }
    public List<HotTakeDTO> getHotTakesByUserId(Long userId) {
        return hotTakeRepository.findByAuthorId(userId)
                .stream()
                .map(hotTakeService::convertDTO)
                .toList();
    }
    public List<CommentDTO> getCommentsByUserId(Long userId) {
        return commentRepository.findByAuthorId(userId)
                .stream()
                .map(commentService::convertToDTO)
                .toList();
    }
    // Update
    @Transactional
    public UserDTO changeName(Long userId, String newName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("The User was not found"));
        user.setUserName(newName);
        return convertDTO(userRepository.save(user));
    }
    @Transactional
    public UserDTO changeBio(Long userId, String newBio) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("The User was not found"));
        user.setBio(newBio);
        return convertDTO(userRepository.save(user));
    }
    // Delete
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("The User was not found");
        }
        userRepository.deleteById(userId);
    }
}
