import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

@JsonAutoDetect
public class Todo {
    private Long userId;
    private Long id;
    private String title;
    private Boolean completed;
}
