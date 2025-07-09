package com.lhd.tams.module.coursescheduling.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelParser {

    // 解析 Excel 文件为课程列表
    public static List<CourseScheduling> parseCourseExcel(MultipartFile file) throws IOException {
        List<CourseScheduling> result = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return result;
            }

            // 获取表头行
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return result;
            }

            // 构建表头映射（列索引 -> 表头名称）
            Map<Integer, String> headerMap = new HashMap<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    headerMap.put(i, cell.getStringCellValue());
                }
            }

            // 遍历数据行（从第二行开始）
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                CourseScheduling course = new CourseScheduling();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    String header = headerMap.get(j);
                    if (header == null) {
                        continue;
                    }

                    Cell cell = row.getCell(j);
                    String value = getCellValueAsString(cell);

                    // 根据表头映射设置课程属性
                    switch (header.trim()) {
                        case "日期":
                            course.setDate(value);
                            break;
                        case "开始时间":
                            course.setStartTime(value);
                            break;
                        case "结束时间":
                            course.setEndTime(value);
                            break;
                        case "课程名称":
                            course.setCourseName(value);
                            break;
                        case "授课老师":
                            course.setTeacherName(value);
                            break;
                        case "是否必修":
                            course.setRequired(parseRequired(value));
                            break;
                        case "上课地址":
                            course.setClassroom(value);
                            break;
                    }
                }

                result.add(course);
            }
        }
        return result;
    }

    // 判断行是否为空
    private static boolean isRowEmpty(Row row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    // 获取单元格值为字符串
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    // 解析是否必修字段
    private static Integer parseRequired(String value) {
        if (value == null || value.isEmpty()) {
            return 0; // 默认选修
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // 如果无法解析为数字，根据文本判断
            return value.equalsIgnoreCase("是") || value.equalsIgnoreCase("必修") ? 1 : 0;
        }
    }
}