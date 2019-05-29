package com.cloud.education.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="course_id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @JsonBackReference(value = "student-select-course")
    @ManyToMany(mappedBy = "selectedCourses")
    private Set<User> students = new HashSet<>();

    public void addUser(User user){
        students.add(user);
        user.getSelectedCourses().add(this);
    }

    public void removeUser(User user){
        students.remove(user);
        user.getSelectedCourses().remove(this);
    }

    @Override
    public boolean equals(Object obj) {
        Course course = (Course) obj;
        return this.id == course.id;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @JsonBackReference(value = "teacher-manage-course")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id")
    private User teacher;


    @Column(name="score")
    private Integer score;

    @Column(name="course_time")
    private String courseTime;

    @Column(name="classroom")
    private String classroom;

    @Column(name="course_week")
    private Integer courseWeek;

    @Column(name="course_type")
    private String courseType;

    @JsonBackReference(value = "course-college")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="college_id")
    private College college;

    @JsonBackReference(value = "course-department")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;

    @Column(name="credit")
    private int credit;

}
