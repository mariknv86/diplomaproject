package ru.mariknv86.blog.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mariknv86.blog.dto.GlobalSettingsDto;
import ru.mariknv86.blog.model.GlobalSettings;
import ru.mariknv86.blog.model.enums.GlobalSetting;
import ru.mariknv86.blog.repository.GlobalSettingsRepository;

@Service
@AllArgsConstructor
public class GlobalSettingsService {

    private static final String yes = "yes";
    private static final String no = "no";

    private final GlobalSettingsRepository globalSettingsRepository;

    public GlobalSettingsDto getGlobalSettings() {
        List<GlobalSettings> globalSettingsList = globalSettingsRepository.findAll();
        GlobalSettingsDto globalSettingsDto = new GlobalSettingsDto();

        globalSettingsList.forEach(
            setting -> {
                if(setting.getCode().equals(GlobalSetting.MULTIUSER_MODE)) {
                    globalSettingsDto.setMultiUserMode(setting.getValue().equalsIgnoreCase(yes));
                }
                if(setting.getCode().equals(GlobalSetting.POST_PREMODERATION)) {
                    globalSettingsDto.setPostPreModeration(setting.getValue().equalsIgnoreCase(no));
                }
                if(setting.getCode().equals(GlobalSetting.STATISTICS_IS_PUBLIC)) {
                    globalSettingsDto.setStatisticsIsPublic(setting.getValue().equalsIgnoreCase(yes));
                }
            });

        return globalSettingsDto;
    }

    public void setGlobalSettings(GlobalSettingsDto globalSettingsDto) {
        globalSettingsRepository.updateValue
            (globalSettingsDto.isMultiUserMode() ? yes : no,
                GlobalSetting.MULTIUSER_MODE.toString());
        globalSettingsRepository.updateValue
            (globalSettingsDto.isPostPreModeration() ? yes : no,
                GlobalSetting.POST_PREMODERATION.toString());
        globalSettingsRepository.updateValue
            (globalSettingsDto.isStatisticsIsPublic() ? yes : no,
                GlobalSetting.STATISTICS_IS_PUBLIC.toString());
    }

    @PostConstruct
    private void initSettings() {
        final String multiUserModeValue = "?????????????????????????????????????????? ??????????";
        final String postPreModerationValue = "???????????????????????? ????????????";
        final String statisticsIsPublicValue = "???????????????????? ???????? ???????????????????? ??????????";

        List<GlobalSettings> globalSettingsList = new ArrayList<>();

        GlobalSettings multiUserMode = new GlobalSettings();
        multiUserMode.setId(1);
        multiUserMode.setName(multiUserModeValue);
        multiUserMode.setCode(GlobalSetting.MULTIUSER_MODE);
        multiUserMode.setValue(yes);

        GlobalSettings postPreModeration = new GlobalSettings();
        postPreModeration.setId(2);
        postPreModeration.setName(postPreModerationValue);
        postPreModeration.setCode(GlobalSetting.POST_PREMODERATION);
        postPreModeration.setValue(yes);

        GlobalSettings statisticsIsPublic = new GlobalSettings();
        statisticsIsPublic.setId(3);
        statisticsIsPublic.setName(statisticsIsPublicValue);
        statisticsIsPublic.setCode(GlobalSetting.STATISTICS_IS_PUBLIC);
        statisticsIsPublic.setValue(yes);
    }



}
