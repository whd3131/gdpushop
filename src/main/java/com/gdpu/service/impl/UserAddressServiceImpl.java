package com.gdpu.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.UserAddress;
import com.gdpu.mapper.UserAddressMapper;
import com.gdpu.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-15
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    //保存地址
    @Override
    public boolean saveAddress(String addressInfo,String userId) {
        JSONObject jsonObject = JSONUtil.parseObj(addressInfo);
        String name = jsonObject.getStr("name");
        String address = jsonObject.getStr("address");
        String phone = jsonObject.getStr("phone");

        UserAddress userAddress = new UserAddress();
        userAddress.setName(name);
        userAddress.setAddress(address);
        userAddress.setPhone(phone);
        userAddress.setUserId(userId);

        int res = baseMapper.insert(userAddress);

        return res > 0;
    }


    //查询该用户所有的地址信息
    @Override
    public List<UserAddress> getAllAddressByUserId(String userId) {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<UserAddress> userAddressList = baseMapper.selectList(wrapper);
        return userAddressList;
    }

    //编辑地址
    @Override
    public boolean editAddress(String userId, String addressInfo) {
        JSONObject jsonObject = JSONUtil.parseObj(addressInfo);
        String name = jsonObject.getStr("name");
        String address = jsonObject.getStr("address");
        String phone = jsonObject.getStr("phone");
        String uaId = jsonObject.getStr("uaId");

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setName(name);
        userAddress.setAddress(address);
        userAddress.setPhone(phone);
        userAddress.setUaId(uaId);

        int res = baseMapper.updateById(userAddress);

        return res>0;
    }


}
