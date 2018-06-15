package com.sinwn.capsule.config;

import com.sinwn.capsule.constant.StrConstant;
import com.sinwn.capsule.entity.PermissionEntity;
import com.sinwn.capsule.entity.RoleEntity;
import com.sinwn.capsule.entity.UserEntity;
import com.sinwn.capsule.service.PermissionService;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userInfoService;

    @Resource
    private PermissionService permissionService;

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
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Integer userId = JWTUtil.getUserId(principals.toString());
        if (userId == null) {
            return authorizationInfo;
        }
        List<RoleEntity> roleEntities = permissionService.getRolesByUserId(userId);
        if (roleEntities != null && roleEntities.size() > 0) {
            List<String> roles = new ArrayList<>();
            Set<String> permissionSet = new HashSet<>();
            for (RoleEntity entity : roleEntities) {
                roles.add(entity.getRoleName());

                List<PermissionEntity> permissions
                        = permissionService.getPermissionsByRoleId(entity.getId());
                if (permissions != null && permissions.size() > 0) {
                    for (PermissionEntity permission : permissions) {
                        permissionSet.add(permission.getPermissionName());
                    }
                }
            }
            authorizationInfo.addRoles(roles);
            authorizationInfo.addStringPermissions(permissionSet);
        }

        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        String jwtToken = (String) token.getCredentials();

        Integer userId = JWTUtil.getUserId(jwtToken);
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
