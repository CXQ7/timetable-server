package com.jvav.timetable.module.teacher.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "教师分页查询参数")
@Data
public class TeacherSaveDTO {

    @Schema(description = "姓名")
    private String name;
}
