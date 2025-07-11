package com.jvav.timetable.module.settings.model.convert;

import com.jvav.timetable.module.settings.model.data.CalendarSettingsDO;
import com.jvav.timetable.module.settings.model.dto.CalendarSettingsSaveDTO;
import com.jvav.timetable.module.settings.model.vo.CalendarSettingsVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AbstractCalendarSettingsConverter {

    public static AbstractCalendarSettingsConverter INSTANCE = Mappers.getMapper(AbstractCalendarSettingsConverter.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(source = "classTimes", target = "classTimes", qualifiedByName = "objectToJson")
    public abstract CalendarSettingsDO saveDto2DO(CalendarSettingsSaveDTO saveDTO);

    public abstract CalendarSettingsVO do2VO(CalendarSettingsDO dataObj);

    @Named("objectToJson")
    protected String objectToJson(Object value) {
        if (value == null) return null;
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}