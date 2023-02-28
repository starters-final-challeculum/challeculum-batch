package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.UserMission;
import companion.challeculum.batch.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.writer
 */
@Component
@RequiredArgsConstructor
public class WaitingUserMissionWriter implements ItemWriter<UserMission> {

    private final UserMissionRepository userMissionRepository;

    @Override
    public void write(List<? extends UserMission> userMissions) {
        userMissions.forEach(item -> {
            item.setIsAccepted(Constants.USER_MISSION_ACCEPTED);
            userMissionRepository.save(item);
        });
    }
}
