# Cloud Educational Administration System

[![author](https://img.shields.io/badge/Author-yue%20gong-blue.svg)](https://github.com/snowgy) ![](<https://img.shields.io/badge/springboot-2.1.5-red.svg>)

<div align="center"> <img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwcfngnjj227o13ub2a.jpg"/> </div>

`Access my demo at 10.20.35.198:8081 (SUSTech School network only)`

## FEATURE

* **Privilege Management**: Provide three different roles: student, teacher, admin.
* **Student/Teacher/Course Information Management**: Admin could modify the information of student/teacher/course. Student and teacher could change their password.
* **Students Elective Management**: Student can select and drop course.

## DESIGN

### SaaS Design

My cloud educational administration system is a well-designed software service. Every school can use the service of my web app easily. I use a seperate table `college` in the database to record the information of each school. Each course or user in my database will associate with their own college. Therefore, user information and course information are well separated in the logic layer. Users from different schools cannot access each other's data. 

If a user want to use this cloud system, I will firstly add his college into the `college` table and inject the college teacher, student and course information into the database. Then they can easily use our system.

### Database Design

<div align="center"> <img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwc6exw3j21fo0ycdu2.jpg"/> </div>

The database design is relatively easy. We have six tables in total. They are `user`, `role`, `college`,`department`, `student_course` and `course`. One thing that need to mention is that we conbine student, teacher and admin into one simple object `user` and use the attribute **role** to distinguish them. In addition, teacher will have the attribute **grade** left blank and student will have the attribute **title** left blank.

### Web App Design

My design for the app follows the MVC model. The `dao` layer is for database operation. The `model` layer is the data model used for `ORM ` (object relational mapping). The `sevice` layer contains the business logic. The `web` layer provided object for the front end.

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

`You can try three different roles by our provided user information.`

<div align="center"> <img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwclrmt6j227s140woq.jpg"/> </div>

### Student

Student could view all the courses.

<div align="center"><img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwca3xyxj227s110wjs.jpg"/> </div>

Select course and drop course

<div align="center"><img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwcya8crj227w0qsjv6.jpg"/> </div>

Edit their own password

<div align="center"> <img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwc38ob9j227w0rw416.jpg"/></div>

### Teacher

Teacher can view all the courses they taught

<div align="center"> <img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwd5lj5aj227s0kcad0.jpg"/></div>



Change their own password

<div align="center"> <img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwc38ob9j227w0rw416.jpg"/></div>

### Admin

admin can see information of all the teachers, students and courses.

<div align="center"><img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwca3xyxj227s110wjs.jpg"/> </div>

admin can also edit the information of students and courses

<div align="center"> <img width = '700' src ="https://ws1.sinaimg.cn/mw690/74c2bf2dgy1g3zwd2i1u8j227m0yo0wg.jpg"/></div>



