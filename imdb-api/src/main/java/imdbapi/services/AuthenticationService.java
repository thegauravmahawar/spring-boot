package imdbapi.services;

import imdbapi.dao.User;
import imdbapi.exceptions.InvalidAuthKeyException;
import imdbapi.exceptions.InvalidParameterException;
import imdbapi.exceptions.NotFoundException;
import imdbapi.models.AuthKey;
import imdbapi.repositories.UserRepository;
import imdbapi.utils.JsonUtils;
import imdbapi.utils.MessageUtils;
import imdbapi.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUserByAuthKey(String key) throws InvalidAuthKeyException {
        AuthKey authKey = decryptAuthKey(key);
        Optional<User> user = userRepository.findByEmail(authKey.getEmail());
        if (!user.isPresent() || user.get().getKeyGeneratedAt().compareTo(authKey.getKeyGeneratedAt()) != 0) {
            throw new InvalidAuthKeyException(
                    MessageUtils.getMessage("error.invalidAuthKey"),
                    MessageUtils.getMessage("error.code.invalidAuthKey")
            );
        }
        return user.get();
    }

    public AuthKey decryptAuthKey(String key) throws InvalidAuthKeyException {
        AuthKey authKey;
        try {
            String decryptedString = SecurityUtils.decryptAuthKey(key);
            authKey = JsonUtils.parseJson(decryptedString, AuthKey.class);
        } catch (GeneralSecurityException | IOException e) {
            throw new InvalidAuthKeyException(
                    MessageUtils.getMessage("error.invalidAuthKey"),
                    MessageUtils.getMessage("error.code.invalidAuthKey")
            );
        }
        if (authKey == null) {
            throw new InvalidAuthKeyException(
                    MessageUtils.getMessage("error.invalidAuthKey"),
                    MessageUtils.getMessage("error.code.invalidAuthKey")
            );
        }
        return authKey;
    }
}
