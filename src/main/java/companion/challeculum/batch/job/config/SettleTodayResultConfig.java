package companion.challeculum.batch.job.config;

import companion.challeculum.batch.entity.Mission;
import companion.challeculum.batch.job.step.reader.TodayMissionReader;
import companion.challeculum.batch.job.step.writer.MissionAndUserGroundResultWriter;
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
public class SettleTodayResultConfig {
    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TodayMissionReader todayMissionReader;
    private final MissionAndUserGroundResultWriter missionAndUserGroundResultWriter;

    @Scheduled(cron = "0 40 5 * * *") // 매일 5시 40분에 실행
    public void runSettleTodayResultJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(
                settleTodayResultJob(settleTodayResultStep()),
                jobParameters
        );
    }

    @Bean
    public Job settleTodayResultJob(Step settleTodayResultStep) {
        return jobBuilderFactory.get("settleTodayResultJob")
                .incrementer(new RunIdIncrementer())
                .start(settleTodayResultStep)
                .build();
    }

    @Bean
    public Step settleTodayResultStep() {
        return stepBuilderFactory.get("settleTodayResultStep")
                .<Mission, Mission>chunk(10)
                .reader(todayMissionReader)
                .writer(missionAndUserGroundResultWriter)
                .build();
    }
}

