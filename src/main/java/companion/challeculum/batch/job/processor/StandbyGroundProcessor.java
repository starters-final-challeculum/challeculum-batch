package companion.challeculum.batch.job.processor;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.Ground;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.processor
 */
@Component
@RequiredArgsConstructor
public class StandbyGroundProcessor implements ItemProcessor<Ground, Ground> {

    @Override
    public Ground process(Ground ground) {
        return ground;
    }
}
