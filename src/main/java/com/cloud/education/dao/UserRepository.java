package com.cloud.education.dao;

import com.cloud.education.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findStudentByName(String userName);
}
