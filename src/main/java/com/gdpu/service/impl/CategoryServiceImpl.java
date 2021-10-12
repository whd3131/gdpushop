package com.gdpu.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.Category;
import com.gdpu.entity.categoryVo.CategoryVo;
import com.gdpu.entity.categoryVo.SubCategoryVo;
import com.gdpu.entity.categoryVo.SubjectData;
import com.gdpu.listener.SubjectExcelListener;
import com.gdpu.mapper.CategoryMapper;
import com.gdpu.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    //获得所有分类
    @Override
    public List<CategoryVo> getCategory() {
        List<CategoryVo> categoryVoList = new ArrayList<>();

        //查出所有的父分类，他们的parent_id为0
        QueryWrapper<Category> categoryQueryWrapper1 = new QueryWrapper<>();
        categoryQueryWrapper1.eq("parent_id",0);
        List<Category> categoryList = baseMapper.selectList(categoryQueryWrapper1);

        for (Category category : categoryList) {
            CategoryVo fu = new CategoryVo();
            fu.setCat_id(category.getCatId());
            fu.setLabel(category.getCatName());
            fu.setValue(category.getCatValue());

            String parent_id = category.getCatId();
            //查询出该父类下所有子类
            QueryWrapper<Category> categoryQueryWrapper2 = new QueryWrapper<>();
            categoryQueryWrapper2.eq("parent_id",parent_id);
            List<Category> ziCategory = baseMapper.selectList(categoryQueryWrapper2);
            List<SubCategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category zi : ziCategory) {
                SubCategoryVo subCategoryVo = new SubCategoryVo();
                subCategoryVo.setCat_id(zi.getCatId());
                subCategoryVo.setLabel(zi.getCatName());
                subCategoryVo.setValue(zi.getCatValue());
                subCategoryVo.setParentId(category.getCatId());

                subCategoryVoList.add(subCategoryVo);
            }
            fu.setChildren(subCategoryVoList);
            categoryVoList.add(fu);
        }

        return categoryVoList;
    }

    //添加商品分类：上传、读取excel的商品分类信息
    @Override
    public void addCategory(MultipartFile file, CategoryService categoryService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(categoryService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除商品分类
    @Override
    public boolean delCategory(String delCatIdArray) {
        JSONObject jsonObject = JSONUtil.parseObj(delCatIdArray);
        JSONArray idArray = jsonObject.getJSONArray("delCatIdArray");
        for (Object o : idArray) {
            String catId = o.toString();
            //删除分类
            QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
            categoryQueryWrapper.eq("cat_id",catId);
            int res = baseMapper.delete(categoryQueryWrapper);
            if(res<1){
                return false;
            }
        }
        return true;
    }

}
