package com.gdpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.BaseAddress;
import com.gdpu.entity.addressVo.OneAddressVo;
import com.gdpu.entity.addressVo.TriAddressVo;
import com.gdpu.entity.addressVo.TwoAddressVo;
import com.gdpu.mapper.BaseAddressMapper;
import com.gdpu.service.BaseAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-14
 */
@Service
public class BaseAddressServiceImpl extends ServiceImpl<BaseAddressMapper, BaseAddress> implements BaseAddressService {

    // 获得所有的城市地址
    @Override
    public List<OneAddressVo> getAllAddress() {
        //最终返回的集合
        List<OneAddressVo> oneAddressVoList = new ArrayList<>();

        //遍历找出所有parent_id为0的地址
        QueryWrapper<BaseAddress> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id",0);
        List<BaseAddress> oneAddressList = baseMapper.selectList(oneWrapper);

        for (BaseAddress oneAddress : oneAddressList) {

            //保存OneAddressVo
            OneAddressVo oneAddressVo = new OneAddressVo();
            oneAddressVo.setAddressId(oneAddress.getAddressId());
            oneAddressVo.setAddressTitle(oneAddress.getAddressTitle());

            //private List<TwoAddressVo> twoAddressVoList;
            List<TwoAddressVo> twoAddressVoList = new ArrayList<>();

            //查询该一级城市下所有的二级城市，并封装到twoAddressVoList
            String oneId = oneAddress.getAddressId();
            QueryWrapper<BaseAddress> twoWrapper = new QueryWrapper<>();
            twoWrapper.eq("parent_id",oneId);
            List<BaseAddress> twoAddressList = baseMapper.selectList(twoWrapper);

            for (BaseAddress twoAddress : twoAddressList) {

                //保存TwoAddressVo
                TwoAddressVo twoAddressVo = new TwoAddressVo();
                twoAddressVo.setAddressId(twoAddress.getAddressId());
                twoAddressVo.setAddressTitle(twoAddress.getAddressTitle());

                // private List<TriAddressVo> triAddressVoList;
                List<TriAddressVo> triAddressVoList = new ArrayList<>();
                //查询该二级城市下所有的三级城市，并封装到triAddressVoList
                String twoId = twoAddress.getAddressId();
                QueryWrapper<BaseAddress> triWrapper = new QueryWrapper<>();
                triWrapper.eq("parent_id",twoId);//triWrapper不小心写成twoWapper找半天bug
                List<BaseAddress> triAddressList = baseMapper.selectList(triWrapper);

                //因为可能存在直辖市的情况，这里需要判断
                if(triAddressList.size() > 0){
                    for (BaseAddress triAddress : triAddressList) {
                        //保存TriAddressVo
                        TriAddressVo triAddressVo = new TriAddressVo();
                        triAddressVo.setAddressId(triAddress.getAddressId());
                        triAddressVo.setAddressTitle(triAddress.getAddressTitle());

                        //把triAddressVo 加入到triAddressVoList中
                        triAddressVoList.add(triAddressVo);
                    }

                    //添加完3级城市后 把3级城市存到2级城市的属性中
                    twoAddressVo.setChildren(triAddressVoList);
                    //把twoAddressVo 加入到twoAddressVoList中
                    twoAddressVoList.add(twoAddressVo);

                }else{
                    triAddressVoList = null;
                    twoAddressVoList.add(twoAddressVo);
                }

            }

            //添加完2级城市后 把2级城市存到1级城市的属性中
            oneAddressVo.setChildren(twoAddressVoList);
            oneAddressVoList.add(oneAddressVo);
        }


        return oneAddressVoList;
    }
}
