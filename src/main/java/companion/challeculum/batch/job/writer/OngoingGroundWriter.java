package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.config.Constants;
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
public class OngoingGroundWriter implements ItemWriter<Ground> {
    private static final Logger log = LoggerFactory.getLogger(OngoingGroundWriter.class);
    private final UserRepository userRepository;
    private final GroundRepository groundRepository;
    private final UserGroundRepository userGroundRepository;

    @Override
    @Transactional
    public void write(List<? extends Ground> grounds) {
        for (Ground ground : grounds) {
            log.info("Completing ground: {}", ground);
            try {
                // Update ground status to COMPLETED
                ground.setStatus(Constants.GROUND_COMPLETED);
                groundRepository.save(ground);

                // Pay rewards to successful users
                List<UserGround> totalUserGround = userGroundRepository.findAllByGround_Id(ground.getId());
                List<UserGround> successUserGroundList = userGroundRepository.findAllByGround_Id(ground.getId()).stream()
                        .filter(UserGround::isSuccess).toList();
                int totalDeposit = ground.getDeposit() * totalUserGround.size();
                int reward = totalDeposit / successUserGroundList.size();
                successUserGroundList.stream().map(UserGround::getUser).forEach(user -> {
                    user.setPoint(user.getPoint() + reward);
                    userRepository.save(user);
                });
                log.info("Completed ground: {}", ground);
            } catch (Exception e) {
                log.error("Error completing ground: " + ground, e);
            }
        }
    }
}

