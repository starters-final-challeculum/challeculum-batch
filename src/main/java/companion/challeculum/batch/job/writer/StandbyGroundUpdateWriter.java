package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.entity.User;
import companion.challeculum.batch.entity.UserGround;
import companion.challeculum.batch.repository.GroundRepository;
import companion.challeculum.batch.repository.UserGroundRepository;
import companion.challeculum.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public void write(List<? extends Ground> grounds) {
        for (Ground ground : grounds) {
            // 최소인원 미충족 시 예치금 환불 및 그라운드 삭제(X) 그라운드 상태 GROUND_CANCELLED
            if (ground.getUserGroundList().size() < ground.getMinCapacity()) {
                ground.getUserGroundList().forEach(userGround -> {
                    User user = userGround.getUser();
                    user.setPoint(user.getPoint() + ground.getDeposit());
                    userRepository.save(user);
                    userGroundRepository.delete(userGround);
                });
                ground.setStatus(Constants.GROUND_CANCELLED);
            }
            // 최소인원 충족시 상태 업데이트(GROUND_ONGOING)
            else {
                ground.setStatus(Constants.GROUND_ONGOING);
            }
            groundRepository.save(ground);
        }
    }
}
