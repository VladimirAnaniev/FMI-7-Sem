package course.spring.restmvc.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import course.spring.restmvc.dao.UsersRepository;
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
        return usersRepository.findById(userId).orElse(null);
    }

    @Override
    public User add(User user) {
        return usersRepository.insert(user);
    }

    @Override
    public User update(User user) {
        return usersRepository.save(user);
    }

    @Override
    public User remove(String userId) {
        User user = usersRepository.findById(userId).orElse(null); //orElseThrow
        usersRepository.delete(user);
        return user;
    }
}
