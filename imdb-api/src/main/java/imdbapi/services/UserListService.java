package imdbapi.services;

import imdbapi.dao.Title;
import imdbapi.dao.User;
import imdbapi.dao.UserList;
import imdbapi.dao.UserListTitleMapping;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.repositories.TitleRepository;
import imdbapi.repositories.UserListRepository;
import imdbapi.resources.UserListResource;
import imdbapi.utils.ResourceValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserListService {

    private final UserListRepository userListRepository;
    private final TitleRepository titleRepository;

    public UserListService(UserListRepository userListRepository, TitleRepository titleRepository) {
        this.userListRepository = userListRepository;
        this.titleRepository = titleRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public UserList create(UserListResource userListResource, User principal) throws InvalidParameterException {
        ResourceValidator.validate(userListResource);
        validate(userListResource);
        UserList userList = userListResource.getModel();
        if (CollectionUtils.isNotEmpty(userList.getTitles())) {
            List<Title> titles = (List<Title>) titleRepository.findAllById(userList.getTitles());
            List<UserListTitleMapping> titleMappings = titles.stream()
                    .map(title -> new UserListTitleMapping(userList, title))
                    .collect(Collectors.toList());
            userList.setTitleMapping(titleMappings);
        }
        userList.setCreatedBy(principal);
        return userListRepository.save(userList);
    }

    private void validate(UserListResource userListResource) {
    }
}
