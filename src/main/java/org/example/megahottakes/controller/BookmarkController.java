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
    @PostMapping("/{hotTakeId}")
    public void addBookmark(@RequestAttribute Long authUserId, @PathVariable Long hotTakeId) {
        bookmarkService.addBookmark(authUserId, hotTakeId);
    }
    // Read
    @GetMapping
    public List<HotTakeDTO> getBookmarksByUser(@RequestAttribute Long authUserId) {
        return bookmarkService.getBookmarksByUser(authUserId);
    }
    // Delete
    @DeleteMapping("/{hotTakeId}")
    public void removeBookmark(@RequestAttribute Long authUserId, @PathVariable Long hotTakeId) {
        bookmarkService.removeBookmark(authUserId, hotTakeId);
    }
}
