package com.springcloud.book.foreign.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpMethod.POST;

/**
 * 短信发送工具
 */
@Slf4j
public class SmsUtils {


    /**
     * @param url 接口url
     * @param uid 用户ID
     * @param pwd 密码
     * @param mobile 手机号
     * @param msg 信息
     */
    public static boolean sendMsg(String url, String uid, String pwd, String mobile, String msg) throws NoSuchAlgorithmException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("uid", uid);
        data.add("auth",MD5.md5(pwd));
        data.add("mobile", mobile);
        data.add("expid", "0");
        data.add("msg", msg);

        ResponseEntity<String> response = restTemplate.exchange(url, POST, new HttpEntity<>(data, headers), String.class);
        if(response.getStatusCode() != HttpStatus.OK){
            log.error("短信发送失败，失败代码为 {}", response.getStatusCode().value());
            return false;
        }else {
            String resultMsg = response.getBody();
            if (resultMsg == null){
                log.error("发送短息失败，返回结果信息为空");
                return false;
            }
            SmsResult result = getErrorMsg(resultMsg);
            if (result.isSuccess()){
                log.info("短信发送成功，结果为{}, 结果码为 {}", result.getMsg(), result.getStatusCode());
                return true;
            }else {
                log.error("短信发送失败，结果为{}, 结果码为 {}", result.getMsg(), result.getStatusCode());
                return false;
            }
        }
    }

    private static SmsResult getErrorMsg(String resultValue){

        switch (resultValue){

            case "-1": return new SmsResult(false, resultValue, "签权失败");
            case "-2": return new SmsResult(false, resultValue, "未检索到被叫号码");
            case "-3": return new SmsResult(false, resultValue, "被叫号码过多");
            case "-4": return new SmsResult(false, resultValue, "内容未签名");
            case "-5": return new SmsResult(false, resultValue, "内容过长");
            case "-6": return new SmsResult(false, resultValue, "余额不足");
            case "-7": return new SmsResult(false, resultValue, "暂停发送");
            case "-8": return new SmsResult(false, resultValue, "保留");
            case "-9": return new SmsResult(false, resultValue, "定时发送时间格式错误");
            case "-10": return new SmsResult(false, resultValue, "下发内容为空");
            case "-11": return new SmsResult(false, resultValue, "账户无效");
            case "-12": return new SmsResult(false, resultValue, "Ip地址非法");
            case "-13": return new SmsResult(false, resultValue, "操作频率快");
            case "-14": return new SmsResult(false, resultValue, "操作失败");
            case "-15": return new SmsResult(false, resultValue, "拓展码无效");
            case "-16": return new SmsResult(false, resultValue, "取消定时,seqid错误");
            case "-17": return new SmsResult(false, resultValue, "未开通报告");
            case "-18": return new SmsResult(false, resultValue, "暂留");
            case "-19": return new SmsResult(false, resultValue, "未开通上行");
            case "-20": return new SmsResult(false, resultValue, "暂留");
            case "-21": return new SmsResult(false, resultValue, "包含屏蔽词");
            default: return new SmsResult(true, resultValue, "操作成功");
        }
    }

    private static class SmsResult{

        private boolean success;
        private String statusCode;
        private String msg;

        public SmsResult(boolean success, String statusCode, String msg) {
            this.success = success;
            this.statusCode = statusCode;
            this.msg = msg;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public String getMsg() {
            return msg;
        }
    }
}
