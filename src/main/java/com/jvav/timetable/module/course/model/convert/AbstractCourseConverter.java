package com.jvav.timetable.module.course.model.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jvav.timetable.module.course.model.data.CourseDO;
import com.jvav.timetable.module.course.model.dto.CourseSaveDTO;
import com.jvav.timetable.module.course.model.vo.CourseListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class AbstractCourseConverter {

    public static AbstractCourseConverter INSTANCE = Mappers.getMapper(AbstractCourseConverter.class);

    /**
     * doPage2ListVoPage
     * @param doPage
     * @return
     */
    public abstract Page<CourseListVO> doPage2ListVoPage(IPage<CourseDO> doPage);

    /**
     * doList2ListVoList
     * @param doList
     * @return
     */
    public abstract List<CourseListVO> doList2ListVoList(List<CourseDO> doList);

    /**
     * do2ListVO
     * @param dataObj
     * @return
     */
    public abstract CourseListVO do2ListVO(CourseDO dataObj);

    /**
     * saveDto2DO
     * @param saveDTO
     * @return
     */
    public abstract CourseDO saveDto2DO(CourseSaveDTO saveDTO);

}
