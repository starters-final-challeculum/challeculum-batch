package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.job.processor.StandbyGroundProcessor;
import companion.challeculum.batch.job.reader.StandbyGroundReader;
import companion.challeculum.batch.job.writer.StandbyGroundWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jonghyeon on 2023/02/28,
 * Package : companion.challeculum.batch.job
 */
@Configuration
@RequiredArgsConstructor
public class StandbyGroundUpdateJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final StandbyGroundReader standbyGroundReader;
    private final StandbyGroundProcessor standbyGroundProcessor;
    private final StandbyGroundWriter standbyGroundWriter;

    @Bean
    public Job standbyGroundUpdateJob(Step standbyGroundUpdateStep) {
        return jobBuilderFactory.get("standbyGroundUpdateJob")
                .incrementer(new RunIdIncrementer())
                .start(standbyGroundUpdateStep)
                .build();
    }

    @Bean
    public Step standbyGroundUpdateStep() {
        return stepBuilderFactory.get("standbyGroundUpdateStep")
                .<Ground, Ground>chunk(10)
                .reader(standbyGroundReader)
                .processor(standbyGroundProcessor)
                .writer(standbyGroundWriter)
                .build();
    }

}
