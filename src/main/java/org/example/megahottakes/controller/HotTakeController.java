package org.example.megahottakes.controller;


import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.ReactionType;
import org.example.megahottakes.entities.Verdict;
import org.example.megahottakes.services.CardService;
import org.example.megahottakes.services.HotTakeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hottakes")
public class HotTakeController {
    private final HotTakeService hotTakeService;
    private final CardService cardService;

    public HotTakeController(HotTakeService hotTakeService, CardService cardService) {
        this.hotTakeService = hotTakeService;
        this.cardService = cardService;
    }

    // Create
    @PostMapping("/user/{id}")
    public HotTakeDTO createHotTakePost(@PathVariable Long id, @RequestBody HotTake hotTake) {
        return hotTakeService.createHotTake(id, hotTake.getContent(), hotTake.getTag());
    }
    // Read
    @GetMapping("/{id}")
    public HotTakeDTO getHotTake(@PathVariable Long id){
        return hotTakeService.getHotTake(id);
    }
    @GetMapping("/{id}/hottakes")
    public List<HotTakeDTO> getHotTakeById(@PathVariable Long id){
        return hotTakeService.getHotTakesByUser(id);
    }
    @GetMapping("/feed")
    public List<HotTakeDTO> hotTakeFeed() {
        return hotTakeService.getHotTakeFeed();
    }
    @GetMapping("/search")
    public List<HotTakeDTO> searchHotTakeFeed(@RequestParam String keyword) {
        return hotTakeService.searchHotTakes(keyword);
    }
    // Update
    @PutMapping("/{id}")
    public HotTakeDTO updateHotTake(@PathVariable Long id, @RequestBody HotTake hotTake) {
        return hotTakeService.updateHotTake(id, hotTake.getContent(), hotTake.getTag());
    }
    @PatchMapping("/{hotTakeId}/react/{userId}/{type}")
    public HotTakeDTO react(@PathVariable Long hotTakeId, @PathVariable Long userId, @PathVariable ReactionType type) {
        return hotTakeService.react(userId, hotTakeId, type);
    }
    @PatchMapping("/{id}/verdict/{verdict}")
    public HotTakeDTO setVerdict(@PathVariable Long id, @PathVariable Verdict verdict) {
        return hotTakeService.setVerdict(id, verdict);
    }
    @GetMapping(value = "/{id}/card", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCard(@PathVariable Long id) {
        return cardService.generateCard(id);
    }
    // Delete
    @DeleteMapping("/{id}")
    public void deleteHotTake(@PathVariable Long id){
        hotTakeService.deleteHotTake(id);
    }
}
