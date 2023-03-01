package companion.challeculum.batch.job.step.writer;

import companion.challeculum.batch.common.Constants;
import companion.challeculum.batch.entity.UserMission;
import companion.challeculum.batch.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(StandbyGroundUpdateWriter.class);


    @Override
    public void write(List<? extends UserMission> userMissions) {
        userMissions.forEach(userMission -> {
            userMission.setIsAccepted(Constants.USER_MISSION_ACCEPTED);
            userMissionRepository.save(userMission);
            logger.info("UserMission accepted. reason: Submitted but not evaluated by admin. user_id: {}, mission_id: {} , ground_id: {}, submit_at: {}",
                    userMission.getUser().getId(), userMission.getMission().getId(), userMission.getMission().getGround().getId(), userMission.getSubmitAt());
        });
    }
}
