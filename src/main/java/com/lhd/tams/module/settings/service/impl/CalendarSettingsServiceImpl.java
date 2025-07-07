package com.lhd.tams.module.settings.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhd.tams.module.settings.dao.CalendarSettingsMapper;
import com.lhd.tams.module.settings.model.data.CalendarSettingsDO;
import com.lhd.tams.module.settings.model.dto.CalendarSettingsSaveDTO;
import com.lhd.tams.module.settings.service.CalendarSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarSettingsServiceImpl extends ServiceImpl<CalendarSettingsMapper, CalendarSettingsDO>
        implements CalendarSettingsService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void save(CalendarSettingsSaveDTO dto) {
        CalendarSettingsDO entity = convert(dto);
        this.save(entity);
    }

    @Override
    public void update(CalendarSettingsSaveDTO dto) {
        CalendarSettingsDO entity = convert(dto);
        // 假设只有一条设置，id=1
        entity.setId(1L);
        this.updateById(entity);
    }

    private CalendarSettingsDO convert(CalendarSettingsSaveDTO dto) {
        CalendarSettingsDO entity = new CalendarSettingsDO();
        entity.setName(dto.getName());
        entity.setFirstWeekDay(java.time.LocalDate.parse(dto.getFirstWeekDay()));
        entity.setClassesPerDay(dto.getClassesPerDay());
        entity.setSemesterCount(dto.getSemesterCount());
        entity.setShowSaturday(dto.getShowSaturday());
        entity.setShowSunday(dto.getShowSunday());
        entity.setShowNonCurrentWeek(dto.getShowNonCurrentWeek());
        entity.setShowTeacher(dto.getShowTeacher());
        entity.setShowClassroom(dto.getShowClassroom());
        try {
            entity.setClassTimes(objectMapper.writeValueAsString(dto.getClassTimes()));
        } catch (Exception e) {
            entity.setClassTimes("[]");
        }
        return entity;
    }
}