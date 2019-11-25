package course.spring.restmvc.web;

import course.spring.restmvc.domain.PostsService;
import course.spring.restmvc.model.Post;
import lombok.RequiredArgsConstructor;

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

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostsController {
    private final PostsService postsService;

    @GetMapping
    public List<Post> getPosts() {
        return postsService.findAll();
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post,
                                     UriComponentsBuilder uriComponentsBuilder) {
        Post created = postsService.add(post);
        URI location = uriComponentsBuilder.path("api/posts/").pathSegment("{id}").build(created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("{id}")
    public Post getPost(@PathVariable("id") String postId) {
        return postsService.findById(postId);
    }

    @PutMapping("{id}")
    public Post updatePost(@PathVariable("id") String postId, @RequestBody Post post) {
        return postsService.update(postId, post);
    }

    @DeleteMapping("{id}")
    public Post deletePost(@PathVariable("id") String postId) {
        return postsService.remove(postId);
    }
}
