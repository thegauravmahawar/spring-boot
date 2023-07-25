package imdbapi.resources;

import imdbapi.dao.UserList;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

    public String getCreatedAt() {
        return getModel().getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getUpdatedAt() {
        return getModel().getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
