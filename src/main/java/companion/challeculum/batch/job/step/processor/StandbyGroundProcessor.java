package companion.challeculum.batch.job.step.processor;

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
