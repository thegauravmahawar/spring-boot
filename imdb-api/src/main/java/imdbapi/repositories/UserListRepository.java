package imdbapi.repositories;

import imdbapi.dao.UserList;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserListRepository extends ListCrudRepository<UserList, UUID> {
}
