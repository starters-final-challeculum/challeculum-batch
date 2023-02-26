package companion.challeculum.batch.job.writer;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.repository.GroundRepository;
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

    @Override
    @Transactional
    public void write(List<? extends Ground> grounds) throws Exception {
        for (Ground ground : grounds) {
            groundRepository.save(ground);
        }
    }
}
