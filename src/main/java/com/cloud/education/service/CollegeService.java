package com.cloud.education.service;

import com.cloud.education.dao.CollegeRepository;
import com.cloud.education.model.College;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegeService {
    @Autowired
    CollegeRepository collegeRepository;

    public List<College> findAll() {
        return collegeRepository.findAll();
    }
    public College findByName(String name) { return collegeRepository.findCollegeByName(name);}

}
