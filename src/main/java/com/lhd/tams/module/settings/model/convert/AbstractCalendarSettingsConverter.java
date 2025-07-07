package com.lhd.tams.module.settings.model.convert;

import com.lhd.tams.module.settings.model.data.CalendarSettingsDO;
import com.lhd.tams.module.settings.model.dto.CalendarSettingsSaveDTO;
import com.lhd.tams.module.settings.model.vo.CalendarSettingsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AbstractCalendarSettingsConverter {

    public static AbstractCalendarSettingsConverter INSTANCE = Mappers.getMapper(AbstractCalendarSettingsConverter.class);

    public abstract CalendarSettingsDO saveDto2DO(CalendarSettingsSaveDTO saveDTO);

    public abstract CalendarSettingsVO do2VO(CalendarSettingsDO dataObj);
}