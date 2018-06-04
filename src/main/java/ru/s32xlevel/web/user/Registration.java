package ru.s32xlevel.web.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.s32xlevel.model.User;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(Registration.REGISTRATION_URL)
public class Registration extends AbstractUserController {
    static final String REGISTRATION_URL = "/login/registration";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {
        User created = super.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REGISTRATION_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
