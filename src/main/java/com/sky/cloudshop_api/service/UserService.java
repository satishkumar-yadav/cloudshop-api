package com.sky.cloudshop_api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sky.cloudshop_api.dto.AddressDTO;
import com.sky.cloudshop_api.dto.UserRequest;
import com.sky.cloudshop_api.dto.UserResponse;
import com.sky.cloudshop_api.model.Address;
import com.sky.cloudshop_api.model.User;
import com.sky.cloudshop_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // private List<User> userList = new ArrayList<>();
    // private Long nextId = 1L;

    public List<UserResponse> fetchAllUsers() {
        // List<User> userList= userRepository.findAll(); // return userList;
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUser(Long id) {
        // for (User user : userList)
        // if(user.getId().equals(id)) return user;
        // return null;
        // return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public void addUser(UserRequest userRequest) {
        // user.setId(nextId++);
        // userList.add(user);
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest) {
        // return userList.stream()
        // .filter(user -> user.getId().equals(id))
        // .findFirst()
        // .map(existingUser -> {
        // existingUser.setFirstName(updatedUser.getFirstName());
        // existingUser.setLastName(updatedUser.getLastName());
        // return true;
        // }).orElse(false);
        return userRepository.findById(id)
                .map(existingUser -> {
                    // existingUser.setFirstName(updatedUser.getFirstName());
                    // existingUser.setLastName(updatedUser.getLastName());
                    // existingUser.setEmail(updatedUser.getEmail());
                    // existingUser.setPhone(updatedUser.getPhone());
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
        }
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }

}
