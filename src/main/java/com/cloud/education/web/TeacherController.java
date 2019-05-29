package com.cloud.education.web;

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
public class TeacherController {
    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    private int editFlag = 0;
    private String editMessage;

    @GetMapping(value = {"/admin/editTeacher"})
    public String editTeacherRender(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        User teacher = (User) session.getAttribute("teacher");
        model.addAttribute("user", user);
        model.addAttribute("teacher", teacher);
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
        return "admin/editTeacher";
    }

    @GetMapping(value = {"/editTeacher/{user}/{id}/{college}"})
    public String editTeacher(@PathVariable(value = "user") String userName,
                             @PathVariable(value = "id") Long id,
                              @PathVariable(value = "college")String college,
                             HttpSession session,
                             Model model) {
        System.out.println("edit Teacher");
        User user = userService.findUserByNameAndCollege(userName, college);
        User teacher = userService.findUserByid(id);
        session.setAttribute("user", user);
        session.setAttribute("teacher", teacher);
        return "redirect:/admin/editTeacher";
    }

    @RequestMapping(value = "/editTeacherAction", method = RequestMethod.POST)
    public String editTeacherAction(@RequestParam String name,
                                    @RequestParam String sex,
                                    @RequestParam String birthday,
                                    @RequestParam String title,
                                    @RequestParam String departmentName,
                                    HttpSession session) {
        User teacher = (User) session.getAttribute("teacher");
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setTitle(title);
        teacher.setBirthday(birthday);
        Department department = departmentService.findDepartmentByName(departmentName);
        if (department == null) {
            editFlag = 2; //fail
            editMessage = "department does not exist";
            return "redirect:/admin/editTeacher";
        }
        teacher.setDepartment(department);
        userService.save(teacher);
        session.setAttribute("teacher", teacher);
        editFlag = 1;
        editMessage = "success";
        return "redirect:/admin/editTeacher";
    }
}
