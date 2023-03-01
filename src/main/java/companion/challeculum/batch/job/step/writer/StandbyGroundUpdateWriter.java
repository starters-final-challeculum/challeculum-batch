package companion.challeculum.batch.job.step.writer;

import companion.challeculum.batch.common.Constants;
import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.entity.User;
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
public class StandbyGroundUpdateWriter implements ItemWriter<Ground> {
    private final GroundRepository groundRepository;
    private final UserRepository userRepository;
    private final UserGroundRepository userGroundRepository;

    private static final Logger logger = LoggerFactory.getLogger(StandbyGroundUpdateWriter.class);

    @Override
    @Transactional
    public void write(List<? extends Ground> grounds) {
        for (Ground ground : grounds) {
            if (ground.getUserGroundList().size() < ground.getMinCapacity()) {
                logger.info("Ground cancelled. ground_id: {}, reason: Not enough participants. Current participants: {}, Min capacity: {}",
                        ground.getId(), ground.getUserGroundList().size(), ground.getMinCapacity());
                ground.getUserGroundList().forEach(userGround -> {
                    User user = userGround.getUser();
                    user.setPoint(user.getPoint() + ground.getDeposit());
                    userRepository.save(user);
                    userGroundRepository.delete(userGround);
                });
                ground.setStatus(Constants.GROUND_CANCELLED);
            } else {
                logger.info("Ground ongoing. ground_id: {}", ground.getId());
                ground.setStatus(Constants.GROUND_ONGOING);
            }
            groundRepository.save(ground);
        }
    }
}
