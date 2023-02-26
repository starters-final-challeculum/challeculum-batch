package companion.challeculum.batch.job.processor;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.Ground;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.processor
 */
public class OngoingGroundProcessor implements ItemProcessor<Ground, Ground> {
    private static final Logger log = LoggerFactory.getLogger(OngoingGroundProcessor.class);

    @Override
    public Ground process(Ground ground) throws Exception {
        log.info("Checking ground status: {}", ground);

        LocalDate now = LocalDate.now();
        if (ground.getStartAt().isBefore(now) && ground.getStatus().equals(Constants.GROUND_STANDBY)) {
            // Update ground status to ONGOING
            log.info("Starting ground: {}", ground);
            ground.setStatus(Constants.GROUND_STANDBY);
        }

        return ground;
    }
}

