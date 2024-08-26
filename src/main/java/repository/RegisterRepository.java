package repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.User;

public interface RegisterRepository extends MongoRepository<User, String> {

    static void save(model.User userObject) {

    }
}
