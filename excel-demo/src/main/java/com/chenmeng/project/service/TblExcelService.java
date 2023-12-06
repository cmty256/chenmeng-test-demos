package com.chenmeng.project.service;

import com.chenmeng.project.model.entity.TblExcel;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
* @author 沉梦听雨
* @description 针对表【tbl_excel(Excel测试表)】的数据库操作Service
* @createDate 2023-11-09 15:20:40
*/
public interface TblExcelService extends IService<TblExcel> {

    void export(HttpServletResponse response);
}
