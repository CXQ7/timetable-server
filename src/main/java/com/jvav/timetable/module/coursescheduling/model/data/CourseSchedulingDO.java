package com.jvav.timetable.module.coursescheduling.model.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@TableName("courseScheduling")
public class CourseSchedulingDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 教室id
     */
    private Long classroomId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 老师id
     */
    private Long teacherId;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 上课时间
     */
    private LocalTime attendTime;

    /**
     * 下课时间
     */
    private LocalTime finishTime;
    /**
     * 备注
     */
    private String remarks;
    
    /**
     * 用户名
     */
    private String username;
}
