package org.example.megahottakes.services;

import jakarta.transaction.Transactional;
import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.repositories.HotTakeRepository;
import org.example.megahottakes.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

// Pull eligibility, resurfacing, and crediting. Truth-check via AI/web search comes later.
@Service
public class ReceiptService {
    private static final int PULL_RECEIPT_MIN_HOURS = 2;

    private final HotTakeRepository hotTakeRepository;
    private final UserRepository userRepository;
    private final HotTakeService hotTakeService;
    private final NotificationService notificationService;

    public ReceiptService(HotTakeRepository hotTakeRepository, UserRepository userRepository, HotTakeService hotTakeService, NotificationService notificationService) {
        this.hotTakeRepository = hotTakeRepository;
        this.userRepository = userRepository;
        this.hotTakeService = hotTakeService;
        this.notificationService = notificationService;
    }

    @Transactional
    public HotTakeDTO pullReceipt(Long userId, Long hotTakeId) {
        User puller = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("The User was not found"));
        HotTake hotTake = hotTakeRepository.findById(hotTakeId)
                .orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        if (Duration.between(hotTake.getCreationDate(), LocalDateTime.now()).toHours() < PULL_RECEIPT_MIN_HOURS) {
            throw new IllegalArgumentException("This take must be at least " + PULL_RECEIPT_MIN_HOURS + " hours old before receipts can be pulled");
        }

        hotTake.setPulledBy(puller);
        hotTake.setLastResurfacedDate(LocalDateTime.now());
        hotTake.setResurfaceCount(hotTake.getResurfaceCount() + 1);
        HotTake resurfaced = hotTakeRepository.save(hotTake);

        notificationService.notifyBookmarkersOfResurface(resurfaced);

        return hotTakeService.convertDTO(resurfaced);
    }
}
