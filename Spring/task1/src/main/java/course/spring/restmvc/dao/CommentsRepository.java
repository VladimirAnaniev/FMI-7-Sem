package course.spring.restmvc.dao;

import course.spring.restmvc.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentsRepository extends MongoRepository<Comment, String> {
}
