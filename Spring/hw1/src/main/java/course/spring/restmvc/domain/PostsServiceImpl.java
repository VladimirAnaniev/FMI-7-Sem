package course.spring.restmvc.domain;

import course.spring.restmvc.dao.PostsRepository;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.model.ErrorResponse;
import course.spring.restmvc.model.Post;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    private final PostsRepository postsRepository;

    @Override
    public List<Post> findAll() {
        return postsRepository.findAll();
    }

    @Override
    public Post findById(String postId) {
        return postsRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with id '" + postId + "' does not exist!"));
    }

    @Override
    public Post add(Post post) {
        return postsRepository.insert(post);
    }

    @Override
    public Post update(String postId, Post post) {
        Post dbPost = findById(postId);
        dbPost.setActive(post.isActive());
        dbPost.setAuthor(post.getAuthor());
        dbPost.setContent(post.getContent());
        dbPost.setTags(post.getTags());
        dbPost.setTitle(post.getTitle());
        return postsRepository.save(post);
    }

    @Override
    public Post remove(String postId) {
        Post post = findById(postId);
        postsRepository.delete(post);
        return post;
    }
}
