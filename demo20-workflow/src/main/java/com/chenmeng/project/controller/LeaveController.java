package com.chenmeng.project.controller;

import com.chenmeng.project.common.CommonResult;
import com.chenmeng.project.service.LeaveService;
import com.chenmeng.project.vo.LeaveCreateReqVO;
import com.chenmeng.project.vo.LeaveRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 请假单接口：发起请假 + 请假单列表。
 *
 * <p>推荐体验顺序（配合 README 的"推荐体验步骤"）：
 * <ol>
 *     <li>POST /leave/create：发起一张 day=2 的请假单（短路径，只走领导审批 → HR 备案）；</li>
 *     <li>GET /leave/list：看到 status=1 审批中；</li>
 *     <li>（去 TaskController 查待办、审批）；</li>
 *     <li>每审批一步回来刷一次 /leave/list，观察 status 何时从 1 变成 2/3。</li>
 * </ol>
 *
 * @author chenmeng
 */
@Api(tags = "请假单接口")
@RestController
@RequestMapping("/leave")
@Validated
public class LeaveController {

    @Resource
    private LeaveService leaveService;

    /**
     * 发起请假：一次调用完成"业务表落库 + 流程实例启动 + 流程实例 id 回写"。
     * 响应里的 processInstanceId 是后续查历史/轨迹接口的入参，请注意保存。
     */
    @ApiOperation("发起请假（创建业务单 + 启动流程）")
    @PostMapping("/create")
    public CommonResult<LeaveRespVO> createLeave(@RequestBody @Valid LeaveCreateReqVO reqVO) {
        return CommonResult.success(leaveService.createLeave(reqVO));
    }

    /**
     * 请假单列表：观察业务状态如何随流程推进变化（1 审批中 → 2 通过 / 3 拒绝）。
     */
    @ApiOperation("请假单列表")
    @GetMapping("/list")
    public CommonResult<List<LeaveRespVO>> listLeave() {
        return CommonResult.success(leaveService.listLeave());
    }
}
