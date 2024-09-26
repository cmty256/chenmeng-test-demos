package com.chenmeng.project.service;

import com.chenmeng.project.model.dto.FileDTO;
import com.chenmeng.project.model.entity.ExcelDO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
* @author chenmeng
* @description 针对表【tbl_excel(Excel测试表)】的数据库操作Service
* @createDate 2023-11-09 15:20:40
*/
public interface ExcelService extends IService<ExcelDO> {

    void export(HttpServletResponse response);

    boolean importInfo(FileDTO dto);
}
