package course.spring.restmvc.web;

import java.net.URI;
import java.util.List;

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

import course.spring.restmvc.domain.UsersService;
import course.spring.restmvc.model.Post;
import course.spring.restmvc.model.User;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    public List<User> getUsers() {
        return usersService.findAll();
    }

    @PostMapping
    public ResponseEntity<User> addPost(@RequestBody User user,
                                     UriComponentsBuilder uriComponentsBuilder) {
        User created = usersService.add(user);
        URI location = uriComponentsBuilder.path("api/users/").pathSegment("{id}").build(created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") String userId) {
        return usersService.findById(userId);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable("id") String userId, @RequestBody User user) {
        return usersService.update(userId, user);
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable("id") String userId) {
        return usersService.remove(userId);
    }
}
