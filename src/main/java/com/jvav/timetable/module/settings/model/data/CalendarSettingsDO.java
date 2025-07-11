package com.jvav.timetable.module.settings.model.data;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("calendarSettings")
public class CalendarSettingsDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private LocalDate firstWeekDay;
    private Integer classesPerDay;
    private Integer semesterCount;
    private Boolean showSaturday;
    private Boolean showSunday;
    private Boolean showNonCurrentWeek;
    private Boolean showTeacher;
    private Boolean showClassroom;
    private String classTimes; // JSON字符串
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}