package com.jvav.timetable.module.classroom.model.dto;

import com.jvav.timetable.common.model.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "教室分页查询参数")
@Data
public class ClassroomPageQuery extends BasePageQuery {

    @Schema(description = "停启用状态")
    private Integer enableState;
}
