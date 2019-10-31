package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.redis.RedisServer;

/**
 *  用户角色VO
 */
public class UserRoleVO {
    private String token;
    private String userType;
    private String hospitalId;
    private String roleName;
    private String roleCode;

    public UserRoleVO getRoleInfo(RedisServer redisServer, UserRoleVO roleVO){
        String userRole = (String) redisServer.get(Constants.getUserModuleCacheUserRole(roleVO.getToken()));
        String roleCode = (String) redisServer.get(Constants.getUserModuleCacheUserRoleCode(roleVO.getToken()));
        String userType = (String) redisServer.get(Constants.getUserModuleCacheKeyUserType(roleVO.getToken()));
        String hospitalId = (String) redisServer.get(Constants.getUserModuleCacheKeyHospitalId(roleVO.getToken()));
        roleVO.setHospitalId(hospitalId);
        roleVO.setRoleName(userRole);
        roleVO.setRoleCode(roleCode);
        roleVO.setUserType(userType);
        return roleVO;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
