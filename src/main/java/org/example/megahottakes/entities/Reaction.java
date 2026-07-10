package org.example.megahottakes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reaction", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "hottake_id"}))
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "hottake_id", nullable = false)
    @JsonIgnore
    private HotTake hotTake;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType type;
}
