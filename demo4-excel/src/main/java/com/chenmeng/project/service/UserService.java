package com.chenmeng.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenmeng.common.model.base.BasePageDTO;
import com.chenmeng.common.model.entity.UserDO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author chenmeng
 */
public interface UserService extends IService<UserDO> {

    void exportByRepeat(BasePageDTO pageReqVO, HttpServletResponse response);
}
