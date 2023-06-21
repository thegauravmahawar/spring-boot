package imdbapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import imdbapi.dao.User;
import imdbapi.exceptions.AlreadyExistException;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.repositories.UserRepository;
import imdbapi.resources.UserResource;
import imdbapi.utils.ResourceValidator;
import imdbapi.utils.SecurityUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static java.lang.String.format;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = {Throwable.class})
    public User create(UserResource userResource) throws InvalidParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException, AlreadyExistException, JsonProcessingException {
        ResourceValidator.validate(userResource);
        validate(userResource);
        User user = userResource.getModel();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistException(format("User with email %s already exist.", user.getEmail()), "ALREADY_EXIST");
        }
        String salt = RandomStringUtils.randomAlphanumeric(10);
        String password = SecurityUtils.encrypt(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        user = userRepository.save(user);
        user.setAuthKey(SecurityUtils.generateAuthKey(user));
        return user;
    }

    private void validate(UserResource userResource) throws InvalidParameterException {
        String password = userResource.getModel().getPassword();
        int passwordLength = StringUtils.length(password);
        if (passwordLength < 8 || passwordLength > 20) {
            throw new InvalidParameterException("Password must have 8-20 characters.", "INVALID_PARAMETER");
        }
        String passwordRegex = "^[a-zA-Z0-9#_@]{8,20}$";
        if (!password.matches(passwordRegex)) {
            throw new InvalidParameterException("Password can only have the following characters: Alphabets (a-zA-Z), Numbers (0-9), Special characters (-#_@).", "INVALID_PARAMETER");
        }
    }

}
