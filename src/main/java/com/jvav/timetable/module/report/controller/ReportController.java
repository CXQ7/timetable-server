package com.jvav.timetable.module.report.controller;

import com.jvav.timetable.common.base.BaseController;
import com.jvav.timetable.common.model.ApiResult;
import com.jvav.timetable.module.coursescheduling.model.vo.CourseSchedulingReportVO;
import com.jvav.timetable.module.coursescheduling.service.CourseSchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "报表")
@RequestMapping("report")
@RestController
public class ReportController extends BaseController {

    @Autowired
    private CourseSchedulingService courseSchedulingService;

    @Operation(summary = "老师上课数量")
    @GetMapping("teacher/count")
    public ResponseEntity<ApiResult<List<CourseSchedulingReportVO>>> getReportTeacherCount(@RequestParam("startDate") String startDate,
                                                                                           @RequestParam("endDate") String endDate) {

        return success(courseSchedulingService.getReportTeacherCount(startDate, endDate));
    }

    @Operation(summary = "课程数量")
    @GetMapping("course/count")
    public ResponseEntity<ApiResult<List<CourseSchedulingReportVO>>> getReportCourseCount(@RequestParam("startDate") String startDate,
                                                                                          @RequestParam("endDate") String endDate) {

        return success(courseSchedulingService.getReportCourseCount(startDate, endDate));
    }
}
