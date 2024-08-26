package repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import model.User;

public interface LoginRepository extends MongoRepository<User, String> {
    User findByName(String name);
}
