package com.cloud.education.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name")
    private String name;

    @JsonBackReference(value = "student-select-course")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    private User student;

    @JsonBackReference(value = "teacher-manage-course")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id")
    private User teacher;

    @Column(name="score")
    private int score;

    @Column(name="course_time")
    private String courseTime;

    @Column(name="classroom")
    private String classroom;

    @Column(name="course_week")
    private int courseWeek;

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
