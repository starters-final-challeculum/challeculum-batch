package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.job.processor.GroundCancelProcessor;
import companion.challeculum.batch.job.reader.GroundCancelReader;
import companion.challeculum.batch.job.writer.GroundCancelWriter;
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
public class GroundCancelJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final GroundCancelReader groundCancelReader;
    private final GroundCancelProcessor groundCancelProcessor;
    private final GroundCancelWriter groundCancelWriter;
    @Bean
    public Job groundCancelJob() {
        return jobBuilderFactory.get("groundCancelJob")
                .incrementer(new RunIdIncrementer())
                .start(cancelGroundStep())
                .build();
    }

    @Bean
    public Step cancelGroundStep() {
        return stepBuilderFactory.get("cancelGroundStep")
                .<Ground, Ground>chunk(10)
                .reader(groundCancelReader)
                .processor(groundCancelProcessor)
                .writer(groundCancelWriter)
                .build();
    }

}
