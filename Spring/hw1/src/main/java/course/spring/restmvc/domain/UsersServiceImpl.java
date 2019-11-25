package course.spring.restmvc.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import course.spring.restmvc.dao.UsersRepository;
import course.spring.restmvc.exception.EntityNotFoundException;
import course.spring.restmvc.model.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public User findById(String userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id '" + userId + "' does not exist!"));
    }

    @Override
    public User add(User user) {
        return usersRepository.insert(user);
    }

    @Override
    public User update(String userId, User user) {
        User dbUser = findById(userId);
        dbUser.setEmail(user.getEmail());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setPassword(user.getPassword());
        dbUser.setPhoto(user.getPhoto());
        dbUser.setRole(user.getRole());
        return usersRepository.save(dbUser);
    }

    @Override
    public User remove(String userId) {
        User user = findById(userId);
        usersRepository.delete(user);
        return user;
    }
}
