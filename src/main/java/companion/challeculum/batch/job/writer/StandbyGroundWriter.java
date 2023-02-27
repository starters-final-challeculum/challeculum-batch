package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.entity.UserGround;
import companion.challeculum.batch.repository.GroundRepository;
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
public class StandbyGroundWriter implements ItemWriter<Ground> {
    private final GroundRepository groundRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void write(List<? extends Ground> grounds) {
        for (Ground ground : grounds) {
            // 최소인원 미충족 시 예치금 환불 및 그라운드 삭제
            if (ground.getUserGroundList().size() < ground.getMinCapacity()) {
                ground.getUserGroundList().stream().map(UserGround::getUser).forEach(user -> {
                    user.setPoint(user.getPoint() + ground.getDeposit());
                    userRepository.save(user);
                });
                groundRepository.delete(ground);
            }
            // 최소인원 충족시 상태 업데이트(GROUND_ONGOING)
            else groundRepository.save(ground);
        }
    }
}
