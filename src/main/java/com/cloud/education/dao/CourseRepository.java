package com.cloud.education.dao;

import com.cloud.education.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseById(Long id);
}
