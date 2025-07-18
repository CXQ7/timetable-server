package com.jvav.timetable.common.base;

import com.jvav.timetable.common.model.ApiResult;
import com.jvav.timetable.common.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;


public class BaseController {

    protected static ResponseEntity<ApiResult<?>> success() {
        return ResponseEntityUtils.ok("");
    }

    protected static <T> ResponseEntity<ApiResult<T>> success(T data) {
        return ResponseEntityUtils.ok(data);
    }

    protected static ResponseEntity<ApiResult<?>> error(String msg) {
        return ResponseEntityUtils.badRequest(msg);
    }

    protected static ResponseEntity<ApiResult<?>> successOrFail(Boolean flag) {
        return flag ? success() : ResponseEntityUtils.badRequest("操作失败，数据可能已被修改或删除");
    }
}
