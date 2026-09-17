package Library.Management.System.Dto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BorrowingRecordResponseDTO {

    private Long recordId;
    private String bookTitle; // We don't send the whole Book object, just the title!
    private String memberName; // Just the name, not the whole Member object!
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;
}
