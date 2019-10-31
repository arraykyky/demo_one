package com.springcloud.book.foreign.controller;

import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.service.UserService;
import com.springcloud.book.foreign.util.MD5;
import com.springcloud.book.foreign.util.SmsUtils;
import com.springcloud.book.foreign.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping(value = "/wechat")
public class WeChatController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private UserService userService;

    /**
     * 微信公众号关注登录
     * @param openId 微信用户唯一标识
     * @return
     */
    @PostMapping("/login")
    public Object loginByOpenId(String openId) {
        PageData pageData = new PageData();
        try {
            if (openId != null && !"".equals(openId)){
                User u = new User();
                u.setUserOpenId(openId);
                User user = userService.getUserByOpenId(u);
                if (user == null){
                    throw new RuntimeException("账户未绑定");
                }else {
                    String userName = user.getUserName();
                    String token = MD5.md5(userName);
                    pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                    pageData.put(OverallConfig.DATA,token);
                    pageData.put(OverallConfig.MSG,"登录成功");
                    redisServer.set(token,true);
                    redisServer.set(Constants.getUserModuleCacheKeyId(token),user.getEllaUserId());
                }
            }else {
                throw new RuntimeException("参数值不能位空");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 微信公众号获取验证码
     * @param phoneNumber   用户手机号
     * @return
     */
    @GetMapping("/sms_code")
    public Object getSmsVerificationCode(String phoneNumber){
        PageData pageData = new PageData();
        try {
            if (phoneNumber != null && !"".equals(phoneNumber)){
                User user = new User();
                user.setUserPhone(phoneNumber);
                try {
                    user = userService.getUserByPhoneNumber(user);
                    if (user == null){
                        throw new RuntimeException("此手机号用户不存在!");
                    }else {
                        pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                        pageData.put(OverallConfig.MSG,"账号绑定验证码以发送!");
                        String code = Tools.getRondomNumStr(6);
                        String smsText = "【博库科技】博库科技提醒您，您的验证码为:" + code + "，此验证码5分钟内有效";
                        try {
                            SmsUtils.sendMsg(OverallConfig.SMS_URL,OverallConfig.SMS_UID,OverallConfig.SMS_PASSWORD,phoneNumber,smsText);
                            redisServer.set(phoneNumber,code,5*60);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException("短信发送失败，请检查手机号是否正确");
                        }
                    }
                } catch (Exception e){
                    String msg = e.getMessage();
                    if (msg.contains("TooManyResultsException")){
                        throw new RuntimeException("此手机号存在多个用户，请联系管理员");
                    }else {
                        throw e;
                    }
                }
            }else {
                throw new RuntimeException("参数值不能位空");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 微信手机号绑定
     * @param phoneNumber   用户手机号
     * @param openId    微信用户唯一标识
     * @param verificationCode 验证码
     * @return
     * @throws Exception
     */
    @PostMapping("/binding")
    public Object addWechatPhoneNumberBinding(String phoneNumber,String openId,String verificationCode) throws Exception {
        PageData pageData = new PageData();
        try {
            if (StringUtils.isNotBlank(phoneNumber) && StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(verificationCode)){
                String code = (String) redisServer.get(phoneNumber);
                if (!code.equals(verificationCode)){
                    throw new RuntimeException("验证码输入错误!");
                }
                //由绑定手机号获取用户信息
                User u = new User();
                u.setUserPhone(phoneNumber);
                User daoUser = userService.getUserByPhoneNumber(u);
                //更新用户openId
                User user = new User();
                user.setUserId(daoUser.getUserId());
                user.setUserOpenId(openId);
                Integer value = userService.updateUser(user);
                if (value > 0){
                    pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                    pageData.put(OverallConfig.MSG,"绑定成功");
                }else {
                    throw new RuntimeException("绑定失败!");
                }
            }else {
                throw new RuntimeException("参数值不能位空");
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }
}
