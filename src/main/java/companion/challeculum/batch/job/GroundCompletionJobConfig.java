package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.job.processor.OngoingGroundProcessor;
import companion.challeculum.batch.job.reader.OngoingGroundReader;
import companion.challeculum.batch.job.writer.OngoingGroundWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job
 */
@Configuration
@RequiredArgsConstructor
public class GroundCompletionJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OngoingGroundReader ongoingGroundReader;
    private final OngoingGroundProcessor ongoingGroundProcessor;
    private final OngoingGroundWriter ongoingGroundWriter;

    @Bean
    public Job groundCompletionJob(Step groundCompletionStep) {
        return jobBuilderFactory.get("groundCompletionJob")
                .incrementer(new RunIdIncrementer())
                .start(groundCompletionStep)
                .build();
    }

    @Bean
    public Step groundCompletionStep() {
        return stepBuilderFactory.get("groundCompletionStep")
                .<Ground, Ground>chunk(10)
                .reader(ongoingGroundReader)
                .processor(ongoingGroundProcessor)
                .writer(ongoingGroundWriter)
                .build();
    }

}
