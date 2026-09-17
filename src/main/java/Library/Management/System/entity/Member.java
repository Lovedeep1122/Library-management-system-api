package Library.Management.System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotBlank(message = "First name is mandatory")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format must be valid")
    @Column(nullable = false, unique = true,length = 100)
    private String email;

    @PastOrPresent(message = "Join date cannot be in the future")
    @Column(nullable = false)
    private LocalDate birthDate;


    // A single member can have many borrowing records.
    // 'mappedBy' tells JPA that the BorrowingRecord class owns the database relationship.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();
}
