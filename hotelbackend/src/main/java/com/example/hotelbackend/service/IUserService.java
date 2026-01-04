package com.example.hotelbackend.service;

import java.util.List;

import com.example.hotelbackend.model.User;




public interface IUserService {
    User getUserId (String Id);
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
