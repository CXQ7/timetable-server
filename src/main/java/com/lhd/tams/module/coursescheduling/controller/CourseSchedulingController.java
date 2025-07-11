package com.lhd.tams.module.coursescheduling.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lhd.tams.common.base.BaseController;
import com.lhd.tams.common.model.ApiResult;
import com.lhd.tams.module.coursescheduling.manager.CourseSchedulingExcelManager;
import com.lhd.tams.module.coursescheduling.model.dto.*;
import com.lhd.tams.module.coursescheduling.model.vo.CourseSchedulingListVO;
import com.lhd.tams.module.coursescheduling.service.CourseSchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "排课")
@RequestMapping("course-scheduling")
@RestController
public class CourseSchedulingController extends BaseController {

    @Autowired
    private CourseSchedulingService courseSchedulingService;

    @Autowired
    private CourseSchedulingExcelManager courseSchedulingExcelManager;

    @Operation(summary = "列表")
    @PostMapping("list")
    public ResponseEntity<ApiResult<List<CourseSchedulingListVO>>> listCourseScheduling(@RequestBody CourseSchedulingQuery query) {

        return success(courseSchedulingService.listCourseScheduling(query));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    public ResponseEntity<ApiResult<CourseSchedulingListVO>> getCourseSchedulingById(@PathVariable("id") Long id) {

        return success(courseSchedulingService.getCourseSchedulingById(id));
    }

    @Operation(summary = "课程数量")
    @PostMapping("course/count")
    public ResponseEntity<ApiResult<Map<String, Integer>>> getCourseSchedulingCourseCount(@RequestBody CourseSchedulingQuery query) {

        return success(courseSchedulingService.getCourseSchedulingCourseCount(query));
    }

    @Operation(summary = "新增")
    @PostMapping
    public ResponseEntity<ApiResult<?>> saveCourseScheduling(@Validated @RequestBody CourseSchedulingSaveDTO saveDTO) {

        return successOrFail(courseSchedulingService.saveCourseScheduling(saveDTO));
    }

    @Operation(summary = "批量新增")
    @PostMapping("batch")
    public ResponseEntity<ApiResult<?>> saveCourseScheduling(@Validated @RequestBody CourseSchedulingBatchSaveDTO saveDTO) {

        courseSchedulingService.batchSaveCourseScheduling(saveDTO);

        return success();
    }

    @Operation(summary = "修改排课时间")
    @PutMapping("{id}/time")
    public ResponseEntity<ApiResult<?>> updateCourseSchedulingTimeById(@PathVariable("id") Long id, @Validated @RequestBody CourseSchedulingTimeUpdateDTO updateDTO) {

        return successOrFail(courseSchedulingService.updateCourseSchedulingTimeById(id, updateDTO));
    }

    @Operation(summary = "修改")
    @PutMapping("{id}")
    public ResponseEntity<ApiResult<?>> updateCourseSchedulingById(@PathVariable("id") Long id, @Validated @RequestBody CourseSchedulingSaveDTO saveDTO) {

        return successOrFail(courseSchedulingService.updateCourseSchedulingById(id, saveDTO));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResult<?>> removeCourseSchedulingById(@PathVariable("id") Long id) {

        return successOrFail(courseSchedulingService.removeCourseSchedulingById(id));
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    public ResponseEntity<ApiResult<?>> removeCourseSchedulingByIdList(@RequestBody List<Long> idList) {

        courseSchedulingService.removeCourseSchedulingByIdList(idList);

        return success();
    }

    @Operation(summary = "导出excel")
    @GetMapping("export/excel")
    public void exportExcel(HttpServletResponse response, @Validated CourseSchedulingExportDTO dto) throws IOException {

        Workbook workbook = courseSchedulingExcelManager.createExcel(dto);

        String filename = (StrUtil.isEmpty(dto.getFilename()) ? "课表" : dto.getFilename()) + ".xlsx";
        response.reset();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), StandardCharsets.ISO_8859_1));

        workbook.write(response.getOutputStream());
        response.flushBuffer();
        workbook.close();
    }

    class CourseSchedulingImportDTO {
        // 对应Excel中的列
        private String courseName;      // 课程名称
        private String teacherName;     // 教师姓名
        private String className;       // 班级名称
        private String courseTime;      // 上课时间
        private String classroom;       // 教室

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getCourseTime() {
            return courseTime;
        }

        public void setCourseTime(String courseTime) {
            this.courseTime = courseTime;
        }

        public String getClassroom() {
            return classroom;
        }

        public void setClassroom(String classroom) {
            this.classroom = classroom;
        }
    }


    class CourseSchedulingImportParam {
        // 导入参数，如学期、年级等
        private String semester;
        private String grade;

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getSemester() {

            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }
    }


    static class CourseSchedulingDataListener extends AnalysisEventListener<CourseSchedulingImportDTO> {

        private final List<CourseSchedulingImportDTO> dataList = new ArrayList<>();
        private int rowNum = 0;

        @Override
        public void invoke(CourseSchedulingImportDTO data, AnalysisContext context) {
            rowNum++;

            // 跳过空行和表头（假设表头在第1行）
            if (rowNum == 1 || isEmptyRow(data)) {
                return;
            }

            dataList.add(data);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            // 解析完成后清理资源
        }

        private boolean isEmptyRow(CourseSchedulingImportDTO data) {
            // 根据实际情况判断行是否为空
            // 示例：假设所有字段都为空则视为空行
            return StringUtils.isEmpty(data.getCourseName()) &&
                    StringUtils.isEmpty(data.getTeacherName()) &&
                    StringUtils.isEmpty(data.getClassName());
        }

        public List<CourseSchedulingImportDTO> getDataList() {
            return dataList;
        }
    }



    @Operation(summary = "导入excel")
    @PostMapping("import/import")
    public Result<List<CourseSchedulingImportDTO>> importExcel(
            @RequestParam("file") MultipartFile file,
            @Validated CourseSchedulingImportParam param) throws IOException {

        if (file.isEmpty()) {
            return Result.error("请上传Excel文件");
        }

        // 校验文件类型
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(".xlsx") && !originalFilename.endsWith(".xls")) {
            return Result.error("请上传Excel文件（.xlsx或.xls格式）");
        }

        // 使用EasyExcel解析Excel
        CourseSchedulingDataListener listener = new CourseSchedulingDataListener();
        EasyExcel.read(file.getInputStream(), CourseSchedulingImportDTO.class, listener).sheet().doRead();

        // 处理解析结果
        List<CourseSchedulingImportDTO> dataList = listener.getDataList();
        if (dataList.isEmpty()) {
            return Result.error("Excel中未读取到有效数据");
        }

        // 处理解析结果

        // 调用业务层处理导入数据
     //   boolean importResult = courseSchedulingExcelManager.importExcelData(dataList, param);

      /*  if (importResult) {
            return Result.success(dataList, "导入成功，共读取" + dataList.size() + "条数据");
        } else {
            return Result.error("导入失败，请检查数据格式");
        }*/
        return null;
    }


    static class Result<T> {
        private int code;
        private String message;
        private T data;

        public static <T> Result<T> success(T data) {
            return success(data, "操作成功");
        }

        public static <T> Result<T> success(T data, String message) {
            Result<T> result = new Result<>();
            result.code = 200;
            result.message = message;
            result.data = data;
            return result;
        }

        public static <T> Result<T> error(String message) {
            Result<T> result = new Result<>();
            result.code = 500;
            result.message = message;
            return result;
        }


    } }
