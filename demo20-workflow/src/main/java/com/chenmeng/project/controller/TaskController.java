package com.chenmeng.project.controller;

import com.chenmeng.project.common.CommonResult;
import com.chenmeng.project.service.LeaveService;
import com.chenmeng.project.vo.TaskCompleteReqVO;
import com.chenmeng.project.vo.TaskRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 任务接口：待办查询 + 审批。
 *
 * <p>角色扮演体验（发起时传的 leader=lisi、director=wangwu、hr=hr001）：
 * <ol>
 *     <li>GET /task/todo?assignee=lisi：领导 lisi 的待办里出现"领导审批"；</li>
 *     <li>POST /task/complete：传 taskId + approved=true + comment —— lisi 审批通过；</li>
 *     <li>day=2 的情况：待办流转到 hr001 的"HR 备案"（网关走了 ${approved} 短分支）；
 *         day=5 的情况：待办先流转到 wangwu 的"总监审批"，再到 hr001；</li>
 *     <li>试着在任一环节传 approved=false —— 流程直达结束，请假单变 3-拒绝。</li>
 * </ol>
 *
 * @author chenmeng
 */
@Api(tags = "任务接口")
@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Resource
    private LeaveService leaveService;

    /**
     * 查某人待办：只查 ACT_RU_TASK（运行时任务表），完成的任务不会出现。
     */
    @ApiOperation("查某人待办任务列表")
    @GetMapping("/todo")
    public CommonResult<List<TaskRespVO>> listTodoTask(
            @ApiParam(value = "办理人（发起时传的 leader / director / hr）", required = true, example = "lisi")
            @RequestParam("assignee") @NotBlank(message = "办理人不能为空") String assignee) {
        return CommonResult.success(leaveService.listTodoTask(assignee));
    }

    /**
     * 审批：写意见（ACT_HI_COMMENT）+ 完成任务（approved 变量驱动网关分支）。
     */
    @ApiOperation("审批（完成任务：approved=true 通过 / false 拒绝）")
    @PostMapping("/complete")
    public CommonResult<Boolean> completeTask(@RequestBody @Valid TaskCompleteReqVO reqVO) {
        leaveService.completeTask(reqVO);
        return CommonResult.success(Boolean.TRUE);
    }
}
