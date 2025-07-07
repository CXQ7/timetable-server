package com.lhd.tams.module.course.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "课程列表返回结果")
@Data
public class CourseListVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "是否必修")
    private Integer courseType;
    @Schema(description = "停启用状态")
    private Integer enableState;

    @Schema(description = "课程时长，单位分钟")
    private Integer duration;

    @Schema(description = "背景颜色")
    private String backgroundColor;
}
