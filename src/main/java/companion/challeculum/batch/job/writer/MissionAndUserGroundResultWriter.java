package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.*;
import companion.challeculum.batch.repository.UserGroundRepository;
import companion.challeculum.batch.repository.UserMissionRepository;
import companion.challeculum.batch.repository.UserRepository;
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
public class MissionAndUserGroundResultWriter implements ItemWriter<Mission> {
    private final UserMissionRepository userMissionRepository;
    private final UserGroundRepository userGroundRepository;
    private final UserRepository userRepository;

    @Override
    public void write(List<? extends Mission> list) {
        list.forEach(mission -> {
            Ground ground = mission.getGround();
            List<UserGround> userGroundList = ground.getUserGroundList();
            userGroundList.forEach(userGround -> {
                User user = userGround.getUser();
                UserMission userMission = userMissionRepository
                        .findByUserIdAndMissionId(user.getId(), mission.getId());
                if (userMission == null) {
                    userGround.setIsSuccess(false);
                    user.setMissionScore(user.getMissionScore() - 10);
                    userGroundRepository.save(userGround);
                }
                if (userMission.getIsAccepted().equals(Constants.USER_MISSION_REJECTED)) {
                    userGround.setIsSuccess(false);
                    user.setMissionScore(user.getMissionScore() - 5);
                    userGroundRepository.save(userGround);
                } else {
                    user.setMissionScore(user.getMissionScore() + 10);
                }
                userRepository.save(user);
            });
        });
    }
}
