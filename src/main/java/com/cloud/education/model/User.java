package com.cloud.education.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="user")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Course> selectedCourses = new ArrayList<>();

    @JsonBackReference(value = "teacher-manage-course")
    @OneToMany(
            mappedBy = "teacher",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Course> managedCourses = new ArrayList<>();


}
