package com.springcloud.book.foreign.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.MeshHeads;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.domain.vo.MeshHeadsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-08
 */
public interface MeshHeadsDao extends BaseMapper<MeshHeads> {

    List<MeshHeads> selectMeshHeadsMenuByMenuParentId(MeshHeadsVO meshHeadsVO);

    List<MeshHeads> selectSearchMeshHeads(Page page, @Param("pd") MeshHeadsVO meshHeadsVO);
}
