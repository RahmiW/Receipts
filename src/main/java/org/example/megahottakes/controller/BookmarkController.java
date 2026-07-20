package org.example.megahottakes.controller;

import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.services.BookmarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }
    // Create
    @PostMapping("/{userId}/{hotTakeId}")
    public void addBookmark(@PathVariable Long userId, @PathVariable Long hotTakeId) {
        bookmarkService.addBookmark(userId, hotTakeId);
    }
    // Read
    @GetMapping("/{userId}")
    public List<HotTakeDTO> getBookmarksByUser(@PathVariable Long userId) {
        return bookmarkService.getBookmarksByUser(userId);
    }
    // Delete
    @DeleteMapping("/{userId}/{hotTakeId}")
    public void removeBookmark(@PathVariable Long userId, @PathVariable Long hotTakeId) {
        bookmarkService.removeBookmark(userId, hotTakeId);
    }
}
