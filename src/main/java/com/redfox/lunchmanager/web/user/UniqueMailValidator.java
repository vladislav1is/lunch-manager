package com.redfox.lunchmanager.web.user;

import com.redfox.lunchmanager.HasIdAndEmail;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.UserRepository;
import com.redfox.lunchmanager.web.ExceptionInfoHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UniqueMailValidator implements Validator {

    private final UserRepository repository;

    public UniqueMailValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasIdAndEmail user = ((HasIdAndEmail) target);
        String email = user.getEmail();
        if (StringUtils.hasText(email)) {
            User dbUser = repository.getBy(email.toLowerCase());
            if (dbUser != null && !dbUser.getId().equals(user.getId())) {
                errors.rejectValue("email", ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL);
            }
        }
    }
}
