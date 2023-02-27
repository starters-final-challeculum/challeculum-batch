package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.UserMission;
import companion.challeculum.batch.job.reader.WaitingUserMissionReader;
import companion.challeculum.batch.job.writer.WaitingUserMissionWriter;
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
public class WaitingUserMissionJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final WaitingUserMissionReader waitingUserMissionReader;
    private final WaitingUserMissionWriter waitingUserMissionWriter;

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
