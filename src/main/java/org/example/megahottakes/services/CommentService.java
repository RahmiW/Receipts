package org.example.megahottakes.services;

import jakarta.transaction.Transactional;
import org.example.megahottakes.dto.CommentDTO;
import org.example.megahottakes.entities.Comment;
import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.User;
import org.example.megahottakes.repositories.CommentRepository;
import org.example.megahottakes.repositories.HotTakeRepository;
import org.example.megahottakes.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final HotTakeRepository hotTakeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    public CommentService(HotTakeRepository hotTakeRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.hotTakeRepository = hotTakeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }
    public CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedDate(comment.getCreatedDate());
        commentDTO.setAuthorName(comment.getAuthor().getUserName());
        commentDTO.setAuthorId(comment.getAuthor().getId());
        commentDTO.setHotTakeId(comment.getHotTake().getId());
        return commentDTO;
    }
    @Transactional
    public CommentDTO addComment(Long hotTakeId, Long authorId, String contentOfComment) {
        if (contentOfComment == null || contentOfComment.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        Comment newComment = new Comment();
        HotTake hotTakeObject = hotTakeRepository.findById(hotTakeId)
                .orElseThrow(() -> new IllegalArgumentException("The HotTake Post was not Found"));
        User authorObject = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("The User was not Found"));
        newComment.setAuthor(authorObject);
        newComment.setHotTake(hotTakeObject);
        newComment.setContent(contentOfComment);
        return convertToDTO(commentRepository.save(newComment));
    }
    public List<CommentDTO> getCommentsByHotTake(Long hotTakeId){
        HotTake hotTake = hotTakeRepository.findById(hotTakeId).orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        return hotTake.getComments().stream()
                .map(this::convertToDTO)
                .toList();
    }
    @Transactional
    public void deleteComment(Long commentId){
        if (!commentRepository.existsById(commentId)){
            throw new IllegalArgumentException("The Comment was not Found");
        }
        commentRepository.deleteById(commentId);
    }
}
