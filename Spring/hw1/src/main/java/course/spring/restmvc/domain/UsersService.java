package course.spring.restmvc.domain;

import java.util.List;

import course.spring.restmvc.model.User;

public interface UsersService {

    List<User> findAll();

    User findById(String userId);

    User add(User user);

    User update(String userId, User user);

    User remove(String userId);
}
