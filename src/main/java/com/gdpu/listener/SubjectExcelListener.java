package com.gdpu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.Category;
import com.gdpu.entity.categoryVo.SubjectData;
import com.gdpu.service.CategoryService;

/**
 * @author:王浩东
 * @createTime: 2021/10/12
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为SubjectExcelListener不交给spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    public CategoryService categoryService;
    public SubjectExcelListener() {}
    public SubjectExcelListener(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    //读取excel内容，一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new RuntimeException("文件数据为空");
        }

        System.out.println("SubjectExcelListener.invoke");

        //判断一级分类是否重复
        Category existOneSubject = this.existOneSubject(categoryService, subjectData.getOneSubjectName());
        if(existOneSubject == null) { //没有相同一级分类，进行添加
            existOneSubject = new Category();
            existOneSubject.setParentId("0");
            existOneSubject.setCatName(subjectData.getOneSubjectName());//一级分类名称
            existOneSubject.setCatValue(subjectData.getOneSubjectValue());//一级分类的值
            categoryService.save(existOneSubject);

            System.out.println("existOneSubject = " + existOneSubject);
        }

        //获取一级分类id值
        String pid = existOneSubject.getCatId();

        //添加二级分类
        //判断二级分类是否重复
        Category existTwoSubject = this.existTwoSubject(categoryService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new Category();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setCatName(subjectData.getTwoSubjectName());//二级分类名称
            existTwoSubject.setCatValue(subjectData.getTwoSubjectValue());//二级分类的值
            categoryService.save(existTwoSubject);

            System.out.println("existTwoSubject = " + existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private Category existOneSubject(CategoryService subjectService,String name) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("cat_name",name);
        wrapper.eq("parent_id","0");
        Category oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private Category existTwoSubject(CategoryService subjectService,String name,String pid) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("cat_name",name);
        wrapper.eq("parent_id",pid);
        Category twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }




    //读取完之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
