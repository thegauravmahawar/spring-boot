package imdbapi.services;

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
            InvalidKeyException, AlreadyExistException {
        ResourceValidator.validate(userResource);
        User user = userResource.getModel();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistException(format("User with email %s already exist.", user.getEmail()), "ALREADY_EXIST");
        }
        String salt = RandomStringUtils.randomAlphanumeric(10);
        String password = SecurityUtils.encrypt(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
        return userRepository.save(user);
    }

}
