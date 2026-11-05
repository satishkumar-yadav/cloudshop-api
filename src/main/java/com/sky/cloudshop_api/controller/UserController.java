package com.sky.cloudshop_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.cloudshop_api.dto.UserRequest;
import com.sky.cloudshop_api.dto.UserResponse;
import com.sky.cloudshop_api.model.User;
import com.sky.cloudshop_api.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // generate constructor for final fields
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // @RequestMapping(value="/api/users", method=RequestMethod.GET)
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        // return ResponseEntity.ok(userService.fetchAllUsers()); // or
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        // User user = userService.fetchUser(id);
        // if(user==null) return ResponseEntity.notFound().build();
        // return ResponseEntity.ok(user);
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User Added Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest updatedUserRequest) {
        boolean updated = userService.updateUser(id, updatedUserRequest);
        if (updated)
            return ResponseEntity.ok("User Updated Successfully");
        return ResponseEntity.notFound().build();
    }
}
