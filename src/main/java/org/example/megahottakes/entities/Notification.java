package org.example.megahottakes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    @JsonIgnore
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "hot_take_id", nullable = false)
    @JsonIgnore
    private HotTake hotTake;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    private LocalDateTime createdDate = LocalDateTime.now();

    private boolean read = false;
}
