package com.cloud.education.service;

import com.cloud.education.dao.CollegeRepository;
import com.cloud.education.dao.UserRepository;
import com.cloud.education.model.College;
import com.cloud.education.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CollegeRepository collegeRepository;
    /**
     * find user by name
     * @param userName username
     * @return corresponding user
     */
    public User findUserByName(String userName) {
        return userRepository.findUserByName(userName);
    }

    /**
     * save user to repository
     * @param user user object
     */
    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getAllStudents(College college) {
        List<User> users = userRepository.findUserByCollege(college);
        List<User> students = new ArrayList<>();
        for (User user: users) {
            if (user.getRole().getName().equals("student")) {
                students.add(user);
            }
        }
        return students;
    }

    public List<User> getAllTeachers(College college) {
        List<User> users = userRepository.findUserByCollege(college);
        List<User> teachers = new ArrayList<>();
        for (User user: users) {
            if (user.getRole().getName().equals("teacher")) {
                teachers.add(user);
            }
        }
        return teachers;
    }

    public User findUserByid(long id) {
        return userRepository.findUserById(id);
    }

    public User findUserByNameAndCollege(String userName, String collegeName) {
        College college = collegeRepository.findCollegeByName(collegeName);
        return userRepository.findUserByNameAndCollege(userName, college);
    }
}
