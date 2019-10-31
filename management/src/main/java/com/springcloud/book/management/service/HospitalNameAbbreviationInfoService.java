package com.springcloud.book.management.service;

import com.springcloud.book.management.domain.HospitalNameAbbreviationInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-10-13
 */
public interface HospitalNameAbbreviationInfoService extends IService<HospitalNameAbbreviationInfo> {
    //添加单位信息
    void addHospitalNameAbbreviation(HospitalNameAbbreviationInfo hospitalNameAbbreviationInfo) throws Exception;
    //获取对应社会统一编码的单位
    HospitalNameAbbreviationInfo getHospitalNameAbbreviationByHiAbbreviation(HospitalNameAbbreviationInfo hospitalNameAbbreviationInfo) throws Exception;
    //单位数据集合
    List<HospitalNameAbbreviationInfo> getHospitalStatisticsType();
}
