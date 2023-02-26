package companion.challeculum.batch.job.processor;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.Ground;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.processor
 */
public class StandbyGroundProcessor implements ItemProcessor<Ground, Ground> {

    @Override
    public Ground process(Ground item) {
        item.setStatus(Constants.GROUND_ONGOING);
        return item;
    }
}
