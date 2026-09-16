package Library.Management.System.repository;
import Library.Management.System.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    // Basic CRUD operations are inherited.
    // You can add custom queries to find specific records later, for example:

    // Find all records for a specific member
    List<BorrowingRecord> findByMemberId(Long memberId);

    // Find all records that are currently "BORROWED" (not yet returned)
    List<BorrowingRecord> findByStatus(String status);
}
