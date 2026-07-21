package org.example.megahottakes.controller;

import org.example.megahottakes.services.FollowService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }
    // Create
    @PostMapping("/{followedId}")
    public void follow(@RequestAttribute Long authUserId, @PathVariable Long followedId){
        followService.follow(authUserId, followedId);
    }
    @DeleteMapping("/{followedId}")
    public void unfollow(@RequestAttribute Long authUserId, @PathVariable Long followedId){
        followService.unfollow(authUserId, followedId);
    }
    @GetMapping("/isFollowing/{followerId}/{followedId}")
    public boolean isFollowing(@PathVariable Long followerId, @PathVariable Long followedId){
        return followService.isFollowing(followerId, followedId);
    }
    @GetMapping("/followcount/{id}")
    public int getFollowCount(@PathVariable Long id){
        return followService.getFollowCount(id);
    }
    @GetMapping("/followingcount/{id}")
    public int getFollowingCount(@PathVariable Long id){
        return followService.getFollowingCount(id);
    }
}
