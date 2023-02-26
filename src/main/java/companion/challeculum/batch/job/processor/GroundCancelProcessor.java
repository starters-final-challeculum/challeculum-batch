package companion.challeculum.batch.job.processor;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.entity.User;
import companion.challeculum.batch.entity.UserGround;
import companion.challeculum.batch.repository.UserGroundRepository;
import companion.challeculum.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.reader
 */
@Component
@RequiredArgsConstructor
public class GroundCancelProcessor implements ItemProcessor<Ground, Ground> {
    private final UserRepository userRepository;

    @Override
    public Ground process(Ground ground) {
        if (ground.getUserGroundList().size() < ground.getMinCapacity()) {
            // Cancel ground and refund deposit
            ground.getUserGroundList().stream().map(UserGround::getUser).forEach(user -> {
                user.setPoint(user.getPoint() + ground.getDeposit());
                userRepository.save(user);
            });
            return ground;
        } else {
            return null;
        }
    }
}
