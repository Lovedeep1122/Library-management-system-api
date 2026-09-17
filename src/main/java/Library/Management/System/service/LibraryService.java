package Library.Management.System.service;

import Library.Management.System.Dto.BorrowingRecordResponseDTO;
import Library.Management.System.entity.Book;
import Library.Management.System.entity.BorrowingRecord;
import Library.Management.System.entity.Member;
import Library.Management.System.repository.BookRepository;
import Library.Management.System.repository.BorrowingRecordRepository;
import Library.Management.System.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    // Constructor injection is the industry standard way to bring in repositories
    public LibraryService(BookRepository bookRepository,
                          MemberRepository memberRepository,
                          BorrowingRecordRepository borrowingRecordRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @Transactional
    public BorrowingRecord borrowBook(Long memberId, Long bookId) {

        // 1. Verify the member exists
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

        // 2. Verify the book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        // 3. Check inventory
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book is currently out of stock.");
        }

        // 4. Decrease book inventory
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // 5. Create the borrowing record
        BorrowingRecord record = new BorrowingRecord();
        record.setMember(member);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14)); // 2-week loan period
        record.setStatus("BORROWED");

        // 6. Save and return the record
        return borrowingRecordRepository.save(record);
    }

    @Transactional
    public BorrowingRecordResponseDTO returnBook(Long memberId, Long bookId) {

        // 1. Find the active borrowing record. We need to find the record where
        // the member ID matches, the book ID matches, and it hasn't been returned yet.
        List<BorrowingRecord> records = borrowingRecordRepository.findByMemberId(memberId);

        BorrowingRecord activeRecord = records.stream()
                .filter(record -> record.getBook().getId().equals(bookId)
                        && "BORROWED".equals(record.getStatus()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active borrowing record found for this member and book."));

        // 2. Mark the record as returned and log the return date
        activeRecord.setStatus("RETURNED");
        activeRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(activeRecord);

        // 3. Find the book and increase the inventory
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found."));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        // 4. Convert the entity right before sending it back
        return convertToDTO(activeRecord);
    }

    // Helper method to convert Entity to DTO
    private BorrowingRecordResponseDTO convertToDTO(BorrowingRecord record) {
        BorrowingRecordResponseDTO dto = new BorrowingRecordResponseDTO();
        dto.setRecordId(record.getId());
        dto.setBookTitle(record.getBook().getTitle()); // Extract just the string!

        // Combine first and last name for a cleaner response
        dto.setMemberName(record.getMember().getFirstName() + " " + record.getMember().getLastName());

        dto.setBorrowDate(record.getBorrowDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setStatus(record.getStatus());
        return dto;
    }
}