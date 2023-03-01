package companion.challeculum.batch.job.step.reader;

import companion.challeculum.batch.common.Constants;
import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.repository.GroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

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
//            List<Ground> ongoingGrounds = groundRepository.findByEndAtEqualsAndStatusEquals(LocalDate.now().minusDays(1), Constants.GROUND_ONGOING);
            List<Ground> ongoingGrounds = groundRepository.findByStatusEquals(Constants.GROUND_ONGOING);
            groundIterator = ongoingGrounds.iterator();
        }
        if (groundIterator.hasNext()) return groundIterator.next();
        else return null;
    }
}
