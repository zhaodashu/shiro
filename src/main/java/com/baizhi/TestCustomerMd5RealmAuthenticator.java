package com.baizhi;

import com.baizhi.realm.CustomerMd5Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

public class TestCustomerMd5RealmAuthenticator {
    public static void main(String[] args) {

        //创建安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //注入realm
        CustomerMd5Realm realm = new CustomerMd5Realm();
        //设置realm使用hash凭证匹配器

        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5"); //会自动加上盐进行处理
        //散列次数
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);

        defaultSecurityManager.setRealm(realm);

        //将安全管理器注入安全工具类
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //通过安全工具类获取subject对象
        Subject subject = SecurityUtils.getSubject();

        //认证
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen","123");

        try {
            subject.login(token);
            System.out.println("登录成功");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }

        //认证用户进行授权
        if(subject.isAuthenticated()){

            //1.基于角色的权限控制
            System.out.println(subject.hasRole("admin"));
            //基于多角色权限控制
            System.out.println(subject.hasAllRoles(Arrays.asList("admin", "user")));
            //是否具有其中的一个角色
            boolean[] booleans = subject.hasRoles(Arrays.asList("admin", "super", "user"));
            for (boolean aBoolean : booleans){
                System.out.println(aBoolean);
            }
            System.out.println("==============================");
            //2.基于权限字符串的访问控制  资源标识符：操作：资源类型
            System.out.println("权限：" + subject.isPermitted("user:*:01"));
            System.out.println("权限：" + subject.isPermitted("product:create"));
            //分别具有哪些权限
            boolean[] permitted = subject.isPermitted("user:*:01", "order:*:01");
            for (boolean b : permitted){
                System.out.println(b);
            }
            //同时具有哪些权限
            boolean permittedAll = subject.isPermittedAll("user:*:01", "order:*:01");
            System.out.println(permittedAll);

        }
    }
}
