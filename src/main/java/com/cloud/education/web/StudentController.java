package com.cloud.education.web;

import com.cloud.education.model.Course;
import com.cloud.education.model.Department;
import com.cloud.education.model.User;
import com.cloud.education.service.DepartmentService;
import com.cloud.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class StudentController {
    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    private int editFlag = 0;
    private String editMessage;

    @GetMapping(value = {"/admin/editStudent"})
    public String editCourseRender(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        User student = (User) session.getAttribute("student");
        model.addAttribute("user", user);
        model.addAttribute("student", student);
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
        return "admin/editStudent";
    }

    @GetMapping(value = {"/editStudent/{user}/{id}/{college}"})
    public String editCourse(@PathVariable(value = "user") String userName,
                             @PathVariable(value = "id") Long id,
                             @PathVariable(value = "college") String college,
                             HttpSession session,
                             Model model) {
        System.out.println("edit Student");
        User user = userService.findUserByNameAndCollege(userName, college);
        User student = userService.findUserByid(id);
        session.setAttribute("user", user);
        session.setAttribute("student", student);
        return "redirect:/admin/editStudent";
    }

    @RequestMapping(value = "/editStudentAction", method = RequestMethod.POST)
    public String editStudentAction(@RequestParam String name,
                                   @RequestParam String sex,
                                   @RequestParam String birthday,
                                   @RequestParam String degree,
                                   @RequestParam String year,
                                   @RequestParam String departmentName,
                                   HttpSession session) {
        User student = (User) session.getAttribute("student");
        student.setName(name);
        student.setSex(sex);
        student.setDegree(degree);
        student.setBirthday(birthday);
        student.setYear(Integer.valueOf(year));

        Department department = departmentService.findDepartmentByName(departmentName);
        if (department == null) {
            editFlag = 2; //fail
            editMessage = "department does not exist";
            return "redirect:/admin/editStudent";
        }
        student.setDepartment(department);
        userService.save(student);
        session.setAttribute("student", student);
        editFlag = 1;
        editMessage = "success";
        return "redirect:/admin/editStudent";
    }
}
