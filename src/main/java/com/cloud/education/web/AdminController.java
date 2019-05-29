package com.cloud.education.web;

import com.cloud.education.model.Course;
import com.cloud.education.model.User;
import com.cloud.education.service.CourseService;
import com.cloud.education.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;

    @GetMapping(value = "/admin/adminHome")
    public String adminHomeController(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        List<User> students = userService.getAllStudents(user.getCollege());
        model.addAttribute("students", students);
        model.addAttribute("user", user);
        return "admin/adminHome";
    }

    @GetMapping(value = "/admin/adminTeacher")
    public String adminAllTeachersController(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        List<User> teachers = userService.getAllTeachers(user.getCollege());
        model.addAttribute("teachers", teachers);
        model.addAttribute("user", user);
        return "admin/adminTeacher";
    }

    @GetMapping(value = "/admin/adminCourse")
    public String adminAllCoursesController(Model model, HttpSession httpSession) {
        List<Course> courses = courseService.getAllCourses();
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        return "admin/adminCourse";
    }


}
