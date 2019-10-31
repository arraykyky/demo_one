package com.springcloud.book.emailsystem.service.impl.deal;


import com.springcloud.book.emailsystem.domain.UserPointRecord;
import com.springcloud.book.emailsystem.enums.PointAllotTypeEnum;
import com.springcloud.book.emailsystem.util.DateUtil;
import com.springcloud.book.emailsystem.util.Tools;

import java.util.Date;

public class UserPointRecordDeal {

    /**
     * 对账信息
     * @param allotType     分配类型
     * @param allotPoint    分配点数
     * @param userId         分配用户id
     * @param historyPoint  用户剩余可用点数
     * @param enablePoin    分配后用户可用点数 = historyPoint + allotPoint
     * @return
     */
    public static UserPointRecord getUserPointRecord(Integer allotType,Integer allotPoint,String userId,Integer historyPoint,Integer enablePoin){
        UserPointRecord userPointRecord = new UserPointRecord();
        userPointRecord.setRecordId(Tools.getUUID());
        userPointRecord.setUserId(userId);
        userPointRecord.setRecordTime(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        userPointRecord.setConsumeType(allotType);
        if (allotType == PointAllotTypeEnum.P_RECHARGE.getDealNum()){
            userPointRecord.setAddMoney(allotPoint);
        }
        userPointRecord.setHistoryPointCount(historyPoint);
        if (allotType == PointAllotTypeEnum.P_CONSUME.getDealNum()){
            userPointRecord.setConsumePointCount(-allotPoint);
        }else {
            userPointRecord.setConsumePointCount(allotPoint);
        }
        userPointRecord.setEnableUsePointNumber(enablePoin);
        return userPointRecord;
    }

}
