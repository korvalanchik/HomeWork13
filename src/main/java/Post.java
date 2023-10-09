import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

@JsonAutoDetect
public class Post {
    private String username;
    private Long id;
    private String title;
    private String body;
}
