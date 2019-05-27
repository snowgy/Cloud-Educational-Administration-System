package com.cloud.education.service;

import com.cloud.education.dao.UserRepository;
import com.cloud.education.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * find user by name
     * @param userName username
     * @return corresponding user
     */
    public User findStudentByName(String userName) {
        return userRepository.findStudentByName(userName);
    }
}
