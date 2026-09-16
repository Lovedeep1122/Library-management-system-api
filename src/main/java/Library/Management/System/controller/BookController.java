package Library.Management.System.controller;

import Library.Management.System.entity.Book;
import Library.Management.System.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")

public class BookController {
 private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {

        // 1. Check if the ISBN already exists in the database
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            // 2. If it does, return a 400 Bad Request with a helpful message
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: A book with this ISBN already exists.");
        }

        // 3. If it doesn't exist, save it and return a 201 Created status
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
