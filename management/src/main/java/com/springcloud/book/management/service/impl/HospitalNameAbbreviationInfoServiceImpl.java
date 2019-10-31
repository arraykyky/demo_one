package com.springcloud.book.management.service.impl;

import com.springcloud.book.management.domain.HospitalNameAbbreviationInfo;
import com.springcloud.book.management.dao.HospitalNameAbbreviationInfoDao;
import com.springcloud.book.management.service.HospitalNameAbbreviationInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-10-13
 */
@Service
public class HospitalNameAbbreviationInfoServiceImpl extends ServiceImpl<HospitalNameAbbreviationInfoDao, HospitalNameAbbreviationInfo> implements HospitalNameAbbreviationInfoService {

    @Override
    public void addHospitalNameAbbreviation(HospitalNameAbbreviationInfo hospitalNameAbbreviationInfo) throws Exception{
        HospitalNameAbbreviationInfo daoHospitalNameAbbreviationInfo = this.getHospitalNameAbbreviationByHiAbbreviation(hospitalNameAbbreviationInfo);
        if (daoHospitalNameAbbreviationInfo == null){
            baseMapper.insert(hospitalNameAbbreviationInfo);
        }else {
            throw new RuntimeException("该医院/单位信息已存在，不能重复添加");
        }
    }

    @Override
    public HospitalNameAbbreviationInfo getHospitalNameAbbreviationByHiAbbreviation(HospitalNameAbbreviationInfo hospitalNameAbbreviationInfo) throws Exception {
        return baseMapper.selectHospitalNameAbbreviationByHiAbbreviation(hospitalNameAbbreviationInfo);
    }

    @Override
    public List<HospitalNameAbbreviationInfo> getHospitalStatisticsType() {
        return baseMapper.selectList(null);
    }
}
