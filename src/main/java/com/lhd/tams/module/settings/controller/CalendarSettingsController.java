package com.lhd.tams.module.settings.controller;

import com.lhd.tams.common.base.BaseController;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.settings.model.dto.CalendarSettingsSaveDTO;
import com.lhd.tams.module.settings.model.vo.CalendarSettingsVO;
import com.lhd.tams.module.settings.service.CalendarSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "课表设置")
@RequestMapping("/settings")
@RestController
public class CalendarSettingsController extends BaseController {

    @Autowired
    private CalendarSettingsService calendarSettingsService;

    @Operation(summary = "获取课表设置")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<CalendarSettingsVO>> getCalendarSettings() {
        return success(calendarSettingsService.getCalendarSettings());
    }

    @Operation(summary = "保存课表设置")
    @PostMapping("/save")
    public ApiResult<?> save(@RequestBody CalendarSettingsSaveDTO dto) {
        calendarSettingsService.save(dto);
        return ApiResult.success();
    }

    @Operation(summary = "更新课表设置")
    @PutMapping("/update")
    public ApiResult<?> update(@RequestBody CalendarSettingsSaveDTO dto) {
        calendarSettingsService.update(dto);
        return ApiResult.success();
    }
}