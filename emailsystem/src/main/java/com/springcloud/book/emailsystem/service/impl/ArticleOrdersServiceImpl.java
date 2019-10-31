package com.springcloud.book.emailsystem.service.impl;

import com.springcloud.book.emailsystem.bean.EmailVO;
import com.springcloud.book.emailsystem.config.OverallConfig;
import com.springcloud.book.emailsystem.domain.ArticleOrders;
import com.springcloud.book.emailsystem.dao.ArticleOrdersDao;
import com.springcloud.book.emailsystem.domain.User;
import com.springcloud.book.emailsystem.domain.UserPointRecord;
import com.springcloud.book.emailsystem.enums.OrderEmailStateEnum;
import com.springcloud.book.emailsystem.enums.PointAllotTypeEnum;
import com.springcloud.book.emailsystem.service.ArticleOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.emailsystem.service.UserPointRecordService;
import com.springcloud.book.emailsystem.service.UserService;
import com.springcloud.book.emailsystem.service.impl.deal.UserPointRecordDeal;
import com.springcloud.book.emailsystem.util.email.SendEmail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-10-09
 */
@Service
public class ArticleOrdersServiceImpl extends ServiceImpl<ArticleOrdersDao, ArticleOrders> implements ArticleOrdersService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPointRecordService userPointRecordService;

    //附件邮件
    @Override
    @Transactional
    public void sendEmailAttach(EmailVO emailVO) throws Exception {
        //发送邮件
        String emailState = null;
        String emailSendError = "无";
        ArticleOrders articleOrders = new ArticleOrders();
        articleOrders.setOrderId(emailVO.getOrderId());
        try {
            //如果启用借读点，则执行借读点相关业务
            if (OverallConfig.ENABLE_BORROW_POINT){
                //获取用户信息
                User user = userService.getUserByEllaUserId(emailVO.getOrderApplyUserId());
                if (user != null){
                    if (user.getEnableUsePointNumber() >= 1){
                        //扣除借读点
                        User daoUser = new User();
                        daoUser.setUserId(user.getUserId());
                        daoUser.setEnableUsePointNumber(user.getEnableUsePointNumber() - 1);
                        userService.updateById(daoUser);
                        //借读点流水记录
                        UserPointRecord userPointRecord = UserPointRecordDeal.getUserPointRecord(PointAllotTypeEnum.P_CONSUME.getDealNum(),1,user.getUserId(),user.getEnableUsePointNumber(),daoUser.getEnableUsePointNumber());
                        userPointRecordService.addUserPointRecord(userPointRecord);
                        //发送邮件
                        InputStreamSource inputStreamSource = new ByteArrayResource(emailVO.getBytes());
                        SendEmail.sendEmail(emailVO.getPort(),emailVO.getSystemEmail(),emailVO.getPassword(),emailVO.getRecipient(),inputStreamSource,emailVO.getAttachName(),emailVO.getEmailTitle(),emailVO.getEmailContent());
                        emailState = OrderEmailStateEnum.E_SUCCESS.getDealNum();
                    }else {
                        throw new RuntimeException("借读点不足");
                    }
                }else {
                    throw new RuntimeException("用户信息异常");
                }
            }else {
                //发送邮件
                InputStreamSource inputStreamSource = new ByteArrayResource(emailVO.getBytes());
                SendEmail.sendEmail(emailVO.getPort(),emailVO.getSystemEmail(),emailVO.getPassword(),emailVO.getRecipient(),inputStreamSource,emailVO.getAttachName(),emailVO.getEmailTitle(),emailVO.getEmailContent());
                emailState = OrderEmailStateEnum.E_SUCCESS.getDealNum();
            }
        } catch (Exception e) {
            emailState = OrderEmailStateEnum.E_ERROR.getDealNum();
            emailSendError = e.getMessage();
        }
        articleOrders.setEmailState(emailState);
        if (StringUtils.isNotBlank(emailSendError)){
            articleOrders.setEmailFailDesc(emailSendError);
        }
        baseMapper.updateById(articleOrders);
    }

    //多人接收邮件
    @Override
    @Transactional
    public void sendEmailMoreRecipient(EmailVO emailVO) throws Exception {
        //发送邮件
        String emailState = null;
        String emailSendError = "无";
        ArticleOrders articleOrders = new ArticleOrders();
        articleOrders.setOrderId(emailVO.getOrderId());
        try {
            List<String> addresses = emailVO.getAddressesArray();
            if (addresses.isEmpty()){
                new RuntimeException("参数不完整");
            }
            Address[] addressesArray = new Address[addresses.size()];
            ArrayList<Address> addressList = new ArrayList();
            for (String str: addresses){
                if (str != null && !"".equals(str)){
                    Address address = new InternetAddress(str);
                    addressList.add(address);
                }
            }
            SendEmail.sendEmail(emailVO.getPort(), emailVO.getSystemEmail(), emailVO.getPassword(), addressList.toArray(addressesArray), emailVO.getEmailTitle(), emailVO.getEmailContent());
            emailState = OrderEmailStateEnum.E_SUCCESS.getDealNum();
        } catch (Exception e) {
            emailState = OrderEmailStateEnum.E_ERROR.getDealNum();
            emailSendError = e.getMessage();
        }
        articleOrders.setEmailState(emailState);
        if (StringUtils.isNotBlank(emailSendError)){
            articleOrders.setEmailFailDesc(emailSendError);
        }
        baseMapper.updateById(articleOrders);
    }

    //文本邮件
    @Override
    @Transactional
    public void sendEmailText(EmailVO emailVO) throws Exception {
        //发送邮件
        String emailState = null;
        String emailSendError = "无";
        ArticleOrders articleOrders = new ArticleOrders();
        articleOrders.setOrderId(emailVO.getOrderId());
        try {
            SendEmail.sendEmail(emailVO.getPort(), emailVO.getSystemEmail(), emailVO.getPassword(), emailVO.getRecipient(), emailVO.getEmailTitle(), emailVO.getEmailContent());
            emailState = OrderEmailStateEnum.E_SUCCESS.getDealNum();
        } catch (Exception e) {
            emailState = OrderEmailStateEnum.E_ERROR.getDealNum();
            emailSendError = e.getMessage();
        }
        articleOrders.setEmailState(emailState);
        if (StringUtils.isNotBlank(emailSendError)){
            articleOrders.setEmailFailDesc(emailSendError);
        }
        baseMapper.updateById(articleOrders);
    }
}
