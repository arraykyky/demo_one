package com.springcloud.book.management.run;

import com.springcloud.book.management.config.OverallConfig;
import com.springcloud.book.management.domain.ArticleOrders;
import com.springcloud.book.management.domain.ArticleOrdersFlowDetails;
import com.springcloud.book.management.domain.vo.EmailSendVO;
import com.springcloud.book.management.domain.vo.EmailVO;
import com.springcloud.book.management.enums.OrderEmailStateEnum;
import com.springcloud.book.management.enums.OrderTypeEnum;
import com.springcloud.book.management.remote.EmailSendRemote;
import com.springcloud.book.management.service.ArticleOrdersFlowDetailsService;
import com.springcloud.book.management.service.ArticleOrdersService;
import com.springcloud.book.management.service.impl.ArticleOrdersServiceImpl;
import com.springcloud.book.management.util.DateUtil;
import com.springcloud.book.management.util.Tools;

import java.util.Date;

public class EmailSendRunnable implements Runnable {

    private ArticleOrdersService articleOrdersService;
    private ArticleOrdersFlowDetailsService articleOrdersFlowDetailsService;
    private EmailSendRemote emailSendRemote;
    private ArticleOrders articleOrders;
    private EmailVO emailVO;
    private String token;

    public EmailSendRunnable(ArticleOrdersServiceImpl articleOrdersService, ArticleOrdersFlowDetailsService articleOrdersFlowDetailsService, EmailSendRemote emailSendRemote, ArticleOrders articleOrders, EmailVO emailVO, String token) {
        this.articleOrdersService = articleOrdersService;
        this.articleOrdersFlowDetailsService = articleOrdersFlowDetailsService;
        this.emailSendRemote = emailSendRemote;
        this.articleOrders = articleOrders;
        this.emailVO = emailVO;
        this.token = token;
    }

    //解读点书停用，需要时再添加
    @Override
    public void run() {
        //时间
        long time = System.currentTimeMillis();
        String applyTime = articleOrders.getOrderApplyTime();
        //声明数据更新对象
        ArticleOrders daoArticleOrders = new ArticleOrders();
        daoArticleOrders.setOrderId(articleOrders.getOrderId());
        daoArticleOrders.setOrderState(OrderTypeEnum.O_FINISH.getDealNum());
        daoArticleOrders.setOrderFinalActionTime(DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss"));
        daoArticleOrders.setOrderEfficiencyTimes(time - DateUtil.parseDateTime(applyTime).getTime());
        daoArticleOrders.setEmailState(OrderEmailStateEnum.E_ERROR.getDealNum());
        daoArticleOrders.setEmailFailDesc("服务请求异常");
        //更新数据
        articleOrdersService.updateOrderById(daoArticleOrders);
        //添加订单流程
        ArticleOrdersFlowDetails flowDetails = new ArticleOrdersFlowDetails();
        flowDetails.setFlowDetailId(Tools.getUUID());
        flowDetails.setOrderId(articleOrders.getOrderId());
        flowDetails.setOrderAcceptAdminId(articleOrders.getOrderAcceptAdminId());
        flowDetails.setOrderAcceptAdminName(articleOrders.getOrderAcceptAdminName());
        flowDetails.setFlowDetailDealSign(daoArticleOrders.getOrderState());
        flowDetails.setFlowDetailDealNote(OrderTypeEnum.getCodeByNum(flowDetails.getFlowDetailDealSign()));
        flowDetails.setFlowDetailCreateTime(DateUtil.formatCurrentDateTime(time));
        articleOrdersFlowDetailsService.addOrderFlowDetailsApply(flowDetails);
        //发送邮件
        emailSendRemote.sendEmailAttach(this.token, emailVO);
    }
}
