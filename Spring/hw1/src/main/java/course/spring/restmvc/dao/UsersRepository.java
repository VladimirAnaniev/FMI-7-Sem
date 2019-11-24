package course.spring.restmvc.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import course.spring.restmvc.model.User;

public interface UsersRepository extends MongoRepository<User, String> {

}
