package org.example.megahottakes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Table(name = "app_user")
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String userName;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    private String bio;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Set<HotTake> hotTakes = new HashSet<>();
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "user_liked_hottakes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hottake_id")
    )
    private Set<HotTake> likedHotTakes = new HashSet<>();
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Set<Follow> followingRelations = new HashSet<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Set<Follow> followerRelations = new HashSet<>();
}
