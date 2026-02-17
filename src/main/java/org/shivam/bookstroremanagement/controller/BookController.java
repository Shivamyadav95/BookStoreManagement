package org.shivam.bookstroremanagement.controller;

import jakarta.validation.Valid;
import org.shivam.bookstroremanagement.model.Books;
import org.shivam.bookstroremanagement.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Books> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Books addBook(@Valid  @RequestBody Books book) {
        return bookService.saveBook(book);
    }
    @GetMapping("/{id}")
    public Books getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Books updateBook(@PathVariable Long id, @RequestBody Books book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
    @GetMapping("/page")
    public Page<Books> getBooks(Pageable pageable) {
        return bookService.getBooksWithPagination(pageable);
    }

    @GetMapping("/search/title")
    public List<Books> searchTitle(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

    @GetMapping("/search/author")
    public List<Books> searchAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

}