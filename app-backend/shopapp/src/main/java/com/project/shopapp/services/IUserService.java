package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.User;

public interface IUserService {
    public User createUser(UserDTO userDTO) throws Exception;
    public String login(String phoneNumber, String password) throws Exception;
}
