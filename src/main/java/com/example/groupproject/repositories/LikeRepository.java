package com.example.groupproject.repositories;

import com.example.groupproject.models.Book;
import com.example.groupproject.models.Like;
import com.example.groupproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    int countByBook(Book book);

    Optional<Like> findByUserAndBook(User user, Book book);

    // Add any other necessary methods
}
