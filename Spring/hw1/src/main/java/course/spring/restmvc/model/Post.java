package course.spring.restmvc.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Document("posts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private String author;
    private Set<String> tags;
    private boolean active;
    private LocalDateTime published = LocalDateTime.now();
}
