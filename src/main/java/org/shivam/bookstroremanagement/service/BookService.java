package org.shivam.bookstroremanagement.service;
import org.shivam.bookstroremanagement.exception.ResourceNotFoundException;
import org.shivam.bookstroremanagement.model.Books;
import org.shivam.bookstroremanagement.repo.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    public Books saveBook(Books book) {
        return bookRepository.save(book);
    }

    public Books getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public Books updateBook(Long id, Books updatedBook) {
        Books book = getBookById(id);

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setGenre(updatedBook.getGenre());
        book.setIsbn(updatedBook.getIsbn());
        book.setPrice(updatedBook.getPrice());
        book.setDescription(updatedBook.getDescription());
        book.setStockQuantity(updatedBook.getStockQuantity());
        book.setImageUrl(updatedBook.getImageUrl());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    //paging method
    public Page<Books> getBooksWithPagination(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public List<Books> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Books> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
}