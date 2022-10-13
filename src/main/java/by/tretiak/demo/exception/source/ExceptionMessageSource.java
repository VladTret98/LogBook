package by.tretiak.demo.exception.source;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
@NoArgsConstructor
@Setter
public class ExceptionMessageSource {

    private static final String PATH = "/messagesource.properties";

    public static final String SERVER_ERROR = "exception.message.server.error";
    public static final String BAD_REQUEST = "exception.message.bad.request";
    public static final String INCORRECT_INPUT = "exception.message.input.incorrect";
    public static final String NO_INPUT_DATA = "exception.message.input.no.data";

    public static final String NOT_AUTHORIZED = "warning.message.not.authorized";
    public static final String NOT_ACCESS = "warning.message.access";
    public static final String USER_NOT_FOUND = "warning.message.user.not.found";
    public static final String DATA_NOT_FOUND = "warning.message.data.not.found";

    public static final String ROLE_ADMIN_NOT_FOUND = "error.role.admin.not.found";
    public static final String ROLE_KEEPER_NOT_FOUND = "error.role.keeper.not.found";
    public static final String ROLE_USER_NOT_FOUND = "error.role.user.not.found";
    public static final String INCORRECT_ROLE = "error.this.role.not found";
    public static final String ROLE_NOT_FOUND = "error.role.not.found";
    public static final String USERNAME_USED = "error.user.name.used";
    public static final String NOT_ENABLE = "error.have.not.access.now";

    public static final String COMPANY_NOT_FOUND = "warning.company.not.found";


    private static final String CRITICAL_SERVER_ERROR = "Критическая ошибка сервера";

    public static Properties properties;

    public static String getMessage(String key) {
        if (properties == null) {
            try(InputStream is = ExceptionMessageSource.class.getResourceAsStream(PATH)) {
                properties = new Properties();
                properties.load(is);
            } catch (IOException e) {
                return CRITICAL_SERVER_ERROR;
            }
        }
        return properties.getProperty(key);
    }



}
