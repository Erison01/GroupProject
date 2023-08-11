package com.example.groupproject.services;
import com.example.groupproject.models.Comment;
import com.example.groupproject.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    public List<Comment> allComments(){
        return commentRepo.findAll();
    }

    public List<Comment> bookComments(Long bookId){
        return commentRepo.findByBookIdIs(bookId);
    }

    public CommentRepository getCommentRepo() {
        return commentRepo;
    }

    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void setCommentRepo(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment addComment(Comment comment) {
        return commentRepo.save(comment);
    }
    public Comment findCommentById(Long id) {
        return commentRepo.findById(id).orElse(null);
    }

    public void deleteComment(Comment comment) {
        commentRepo.delete(comment);
    }
}