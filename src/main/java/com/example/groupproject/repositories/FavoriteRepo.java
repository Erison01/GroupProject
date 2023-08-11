package com.example.groupproject.repositories;

import com.example.groupproject.models.Book;
import com.example.groupproject.models.Favorite;
import com.example.groupproject.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepo extends CrudRepository<Favorite,Long> {

    List<Favorite> findByUser(User user);

    List<Favorite> findByBook(Book book);
}
