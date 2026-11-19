package com.chenmeng.project.controller;

import com.chenmeng.project.common.CommonResult;
import com.chenmeng.project.service.LeaveService;
import com.chenmeng.project.vo.HistoryTaskRespVO;
import com.chenmeng.project.vo.ProcessTrackNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 流程接口：历史任务 + 流程轨迹（流程图高亮数据）。
 *
 * <p>这两个接口是理解 ACT_HI_* 表的关键体验点：
 * <ul>
 *     <li>流程进行中调用 /process/history：能看到已完成 + 进行中两类任务（endTime 是否为空）；</li>
 *     <li>流程结束后调用：ACT_RU_* 里该流程的数据已被引擎删除，但这两个接口照常工作——历史永存；</li>
 *     <li>/process/track：把返回的 activityId 与 leave.bpmn20.xml 里的节点 id 对照，
 *         finished=true 的节点涂绿、finished=false 的节点高亮闪烁，就是流程图可视化的全部原理。</li>
 * </ul>
 *
 * @author chenmeng
 */
@Api(tags = "流程接口")
@RestController
@RequestMapping("/process")
@Validated
public class ProcessController {

    @Resource
    private LeaveService leaveService;

    /**
     * 查指定流程实例的历史任务（含进行中任务和每步审批意见）。
     */
    @ApiOperation("查流程实例的历史任务（ACT_HI_TASKINST + ACT_HI_COMMENT）")
    @GetMapping("/history")
    public CommonResult<List<HistoryTaskRespVO>> listHistoryTask(
            @ApiParam(value = "流程实例 id（发起请假接口返回的 processInstanceId）", required = true)
            @RequestParam("processInstanceId") @NotBlank(message = "流程实例 id 不能为空") String processInstanceId) {
        return CommonResult.success(leaveService.listHistoryTask(processInstanceId));
    }

    /**
     * 查指定流程实例的流程轨迹节点（流程图高亮数据来源）。
     */
    @ApiOperation("查流程实例的流程轨迹节点（ACT_HI_ACTINST，流程图高亮原理）")
    @GetMapping("/track")
    public CommonResult<List<ProcessTrackNodeVO>> trackProcess(
            @ApiParam(value = "流程实例 id（发起请假接口返回的 processInstanceId）", required = true)
            @RequestParam("processInstanceId") @NotBlank(message = "流程实例 id 不能为空") String processInstanceId) {
        return CommonResult.success(leaveService.trackProcess(processInstanceId));
    }
}
