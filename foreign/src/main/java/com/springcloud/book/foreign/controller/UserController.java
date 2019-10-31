package com.springcloud.book.foreign.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.HttpClientService;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.domain.vo.UserVO;
import com.springcloud.book.foreign.enums.RoleCodeKeyEnum;
import com.springcloud.book.foreign.service.UserService;
import com.springcloud.book.foreign.util.MD5;
import org.apache.commons.lang.StringUtils;
import org.crazycake.shiro.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  用户
 * @author grl
 * @since 2019-07-30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口，登录成功后，将userName做token返回，任意非登录接口必须带token验证参数
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Object login(HttpServletRequest request){
        PageData pageData = new PageData(request);
        try {
            //调用ella登录管理，用户登录信息缓存在redis上统一管理
            String msg = httpClientService.doPost(OverallConfig.LOGIN_URL,pageData);
            Map msgMap = JSON.parseObject(msg, HashMap.class);
            String data = msgMap.get("code").toString();
            if (msg!=null && !msg.equals("") && data.equals("8001")){
                pageData.clear();
                String userName = msgMap.get("data").toString();
                String token = MD5.md5(userName);
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                pageData.put(OverallConfig.DATA,token);
                pageData.put(OverallConfig.MSG,"登录成功");
                //登录成功，以用户名为key做token在redis中记录，记录时间
                long id = (long) redisCache.get(Constants.getUserModuleCacheKeyId(userName));
                String userRole = (String) redisCache.get(Constants.getUserModuleCacheUserRole(userName));
                String userRoleCode = (String) redisCache.get(Constants.getUserModuleCacheUserRoleCode(userName));
                //String userTypeId = (String) redisCache.get(Constants.getUserModuleCacheKeyUserType(userName));
                //由角色指定用户类型的key(在我的图书馆中，控制菜单的显隐)
                String userTypeId = RoleCodeKeyEnum.getNumByCode(userRoleCode);
                long userHospitalId = (long) redisCache.get(Constants.getUserModuleCacheKeyHospitalId(userName));
                //通过EllauserId获取外文上的该用户信息
                UserVO userVO = new UserVO();
                userVO.setEllaUserId(String.valueOf(id));
                User u = userService.getUserByEllaUserId(userVO);
                if (u != null){
                    Map<String,String> map = new HashMap<>();
                    map.put("id",u.getHospitalId());
                    String unitName = httpClientService.doGet(OverallConfig.UNIT_NAME_BY_LONG_ID, map);
                    if (StringUtils.isNotBlank(unitName)){
                        redisServer.set(Constants.getUserModuleCacheKeyHospitalName(token),unitName);
                    }
                    redisServer.set(Constants.getUserModuleCacheKeyForeignUserId(token),u.getUserId());
                }else {
                    throw new RuntimeException("非外文用户，请联系管理员，为您设置用户信息");
                }
                //token值
                redisServer.set(token,true);
                redisServer.set(Constants.getUserModuleCacheKeyId(token),String.valueOf(id));
                redisServer.set(Constants.getUserModuleCacheUserRole(token),userRole);
                redisServer.set(Constants.getUserModuleCacheUserRoleCode(token),userRoleCode);
                redisServer.set(Constants.getUserModuleCacheKeyUserType(token),userTypeId);
                redisServer.set(Constants.getUserModuleCacheKeyHospitalId(token),String.valueOf(userHospitalId));
            }else{
                throw new RuntimeException("登录失败，用户名或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }


    /**
     * 获取用户的基本信息
     * @param userVO 用户VO 包含必要参数 token -- 用户登录标识
     * @return
     */
    @GetMapping("/info")
    public Object getUserInfo(UserVO userVO){
        PageData pageData = new PageData();
        try {
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(userVO.getToken()));
            userVO.setEllaUserId(ellaUserId);
            User u = userService.getUserByEllaUserId(userVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,u);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 获取用户组织机构 -- Ella上获取
     * @param userVO 用户VO 包含必要参数 token -- 用户登录标识
     * @return
     */
    @GetMapping("/units")
    public Object getUserUnitTreeDemo(UserVO userVO){
        PageData pageData = new PageData();
        try {
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(userVO.getToken()));
            userVO.setEllaUserId(ellaUserId);
            List<Map> treeList = userService.getUserUnitList(userVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,treeList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 保存用户修改内容
     * @param userVO 用户VO 包含必要参数 token 用户登录标识 ; userId 用户id,主键 ;
     *                                     userRealName 真实姓名 ; userIdCardNum 身份证号 ; userPhone 电话;
     *                                     userEmail 邮箱 ; researchField 研究领域 ; deptId 部门id(对应机构菜单中id字段)
     * @return
     */
    @PostMapping("/updata")
    public Object updataUserInfo(UserVO userVO){
        PageData pageData = new PageData();
        try {
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(userVO.getToken()));
            userVO.setEllaUserId(ellaUserId);
            userService.updateUserById(userVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 原始密码验证
     * @param userVO 用户VO 包含必要参数 userPassword 用户密码
     * @return
     */
    @GetMapping("check_old_password")
    public Object checkOldPassWord(UserVO userVO){
        PageData pageData = new PageData();
        try {
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(userVO.getToken()));
            userVO.setEllaUserId(ellaUserId);
            boolean value = userService.passWordCheck(userVO);
            if (value){
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
                pageData.put(OverallConfig.DATA,true);
            }else {
                pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
                pageData.put(OverallConfig.MSG,"原始密码输入有误");
                pageData.put(OverallConfig.DATA,false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }


    /**
     * 保存新密码
     * @param userVO 用户VO 包含必要参数 userPassword 用户密码
     * @return
     */
    @PostMapping("/modify_password")
    public Object modifyPassWord(UserVO userVO){
        PageData pageData = new PageData();
        try {
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(userVO.getToken()));
            userVO.setEllaUserId(ellaUserId);
            userService.modifyPassWord(userVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }





}

