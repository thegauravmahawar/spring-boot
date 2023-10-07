package todoapi.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import todoapi.dao.User;

public class UserResource extends Resource<User> {

    public UserResource(User user) {
        super(user);
    }

    public UserResource() {
        super(new User());
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAuthKey() {
        return getModel().getAuthKey();
    }
}
