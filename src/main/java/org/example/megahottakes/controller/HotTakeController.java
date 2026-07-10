package org.example.megahottakes.controller;


import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.entities.HotTake;
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
    @GetMapping("/{id}/heatscore")
    public Integer getHeatScore(@PathVariable Long id){
        return hotTakeService.getHeatScore(id);
    }
    // Update
    @PutMapping("/{id}")
    public HotTakeDTO updateHotTake(@PathVariable Long id, @RequestBody HotTake hotTake) {
        return hotTakeService.updateHotTake(id, hotTake.getContent(), hotTake.getTag());
    }
    @PatchMapping("/{hotTakeId}/like/{userId}")
    public int likeHotTake(@PathVariable Long hotTakeId, @PathVariable Long userId) {
        return hotTakeService.addToHeatScore(userId, hotTakeId);
    }
    // Delete
    @DeleteMapping("/{id}")
    public void deleteHotTake(@PathVariable Long id){
        hotTakeService.deleteHotTake(id);
    }
    @DeleteMapping("/{hotTakeId}/like/{userId}")
    public int unlikeHotTake(@PathVariable Long hotTakeId, @PathVariable Long userId) {
        return hotTakeService.decreaseHeatScore(userId, hotTakeId);
    }
}
