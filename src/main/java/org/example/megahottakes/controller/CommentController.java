package org.example.megahottakes.controller;


import org.example.megahottakes.dto.CommentDTO;
import org.example.megahottakes.entities.Comment;
import org.example.megahottakes.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Create
    @PostMapping("/hottake/{hotTakeId}")
    public CommentDTO createComment(@PathVariable Long hotTakeId, @RequestAttribute Long authUserId, @RequestBody Comment comment) {
        return commentService.addComment(hotTakeId, authUserId, comment.getContent());
    }

    // Read
    @GetMapping("/{id}/all")
    public List<CommentDTO> getAllComments(@PathVariable Long id) {
        return commentService.getCommentsByHotTake(id);
    }
    // Delete
    @DeleteMapping("/{id}")
    public void deleteCommentById(@PathVariable Long id){
        commentService.deleteComment(id);
    }
}
