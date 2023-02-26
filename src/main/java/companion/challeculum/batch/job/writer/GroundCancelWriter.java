package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.entity.UserGround;
import companion.challeculum.batch.repository.GroundRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.reader
 */

@Component
@RequiredArgsConstructor
public class GroundCancelWriter implements ItemWriter<Ground> {
    private static final Logger log = LoggerFactory.getLogger(GroundCancelWriter.class);
    private final GroundRepository groundRepository;

    @Override
    public void write(List<? extends Ground> grounds) {
        grounds.forEach(ground -> {
            log.info("Canceling ground: {}", ground);
            groundRepository.delete(ground);
        });
    }
}
