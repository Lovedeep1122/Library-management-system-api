package Library.Management.System.repository;
import Library.Management.System.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
}
