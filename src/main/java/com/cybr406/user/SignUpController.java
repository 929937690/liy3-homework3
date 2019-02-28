package com.cybr406.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

@RestController
public class SignUpController {

    @Autowired
    JdbcUserDetailsManager userDetailsManager;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    User.UserBuilder userBuilder;

    @PostMapping("/register")

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new SignUpValidator());
    }


    public ResponseEntity<Profile> register(@RequestBody @Valid SignUp signup) {
        userDetailsManager.createUser(userBuilder
                .username(signup.getEmail())
                .password(signup.getPassword())
                .roles("AUTHOR")
                .build());

        Profile profile = new Profile();
        profile.setFirstName(signup.getFirstName());
        profile.setLastName(signup.getLastName());
        profile.setEmail(signup.getEmail());

        return new ResponseEntity<>(profileRepository.save(profile), HttpStatus.CREATED);
    }
}
