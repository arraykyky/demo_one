package com.springcloud.book.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.OrderUserView;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.management.domain.vo.AdminOrderAnalysisTotalDataVO;
import com.springcloud.book.management.domain.vo.OrderUserViewVO;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
public interface OrderUserViewService extends IService<OrderUserView> {

    void getOrderAnalysisPage(Page page, OrderUserViewVO orderUserViewVO);

    void getAdminOrderAnalysisPage(Page page, OrderUserViewVO orderUserViewVO);

    AdminOrderAnalysisTotalDataVO getAdminOrderAnalysisTotalData(OrderUserViewVO orderUserViewVO);

    void getUnitOrderAnalysisDataPage(Page page, OrderUserViewVO orderUserViewVO);
}
