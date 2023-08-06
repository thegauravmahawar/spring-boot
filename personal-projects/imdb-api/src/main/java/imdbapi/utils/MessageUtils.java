package imdbapi.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtils {

    private static final Logger LOGGER = LogManager.getLogger(MessageUtils.class);

    private static final String DEFAULT_LANG = "en";
    private static Locale defaultLocale = new Locale(DEFAULT_LANG);

    private static MessageSource messageSource = null;

    private MessageUtils(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }

    public static void setMessageSource(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    public static String getMessage(String code) {
        try {
            return messageSource == null ? code : messageSource.getMessage(code, null, defaultLocale);
        } catch (Exception e) {
            LOGGER.error("Exception in getMessage :", e);
            return code;
        }
    }

    public static String getMessage(MessageSourceResolvable msr) {
        return messageSource == null ? msr.getDefaultMessage() : messageSource.getMessage(msr, defaultLocale);
    }

    public static String getMessage(String code, Object... args) {
        return getMessageMultiple(code, args);
    }

    public static String getMessageMultiple(String code, Object[] args) {
        return messageSource.getMessage(code, args, defaultLocale);
    }
}
