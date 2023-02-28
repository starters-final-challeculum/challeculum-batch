package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.Ground;
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
public class OngoingGroundUpdateWriter implements ItemWriter<Ground> {
    private final UserRepository userRepository;
    private final GroundRepository groundRepository;
    private final UserGroundRepository userGroundRepository;

    @Override
    @Transactional
    public void write(List<? extends Ground> grounds) {
        // 성공한 유저에게 보상(전체 예치금 / 성공한 유저 수)
        grounds.forEach(ground -> {
            List<UserGround> totalUserGround = userGroundRepository.findAllByGroundId(ground.getId()).stream().peek(userGround -> {
                if (userGround.getIsSuccess() == null) {
                    userGround.setIsSuccess(true);
                    userGroundRepository.save(userGround);
                }
            }).toList();
            List<UserGround> successUserGroundList = totalUserGround.stream().filter(UserGround::getIsSuccess).toList();

            int totalDeposit = ground.getDeposit() * totalUserGround.size();
            int reward = totalDeposit / successUserGroundList.size();
            successUserGroundList.stream().map(UserGround::getUser).forEach(user -> {
                user.setPoint(user.getPoint() + reward);
                user.setMissionScore(user.getMissionScore() + 5);
                userRepository.save(user);
            });
            ground.setStatus(Constants.GROUND_COMPLETED);
            groundRepository.save(ground);
        });
    }
}

