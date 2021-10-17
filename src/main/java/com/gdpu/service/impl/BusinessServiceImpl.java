package com.gdpu.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gdpu.entity.Business;
import com.gdpu.entity.BusinessGoods;
import com.gdpu.mapper.BusinessMapper;
import com.gdpu.service.BusinessGoodsService;
import com.gdpu.service.BusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

    @Autowired
    private BusinessGoodsService businessGoodsService;

    //添加一个商家
    @Override
    public boolean addBusiness(String businessInfo) {
        JSONObject jsonObject = JSONUtil.parseObj(businessInfo);
        String businessName = jsonObject.getStr("businessName");
        String businessContent = jsonObject.getStr("businessContent");

        Business business = new Business();
        business.setBusinessName(businessName);
        business.setBusinessContent(businessContent);
        int res = baseMapper.insert(business);

        return res>0;
    }

    //添加商家与商品的关联关系
    @Override
    public boolean addBusinessGoods(String bgInfo) {
        JSONObject jsonObject = JSONUtil.parseObj(bgInfo);
        String businessId = jsonObject.getStr("businessId");
        JSONArray goodsIdList = jsonObject.getJSONArray("goodsIdList");
        List<String> gidList = goodsIdList.toList(String.class);

        List<BusinessGoods> businessGoodsList = new ArrayList<>();
        for (String goodsId : gidList) {
            BusinessGoods bg = new BusinessGoods();
            bg.setBusinessId(businessId);
            bg.setGoodsId(goodsId);
            businessGoodsList.add(bg);
        }
        boolean res = businessGoodsService.saveOrUpdateBatch(businessGoodsList);
        return res;
    }

    //获得商家的信息，根据商品的ID
    @Override
    public Business getBusinessByGoodsId(String goodsId) {
        Business business = baseMapper.getBusinessByGoodsId(goodsId);
        return business;
    }
}
