package ru.javawebinar.topjava.web.user;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.HasEmail;
import ru.javawebinar.topjava.web.LanguageUtil;

@Component
public class UserValidator implements Validator {
    private final static String USER_EMAIL_FIELD = "email";
    private final static String USER_EMAIL_ERROR_MESSAGE = "user.emailExistError";

    private final UserRepository repository;
    private final LanguageUtil languageUtil;

    public UserValidator(UserRepository repository, LanguageUtil languageUtil) {
        this.repository = repository;
        this.languageUtil = languageUtil;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        if (errors.getFieldErrors().stream().anyMatch(ef -> ef.getField().equals(USER_EMAIL_FIELD)))
            return;

        HasEmail hasEmail = (HasEmail) target;
        User persistentUser = repository.getByEmail(hasEmail.getEmail());
        if (persistentUser != null && (hasEmail.getId() == null || !persistentUser.getId().equals(hasEmail.getId()))) {
            errors.rejectValue(USER_EMAIL_FIELD, USER_EMAIL_ERROR_MESSAGE, languageUtil.getLocalizedMessage(USER_EMAIL_ERROR_MESSAGE));
        }
    }
}