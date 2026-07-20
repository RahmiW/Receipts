package org.example.megahottakes.services;

import jakarta.transaction.Transactional;
import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.Reaction;
import org.example.megahottakes.entities.ReactionType;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.repositories.HotTakeRepository;
import org.example.megahottakes.repositories.ReactionRepository;
import org.example.megahottakes.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HotTakeService {
    private static final int MAX_CONTENT_LENGTH = 280;

    private final HotTakeRepository hotTakeRepository;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;

    public HotTakeService(HotTakeRepository hotTakeRepository, UserRepository userRepository, ReactionRepository reactionRepository) {
        this.hotTakeRepository = hotTakeRepository;
        this.userRepository = userRepository;
        this.reactionRepository = reactionRepository;
    }

    public HotTakeDTO convertDTO(HotTake hotTake){
        HotTakeDTO hotTakeDTO = new HotTakeDTO();
        hotTakeDTO.setId(hotTake.getId());
        hotTakeDTO.setAuthorName(hotTake.getAuthor().getUserName());
        hotTakeDTO.setContent(hotTake.getContent());
        hotTakeDTO.setTag(hotTake.getTag());
        hotTakeDTO.setHeatCount(reactionRepository.countByHotTakeAndType(hotTake, ReactionType.HEAT));
        hotTakeDTO.setColdCount(reactionRepository.countByHotTakeAndType(hotTake, ReactionType.COLD));
        hotTakeDTO.setAuthorId(hotTake.getAuthor().getId());
        hotTakeDTO.setCreationDate(hotTake.getCreationDate());
        hotTakeDTO.setPulledByUsername(hotTake.getPulledBy() != null ? hotTake.getPulledBy().getUserName() : null);
        hotTakeDTO.setLastResurfacedDate(hotTake.getLastResurfacedDate());
        return hotTakeDTO;
    }
    // Create
    @Transactional
    public HotTakeDTO createHotTake(Long id, String contentOfHotTake, String tag) {
        User authorOfHotTake = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The User was not Found")); // grab id of User
        if (contentOfHotTake == null || contentOfHotTake.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        if (contentOfHotTake.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("Content cannot exceed " + MAX_CONTENT_LENGTH + " characters");
        }
        HotTake hotTake = new HotTake();
        hotTake.setContent(contentOfHotTake);
        hotTake.setTag(tag);
        hotTake.setAuthor(authorOfHotTake);
        return convertDTO(hotTakeRepository.save(hotTake));
    }

    // Read
    public HotTakeDTO getHotTake(Long hotTakeId) {
        HotTake hotTake = hotTakeRepository.findById(hotTakeId)
                .orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        return convertDTO(hotTake);
    }

    // Randomized feed so low-engagement takes get the same shot at being seen as popular ones
    public List<HotTakeDTO> getHotTakeFeed() {
        List<HotTakeDTO> feed = new ArrayList<>(hotTakeRepository.findAll().stream()
                .map(this::convertDTO)
                .toList());
        Collections.shuffle(feed);
        return feed;
    }

    public List<HotTakeDTO> getHotTakesByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("The User was not Found"));
        return user.getHotTakes().stream()
                .map(this::convertDTO)
                .toList();
    }

    public List<HotTakeDTO> searchHotTakes(String keyword) {
        List<HotTake> hotTake = hotTakeRepository.findByContentContainingIgnoreCase(keyword);
        return hotTake.stream()
                .map(this::convertDTO)
                .toList();
    }

    // Update
    @Transactional
    public HotTakeDTO updateHotTake(Long hotTakeId, String newContent, String tag) {
        HotTake hotTake = hotTakeRepository.findById(hotTakeId).orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        if (newContent.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("Content cannot exceed " + MAX_CONTENT_LENGTH + " characters");
        }
        hotTake.setContent(newContent);
        hotTake.setTag(tag);
        return convertDTO(hotTakeRepository.save(hotTake));
    }

    // Delete
    @Transactional
    public void deleteHotTake(Long hotTakeId) {
        hotTakeRepository.deleteById(hotTakeId);
    }
    // React (heat/cold): tapping the same reaction again untoggles it, tapping the other switches it
    @Transactional
    public HotTakeDTO react(Long userId, Long hotTakeId, ReactionType type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("The User was not Found"));
        HotTake hotTake = hotTakeRepository.findById(hotTakeId).orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        reactionRepository.findByUserAndHotTake(user, hotTake).ifPresentOrElse(existing -> {
            if (existing.getType() == type) {
                reactionRepository.delete(existing);
            } else {
                existing.setType(type);
                reactionRepository.save(existing);
            }
        }, () -> {
            Reaction reaction = new Reaction();
            reaction.setUser(user);
            reaction.setHotTake(hotTake);
            reaction.setType(type);
            reactionRepository.save(reaction);
        });
        return convertDTO(hotTake);
    }
}
