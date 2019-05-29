package com.cloud.education.dao;

import com.cloud.education.model.College;
import com.cloud.education.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String userName);
    User findUserById(Long id);
    User findUserByNameAndCollege(String userName, College college);
    List<User> findUserByCollege(College college);
}
