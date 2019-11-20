package course.spring.restmvc.domain;

import java.util.Collection;

import course.spring.restmvc.model.Comment;

public interface CommentsService {
    Collection<Comment> getAllComments();
    Comment getComment(String id);
    Comment create(Comment comment);
    Comment update(String id, Comment comment);
    Comment delete(String id);
}
