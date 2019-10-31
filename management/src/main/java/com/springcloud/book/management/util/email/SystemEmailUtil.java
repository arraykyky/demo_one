package com.springcloud.book.management.util.email;

import com.springcloud.book.management.domain.SystemEmail;
import com.springcloud.book.management.domain.vo.EmailVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SystemEmailUtil {

    static Logger logger = LoggerFactory.getLogger(SystemEmailUtil.class);

    /**
     * 获取发送邮件的系统email对象
     * @param systemEmailDataList
     * @return
     */
    public static SystemEmail getSystemEmailToSend(List<SystemEmail> systemEmailDataList){
        int index = (int)(Math.random() * systemEmailDataList.size());
        logger.info("系统邮件发送邮箱共" + systemEmailDataList.size() + "个，此次使用第" + (index+1) + "个发送邮件");
        if (index > systemEmailDataList.size()){
            return systemEmailDataList.get(systemEmailDataList.size() - 1);
        }else {
            return systemEmailDataList.get(index);
        }
    }

    /**
     * 封装邮件VO
     * @param list  系统可用邮箱集合
     * @param orderId
     * @param recipient 邮件接收方,可为null,addressesArray不能为null
     * @param emailTitle    邮件标题
     * @param emailContent  邮件内容
     * @param bytes          附件字节数组，可为null
     * @param attachName    附件名字,可为null
     * @param addressesArray    多人接收数组，可为null,recipient不能为null
     * @return
     */
    public static EmailVO getEmailVO(List<SystemEmail> list, String orderId,String ellaUserId, String recipient, String emailTitle, String emailContent, byte[] bytes, String attachName, List<String> addressesArray) throws Exception{
        EmailVO emailVO = new EmailVO();
        if (list!=null && !list.isEmpty()){
            SystemEmail se = getSystemEmailToSend(list);
            emailVO.setPort(se.getPort());
            emailVO.setSystemEmail(se.getEmail());
            emailVO.setPassword(se.getPassword());
            emailVO.setEmailTitle(emailTitle);
            emailVO.setEmailContent(emailContent);
            emailVO.setOrderId(orderId);
            emailVO.setOrderApplyUserId(ellaUserId);
            if (StringUtils.isNotBlank(recipient)){
                emailVO.setRecipient(recipient);
            }else if (addressesArray != null && !addressesArray.isEmpty()){
                emailVO.setAddressesArray(addressesArray);
            }else {
                throw new RuntimeException("邮件接收方不能为空");
            }
            if (bytes != null && bytes.length > 0){
                emailVO.setBytes(bytes);
            }
            if (StringUtils.isNotBlank(attachName)){
                emailVO.setAttachName(attachName);
            }
        }else {
            throw new RuntimeException("无可用系统邮箱");
        }
        return emailVO;
    }
}
