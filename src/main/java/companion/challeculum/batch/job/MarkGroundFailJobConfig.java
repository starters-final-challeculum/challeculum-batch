package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.Mission;
import companion.challeculum.batch.job.reader.TodayMissionReader;
import companion.challeculum.batch.job.writer.MarkUserGroundFailWriter;
import companion.challeculum.batch.repository.GroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
public class MarkGroundFailJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TodayMissionReader todayMissionReader;
    private final MarkUserGroundFailWriter markUserGroundFailWriter;

    @Bean
    public Job updateGroundStatusJob(Step updateGroundStatusStep) {
        return jobBuilderFactory.get("updateGroundStatusJob")
                .incrementer(new RunIdIncrementer())
                .start(updateGroundStatusStep)
                .build();
    }

    @Bean
    public Step updateGroundStatusStep() {
        return stepBuilderFactory.get("updateGroundStatusStep")
                .<Mission, Mission>chunk(10)
                .reader(todayMissionReader)
                .writer(markUserGroundFailWriter)
                .build();
    }
}
