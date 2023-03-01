package companion.challeculum.batch.job.config;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.job.step.processor.OngoingGroundProcessor;
import companion.challeculum.batch.job.step.reader.OngoingGroundReader;
import companion.challeculum.batch.job.step.writer.OngoingGroundUpdateWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by jonghyeon on 2023/02/28,
 * Package : companion.challeculum.batch.job
 */
@Configuration
@RequiredArgsConstructor
public class OngoingGroundUpdateJobConfig {
    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OngoingGroundReader ongoingGroundReader;
    private final OngoingGroundProcessor ongoingGroundProcessor;
    private final OngoingGroundUpdateWriter ongoingGroundUpdateWriter;

    @Scheduled(cron = "0 0 6 * * *") // 매일 6시에 실행
    public void runOngoingGroundUpdateJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(
                ongoingGroundUpdateJob(ongoingGroundUpdateStep()),
                jobParameters
        );
    }

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