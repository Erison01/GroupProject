package com.example.groupproject.repositories;

import java.util.List;
import com.example.groupproject.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAll();
    List<Comment> findByBookIdIs(Long id);
}