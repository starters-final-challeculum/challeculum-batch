package companion.challeculum.batch.job.reader;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.repository.GroundRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.reader
 */

@Component
@RequiredArgsConstructor
public class GroundCancelReader implements ItemReader<Ground> {
    private static final Logger log = LoggerFactory.getLogger(GroundCancelReader.class);

    private final GroundRepository groundRepository;

    @Override
    public Ground read() {
        LocalDate now = LocalDate.now();
        List<Ground> grounds = groundRepository.findByStartAtBeforeAndStatus(now, Constants.GROUND_ONGOING);

        if (grounds.isEmpty()) {
            log.info("No grounds to cancel.");
            return null;
        }

        // return first ground to cancel
        return grounds.get(0);
    }
}
