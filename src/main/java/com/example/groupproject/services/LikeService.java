package com.example.groupproject.services;

import com.example.groupproject.models.Book;
import com.example.groupproject.models.Like;
import com.example.groupproject.models.User;
import com.example.groupproject.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public void addLike(User user, Book book) {
        if (!likeRepository.findByUserAndBook(user, book).isPresent()) {
            Like like = new Like();
            like.setUser(user);
            like.setBook(book);
            likeRepository.save(like);
        }
    }

    public void removeLike(User user, Book book) {
        Optional<Like> like = likeRepository.findByUserAndBook(user, book);
        like.ifPresent(likeRepository::delete);
    }

    public int countLikesForBook(Book book) {
        return likeRepository.countByBook(book);
    }

    public LikeRepository getLikeRepository() {
        return likeRepository;
    }

    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public int getBookLikes(Book book) {
        // Logic to count likes for the book
        return likeRepository.countByBook(book); // This is just a hypothetical method, adjust based on your actual setup
    }
}
