package org.example.megahottakes.services;

import jakarta.transaction.Transactional;
import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.repositories.HotTakeRepository;
import org.example.megahottakes.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HotTakeService {
    private final HotTakeRepository hotTakeRepository;
    private final UserRepository userRepository;

    public HotTakeService(HotTakeRepository hotTakeRepository, UserRepository userRepository) {
        this.hotTakeRepository = hotTakeRepository;
        this.userRepository = userRepository;
    }
    public HotTakeDTO convertDTO(HotTake hotTake){
        HotTakeDTO hotTakeDTO = new HotTakeDTO();
        hotTakeDTO.setId(hotTake.getId());
        hotTakeDTO.setAuthorName(hotTake.getAuthor().getUserName());
        hotTakeDTO.setContent(hotTake.getContent());
        hotTakeDTO.setHeatScore(hotTake.getLikedByUsers().size());
        hotTakeDTO.setAuthorId(hotTake.getAuthor().getId());
        hotTakeDTO.setCreationDate(hotTake.getCreationDate());
        return hotTakeDTO;
    }
    // Create
    @Transactional
    public HotTakeDTO createHotTake(Long id, String contentOfHotTake) {
        User authorOfHotTake = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The User was not Found")); // grab id of User
        if (contentOfHotTake == null || contentOfHotTake.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        HotTake hotTake = new HotTake();
        hotTake.setContent(contentOfHotTake);
        hotTake.setAuthor(authorOfHotTake);
        return convertDTO(hotTakeRepository.save(hotTake));
    }

    // Read
    public HotTakeDTO getHotTake(Long hotTakeId) {
        HotTake hotTake = hotTakeRepository.findById(hotTakeId)
                .orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        return convertDTO(hotTake);
    }

    public List<HotTakeDTO> getHotTakeFeed() {
        LocalDateTime since = LocalDateTime.now().minusHours(48);
        List<HotTake> hotTakes = hotTakeRepository.findByCreationDateAfterOrderByLikedByUsersDesc(since);
        return hotTakes.stream()
                .map(this::convertDTO)
                .toList();
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
    public HotTakeDTO updateHotTake(Long hotTakeId, String newContent) {
        HotTake hotTake = hotTakeRepository.findById(hotTakeId).orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        hotTake.setContent(newContent);
        return convertDTO(hotTakeRepository.save(hotTake));
    }

    // Delete
    @Transactional
    public void deleteHotTake(Long hotTakeId) {
        hotTakeRepository.deleteById(hotTakeId);
    }
    // Like HotTake logic
    public Integer getHeatScore(Long hotTakeId) {
        HotTake hotTake = hotTakeRepository.findById(hotTakeId).orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        return hotTake.getLikedByUsers().size();
    }
    @Transactional
    public Integer addToHeatScore(Long userId, Long hotTakeId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("The User was not Found"));
        HotTake hotTake = hotTakeRepository.findById(hotTakeId).orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        if (!user.getLikedHotTakes().contains(hotTake)) {
            user.getLikedHotTakes().add(hotTake);
            userRepository.save(user);
        }
        return hotTake.getLikedByUsers().size();
    }
    @Transactional
    public Integer decreaseHeatScore(Long userId, Long hotTakeId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("The User was not Found"));
        HotTake hotTake = hotTakeRepository.findById(hotTakeId).orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        if (user.getLikedHotTakes().contains(hotTake)) {
            user.getLikedHotTakes().remove(hotTake);
            userRepository.save(user);
        }
        return hotTake.getLikedByUsers().size();
    }
}
