package com.springcloud.book.foreign.controller;

import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.domain.ArticleOrders;
import com.springcloud.book.foreign.domain.FullTextUploadFlow;
import com.springcloud.book.foreign.domain.vo.OrderApplyVO;
import com.springcloud.book.foreign.enums.OrderTypeEnum;
import com.springcloud.book.foreign.service.ArticleOrdersService;
import com.springcloud.book.foreign.service.FullTextUploadFlowService;
import com.springcloud.book.foreign.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private ArticleOrdersService ordersService;

    @Autowired
    private FullTextUploadFlowService fullTextUploadFlowService;

    /**
     * 订单重复申请验证
     * 邮件状态(0001发送失败;0000未发送，默认值;1111发送成功)
     * 订单状态(0010被拒绝;0000申请中，默认值;0001处理中;0011已完成;)
     * @param abstractId
     * @param orderApplyUserEmail
     * @return
     */
    @GetMapping(value = "/repeat_apply_check")
    public Object repeatApplyCheck(String token,String abstractId,String orderApplyUserEmail){
        PageData pageData = new PageData();
        ArticleOrders orderInfo = ordersService.findOrderInfoByAbstractId(abstractId,orderApplyUserEmail);
        if(orderInfo != null){
            String os = orderInfo.getOrderState();
            String at = orderInfo.getOrderApplyTime();
            long time = System.currentTimeMillis() - DateUtil.parseDateTime(at).getTime();
            if (time < (30 * 60 * 1000)){
                if (OrderTypeEnum.O_APPLYING.getDealNum().equals(os) || OrderTypeEnum.O_DEALING.getDealNum().equals(os)){
                    pageData.put(OverallConfig.CODE,"201");
                    pageData.put(OverallConfig.MSG,"您的订单申请已被受理，请耐心等待");
                }
                if (OrderTypeEnum.O_FINISH.getDealNum().equals(os)){
                    FullTextUploadFlow full = fullTextUploadFlowService.findFullTextInfoByAbstractId(abstractId);
                    if (full != null){
                        pageData.put(OverallConfig.CODE,"202");
                        pageData.put(OverallConfig.MSG,"您的订单申请已完成，若无收到订单全文邮件，请查看此链接#URL#查看/下载全文");
                        //此处的url的api使用管理端的api(management路由路径，http://127.0.0.1:5001/management/text_full/037062310.1021bi500853s25140443.pdf?token=e99064a83fa76232f8b825c07ad08ef2)
                        pageData.put("filePathUrl","/"+full.getFilePathUrl()+"?token="+token);
                    }
                }
            }else {
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            }
        }else {
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
        }
        return pageData;
    }

    /**
     * 添加用户文献订单申请
     * orderApplyVO 订单申请VO  包含必要参数:  abstractId 申请文献摘要id
     *                                         orderApplyUserEmail 订单申请人邮箱
     * @return
     */
    @PostMapping("/apply")
    public Object addApplyArticleOrder(String token,OrderApplyVO orderApplyVO) {
        PageData pageData = new PageData();
        try {
            String userId = (String) redisServer.get(Constants.getUserModuleCacheKeyForeignUserId(token));
            orderApplyVO.setUserId(userId);
            ordersService.addOrderApply(token,orderApplyVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        } catch (Exception e) {
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
            logger.error(e.getMessage(),e);
        }
        return pageData;
    }
}
