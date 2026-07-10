package org.example.megahottakes.services;

import jakarta.transaction.Transactional;
import org.example.megahottakes.entities.Follow;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.repositories.FollowRepository;
import org.example.megahottakes.repositories.UserRepository;
import org.springframework.stereotype.Service;

// Handles follow/unfollow relationships between users
@Service
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void follow(Long followerId, Long followedId){
        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }
        User follower = userRepository.findById(followerId)
            .orElseThrow(() -> new IllegalArgumentException("Follower was not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("Followed user was not found"));
        if (followRepository.existsByFollowerAndFollowed(follower, followed)) {
            throw new IllegalArgumentException("You are already following the user");
        }
        Follow newFollow = new Follow();
        newFollow.setFollower(follower);
        newFollow.setFollowed(followed);
        followRepository.save(newFollow);
    }
    @Transactional
    public void unfollow(Long followerId, Long followedId){
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower was not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("Followed user was not found"));
        Follow followRelationship = followRepository.findByFollowerAndFollowed(follower, followed)
                .orElseThrow(() -> new IllegalArgumentException("This relationship was not found"));
        followRepository.delete(followRelationship);
    }
    public boolean isFollowing(Long followerId, Long followedId){
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower was not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("Followed user was not found"));
        return  followRepository.existsByFollowerAndFollowed(follower, followed);
    }
    public int getFollowCount(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User was not found"));
        return  followRepository.countByFollowed(user);
    }
    public int getFollowingCount(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User was not found"));
        return  followRepository.countByFollower(user);
    }
}
