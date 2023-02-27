package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.Mission;
import companion.challeculum.batch.job.reader.TodayMissionReader;
import companion.challeculum.batch.job.writer.MissionAndUserGroundResultWriter;
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
public class SettleTodayResultConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TodayMissionReader todayMissionReader;
    private final MissionAndUserGroundResultWriter missionAndUserGroundResultWriter;


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

