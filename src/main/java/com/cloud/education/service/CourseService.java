package com.cloud.education.service;

import com.cloud.education.dao.CourseRepository;
import com.cloud.education.model.Course;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    /**
     * find course by id
     * @param id
     * @return
     */
    public Course findCourseById(Long id) {
        return courseRepository.findCourseById(id);
    }

    /**
     * save the course
     * @param course course
     */
    public void save(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
