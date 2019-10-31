package com.springcloud.book.management.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.domain.ArticleOrders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.management.domain.vo.ArticleOrdersVO;
import com.springcloud.book.management.domain.vo.OrderLogsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-25
 */
public interface ArticleOrdersDao extends BaseMapper<ArticleOrders> {

    List<ArticleOrders> selectOrderPage(Page page,@Param("pd") ArticleOrdersVO articleOrdersVO);

    List<ArticleOrders> selectOrderLogesPage(Page page,@Param("pd") OrderLogsVO orderLogsVO);

    List<ArticleOrders> selectOrderLogesPage(@Param("pd") OrderLogsVO orderLogsVO);
}
