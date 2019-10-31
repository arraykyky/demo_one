package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.ArticleOrders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.BookStoreMyRequestOrdersVO;
import com.springcloud.book.foreign.domain.vo.OrderApplyVO;
import com.springcloud.book.foreign.domain.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-16
 */
public interface ArticleOrdersService extends IService<ArticleOrders> {
    //获取当前用户的订单数据
    void getCurrentUserRequestOrders(Page page, BookStoreMyRequestOrdersVO myRequestOrdersVO)throws Exception;
    //通过订单id获取订单详情
    ArticleOrders GetMyCurrentOrdersRequestInfo(BookStoreMyRequestOrdersVO requestOrdersVO) throws Exception;
    //通过医院id获取该医院的订单
    void getHospitalOrderPage(Page page, UserVO userVO)throws Exception;
    //通过医院id获取该医院每天的订单统计数据
    void getHospitalOrderReportPage(Page page, UserVO userVO)throws Exception;
    //添加订单申请
    void addOrderApply(String token, OrderApplyVO orderApplyVO) throws Exception;
    //添加订单数据
    void insertOrderApply(OrderApplyVO orderApplyVO);
    //通过文献id和申请邮箱账号获取订单信息
    ArticleOrders findOrderInfoByAbstractId(String abstractId,String orderApplyUserEmail);
}
