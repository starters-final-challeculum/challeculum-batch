package companion.challeculum.batch.job.step.writer;

import companion.challeculum.batch.common.Constants;
import companion.challeculum.batch.entity.*;
import companion.challeculum.batch.repository.UserGroundRepository;
import companion.challeculum.batch.repository.UserMissionRepository;
import companion.challeculum.batch.repository.UserRepository;
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
public class MissionAndUserGroundResultWriter implements ItemWriter<Mission> {

    private final UserMissionRepository userMissionRepository;
    private final UserGroundRepository userGroundRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(MissionAndUserGroundResultWriter.class);

    @Override
    public void write(List<? extends Mission> list) {
        for (Mission mission : list) {
            Ground ground = mission.getGround();
            List<UserGround> userGroundList = ground.getUserGroundList();
            for (UserGround userGround : userGroundList) {
                User user = userGround.getUser();
                UserMission userMission = userMissionRepository.findByUserIdAndMissionId(user.getId(), mission.getId());
                int updateScore;
                if (userMission == null) {
                    userGround.setIsSuccess(false);
                    updateScore = -10;
                    userGroundRepository.save(userGround);
                    logger.info("UserGround Failed : UserMission omitted. user_id: {}, mission_id: {}, ground_id: {}", user.getId(), mission.getId(), ground.getId());
                } else if (Constants.USER_MISSION_REJECTED.equals(userMission.getIsAccepted())) {
                    userGround.setIsSuccess(false);
                    updateScore = -5;
                    userGroundRepository.save(userGround);
                    logger.info("UserGround Failed : UserMission rejected. user_id: {}, mission_id: {}, ground_id: {}", user.getId(), mission.getId(), ground.getId());
                } else {
                    updateScore = 10;
                }
                user.setMissionScore(user.getMissionScore() + updateScore);
                userRepository.save(user);
                logger.info("User score updated. user_id: {}, updateScore : {}, mission_score: {}", user.getId(), updateScore, user.getMissionScore());
            }
        }
    }
}
