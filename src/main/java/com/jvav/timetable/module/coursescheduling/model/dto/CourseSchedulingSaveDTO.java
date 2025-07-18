package com.jvav.timetable.module.coursescheduling.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "排课新增参数")
@Data
public class CourseSchedulingSaveDTO {

    @NotNull
    @Schema(description = "教室id")
    private Long classroomId;

    @NotNull
    @Schema(description = "课程id")
    private Long courseId;

    @NotNull
    @Schema(description = "老师id")
    private Long teacherId;

    @NotNull
    @Schema(description = "日期")
    private LocalDate date;

    @NotNull
    @Schema(description = "上课时间")
    private LocalTime attendTime;

    @NotNull
    @Schema(description = "下课时间")
    private LocalTime finishTime;

    @Schema(description = "备注")
    private String remarks;
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    @Schema(description = "用户名")
    private String username;
}
