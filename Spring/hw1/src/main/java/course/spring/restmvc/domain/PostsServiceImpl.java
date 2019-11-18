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
        return postsRepository.findById(postId).orElse(null); // TODO orElseThrow;
    }

    @Override
    public Post add(Post post) {
        return postsRepository.insert(post);
    }

    @Override
    public Post update(Post post) {
        return postsRepository.save(post);
    }

    @Override
    public Post remove(String postId) {
        Post post = postsRepository.findById(postId).orElse(null); // TODO orElseThrow
        postsRepository.delete(post);
        return post;
    }
}
