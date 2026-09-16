package Library.Management.System.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true,length = 100)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;


    // A single member can have many borrowing records.
    // 'mappedBy' tells JPA that the BorrowingRecord class owns the database relationship.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();
}
