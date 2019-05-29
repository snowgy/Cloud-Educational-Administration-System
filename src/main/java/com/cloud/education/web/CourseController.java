package com.cloud.education.web;

import com.cloud.education.dao.CourseRepository;
import com.cloud.education.model.College;
import com.cloud.education.model.Course;
import com.cloud.education.model.Department;
import com.cloud.education.model.User;
import com.cloud.education.service.CollegeService;
import com.cloud.education.service.CourseService;
import com.cloud.education.service.DepartmentService;
import com.cloud.education.service.UserService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Set;

@Controller
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    CollegeService collegeService;

    private int dropFlag = 0;
    private int editFlag = 0;
    private String editMessage;

    @GetMapping(value = {"/student/selectedCourse"})
    public String selecedCourse(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Set<Course> courses = user.getSelectedCourses();
        model.addAttribute("user", user);
        model.addAttribute("courses", courses);
        if (dropFlag == 1){
            dropFlag = 0;
            model.addAttribute("message", "success drop the course");
            model.addAttribute("alertClass", "alert-success");
        }

        return "student/selectedCourse";
    }

    @GetMapping(value = {"/student/selectedCourse/{user}/{college}"})
    public String showSelectedCourse(@PathVariable(value = "user") String userName, @PathVariable(value = "college") String college, Model model, HttpSession session){
        System.out.println("selected Course");
        User user = userService.findUserByNameAndCollege(userName, college);
        session.setAttribute("user", user);
        Set<Course> courseSet = user.getSelectedCourses();
        model.addAttribute("courses", courseSet);
        model.addAttribute("user", user);
        return "redirect:/student/selectedCourse";
    }

    @GetMapping(value = {"/dropCourse/{user}/{id}/{college}"})
    public String dropCourse(@PathVariable(value = "user") String userName, @PathVariable(value = "id") Long id,  @PathVariable(value = "college") String college, HttpSession session) {
        System.out.println("drop Course");
        User user = userService.findUserByNameAndCollege(userName, college);
        Course course = courseService.findCourseById(id);
        user.removeCourse(course);
        course.removeUser(user);
        userService.save(user);
        session.setAttribute("user", user);
        courseService.save(course);
        dropFlag = 1;
        return "redirect:/student/selectedCourse";
    }

    @GetMapping(value = {"/admin/editCourse"})
    public String editCourseRender(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Course course = (Course) session.getAttribute("course");
        model.addAttribute("user", user);
        model.addAttribute("course", course);
        // fail
        if (editFlag == 2) {
            editFlag = 0; // remove message
            model.addAttribute("message", editMessage);
            model.addAttribute("alertClass", "alert-danger");
        } else if (editFlag == 1) {
            editFlag = 0; // remove message
            model.addAttribute("message", editMessage);
            model.addAttribute("alertClass", "alert-success");

        }
        return "admin/editCourse";
    }

    @GetMapping(value = {"/editCourse/{user}/{id}/{college}"})
    public String editCourse(@PathVariable(value = "user") String userName,
                             @PathVariable(value = "id") Long id,
                             @PathVariable(value = "college") String college,
                             HttpSession session,
                             Model model) {
        System.out.println("edit Course");
        User user = userService.findUserByNameAndCollege(userName, college);
        Course course = courseService.findCourseById(id);
        session.setAttribute("user", user);
        session.setAttribute("course", course);
        session.setAttribute("college", collegeService.findByName(college));
        return "redirect:/admin/editCourse";
    }

    @RequestMapping(value = "/editCourseAction", method = RequestMethod.POST)
    public String editCourseAction(@RequestParam String name,
                                   @RequestParam String classroom,
                                   @RequestParam String courseTime,
                                   @RequestParam String courseType,
                                   @RequestParam String courseWeek,
                                   @RequestParam String credit,
                                   @RequestParam String teacher,
                                   @RequestParam String department,
                                   HttpSession session) {
        Course course = (Course) session.getAttribute("course");
        course.setName(name);
        course.setClassroom(classroom);
        course.setCourseTime(courseTime);
        course.setCourseType(courseType);
        course.setCourseWeek(Integer.valueOf(courseWeek));
        course.setCredit(Integer.valueOf(credit));
        College college = (College) session.getAttribute("college");
        User teacherObj = userService.findUserByNameAndCollege(teacher, college.getName());
        if (teacherObj == null) {
            editFlag = 2; //fail
            editMessage = "teacher does not exist";
            return "redirect:/admin/editCourse";
        }
        course.setTeacher(teacherObj);
        Department departmentObj = departmentService.findDepartmentByName(department);
        if (departmentObj == null) {
            editFlag = 2; //fail
            editMessage = "department does not exist";
            return "redirect:/admin/editCourse";
        }
        course.setDepartment(departmentObj);
        courseService.save(course);
        session.setAttribute("course", course);
        editFlag = 1;
        editMessage = "success";
        return "redirect:/admin/editCourse";
    }
}
