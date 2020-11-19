package com.baizhi.realm;

/*
 *  自定义Realm实现 将认证/授权数据的来源转为数据库的实现
 */

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class CustomerRealm extends AuthorizingRealm {

   // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 在token中获取用户名
        String principal = (String) token.getPrincipal();
        System.out.println(principal);
        // 根据身份信息使用jpa mybatis查询数据库
        if("zmm".equals(principal)){
            // 参数1 返回数据库中的正确用户名 //参数2 返回数据库中的正确密码 //参数3 提供当前Realm的名字 调用父类的this.geyName()
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"123456",this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
