package com.jvav.timetable.module.classroom.model.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jvav.timetable.module.classroom.model.data.ClassroomDO;
import com.jvav.timetable.module.classroom.model.dto.ClassroomSaveDTO;
import com.jvav.timetable.module.classroom.model.vo.ClassroomListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class AbstractClassroomConverter {

    public static AbstractClassroomConverter INSTANCE = Mappers.getMapper(AbstractClassroomConverter.class);

    /**
     * doPage2ListVoPage
     * @param doPage
     * @return
     */
    public abstract Page<ClassroomListVO> doPage2ListVoPage(IPage<ClassroomDO> doPage);

    /**
     * doList2ListVoList
     * @param doList
     * @return
     */
    public abstract List<ClassroomListVO> doList2ListVoList(List<ClassroomDO> doList);

    /**
     * do2ListVO
     * @param dataObj
     * @return
     */
    public abstract ClassroomListVO do2ListVO(ClassroomDO dataObj);

    /**
     * saveDto2DO
     * @param saveDTO
     * @return
     */
    public abstract ClassroomDO saveDto2DO(ClassroomSaveDTO saveDTO);

}
