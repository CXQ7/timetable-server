package com.lhd.tams.module.coursescheduling.controller;

import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.classroom.model.vo.ClassroomListVO;
import com.lhd.tams.module.classroom.service.ClassroomService;
import com.lhd.tams.module.course.model.vo.CourseListVO;
import com.lhd.tams.module.course.service.CourseService;
import com.lhd.tams.module.coursescheduling.model.dto.CourseSchedulingSaveDTO;
import com.lhd.tams.module.coursescheduling.service.CourseSchedulingService;
import com.lhd.tams.module.teacher.model.vo.TeacherListVO;
import com.lhd.tams.module.teacher.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("course-scheduling")
@RestController
public class import_excel {
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private CourseSchedulingService courseSchedulingService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    @Operation(summary = "导入excel")
    @PostMapping("import")
    public ResponseEntity<ApiResult<?>> importExcel(
            @RequestParam("file") MultipartFile file,
            @Validated CourseSchedulingController.CourseSchedulingImportParam param) throws IOException {


        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResult.error("请上传Excel文件"));
        }

        // 校验文件类型
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(".xlsx") && !originalFilename.endsWith(".xls")) {
            //    return ResponseEntity.badRequest().body(ApiResult.error("请上传Excel文件（.xlsx或.xls格式）"));
        }

        try {
            // 使用Apache POI解析Excel
            List<CourseScheduling> dataList = ExcelParser.parseCourseExcel(file);

            if (dataList.isEmpty()) {
                return ResponseEntity.badRequest().body(ApiResult.error("Excel中未读取到有效数据"));
            }

            // 解析Excel数据
            for(int i=0;i<dataList.size();i++){

                CourseSchedulingSaveDTO saveDTO = new CourseSchedulingSaveDTO();
                //教室名称
                List<ClassroomListVO> classroomListVOS = classroomService.refList();
                if(classroomListVOS.size()>0){
                    for (ClassroomListVO classroomListVO : classroomListVOS) {
                        if(classroomListVO.getName().equals(dataList.get(i).getClassroom())){
                            saveDTO.setClassroomId(classroomListVO.getId());
                        }
                    }
                    //  saveDTO.setClassroomId(classroomListVOS.get(0).getId());
                }
                //查询教师
                List<TeacherListVO> teacherListVOS = teacherService.refList();
                if(teacherListVOS.size()>0){
                    for (TeacherListVO teacherListVO : teacherListVOS) {
                        if(teacherListVO.getName().equals(dataList.get(i).getTeacherName())){
                            saveDTO.setTeacherId(teacherListVO.getId());
                        }
                    }
                    //  saveDTO.setTeacherId(teacherListVOS.get(0).getId());
                }
                List<CourseListVO> courseListVOS = courseService.refList();
                if(courseListVOS.size()>0){
                    for (CourseListVO courseListVO : courseListVOS) {
                        if(courseListVO.getName().equals(dataList.get(i).getCourseName())){
                            saveDTO.setCourseId(courseListVO.getId());
                        }
                    }
                    //  saveDTO.setCourseId(courseListVOS.get(0).getId());
                }
                LocalDate date = LocalDate.parse(dataList.get(i).getDate(), DATE_FORMATTER);
                saveDTO.setDate(date);
                LocalTime attendTime = LocalTime.parse(dataList.get(i).getStartTime(), TIME_FORMATTER);
                saveDTO.setAttendTime(attendTime);
                LocalTime getEndTime = LocalTime.parse(dataList.get(i).getEndTime(), TIME_FORMATTER);
                saveDTO.setFinishTime(getEndTime);
                courseSchedulingService.saveCourseScheduling(saveDTO);

            }
            //查询课程，
            //教室
            //地址
            // 调用业务层处理导入数据
            //  boolean importResult = courseSchedulingService.importCourseScheduling(dataList, param);

            if (1==1) {
                return ResponseEntity.ok(ApiResult.success("导入成功，共读取" + dataList.size() + "条数据"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResult.error("导入失败，请检查数据格式"));
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResult.error("导入失败：" + e.getMessage()));
        }
    }
}
