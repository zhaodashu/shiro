package com.baizhi;

import com.baizhi.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

public class TestCustomerRealmAuthenticator {
    public static void main(String[] args) {

        // 创建securityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 设置自定义Realm
        defaultSecurityManager.setRealm(new CustomerRealm());
        // 将安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        // 通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();
        // 创建token
        UsernamePasswordToken token = new UsernamePasswordToken("zmm", "123456");

        try {
            subject.login(token);
            System.out.println(subject.isAuthenticated());
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }

    }

}
