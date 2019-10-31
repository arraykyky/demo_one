package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.MeshHeads;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.MeshHeadsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-08
 */
public interface MeshHeadsService extends IService<MeshHeads> {
    //通过父节点id获取该节点下的子节点
    List<MeshHeads> getMeshHeadsMenuByMenuParentId(MeshHeadsVO meshHeadsVO);
    //通过主题词菜单主键id获取菜单信息
    MeshHeads getMeshHeadsInfoById(MeshHeadsVO meshHeadsVO);
    //通过主题词检索获取主题词列表信息
    void getSearchMeshHeads(Page page, MeshHeadsVO meshHeadsVO);
}
