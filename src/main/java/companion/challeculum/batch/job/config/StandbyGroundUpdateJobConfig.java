package companion.challeculum.batch.job.config;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.job.step.processor.StandbyGroundProcessor;
import companion.challeculum.batch.job.step.reader.StandbyGroundReader;
import companion.challeculum.batch.job.step.writer.StandbyGroundUpdateWriter;
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
public class StandbyGroundUpdateJobConfig {
    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final StandbyGroundReader standbyGroundReader;
    private final StandbyGroundProcessor standbyGroundProcessor;
    private final StandbyGroundUpdateWriter standbyGroundUpdateWriter;

    @Scheduled(cron = "0 0 6 * * *") // 매일 6시에 실행
    public void runStandbyGroundUpdateJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(
                standbyGroundUpdateJob(standbyGroundUpdateStep()),
                jobParameters
        );
    }


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
                .writer(standbyGroundUpdateWriter)
                .build();
    }

}
