import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

@JsonAutoDetect
public class Comment {
//    private String postId;
    private Long id;
    private String name;
    private String email;
    private String body;
}
