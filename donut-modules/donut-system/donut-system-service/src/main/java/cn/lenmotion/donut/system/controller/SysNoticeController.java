package cn.lenmotion.donut.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.lenmotion.donut.core.annotation.OperationLog;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.utils.PageUtils;
import cn.lenmotion.donut.system.entity.po.SysNotice;
import cn.lenmotion.donut.system.entity.query.NoticeQuery;
import cn.lenmotion.donut.system.entity.query.UserNoticeQuery;
import cn.lenmotion.donut.system.entity.request.SysNoticeRequest;
import cn.lenmotion.donut.system.entity.vo.NoticeVO;
import cn.lenmotion.donut.system.entity.vo.UserNoticeVO;
import cn.lenmotion.donut.system.service.SysNoticeReadService;
import cn.lenmotion.donut.system.service.SysNoticeSendRelationService;
import cn.lenmotion.donut.system.service.SysNoticeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 公告 信息操作处理
 *
 * @author lenmotion
 */
@RestController
@RequestMapping("/system/notice")
@RequiredArgsConstructor
public class SysNoticeController {

    private final SysNoticeService noticeService;
    private final SysNoticeSendRelationService noticeSendRelationService;
    private final SysNoticeReadService noticeReadService;

    @Operation(summary = "获取公告列表")
    @SaCheckPermission("system:notice:list")
    @GetMapping("/list")
    public ResponseResult<PageResult<SysNotice>> list(NoticeQuery baseQuery) {
        IPage<SysNotice> list = noticeService.selectPage(baseQuery);
        return ResponseResult.success(PageUtils.getPageResult(list));
    }

    @Operation(summary = "获取公告详情")
    @SaCheckPermission("system:notice:list")
    @GetMapping("/{id}")
    public ResponseResult<NoticeVO> list(@PathVariable Long id) {
        return ResponseResult.success(noticeService.getDetailById(id));
    }

    @Operation(summary = "获取用户公告列表")
    @GetMapping("userNoticeList")
    public ResponseResult<PageResult<UserNoticeVO>> userNoticeList(UserNoticeQuery query) {
        query.setUserId(StpUtil.getLoginIdAsLong());
        var page = noticeSendRelationService.getUserNotice(query);
        return ResponseResult.success(PageUtils.getPageResult(page));
    }

    @Operation(summary = "阅读公告")
    @PostMapping("read/{id}")
    public ResponseResult<Boolean> read(@PathVariable Long id) {
        return ResponseResult.success(noticeReadService.read(id, StpUtil.getLoginIdAsLong()));
    }

    @SaCheckPermission("system:notice:save")
    @Operation(summary = "新增或修改通知公告")
    @OperationLog("新增或修改通知公告")
    @PostMapping
    public ResponseResult<Boolean> saveOrUpdate(@Validated @RequestBody SysNoticeRequest request) {
        return ResponseResult.success(noticeService.saveOrUpdate(request));
    }

    @SaCheckPermission("system:notice:status")
    @Operation(summary = "更新公告状态")
    @OperationLog("更新公告状态")
    @PutMapping("status")
    public ResponseResult<Boolean> updateUserStatus(@Validated @RequestBody BaseUpdateStatus request) {
        return ResponseResult.success(noticeService.updateStatus(request));
    }

    @SaCheckPermission("system:notice:remove")
    @Operation(summary = "删除通知公告")
    @OperationLog("删除通知公告")
    @DeleteMapping("/{noticeIds}")
    public ResponseResult<Boolean> remove(@PathVariable Long[] noticeIds) {
        return ResponseResult.success(noticeService.removeByIds(Arrays.asList(noticeIds)));
    }

}
