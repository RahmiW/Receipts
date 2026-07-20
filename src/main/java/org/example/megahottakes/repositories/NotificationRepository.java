package org.example.megahottakes.repositories;

import org.example.megahottakes.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdOrderByCreatedDateDesc(Long recipientId);
}
