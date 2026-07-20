package org.example.megahottakes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class HotTake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Sport tag;

    private LocalDateTime creationDate =  LocalDateTime.now();

    private LocalDateTime lastResurfacedDate;

    @Column(columnDefinition = "TEXT")
    private String resurfaceContext;

    private int resurfaceCount = 0;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnore
    private User author;

    @ManyToOne
    @JoinColumn(name = "pulled_by_id")
    @JsonIgnore
    private User pulledBy;

    @OneToMany(mappedBy = "hotTake", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}
