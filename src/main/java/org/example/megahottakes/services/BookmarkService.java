package org.example.megahottakes.services;

import jakarta.transaction.Transactional;
import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.entities.Bookmark;
import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.repositories.BookmarkRepository;
import org.example.megahottakes.repositories.HotTakeRepository;
import org.example.megahottakes.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Handles bookmarking hot takes so users can be notified later if a bookmarked take resurfaces
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final HotTakeRepository hotTakeRepository;
    private final HotTakeService hotTakeService;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository, HotTakeRepository hotTakeRepository, HotTakeService hotTakeService) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.hotTakeRepository = hotTakeRepository;
        this.hotTakeService = hotTakeService;
    }

    @Transactional
    public void addBookmark(Long userId, Long hotTakeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("The User was not found"));
        HotTake hotTake = hotTakeRepository.findById(hotTakeId)
                .orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        if (bookmarkRepository.findByUserIdAndHotTakeId(userId, hotTakeId).isPresent()) {
            throw new IllegalArgumentException("This take is already bookmarked");
        }
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setHotTake(hotTake);
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removeBookmark(Long userId, Long hotTakeId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndHotTakeId(userId, hotTakeId)
                .orElseThrow(() -> new IllegalArgumentException("This take is not bookmarked"));
        bookmarkRepository.delete(bookmark);
    }

    public List<HotTakeDTO> getBookmarksByUser(Long userId) {
        return bookmarkRepository.findByUserId(userId)
                .stream()
                .map(bookmark -> hotTakeService.convertDTO(bookmark.getHotTake()))
                .toList();
    }
}
