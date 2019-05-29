package com.cloud.education.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="password")
    private String password;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "degree")
    private String degree;

    @Column(name="title")
    private String title;

    @Column(name = "grade")
    private Integer year;


    @JsonBackReference(value = "department-has-users")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department")
    private Department department;

    @JsonBackReference(value = "college-has-users")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="college")
    private College college;

    @JsonBackReference(value = "user-has-role")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role")
    private Role role;

    @JsonBackReference(value = "student-select-course")
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.ALL
    })
    @JoinTable(name="student_course",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="course_id")
    )
    private Set<Course> selectedCourses = new HashSet<>();

    public void addCourse(Course course){
        selectedCourses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course){
        selectedCourses.remove(course);
        course.getStudents().remove(this);
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return this.id == user.id;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @JsonBackReference(value = "teacher-manage-course")
    @OneToMany(
            mappedBy = "teacher",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Course> managedCourses = new ArrayList<>();


}
