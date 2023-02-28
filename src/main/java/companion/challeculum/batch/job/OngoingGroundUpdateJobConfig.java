package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.job.processor.OngoingGroundProcessor;
import companion.challeculum.batch.job.reader.OngoingGroundReader;
import companion.challeculum.batch.job.writer.OngoingGroundUpdateWriter;
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
public class OngoingGroundUpdateJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OngoingGroundReader ongoingGroundReader;
    private final OngoingGroundProcessor ongoingGroundProcessor;
    private final OngoingGroundUpdateWriter ongoingGroundUpdateWriter;

    @Bean
    public Job ongoingGroundUpdateJob(Step ongoingGroundUpdateStep) {
        return jobBuilderFactory.get("ongoingGroundUpdateJob")
                .incrementer(new RunIdIncrementer())
                .start(ongoingGroundUpdateStep)
                .build();
    }

    @Bean
    public Step ongoingGroundUpdateStep() {
        return stepBuilderFactory.get("ongoingGroundUpdateStep")
                .<Ground, Ground>chunk(10)
                .reader(ongoingGroundReader)
                .processor(ongoingGroundProcessor)
                .writer(ongoingGroundUpdateWriter)
                .build();
    }
}