package org.example.megahottakes.services;

import jakarta.transaction.Transactional;
import org.example.megahottakes.dto.NotificationDTO;
import org.example.megahottakes.entities.Bookmark;
import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.Notification;
import org.example.megahottakes.repositories.BookmarkRepository;
import org.example.megahottakes.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Creates in-app notification records when a bookmarked take resurfaces. Push/email delivery is later.
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final BookmarkRepository bookmarkRepository;

    public NotificationService(NotificationRepository notificationRepository, BookmarkRepository bookmarkRepository) {
        this.notificationRepository = notificationRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    private NotificationDTO convertDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setHotTakeId(notification.getHotTake().getId());
        dto.setHotTakeContent(notification.getHotTake().getContent());
        dto.setMessage(notification.getMessage());
        dto.setCreatedDate(notification.getCreatedDate());
        dto.setRead(notification.isRead());
        return dto;
    }

    @Transactional
    public void notifyBookmarkersOfResurface(HotTake hotTake) {
        List<Bookmark> bookmarks = bookmarkRepository.findByHotTakeId(hotTake.getId());
        for (Bookmark bookmark : bookmarks) {
            Notification notification = new Notification();
            notification.setRecipient(bookmark.getUser());
            notification.setHotTake(hotTake);
            notification.setMessage("A take you bookmarked just had its receipts pulled and resurfaced.");
            notificationRepository.save(notification);
        }
    }

    public List<NotificationDTO> getNotificationsForUser(Long userId) {
        return notificationRepository.findByRecipientIdOrderByCreatedDateDesc(userId)
                .stream()
                .map(this::convertDTO)
                .toList();
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("The Notification was not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
