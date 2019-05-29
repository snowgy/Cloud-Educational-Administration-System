package com.cloud.education.service;

import com.cloud.education.dao.DepartmentRepository;
import com.cloud.education.model.Department;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public Department findDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }
}
