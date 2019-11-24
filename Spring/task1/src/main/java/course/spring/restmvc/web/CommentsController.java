package course.spring.restmvc.web;

import java.net.URI;
import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import course.spring.restmvc.domain.CommentsService;
import course.spring.restmvc.model.Comment;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentsService commentsService;


    @GetMapping
    public Collection<Comment> getAllComments() {
        return commentsService.getAllComments();
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody Comment comment,
            UriComponentsBuilder uriComponentsBuilder) {
        Comment createdComment = commentsService.create(comment);
        URI location = uriComponentsBuilder.path("api/comments/").pathSegment("{id}").build(createdComment.getId());
        return ResponseEntity.created(location).body(createdComment);
    }

    @GetMapping("{id}")
    public Comment getComment(@PathVariable String id) {
        return commentsService.getComment(id);
    }

    @PutMapping("{id}")
    public Comment updateComment(@PathVariable String id, @RequestBody Comment comment) {
        return commentsService.update(id, comment);
    }

    @DeleteMapping("{id}")
    public Comment deleteComment(@PathVariable String id) {
        return commentsService.delete(id);
    }
}
