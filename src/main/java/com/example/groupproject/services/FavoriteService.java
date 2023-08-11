package com.example.groupproject.services;

import com.example.groupproject.models.Book;
import com.example.groupproject.models.Favorite;
import com.example.groupproject.models.User;
import com.example.groupproject.repositories.FavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepo favoriteRepo;

    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepo.save(favorite);
    }

    public List<Favorite> getUserFavorites(User user) {
        return favoriteRepo.findByUser(user);
    }

    public Favorite addFavorite(User user, Book book) {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);
        return favoriteRepo.save(favorite);
    }

    // new part of code

    public boolean isFavorite(User user, Book book) {
        List<Favorite> userFavorites = favoriteRepo.findByUser(user);
        return userFavorites.stream().anyMatch(favorite -> favorite.getBook().equals(book));
    }

    public List<User> getFavoriteUsers(Book book) {
        List<Favorite> favorites = favoriteRepo.findByBook(book);
        return favorites.stream().map(Favorite::getUser).collect(Collectors.toList());
    }

    public void removeFavorite(User user, Book book) {
        List<Favorite> favorites = favoriteRepo.findByUser(user);
        favorites.stream()
                .filter(favorite -> favorite.getBook().equals(book))
                .forEach(favoriteRepo::delete);
    }
}
