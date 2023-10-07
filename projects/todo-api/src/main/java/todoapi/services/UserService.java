package todoapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todoapi.dao.User;
import todoapi.repositories.UserRepository;
import todoapi.resources.UserResource;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public User create(UserResource userResource) {
        User user = userResource.getModel();
        return userRepository.save(user);
    }
}
