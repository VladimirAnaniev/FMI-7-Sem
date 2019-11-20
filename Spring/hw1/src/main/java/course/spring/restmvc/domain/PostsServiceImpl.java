package course.spring.restmvc.domain;

import course.spring.restmvc.dao.PostsRepository;
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
        return postsRepository.findById(postId).orElse(null);
    }

    @Override
    public Post add(Post post) {
        return postsRepository.insert(post);
    }

    @Override
    public Post update(String postId, Post post) {
        // TODO: assert postId == post.getId()
        if (exists(postId)) {
            return null;
        }
        return postsRepository.save(post);
    }

    @Override
    public Post remove(String postId) {
        Post post = findById(postId);
        if (post == null) {
            return null;
        }
        postsRepository.delete(post);
        return post;
    }

    private boolean exists(String postId) {
        return postsRepository.existsById(postId);
    }
}
