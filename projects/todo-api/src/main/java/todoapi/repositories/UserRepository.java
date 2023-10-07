package todoapi.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import todoapi.dao.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {
}
