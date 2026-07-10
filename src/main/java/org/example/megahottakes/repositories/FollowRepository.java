package org.example.megahottakes.repositories;

import org.example.megahottakes.entities.Follow;
import org.example.megahottakes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowed(User follower, User followed);
    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);
    int countByFollower(User follower);
    int countByFollowed(User followed);
}
