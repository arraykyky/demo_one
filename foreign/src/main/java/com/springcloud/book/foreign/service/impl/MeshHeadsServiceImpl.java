package com.springcloud.book.foreign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.MeshHeads;
import com.springcloud.book.foreign.dao.MeshHeadsDao;
import com.springcloud.book.foreign.domain.vo.MeshHeadsVO;
import com.springcloud.book.foreign.service.MeshHeadsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-08
 */
@Service
public class MeshHeadsServiceImpl extends ServiceImpl<MeshHeadsDao, MeshHeads> implements MeshHeadsService {

    @Override
    public List<MeshHeads> getMeshHeadsMenuByMenuParentId(MeshHeadsVO meshHeadsVO) {
        return baseMapper.selectMeshHeadsMenuByMenuParentId(meshHeadsVO);
    }

    @Override
    public MeshHeads getMeshHeadsInfoById(MeshHeadsVO meshHeadsVO) {
        return baseMapper.selectById(meshHeadsVO);
    }

    @Override
    public void getSearchMeshHeads(Page page, MeshHeadsVO meshHeadsVO) {
        List<MeshHeads> list = baseMapper.selectSearchMeshHeads(page,meshHeadsVO);
        page.setRecords(list);
    }
}
