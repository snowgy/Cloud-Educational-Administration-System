package com.cloud.education.dao;

import com.cloud.education.model.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Long>{
    public College findCollegeByName(String name);
}
