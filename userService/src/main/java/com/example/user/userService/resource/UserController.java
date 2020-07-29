package com.example.user.userService.resource;

import com.example.user.userService.dao.User;
import com.example.user.userService.exception.UserBadRequest;
import com.example.user.userService.exception.UserNotFoundException;
import com.example.user.userService.request.UserRequest;
import com.example.user.userService.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final String BASE_PATH = "/api/users";

    @ApiOperation("Find all the users")
    @GetMapping(BASE_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return userService.findAll();
    }

    @ApiOperation("Retrieve a user for a given Id")
    @GetMapping(BASE_PATH+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Integer id) {
        return userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    }
    @ApiOperation("Create a user")
    @PostMapping(BASE_PATH)
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userRequest, Errors errors) throws Exception {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = new User(userRequest.getName(), userRequest.getSurname(), userRequest.getAge());
        user = userService.save(user);
        ResponseEntity<User> responseEntity = new ResponseEntity(user, HttpStatus.OK);

        return responseEntity;
    }

    @ApiOperation("Update a user for a given Id")
    @PutMapping(BASE_PATH+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserById(@PathVariable Integer id, @RequestBody  @Valid UserRequest userRequest, Errors errors) {
        if (errors.hasErrors()) {
            throw new UserBadRequest(id);
        }
        userService.findById(id).ifPresentOrElse(user -> {
            user.setId(id);
            user.setAge(userRequest.getAge());
            user.setName(userRequest.getName());
            user.setSurname(userRequest.getSurname());
            user.setEmail(userRequest.getEmail());
            user.setMobile(userRequest.getMobile());
            userService.update(user);
        }, () -> new UserNotFoundException(id));


    }
}
