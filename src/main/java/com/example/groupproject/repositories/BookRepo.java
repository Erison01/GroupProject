package com.example.groupproject.repositories;

import com.example.groupproject.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends CrudRepository<Book, Long> {

    List<Book>findAll();

    List<Book> findByTitleContainingIgnoreCase(String title);


}
