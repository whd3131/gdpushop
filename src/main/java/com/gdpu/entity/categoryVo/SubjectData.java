package com.gdpu.entity.categoryVo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 商品分类所需的excel实体类
 * @author:王浩东
 * @createTime: 2021/10/12
 */
@Data
public class SubjectData {

    /**
     * 一级分类
     */
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    /**
     * 一级分类的值
     */
    @ExcelProperty(index = 1)
    private String oneSubjectValue;

    /**
     * 二级分类
     */
    @ExcelProperty(index = 2)
    private String twoSubjectName;

    /**
     * 二级分类的值
     */
    @ExcelProperty(index = 3)
    private String twoSubjectValue;
}
