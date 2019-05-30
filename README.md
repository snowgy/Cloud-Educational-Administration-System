# Cloud Educational Administration System

[![author](https://img.shields.io/badge/Author-yue%20gong-blue.svg)](https://github.com/snowgy) ![](<https://img.shields.io/badge/springboot-2.1.5-red.svg>)

<img src="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3jwfr3nynj227o13ub2a.jpg" width="300"/>

## FEATURE

* **Privilege Management**: Provide three different roles: student, teacher, admin.
* **Student/Teacher/Course Information Management**: Admin could modify the information of student/teacher/course. Student and teacher could change their password.
* **Students Elective Management**: Student can select and drop course.

## DESIGN

### Database Design

<img src="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3jvokt9g3j21fo0ycdu2.jpg" width="300"/>

The database design is relatively easy. We have six tables in total. They are `user`, `role`, `college`,`department`, `student_course` and `course`. One thing that need to mention is that we conbine student, teacher and admin into one simple object `user` and use the attribute **role** to distinguish them. In addition, teacher will have the attribute **grade** left blank and student will have the attribute **title** left blank.

### Web App Design

My design for the app follows the MVC model. The `dao` layer is for database operation. The `model` layer is the data model used for `ORM ` (object relational mapping). The `sevice` layer contains the business logic. The `web` layer provided object for the front end.

```shell
.
└── com
    └── cloud
        └── education
            ├── EducationApplication.java
            ├── dao
            │   ├── CollegeRepository.java
            │   ├── CourseRepository.java
            │   ├── DepartmentRepository.java
            │   ├── RoleRepository.java
            │   └── UserRepository.java
            ├── model
            │   ├── College.java
            │   ├── Course.java
            │   ├── Department.java
            │   ├── Role.java
            │   └── User.java
            ├── realm
            │   ├── ShiroConfig.java
            │   └── UserRealm.java
            ├── service
            │   ├── CollegeService.java
            │   ├── CourseService.java
            │   ├── DepartmentService.java
            │   └── UserService.java
            └── web
                ├── AdminController.java
                ├── CourseController.java
                ├── StudentController.java
                ├── TeacherController.java
                └── UserController.java
```

### Technique Stack

`front end`  thymeleaf

`back end` springboot

`orm` hibernate

`privilege management`  shiro

## PROBLEMS

1. Shiro is a powerful and easy-to-use Java security framework that performs authentication, authorization, cryptography, and session management. I met some problems when I was learning how to use it. At first, I cannot tell the difference from `doGetAuthorizationInfo` and `doGetAuthenticationInfo`. And I cannot run shiro properly because I forgot to write a shiroConfig class.

2. I use` thymeleaf` to build my front end. However, I don't know how to call a backend api directly by `button` element through thymeleaf. The traditional way is to write javascript in the onclick attribute. But I really don't want to write the onerous javascript. Finally I found a perfect way to call backend api through thymeleaf which is quite simple using form like the following.

   ```html
   <form action="#" th:action="@{'/dropCourse/'+${user.name}+'/'+${course.id}+'/'+${user.college.name}}" th:method="get" >
      <button type="submit" class="btn btn-danger btn-sm">drop</button>
   </form>
   ```

## RUNNING RESULT

You can try three different roles by our provided user information.

<img src="https://ws1.sinaimg.cn/mw690/74c2bf2dly1g3jwtwgu9mj227s140woq.jpg" width="300"/>

