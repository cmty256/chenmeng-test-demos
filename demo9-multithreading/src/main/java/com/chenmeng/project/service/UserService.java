package com.chenmeng.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenmeng.common.model.entity.UserDO;
import com.chenmeng.project.vo.UserImportExcelVO;

import java.util.List;

/**
* @author chenmeng
*/
public interface UserService extends IService<UserDO> {

    Boolean importExcel(List<UserImportExcelVO> importList);
}
