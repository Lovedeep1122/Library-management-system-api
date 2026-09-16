package Library.Management.System.repository;


import Library.Management.System.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    boolean existsByIsbn(String isbn);
}
