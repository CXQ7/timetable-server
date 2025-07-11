package com.jvav.timetable.module.coursescheduling.controller;

import lombok.Data;

@Data
public class CourseScheduling {
    private String date;        // 日期
    private String startTime;   // 开始时间
    private String endTime;     // 结束时间
    private String courseName;  // 课程名称
    private String teacherName; // 授课老师
    private Integer required;   // 是否必修
    private String classroom;   // 上课地址
}
