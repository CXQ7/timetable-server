package com.lhd.tams.module.settings.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "课表设置保存参数")
public class CalendarSettingsSaveDTO {

    @Schema(description = "课表名称")
    private String name;

    @Schema(description = "第一周星期一日期")
    private String firstWeekDay;

    @Schema(description = "每天课程节数")
    private Integer classesPerDay;

    @Schema(description = "学期数量")
    private Integer semesterCount;

    @Schema(description = "是否显示周六")
    private Boolean showSaturday;

    @Schema(description = "是否显示周日")
    private Boolean showSunday;

    @Schema(description = "是否显示非本周课程")
    private Boolean showNonCurrentWeek;

    @Schema(description = "是否显示教师")
    private Boolean showTeacher;

    @Schema(description = "是否显示教室")
    private Boolean showClassroom;

    @Schema(description = "上课时间配置")
    private Object classTimes; // 前端传数组，后端转JSON
}