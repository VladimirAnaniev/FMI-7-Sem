package course.spring.restmvc.domain;

import course.spring.restmvc.model.Post;

import java.util.List;

public interface PostsService {

    List<Post> findAll();

    Post findById(String postId);

    Post add(Post post);

    Post update(String postId, Post post);

    Post remove(String postId);
}
