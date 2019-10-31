package com.springcloud.book.foreign.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.ArticleOrders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.domain.vo.OrderApplyVO;
import com.springcloud.book.foreign.domain.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-16
 */
public interface ArticleOrdersDao extends BaseMapper<ArticleOrders> {

    List<ArticleOrders> selectMyOrderList(Page page,@Param("pd") ArticleOrders articleOrders);

    List<PageData> selectHospitalOrderList(Page page, @Param("pd") UserVO userVO);

    List<PageData> selectHospitalOrderReportList(Page page, @Param("pd") UserVO userVO);

    void insertData(OrderApplyVO orderApplyVO);

    ArticleOrders selectOne(ArticleOrders articleOrders);
}
