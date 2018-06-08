package com.gzr.controller;

import com.gzr.pojo.User;
import com.gzr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.jws.soap.SOAPBinding;
import java.util.Objects;

/**
 * @author gaozhirong
 * @version ${todo}
 * @Title: ${file_name}
 * @Description: ${todo}
 * @date 2018/6/815:47
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
    @ExceptionHandler(Exception.class)
    public void notFound() {
    }

    @GetMapping("")
    public Flux<User> list() {
        return this.userService.list();
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable("id") final String id) {
        return this.userService.getById(id);
    }

    @PostMapping("")
    public Flux<User> create(@RequestBody Flux<User> users) {
        return this.userService.createOrUpdate(users);
    }

    @PostMapping("/{id}")
    public Mono<User> update(@PathVariable("id") String id, @RequestBody User user) {
        Objects.requireNonNull(user);
        user.setId(id);
        return this.userService.createOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public Mono<User> delete(@PathVariable("id") String id) {
        return this.userService.delete(id);
    }

}
