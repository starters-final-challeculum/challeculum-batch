package companion.challeculum.batch.writer;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.UserMission;
import companion.challeculum.batch.repository.UserMissionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.writer
 */
@Component
public class MissionAcceptedItemWriter implements ItemWriter<UserMission> {

    private final UserMissionRepository userMissionRepository;

    public MissionAcceptedItemWriter(UserMissionRepository userMissionRepository) {
        this.userMissionRepository = userMissionRepository;
    }

    @Override
    public void write(List<? extends UserMission> items) {
        for (UserMission item : items) {
            if (item.getIsAccepted().equals(Constants.USER_MISSION_WAITING)) {
                item.setIsAccepted(Constants.USER_MISSION_ACCEPTED);
                userMissionRepository.save(item);
            }
        }
    }
}