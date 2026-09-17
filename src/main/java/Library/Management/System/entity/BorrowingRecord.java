package Library.Management.System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records")
@Data
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many borrowing records belong to ONE member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnore
    private Book book;

    @Column(nullable = false)
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate dueDate;

    @Column(length = 20)
    private String status = "BORROWED";


}
