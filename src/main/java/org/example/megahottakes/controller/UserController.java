package org.example.megahottakes.controller;

import org.example.megahottakes.dto.CommentDTO;
import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.dto.UserDTO;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // Create
    @PostMapping
    public UserDTO createUser(@RequestBody User user) {
        return userService.createUser(user.getUserName(), user.getBio(),user.getPassword());
    }
    @PostMapping("/login")
    public UserDTO login(@RequestBody User user) {
        return userService.login(user.getUserName(), user.getPassword());
    }
    // Read
    @GetMapping
    public List<UserDTO> findAll() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @GetMapping("/{id}/bio")
    public String getUserBioById(@PathVariable Long id) {
        return userService.getBio(id);
    }
    @GetMapping("/by-name/{name}")
    public UserDTO getByUserName(@PathVariable String name){return userService.getUserByName(name);
    }
    @GetMapping("/{id}/hottakes")
    public List<HotTakeDTO> getHotTakesByUser(@PathVariable Long id){
        return userService.getHotTakesByUserId(id);
    }
    @GetMapping("/{id}/comments")
    public List<CommentDTO>  getCommentsByUser(@PathVariable Long id){
        return userService.getCommentsByUserId(id);
    }
    // Update Section will include: changeName, changeBio
    @PutMapping("/{id}/username")
    public UserDTO updateUserName(@PathVariable Long id, @RequestBody User user){
        return userService.changeName(id, user.getUserName());
    }
    @PutMapping("/{id}/bio")
    public UserDTO updateUserBio(@PathVariable Long id, @RequestBody User user){
        return userService.changeBio(id, user.getBio());
    }
    // Delete Section
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
