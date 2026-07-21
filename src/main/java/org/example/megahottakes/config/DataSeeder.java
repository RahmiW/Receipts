package org.example.megahottakes.config;

import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.dto.UserDTO;
import org.example.megahottakes.entities.Sport;
import org.example.megahottakes.repositories.UserRepository;
import org.example.megahottakes.services.CommentService;
import org.example.megahottakes.services.HotTakeService;
import org.example.megahottakes.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {
    private static final String SEED_PASSWORD = "password123";

    private final UserRepository userRepository;
    private final UserService userService;
    private final HotTakeService hotTakeService;
    private final CommentService commentService;

    public DataSeeder(UserRepository userRepository, UserService userService, HotTakeService hotTakeService, CommentService commentService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.hotTakeService = hotTakeService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        UserDTO theRock = userService.createUser("TheRock", "It's about grind", SEED_PASSWORD, Set.of(Sport.NBA, Sport.NFL));
        UserDTO johnCena = userService.createUser("John Cena", "You can't see me", SEED_PASSWORD, Set.of(Sport.MLB));
        UserDTO kevinHart = userService.createUser("Kevin Hart", "Shortest guy in the room", SEED_PASSWORD, Set.of(Sport.NBA));

        HotTakeDTO rockTake = hotTakeService.createHotTake(theRock.getId(), "LeBron is officially better than MJ after last night.", Sport.NBA);
        HotTakeDTO cenaTake = hotTakeService.createHotTake(johnCena.getId(), "The Celtics are winning the next 3 championships. Guaranteed.", Sport.NBA);
        HotTakeDTO hartTake = hotTakeService.createHotTake(kevinHart.getId(), "Shaq would average 50 points in today's NBA.", Sport.NBA);

        commentService.addComment(rockTake.getId(), johnCena.getId(), "You can't even see the truth in that statement!");
        commentService.addComment(hartTake.getId(), kevinHart.getId(), "Shaq, you're just big. I'd cross you up!");
        commentService.addComment(hartTake.getId(), theRock.getId(), "He would not lets be realistic");
    }
}
