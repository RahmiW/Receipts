package org.example.megahottakes.repositories;

import java.util.List;

import org.example.megahottakes.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdOrderByCreatedDateDesc(Long recipientId);
    // Unread Notification count
    int countByRecipientIdAndReadFalse(Long recipientId);
}
