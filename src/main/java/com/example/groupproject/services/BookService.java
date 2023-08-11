package com.example.groupproject.services;

import com.example.groupproject.models.Book;
import com.example.groupproject.repositories.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;

    public Book findById(Long id){
        Optional<Book> result = bookRepo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        return null;
    }

    public void deleteBook(Long bookId) {
        bookRepo.deleteById(bookId);
    }

    public List<Book> all(){
        return bookRepo.findAll();
    }

    public Book createB(Book book){
        return bookRepo.save(book);
    }
    public Book updateB(Book book){
        return bookRepo.save(book);
    }

    public void delete(Long id){
        bookRepo.deleteById(id);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepo.findByTitleContainingIgnoreCase(title);
    }
}

