package com.jvav.timetable.module.coursescheduling.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "排课报表返回结果")
@Data
public class CourseSchedulingReportVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "颜色")
    private String color;

    @Schema(description = "数量")
    private Integer count;
}
