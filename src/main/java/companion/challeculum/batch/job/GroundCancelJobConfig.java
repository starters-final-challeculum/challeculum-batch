package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.job.processor.GroundCancelProcessor;
import companion.challeculum.batch.job.reader.GroundCancelReader;
import companion.challeculum.batch.job.writer.GroundCancelWriter;
import companion.challeculum.batch.repository.GroundRepository;
import companion.challeculum.batch.repository.UserGroundRepository;
import companion.challeculum.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job
 */
@Configuration
@RequiredArgsConstructor
public class GroundCancelJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final GroundRepository groundRepository;
    private final UserRepository userRepository;
    private final UserGroundRepository userGroundRepository;
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
                .reader(cancelGroundReader())
                .processor(cancelGroundProcessor())
                .writer(cancelGroundWriter())
                .build();
    }

    @Bean
    public ItemReader<Ground> cancelGroundReader() {
        return new GroundCancelReader(groundRepository);
    }

    @Bean
    public ItemProcessor<Ground, Ground> cancelGroundProcessor() {
        return new GroundCancelProcessor(userRepository);
    }

    @Bean
    public ItemWriter<Ground> cancelGroundWriter() {
        return new GroundCancelWriter(groundRepository);
    }
}
