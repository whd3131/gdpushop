package com.gdpu.service;

import com.gdpu.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdpu.entity.categoryVo.CategoryVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-10
 */
public interface CategoryService extends IService<Category> {

    //获得所有分类
    List<CategoryVo> getCategory();

    //添加商品分类：上传、读取excel的商品分类信息
    void addCategory(MultipartFile file, CategoryService categoryService);

    // 删除商品分类
    boolean delCategory(String delCatIdArray);
}
