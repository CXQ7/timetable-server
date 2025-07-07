package com.lhd.tams.module.course.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "课程新增参数")
@Data
public class CourseSaveDTO {

    @Schema(description = "名称")
    private String name;
    @Schema(description = "是否必修")
    private Integer courseType;
    @Schema(description = "课程时长，单位分钟")
    private Integer duration;

    @Schema(description = "背景颜色")
    private String backgroundColor;
}
