package companion.challeculum.batch.job.config;

import companion.challeculum.batch.entity.UserMission;
import companion.challeculum.batch.job.step.reader.WaitingUserMissionReader;
import companion.challeculum.batch.job.step.writer.WaitingUserMissionWriter;
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
public class WaitingUserMissionJobConfig {
    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WaitingUserMissionReader waitingUserMissionReader;
    private final WaitingUserMissionWriter waitingUserMissionWriter;

    @Scheduled(cron = "0 50 5 * * *") // 매일 5시 50분에 실행
    public void runWaitingUserMissionJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(
                waitingUserMissionJob(waitingUserMissionStep()),
                jobParameters
        );
    }

    @Bean
    public Job waitingUserMissionJob(Step waitingUserMissionStep) {
        return jobBuilderFactory.get("waitingUserMissionJob")
                .incrementer(new RunIdIncrementer())
                .start(waitingUserMissionStep)
                .build();
    }

    @Bean
    public Step waitingUserMissionStep() {
        return stepBuilderFactory.get("waitingUserMissionStep")
                .<UserMission, UserMission>chunk(10)
                .reader(waitingUserMissionReader)
                .writer(waitingUserMissionWriter)
                .build();
    }
}
