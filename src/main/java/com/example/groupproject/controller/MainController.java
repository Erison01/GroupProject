package com.example.groupproject.controller;


import com.example.groupproject.models.*;
import com.example.groupproject.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class MainController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private RestTemplate restTemplate;

    //denis code
    @Autowired
    private CommentService commentService;


    @GetMapping("/")
    private String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @ModelAttribute("newLogin") User newLogin, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId != null) {
            return "redirect:/books";
        }

        model.addAttribute("newLogin", new UserLogin());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, @ModelAttribute("newUser") User newUser,
                           HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId != null) {
            return "redirect:/books";
        }
        model.addAttribute("newUser", new User());

        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result,
                           Model model, HttpSession session) {
        userService.register(newUser, result);
        if (result.hasErrors()) {
            model.addAttribute("newLogin", new UserLogin());
            return "register";
        }
        session.setAttribute("loggedInUserId", newUser.getId());
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") UserLogin newLogin, BindingResult result
            , Model model, HttpSession session) {
        User user = userService.login(newLogin, result);
        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "login";
        }
        session.setAttribute("loggedInUserId", user.getId());
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String showAllBooks(@RequestParam(name = "title", required = false) String title,
                               Model model, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        if (loggedInUserId != null) {
            User loggedInUser = userService.findUser(loggedInUserId);
            model.addAttribute("user", loggedInUser);

            List<Favorite> userFavorites = favoriteService.getUserFavorites(loggedInUser);
            Set<Long> favoriteBookIds = userFavorites.stream()
                    .map(favorite -> favorite.getBook().getId())
                    .collect(Collectors.toSet());

            List<Book> books;

            if (title != null && !title.isEmpty()) {
                books = bookService.searchByTitle(title);
            } else {
                books = bookService.all();
            }

            for (Book book : books) {
                if (favoriteBookIds.contains(book.getId())) {
                    book.setUserFavorite(true);
                } else {
                    book.setUserFavorite(false);
                }
            }

            model.addAttribute("books", books);
        } else {
            List<Book> books;

            if (title != null && !title.isEmpty()) {
                books = bookService.searchByTitle(title);
            } else {
                books = bookService.all();
            }

            model.addAttribute("books", books);
        }

        model.addAttribute("newBook", new Book());
        return "books";
    }


    @PostMapping("/books")
    public String addBook(@Valid @ModelAttribute("newBook") Book newBook, BindingResult result,
                          Model model, HttpSession session) {
        if (result.hasErrors()) {
            List<Book> allBooks = bookService.all();
            model.addAttribute("books", allBooks);
            return "books";
        }


        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId != null) {
            User loggedInUser = userService.findUser(loggedInUserId);
            newBook.setUser(loggedInUser);
        }

        bookService.createB(newBook);
        return "redirect:/books";
    }

    @RequestMapping(value = "/books/{bookId}/addFavorite", method = {RequestMethod.GET, RequestMethod.POST})
    public String addFavorite(@PathVariable Long bookId, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        if (loggedInUserId != null) {
            User loggedInUser = userService.findUser(loggedInUserId);
            Book book = bookService.findById(bookId);

            if (book != null) {
                favoriteService.addFavorite(loggedInUser, book);
            }
        }

        return "redirect:/books";
    }



    @GetMapping("/books/{id}")
    public String viewBookDetails(@PathVariable Long id, Model model, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        if (loggedInUserId != null) {
            User loggedInUser = userService.findUser(loggedInUserId);
            model.addAttribute("user", loggedInUser);
            Book book = bookService.findById(id);

            int numberOfLikes = likeService.countLikesForBook(book);
            model.addAttribute("numberOfLikes", numberOfLikes);

            if (book != null && book.getUser() != null) {
                model.addAttribute("book", book);
                if (loggedInUser.getId().equals(book.getUser().getId())) {
                    model.addAttribute("isCreator", true);
                } else {
                    model.addAttribute("isCreator", false);
                }
                boolean isFavorited = favoriteService.isFavorite(loggedInUser, book);
                model.addAttribute("isFavorited", isFavorited);
                List<User> favoriteUsers = favoriteService.getFavoriteUsers(book);
                model.addAttribute("favoriteUsers", favoriteUsers);
                return "book-details";
            }
        }
        return "redirect:/books";
    }


////    @GetMapping("/books/{id}")
////    public String viewBookDetails(@PathVariable Long id, Model model, HttpSession session) {
////        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
////
////        if (loggedInUserId != null) {
////            User loggedInUser = userService.findUser(loggedInUserId);
////            model.addAttribute("user", loggedInUser);
////
////            Book book = bookService.findById(id);
////
////            if (book != null) {
////                model.addAttribute("book", book);
////
////                if (loggedInUser.getId().equals(book.getUser().getId())) {
////                    // User is the creator of the book, show details and update form
////                    model.addAttribute("isCreator", true);
////                } else {
////                    // User is not the creator of the book, show details only
////                    model.addAttribute("isCreator", false);
////                }
////
////                // Check if the logged-in user has favorited this book
////                boolean isFavorited = favoriteService.isFavorite(loggedInUser, book);
////                model.addAttribute("isFavorited", isFavorited);
////
////                // Get the list of users who have favorited this book
////                List<User> favoriteUsers = favoriteService.getFavoriteUsers(book);
////                model.addAttribute("favoriteUsers", favoriteUsers);
////
////                return "book-details";
////            }
////        }
//
//
//        return "redirect:/books";
//    }


    @PostMapping("/books/{id}/update")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book updatedBook, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        if (loggedInUserId != null) {
            User loggedInUser = userService.findUser(loggedInUserId);
            Book existingBook = bookService.findById(id);

            if (existingBook != null && loggedInUser.getId().equals(existingBook.getUser().getId())) {
                // Update the book details

                existingBook.setDescription(updatedBook.getDescription());

                bookService.updateB(existingBook);
            }
        }

        // Redirect to the book details page
        return "redirect:/books";
    }

    @RequestMapping(value = "/books/{bookId}/removeFavorite", method = {RequestMethod.GET, RequestMethod.POST})
    public String removeFavorite(@PathVariable Long bookId, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        if (loggedInUserId != null) {
            User loggedInUser = userService.findUser(loggedInUserId);
            Book book = bookService.findById(bookId);

            if (book != null) {
                favoriteService.removeFavorite(loggedInUser, book);
            }
        }

        return "redirect:/books";
    }


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/books/{id}/comments")
    public String viewBookComments(
            @PathVariable("id") Long id,
            HttpSession session,
            Model model,
            @ModelAttribute("comment") Comment comment) {

        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", commentService.bookComments(id));

        return "comment";
    }



    @PostMapping("/books/{id}/comments")
    public String newBookComment(
            @PathVariable("id") Long id,
            HttpSession session,
            Model model,
            @Valid @ModelAttribute("comment") Comment comment,
            BindingResult result) {

        if (session.getAttribute("loggedInUserId") == null) {
            return "redirect:/logout";
        }
        Long userId = (Long) session.getAttribute("loggedInUserId");

        Book book = bookService.findById(id);

        if (result.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("comments", commentService.bookComments(id));
            return "comment";
        } else {
            Comment newComment = new Comment(comment.getContent());
            newComment.setBook(book);
            newComment.setCreator(userService.findUser(userId));
            commentService.addComment(newComment);
            return "redirect:/books/" + id + "/comments";
        }        }

    @GetMapping("/comments/{commentId}")
    public String viewCommentDetails(@PathVariable Long commentId, Model model) {
        Comment comment = commentService.findCommentById(commentId);
        if (comment != null) {
            model.addAttribute("comment", comment);
            return "comment";
        }
        return "redirect:/books";
    }
    @PostMapping("/books/{bookId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long bookId, @PathVariable Long commentId, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/login";
        }
        Comment comment = commentService.findCommentById(commentId);
        if (comment != null && comment.getCreator().getId().equals(loggedInUserId)) {
            commentService.deleteComment(comment);
        }
        return "redirect:/books/" + bookId + "/comments";
    }

    @RequestMapping(value = "/books/{bookId}/like", method = RequestMethod.GET)
    public String likeBook(@PathVariable Long bookId, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId != null) {
            User loggedInUser = userService.findUser(loggedInUserId);
            Book book = bookService.findById(bookId);

            if (book != null) {
                likeService.addLike(loggedInUser, book);
            }
        }
        return "redirect:/books";
    }
    @RequestMapping(value = "/books/{bookId}/delete", method = RequestMethod.GET)
    public String deleteBook(@PathVariable Long bookId, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        if (loggedInUserId != null) {
            Book book = bookService.findById(bookId);
            if (book != null && book.getUser().getId().equals(loggedInUserId)) {
                bookService.deleteBook(bookId);
            }
        }

        return "redirect:/books";
    }
    @GetMapping("/externalData")
    public String getExternalData(@RequestParam(required = false) String query, Model model) {
        final String url = "https://uncovered-treasure-v1.p.rapidapi.com/topics";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "cbd72f4473mshe051356c670e17cp18724djsna234e4990082");
        headers.set("X-RapidAPI-Host", "uncovered-treasure-v1.p.rapidapi.com");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiDataItem[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ApiDataItem[].class);
            List<ApiDataItem> items = Arrays.asList(response.getBody());

            if (query != null && !query.isEmpty()) {
                items = items.stream()
                        .filter(item -> item.getTitle().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
            }

            model.addAttribute("apiData", items);

        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch data from the external API.");
        }

        return "externalData";
    }



}




