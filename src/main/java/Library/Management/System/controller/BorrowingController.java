package Library.Management.System.controller;

import Library.Management.System.entity.BorrowingRecord;
import Library.Management.System.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
public class BorrowingController {
    private final LibraryService libraryService;

    // Spring automatically injects your LibraryService here
    public BorrowingController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // CREATE a new borrowing record
    // Example URL: /api/borrow/1/1 (Member ID 1 borrowing Book ID 1)
    @PostMapping("/{memberId}/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        try {
            // We let the Service layer handle the complex logic (checking inventory, setting dates)
            BorrowingRecord record = libraryService.borrowBook(memberId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(record);

        } catch (RuntimeException e) {
            // If the Service throws an error (e.g., "Book out of stock"), return a 400 error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
