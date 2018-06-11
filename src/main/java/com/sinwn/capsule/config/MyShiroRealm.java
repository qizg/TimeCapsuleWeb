package com.sinwn.capsule.config;

import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.service.UserService;
import com.sinwn.capsule.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userInfoService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /* 授权 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = JWTUtil.getUserId(principals.toString());
        UserEntity user = userInfoService.findByUserId(userId);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        Set<String> permission = new HashSet<>(Arrays.asList("view,edit".split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        String jwtToken = (String) token.getCredentials();

        Long userId = JWTUtil.getUserId(jwtToken);
        if (userId == null) {
            throw new AuthenticationException("token invalid");
        }

        UserEntity userBean = userInfoService.findByUserId(userId);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        String secret = userBean.getCreateTime().getTime() + StrConstant.PASSWORD_SALT;

        if (!JWTUtil.verify(jwtToken, userId, secret)) {
            throw new AuthenticationException("Token error");
        }

        return new SimpleAuthenticationInfo(
                jwtToken,
                jwtToken,
                getName()  //realm name
        );
    }

}
