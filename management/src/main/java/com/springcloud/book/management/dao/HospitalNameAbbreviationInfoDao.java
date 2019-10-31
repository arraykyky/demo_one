package com.springcloud.book.management.dao;

import com.springcloud.book.management.domain.HospitalNameAbbreviationInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-10-13
 */
public interface HospitalNameAbbreviationInfoDao extends BaseMapper<HospitalNameAbbreviationInfo> {

    HospitalNameAbbreviationInfo selectHospitalNameAbbreviationByHiAbbreviation(HospitalNameAbbreviationInfo hospitalNameAbbreviationInfo);
}
