package course.spring.restmvc.domain;

import java.util.Collection;

import org.springframework.stereotype.Service;

import course.spring.restmvc.dao.CommentsRepository;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.model.Comment;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    @Override
    public Collection<Comment> getAllComments() {
        return commentsRepository.findAll();
    }

    @Override
    public Comment getComment(String id) {
        return commentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id '" + id + "' does not exist!"));
    }

    @Override
    public Comment create(Comment comment) {
        return commentsRepository.insert(comment);
    }

    @Override
    public Comment update(String id, Comment comment) {
        Comment existing = getComment(id);
        existing.setAuthorEmail(comment.getAuthorEmail());
        existing.setCommentText(comment.getCommentText());
        existing.setPageUrl(comment.getPageUrl());
        return commentsRepository.save(existing);
    }

    @Override
    public Comment delete(String id) {
        Comment comment = getComment(id);
        commentsRepository.delete(comment);
        return comment;
    }
}
