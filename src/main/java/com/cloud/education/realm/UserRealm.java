package com.cloud.education.realm;

import com.cloud.education.model.Role;
import com.cloud.education.model.User;
import com.cloud.education.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("authorization is called");
        // get user role
        User user = (User) getAvailablePrincipal(principalCollection);
        System.out.println("username: " + user.getName());
        Role role = user.getRole();
        // get user permissions
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        if (role != null) {
            roles.add(role.getName());
            info.setRoles(roles);
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("authentication is called");
        // get username and collegeName
        String userNameAndCollegeName = (String) authenticationToken.getPrincipal();
        System.out.println("auth:" + userNameAndCollegeName);
        String[] splited = userNameAndCollegeName.split("\\s+");
        String userName = splited[0];
        String collegeName  = splited[1];
        // get password
        String password = new String((char[])authenticationToken.getCredentials());
        User user = userService.findUserByNameAndCollege(userName, collegeName);

        if (user == null) {
            throw new UnknownAccountException();
        } else if (!password.equals(user.getPassword()))
            throw new IncorrectCredentialsException();

        // pass authentication
        AuthenticationInfo aInfo = new SimpleAuthenticationInfo(user, password, getName());
        return aInfo;
    }
}
