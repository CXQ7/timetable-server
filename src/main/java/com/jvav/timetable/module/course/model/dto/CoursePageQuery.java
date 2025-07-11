package com.jvav.timetable.module.course.model.dto;

import com.jvav.timetable.common.model.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "课程分页查询参数")
@Data
public class CoursePageQuery extends BasePageQuery {

    @Schema(description = "停启用状态")
    private Integer enableState;
}
