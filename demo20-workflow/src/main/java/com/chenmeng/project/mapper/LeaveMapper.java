package com.chenmeng.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenmeng.project.entity.LeaveDO;

/**
 * 请假单 Mapper（MyBatis-Plus BaseMapper）。
 *
 * <p>继承 {@link BaseMapper} 后即可拥有 insert / updateById / selectById / selectBatchIds
 * / selectList 等 CRUD 能力，零 SQL。
 *
 * <p>与 yudao 的对应关系：yudao 的业务模块同样继承 BaseMapperX（yudao 对 BaseMapper 的增强），
 * 本教学 demo 为聚焦工作流，直接使用原生 BaseMapper。
 *
 * <p>注意：本 Mapper 只负责业务表 leave_apply；
 * Flowable 引擎的 ACT_* 表由引擎内部私有的 MyBatis 映射访问，业务代码不允许直接 SQL 改 ACT_* 表
 * （绕过引擎直接改运行时表会破坏引擎状态机，这是工作流使用的一条铁律）。
 *
 * @author chenmeng
 */
public interface LeaveMapper extends BaseMapper<LeaveDO> {
}
