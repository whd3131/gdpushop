package com.gdpu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdpu.entity.vo.CartVo;

import java.util.List;

public interface CartVoMapper extends BaseMapper<CartVo> {


    List<CartVo> getUserCart(String userId);
}
