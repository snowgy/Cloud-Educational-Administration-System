package com.cloud.education.web;

import com.cloud.education.model.College;
import com.cloud.education.model.Course;
import com.cloud.education.model.User;
import com.cloud.education.service.CollegeService;
import com.cloud.education.service.CourseService;
import com.cloud.education.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    CollegeService collegeService;

    private int flag = 0;

    private int changePasswordFlag = 0;

    @GetMapping(value = {"/", "/index", "/index.html"})
    public String welcomeControl(){
        return "index";
    }

    @GetMapping(value = {"/login", "/login.html"})
    public String signinControl(Model model){
        List<College> colleges = collegeService.findAll();
        model.addAttribute("colleges", colleges);
        return "login";
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public String login(@RequestParam String username,
                        @RequestParam String collegeName,
                        @RequestParam String password,
                        HttpSession httpSession,
                        Model model){
        System.out.println("call log in");
        System.out.println(username + " " + collegeName + " " + password);
        String usernameAndCollegeName = username + " " + collegeName;
        // user shiro to login
        UsernamePasswordToken token = new UsernamePasswordToken(usernameAndCollegeName, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            model.addAttribute("message","Invalid Credential");
            // if unsuccessful
            return "redirect:/login";
        }

        User user = userService.findUserByNameAndCollege(username, collegeName);
        httpSession.setAttribute("user", user);

        if (subject.hasRole("admin"))
            return "redirect:/admin/adminHome";
        else if (subject.hasRole("teacher"))
            return "redirect:/teacher/teacherHome";
        else if (subject.hasRole("student"))
            return "redirect:/student/studentHome";

        return "redirect:/login";
    }

    @GetMapping(value = "/teacher/teacherHome")
    public String teacherHomeController(Model model,
                                        HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        List<Course> courses = user.getManagedCourses();
        model.addAttribute("user", user);
        model.addAttribute("courses", courses);
        return "teacher/teacherHome";
    }
    @GetMapping(value = "/student/studentHome")
    public String userHomeController(Model model,
                                     HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        System.out.println("call userHomeController, username:"+user.getName());
        College college = user.getCollege();
        List<Course> courses = college.getCourses();
        model.addAttribute("user",user);
        model.addAttribute("courses", courses);
        if (flag == 2) {
            flag = 0;
            model.addAttribute("message", "success");
            model.addAttribute("alertClass", "alert-success");
        } else if (flag == 1) {
            flag = 0;
            model.addAttribute("message", "you already choose this course");
            model.addAttribute("alertClass", "alert-danger");
        }
        return "student/studentHome";
    }

    @GetMapping(value = {"/logout","/logout.html"})
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login";
    }

    @GetMapping(value = {"/selectCourse/{user}/{id}/{college}"})
    public String selectCourse(@PathVariable(value = "user") String userName,
                               @PathVariable(value = "id") Long courseId,
                               @PathVariable(value = "college") String college) {
        User user = userService.findUserByNameAndCollege(userName, college);
        System.out.println("userName:" + userName);
        Course course = courseService.findCourseById(courseId);
        System.out.println(user.getName());
        System.out.println(course.getName());
        if (user.getSelectedCourses().contains(course)){
            System.out.println("failed");
            flag = 1;   // fail
            return "redirect:/student/studentHome";
        }
        course.addUser(user);
        user.addCourse(course);
        userService.save(user);
        courseService.save(course);
        flag = 2;   // success
        return "redirect:/student/studentHome";
    }

    @GetMapping(value = "/student/changePassword")
    public String showChangePasswordStudent(HttpSession session, Model model) {
        changePassword(session, model);
        return "student/changePassword";
    }

    @GetMapping(value = "/teacher/changePassword")
    public String showChangePasswordTeacher(HttpSession session, Model model) {
        changePassword(session, model);
        return "teacher/changePassword";
    }

    @GetMapping(value = "/admin/changePassword")
    public String showChangePasswordAdmin(HttpSession session, Model model) {
        changePassword(session, model);
        return "admin/changePassword.html";
    }


    public void changePassword(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        if (changePasswordFlag == 2) {
            changePasswordFlag = 0;
            model.addAttribute("message", "two password differs");
            model.addAttribute("alertClass", "alert-danger");
        } else if (changePasswordFlag == 1) {
            changePasswordFlag = 0;
            model.addAttribute("message", "success");
            model.addAttribute("alertClass", "alert-success");
        }
    }

    @RequestMapping(value = "/changePasswordAction", method = RequestMethod.POST)
    public String changePassword(@RequestParam String password,
                        @RequestParam String confirmPassword,
                        HttpSession httpSession,
                        Model model) {

        User user = (User) httpSession.getAttribute("user");
        System.out.println(user.getName() + " change password");
        if (! password.equals(confirmPassword)) {
            changePasswordFlag = 2;
            return "redirect:/changePassword.html";
        }
        changePasswordFlag = 1;
        user.setPassword(password);
        userService.save(user);
        httpSession.setAttribute("user", user);
        if (user.getRole().getName().equals("student"))
            return "redirect:/student/changePassword";
        else if (user.getRole().getName().equals("teacher"))
            return "redirect:/teacher/changePassword";
        else if (user.getRole().getName().equals("admin"))
            return "redirect:/admin/changePassword";
        return "";
    }

    @GetMapping(value={"/signup","/signup.html"})
    public String signupControl(){
        return "signup";
    }

}
