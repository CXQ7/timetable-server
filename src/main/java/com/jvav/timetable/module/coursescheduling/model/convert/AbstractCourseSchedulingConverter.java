package com.jvav.timetable.module.coursescheduling.model.convert;

import com.jvav.timetable.module.coursescheduling.model.data.CourseSchedulingDO;
import com.jvav.timetable.module.coursescheduling.model.dto.CourseSchedulingBatchSaveDTO;
import com.jvav.timetable.module.coursescheduling.model.dto.CourseSchedulingSaveDTO;
import com.jvav.timetable.module.coursescheduling.model.dto.CourseSchedulingTimeUpdateDTO;
import com.jvav.timetable.module.coursescheduling.model.dto.CourseSchedulingUpdateDTO;
import com.jvav.timetable.module.coursescheduling.model.vo.CourseSchedulingListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class AbstractCourseSchedulingConverter {

    public static AbstractCourseSchedulingConverter INSTANCE = Mappers.getMapper(AbstractCourseSchedulingConverter.class);

    /**
     * doList2ListVoList
     * @param doList
     * @return
     */
    public abstract List<CourseSchedulingListVO> doList2ListVoList(List<CourseSchedulingDO> doList);

    /**
     * do2ListVO
     * @param dataObj
     * @return
     */
    public abstract CourseSchedulingListVO do2ListVO(CourseSchedulingDO dataObj);

    /**
     * saveDto2DO
     * @param saveDTO
     * @return
     */
    public abstract CourseSchedulingDO saveDto2DO(CourseSchedulingSaveDTO saveDTO);

    /**
     * batchSaveDto2DO
     * @param saveDTO
     * @return
     */
    public abstract CourseSchedulingDO batchSaveDto2DO(CourseSchedulingBatchSaveDTO saveDTO);

    /**
     * timeUpdateDto2DO
     * @param updateDTO
     * @return
     */
    public abstract CourseSchedulingDO timeUpdateDto2DO(CourseSchedulingTimeUpdateDTO updateDTO);

    /**
     * updateDto2DO
     * @param updateDTO
     * @return
     */
    public abstract CourseSchedulingDO updateDto2DO(CourseSchedulingUpdateDTO updateDTO);
}
