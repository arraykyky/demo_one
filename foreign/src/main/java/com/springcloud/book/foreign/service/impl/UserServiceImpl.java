package com.springcloud.book.foreign.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.foreign.config.HttpClientService;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.dao.UserDao;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.domain.vo.UserVO;
import com.springcloud.book.foreign.service.UserService;
import com.springcloud.book.foreign.service.impl.deals.UserDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-07-30
 */
@Service("userServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private HttpClientService httpClientService;

    @Override
    public User getUserByEllaUserId(UserVO userVO) throws Exception{
        return baseMapper.selectUserByEllaUserId(userVO);
    }

    @Override
    public List<Map> getUserUnitList(UserVO userVO) throws Exception{
        PageData pageData = new PageData();
        pageData.put("id",userVO.getEllaUserId());
        String treeStr = httpClientService.doPost(OverallConfig.USER_UNIT_TREE, pageData);
        return JSON.parseObject(treeStr,new ArrayList<Map>().getClass());
    }

    @Override
    @Transactional
    public void updateUserById(UserVO userVO) throws Exception{
        baseMapper.updateById(userVO);
        //更新Ella上对应用户的信息
        Map<String, String> map = UserDeal.forigenUser2EllaUser(userVO);
        try {
            String flag = httpClientService.doPost(OverallConfig.UPDATA_USER,map);
            //更新失败
            if (flag.equals("0")){
                throw new RuntimeException("Ella用户信息更新失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    @Override
    public boolean passWordCheck(UserVO userVO) throws Exception{
        User user = baseMapper.selectUserByEllaUserId(userVO);
        if (user != null && user.getUserPassword().equals(userVO.getUserPassword())){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void modifyPassWord(UserVO userVO) throws Exception{
        //本地用户信息更新
        baseMapper.updatePassWordByElleUserId(userVO);
        //Ella用户信息更新
        try {
            PageData pageData = new PageData();
            pageData.put("password",userVO.getUserPassword());
            pageData.put("id",userVO.getEllaUserId());
            String flag = httpClientService.doPost(OverallConfig.MOTIFY_PWD,pageData);
            //更新失败
            if (flag==null || flag.equals("0")){
                throw new RuntimeException("Ella更新失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    @Override
    public User getUserByOpenId(User u) {
        return baseMapper.selectUserByOpenId(u);
    }

    @Override
    public User getUserByPhoneNumber(User u) {
        return baseMapper.selectUserByPhoneNumber(u);
    }

    @Override
    public Integer updateUser(User user) {
        return baseMapper.updateById(user);
    }
}
