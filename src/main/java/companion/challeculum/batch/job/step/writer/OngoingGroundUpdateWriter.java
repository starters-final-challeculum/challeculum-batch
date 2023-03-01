package companion.challeculum.batch.job.step.writer;

import companion.challeculum.batch.common.Constants;
import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.entity.UserGround;
import companion.challeculum.batch.repository.GroundRepository;
import companion.challeculum.batch.repository.UserGroundRepository;
import companion.challeculum.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.writer
 */
@Component
@RequiredArgsConstructor
public class OngoingGroundUpdateWriter implements ItemWriter<Ground> {

    private final UserRepository userRepository;
    private final GroundRepository groundRepository;
    private final UserGroundRepository userGroundRepository;

    private static final Logger logger = LoggerFactory.getLogger(OngoingGroundUpdateWriter.class);

    @Override
    @Transactional
    public void write(List<? extends Ground> grounds) {
        for (Ground ground : grounds) {
            List<UserGround> totalUserGround = userGroundRepository.findAllByGroundId(ground.getId()).stream().peek(userGround -> {
                if (userGround.getIsSuccess() == null) {
                    userGround.setIsSuccess(true);
                    userGroundRepository.save(userGround);
                }
            }).toList();
            List<UserGround> successUserGroundList = totalUserGround.stream().filter(UserGround::getIsSuccess).toList();

            int totalDeposit = ground.getDeposit() * totalUserGround.size();
            int reward = successUserGroundList.isEmpty() ? 0 : totalDeposit / successUserGroundList.size();

            for (UserGround userGround : successUserGroundList) {
                userGround.getUser().setPoint(userGround.getUser().getPoint() + reward);
                userGround.getUser().setMissionScore(userGround.getUser().getMissionScore() + 5);
                userRepository.save(userGround.getUser());
            }
            logger.info("Ground completed. ground_id: {}, total_users: {}, success_users: {}, total_deposit: {}, reward_per_user: {}",
                    ground.getId(), totalUserGround.size(), successUserGroundList.size(), totalDeposit, reward);
            ground.setStatus(Constants.GROUND_COMPLETED);
            groundRepository.save(ground);
        }
    }
}


