package companion.challeculum.batch.job;

import companion.challeculum.batch.entity.Ground;
import companion.challeculum.batch.entity.Mission;
import companion.challeculum.batch.job.processor.StandbyGroundProcessor;
import companion.challeculum.batch.job.reader.TodayMissionReader;
import companion.challeculum.batch.job.writer.MarkGroundFailWriter;
import companion.challeculum.batch.job.writer.StandbyGroundWriter;
import companion.challeculum.batch.repository.GroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class MarkGroundFailJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final GroundRepository groundRepository;

    @Bean
    public Job updateGroundStatusJob(TodayMissionReader todayMissionReader, MarkGroundFailWriter markGroundFailWriter) {
        return jobBuilderFactory.get("updateGroundStatusJob")
                .incrementer(new RunIdIncrementer())
                .start(updateGroundStatusStep(todayMissionReader, markGroundFailWriter))
                .build();
    }

    @Bean
    public Step updateGroundStatusStep(TodayMissionReader todayMissionReader, MarkGroundFailWriter markGroundFailWriter) {
        return stepBuilderFactory.get("updateGroundStatusStep")
                .<Mission, Mission>chunk(10)
                .reader(todayMissionReader)
                .writer(markGroundFailWriter)
                .build();
    }
}
