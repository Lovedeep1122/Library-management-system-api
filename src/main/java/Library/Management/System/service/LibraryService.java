package Library.Management.System.service;

import Library.Management.System.entity.Book;
import Library.Management.System.entity.BorrowingRecord;
import Library.Management.System.entity.Member;
import Library.Management.System.repository.BookRepository;
import Library.Management.System.repository.BorrowingRecordRepository;
import Library.Management.System.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
}
