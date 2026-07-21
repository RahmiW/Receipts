package org.example.megahottakes.controller;


import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.ReactionType;
import org.example.megahottakes.services.HotTakeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hottakes")
public class HotTakeController {
    private final HotTakeService hotTakeService;

    public HotTakeController(HotTakeService hotTakeService) {
        this.hotTakeService = hotTakeService;
    }

    // Create
    @PostMapping
    public HotTakeDTO createHotTakePost(@RequestAttribute Long authUserId, @RequestBody HotTake hotTake) {
        return hotTakeService.createHotTake(authUserId, hotTake.getContent(), hotTake.getTag());
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
    public List<HotTakeDTO> hotTakeFeed(@RequestAttribute Long authUserId) {
        return hotTakeService.getHotTakeFeed(authUserId);
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
    @PatchMapping("/{hotTakeId}/react/{type}")
    public HotTakeDTO react(@PathVariable Long hotTakeId, @RequestAttribute Long authUserId, @PathVariable ReactionType type) {
        return hotTakeService.react(authUserId, hotTakeId, type);
    }
    // Delete
    @DeleteMapping("/{id}")
    public void deleteHotTake(@PathVariable Long id){
        hotTakeService.deleteHotTake(id);
    }
}
