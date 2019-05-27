package com.cloud.education.web;

import com.cloud.education.model.User;
import com.cloud.education.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping(value = {"/", "/login", "/login.html"})
    public String signinControl(){
        return "login";
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession httpSession,
                        Model model){
        System.out.println("call log in");
        System.out.println(username+"  "+password);

        // user shiro to login
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        User user = userService.findStudentByName(username);
        httpSession.setAttribute("user", user);

        if (subject.hasRole("admin"))
            return "redirect:/admin/adminHome";
        else if (subject.hasRole("teacher"))
            return "redirect:/teacher/teacherHome";
        else if (subject.hasRole("student"))
            return "redirect:/student/studentHome";

        model.addAttribute("message","Invalid Credential");

        // if unsuccessful
        return "/login";

    }

    @GetMapping(value = "/student/studentHome")
    public String userHomeController(Model model,
                                     HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        System.out.println("call userHomeController, username:"+user.getName());

        model.addAttribute("user",user);
        return "student/studentHome";
    }
}
