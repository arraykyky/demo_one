package com.springcloud.book.management.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.OrderUserView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.management.domain.vo.OrderUserViewVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
public interface OrderUserViewDao extends BaseMapper<OrderUserView> {

    List<OrderUserView> selectOrderAnalysisPage(Page page,@Param("pd") OrderUserViewVO orderUserViewVO);

    List<PageData> selectAdminOrderAnalysisPage(Page page,@Param("pd") OrderUserViewVO orderUserViewVO);

    PageData selectAdminOrderAnalysisTotalData(OrderUserViewVO orderUserViewVO);

    List<PageData> selectUnitOrderAnalysisDataPage(Page page, @Param("pd") OrderUserViewVO orderUserViewVO);
}
