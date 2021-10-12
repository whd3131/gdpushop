package com.gdpu.controller;


import com.gdpu.entity.categoryVo.CategoryVo;
import com.gdpu.service.CategoryService;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whd
 * @since 2021-10-10
 */
@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/getCategory")
    public R  getCategory(){
        List<CategoryVo> categoryVoList = categoryService.getCategory();
        return R.ok().data("categoryVoList",categoryVoList);
    }

    //添加商品分类：上传、读取excel的商品分类信息
    @PostMapping("/addCategory")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        categoryService.addCategory(file,categoryService);
        return R.ok();
    }

    //删除分类节点
    @PostMapping("/delCategory")
    public R delCategory(@RequestBody String delCatIdArray){

        boolean res = categoryService.delCategory(delCatIdArray);
        return res?R.ok():R.error();
    }

}

