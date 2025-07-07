package com.lhd.tams.module.settings.service;

import com.lhd.tams.module.settings.model.dto.CalendarSettingsSaveDTO;
import com.lhd.tams.module.settings.model.vo.CalendarSettingsVO;

public interface CalendarSettingsService {

    /**
     * 保存课表设置
     * @param saveDTO 保存参数
     * @return 是否成功
     */
    boolean saveCalendarSettings(CalendarSettingsSaveDTO saveDTO);

    /**
     * 更新课表设置
     * @param saveDTO 更新参数
     * @return 是否成功
     */
    boolean updateCalendarSettings(CalendarSettingsSaveDTO saveDTO);

    /**
     * 获取课表设置
     * @return 课表设置
     */
    CalendarSettingsVO getCalendarSettings();

    void save(CalendarSettingsSaveDTO dto);
    void update(CalendarSettingsSaveDTO dto);
}