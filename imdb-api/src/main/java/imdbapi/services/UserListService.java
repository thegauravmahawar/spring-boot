package imdbapi.services;

import imdbapi.dao.User;
import imdbapi.dao.UserList;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.repositories.UserListRepository;
import imdbapi.resources.UserListResource;
import imdbapi.utils.ResourceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserListService {

    private final UserListRepository userListRepository;

    public UserListService(UserListRepository userListRepository) {
        this.userListRepository = userListRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public UserList create(UserListResource userListResource, User principal) throws InvalidParameterException {
        ResourceValidator.validate(userListResource);
        validate(userListResource);
        UserList userList = userListResource.getModel();
        userList.setCreatedBy(principal);
        return userListRepository.save(userList);
    }

    private void validate(UserListResource userListResource) {
    }

    @Transactional(rollbackFor = {Throwable.class})
    public UserList addTitles(UserListResource userListResource, User principal) throws InvalidParameterException {
        ResourceValidator.validate(userListResource);
        validate(userListResource);
        UserList userList = userListResource.getModel();
        userList.setCreatedBy(principal);
        return userListRepository.save(userList);
    }
}
