package companion.challeculum.batch.job.reader;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.repository.GroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.reader
 */
@Component
@RequiredArgsConstructor
public class OngoingGroundReader implements ItemReader<Ground> {
    private final GroundRepository groundRepository;
    private Iterator<Ground> groundIterator;

    @Override
    public Ground read() {
        if (groundIterator == null) {
            List<Ground> ongoingGrounds = groundRepository
                    .findByEndAtEqualsAndStatusEquals(LocalDate.now().minusDays(1), Constants.GROUND_STANDBY);
            groundIterator = ongoingGrounds.iterator();
        }
        if (groundIterator.hasNext()) return groundIterator.next();
        else return null;
    }
}
