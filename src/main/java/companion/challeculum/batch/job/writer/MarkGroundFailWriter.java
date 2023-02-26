package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.entity.Mission;
import companion.challeculum.batch.entity.UserGround;
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
public class MarkGroundFailWriter implements ItemWriter<Mission> {
    private final UserMissionRepository userMissionRepository;
    @Override
    public void write(List<? extends Mission> list) throws Exception {
        list.forEach(mission -> {
            Ground ground = mission.getGround();
            List<UserGround> userGroundList = ground.getUserGroundList();
            userGroundList.forEach(userGround -> {
                UserMission userMission = userMissionRepository
                        .findByUserIdAndMissionId(userGround.getUser().getId(), mission.getId());
                if (userMission == null) userGround.setSuccess(false);
                if (userMission.getIsAccepted().equals(Constants.USER_MISSION_REJECTED)) userGround.setSuccess(false);
            });
        });
    }
}
