package imdbapi.services;

import imdbapi.dao.User;
import imdbapi.repositories.UserRepository;
import imdbapi.resources.UserResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public User create(UserResource userResource) {
        return null;
    }
}
