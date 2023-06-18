package imdbapi.resources;

import imdbapi.dao.User;
import imdbapi.models.Resource;

public class UserResource extends Resource<User> {

    public UserResource(User user) {
        super(user);
    }

    public String getName() {
        return getModel().getName();
    }

    public void setName(String name) {
        getModel().setName(name);
    }

    public String getEmail() {
        return getModel().getEmail();
    }

    public void setEmail(String email) {
        getModel().setEmail(email);
    }

    public void setPassword(String password) {
        getModel().setPassword(password);
    }
}
