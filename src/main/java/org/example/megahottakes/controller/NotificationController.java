package org.example.megahottakes.controller;

import org.example.megahottakes.dto.NotificationDTO;
import org.example.megahottakes.services.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationDTO> getNotifications(@RequestAttribute Long authUserId) {
        return notificationService.getNotificationsForUser(authUserId);
    }

    @PatchMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}
