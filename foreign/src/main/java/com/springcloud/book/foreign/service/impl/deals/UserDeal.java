package com.springcloud.book.foreign.service.impl.deals;

import com.springcloud.book.foreign.domain.vo.UserVO;

import java.util.HashMap;
import java.util.Map;

public class UserDeal {

    /**
     * 封装Ella用户信息
     * @param userVO
     * @return
     */
    public static Map<String,String> forigenUser2EllaUser(UserVO userVO){
        Map<String,String> map = new HashMap<>();
        //对应Ella中的UserBase对象中的属性
        String userRealName = userVO.getUserRealName();
        String userPhone = userVO.getUserPhone();
        String userEmail = userVO.getUserEmail();
        //对应Ella中的UserOrg对象中的orgId
        String deptId = userVO.getDeptId();
        //Ella中用户主键
        String id = userVO.getEllaUserId();
        if (userRealName!=null){
            map.put("realName",userRealName);
        }
        if (userPhone!=null){
            map.put("telephone",userPhone);
        }
        if (userEmail!=null){
            map.put("email",userEmail);
        }
        if (deptId!=null){
            map.put("orgId",deptId);
        }
        //给UserBase对象id赋值
        map.put("id",id);
        //给UserOrg对象id赋值
        map.put("userId",id);
        return map;
    }

//    public static void addAlltoDeal(PageData pageData, UserService userService, UserPointRecordService userPointRecordService){
//        PageData pd = userService.findUserInfoById(pageData);
//        User user = JSON.parseObject(JSON.toJSONString(pd),User.class);
//        //（1点数分配；2金币充值；-1点数消费）
//        int consumeType = -1;
//        int addMoney = 0;
//        //点数金币充值/点数分配/点数消费的值
//        int consumeCount = pageData.getInt("consumeCount");
//        if (consumeCount==0){
//            throw new RuntimeException("消费点数不能为:0");
//        }else {
//            //addMoney存在则为金币充值,否则就是点数分配/点数消费
//            if (pageData.containsKey("addMoney")){
//                consumeType = 2;
//                addMoney = pageData.getInt("addMoney");
//            }else {
//                //如果消费点数>0则为点数分配，否则为点数消费
//                if (consumeCount>0){
//                    consumeType = 1;
//                    //获取分配账号信息
//                    PageData uPD = userService.findUserInfoByEllaUserId(pageData);
//                    User u = JSON.parseObject(JSON.toJSONString(uPD),User.class);
//                    if (consumeCount<u.getEnableUsePointNumber()){
//                        //记录分配账号对账
//                        int historyPointCount = u.getEnableUsePointNumber();
//                        int enableUsePointNumber = historyPointCount - consumeCount;
//                        PageData pPD = new PageData();
//                        pPD.put("recordId", Tools.getUUID());
//                        pPD.put("userId",u.getUserId());
//                        pPD.put("consumeType",consumeType);
//                        pPD.put("addMoney",addMoney);
//                        pPD.put("recordTime",DateUtil.formatCurrentDateTime());
//                        pPD.put("historyPointCount",historyPointCount);
//                        pPD.put("consumePointCount",-consumeCount);
//                        pPD.put("enableUsePointNumber",enableUsePointNumber);
//                        userPointRecordService.addUserPointRecord(pPD);
//                        //更新分配账号可用点数
//                        userService.updateUserByUserId(pPD);
//                    }else{
//                        throw new RuntimeException("剩余可用点数:"+u.getEnableUsePointNumber()+"点,请重新分配或联系客服管理员");
//                    }
//                }
//            }
//            int historyPointCount = (user.getEnableUsePointNumber() != null && !user.getEnableUsePointNumber().equals("")) ? user.getEnableUsePointNumber() : 0;
//            int enableUsePointNumber = historyPointCount + consumeCount;
//            pageData.put("enableUsePointNumber",enableUsePointNumber);
//            //封装对账对象
//            PageData pPD = new PageData();
//            pPD.put("recordId", Tools.getUUID());
//            pPD.put("userId",user.getUserId());
//            pPD.put("consumeType",consumeType);
//            pPD.put("addMoney",addMoney);
//            pPD.put("recordTime",DateUtil.formatCurrentDateTime());
//            pPD.put("historyPointCount",historyPointCount);
//            pPD.put("consumePointCount",consumeCount);
//            pPD.put("enableUsePointNumber",enableUsePointNumber);
//            userPointRecordService.addUserPointRecord(pPD);
//        }
//    }
}
