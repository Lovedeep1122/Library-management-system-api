package Library.Management.System.controller;

import Library.Management.System.Dto.BorrowingRecordResponseDTO;
import Library.Management.System.entity.BorrowingRecord;
import Library.Management.System.repository.BorrowingRecordRepository;
import Library.Management.System.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/borrow") // Changed to /api/borrow to match your other controllers
public class BorrowingController {

    private final LibraryService libraryService;
    private final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingController(LibraryService libraryService,
                               BorrowingRecordRepository borrowingRecordRepository) {
        this.libraryService = libraryService;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    // Helper method to convert Entity to DTO right inside the controller
    private BorrowingRecordResponseDTO convertToDTO(BorrowingRecord record) {
        BorrowingRecordResponseDTO dto = new BorrowingRecordResponseDTO();
        dto.setRecordId(record.getId());
        dto.setBookTitle(record.getBook().getTitle());
        dto.setMemberName(record.getMember().getFirstName() + " " + record.getMember().getLastName());
        dto.setBorrowDate(record.getBorrowDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setStatus(record.getStatus());
        return dto;
    }

    // 1. BORROW A BOOK
    @PostMapping("/{memberId}/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        try {
            BorrowingRecord record = libraryService.borrowBook(memberId, bookId);
            // Convert to DTO before sending to Postman!
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(record));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 2. RETURN A BOOK
    @PutMapping("/return/{memberId}/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        try {
            // Your service already returns the DTO for this one!
            BorrowingRecordResponseDTO responseDTO = libraryService.returnBook(memberId, bookId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 3. GET ALL RECORDS
    @GetMapping
    public ResponseEntity<List<BorrowingRecordResponseDTO>> getAllBorrowingRecords() {
        List<BorrowingRecord> allRecords = borrowingRecordRepository.findAll();

        // Convert the entire list of Entities into a list of DTOs
        List<BorrowingRecordResponseDTO> dtoList = allRecords.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
}
