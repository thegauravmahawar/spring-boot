package imdbapi.services;

import imdbapi.dao.User;
import imdbapi.dao.UserList;
import imdbapi.repositories.UserListRepository;
import imdbapi.resources.UserListResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserListService {

    private final UserListRepository userListRepository;

    public UserListService(UserListRepository userListRepository) {
        this.userListRepository = userListRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public UserList create(UserListResource userListResource, User principal) {
        return null;
    }
}
