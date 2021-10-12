package com.gdpu.gdpushop.easyexcel;

import com.alibaba.excel.EasyExcel;

/**
 * @author:王浩东
 * @createTime: 2021/10/12
 */
public class TestEasyExcelRead {
    public static void main(String[] args) {

        //实现excel读操作
        String filename = "F:\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
