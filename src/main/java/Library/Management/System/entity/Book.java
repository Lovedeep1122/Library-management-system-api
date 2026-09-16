package Library.Management.System.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@Table(name = "books")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "available_copies")
    private Integer availableCopies = 1;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowingRecord> borrowingHistory = new ArrayList<>();
}
