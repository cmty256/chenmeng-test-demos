package com.chenmeng.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenmeng.project.model.entity.TblUser;

/**
* @author cmty256
* @description 针对表【tbl_user】的数据库操作Service
* @createDate 2023-12-01 23:59:11
*/
public interface TblUserService extends IService<TblUser> {

    void testUpdate();
}
