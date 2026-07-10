package org.example.megahottakes.repositories;

import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.Reaction;
import org.example.megahottakes.entities.ReactionType;
import org.example.megahottakes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByUserAndHotTake(User user, HotTake hotTake);
    int countByHotTakeAndType(HotTake hotTake, ReactionType type);
}
