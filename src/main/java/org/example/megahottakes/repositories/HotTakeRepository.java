package org.example.megahottakes.repositories;

import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.Verdict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HotTakeRepository extends JpaRepository<HotTake, Long> {
    List<HotTake> findByCreationDateAfter(LocalDateTime since);
    List<HotTake> findByContentContainingIgnoreCase(String keyword);
    List<HotTake> findByAuthorId(Long userId);
    int countByAuthorIdAndVerdict(Long authorId, Verdict verdict);
}
