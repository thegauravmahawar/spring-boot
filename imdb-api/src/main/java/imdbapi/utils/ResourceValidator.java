package imdbapi.utils;

import imdbapi.exceptions.InvalidParameterException;
import imdbapi.models.Resource;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;

public class ResourceValidator {

    private static final ValidatorFactory VALIDATOR_FACTORY;
    private static final Validator VALIDATOR;

    static {
        VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
        VALIDATOR = VALIDATOR_FACTORY.getValidator();
    }

    public static <K> void validate(Resource<K> resource) throws InvalidParameterException {
        K model = resource.getModel();
        validateModel(model);
    }

    public static <K> void validateModel(K model) throws InvalidParameterException {
        Set<ConstraintViolation<K>> constraintViolations = VALIDATOR.validate(model);
        String violationMessage = "Following constraints failed : ";
        if (constraintViolations.size() > 0) {
            violationMessage = constraintViolations.
                    stream()
                    .map((constraintViolation) -> constraintViolation.getMessage() + " ")
                    .reduce(violationMessage, String::concat);
            throw new InvalidParameterException(violationMessage, "INVALID_PARAMETER");
        }
    }

    public static <K> Set<ConstraintViolation<K>> getConstraintViolations(K model) {
        return VALIDATOR.validate(model);
    }

    public static <K> void validateModels(List<K> models) throws InvalidParameterException {
        if (CollectionUtils.isNotEmpty(models)) {
            for (K model : models) {
                validateModel(model);
            }
        }

    }
}
