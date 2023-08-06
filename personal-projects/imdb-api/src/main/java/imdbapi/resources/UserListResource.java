package imdbapi.resources;

import imdbapi.dao.Title;
import imdbapi.dao.UserList;
import imdbapi.dao.UserListTitleMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserListResource extends Resource<UserList> {

    public UserListResource() {
        super(new UserList());
    }

    public UserListResource(UserList userList) {
        super(userList);
    }

    public void setName(String name) {
        getModel().setName(name);
    }

    public UUID getId() {
        return getModel().getId();
    }

    public String getName() {
        return getModel().getName();
    }

    public void setTitles(List<UUID> titles) {
        getModel().setTitles(titles);
    }

    public List<TitleResource> getTitles() {
        return getModel().getTitleMapping().stream()
                .map(UserListTitleMapping::getTitle)
                .map(TitleResource::new)
                .collect(Collectors.toList());
    }

    public String getCreatedAt() {
        return getModel().getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getUpdatedAt() {
        return getModel().getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
