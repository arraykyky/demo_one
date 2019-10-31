package com.springcloud.book.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.OrderUserView;
import com.springcloud.book.management.dao.OrderUserViewDao;
import com.springcloud.book.management.domain.vo.AdminOrderAnalysisTotalDataVO;
import com.springcloud.book.management.domain.vo.OrderUserViewVO;
import com.springcloud.book.management.service.OrderUserViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
@Service
public class OrderUserViewServiceImpl extends ServiceImpl<OrderUserViewDao, OrderUserView> implements OrderUserViewService {

    @Override
    public void getOrderAnalysisPage(Page page, OrderUserViewVO orderUserViewVO) {
        List<OrderUserView> list = baseMapper.selectOrderAnalysisPage(page,orderUserViewVO);
        page.setRecords(list);
    }

    @Override
    public void getAdminOrderAnalysisPage(Page page, OrderUserViewVO orderUserViewVO) {
        List<PageData> list = baseMapper.selectAdminOrderAnalysisPage(page,orderUserViewVO);
        page.setRecords(list);
    }

    @Override
    public AdminOrderAnalysisTotalDataVO getAdminOrderAnalysisTotalData(OrderUserViewVO orderUserViewVO) {
        PageData pd = baseMapper.selectAdminOrderAnalysisTotalData(orderUserViewVO);
        return JSON.parseObject(JSON.toJSONString(pd),AdminOrderAnalysisTotalDataVO.class);
    }

    @Override
    public void getUnitOrderAnalysisDataPage(Page page, OrderUserViewVO orderUserViewVO) {
        List<PageData> list = baseMapper.selectUnitOrderAnalysisDataPage(page,orderUserViewVO);
        page.setRecords(list);
    }
}
